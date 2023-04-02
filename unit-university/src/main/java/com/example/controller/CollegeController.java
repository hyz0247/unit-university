package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.College;
import com.example.entity.UniversityInformation;
import com.example.service.CollegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-03-25
 */
@RestController
@RequestMapping("/college")
public class CollegeController {

    @Autowired
    private CollegeService collegeService;

    /** 根据id查询*/
    @GetMapping("listById")
    public Result listById(@RequestParam Integer user_id){
        List<College> list = collegeService.lambdaQuery()
                .eq(College::getUserId, user_id).list();
        if(list.size()> 0){
            College collegeInfo = list.get(0);
            HashMap hashMap = new HashMap();
            hashMap.put("collegeInfo",collegeInfo);

            return Result.suc(hashMap);
        }

        return Result.fail();


    }

}
