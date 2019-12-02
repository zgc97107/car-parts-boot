package com.car.mapper;

import com.car.pojo.Middle;
import com.car.pojo.MiddleExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MiddleMapper {
    long countByExample(MiddleExample example);

    int deleteByExample(MiddleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Middle record);

    int insertSelective(Middle record);

    List<Middle> selectByExample(MiddleExample example);

    Middle selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Middle record, @Param("example") MiddleExample example);

    int updateByExample(@Param("record") Middle record, @Param("example") MiddleExample example);

    int updateByPrimaryKeySelective(Middle record);

    int updateByPrimaryKey(Middle record);

    int insertMiddle(Map param);
}
