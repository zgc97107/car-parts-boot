package com.car.service.impl;

import com.car.mapper.MenuMapper;
import com.car.mapper.MiddleMapper;
import com.car.mapper.RoleMapper;
import com.car.pojo.Menu;
import com.car.pojo.MiddleExample;
import com.car.pojo.Role;
import com.car.pojo.RoleExample;
import com.car.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MiddleMapper middleMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public long countByExample(RoleExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(RoleExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Role record) {
        return 0;
    }

    @Override
    public int insertSelective(Role record, int[] menuid) {
        Map<String,Object> param=new HashMap<>();
        roleMapper.insertSelective(record);
        if(record.getId()!=null){
            Integer id = record.getId();
            param.put("roleId",id);
            param.put("menuids",menuid);
            int i=middleMapper.insertMiddle(param);
            return i;
        }
        return 0;
    }

    @Override
    public List<Role> selectByExample(RoleExample example) {
        return null;
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Role record, RoleExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Role record, RoleExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record, int[] menuid) {
        int i=0;
        try {
            //1.更新角色表
            roleMapper.updateByPrimaryKey(record);
            //2.删除原来的角色和菜单的关系（中间表）
            MiddleExample example=new MiddleExample();
            MiddleExample.Criteria criteria = example.createCriteria();
            criteria.andRoleIdEqualTo(record.getId());
            middleMapper.deleteByExample(example);
            //3.新增角色和菜单的关系（中间表）
            Map param=new HashMap<>();
            param.put("roleId",record.getId());
            param.put("menuids",menuid);
            middleMapper.insertMiddle(param);
            i=1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    @Override
    public List<Role> findAllRole() {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        roleExample.setOrderByClause("id ASC");
        criteria.andRoleStateEqualTo(1);
        List<Role> roleList = roleMapper.selectByExample(roleExample);
        return roleList;
    }

    @Override
    public PageInfo<Role> getList(String rolename, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("rolename",rolename);
        PageHelper.startPage(pageNum,pageSize);
        List<Role> roleList = roleMapper.getRoleList(param);
        for (Role role : roleList) {
            List<Menu> menuList = menuMapper.findMenuByRoleId(role.getId());
            role.setMenuList(menuList);
        }
        PageInfo pageInfo=new PageInfo(roleList);
        return pageInfo;
    }

    @Override
    public void delRoles(List<Integer> ids) {
        //批量删除中间表关系
        MiddleExample example1=new MiddleExample();
        MiddleExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andRoleIdIn(ids);
        middleMapper.deleteByExample(example1);
        //批量删除角色表
        RoleExample example=new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        roleMapper.deleteByExample(example);
    }
}
