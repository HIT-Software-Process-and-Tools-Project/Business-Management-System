package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Record;
import com.wms.mapper.GoodsMapper;
import com.wms.mapper.RecordMapper;
import com.wms.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

   @Autowired
    private GoodsService goodsService;
    @Resource
    private GoodsMapper goodsMapper;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Goods goods){
        return goodsService.save(goods)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Goods goods){
        return goodsService.updateById(goods)?Result.suc():Result.fail();
    }
    @PostMapping("/save1")
    public Result save1(@RequestBody Goods goods){
        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(Goods::getName,goods.getName());
        lambdaQueryWrapper.eq(Goods::getStorage,goods.getStorage());
        List<Goods> muList = goodsMapper.selectList(lambdaQueryWrapper);
        LambdaQueryWrapper<Goods> Wrapper = new LambdaQueryWrapper();
        Wrapper.eq(Goods::getId,goods.getId());
        List<Goods> yuanList = goodsMapper.selectList(Wrapper);
        Goods yuan=yuanList.get(0);
        System.out.println("进入调库");
        if(muList.size()!=0){
            System.out.println("有目标");
            Goods mu=muList.get(0);
            Integer muCount=goods.getCount();
            Integer yuanCount = yuan.getCount()-muCount;
            System.out.println("初始原"+yuan.getCount());
            System.out.println("更改原"+yuanCount);
            System.out.println("初始目的"+mu.getCount());
            muCount=mu.getCount()+muCount;
            System.out.println("更改目的"+muCount);
            mu.setCount(muCount);
            yuan.setCount(yuanCount);
            goodsService.updateById(yuan);
            return goodsService.updateById(mu)?Result.suc():Result.fail();

        }
        else {
            System.out.println("无目标");
            Integer yuanCount = yuan.getCount()-goods.getCount();
            yuan.setCount(yuanCount);
            goodsService.updateById(yuan);
            return goodsService.save(goods)?Result.suc():Result.fail();
        }

    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return goodsService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String storage = (String)param.get("storage");

        Page<Goods> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Goods> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Goods::getName,name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            lambdaQueryWrapper.eq(Goods::getGoodstype,goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            lambdaQueryWrapper.eq(Goods::getStorage,storage);
        }

        IPage result = goodsService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
}
