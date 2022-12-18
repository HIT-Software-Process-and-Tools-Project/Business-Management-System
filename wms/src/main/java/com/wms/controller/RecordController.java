package com.wms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wms.common.QueryPageParam;
import com.wms.common.Result;
import com.wms.entity.Goods;
import com.wms.entity.Goodstype;
import com.wms.entity.Record;
import com.wms.entity.User;
import com.wms.mapper.GoodsMapper;
import com.wms.mapper.RecordMapper;
import com.wms.service.GoodsService;
import com.wms.service.RecordService;
import com.wms.service.UserService;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Formatter;
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
@RequestMapping("/record")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private UserService userService;

    @Resource
    private RecordMapper recordMapper;

    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam query){
        HashMap param = query.getParam();
        String name = (String)param.get("name");
        String goodstype = (String)param.get("goodstype");
        String iswholesale = (String)param.get("iswholesale");
        String storage = (String)param.get("storage");
        String roleId = (String)param.get("roleId");
        String userId = (String)param.get("userId");
        String state = (String)param.get("state");
        System.out.println(state);

        Page<Record> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        QueryWrapper<Record> queryWrapper = new QueryWrapper();
        queryWrapper.apply(" a.goods=b.id and b.storage=c.id and b.goodsType=d.id");

        /*if("2".equals(roleId)){
            // queryWrapper.eq(Record::getUserid,userId);
            queryWrapper.apply(" a.userId= "+userId);
        }*/

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like("b.name",name);
        }
        if(StringUtils.isNotBlank(goodstype) && !"null".equals(goodstype)){
            queryWrapper.eq("d.id",goodstype);
        }
        if(StringUtils.isNotBlank(storage) && !"null".equals(storage)){
            queryWrapper.eq("c.id",storage);
        }
        if(StringUtils.isNotBlank(state) && !"null".equals(state)){
            System.out.println(state);
            queryWrapper.eq("a.state",state);
        }

        IPage result = recordService.pageCC(page,queryWrapper);
        return Result.suc(result.getRecords(),result.getTotal());
    }
    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();
        float discount=Float.parseFloat(record.getRemark());
        int exCount=(int)discount/10;
        discount=discount%1;
        if(discount==0)
            discount+=1;
        System.out.println(discount);
        record.setRemark("");
        //出库
        if("2".equals(record.getAction())){
            //n = -n;
            record.setCount(n+exCount);
            int num = goods.getCount()-n-exCount;
            goods.setCount(num);
            float price=n*Float.parseFloat(goods.getWholesaleprice())*discount;
            float jprice=price-(n+exCount)*Float.parseFloat(goods.getPurchaseprice());
            String p=Float.toString(price);
            String jp=Float.toString(jprice);
            record.setTotalprice(p);
            record.setProfit(jp);
        }
        else if("1".equals(record.getAction())){
            int num = goods.getCount()+n;
            goods.setCount(num);
            float price=-record.getCount()*Float.parseFloat(goods.getPurchaseprice());
            float jprice=-record.getCount()*Float.parseFloat(goods.getPurchaseprice());
            String p=Float.toString(price);
            String jp=Float.toString(jprice);
            record.setTotalprice(p);
            record.setProfit(jp);
        }
        else{
            int num = goods.getCount()-n;
            goods.setCount(num);
            float price=record.getCount()*Float.parseFloat(goods.getRetailprice());
            float jprice=record.getCount()*(Float.parseFloat(goods.getRetailprice())-Float.parseFloat(goods.getPurchaseprice()));
            String p=Float.toString(price);
            String jp=Float.toString(jprice);
            record.setTotalprice(p);
            record.setProfit(jp);
        }
        goodsService.updateById(goods);

        return recordService.save(record)?Result.suc():Result.fail();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Record record){
        return recordService.updateById(record)?Result.suc():Result.fail();
    }

    @PostMapping("/update1")
    public Result update1(@RequestBody Record record){
        String remark=record.getRemark();
        if(StringUtils.isNotBlank(remark) && !"null".equals(remark)&&record.getUserid()!=null){
            User user=userService.getById(record.getUserid());
            float vip=Float.parseFloat(user.getVip())+Float.parseFloat(remark);
            user.setVip(Float.toString(vip));
            userService.updateById(user);
        }
        record.setRemark("");
        return recordService.updateById(record)?Result.suc():Result.fail();
    }

    @PostMapping("/update2")
    public Result update2(@RequestBody Record record){
        String remark=record.getRemark();
        if(StringUtils.isNotBlank(remark) && !"null".equals(remark)&&record.getUserid()!=null){
            User user=userService.getById(record.getUserid());
            float deposit=Float.parseFloat(user.getDeposit())-Float.parseFloat(remark);
            user.setDeposit(Float.toString(deposit));
            userService.updateById(user);
        }
        record.setRemark("");
        return recordService.updateById(record)?Result.suc():Result.fail();
    }

    @GetMapping("/del")
    public Result del(@RequestParam String id){
        return recordService.removeById(id)?Result.suc():Result.fail();
    }

    @PostMapping("/pos")
    public Float pos(@RequestBody Record record){
        Goods goods = goodsService.getById(record.getGoods());
        int n = record.getCount();

        int num = goods.getCount()-n;
        goods.setCount(num);
        float price=record.getCount()*Float.parseFloat(goods.getRetailprice());
        float jprice=record.getCount()*(Float.parseFloat(goods.getRetailprice())-Float.parseFloat(goods.getPurchaseprice()));
        String p=Float.toString(price);
        String jp=Float.toString(jprice);
        record.setTotalprice(p);
        record.setProfit(jp);

        goodsService.updateById(goods);
        recordService.save(record);

        return price;
    }

    @PostMapping("/sta")
    public Float sta(@RequestBody User user){
        float score = 0;
        List<String> cus=new ArrayList<>();
        String nowCus;
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        String id=user.getId().toString();
        lambdaQueryWrapper.eq(Record::getAdminId,id);
        List<Record> sta = recordMapper.selectList(lambdaQueryWrapper);
        //销售业绩=0.5*创收+0.3*销售数量+0.2*客户数量
        for(Record i:sta){
            if(i.getProfit()!=null){
                score+=0.5*Float.parseFloat(i.getProfit());
            }
            if(i.getCount()!=null){
                score+=0.3*i.getCount();
            }
        }
        for(Record i:sta){
            if(i.getUserid()!=null)
            {
                nowCus=i.getUserid().toString();
                if(!cus.contains(nowCus))
                    cus.add(nowCus);
            }
        }
        score+= 0.2*cus.size();

        //return score;
        return Float.parseFloat(String.format("%.2f", score));
    }

    @PostMapping("/paid")
    public Float paid(){
        float price = 0;
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.between(Record::getState,'2','4');
        List<Record> sta = recordMapper.selectList(lambdaQueryWrapper);
        for(Record i:sta){
            if(i.getTotalprice()!=null)
            {
                price+=Float.parseFloat(i.getTotalprice());
            }
        }
        return price;
    }

    @PostMapping("/unpaid")
    public Float unpaid(){
        float price = 0;
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.le(Record::getState,'1');
        List<Record> sta = recordMapper.selectList(lambdaQueryWrapper);
        for(Record i:sta){
            if(i.getTotalprice()!=null)
            {
                price+=Float.parseFloat(i.getTotalprice());
            }
        }
        return price;
    }

    @PostMapping("/paid1")
    public Float paid1(@RequestBody User user){
        float price = 0;
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.between(Record::getState,'2','4');
        lambdaQueryWrapper.eq(Record::getUserid,user.getId());
        List<Record> sta = recordMapper.selectList(lambdaQueryWrapper);
        for(Record i:sta){
            if(i.getTotalprice()!=null)
            {
                price+=Float.parseFloat(i.getTotalprice());
            }
        }
        return price;
    }

    @PostMapping("/unpaid1")
    public Float unpaid1(@RequestBody User user){
        float price = 0;
        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.le(Record::getState,'1');
        lambdaQueryWrapper.eq(Record::getUserid,user.getId());
        List<Record> sta = recordMapper.selectList(lambdaQueryWrapper);
        for(Record i:sta){
            if(i.getTotalprice()!=null)
            {
                price+=Float.parseFloat(i.getTotalprice());
            }
        }
        return price;
    }

    @PostMapping("/percent")
    public float[] percent(@RequestBody User user){
        float[] per={0,1,2};//金额、客户、数量
        List<String> cus=new ArrayList<>();
        List<String> uCus=new ArrayList<>();
        String nowCus;
        float price=0;
        float allPrice=0;
        float count=0;
        float allCount=0;

        LambdaQueryWrapper<Record> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.ge(Record::getId,"1");
        lambdaQueryWrapper.ne(Record::getState,"4");
        List<Record> all = recordMapper.selectList(lambdaQueryWrapper);
        for(Record i:all){
            if(i.getTotalprice()!=null)
            {
                allPrice+=Float.parseFloat(i.getTotalprice());
            }
            if(i.getUserid()!=null)
            {
                nowCus=i.getUserid().toString();
                if(!cus.contains(nowCus))
                    cus.add(nowCus);
            }
            if(i.getCount()!=null)
            {
                allCount+=(float)i.getCount();
            }
        }

        LambdaQueryWrapper<Record> lambdaQuery = new LambdaQueryWrapper();
        lambdaQuery.eq(Record::getAdminId,user.getId());
        lambdaQuery.ne(Record::getState,"4");
        List<Record> uAll = recordMapper.selectList(lambdaQuery);
        for(Record i:uAll){
            if(i.getTotalprice()!=null)
            {
                price+=Float.parseFloat(i.getTotalprice());
            }
            if(i.getUserid()!=null)
            {
                nowCus=i.getUserid().toString();
                if(!uCus.contains(nowCus))
                    uCus.add(nowCus);
            }
            if(i.getCount()!=null)
            {
                count+=(float)i.getCount();
            }
        }

        per[0]=new BigDecimal(100*price/allPrice).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        per[1]=new BigDecimal(100.0*uCus.size()/cus.size()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        per[2]=new BigDecimal(100*count/allCount).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        System.out.println("this is feature_4");


        return per;
    }



}

