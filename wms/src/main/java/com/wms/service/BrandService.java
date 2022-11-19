package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Brand;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.Storage;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
public interface BrandService extends IService<Brand> {
    IPage pageCC(IPage<Brand> page, Wrapper wrapper);
}
