package com.car.action;

import com.car.pojo.Menu;
import com.car.pojo.Role;
import com.car.service.MenuService;
import com.car.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("power.html")
public class PowerAction {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;

    @RequestMapping(params = "act=user")
    public String user(ModelMap map){
        List<Role> roleList = roleService.findAllRole();
        map.put("roleList",roleList);
        return "user";
    }

    @RequestMapping(params = "act=role")
    public String role(ModelMap map){
        List<Menu> menuList = menuService.findAllMenu();
        map.put("menus",menuList);
        return "role";
    }

    @RequestMapping(params = "act=menu")
    public String menu(ModelMap map){
        List<Menu> upMenus = menuService.findAllUpMenu();
        map.put("upMenus",upMenus);
        return "menu";
    }


}
