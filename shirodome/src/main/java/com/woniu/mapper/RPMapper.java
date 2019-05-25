package com.woniu.mapper;

import com.woniu.entity.RP;
import com.woniu.entity.RPExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RPMapper {
    long countByExample(RPExample example);

    int deleteByExample(RPExample example);

    int deleteByPrimaryKey(Integer rpid);

    int insert(RP record);

    int insertSelective(RP record);

    List<RP> selectByExample(RPExample example);

    RP selectByPrimaryKey(Integer rpid);

    int updateByExampleSelective(@Param("record") RP record, @Param("example") RPExample example);

    int updateByExample(@Param("record") RP record, @Param("example") RPExample example);

    int updateByPrimaryKeySelective(RP record);

    int updateByPrimaryKey(RP record);
}