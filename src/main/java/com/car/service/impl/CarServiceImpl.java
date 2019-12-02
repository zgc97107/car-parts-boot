package com.car.service.impl;

import com.car.mapper.PartsMapper;
import com.car.pojo.Parts;
import com.car.service.CarService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private PartsMapper partsMapper;

    /**
     * 查询所有配件信息（分页）
     */
    @Override
    public PageInfo<Parts> getList(String partname, Integer pageNum, Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice, Integer typeid, Integer dealerid) {
        Map<String ,Object> param=new HashMap<>();
        param.put("partname",partname);
        param.put("minPrice",minPrice);
        param.put("maxPrice",maxPrice);
        param.put("typeid",typeid);
        param.put("dealerid",dealerid);
        PageHelper.startPage(pageNum,pageSize);
        List<Parts> partsList=partsMapper.getList(param);
        PageInfo<Parts> pageInfo=new PageInfo<>(partsList);
        return pageInfo;
    }

    /**
     * 添加配件
     */
    @Override
    public void addParts(Parts parts) {
        partsMapper.insert(parts);
    }

    /**
     * 修改配件信息
     */
    @Override
    public void updateParts(Parts parts) {
        partsMapper.updateByPrimaryKeySelective(parts);
    }

    /**
     * 批量删除配件
     */
    @Override
    public void delParts(int[] ids) {
        partsMapper.upPartsState(ids);
    }
}
