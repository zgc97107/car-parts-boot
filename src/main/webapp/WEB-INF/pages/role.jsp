<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>角色管理</legend>
    </fieldset>
</div>
<form action="#" id="doc-search-form" class="layui-form" method="post">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">查询条件：</label>
            <div class="layui-input-inline">
                <div lay-filter="doc-type" class="layui-input-inline">
                    <input type="text" name="rolename" placeholder="角色姓名" class="layui-input" >
                </div>
            </div>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-role">搜索</button>
    </div>
</form>

<script type="text/html" id="role-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon">&#xe654;</i>新增</button>
        <div class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon">&#xe640;</i>批量删除</div>
    </div>
</script>
<script type="text/html" id="doc-doc-bar">
    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="up-role">启用/禁用</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit-role">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-role">删除</a>
</script>
<table class="layui-table" lay-filter="role-table" id="role-table">
</table>
<script type="text/html" id="doc-edit-layer">
    <form id="doc-edit-form" style="width: 80%;padding-top: 15px;" class="layui-form" lay-filter="doc-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">角色名称</label>
            <div class="layui-input-inline">
                <input type="text" name="rolename" id="rolename" autocomplete="off" placeholder="请输入角色名称" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">名称至少两个字</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单资源</label>
            <div class="layui-input-block">
                <ul>
                    <c:forEach items="${menus}" var="menu1">
                        <li><input type="checkbox" name="menuid" id="menu${menu1.id}" value="${menu1.id}" lay-skin="primary" title="${menu1.menuname}" lay-filter="check-menu1">
                            <ul class="menu${menu1.id}">
                                <c:forEach items="${menu1.seconds}" var="menu2">
                                    <li>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" name="menuid" value="${menu2.id}" class="checkparent" lay-skin="primary" title="${menu2.menuname}" lay-filter="check-menu2"></li>
                                </c:forEach>
                            </ul>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">角色状态</label>
            <div class="layui-input-block">
                <input type="radio" name="roleState" id="used" value="1" title="启用">
                <input type="radio" name="roleState" id="useless" value="0" title="禁用">
            </div>
        </div>
    </form>
</script>
<script type="text/html" id="view-div" >
    <table lay-filter="demo" id="demo">
    </table>
</script>

<script>
    var table
    var layer
    var form
    var laydate
    layui.use(['table','layer','form','laydate'],function () {
        table=layui.table;
        layer=layui.layer;
        form=layui.form;
        laydate=layui.laydate;

        table.render({
            id:"role-table",
            elem: '#role-table'
            , toolbar: '#role-head-bar'
            , cellMinWidth: 80
            , url: '${pageContext.request.contextPath}/role.html?act=rolesPage' //数据接口
            , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
                layout: [ 'prev', 'page', 'next', 'count', 'skip','limit'] //自定义分页布局
                //,curr: 5 //设定初始在第 5 页
                ,groups: 5 //只显示 1 个连续页码
                ,first: false //不显示首页
                ,last: false //不显示尾页,
                ,limits:[5,10,15,20,25,30]
                ,limit:5

            }

            , cols: [[ //表头
                {field: 'id',type: 'checkbox', fixed: 'left'}
                ,{field: 'rolename',title:'角色名称',align:'center'}
                ,{
                    field: 'roleState', title: '状态', align:'center', templet: function (data) {
                        return (data.roleState==1?'启用':'禁用')
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#doc-doc-bar', width: 220, align:'center'}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(role-table)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                case 'add':
                    openEditWindow();
                    /* 渲染表单 */
                    form.render();
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('至少选择一行');
                    } else {
                        layer.confirm('确定删除吗？',function(index){
                            delRoles(data.map(function(item){return item.id;}));
                        });
                    }
                    break;
            };
        });
        //监听行工具事件
        table.on('tool(role-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'up-role':{
                    layer.confirm('确定改变状态吗？',{title: '启用/禁用'},function(index){
                        upRole(data.id,data.roleState);
                    });
                    break;
                }
                case 'edit-role': {
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.val("doc-edit-form", {
                            "id":data.id
                            , "rolename": data.rolename
                        });
                        var menuids=$("input[name='menuid']");
                        for(var i=0;i<data.menuList.length;i++){
                            for(var x=0;x<menuids.length;x++){
                                if(data.menuList[i].id==menuids[x].value){
                                    menuids[x].checked = true;
                                }
                            }
                        }
                        form.render('checkbox');
                        if(data.roleState==1){
                            $("#used").attr("checked","checked");
                        }else{
                            $("#useless").attr("checked","checked");
                        }
                        form.render('radio');
                    })
                    break;
                }
                case 'del-role':{
                    layer.confirm('确定删除吗？',function(index){
                        delRoles(data.id);
                    });
                    break;
                }
            }
        })
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-role)', function(data){
                table.reload('role-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });

            /**
             * 点击某个子菜单，自动选中相应的父菜单
             */
            form.on('checkbox(check-menu2)', function(data){
                var menu2=data.elem;
                var cp=menu2.parentNode.parentNode.previousSibling.previousSibling;
                if(data.elem.checked){
                    cp.classList.add("layui-form-checked");
                }
            });

        })

        function openEditWindow() {
            layer.open({
                type: 1,
                content: $('#doc-edit-layer').html(),
                title: '角色信息'
                , area: ['600px', '400px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        return;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/role.html?act=edit',
                            data: $("#doc-edit-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('role-table', {});
                                    layer.msg('操作成功');
                                    layer.close(index);
                                } else {
                                    layer.alert(result.message)
                                }
                            }
                        });
                    }
                }
            });
        }

        function verify(){
            var rolename=$("#rolename").val();
            var roleState=$("input[name='roleState']:checked").val();
            if(rolename.length<2){
                layer.msg('角色名称至少两个字符',{icon:5});
                return "false";
            }
            if(!roleState){
                layer.msg('请选择角色状态',{icon:5});
                return "false";
            }
        }
        function delRoles(ids) {
            $.ajax({
                url: '${pageContext.request.contextPath}/role.html?act=del',
                data: "ids="+ids,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('role-table', {});
                        layer.msg('删除成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }
        function upRole(id,roleState) {
            $.ajax({
                url: '${pageContext.request.contextPath}/role.html?act=upState',
                data: "id="+id+"&roleState="+roleState,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('role-table', {});
                        layer.msg('操作成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }

    })



</script>
