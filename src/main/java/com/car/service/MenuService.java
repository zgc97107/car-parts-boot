package com.car.service;

import com.car.pojo.Menu;
import com.car.pojo.MenuExample;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuService {
    long countByExample(MenuExample example);

    int deleteByExample(MenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    List<Menu> selectByExample(MenuExample example);

    Menu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByExample(@Param("record") Menu record, @Param("example") MenuExample example);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findMenuByRoleId(int roleId);

    List<Menu> findAllMenu();

    PageInfo<Menu> getList(String menuname, Integer pageNum, Integer pageSize);

    void delMenus(List<Integer> ids);

    List<Menu> findAllUpMenu();

}
