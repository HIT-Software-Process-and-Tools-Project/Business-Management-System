package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Brand;
import com.wms.entity.User;
import com.wms.mapper.BrandMapper;
import com.wms.mapper.UserMapper;
import com.wms.service.BrandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
    @Resource
    private BrandMapper brandMapper;
    @Override
    public IPage pageCC(IPage<Brand> page, Wrapper wrapper) {
        return brandMapper.pageCC(page,wrapper);
    }
}
