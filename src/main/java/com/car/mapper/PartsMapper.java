package com.car.mapper;

import com.car.pojo.Parts;
import com.car.pojo.PartsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PartsMapper {
    long countByExample(PartsExample example);

    int deleteByExample(PartsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parts record);

    int insertSelective(Parts record);

    List<Parts> selectByExample(PartsExample example);

    Parts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByExample(@Param("record") Parts record, @Param("example") PartsExample example);

    int updateByPrimaryKeySelective(Parts record);

    int updateByPrimaryKey(Parts record);

    List<Parts> getList(Map param);

    void upPartsState(int[] ids);
}
