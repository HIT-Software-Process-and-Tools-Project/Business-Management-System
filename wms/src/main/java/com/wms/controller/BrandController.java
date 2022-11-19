package com.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Brand;
import com.wms.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: zxr
 * \* Date: 2022/11/19
 * \* Time: 18:08
 * \* Description:
 * \
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Brand brand){
        return brandService.save(brand)?Result.suc():Result.fail();
    }
    //更新
    @PostMapping("/update")
    public Result update(@RequestBody Brand brand){
        return brandService.updateById(brand)?Result.suc():Result.fail();
    }
    //删除
    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return brandService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");

        Page<Brand> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Brand> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            lambdaQueryWrapper.like(Brand::getName,name);
        }

        IPage result = brandService.pageCC(page,lambdaQueryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }

    @GetMapping("/list")
    public Result list(){
        List list = brandService.list();
        return Result.suc(list);
    }
}
