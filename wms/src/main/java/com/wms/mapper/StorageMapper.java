package com.wms.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.wms.entity.Stock;
import com.wms.entity.Storage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wms.entity.StorageTransaction;
import com.wms.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.transaction.Transaction;

import java.awt.*;
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
public interface StorageMapper extends BaseMapper<Storage> {

    IPage pageCC(IPage<Storage> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    int insertStorage(Storage storage);

    int deleteStorageByGid(Long g_id);

    List<Integer> queryQuantityByGid(Long g_id);

    List<Storage> queryStorageWithGnameList();

    /**
     * 根据仓库ID查询仓库内货品信息
     *
     * @param il_id 仓库ID
     * @return {库存ID、货品ID、货品名、货品数量、单件成本}列表
     */
    List<Storage> queryStorageWithIlid(Integer il_id);

    Integer insertWarehouse(Storage storage);

    Integer insertStorageWithGoodName(Storage storage);

    List<Storage> selectStorageByName(String name);

    Integer updateStorage(Storage storage);

    Storage queryStorageById(Integer i_id);

    Integer mergeStorage(Storage storage);

    Integer deleteStorageByIId(Integer i_id);

    List<Storage> queryWarehouseList();

    Storage queryStorageByIdAndIlID(Integer g_id, Integer il_id);

    Storage queryWarehouseByIlID(Integer il_id);

    Integer updateWarehouse(Storage storage);

    List<Stock> queryAllStocks();

    Integer insertStock(Stock stock);

}
