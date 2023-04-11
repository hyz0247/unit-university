package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.ApprovalRecord;
import com.example.entity.CompanyWrittenTests;
import com.example.entity.User;
import com.example.service.CompanyWrittenTestsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/company-written-tests")
public class CompanyWrittenTestsController {

    @Autowired
    private CompanyWrittenTestsService companyWrittenTestsService;


    /**笔试题库分页查找*/
    @PostMapping("/listQuestion")
    public Result listQuestion(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String title = (String)hashMap.get("title");
        String unit = (String)hashMap.get("unit");

        IPage<CompanyWrittenTests> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<CompanyWrittenTests> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(title) && !"null".equals(title)){
            queryWrapper.like(CompanyWrittenTests::getTitle,title);
        }
        if (StringUtils.isNotBlank(unit) && !"null".equals(unit)){
            queryWrapper.eq(CompanyWrittenTests::getCompanyId,unit);
        }


        IPage result = companyWrittenTestsService.pageQuestion(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

    /** 添加*/
    @PostMapping("/save")
    public Result save(@RequestBody CompanyWrittenTests companyWrittenTests){
        return companyWrittenTestsService.save(companyWrittenTests)?Result.suc():Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody CompanyWrittenTests companyWrittenTests){
        return companyWrittenTestsService.updateById(companyWrittenTests)?Result.suc():Result.fail();
    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return companyWrittenTestsService.removeById(id)?Result.suc():Result.fail();
    }
}
