package com.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.wms.entity.*;
import com.wms.mapper.*;
import com.wms.service.StorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, Storage> implements StorageService {

    @Autowired
    StorageMapper storageMapper;

    @Autowired
    StorageTransactionMapper storageTransactionMapper;

    @Autowired
    GoodstypeMapper goodstypeMapper;

    @Override
    public IPage pageCC(IPage<Storage> page, Wrapper wrapper) {
        return storageMapper.pageCC(page,wrapper);
    }

    @Override
    public int insertStorageChange(Storage storage) {
        assert storageMapper != null;
        return storageMapper.insertStorage(storage);
    }
    /**
     * @return
     */

    @Override
    public List<Storage> queryStorageWithGnameList() {
        return storageMapper.queryStorageWithGnameList();
    }

    @Override
    public Integer updateStorage(Storage storage){
        if(storage.getQuantity() > 0) {
            return storageMapper.updateStorage(storage);
        }else{
            return deleteStorageByIID(storage.getI_id());
        }
    }

    @Override
    public Storage queryStorageByIId(Integer i_id){
        return storageMapper.queryStorageById(i_id);
    }

    @Override
    public Integer queryQuantityByGid(Long g_id) {
        List<Integer> quantities = storageMapper.queryQuantityByGid(g_id);
        int sum = 0;
        if (quantities == null)
            return 0;
        for (Integer i : quantities)
            sum += i;
        return sum;
    }

    @Override
    public Integer insertStorageWithGoodName(Storage storage){
        if(storage.getQuantity() >=0)
            return storageMapper.insertStorageWithGoodName(storage);
        else return -1;
    }

    @Override
    public List<Storage> selectStorageByName(String name){
        return storageMapper.selectStorageByName(name);
    }
    @Override
    public Integer mergeStorage(Storage storage){
        return storageMapper.mergeStorage(storage);
    }

    @Override
    public Integer mergeInsertStorage(Storage storage){
        List<Storage> storage1 = this.selectStorageByName(storage.getName());
        if((!storage1.isEmpty()) && storage1.get(0).getIl_id().equals(storage.getIl_id())){
            storage.setIl_id(storage1.get(0).getIl_id());
            return this.mergeStorage(storage);
        }else {
            return this.insertStorageWithGoodName(storage);
        }
    }

    @Override
    public Integer deleteStorageByIID(Integer i_id){
        return storageMapper.deleteStorageByIId(i_id);
    }
    @Override
    public List<Storage> queryWarehouseList(){
        return storageMapper.queryWarehouseList();
    }
    @Override
    public Storage queryStorageByIdAndIlID(Integer g_id, Integer il_id){
        return  storageMapper.queryStorageByIdAndIlID(g_id, il_id);
    }

    @Override
    public List<StorageTransaction> getStorageTransactionWithUid(Integer u_id){
        List<StorageTransaction> transactions = storageTransactionMapper.getStorageTransactionByUid(u_id);
        int transactions_len = transactions.size();
        for(int i = 0;i < transactions_len;i ++){
            StorageTransaction storageTransaction = transactions.get(i);
            modifyforTransactionDetail(storageTransaction);
        }
        return transactions;
    }

    private void modifyforTransactionDetail(StorageTransaction storageTransaction) {

        Goodstype goodsInfo = goodstypeMapper.queryGoodstypeByGid(storageTransaction.getG_id());
        String goods_name = goodsInfo.getName();
        Storage s_storage = getStorageByNameAndIlId(goods_name, storageTransaction.getIl_id_s());

        if(s_storage != null) {
            storageTransaction.setS_quantity(s_storage.getQuantity());
        }else{
            storageTransaction.setS_quantity(0);
        }

        String s_storage_name = storageMapper.queryWarehouseByIlID(storageTransaction.getIl_id_s()).getStorage_name();
        storageTransaction.setS_storage_name(s_storage_name);

        Storage warehouse = storageMapper.queryWarehouseByIlID(storageTransaction.getIl_id_d());
        String d_storage_name = warehouse.getStorage_name();

        // 原仓库名，目的仓库名，货物名
        storageTransaction.setD_storage_name(d_storage_name);
        storageTransaction.setGoods_name(goods_name);

        // 若目的仓库中有库存，则使用库存值，否则使用0
        Storage d_storage = getStorageByNameAndIlId(goods_name, storageTransaction.getIl_id_d());
        if(d_storage != null){
            storageTransaction.setD_quantity(d_storage.getQuantity());
            // 若目的库中存在对应的库存
            storageTransaction.setI_id_d(d_storage.getI_id());
        }else{
            storageTransaction.setD_quantity(0);
        }
    }

    private Storage getStorageByNameAndIlId(String name, Integer il_id){
        List<Storage> storage1 = this.selectStorageByName(name);
        int k = 0;
        Integer storage1_len = storage1.size();
        for(;k<storage1_len;k++){
            if(storage1.get(k).getIl_id().equals(il_id)) break;
        }
        if(k<storage1_len){
            return storage1.get(k);
        }else{
            return null;
        }
    }

    @Override
    public Integer insertStorageTransaction(StorageTransaction storageTransaction) {
        return storageTransactionMapper.insertStorageTransaction(storageTransaction);
    }

    @Override
    public void refreshStorageTransView(Model model, HttpServletRequest httpServletRequest) {
        List<Storage> inventories = this.queryStorageWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Storage> storage_lists = this.queryWarehouseList();
        model.addAttribute("storage_lists", storage_lists);
        Integer u_id = (Integer) httpServletRequest.getSession().getAttribute("u_id");
        List<StorageTransaction> storageTransactions = this.getStorageTransactionWithUid(u_id);
        model.addAttribute("storage_transactions", storageTransactions);
    }

    @Override
    public boolean deleteStorageTransactionByItiId(Integer iti_id) {
        StorageTransaction storageTransaction =  storageTransactionMapper.getStorageTransactionByItiId(iti_id);
        modifyforTransactionDetail(storageTransaction);

        Storage d_storage = new Storage();
        if(storageTransaction.getI_id_d() != null) {
            d_storage = this.queryStorageByIId(storageTransaction.getI_id_d());
        }else return false;

        if(d_storage.getQuantity() < storageTransaction.getQuantity()){
            return false;
        }

        Storage storage = new Storage();
        storage.setQuality(d_storage.getQuality());
        storage.setIl_id(storageTransaction.getIl_id_s());
        storage.setQuantity(storageTransaction.getQuantity());
        storage.setG_id(d_storage.getG_id());
        storage.setName(d_storage.getName());
        this.mergeInsertStorage(storage);

        this.decreaseStorage(storageTransaction.getQuantity(), d_storage);
        storageTransactionMapper.deleteStorageTransactionByItiId(iti_id);
        return true;
    }

    @Override
    public void deleteStorageTransactionByUId(Integer u_id) {
        storageTransactionMapper.deleteStorageTransactionByUId(u_id);
    }

    @Override
    public void decreaseStorage(Integer decreaseQuantity, Storage storage) {
        if(decreaseQuantity >= storage.getQuantity()){
            this.deleteStorageByIID(storage.getI_id());
        }else{
            storage.setQuantity(storage.getQuantity() - decreaseQuantity);
            this.updateStorage(storage);
        }
    }

    @Override
    public void insertWarehouse(Storage storage) {
        storageMapper.insertWarehouse(storage);
    }

    class WarehouseComparator implements Comparator<Storage> {

        @Override
        public int compare(Storage o1, Storage o2) {
            return o1.getStorage_prior() - o2.getStorage_prior();
        }

        @Override
        public boolean equals(Object obj) {
            return false;
        }
    }

    /**
     * 每次刷新storageView时，向model中添加库存和排好序的仓库
     * @param model
     */
    @Override
    public void updateStorageView(org.springframework.ui.Model model) {
        List<Storage> inventories = this.queryStorageWithGnameList();
        model.addAttribute("inventories", inventories);
        List<Storage> storage_lists = this.queryWarehouseList();
        storage_lists.sort(new WarehouseComparator());
        model.addAttribute("storage_lists", storage_lists);
    }

    @Override
    public void updateWarehouse(Storage storage) {
        storageMapper.updateWarehouse(storage);
    }

    @Override
    public void insertStock(Stock stock) {
        storageMapper.insertStock(stock);
    }

    @Override
    public void queryAllStocks() {
        storageMapper.queryAllStocks();
    }



}
