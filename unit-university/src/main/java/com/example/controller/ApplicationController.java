package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Application;
import com.example.entity.Resume;
import com.example.entity.User;
import com.example.mapper.ApplicationMapper;
import com.example.service.ApplicationService;
import com.example.service.UserService;
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
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationMapper applicationMapper;

    @Autowired
    private ApplicationService applicationService;

    /** 添加*/
    @GetMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestParam Integer studentId,Integer jobId){
        List<Application> list = applicationService.lambdaQuery().eq(Application::getStudentId, studentId)
                .eq(Application::getJobId, jobId).list();
        if (list.size()>0){
            return Result.fail();
        }else {
            Application application = new Application();
            application.setStudentId(studentId);
            application.setJobId(jobId);
            application.setStatus(0);
            applicationService.save(application);
            return Result.suc();
        }

    }

    /** 添加*/
    @GetMapping("/update")
    public Result update(@RequestParam String status,String applicationId){

        Application application = applicationMapper.selectById(applicationId);
        application.setStatus(Integer.parseInt(status));
        return applicationService.updateById(application)?Result.suc():Result.fail();
    }

    /**申请表分页查找*/
    @PostMapping("/PageApplication")
    public Result PageApplication(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String status = (String)hashMap.get("status");
        String roleId = (String)hashMap.get("roleId");
        String userId = (String)hashMap.get("userId");

        User user = userService.lambdaQuery().eq(User::getId, userId).list().get(0);

        IPage<Application> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Application> applicationQueryWrapper = new LambdaQueryWrapper<>();

        applicationQueryWrapper.apply("job.id = application.job_id and student_information.student_id = application.student_id");

        if(roleId.equals("1")){
            applicationQueryWrapper.apply("application.student_id = '"+userId+"'");
        }else if (roleId.equals("3")){
            if (user.getAffiliation() != null){
                User user1 = userService.lambdaQuery().eq(User::getId, user.getAffiliation()).list().get(0);
                applicationQueryWrapper.apply("(job.company_id = "+userId+" or job.company_id = "+user1.getId()+")");
            }else
            applicationQueryWrapper.apply("job.company_id ='"+userId+"'");
        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            applicationQueryWrapper.apply("job.title like'%"+name+"%'");
        }
        if (StringUtils.isNotBlank(status) && !"null".equals(status)){
            applicationQueryWrapper.apply("application.status = "+status+"");
        }


        IPage result = applicationService.pageApplication(page,applicationQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

}
