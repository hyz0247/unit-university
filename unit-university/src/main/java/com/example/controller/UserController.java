package com.example.controller;


import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.UserMapper;
import com.example.service.*;
import com.example.utils.CheckCodeUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
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

    @Autowired
    private AdminInformationService adinS;

    @Autowired
    private StudentInformationService studentInformationService;

    @Autowired
    private UnitInformationService unitInformationService;

    @Autowired
    private UniversityInformationService universityInformationService;



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
            switch (user1.getRoleId()){
                case 0:
                    if(adinS.lambdaQuery().eq(AdminInformation::getUserId,user1.getId()).list().size()>0){
                        AdminInformation adminInformation = adinS.lambdaQuery().eq(AdminInformation::getUserId,user1.getId()).list().get(0);
                        result.put("personInfo",adminInformation);
                    }
                    break;
                case 1:
                    if(studentInformationService.lambdaQuery().eq(StudentInformation::getStudentId,user1.getId()).list().size()>0){
                        StudentInformation studentInformation = studentInformationService.lambdaQuery().eq(StudentInformation::getStudentId,user1.getId()).list().get(0);
                        result.put("personInfo",studentInformation);
                    }
                    break;
                case 2:
                    if(universityInformationService.lambdaQuery().eq(UniversityInformation::getUserId,user1.getId()).list().size()>0){
                        UniversityInformation universityInformation = universityInformationService.lambdaQuery().eq(UniversityInformation::getUserId,user1.getId()).list().get(0);
                        result.put("personInfo",universityInformation);
                    }
                    break;
                case 3:
                    if (unitInformationService.lambdaQuery().eq(UnitInformation::getUserId,user1.getId()).list().size()>0){
                        UnitInformation unitInformation = unitInformationService.lambdaQuery().eq(UnitInformation::getUserId,user1.getId()).list().get(0);
                        result.put("personInfo",unitInformation);
                    }
                    break;

            }
            result.put("user",user1);
            result.put("menu",menuList);
            return Result.suc(result);
        }
        return Result.fail();
    }

    /**修改密码*/
    @PostMapping("/modifyPwd")
    public Result modifyPwd(@RequestBody HashMap hashMap){

        System.out.println(hashMap);
        Integer id = (Integer)hashMap.get("id");
        String password = (String)hashMap.get("password");

        User user = userMapper.selectById(id);
        user.setPassword(password);

        userMapper.updateById(user);

        return Result.suc();
    }

    /**修改密码验证旧密码是否正确*/
    @GetMapping("/findPwd")
    public Result findPwd(@RequestParam String username,String pwd){

        List<User> list = userService.lambdaQuery().eq(User::getPassword, pwd)
                .eq(User::getUsername, username).list();

        return list.size()>0?Result.suc():Result.fail();
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


    /**更改头像*/
    @RequestMapping("/toUploadAvatar")
    public Result updateAvatar(MultipartFile file){
        //判断文件类型
        String pType=file.getContentType();
        pType=pType.substring(pType.indexOf("/")+1);

        if("jpeg".equals(pType)){
            pType="jpg";
        }

        long time=System.currentTimeMillis();
        //这里我采用绝对路径
        String path="D:/workspace/unit-un-demo/unit-university/src/main/resources/static/images/avatar"+time+"."+pType;
        String url = "http://localhost:8082/"+path.substring(path.indexOf("images/"));

        try{
            file.transferTo(new File(path));
            //文件路径保存到数据库中从而读取
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.uploadSuc(url);
    }
}
