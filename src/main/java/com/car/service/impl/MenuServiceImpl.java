package com.car.service.impl;

import com.car.common.utils.NewmenuUtil;
import com.car.mapper.MenuMapper;
import com.car.pojo.Menu;
import com.car.pojo.MenuExample;
import com.car.service.MenuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public long countByExample(MenuExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(MenuExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Menu record) {
        return menuMapper.insert(record);
    }

    @Override
    public int insertSelective(Menu record) {
        return 0;
    }

    @Override
    public List<Menu> selectByExample(MenuExample example) {
        return null;
    }

    @Override
    public Menu selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Menu record, MenuExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Menu record, MenuExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Menu record) {
        return menuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Menu record) {
        return 0;
    }

    @Override
    public List<Menu> findMenuByRoleId(int roleId) {
        return null;
    }

    @Override
    public List<Menu> findAllMenu() {
        MenuExample example=new MenuExample();
        List<Menu> list = menuMapper.selectByExample(example);
        List<Menu> menus = NewmenuUtil.newmenuList(list);
        return menus;
    }

    @Override
    public PageInfo<Menu> getList(String menuname, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("menuname",menuname);
        PageHelper.startPage(pageNum,pageSize);
        List<Menu> menuList = menuMapper.getMenuList(param);
        PageInfo pageInfo=new PageInfo(menuList);
        return pageInfo;
    }

    @Override
    public void delMenus(List<Integer> ids) {

    }

    @Override
    public List<Menu> findAllUpMenu() {
        MenuExample menuExample=new MenuExample();
        MenuExample.Criteria criteria = menuExample.createCriteria();
        criteria.andUpmenuidEqualTo(-1);
        List<Menu> upMenus = menuMapper.selectByExample(menuExample);
        return upMenus;
    }
}
