package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.UniversityInformationMapper;
import com.example.service.UniversityInformationService;
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
@RequestMapping("/university-information")
public class UniversityInformationController {

    @Autowired
    private UniversityInformationMapper universityMapper;

    @Autowired
    private UniversityInformationService universityInformationService;

    /** 修改学校信息*/
    @PostMapping("/modify")
    public Result modify(@RequestBody UniversityInformation university){

        universityInformationService.saveOrUpdate(university);
        UniversityInformation university1 = universityMapper.selectById(university.getId());
        return Result.suc(university1);
    }

    @GetMapping("list")
    public Result list(){
        List<UniversityInformation> list = universityInformationService.list();
        return Result.suc(list);
    }

    /** 根据id查询*/
    @GetMapping("listById")
    public Result listById(@RequestParam Integer user_id){
        List<UniversityInformation> list = universityInformationService.lambdaQuery()
                .eq(UniversityInformation::getUserId, user_id).list();
        if(list.size()> 0){
            UniversityInformation univerInfo = list.get(0);
            HashMap hashMap = new HashMap();
            hashMap.put("univerInfo",univerInfo);

            return Result.suc(hashMap);
        }

        return Result.fail();


    }

}
