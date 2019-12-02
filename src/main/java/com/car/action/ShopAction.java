package com.car.action;

import com.car.pojo.Shopping;
import com.car.pojo.TableData;
import com.car.pojo.User;
import com.car.service.ShoppingService;
import com.car.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("shop.html")
public class ShopAction {
    @Autowired
    private ShoppingService shoppingService;
    @Autowired
    private UserService userService;

    @RequestMapping(params = "act=shopping")
    public String shopping(ModelMap map){
        List<User> dealerList = userService.findAllDealer();
        map.put("dealers",dealerList);
        return "shopping";
    }

    @RequestMapping(params = "act=markParts")
    @ResponseBody
    public Map<String, Object> markParts(Shopping shop) {
        Map<String, Object> result = new HashMap<>();
        try {
            shoppingService.insert(shop);
            result.put("status",true);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=marksPage")
    @ResponseBody
    public TableData marksPage(Integer dealerId, String partname, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        Integer userId=null;
        User userLogin = (User) session.getAttribute("userLogin");
        if(userLogin.getRoleId()!=null&&(userLogin.getRoleId().equals(3))){
            userId=userLogin.getId();
        }
        PageInfo<Shopping> marksPage = shoppingService.getList(dealerId,userId,partname,pageNum, pageSize);
        data.setCode(0);
        data.setCount(marksPage.getTotal());
        data.setData(marksPage.getList());
        return data;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(Shopping shopping) {
        Map<String, Object> result = new HashMap<>();
        try {
            shoppingService.updateByPrimaryKeySelective(shopping);
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
                shoppingService.delMarks(idList);
                result.put("status",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=account")
    @ResponseBody
    public Map<String, Object> account(int[] ids,String[] idnums) {
        Map<String, Object> result = new HashMap<>();
        LinkedList<Integer> idList=new LinkedList<>();
        try {
            if(ids!=null){
                for (int i = 0; i < ids.length; i++) {
                    idList.add(ids[i]);
                }
                int i = shoppingService.accountMarks(idList, idnums);
                if(i>0){
                    result.put("status",true);
                }else if(i==-1){
                    result.put("status", false);
                    result.put("message", "请选择同一个卖家");
                }else{
                    result.put("status", false);
                    result.put("message", "系统错误，结算失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }


}
