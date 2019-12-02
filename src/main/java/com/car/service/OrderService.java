package com.car.service;

import com.car.pojo.Order;
import com.car.pojo.OrderExample;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderService {
    long countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(String id);

    int insert(Order record);

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    PageInfo<Order> getList(String id, Integer dealerId, Integer pageNum, Integer pageSize);

    PageInfo<Order> getViewList(String id, Integer pageNum, Integer pageSize);

    PageInfo<Order> getLookList(String id, Integer customerId, Integer pageNum, Integer pageSize);
}
