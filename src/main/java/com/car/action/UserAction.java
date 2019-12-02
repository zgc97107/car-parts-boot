package com.car.action;

import com.car.common.annotation.NoLoginRequired;
import com.car.common.annotation.SysLog;
import com.car.common.utils.JwtUtil;
import com.car.pojo.TableData;
import com.car.pojo.User;
import com.car.service.UserService;
import com.github.pagehelper.PageInfo;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user.html")
public class UserAction {
    @Autowired
    private UserService userService;

    @RequestMapping(params = "act=myInfo")
    public String myInfo(){
        return "myInfo";
    }

    @RequestMapping(params = "act=password")
    public String password(){
        return "password";
    }

    @NoLoginRequired
    @SysLog(level = Level.INFO, operation = "用户登录")
    @RequestMapping(value = "/loginUser")
    @ResponseBody
    public Map login(User user, HttpServletResponse response, HttpSession session){
        Map<String,Object> map=new HashMap<>();
        User userLogin = userService.login(user);
        if(userLogin!=null){
            session.setAttribute("userLogin",userLogin);
            // 生成token并将token写入到cookie中
            String token = JwtUtil.sign(userLogin.getId(), userLogin.getRoleId(), userLogin.getNickname());
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 1);
            cookie.setPath("*");
            response.addCookie(cookie);
            map.put("code",200);
        }else{
            map.put("code",-1);
        }
        return map;
    }

    @RequestMapping(value = "/regiUser")
    @ResponseBody
    public Map register(User user){
        Map<String,Object> map=new HashMap<>();
        int i = userService.insert(user);
        if(i>0){
            map.put("code",1);
        }else{
            map.put("code",0);
        }
        return map;
    }

    @RequestMapping(params = "act=editInfo")
    @ResponseBody
    public Map editInfo(String nickname, Integer id, HttpSession session){
        User record=new User();
        Map map=new HashMap<>();
        if(null!=nickname&&null!=id){
            record.setId(id);
            record.setNickname(nickname);
            int i = userService.updateByPrimaryKeySelective(record);
            if(i>0){
                User userLogin = (User) session.getAttribute("userLogin");
                userLogin.setNickname(nickname);
                session.setAttribute("userLogin",userLogin);
                map.put("code",1);
                return map;
            }
        }
        map.put("code",0);
        return map;
    }

    @RequestMapping(params = "act=editPass")
    @ResponseBody
    public Map editPass(String newpass, Integer id, HttpSession session){
        User record=new User();
        Map map=new HashMap<>();
        if(null!=newpass&&null!=id){
            record.setId(id);
            record.setPassword(newpass);
            int i = userService.updateByPrimaryKeySelective(record);
            if(i>0){
                session.invalidate();
                map.put("code",1);
                return map;
            }
        }
        map.put("code",0);
        return map;
    }

    @RequestMapping(params = "act=userPages")
    @ResponseBody
    public TableData getUserList(String nickname, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize){
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<User> usersPage = userService.getUserList(nickname,pageNum,pageSize);
        data.setCode(0);
        data.setCount(usersPage.getTotal());
        data.setData(usersPage.getList());
        return data;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(User user, Integer roleEdit) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(roleEdit!=null){
                user.setRoleId(roleEdit);
            }
            if(user.getId()==null){
                userService.insert(user);
            }else{
                userService.updateByPrimaryKeySelective(user);
            }
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=del")
    @ResponseBody
    public Map<String, Object> del(int[] ids) {
        Map<String, Object> result = new HashMap<>();
        List<Integer> idList=new ArrayList<>();
        try {
            if(ids!=null){
                for (int id : ids) {
                    idList.add(id);
                }
                userService.delUsers(idList);
                result.put("status",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
}
