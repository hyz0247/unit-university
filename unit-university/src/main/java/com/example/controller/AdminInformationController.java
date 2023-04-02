package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.AdminInformation;
import com.example.mapper.AdminInformationMapper;
import com.example.service.AdminInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-03-22
 */
@RestController
@RequestMapping("/admin-information")
public class AdminInformationController {
    @Autowired
    private AdminInformationMapper adminMapper;

    @Autowired
    private AdminInformationService adinS;

    /** 修改管理员信息*/
    @PostMapping("/modify")
    public Result modify(@RequestBody AdminInformation admin){

        adinS.saveOrUpdate(admin);
        AdminInformation admin1 = adminMapper.selectById(admin.getId());
        return Result.suc(admin1);
    }
}
