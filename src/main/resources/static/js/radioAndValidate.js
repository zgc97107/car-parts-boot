/**
 * 点击父菜单，自动选中所有子菜单
 */
function checkeall(num){
    var menu1=document.getElementById("menu"+num);
    var ul=document.getElementsByClassName("menu"+num);
    $(ul).each(function () {
        var li = $(ul).children();
        if($(menu1).is(":checked")){
            $(li.children()).attr("checked",true);
        } else{
            $(li.children()).attr("checked",false);
        }
    });
}

$(function () {
    /**
     * 点击某个子菜单，自动选中相应的父菜单
     */
    $(".checkparent").change(function () {
        var cp=$(this).parent().parent().prev();
        if($(this).is(':checked')){
            $(cp).attr("checked",true);
        }
    });
})

/**
 * 表单验证，角色不能为空，菜单至少选一个(和全选方法思路一🐏)
 */
function rolevalidate() {
    var rolename=$("[name='rolename']").val();
    var menuids=$("[name='menuid']").val();
    if(rolename==false){
        $("[name='rolename']").next().html("请输入角色名");
        return false;
    }
    return true;
}