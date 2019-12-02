<%@ page pageEncoding="UTF-8" contentType="text/html; UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>登录/注册</title>
    <link rel="icon" href="/layui/images/logo.png" type="image/x-icon" />
    <link rel="shortcut icon" href="/layui/images/logo.png" type="image/x-icon" />
    <link href='https://fonts.googleapis.com/css?family=Titillium+Web:400,300,600' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="/css/normalize.min.css">
    <link rel="stylesheet" href="/css/style.css">
    <script type="text/javascript" src="/layui/layui.js"></script>
    <script>
        layui.use('form', function(){
            var layer=layui.layer;
            var form = layui.form;
         });
        function regiUser(){
            var password=$('#newpass').val();
            var repass=$('input[name=repass]').val();
            var roleId=$('select[name=roleId]').val();
            var nickname=$('#reginame').val();
            var msg;
            if(!roleId){
                layer.msg('必须选择角色',{icon:5});
                return false;
            }
            if(nickname.length<2){
                layer.msg('昵称至少两个字符',{icon:5});
                return false;
            }else{
                $.ajax({
                    type: "post",//方法类型
                    dataType: "json",//预期服务器返回的数据类型
                    async:false,
                    url: '${pageContext.request.contextPath}/validateName',
                    data: "nickname="+nickname+"&type=register",
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
                    return false;
                }
            }
            if(password.length<2){
                layer.msg("密码至少两个字符",{icon:5});
                return false;
            }
            if(repass!=password){
                layer.msg("两次输入密码不一致",{icon:5});
                return false;
            }
            $.ajax({
                //几个参数需要注意一下
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                async:false,
                url: "${pageContext.request.contextPath}/user.html/regiUser" ,//url
                data: $('#regiForm').serialize(),
                success: function (rs) {
                    if (rs.code === 1) {
                        layer.msg("注册成功，请登录",{icon:1});
                        setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/login.html';
                        },1000)
                    }else{
                        layer.msg("注册失败，请重新注册",{icon:2});
                        setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/login.html';
                        },3000)
                    }
                }
            });
        }

        function loginUser() {
            //act=loginUser
            var layer=layui.layer;
            $.ajax({
                //几个参数需要注意一下
                type: "post",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                async:false,
                url: "${pageContext.request.contextPath}/user.html/loginUser" ,//url
                data: $('#loginForm').serialize(),
                success: function (rs) {
                    if (rs.code === 200) {
                        layer.msg("登录成功",{icon:1});
                        setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/index.html';
                        },1000)
                    }else{
                        layer.msg("用户名或密码错误，请重新登录",{icon:2});
                        /*setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/login.html';
                        },3000)*/
                    }
                }
            });

        }

    </script>

</head>

<body>
    <div class="form">
        <ul class="tab-group">
            <li class="tab active"><a href="#login">登录</a></li>
            <li class="tab"><a href="#signup">注册</a></li>
        </ul>
        <div class="tab-content">
            <div id="login">
                <h1>Welcome Back!</h1>
                <form id="loginForm" action="#" onsubmit="return false" method="post">
                    <div class="field-wrap">
                        <label>
                            用户名<span class="req">*</span>
                        </label>
                        <input type="text" name="nickname" required />
                    </div>
                    <div class="field-wrap">
                        <label>
                            密码<span class="req">*</span>
                        </label>
                        <input type="password" name="password" required autocomplete="off"/>
                    </div>
                    <%--<p class="forgot"><a href="#">忘记密码？</a></p>--%>
                    <button class="button button-block" onclick="loginUser();"/>登录</button>
                </form>
            </div>

            <div id="signup">
                <h1>Sign Up For Free!</h1>
                <form id="regiForm" action="#" onsubmit="return false" method="post">
                    <div class="field-wrap">
                        <select name="roleId" class="selectCss">
                            <option value="">---请选择角色---</option>
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">${role.rolename}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="field-wrap">
                        <label>
                            昵称<span class="req">*</span>
                        </label>
                        <input type="text" name="nickname" id="reginame" required autocomplete="off" />
                    </div>
                    <div class="field-wrap">
                        <label>
                            密码<span class="req">*</span>
                        </label>
                        <input type="password" name="password" id="newpass" required autocomplete="off"/>
                    </div>
                    <div class="field-wrap">
                        <label>
                            确认密码<span class="req">*</span>
                        </label>
                        <input type="password" name="repass" required autocomplete="off"/>
                    </div>
                    <button type="submit" onclick="regiUser();" class="button button-block"/>注册</button>
                </form>
            </div>

        </div><!-- tab-content -->

    </div> <!-- /form -->

    <script type="text/javascript" src='/js/jquery-2.0.0.min.js'></script>
    <script type="text/javascript" src="/js/login.js"></script>


</body>

</html>
