package com.car.common.utils;

import com.car.pojo.Menu;

import java.util.ArrayList;
import java.util.List;

public class NewmenuUtil {
    /**
     * 分一二级菜单
     */
    public static List<Menu> newmenuList(List<Menu> list) {
        //分一二级菜单
        List<Menu> newmenulist=new ArrayList<>();
        for (Menu menu : list) {
            if(menu.getUpmenuid()==-1){
                for (Menu menu1 : list) {
                    if(menu1.getUpmenuid()==menu.getId()){
                        menu.getSeconds().add(menu1);
                    }
                }
                newmenulist.add(menu);
            }
        }
        return newmenulist;
    }
}
