package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.CollectMapper;
import com.example.mapper.JobMapper;
import com.example.service.CollectService;
import com.example.service.CollegeService;
import com.example.service.JobService;
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
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    @Autowired
    private CollectMapper collectMapper;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobMapper jobMapper;

    @GetMapping("/saveOrMove")
    public Result saveOrMove(@RequestParam  Integer jobId,Integer studentId ){
        List<Collect> list = collectService.lambdaQuery().eq(Collect::getJobId, jobId)
                .eq(Collect::getStudentId, studentId).list();
        if (list.size()>0){
            Job job = jobService.lambdaQuery().eq(Job::getId, jobId).list().get(0);
            int num = job.getCollectNumber() - 1;
            job.setCollectNumber(num);
            jobService.updateById(job);
            return collectService.removeById(list.get(0))?Result.suc():Result.fail();
        }else {
            Collect collect = new Collect();
            collect.setJobId(jobId);
            collect.setStudentId(studentId);
            Job job = jobService.lambdaQuery().eq(Job::getId, jobId).list().get(0);
            int num = job.getCollectNumber() + 1;
            job.setCollectNumber(num);
            jobService.updateById(job);
            return collectService.save(collect)? Result.suc():Result.fail();
        }

    }

    /** 根据id查询*/
    @PostMapping("/listCollect")
    public Result listCollect(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String location = (String)hashMap.get("location");
        String userId = (String)hashMap.get("userId");
        String unit = (String)hashMap.get("unit");
        String salaryMin = (String)hashMap.get("salaryMin");
        String salaryMax = (String)hashMap.get("salaryMax");

        IPage<Job> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Job> jobQueryWrapper = new LambdaQueryWrapper<>();

        jobQueryWrapper.apply("job.id = collect.job_id AND collect.student_id = "+userId+"");
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

        IPage<Job> result = collectService.listCollect(page,jobQueryWrapper);
//        for (int i=0;i<result.getTotal();i++){
//            List<Collect> list = collectService.lambdaQuery().eq(Collect::getJobId, result.getRecords().get(i).getId())
//                    .eq(Collect::getStudentId, userId).list();
//            if (list.size()>0){
//                result.getRecords().get(i).setFavorite(true);
//            }else result.getRecords().get(i).setFavorite(false);
//        }
        return Result.suc(result.getRecords(),result.getTotal());
    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
            Collect collect = collectMapper.selectById(id);
            Job job = jobService.lambdaQuery().eq(Job::getId, collect.getJobId()).list().get(0);
        if (collectService.removeById(id)){
            int num = job.getCollectNumber() - 1;
            job.setCollectNumber(num);
            jobService.updateById(job);
            return Result.suc();
        }

        return Result.fail();
    }

}
