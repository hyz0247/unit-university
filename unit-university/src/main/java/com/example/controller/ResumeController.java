package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.AdminInformation;
import com.example.entity.Resume;
import com.example.mapper.AdminInformationMapper;
import com.example.mapper.ResumeMapper;
import com.example.service.AdminInformationService;
import com.example.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeMapper resumeMapper;

    @Autowired
    private ResumeService resumeService;

    /** 新增或修改*/
    @PostMapping("/uploadResume")
    public Result uploadResume(@RequestBody Resume resume){
        List<Resume> list = resumeService.lambdaQuery().eq(Resume::getStudentId, resume.getStudentId()).list();
        if (list.size()>0){
            Resume resume1 = list.get(0);
            resume1.setUrl(resume.getUrl());
            resumeService.updateById(resume1);
            return Result.suc();
        }else {
            return resumeService.save(resume)?Result.suc():Result.fail();
        }


    }

}
