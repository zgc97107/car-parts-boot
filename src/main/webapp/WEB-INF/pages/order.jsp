<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>订单管理</legend>
    </fieldset>
</div>
<form action="#" id="doc-search-form" class="layui-form" method="post">
    <div class="layui-form-item">
        <div class="layui-inline">
            <label class="layui-form-label">查询条件：</label>
            <div class="layui-input-inline">
                <div lay-filter="doc-type" class="layui-input-inline">
                    <input type="text" name="id" placeholder="订单号" class="layui-input" >
                </div>
            </div>
        </div>
        <button class="layui-btn" lay-submit lay-filter="search-doc">搜索</button>
    </div>
</form>
<script type="text/html" id="doc-head-bar">
</script>
<script type="text/html" id="doc-doc-bar">
</script>
<table class="layui-table" lay-filter="doc-table" id="doc-table">
</table>
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
            , url: '<%=request.getContextPath()%>/order.html?act=ordersPage' //数据接口
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
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'id',title:'订单号', align:'center'}
                ,{
                    field: 'customer', title: '买家', align:'center', templet: function (data) {
                    return (data.customer.nickname)
                    }
                }
                ,{
                    field: 'orderState', title: '状态', align:'center', templet: function (data) {
                        return (data.orderState==0?'未发货':data.orderState==1?'已发货':'已完成')
                    }
                }
                ,{
                    fixed: 'right',title: '操作', width: 180, align:'center', templet: function (data) {
                        return (data.orderState == 0) ? '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="info-doc">查看</a><a class="layui-btn layui-btn layui-btn-warm layui-btn-xs" lay-event="send-doc">发货</a>'
                            : '<a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="info-doc">查看</a>'
                    }
                }
            ]]
        });

        //监听行工具事件
        table.on('tool(doc-table)', function (obj) {
            var data = obj.data;
            switch (obj.event) {
                case 'info-doc': {
                    layer.open({
                        type:1
                        ,area: ['800px', '500px']
                        ,content:$('#view-div').html()
                        ,title: '订单记录'
                        ,success:function (layero) {
                            table.render({
                                id:"demo"
                                , toolbar: '#doc-head-bar'
                                , elem: '#demo'
                                , cellMinWidth: 80
                                , url: '<%=request.getContextPath()%>/order.html?act=viewOrder&id='+data.id //数据接口
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
                                    {type: 'checkbox', fixed: 'left'}
                                    ,{
                                        field: 'partname', title: '配件名称',align:'center',width:220, templet: function (data) {
                                            return (data.parts.partname)
                                        }
                                    }
                                    ,{field: 'num',title:'数量',align:'center'}
                                    ,{field: 'allPrice',title:'总价',align:'center',fixed: 'right',width:220}
                                ]]
                            });
                        }
                    });
                    break;
                }
                case 'send-doc':{
                    layer.confirm('确定发货吗？',function(index){
                        send(data.id);
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
        function send(id) {
            $.ajax({
                url: '${pageContext.request.contextPath}/order.html?act=send',
                data: "id="+id,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('doc-table', {});
                        layer.msg('发货成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }
    })
</script>
