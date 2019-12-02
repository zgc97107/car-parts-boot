<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<script>
    //Demo
    layui.use('form', function(){
        var layer=layui.layer;
        var form = layui.form;
    });
    function validate() {
        var layer=layui.layer;
        var msg;
        var nickname=$("[name='nickname']").val();
        if(nickname.length<2){
            layer.msg('昵称至少两个字符',{icon:5});
            return false;
        }else{
            $.ajax({
                url: '${pageContext.request.contextPath}/validateName',
                async:false,
                data: "nickname="+nickname+"&type=myInfo",
                method: 'post',
                success: function (map) {
                    if (map.result) {
                        msg = map.result;
                    } else {
                        msg = map.result;
                    }
                }
            });
            if(msg===true){
                $.ajax({
                    //几个参数需要注意一下
                    type: "post",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    async:false,
                    url: "${pageContext.request.contextPath}/user.html?act=editInfo" ,//url
                    data: $('#infoForm').serialize(),
                    success: function (rs) {
                        if (rs.code === 1) {
                            layer.msg("修改成功",{icon:1});
                            setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/index.html';
                            },1000)
                        }else{
                            layer.msg("修改失败",{icon:2});
                            setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/index.html';
                            },1000)
                        }
                    }
                });
            }else{
                layer.msg("昵称已存在，换一个试试吧",{icon:5});
                return false;
            }
        }

    }
</script>

<div style="padding: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>修改个人资料</legend>
    </fieldset>
</div>
<div id="edit-form">
    <form class="layui-form" id="infoForm" action="#" onsubmit="return false" method="post">
        <input type="hidden" name="id" value="${sessionScope.userLogin.id}">
        <div class="layui-form-item">
            <label class="layui-form-label">角色</label>
            <div class="layui-input-inline">
                <input type="text" name="role" required disabled lay-verify="required" value="${sessionScope.userLogin.role.rolename}" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">昵称</label>
            <div class="layui-input-inline">
                <input type="text" name="nickname" value="${sessionScope.userLogin.nickname}"  lay-verify="required|nickname" placeholder="请输入用户名" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <butto class="layui-btn" onClick="validate();" lay-filter="formDemo">立即提交</butto>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>

