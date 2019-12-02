<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>菜单管理</legend>
    </fieldset>
</div>
<form action="#" id="doc-search-form" class="layui-form" method="post">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">查询条件：</label>
            <div class="layui-input-inline">
                <div lay-filter="doc-type" class="layui-input-inline">
                    <input type="text" name="menuname" placeholder="菜单名称" class="layui-input" >
                </div>
            </div>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-menu">搜索</button>
    </div>
</form>

<script type="text/html" id="menu-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon">&#xe654;</i>新增</button>
    </div>
</script>
<script type="text/html" id="doc-doc-bar">
    <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="up-menu">启用/禁用</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit-menu">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-menu">删除</a>
</script>
<table class="layui-table" lay-filter="menu-table" id="menu-table">
</table>
<script type="text/html" id="doc-edit-layer">
    <form id="doc-edit-form" style="width: 80%;padding-top: 15px;" class="layui-form" lay-filter="doc-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">菜单名称</label>
            <div class="layui-input-inline">
                <input type="text" name="menuname" id="menuname" autocomplete="off" placeholder="请输入菜单名称" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">名称至少两个字</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">上级菜单</label>
            <div class="layui-input-inline">
                <select name="umEdit" id="umEdit" lay-search>
                    <option value="-1">---无上级菜单---</option>
                    <c:forEach items="${upMenus}" var="upMenu">
                        <option value="${upMenu.id}">${upMenu.menuname}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="umInit" id="umInit">
            </div>
            <div class="layui-form-mid layui-word-aux">父级目录无需选择</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单路径</label>
            <div class="layui-input-inline">
                <input type="text" name="menupath" id="menupath" autocomplete="off" placeholder="请输入菜单路径" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">父级目录路径用/表示即可</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">菜单状态</label>
            <div class="layui-input-block">
                <input type="radio" name="menuState" id="used" value="1" title="启用">
                <input type="radio" name="menuState" id="useless" value="0" title="禁用">
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
            id:"menu-table",
            elem: '#menu-table'
            , toolbar: '#menu-head-bar'
            , cellMinWidth: 80
            , url: '${pageContext.request.contextPath}/menu.html?act=menusPage' //数据接口
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
                ,{field: 'menuname',title:'菜单名称',align:'center'}

                ,{field: 'menupath',title:'菜单路径',align:'center'}
                ,{
                    field: 'menuState', title: '状态', align:'center', templet: function (data) {
                        return (data.menuState==1?'启用':'禁用')
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#doc-doc-bar', width: 220, align:'center'}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(menu-table)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                case 'add':
                    openEditWindow();
                    /* 渲染表单 */
                    form.render();9
                    table.reload('menu-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                    break;

            };
        });
        //监听行工具事件
        table.on('tool(menu-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'up-menu':{
                    layer.confirm('确定改变状态吗？',{title: '启用/禁用'},function(index){
                        upMenu(data.id,data.menuState);
                    });
                    break;
                }
                case 'edit-menu': {
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.val("doc-edit-form", {
                            "id":data.id
                            , "menuname": data.menuname
                            , "menupath": data.menupath
                            , "umInit":data.upmenuid
                        });
                        var umInit=$("#umInit").val();
                        // 遍历select
                        $("[name='umEdit']").each(function() {
                            // this代表的是<option></option>，对option再进行遍历
                            $(this).children("option").each(function() {
                                // 判断需要对那个选项进行回显
                                if (this.value == umInit) {
                                    // 进行回显
                                    $(this).prop("selected", "selected");
                                }
                            });
                        });
                        form.render('select');
                        if(data.menuState==1){
                            $("#used").attr("checked","checked");
                        }else{
                            $("#useless").attr("checked","checked");
                        }
                        form.render('radio');

                    })
                    break;
                }
                case 'del-menu':{
                    layer.confirm('确定删除吗？',function(index){
                        delMenus(data.id);
                    });
                    break;
                }
            }
        })
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-menu)', function(data){
                table.reload('menu-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        })
        function openEditWindow() {
            layer.open({
                type: 1,
                content: $('#doc-edit-layer').html(),
                title: '菜单信息'
                , area: ['600px', '400px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        return false;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/menu.html?act=edit',
                            data: $("#doc-edit-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('menu-table', {});
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
            var menuname=$("#menuname").val();
            var menuState=$("input[name='menuState']:checked").val();
            if(menuname.length<2){
                layer.msg('菜单名称至少两个字符',{icon:5});
                return "false";
            }
            if(!menuState){
                layer.msg('请选择菜单状态',{icon:5});
                return "false";
            }
        }
        function delMenus(id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/menu.html?act=del',
                data: "id="+id,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('menu-table', {});
                        layer.msg('删除成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }
        function upMenu(id,menuState) {
            $.ajax({
                url: '${pageContext.request.contextPath}/menu.html?act=upState',
                data: "id="+id+"&menuState="+menuState,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('menu-table', {});
                        layer.msg('操作成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }


    })

</script>
