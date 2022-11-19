package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Goodstype;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.Storage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
@Mapper
public interface GoodstypeMapper extends BaseMapper<Goodstype> {
    IPage pageCC(IPage<Goodstype> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    Integer insertGoodstype(Goodstype goodstype);

    Integer updateGoodstype(Goodstype goodstype);

    Integer updateGoodsCost(Goodstype goodstype);

    Goodstype queryGoodstypeByName(String name);

    Goodstype queryGoodstypeByGid(Long g_id);

    Integer deleteGoodstypeByGid(Long g_id);

    List<Goodstype> queryGoodstypeList();

    Storage queryStorageById(Integer i_id);
}
