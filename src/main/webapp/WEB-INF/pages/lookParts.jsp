<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>查看配件</legend>
    </fieldset>
</div>
    <form action="#" id="doc-search-form" class="layui-form" method="post">
        <div class="layui-form-item">
            <div class="layui-inline">
                <label class="layui-form-label">查询条件：</label>
                <div class="layui-input-inline">
                    <div lay-filter="doc-type" class="layui-input-inline">
                        <input type="text" name="partname" placeholder="配件名称" class="layui-input" >
                    </div>
                </div>
            </div>
            <div class="layui-inline">
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="minPrice" placeholder="最低价￥" autocomplete="off" class="layui-input">
                </div>
                <div class="layui-form-mid">-</div>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="text" name="maxPrice" placeholder="最高价￥" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <select name="typeid" lay-verify="" lay-search>
                    <option value="">请选择分类</option>
                    <c:forEach items="${typeList}" var="type">
                        <option value="${type.id}">${type.typename}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="layui-inline">
                <select name="dealerid" lay-verify="" lay-search ${sessionScope.userLogin.roleId==2?'disabled':''}>
                    <option value="">请选择卖家</option>
                    <c:forEach items="${dealerList}" var="dealer">
                        <option value="${dealer.id}">${dealer.nickname}</option>
                    </c:forEach>
                </select>
            </div>
                <button class="layui-btn" lay-submit lay-filter="search-doc">搜索</button>
            </div>
    </form>
    <script type="text/html" id="doc-head-bar">
        <div class="layui-btn-container">
        </div>
    </script>
    <script type="text/html" id="doc-doc-bar">
        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="info-car">查看</a>
        <a class="layui-btn layui-btn layui-btn-warm layui-btn-xs" lay-event="mark-car">加购</a>
    </script>
    <table class="layui-table" lay-filter="doc-table" id="doc-table">
    </table>
    <script type="text/html" id="doc-mark-layer">
        <form id="doc-mark-form" style="padding: 15px;" class="layui-form" lay-filter="doc-mark-form">
                <input type="hidden" name="userId" id="userId" value="${sessionScope.userLogin.id}">
                <input type="hidden" name="partId" id="partId">
                <input type="hidden" name="stock" id="stock">
                <input type="text" name="num" id="num" class="layui-input">
        </form>
    </script>
    <script type="text/html" id="doc-view-layer">
        <form id="doc-view-form" style="padding: 15px;" class="layui-form" lay-filter="doc-view-form">
            <input type="hidden" name="id">
            <div class="layui-form-item">
                <label class="layui-form-label">配件名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="partname" disabled class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">配件分类</label>
                <div class="layui-input-inline">
                    <input type="text" name="typeInit" disabled class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">卖家</label>
                <div class="layui-input-inline">
                    <input type="text" name="dealerInit" disabled class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">价格</label>
                <div class="layui-input-inline">
                    <input type="text" name="price" disabled class="layui-input" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">库存</label>
                <div class="layui-input-inline">
                    <input type="text" name="stock" disabled class="layui-input">
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
            id:"doc-table",
            elem: '#doc-table'
            , toolbar: '#doc-head-bar'
            , cellMinWidth: 80
            , url: '${pageContext.request.contextPath}/car.html?act=partsPage' //数据接口
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
                ,{field: 'partname',title:'配件', align:'center'}
                ,{field: 'price',title:'价格', sort: true, align:'center'}
                , {field: 'stock', title: '库存', sort: true, align:'center'}
                , {field: 'sales', title: '销量', sort: true, align:'center'}
                ,{
                    field: 'type', title: '分类',  templet: function (data) {
                        return (data.type.typename)
                    }
                }
                , {
                    field: 'dealer', title: '卖家', templet: function (data) {
                        return (data.user.nickname)
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#doc-doc-bar', width: 180, align:'center'}
            ]]
        });

        //监听行工具事件
        table.on('tool(doc-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'info-car': {
                    layui.use('form', function () {
                        openViewWindow();
                        var form = layui.form;
                        form.val("doc-view-form", {
                            "id":data.id
                            , "partname": data.partname
                            , "typeInit": data.type.typename
                            , "dealerInit": data.user.nickname
                            , "price": data.price
                            , "stock":data.stock
                        });
                    })
                    break;
                }
                case 'mark-car':{
                    layui.use('form', function () {
                        openMarkWindow();
                        var form = layui.form;
                        form.val("doc-mark-form", {
                            "partId":data.id
                            , "stock": data.stock
                        });
                        form.render();
                    })
                    break;
                }
            }
        })
        layui.use('form',function(){
            var form = layui.form;
            form.render();
            form.on('submit(search-doc)', function(data){
                table.reload('doc-table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    },
                    where: data.field
                });
                return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
            });
        })

        function openViewWindow() {
            layer.open({
                type: 1,
                content: $('#doc-view-layer').html(),
                title: '配件详情'
                , area: ['600px', '400px'],
                btn: ['确定', '取消'] //可以无限个按钮
            });
        }
        
        function openMarkWindow() {
            layer.open({
                type: 1,
                content: $('#doc-mark-layer').html(),
                title: '请输入放入购物车的数量'
                , area: ['282px', '168px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        layer.close(index);
                        return;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/shop.html?act=markParts',
                            async:false,
                            data: $("#doc-mark-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('doc-table', {});
                                    layer.msg('添加成功，请去购物车查看吧');
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

        function verify() {
            var num=$("#num").val();
            var stock=$("#stock").val();
            var regNum=new RegExp("^\\+?[1-9][0-9]*$");
            if(!(regNum.test(num))){
                layer.msg('必须输入正整数',{icon:5});
                return "false";
            }
            if(parseInt(num)>parseInt(stock)){
                layer.msg('不能超过库存数',{icon:5});
                return "false";
            }

        }

    })

</script>
