<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<div style="padding-left: 15px;padding-right: 15px;">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>配件管理</legend>
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
            <button class="layui-btn layui-btn-sm" lay-event="add"><i class="layui-icon">&#xe654;</i>上架配件</button>
            <div class="layui-btn layui-btn-sm layui-btn-danger" lay-event="delete"><i class="layui-icon">&#xe640;</i>批量下架</div>
        </div>
    </script>
    <script type="text/html" id="doc-doc-bar">
        <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="info-car">查看</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit-car">修改</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del-car">下架</a>
    </script>
    <table class="layui-table" lay-filter="doc-table" id="doc-table">
    </table>
    <script type="text/html" id="doc-edit-layer">
        <form id="doc-edit-form" style="width: 80%;padding-top: 15px;" class="layui-form" lay-filter="doc-edit-form">
            <input type="hidden" name="id">
            <div class="layui-form-item">
                <label class="layui-form-label">配件名称</label>
                <div class="layui-input-inline">
                    <input type="text" name="partname" id="partname" autocomplete="off" placeholder="请输入配件名称" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">配件名称至少两个字</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">配件分类</label>
                <div class="layui-input-inline">
                    <select name="typeEdit" id="typeEdit" lay-search>
                        <option value="">请选择分类</option>
                        <c:forEach items="${typeList}" var="type">
                            <option value="${type.id}">${type.typename}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="typeInit" id="typeInit">
                </div>
                <div class="layui-form-mid layui-word-aux">分类不能为空</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">卖家</label>
                <div class="layui-input-inline">
                    <select name="dealerEdit" id="dealerEdit" lay-search ${sessionScope.userLogin.roleId==2?'disabled':''}>
                        <option value="">请选择卖家</option>
                        <c:forEach items="${dealerList}" var="dealer">
                            <option value="${dealer.id}" ${sessionScope.userLogin.id==dealer.id?"selected":""}>${dealer.nickname}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="dealerInit" id="dealerInit">
                </div>
                <div class="layui-form-mid layui-word-aux">卖家不能为空</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">价格</label>
                <div class="layui-input-inline">
                    <input type="text" name="price" id="price" class="layui-input" placeholder="请输入价格">
                </div>
                <div class="layui-form-mid layui-word-aux">价格最多两位小数</div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">库存</label>
                <div class="layui-input-inline">
                    <input type="text" name="stock" id="stock" autocomplete="off" placeholder="请输入库存" class="layui-input">
                </div>
                <div class="layui-form-mid layui-word-aux">库存必须为正整数</div>
            </div>
        </form>
    </script>
    <script type="text/html" id="doc-view-layer">
        <form id="doc-view-form" style="width: 80%;padding-top: 15px;" class="layui-form" lay-filter="doc-view-form">
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

        //头工具栏事件
        table.on('toolbar(doc-table)', function (obj) {
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
                        layer.confirm('确定下架吗？',function(index){
                           delParts(data.map(function(item){return item.id;}));
                        });
                    }
                    break;
            };
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
                case 'edit-car': {
                    layui.use('form', function () {
                        openEditWindow();
                        var form = layui.form;
                        form.val("doc-edit-form", {
                            "id":data.id
                            , "partname": data.partname
                            , "typeInit": data.type.typename
                            , "dealerInit": data.user.nickname
                            , "price": data.price
                            , "stock":data.stock
                        });

                        var typeInit=$("#typeInit").val();
                        var dealerInit=$("#dealerInit").val();
                        // 遍历select
                        $("[name='typeEdit']").each(function() {
                            // this代表的是<option></option>，对option再进行遍历
                            $(this).children("option").each(function() {
                                // 判断需要对那个选项进行回显
                                if (this.text == typeInit) {
                                    // 进行回显
                                    $(this).prop("selected", "selected");
                                }
                            });
                        });
                        $("[name='dealerEdit']").each(function() {
                            // this代表的是<option></option>，对option再进行遍历
                            $(this).children("option").each(function() {
                                // 判断需要对那个选项进行回显
                                if (this.text == dealerInit) {
                                    // 进行回显
                                    $(this).prop("selected", "selected");
                                }
                            });
                        });
                        form.render('select');

                    })
                    break;
                }
                case 'del-car':{
                    layer.confirm('确定下架吗？',function(index){
                        delParts(data.id);
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
        function openViewWindow() {
            layer.open({
                type: 1,
                content: $('#doc-view-layer').html(),
                title: '配件详情'
                , area: ['600px', '400px'],
                btn: ['确定', '取消'] //可以无限个按钮
            });
        }
        
        function verify(){
            var partname=$("#partname").val();
            var typeEdit=$("#typeEdit").val();
            var dealerEdit=$("#dealerEdit").val();
            var price=$("#price").val();
            var stock=$("#stock").val();
            var regStock=new RegExp("^\\+?[1-9][0-9]*$");
            var regPrice=new RegExp("^(([1-9][0-9]*)|(([0]\\.\\d{1,2}|[1-9][0-9]*\\.\\d{1,2})))$");
            if(partname.length<2){
                layer.msg('配件名称至少两个字符',{icon:5});
                return "false";
            }
            if(!typeEdit){
                layer.msg('分类不能为空',{icon:5});
                return "false";
            }
            if(!dealerEdit){
                layer.msg('卖家不能为空',{icon:5});
                return "false";
            }
            if(!(regPrice.test(price))){
                layer.msg('价格必须大于零且最多两位小数',{icon:5});
                return "false";
            }
            if(!(regStock.test(stock))){
                layer.msg('库存必须为正整数',{icon:5});
                return "false";
            }
        }
        function delParts(ids) {
            $.ajax({
                url: '${pageContext.request.contextPath}/car.html?act=del',
                data: "ids="+ids,
                method: 'post',
                success: function (result) {
                    if (result.status) {
                        table.reload('doc-table', {});
                        layer.msg('下架成功');
                    } else {
                        layer.alert(result.message)
                    }
                }
            });
        }

    })

</script>
