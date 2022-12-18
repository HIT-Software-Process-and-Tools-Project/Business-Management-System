package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.*;
import com.wms.mapper.*;
import com.wms.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StateServiceImpl extends ServiceImpl<StateMapper, State> implements StateService {
    @Resource
    private StateMapper stateMapper;

    @Override
    public IPage pageCC(IPage<State> page, Wrapper wrapper) {

        return stateMapper.pageCC(page,wrapper);
    }
}
