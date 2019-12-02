package com.car.service.impl;

import com.car.mapper.TypeMapper;
import com.car.pojo.Type;
import com.car.pojo.TypeExample;
import com.car.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeMapper typeMapper;

    @Override
    public long countByExample(TypeExample example) {
        return 0;
    }

    @Override
    public int deleteByExample(TypeExample example) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int insert(Type record) {
        return typeMapper.insert(record);
    }

    @Override
    public int insertSelective(Type record) {
        return 0;
    }

    @Override
    public List<Type> selectByExample(TypeExample example) {
        return null;
    }

    @Override
    public Type selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int updateByExampleSelective(Type record, TypeExample example) {
        return 0;
    }

    @Override
    public int updateByExample(Type record, TypeExample example) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(Type record) {
        return typeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Type record) {
        return 0;
    }

    @Override
    public List<Type> findAllType() {
        TypeExample typeExample=new TypeExample();
        TypeExample.Criteria criteria = typeExample.createCriteria();
        typeExample.setOrderByClause("id ASC");
        criteria.andTypeStateEqualTo(1);
        List<Type> typeList = typeMapper.selectByExample(typeExample);
        return typeList;
    }

    @Override
    public PageInfo<Type> getList(String typename, Integer pageNum, Integer pageSize) {
        Map<String ,Object> param=new HashMap<>();
        param.put("typename",typename);
        PageHelper.startPage(pageNum,pageSize);
        List<Type> typeList = typeMapper.getTypeList(param);
        PageInfo pageInfo=new PageInfo(typeList);
        return pageInfo;
    }

    @Override
    public void delTypes(List<Integer> ids) {
        TypeExample example=new TypeExample();
        TypeExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        typeMapper.deleteByExample(example);
    }
}
