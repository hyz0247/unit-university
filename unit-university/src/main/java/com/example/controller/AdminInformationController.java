package com.example.controller;


import com.example.commmon.Result;
import com.example.entity.AdminInformation;
import com.example.entity.UniversityInformation;
import com.example.mapper.AdminInformationMapper;
import com.example.service.AdminInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin-information")
public class AdminInformationController {
    @Autowired
    private AdminInformationMapper adminMapper;

    @Autowired
    private AdminInformationService adinS;

    /**
     * 修改管理员信息
     */
    @PostMapping("/modify")
    public Result modify(@RequestBody AdminInformation admin) {

        adinS.saveOrUpdate(admin);
        AdminInformation admin1 = adminMapper.selectById(admin.getId());
        return Result.suc(admin1);
    }

    @GetMapping("list")
    public Result list(){
        List<AdminInformation> list = adinS.list();
        return Result.suc(list);
    }

    /**
     * 删除
     */
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id) {
        return adinS.removeById(id) ? Result.suc() : Result.fail();
    }
}
