package com.car.service;

import com.car.pojo.Shopping;
import com.car.pojo.ShoppingExample;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedList;
import java.util.List;

public interface ShoppingService {
    long countByExample(ShoppingExample example);

    int deleteByExample(ShoppingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Shopping record);

    int insertSelective(Shopping record);

    List<Shopping> selectByExample(ShoppingExample example);

    Shopping selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Shopping record, @Param("example") ShoppingExample example);

    int updateByExample(@Param("record") Shopping record, @Param("example") ShoppingExample example);

    int updateByPrimaryKeySelective(Shopping record);

    int updateByPrimaryKey(Shopping record);

    PageInfo<Shopping> getList(Integer dealerId, Integer userId, String partname, Integer pageNum, Integer pageSize);

    void delMarks(List<Integer> ids);

    int accountMarks(LinkedList<Integer> ids, String[] idnums);
}
