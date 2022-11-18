package com.wms.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wms.entity.Stock;
import com.wms.entity.Storage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wms.entity.StorageTransaction;
import com.wms.entity.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wms
 * @since 2022-10-15
 */
public interface StorageService extends IService<Storage> {

    

    IPage pageCC(IPage<Storage> page, Wrapper wrapper);

    int insertStorageChange(Storage storage);

    List<Storage> queryStorageWithGnameList();

    Integer updateStorage(Storage storage);

    Storage queryStorageByIId(Integer i_id);

    Integer queryQuantityByGid(Long g_id);

    Integer insertStorageWithGoodName(Storage storage);

    List<Storage> selectStorageByName(String name);

    Integer mergeStorage(Storage storage);

    Integer deleteStorageByIID(Integer i_id);

    List<Storage> queryWarehouseList();

    Storage queryStorageByIdAndIlID(Integer g_id, Integer il_id);

    Integer mergeInsertStorage(Storage storage);

    List<StorageTransaction> getStorageTransactionWithUid(Integer u_id);

    Integer insertStorageTransaction(StorageTransaction storageTransaction);

    public void refreshStorageTransView(org.springframework.ui.Model model, HttpServletRequest httpServletRequest);

    public boolean deleteStorageTransactionByItiId(Integer iti_id);

    public void deleteStorageTransactionByUId(Integer u_id);

    public void decreaseStorage(Integer decreaseQuantity, Storage storage);

    public void insertWarehouse(Storage storage);

    public void updateStorageView(Model model);

    public void updateWarehouse(Storage storage);

    public void insertStock(Stock stock);

    public void queryAllStocks();
}
