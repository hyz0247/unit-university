package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.AdminInformationMapper;
import com.example.mapper.JobMapper;
import com.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobMapper jobMapper;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobTypeService jobTypeService;

    @Autowired
    private UnitInformationService unitInformationService;

    @Autowired
    private CollectService collectService;

    @GetMapping("list")
    public Result list(){
        List list = jobService.list();
        return Result.suc(list);
    }

    /**单位分页查找*/
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String location = (String)hashMap.get("location");
        String roleId = (String)hashMap.get("roleId");
        String userId = (String)hashMap.get("userId");
        String unit = (String)hashMap.get("unit");
        String salaryMin = (String)hashMap.get("salaryMin");
        String salaryMax = (String)hashMap.get("salaryMax");

        IPage<Job> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Job> jobQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(roleId)&&roleId.equals("3")){
            jobQueryWrapper.eq(Job::getCompanyId,userId);
        }
        if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            jobQueryWrapper.like(Job::getTitle,name);
        }
        if(StringUtils.isNotBlank(location) && !"null".equals(location)){
            jobQueryWrapper.like(Job::getLocation,location);
        }
        if(StringUtils.isNotBlank(unit) && !"null".equals(unit)){
            jobQueryWrapper.eq(Job::getCompanyId,unit);
        }
        if((StringUtils.isNotBlank(salaryMin) && !"null".equals(salaryMin))&&(StringUtils.isNotBlank(salaryMax) && !"null".equals(salaryMax))){
            jobQueryWrapper.le(Job::getSalaryMin,salaryMax)
                    .ge(Job::getSalaryMax,salaryMin);
        }else if(StringUtils.isNotBlank(salaryMin) && !"null".equals(salaryMin)){
            jobQueryWrapper.ge(Job::getSalaryMax,salaryMin);
        }else if(StringUtils.isNotBlank(salaryMax) && !"null".equals(salaryMax)){
            jobQueryWrapper.le(Job::getSalaryMin,salaryMax);
        }

        IPage<Job> result = jobMapper.pageList(page,jobQueryWrapper);
        for (int i=0;i<result.getTotal();i++){
            if (roleId.equals("1")){
                List<Collect> list = collectService.lambdaQuery().eq(Collect::getJobId, result.getRecords().get(i).getId())
                        .eq(Collect::getStudentId, userId).list();
                if (list.size()>0){
                    result.getRecords().get(i).setFavorite(true);
                }else result.getRecords().get(i).setFavorite(false);
            }
        }
        return Result.suc(result.getRecords(),result.getTotal());
    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return jobService.removeById(id)?Result.suc():Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody Job job){
        return jobService.updateById(job)?Result.suc():Result.fail();
    }

    /** 添加*/
    @PostMapping("/save")
    public Result save(@RequestBody Job job){
        return jobService.save(job)?Result.suc():Result.fail();
    }

    /** 根据id查询*/
    @GetMapping("listById")
    public Result listById(@RequestParam Integer id){
        List<Job> list = jobService.lambdaQuery()
                .eq(Job::getId, id).list();
        if(list.size()> 0){
            Job jobInfo = list.get(0);
            HashMap hashMap = new HashMap();
            hashMap.put("jobInfo",jobInfo);

            if(jobTypeService.lambdaQuery().eq(JobType::getId,jobInfo.getTypeId()).list().size()>0){
                JobType jobTypeInfo = jobTypeService.lambdaQuery().eq(JobType::getId, jobInfo.getTypeId()).list().get(0);
                hashMap.put("jobTypeInfo",jobTypeInfo);
            }
            if(unitInformationService.lambdaQuery().eq(UnitInformation::getUserId,jobInfo.getCompanyId()).list().size()>0){
                UnitInformation unitInfo = unitInformationService.lambdaQuery().eq(UnitInformation::getUserId, jobInfo.getCompanyId()).list().get(0);
                hashMap.put("unitInfo",unitInfo);
            }

            return Result.suc(hashMap);
        }

        return Result.fail();


    }

}