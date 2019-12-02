package com.car.service;

import com.car.pojo.Parts;
import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

public interface CarService {
    PageInfo<Parts> getList(String partname, Integer pageNum, Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice, Integer typeid, Integer dealerid);

    void addParts(Parts parts);

    void updateParts(Parts parts);

    void delParts(int[] ids);
}
