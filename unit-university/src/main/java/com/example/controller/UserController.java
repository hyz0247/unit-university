package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.*;
import com.example.mapper.*;
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
import java.util.ArrayList;
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
                    }else result.put("personInfo",new HashMap<>());
                    break;
                case 1:
                    if(studentInformationService.lambdaQuery().eq(StudentInformation::getStudentId,user1.getId()).list().size()>0){
                        StudentInformation studentInformation = studentInformationService.lambdaQuery().eq(StudentInformation::getStudentId,user1.getId()).list().get(0);
                        result.put("personInfo",studentInformation);
                    }else result.put("personInfo",new HashMap<>());
                    break;
                case 2:
                    if(universityInformationService.lambdaQuery().eq(UniversityInformation::getUserId,user1.getId()).list().size()>0){
                        UniversityInformation universityInformation = universityInformationService.lambdaQuery().eq(UniversityInformation::getUserId,user1.getId()).list().get(0);
                        result.put("personInfo",universityInformation);
                    }else result.put("personInfo",new HashMap<>());
                    break;
                case 3:
                    if (unitInformationService.lambdaQuery().eq(UnitInformation::getUserId,user1.getId()).list().size()>0){
                        UnitInformation unitInformation = unitInformationService.lambdaQuery().eq(UnitInformation::getUserId,user1.getId()).list().get(0);
                        result.put("personInfo",unitInformation);
                    }else result.put("personInfo",new HashMap<>());
                    break;
                default:result.put("personInfo",new HashMap<>());

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

    /** 查询用户名是否存在*/
    @GetMapping("/findByUsername")
    public Result findByUsername(@RequestParam String username){

        List list = userService.lambdaQuery().eq(User::getUsername, username).list();
        return list.size()>0?Result.suc(list):Result.fail();
    }

    /**学生分页查找*/
    @PostMapping("/listPageStu")
    public Result listPageStu(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String gender = (String)hashMap.get("gender");
        String status = (String)hashMap.get("status");
        String roleId = (String)hashMap.get("roleId");
        String university = (String)hashMap.get("university");


        IPage<User> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(roleId)){
            userQueryWrapper.eq(User::getRoleId,roleId);
        }
        if(StringUtils.isNotBlank(status)){
            userQueryWrapper.eq(User::getRoleId,status);
        }

