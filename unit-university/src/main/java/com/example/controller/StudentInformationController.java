package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.StudentInformationMapper;
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
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/student-information")
public class StudentInformationController {

    @Autowired
    private StudentInformationMapper studentMapper;

    @Autowired
    private StudentInformationService studentInformationService;

    @Autowired
    private UniversityInformationService universityInformationService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private JobTypeService jobTypeService;


    /** 修改学生信息*/
    @PostMapping("/modify")
    public Result modify(@RequestBody StudentInformation student){

        studentInformationService.saveOrUpdate(student);
        StudentInformation student1 = studentMapper.selectById(student.getId());
        return Result.suc(student1);
    }
    /** 查询所有*/
    @GetMapping("list")
    public Result list(){
        List<StudentInformation> list = studentInformationService.list();
        return Result.suc(list);
    }
    /** 根据id查询*/
    @GetMapping("listById")
    public Result listById(@RequestParam Integer student_id){
        List<StudentInformation> list = studentInformationService.lambdaQuery()
                .eq(StudentInformation::getStudentId, student_id).list();
        if(list.size()> 0){
            StudentInformation stuInfo = list.get(0);
            HashMap hashMap = new HashMap();
            hashMap.put("stuInfo",stuInfo);

            UniversityInformation univInfo = universityInformationService.lambdaQuery()
                    .eq(UniversityInformation::getUserId, stuInfo.getUniversityId()).list().get(0);
            hashMap.put("univInfo",univInfo);


            String job1 = jobTypeService.lambdaQuery().eq(JobType::getId, stuInfo.getJob1Id()).list().get(0).getName();
            String job2 = jobTypeService.lambdaQuery().eq(JobType::getId, stuInfo.getJob2Id()).list().get(0).getName();
            String job3 = jobTypeService.lambdaQuery().eq(JobType::getId, stuInfo.getJob3Id()).list().get(0).getName();

            String job = job1 +"、"+ job2 +"、"+ job3;
            hashMap.put("job",job);
            return Result.suc(hashMap);
        }

        return Result.fail();


    }

}
