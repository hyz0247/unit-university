package com.example.controller;


import com.example.commmon.Result;
import com.example.service.JobService;
import com.example.service.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
