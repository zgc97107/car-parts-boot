package com.car.action;

import com.car.pojo.Order;
import com.car.pojo.TableData;
import com.car.pojo.User;
import com.car.service.OrderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order.html")
public class OrderAction {
    @Autowired
    private OrderService orderService;

    @RequestMapping(params = "act=order")
    public String order(){
        return "order";
    }

    @RequestMapping(params = "act=lookOrder")
    public String lookOrder(){
        return "lookOrder";
    }

    @RequestMapping(params = "act=ordersPage")
    @ResponseBody
    public TableData ordersPage(String id, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        Integer dealerId=null;
        User userLogin = (User) session.getAttribute("userLogin");
        if(userLogin.getRoleId()!=null&&(userLogin.getRoleId().equals(2))){
            dealerId = userLogin.getId();
        }
        PageInfo<Order> ordersPage = orderService.getList(id,dealerId, pageNum, pageSize);
        data.setCode(0);
        data.setCount(ordersPage.getTotal());
        data.setData(ordersPage.getList());
        return data;
    }

    @RequestMapping(params = "act=viewOrder")
    @ResponseBody
    public TableData viewOrder(String id, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        PageInfo<Order> orderViewPage = orderService.getViewList(id,pageNum, pageSize);
        data.setCode(0);
        data.setCount(orderViewPage.getTotal());
        data.setData(orderViewPage.getList());
        return data;
    }

    @RequestMapping(params = "act=lookPage")
    @ResponseBody
    public TableData lookPage(String id, @RequestParam(name = "page") Integer pageNum, @RequestParam(name = "limit") Integer pageSize, HttpSession session) {
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize > 30) {
            pageSize = 10;
        }
        TableData data = new TableData();
        Integer customerId=null;
        User userLogin = (User) session.getAttribute("userLogin");
        if(userLogin.getRoleId()!=null&&(userLogin.getRoleId().equals(3))){
            customerId = userLogin.getId();
        }
        PageInfo<Order> lookPage = orderService.getLookList(id,customerId, pageNum, pageSize);
        data.setCode(0);
        data.setCount(lookPage.getTotal());
        data.setData(lookPage.getList());
        return data;
    }


    @RequestMapping(params = "act=send")
    @ResponseBody
    public Map<String, Object> send(String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(id!=null){
                Order order=new Order();
                order.setId(id);
                order.setOrderState(1);
                orderService.updateByPrimaryKeySelective(order);
                result.put("status",true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    @RequestMapping(params = "act=confirm")
    @ResponseBody
    public Map<String, Object> confirm(String id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if(id!=null){
                Order order=new Order();
                order.setId(id);
                order.setOrderState(2);
                orderService.updateByPrimaryKeySelective(order);
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
