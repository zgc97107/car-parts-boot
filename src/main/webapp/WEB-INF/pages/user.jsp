<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>用户管理</legend>
    </fieldset>
</div>
<form action="#" id="doc-search-form" class="layui-form" method="post">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">查询条件：</label>
            <div class="layui-input-inline">
                <div lay-filter="doc-type" class="layui-input-inline">
                    <input type="text" name="nickname" placeholder="昵称" class="layui-input" >
                </div>
            </div>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-user">搜索</button>
    </div>
</form>

<script type="text/html" id="user-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon">&#xe654;</i>新增</button>
        <div class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon">&#xe640;</i>批量删除</div>
    </div>
</script>
<script type="text/html" id="doc-doc-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-user">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-user">删除</a>
</script>
<table class="layui-table" lay-filter="user-table" id="user-table">
</table>
<script type="text/html" id="doc-edit-layer">
    <form id="doc-edit-form" style="width: 80%;padding-top: 15px;" class="layui-form" lay-filter="doc-edit-form">
        <input type="hidden" name="id">
        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-inline">
                <select name="roleEdit" id="roleEdit" lay-search>
                    <option value="">请选择角色</option>
                    <c:forEach items="${roleList}" var="role">
                        <option value="${role.id}">${role.rolename}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="roleInit" id="roleInit">
            </div>
            <div class="layui-form-mid layui-word-aux">角色不能为空</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">昵称</label>
            <input type="hidden" name="userNick" id="userNick">
            <div class="layui-input-inline">
                <input type="text" name="nickname" id="nickname" required lay-verify="required|nickname" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">昵称至少两个字符</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">密码</label>
            <div class="layui-input-inline">
                <input type="password" name="password" id="password" required lay-verify="required|oldpass" placeholder="请输入密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">密码至少两个字符</div>
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
            id:"user-table",
            elem: '#user-table'
            , toolbar: '#user-head-bar'
            , cellMinWidth: 80
            , url: '${pageContext.request.contextPath}/user.html?act=userPages' //数据接口
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
                ,{field: 'nickname',title:'昵称',align:'center'}
                ,{
                    field: 'role', title: '角色', align:'center', templet: function (data) {
                        return (data.role.rolename)
                    }
                }
                ,{field: 'password',title:'密码',align:'center', templet: function (data) {
                        return data.password.length>7?plusXing(data.password,3,4):plusXing(data.password,1,1);
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#doc-doc-bar', width: 220, align:'center'}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(user-table)', function (obj) {
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
                            delParts(data.map(function(item){return item.id;}));
                        });
                    }
                    break;
            };
        });
        //监听行工具事件
        table.on('tool(user-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'edit-user': {
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.val("doc-edit-form", {
                            "id":data.id
                            , "nickname": data.nickname
                            , "roleInit": data.role.rolename
                            , "password": data.password
                        });
                        var userNick=data.nickname;
                        $("#userNick").val(userNick);
                        var roleInit=$("#roleInit").val();
                        $("[name='roleEdit']").each(function() {
                            // this代表的是<option></option>，对option再进行遍历
                            $(this).children("option").each(function() {
                                // 判断需要对那个选项进行回显
                                if (this.text == roleInit) {
                                    // 进行回显
                                    $(this).prop("selected", "selected");
                                }
                            });
                        });
                        form.render('select');

                    })
                    break;
                }
                case 'del-user':{
                    layer.confirm('确定删除吗？',function(index){
                        delParts(data.id);
                    });
                    break;
                }
            }
        })
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-user)', function(data){
                table.reload('user-table', {
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
                title: '用户信息'
                , area: ['600px', '400px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        return;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/user.html?act=edit',
                            data: $("#doc-edit-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('user-table', {});
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
        
        function plusXing(str,frontLen,endLen) {
            var len = str.length - frontLen - endLen;
            var xing = '***';
            if(str == '' || typeof str == 'undefined' || str == null){
                return '';
            }
            return str.substring(0, frontLen) + xing + str.substring(str.length - endLen);
        }

        function verify(){
            var nickname=$("#nickname").val();
            var userNick=$("#userNick").val();
            var roleEdit=$("#roleEdit").val();
            var password=$("#password").val();
            var msg;
            if(!roleEdit){
                layer.msg('角色不能为空',{icon:5});
                return "false";
            }
            if(nickname.length<2){
                layer.msg('用户名称至少两个字符',{icon:5});
                return "false";
            }else{
                $.ajax({
                    type: "post",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    async:false,
                    url: '${pageContext.request.contextPath}/validateName',
                    data: "nickname="+nickname+"&userNick="+userNick+"&type=power",
                    success: function (rs) {
                        if (rs.result) {
                            msg = rs.result;
                        } else {
                            msg = rs.result;
                        }
                    }
                });
                if(msg!=true){
                    layer.msg("昵称已存在，换一个试试吧",{icon:5});
                    return "false";
                }
            }
            if(password.length<2){
                layer.msg('密码至少两个字符',{icon:5});
                return "false";
            }
        }
        function delParts(ids) {
            $.ajax({
                url: '${pageContext.request.contextPath}/user.html?act=del',
                data: "ids="+ids,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('user-table', {});
                        layer.msg('删除成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }

    })

</script>
