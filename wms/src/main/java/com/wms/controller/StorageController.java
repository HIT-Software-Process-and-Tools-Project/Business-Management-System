package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
//import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.*;
import com.wms.service.GoodsService;
import com.wms.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2022-10-15
 */
@RestController
@RequestMapping("/storage")
public class StorageController {

    @Autowired
    StorageService storageService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    HttpServletRequest httpServletRequest;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Storage storage){
        return storageService.save(storage)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Storage storage){
        return storageService.updateById(storage)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return storageService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Storage> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Storage> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Storage::getName,name);
        }

        IPage result = storageService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = storageService.list();
        return Result.suc(list);
    }

    /**
     * 添加库存实体类
     *
     * @return 进行网页转发，转发至addStorage
     */
    @RequestMapping("addStorage")
    public String addStorage(Model model) {
        List<Goodstype> goodstypes = goodsService.getAllGoods();
        model.addAttribute("goodstypes", goodstypes);
        List<Storage> storage_lists = storageService.queryWarehouseList();
        model.addAttribute("storage_lists", storage_lists);
        return "addStorage";
    }

    /**
     * 查看库存信息
     *
     * @param model 是视图，可以添加属性，传递信息
     * @return 转发至storageView
     */
    @RequestMapping("storageView")
    public String storageView(Model model) {
        List<Storage> inventories = storageService.queryStorageWithGnameList();
        model.addAttribute("inventories", inventories);
        return "storageView";
    }
    @RequestMapping("storageCheck")
    public String storageCheck(Model model){
        List<Storage> storages = storageService.queryStorageWithGnameList();
        model.addAttribute("storages", storages);
        List<Storage> storage_lists = storageService.queryWarehouseList();
        model.addAttribute("storage_lists", storage_lists);
        return "storageCheck";
    }
    @RequestMapping({"addStorageNow", "updateStorageNow", "deleteStorageNow"})
    public String addStorageNow(Storage storage, Model model, HttpServletRequest request){
        String uri = request.getRequestURI();
        long g_id = 0;
        int quantity_old = 0;
        int quantity_dev = 0;
        double cost_old = 0;
        double cost_new = 0;
        Stock stock = new Stock();
        if(uri.charAt(1) == 'a') {
            g_id = goodsService.queryGoodstypeByName(storage.getName()).getG_id();
            quantity_old = storageService.queryQuantityByGid(g_id);
            quantity_dev = storage.getQuantity();
            cost_old = goodsService.queryGoodstypeByGid(g_id).getCost();
            cost_new = storage.getCost();
            storageService.mergeInsertStorage(storage);
            // 仅在添加库存时，更新入库记录表
            stock.setCost(Math.round((quantity_dev * cost_new) * 100) / 100.0);
            stock.setG_id(g_id);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(new Date());
            stock.setTime_stamp(date);
            storageService.insertStock(stock);
        }else if(uri.charAt(1) == 'u'){
            if (storage != null) {
                g_id = goodsService.queryGoodstypeByName(storage.getName()).getG_id();
                quantity_old = storageService.queryQuantityByGid(g_id);
                quantity_dev = -1 * storage.getQuantity();
                cost_old = goodsService.queryGoodstypeByGid(g_id).getCost();
                cost_new = cost_old;
                Storage storage_old = storageService.queryStorageByIId(storage.getI_id());
                Integer decreaseQuantity = storage.getQuantity();
                storage_old.setQuality(storage.getQuality());
                storageService.decreaseStorage(decreaseQuantity, storage_old);
            }
        }else if(uri.charAt(1) == 'd'){
            g_id = storageService.queryStorageByIId(storage.getI_id()).getG_id();
            quantity_old = storageService.queryQuantityByGid(g_id);
            quantity_dev = -1 * storageService.queryStorageByIId(storage.getI_id()).getQuantity();
            storageService.deleteStorageByIID(storage.getI_id());
        }
        // 重新计算成本价（加权平均）
        double cost_res = cost_old;
        if (quantity_dev + quantity_old != 0 && cost_new != cost_old) {
            cost_res = (cost_old * quantity_old + cost_new * quantity_dev) / (quantity_dev + quantity_old);
        } else if (quantity_dev + quantity_old == 0) {
            cost_res = -1.0;
        }
        // 更新货品资料信息
        if (cost_res != cost_old) {
            Goodstype costUpdate = goodsService.queryGoodstypeByGid(g_id);
            cost_res = (double) Math.round(cost_res * 100) / 100.0; // 保留两位小数
            costUpdate.setCost(cost_res);
            goodsService.updateCost(costUpdate);
        }
        List<Storage> inventories = storageService.queryStorageWithGnameList();
        model.addAttribute("inventories", inventories);
        return "storageView";
    }

    @RequestMapping("updateStorage")
    public String updateStorage(Integer i_id, Model model){
        Storage storage = storageService.queryStorageByIId(i_id);
        model.addAttribute("storage", storage);
        return "updateStorage";
    }

    @RequestMapping("changePrior")
    public String changePrior(@RequestParam("storage") List<String> storage, org.springframework.ui.Model model){
        if(!storage.isEmpty()){
            storage = safeExtractJSONStrings(storage);
            for(String inv:storage){
                JSONObject jbo = JSON.parseObject(inv);
                Storage storage1 = new Storage();
                storage1.setStorage_info(jbo.getString("storage_info"));
                storage1.setStorage_name(jbo.getString("storage_name"));
                storage1.setStorage_prior(jbo.getInteger("storage_prior"));
                storage1.setIl_id(jbo.getInteger("il_id"));
                storageService.updateWarehouse(storage1);
            }
        }
        return "redirect:storageView";
    }

    private List<String> safeExtractJSONStrings(List<String> storage) {
        if(storage.get(0).charAt(storage.get(0).length() - 1) != '}'){
            for(int i = 1; i < storage.size(); i ++){
                storage.set(0, storage.get(0) + ',' + storage.get(i));
            }
            storage = storage.subList(0, 1);
        }
        return storage;
    }
    @RequestMapping("checkStorage")
    public String checkStorage(@RequestParam("storage") List<String> storage, Model model){
        if(! storage.isEmpty()) {
            storage = safeExtractJSONStrings(storage);
            for (String inv : storage) {
                JSONObject jbo = JSONObject.parseObject(inv);
                Storage storage1 = new Storage();
                storage1.setI_id(jbo.getInteger("i_id"));
                storage1.setG_id(jbo.getLong("g_id"));
                storage1.setQuality(jbo.getString("quality"));
                storage1.setQuantity(jbo.getInteger("quantity"));
                storageService.updateStorage(storage1);
            }
        }
        List<Storage> inventories = storageService.queryStorageWithGnameList();
        model.addAttribute("inventories", inventories);
        return "storageCheck";
    }

    @RequestMapping("storageTrans")
    public String storageTrans(Model model) {
        model.addAttribute("warn", true);
        storageService.refreshStorageTransView(model, httpServletRequest);
        return "storageTrans";
    }

    @RequestMapping("addTransTransection")
    public String addTransTransection(Model model){
        return null;
    }

    @RequestMapping("addOneTransformItem")
    public String addOneTransformItem(StorageTransaction transaction, Model model){
        Storage storage = storageService.queryStorageByIId(transaction.getI_id_s());
        transaction.setG_id(storage.getG_id());
        transaction.setIl_id_s(storage.getIl_id());
        storageService.insertStorageTransaction(transaction);


        // 修改库存
        Integer decreaseInteger = transaction.getQuantity();
        storageService.decreaseStorage(decreaseInteger, storage);
        storage.setIl_id(transaction.getIl_id_d());
        storage.setQuantity(transaction.getQuantity());
        storageService.mergeInsertStorage(storage);

        model.addAttribute("warn", true);
        storageService.refreshStorageTransView(model, httpServletRequest);
        return "storageTrans";
    }
    

    @RequestMapping("deleteStorageTransaction")
    public String deleteStorageTransaction(Integer iti_id, Model model){
        storageService.deleteStorageTransactionByItiId(iti_id);
        storageService.refreshStorageTransView(model, httpServletRequest);
        return "storageTrans";
    }

    @RequestMapping("retreatStorageTrans")
    public String retreatStorageTrans(Integer iti_id, Model model){
        boolean success = storageService.deleteStorageTransactionByItiId(iti_id);
        model.addAttribute("warn", success);
        storageService.refreshStorageTransView(model, httpServletRequest);
        return "storageTrans";
    }

    @RequestMapping("sureToTransStorage")
    public String retreatStorageTrans(Model model){
        Integer u_id = (Integer) httpServletRequest.getSession().getAttribute("u_id");
        storageService.deleteStorageTransactionByUId(u_id);
        storageService.refreshStorageTransView(model, httpServletRequest);

        model.addAttribute("warn", true);
        return "storageTrans";
    }

    @RequestMapping("addStorageList")
    public String addStorageList(Storage storage, org.springframework.ui.Model model){
        if(storage.getStorage_name() != null) {
            List<Storage> inventories = storageService.queryWarehouseList();
            Integer maxPrior = 0;
            for (Storage warehouse : inventories) {
                maxPrior = Math.max(maxPrior, warehouse.getStorage_prior());
            }
            storage.setStorage_prior(maxPrior + 1);
            storageService.insertWarehouse(storage);
        }
        return "redirect:storageView";
    }
}