//        if((StringUtils.isNotBlank(name) && !"null".equals(name))||(StringUtils.isNotBlank(gender) && !"null".equals(gender))){
//            List<StudentInformation> stuList = studentInformationService.selectStudentId(studentQueryWrapper);
//            Integer[] a = new Integer[stuList.size()+1] ;
//            String s = new String();
//            for(StudentInformation stu: stuList){
//                int i=0;
//                a[i] = stu.getStudentId();
//
//            }
//            for (int i=1;i<a.length;i++){
//                s += a[i-1];
//                if(a[i]!=null){
//                    s += ",";
//                }
//            }
//            userQueryWrapper.apply("user.id in ("+s+")");
//        }
        if((StringUtils.isNotBlank(name) && !"null".equals(name))&&(StringUtils.isNotBlank(gender) && !"null".equals(gender))&&(StringUtils.isNotBlank(university) && !"null".equals(university))){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where" +
                    " name like '%"+name+"%' and gender = '"+gender+"' and university_id = "+university+")");
        }else if((StringUtils.isNotBlank(name) && !"null".equals(name))&&(StringUtils.isNotBlank(gender) && !"null".equals(gender))){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where" +
                    " name like '%"+name+"%' and gender = '"+gender+"'");
        }else if((StringUtils.isNotBlank(name) && !"null".equals(name))&&(StringUtils.isNotBlank(university) && !"null".equals(university))){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where" +
                    " name like '%"+name+"%' and university_id = "+university+"");
        }else if((StringUtils.isNotBlank(gender) && !"null".equals(gender))&&(StringUtils.isNotBlank(university) && !"null".equals(university))){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where" +
                    " gender = '"+gender+"' and university_id = "+university+"");
        }else if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where name like '%"+name+"%')");
        }else if(StringUtils.isNotBlank(gender) && !"null".equals(gender)){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where gender = '"+gender+"')");
        }else if(StringUtils.isNotBlank(university) && !"null".equals(university)){
            userQueryWrapper.apply("user.id in ( select student_id from student_information where university_id = '"+university+"')");
        }

        IPage result = userService.pageStu(page,userQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

    /**学校分页查找*/
    @PostMapping("/listPageUniver")
    public Result listPageUinver(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String status = (String)hashMap.get("status");
        String roleId = (String)hashMap.get("roleId");
        String address = (String)hashMap.get("address");

        IPage<User> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(roleId)){
            userQueryWrapper.eq(User::getRoleId,roleId);
        }
        if(StringUtils.isNotBlank(status)){
            userQueryWrapper.eq(User::getRoleId,status);
        }
        userQueryWrapper.isNull(User::getAffiliation);

        if((StringUtils.isNotBlank(name) && !"null".equals(name))&&(StringUtils.isNotBlank(address) && !"null".equals(address))){
            userQueryWrapper.apply("user.id in ( select user_id from university_information where name like '%"+name+"%' and address like '%"+address+"%')");
        }else if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            userQueryWrapper.apply("user.id in ( select user_id from university_information where name like '%"+name+"%')");
        }else if(StringUtils.isNotBlank(address) && !"null".equals(address)){
            userQueryWrapper.apply("user.id in ( select user_id from university_information where address like '%"+address+"%')");
        }

        IPage result = userService.pageUniver(page,userQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

    /**单位分页查找*/
    @PostMapping("/listPageUnit")
    public Result listPageUnit(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String name = (String)hashMap.get("name");
        String status = (String)hashMap.get("status");
        String roleId = (String)hashMap.get("roleId");
        String address = (String)hashMap.get("address");

        IPage<User> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();

        if(StringUtils.isNotBlank(roleId)&&!"null".equals(roleId)){
            userQueryWrapper.eq(User::getRoleId,roleId);
        }
        if(StringUtils.isNotBlank(status)&&!"null".equals(status)){
            userQueryWrapper.eq(User::getRoleId,status);
        }
        userQueryWrapper.isNull(User::getAffiliation);

        if((StringUtils.isNotBlank(name) && !"null".equals(name))&&(StringUtils.isNotBlank(address) && !"null".equals(address))){
            userQueryWrapper.apply("user.id in ( select user_id from unit_information where name like '%"+name+"%' and address like '%"+address+"%')");
        }else if(StringUtils.isNotBlank(name) && !"null".equals(name)){
            userQueryWrapper.apply("user.id in ( select user_id from unit_information where name like '%"+name+"%')");
        }else if(StringUtils.isNotBlank(address) && !"null".equals(address)){
            userQueryWrapper.apply("user.id in ( select user_id from unit_information where address like '%"+address+"%')");
        }

        IPage result = userService.pageUnit(page,userQueryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }


    /** 根据id查询*/
    @GetMapping("listCollege")
    public Result listCollege(@RequestParam Integer id){
        List<User> users = userService.listCollege(id);
        if(users.size()> 0){
            return Result.suc(users);
        }

        return Result.fail();

    }

    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return userService.removeById(id)?Result.suc():Result.fail();
    }

    /** 根据ID查用户信息*/
    @GetMapping("/listById")
    public Result listById(@RequestParam Integer userId){
        User user = userMapper.selectById(userId);
        if (user.getRoleId().equals(0)){
            List<AdminInformation> list = adinS.list();
            return Result.suc(list);
        }
        if (user.getRoleId().equals(1)){
            List<StudentInformation> list = studentInformationService.list();
            return Result.suc(list);
        }
        if (user.getRoleId().equals(2)){
            List<UniversityInformation> list = universityInformationService.list();
            return Result.suc(list);
        }
        if (user.getRoleId().equals(3)){
            List<UnitInformation> list = unitInformationService.list();
            return Result.suc(list);
        }
        return Result.fail();
    }

    /** 更新*/
    @PostMapping("/update")
    public Result update(@RequestBody User user){
        return userService.updateById(user)?Result.suc():Result.fail();
    }

    /** 注册或添加*/
    @PostMapping("/save")
    public Result save(@RequestBody User user){
        return userService.save(user)?Result.suc():Result.fail();
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

        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.uploadSuc(url);
    }

    /**简历文件上传*/
    @RequestMapping("/toUploadFile")
    public Result toUploadFile(MultipartFile file){

        long time=System.currentTimeMillis();
        //这里我采用绝对路径
        String path="D:/workspace/unit-un-demo/unit-university/src/main/resources/static/resumeFile/resume"+time+"."+"docx";
        //String path="D:/workspace/unit-un-demo/unit-university/src/main/resources/static/resumeFile/file"+time+"."+"pdf";
        String url = "http://localhost:8082/"+path.substring(path.indexOf("resumeFile/"));
        try{
            file.transferTo(new File(path));

        }catch (Exception e){
            e.printStackTrace();
        }

        return Result.uploadSuc(url);
    }
}
