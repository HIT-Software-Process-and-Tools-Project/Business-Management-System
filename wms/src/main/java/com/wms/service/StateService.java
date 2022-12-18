package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Goodstype;
import com.wms.entity.State;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.Storage;

public interface StateService extends IService<State> {
    IPage pageCC(IPage<State> page, Wrapper wrapper);
}
