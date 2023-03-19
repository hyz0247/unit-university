package com.example.controller;


import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Menu;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.MenuService;
import com.example.service.UserService;
import com.example.utils.CheckCodeUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-03-14
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MenuService menuService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    private UserMapper userMapper;



    /** 登录*/
    @PostMapping("/login")
    public Result login(@RequestBody User user){

        List list = userService.lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword,user.getPassword()).list();
        if(list.size()>0){
            User user1 = (User)list.get(0);
            List menuList = menuService.lambdaQuery().like(Menu::getMenuRight, user1.getRoleId()).list();
            HashMap result = new HashMap();
            result.put("user",user1);
            result.put("menu",menuList);
            return Result.suc(result);
        }
        return Result.fail();
    }





    /**更改验证码图片*/
    @RequestMapping("/newCode")
    public void newCode(HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();

        //生成验证码
        ServletOutputStream os = response.getOutputStream();
        String checkCode = CheckCodeUtil.outputVerifyImage(90, 40, os, 4);

        //存入Session
        session.setAttribute("checkCodeGen",checkCode);

    }


    /**检验验证码*/
    @RequestMapping ("/checkCode")
    public Result checkCode(@RequestBody QueryPageParam param){

        String checkCode = param.getCheckCode();

        HttpSession session = request.getSession();
        String checkCodeGen = (String)session.getAttribute("checkCodeGen");

        if(checkCodeGen.equalsIgnoreCase(checkCode)){
            return Result.suc();
        }
        return Result.fail();
    }
}
