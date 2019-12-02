<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    //Demo
    layui.use('form', function(){
        var layer=layui.layer;
        var form = layui.form;
    });
    function editPass() {
        var layer=layui.layer;
        var msg;
        var oldpass=$("[name='oldpass']").val();
        var newpass=$("[name='newpass']").val();
        var repass=$("[name='repass']").val();
        if(newpass.length < 2){
            layer.msg('新密码至少2个字符',{icon:5});
            return false;
        }
        if(repass!=newpass){
            layer.msg('确认密码要与新密码一致',{icon:5});
            return false;
        }
        $.ajax({
            url:"${pageContext.request.contextPath}/validatePassword",
            async:false,
            data:"oldpass="+oldpass+"&id="+${sessionScope.userLogin.id},
            type:"post",
            dataType:"json",
            success:function (rs) {
                if(rs.result){
                    msg=rs.result;
                }else{
                    msg=rs.result;
                }
            }
        });
        if(msg!=true){
            layer.msg("原密码不正确",{icon:5});
            return false;
        }else{
            $.ajax({
                //几个参数需要注意一下
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                async:false,
                url: "${pageContext.request.contextPath}/user.html?act=editPass" ,//url
                data: $('#passForm').serialize(),
                success: function (rs) {
                    if (rs.code === 1) {
                        layer.msg("修改成功，请重新登录",{icon:1});
                        setTimeout(function(){
                            window.top.location=window.location.protocol + '//' + window.location.host +'/login.html';
                        },1000)
                    }else{
                        layer.msg("修改失败",{icon:2});
                        setTimeout(function(){
                            window.top.location=window.location.protocol + '//' + window.location.host +'/index.html';
                        },1000)
                    }
                }
            });
        }

    }
</script>

<div style="padding: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>修改密码</legend>
    </fieldset>
    <form id="passForm" class="layui-form" action="#" method="post" onsubmit="return false">
        <input type="hidden" name="id" value="${sessionScope.userLogin.id}">
        <div class="layui-form-item">
            <label class="layui-form-label">原密码</label>
            <div class="layui-input-inline">
                <input type="password" name="oldpass" required lay-verify="required|oldpass" placeholder="请输入原密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">请输入正确的原密码</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">新密码</label>
            <div class="layui-input-inline">
                <input type="password" name="newpass" required lay-verify="required|newpass" placeholder="请输入新密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">新密码至少两个字符</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">确认密码</label>
            <div class="layui-input-inline">
                <input type="password" name="repass" required lay-verify="required|repass" placeholder="请输入确认密码" autocomplete="off" class="layui-input">
            </div>
            <div class="layui-form-mid layui-word-aux">确认密码要与新密码一致</div>
        </div>
        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" onclick="editPass();" lay-submit lay-filter="formDemo">立即提交</button>
                <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            </div>
        </div>
    </form>
</div>



