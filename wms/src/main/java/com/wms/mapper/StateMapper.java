package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.State;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StateMapper extends BaseMapper<State> {
    IPage pageCC(IPage<State> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
