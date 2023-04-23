package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Application;
import com.example.entity.ApprovalRecord;
import com.example.entity.Job;
import com.example.entity.User;
import com.example.service.ApprovalRecordService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-03-28
 */
@RestController
@RequestMapping("/approval-record")
public class ApprovalRecordController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApprovalRecordService approvalRecordService;

    /** 注册或添加*/
    @PostMapping("/save")
    public Result save(@RequestBody ApprovalRecord approvalRecord){
        return approvalRecordService.save(approvalRecord)?Result.suc():Result.fail();
    }

    /**申请记录表分页查找*/
    @PostMapping("/pageRecord")
    public Result pageRecord(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String status = (String)hashMap.get("status");
        String roleId = (String)hashMap.get("roleId");
        String userId = (String)hashMap.get("userId");

        User user = userService.lambdaQuery().eq(User::getId, userId).list().get(0);

        IPage<ApprovalRecord> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<ApprovalRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.apply("application.id = approval_record.application_id and job.id = application.job_id");

        if(roleId.equals("1")){
            queryWrapper.apply("application.student_id = '"+userId+"'");
        }else if (roleId.equals("3")){
            if (user.getAffiliation() != null){
                User user1 = userService.lambdaQuery().eq(User::getId, user.getAffiliation()).list().get(0);
                queryWrapper.apply("(approval_record.operator_id ='"+userId+"' or approval_record.operator_id = "+user1.getId()+")");
            }else
            queryWrapper.apply("approval_record.operator_id ='"+userId+"'");
        }
        if (StringUtils.isNotBlank(name) && !"null".equals(name)){
            queryWrapper.apply("job.title like'%"+name+"%'");
        }
        if (StringUtils.isNotBlank(status) && !"null".equals(status)){
            queryWrapper.apply("approval_record.status = "+status+"");
        }


        IPage result = approvalRecordService.pageRecord(page,queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }
}
