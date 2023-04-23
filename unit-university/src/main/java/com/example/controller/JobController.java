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
import com.example.vo.BarVO;
import com.example.vo.PieVO;
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
    private UserService userService;

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

        User user = userService.lambdaQuery().eq(User::getId, userId).list().get(0);

        IPage<Job> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Job> jobQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(roleId)&&roleId.equals("3")){
            if (user.getAffiliation() != null){
                User user1 = userService.lambdaQuery().eq(User::getId, user.getAffiliation()).list().get(0);
                jobQueryWrapper.eq(Job::getCompanyId,userId).or().eq(Job::getCompanyId,user1.getId());
            }else
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
        jobQueryWrapper.eq(Job::getStatus,"1");

        IPage<Job> result = jobService.pageList(page,jobQueryWrapper);
        //System.out.println(result.getTotal());
        //System.out.println(result.getRecords());
        for (int i=0;i<result.getRecords().size();i++){
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

    /** 根据接收前端传来的selectedIndustry，查询不同行业收藏数最多的5个*/
    @PostMapping ("/listByFavorite")
    public Result listByFavorite(@RequestParam String industry){
        Integer typeID=0;
        //根据selectedIndustry给typeID赋值，当selectedIndustry为'it'时，typeID为1，查询IT行业收藏数最多的5个;当selectedIndustry为'finance'时，typeID为2，查询金融行业收藏数最多的5个;
        //当selectedIndustry为'realestate'时，typeID为3，查询房地产行业收藏数最多的5个;当selectedIndustry为'education'时，typeID为4，查询教育行业收藏数最多的5个;当selectedIndustry为'automotive'时，typeID为5，查询其他行业收藏数最多的5个;
        if(industry.equals("it")){
            typeID=1;
        }else if(industry.equals("finance")){
            typeID=2;
        }
        else if(industry.equals("medical")){
            typeID=3;
        }
        else if(industry.equals("education")){
            typeID=4;
        }
        else if(industry.equals("automotive")){
            typeID=5;
        }

        List<Job> list = jobService.lambdaQuery().eq(Job::getTypeId,typeID).orderByDesc(Job::getCollectNumber).last("limit 5").list();
//        List<Job> list = jobService.lambdaQuery().orderByDesc(Job::getCollectNumber).last("limit 5").list();
        return Result.suc(list);
    }

    @PostMapping ("/quarterList")
    public Result quarterList(@RequestBody HashMap hashMap){

        String roleId = (String)hashMap.get("roleId");
        String companyId = (String)hashMap.get("companyId");
        String year = (String)hashMap.get("year");
        String type = (String)hashMap.get("type");
        String unit = (String)hashMap.get("unit");

        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("YEAR(publish_date) = "+year+"");
        if (roleId.equals("3")){
            queryWrapper.eq(Job::getCompanyId,companyId);
        }
        if (StringUtils.isNotBlank(type)&& !"null".equals(type)){
            queryWrapper.eq(Job::getTypeId,type);
        }
        if (StringUtils.isNotBlank(unit)&& !"null".equals(unit)){
            queryWrapper.eq(Job::getCompanyId,unit);
        }

        BarVO barVO = jobService.quarterList(queryWrapper);
        List<PieVO> pieVOS = jobService.quarterListPie(queryWrapper);
        HashMap map = new HashMap<>();
        map.put("barVO",barVO);
        map.put("pieVOS",pieVOS);

        return Result.suc(map);
    }

    /** 录取一个减少一个*/
    @GetMapping("/reduce")
    public Result reduce(@RequestParam Integer jobId){
        Job job = jobMapper.selectById(jobId);
        Integer number = job.getNumber() - 1;
        job.setNumber(number);
        return jobService.updateById(job)?Result.suc():Result.fail();
    }

}
