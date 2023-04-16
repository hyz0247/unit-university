package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.*;
import com.example.service.JobService;
import com.example.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@RestController
@RequestMapping("/job-type")
public class JobTypeController {

    @Autowired
    private JobTypeService jobTypeService;

    @GetMapping("list")
    public Result list(){
        List list = jobTypeService.list();
        return Result.suc(list);
    }

    @GetMapping("typeList")
    public Result typeList(){
        List<JobTypeData> jobTypeDataList = new ArrayList<>();
        List<JobType> JobTypeList = jobTypeService.lambdaQuery().isNull(JobType::getJobParent).list();
        for (JobType jobType:JobTypeList){
            JobTypeData jobTypeData = new JobTypeData();
            List<JobTypeData> typeList = new ArrayList<>();
            List<JobType> list = jobTypeService.lambdaQuery().eq(JobType::getJobParent, jobType.getId()).list();
            jobTypeData.setValue(jobType.getId());
            jobTypeData.setLabel(jobType.getName());
            for (JobType jobType1:list){
                JobTypeData jobTypeData1 = new JobTypeData();
                jobTypeData1.setValue(jobType1.getId());
                jobTypeData1.setLabel(jobType1.getName());
                typeList.add(jobTypeData1);
                jobTypeData.setChildren(typeList);
            }
            jobTypeDataList.add(jobTypeData);

        }
        return Result.suc(jobTypeDataList);
    }


    /**类型分页查找*/
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");

        IPage<JobType> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<JobType> queryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.like(JobType::getName,name);
        }

        IPage<JobType> result = jobTypeService.listPage(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return jobTypeService.removeById(id)?Result.suc():Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody JobType jobType){
        return jobTypeService.updateById(jobType)?Result.suc():Result.fail();
    }

    /** 注册或添加*/
    @PostMapping("/save")
    public Result save(@RequestBody JobType jobType){
        return jobTypeService.save(jobType)?Result.suc():Result.fail();
    }

    /** 注册或添加*/
    @GetMapping("/listTypeParent")
    public Result listTypeParent(){

        List<JobType> list = jobTypeService.lambdaQuery().isNull(JobType::getJobParent).list();

        return list.size()>0?Result.suc(list):Result.fail();
    }

}
