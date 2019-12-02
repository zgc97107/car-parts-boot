<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>汽车配件销售系统</title>
    <link rel="icon" href="/layui/images/logo.png" type="image/x-icon" />
    <link rel="shortcut icon" href="/layui/images/logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script type="text/javascript" src="/js/jquery-2.0.0.min.js"></script>
    <script type="text/javascript" src="/layui/layui.js"></script>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/js/radioAndValidate.js"></script>--%>

    <script type="text/javascript">
        layui.use(['element','carousel'], function () {
            var element = layui.element;
            var carousel = layui.carousel;
            var layer = layui.layer;
            //建造实例
            carousel.render({
                elem: '#carouselTest'
                , width: '1150px' //设置容器宽度
                , height: '520px' //设置容器高度
                , arrow: 'always' //始终显示箭头
                , interval: 2000
                //,anim: 'updown' //切换动画方式
            });
        });

        layui.use(['layer', 'laypage', 'laydate'], function(){
            var layer = layui.layer;//获得layer模块
        });
        function loginout() {
            //询问框
            layer.confirm('确定要退出吗？', {
                title: '退出提示',
                btn: ['确定','取消'] //按钮
            }, function(){
                $.ajax({
                    async:false,
                    url: "${pageContext.request.contextPath}/loginout" ,//url
                    success: function (rs) {
                        if (rs.code === 1) {
                            layer.msg("退出成功",{icon:1});
                            setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/login.html';
                            },1000)
                        }else{
                            layer.msg("退出失败",{icon:2});
                            setTimeout(function(){
                                window.top.location=window.location.protocol + '//' + window.location.host +'/index.html';
                            },1000)
                        }
                    }
                });
            });
        }
    </script>
</head>

<body class="layui-layout-body">
    <div class="layui-layout layui-layout-admin">
        <div class="layui-header">
            <div class="layui-logo" style="font-size: 20px;cursor: pointer" onclick="{window.location.href='index.html'}"><b>汽车配件销售管理</b></div>
            <!-- 头部区域（可配合layui已有的水平导航） -->
            <ul class="layui-nav layui-layout-left">
                <%-- <li class="layui-nav-item"><a href="">控制台</a></li>--%>
            </ul>
            <ul class="layui-nav layui-layout-right">
                <li class="layui-nav-item">
                    <a href="javascript:;">
                        <span style="color: #FFB800;font-size: 15px;font-weight: bold;">${sessionScope.userLogin.role.rolename}</span>
                        &nbsp;&nbsp;
                        ${sessionScope.userLogin.nickname}
                    </a>
                </li>
                <li class="layui-nav-item"><a style="cursor: pointer" onclick="loginout();">退出</a></li>
            </ul>
        </div>

        <div class="layui-side layui-bg-black">
            <div class="layui-side-scroll">
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree" lay-filter="test">
                    <c:forEach items="${sessionScope.userLogin.role.menuList}" var="menu1">
                        <li class="layui-nav-item layui-nav-itemed">
                            <a class="javascript:;" href="javascript:;">${menu1.menuname}</a>
                            <dl class="layui-nav-child">
                                <c:forEach items="${menu1.seconds}" var="menu2">
                                    <dd><a href="javascript:void(0)" onclick="$('#main-content').load('${pageContext.request.contextPath}/${menu2.menupath}')">${menu2.menuname}</a></dd>
                                </c:forEach>
                            </dl>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <div class="layui-body">
            <div id="main-content">
                <div class="layui-carousel" id="carouselTest">
                    <div carousel-item>
                        <div><img src="/layui/images/CarouselMap1.jpg"></div>
                        <div><img src="/layui/images/CarouselMap2.jpg"></div>
                    </div>
                </div>
                <!-- 条目中可以是任意内容，如：<img src=""> -->
            </div>
            <div class="layui-footer">
                <!-- 底部固定区域 -->
                © 2019 yym.com - 仅供学术交流，严禁商业用途
            </div>
        </div>
    </div>
</body>
</html>
