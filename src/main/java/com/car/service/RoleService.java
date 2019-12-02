package com.car.service;

import com.car.pojo.Role;
import com.car.pojo.RoleExample;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {

    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record, int[] menuid);

    List<Role> selectByExample(RoleExample example);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record, int[] menuid);

    List<Role> findAllRole();

    PageInfo<Role> getList(String rolename, Integer pageNum, Integer pageSize);

    void delRoles(List<Integer> ids);
}
