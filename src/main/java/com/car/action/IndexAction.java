package com.car.action;

import com.car.common.annotation.NoLoginRequired;
import com.car.pojo.Role;
import com.car.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexAction {
    @Autowired
    private RoleService roleService;


    @RequestMapping("index.html")
    public String showIndex(){
        return "index";
    }

    @NoLoginRequired
    @RequestMapping("login.html")
    public String login(ModelMap map) {
        List<Role> roleList = roleService.findAllRole();
        map.put("roleList", roleList);
        return "login";
    }

    @RequestMapping("/loginout")
    @ResponseBody
    public Map loginout(HttpSession session){
        Map<String,Object> map=new HashMap<>();
        try {
            session.invalidate();
            map.put("code",1);
        } catch (Exception e) {
            map.put("code",0);
            e.printStackTrace();
        }
        return map;
    }
}
