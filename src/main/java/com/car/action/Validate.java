package com.car.action;

import com.car.pojo.User;
import com.car.pojo.UserExample;
import com.car.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Validate {
    @Autowired
    private UserService userService;

    @RequestMapping("/validatePassword")
    @ResponseBody
    public Map validatePassword(String oldpass,Integer id){
        Map map=new HashMap<>();
        if(null!=id&&null!=oldpass){
            User user = userService.selectByPrimaryKey(id);
            if(oldpass.equals(user.getPassword())){
                map.put("result",true);
                return map;
            }
        }
        map.put("result",false);
        return map;
    }
    @RequestMapping("/validateName")
    @ResponseBody
    public Map validateName(String nickname, String userNick, String type, HttpSession session){
        Map map=new HashMap<>();
        if("myInfo".equals(type)){
            User userLogin = (User)session.getAttribute("userLogin");
            if(null!=userLogin&&userLogin.getNickname().equals(nickname)){
                map.put("result",true);
                return map;
            }
        }else if("power".equals(type)){
            if(nickname.equals(userNick)){
                map.put("result",true);
                return map;
            }
            User userLogin = (User)session.getAttribute("userLogin");
            if(null!=userLogin&&userLogin.getRoleId()==1){
                UserExample example=new UserExample();
                UserExample.Criteria criteria = example.createCriteria();
                criteria.andnicknameEqualTo(nickname);
                List<User> userList = userService.selectByExample(example);
                if(userList.size()==0){
                    map.put("result",true);
                }else{
                    map.put("result",false);
                }
                return map;
            }
        }
        UserExample example=new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andnicknameEqualTo(nickname);
        List<User> userList = userService.selectByExample(example);
        if(userList.size()==0){
            map.put("result",true);
        }else{
            map.put("result",false);
        }
        return map;
    }

}
