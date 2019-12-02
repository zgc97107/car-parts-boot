<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>购物车</legend>
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
            <select name="dealerId" lay-verify="" lay-search >
                <option value="">请选择卖家</option>
                <c:forEach items="${dealers}" var="dealer">
                    <option value="${dealer.id}">${dealer.nickname}</option>
                </c:forEach>
            </select>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-doc">搜索</button>
    </div>
</form>
<script type="text/html" id="doc-head-bar">
    <div class="layui-btn-container">
        <button class="layui-btn layui-btn-sm layui-btn-warm" lay-event="account"><i class="layui-icon">&#xe657;</i>合计结算</button>
        <div class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon">&#xe640;</i>批量删除</div>
    </div>
</script>
<script type="text/html" id="doc-doc-bar">
    <a class="layui-btn layui-btn-xs" lay-event="edit-mark">修改</a>
    <a class="layui-btn layui-btn layui-btn-warm layui-btn-xs" lay-event="account-mark">结算</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-mark">删除</a>
</script>
<table class="layui-table" lay-filter="doc-table" id="doc-table">
</table>
<script type="text/html" id="doc-mark-layer">
    <form id="doc-mark-form" style="padding: 15px;" class="layui-form" lay-filter="doc-mark-form">
        <input type="hidden" name="id">
        <input type="hidden" name="stock" id="stock">
        <input type="text" name="num" id="num" class="layui-input">
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
            , url: '${pageContext.request.contextPath}/shop.html?act=marksPage' //数据接口
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
                ,{
                    field: 'partname', title: '配件名称',  templet: function (data) {
                        return (data.parts.partname)
                    }
                }
                , {field: 'num',title:'购买数量', sort: true, align:'center'}
                , {
                    field: 'stock', title: '库存', templet: function (data) {
                    return (data.parts.stock)
                    }
                }
                , {
                    field: 'dealer', title: '卖家', templet: function (data) {
                        return (data.parts.user.nickname)
                    }
                }
                , {fixed: 'right', title: '操作', toolbar: '#doc-doc-bar', width: 180, align:'center'}
            ]]
        });

        //头工具栏事件
        table.on('toolbar(doc-table)', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch (obj.event) {
                case 'account':
                    if(data.length === 0){
                        layer.msg('至少选择一行');
                    } else {
                        layer.confirm('确定结算吗？',function(index){
                            accountMarks(data.map(function(item){return item.id;})
                                        ,data.map(function(item){return item.id+"-"+item.num;}));
                        });
                    }
                    break;
                case 'delete':
                    if(data.length === 0){
                        layer.msg('至少选择一行');
                    } else {
                        layer.confirm('确定从购物车删除吗？',function(index){
                            delMarks(data.map(function(item){return item.id;}));
                        });
                    }
                    break;
            };
        });
        //监听行工具事件
        table.on('tool(doc-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'edit-mark':{
                    layui.use('form', function () {
                        openMarkWindow();
                        var form = layui.form;
                        form.val("doc-mark-form", {
                            "id":data.id
                            , "stock": data.parts.stock
                        });
                        form.render();
                    })
                    break;
                }
                case 'account-mark':{
                    layer.confirm('确定结算吗？',function(index){
                        accountMarks(data.id,data.id+"-"+data.num);
                    });
                    break;
                }
                case 'del-mark':{
                    layer.confirm('确定从购物车删除吗？',function(index){
                        delMarks(data.id);
                    });
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
        function openEditWindow() {
            layer.open({
                type: 1,
                content: $('#doc-edit-layer').html(),
                title: '配件信息'
                , area: ['600px', '400px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        return;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/car.html?act=edit',
                            data: $("#doc-edit-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('doc-table', {});
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

        function openMarkWindow() {
            layer.open({
                type: 1,
                content: $('#doc-mark-layer').html(),
                title: '修改要购买的商品数量'
                , area: ['282px', '168px'],
                btn: ['提交', '取消'] //可以无限个按钮
                , yes: function (index) {
                    if(verify()==="false"){
                        layer.close(index);
                        return;
                    }else{
                        $.ajax({
                            url: '${pageContext.request.contextPath}/shop.html?act=edit',
                            async:false,
                            data: $("#doc-mark-form").serialize(),
                            method: 'post',
                            success: function (result) {
                                if (result.status) {
                                    table.reload('doc-table', {});
                                    layer.msg('修改成功');
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

        function delMarks(ids) {
            $.ajax({
                url: '${pageContext.request.contextPath}/shop.html?act=del',
                data: "ids="+ids,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('doc-table', {});
                        layer.msg('删除成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }

        function accountMarks(ids,idnums) {
            $.ajax({
                url: '${pageContext.request.contextPath}/shop.html?act=account',
                data: "ids="+ids+"&idnums="+idnums,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('doc-table', {});
                        layer.msg('结算成功，请去订单页面查看吧');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }

    })

</script>
