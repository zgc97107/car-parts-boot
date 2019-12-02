package com.car.action;

import com.car.pojo.Parts;
import com.car.pojo.TableData;
import com.car.pojo.Type;
import com.car.pojo.User;
import com.car.service.CarService;
import com.car.service.TypeService;
import com.car.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("car.html")
public class CarAction {
    @Autowired
    private CarService carService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UserService userService;


    @RequestMapping(params = "act=carParts")
    public String carParts(ModelMap map){
        List<Type> typeList = typeService.findAllType();
        List<User> dealerList = userService.findAllDealer();
        map.put("typeList",typeList);
        map.put("dealerList",dealerList);
        return "carParts";
    }

    @RequestMapping(params = "act=lookParts")
    public String lookParts(ModelMap map){
        List<Type> typeList = typeService.findAllType();
        List<User> dealerList = userService.findAllDealer();
        map.put("typeList",typeList);
        map.put("dealerList",dealerList);
        return "lookParts";
    }

    @RequestMapping(params = "act=partsPage")
    @ResponseBody
    public TableData carPartsList(String partname, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice, Integer typeid, Integer dealerid, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        User userLogin = (User) session.getAttribute("userLogin");
        if(userLogin.getRoleId()!=null&&(userLogin.getRoleId().equals(2))){
            dealerid=userLogin.getId();
        }
        PageInfo<Parts> partsPage = carService.getList(partname, pageNum, pageSize,minPrice,maxPrice,typeid,dealerid);
        data.setCode(0);
        data.setCount(partsPage.getTotal());
        data.setData(partsPage.getList());
        return data;
    }

    @RequestMapping(params = "act=edit")
    @ResponseBody
    public Map<String, Object> edit(Parts parts, Integer typeEdit, Integer dealerEdit, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        try {
            User userLogin = (User) session.getAttribute("userLogin");
            if(userLogin.getRoleId()!=null&&(userLogin.getRoleId().equals(2))){
                parts.setDealerId(userLogin.getId());
            }
            if(typeEdit!=null){
                parts.setTypeId(typeEdit);
            }
            if(dealerEdit!=null){
                parts.setDealerId(dealerEdit);
            }
            if(parts.getId()==null){
                parts.setPartState(1);
                parts.setSales(0);
                carService.addParts(parts);
            }else{
                carService.updateParts(parts);
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
        try {
            if(ids!=null){
                carService.delParts(ids);
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



