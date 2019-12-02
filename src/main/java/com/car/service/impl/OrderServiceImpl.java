package com.car.service.impl;

import com.car.mapper.OrderMapper;
import com.car.mapper.PartsMapper;
import com.car.mapper.UserMapper;
import com.car.pojo.Order;
import com.car.pojo.OrderExample;
import com.car.pojo.Parts;
import com.car.pojo.User;
import com.car.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PartsMapper partsMapper;

    @Override
    public long countByExample(OrderExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(OrderExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(Order record) {
        return 0;
    }

    @Override
    public int insertSelective(Order record) {
        return 0;
    }

    @Override
    public List<Order> selectByExample(OrderExample example) {
        return null;
    }

    @Override
    public Order selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Order record, OrderExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Order record, OrderExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return orderMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return 0;
    }

    @Override
    public PageInfo<Order> getList(String id, Integer dealerId, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("id",id);
        param.put("dealerId",dealerId);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.getOrderList(param);
        for (Order order : orderList) {
            Integer customerId = order.getCustomerId();
            User user = userMapper.selectByPrimaryKey(customerId);
            order.setCustomer(user);
        }
        PageInfo pageInfo=new PageInfo(orderList);
        return pageInfo;
    }

    @Override
    public PageInfo<Order> getViewList(String id, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("id",id);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> viewList = orderMapper.getViewList(param);
        for (Order order : viewList) {
            Integer partId = order.getPartId();
            Parts parts = partsMapper.selectByPrimaryKey(partId);
            order.setParts(parts);
        }
        PageInfo pageInfo=new PageInfo(viewList);
        return pageInfo;
    }

    @Override
    public PageInfo<Order> getLookList(String id, Integer customerId, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("id",id);
        param.put("customerId",customerId);
        PageHelper.startPage(pageNum,pageSize);
        List<Order> orderList = orderMapper.getLookList(param);
        for (Order order : orderList) {
            Integer dealerId = order.getDealerId();
            User user = userMapper.selectByPrimaryKey(dealerId);
            order.setDealer(user);
        }
        PageInfo pageInfo=new PageInfo(orderList);
        return pageInfo;
    }
}
