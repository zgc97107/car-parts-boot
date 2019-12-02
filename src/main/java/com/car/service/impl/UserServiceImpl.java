package com.car.service.impl;

import com.car.common.utils.NewmenuUtil;
import com.car.mapper.MenuMapper;
import com.car.mapper.UserMapper;
import com.car.pojo.Menu;
import com.car.pojo.User;
import com.car.pojo.UserExample;
import com.car.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public long countByExample(UserExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(UserExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(User record, UserExample example) {
        return 0;
    }

    @Override
    public int updateByExample(User record, UserExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public List<User> findAllDealer() {
        UserExample userExample=new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        userExample.setOrderByClause("id ASC");
        criteria.andRoleIdEqualTo(2);
        List<User> dealerList = userMapper.selectByExample(userExample);
        return dealerList;
    }

    @Override
    public User login(User user) {
        User userLogin = userMapper.login(user.getNickname());
        //定义业务逻辑
        if(userLogin!=null&&user.getPassword().equals(userLogin.getPassword())){
            //根据角色查询菜单信息
            List<Menu> list = menuMapper.findMenuByRoleId(userLogin.getRoleId());
            //分一二级菜单
            List<Menu> menus = NewmenuUtil.newmenuList(list);
            userLogin.getRole().setMenuList(menus);
            return userLogin;
        }
        return null;
    }

    @Override
    public PageInfo<User> getUserList(String nickname, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("nickname",nickname);
        PageHelper.startPage(pageNum,pageSize);
        List<User> userList = userMapper.getUserList(param);
        PageInfo pageInfo=new PageInfo(userList);
        return pageInfo;
    }

    @Override
    public void delUsers(List<Integer> ids) {
        UserExample example=new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        userMapper.deleteByExample(example);
    }
}
