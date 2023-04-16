package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.UnitInformation;
import com.example.entity.UniversityInformation;
import com.example.mapper.UnitInformationMapper;
import com.example.service.UnitInformationService;
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
@RequestMapping("/unit-information")
public class UnitInformationController {

    @Autowired
    private UnitInformationMapper unitMapper;

    @Autowired
    private UnitInformationService unitInformationService;

    /** 修改单位信息*/
    @PostMapping("/modify")
    public Result modify(@RequestBody UnitInformation unit){

        unitInformationService.saveOrUpdate(unit);
        UnitInformation unit1 = unitMapper.selectById(unit.getId());
        return Result.suc(unit1);
    }

    @GetMapping("list")
    public Result list(){
        List<UnitInformation> list = unitInformationService.list();
        return Result.suc(list);
    }

    /** 根据id查询*/
    @GetMapping("listById")
    public Result listById(@RequestParam Integer user_id){
        List<UnitInformation> list = unitInformationService.lambdaQuery()
                .eq(UnitInformation::getUserId, user_id).list();
        if(list.size()> 0){
            UnitInformation unitInfo = list.get(0);
            HashMap hashMap = new HashMap();
            hashMap.put("unitInfo",unitInfo);

            return Result.suc(hashMap);
        }

        return Result.fail();

    }

    /** 查询除附属外的所有信息*/
    @GetMapping("listUnit")
    public Result listUnit(){
        List<UnitInformation> list = unitInformationService.listUnit();
        return Result.suc(list);
    }

}
