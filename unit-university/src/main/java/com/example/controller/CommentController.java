package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Comment;
import com.example.entity.CompanyWrittenTests;
import com.example.entity.Messages;
import com.example.entity.User;
import com.example.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /** 添加*/
    @PostMapping("/save")
    public Result save(@RequestBody Comment comment){
        return commentService.save(comment)?Result.suc():Result.fail();
    }



    /** 评论未读改为已读*/
    @GetMapping("read")
    public Result read(@RequestParam Integer userId){
        List<Comment> comments = commentService.unreadComment(userId);
        if (comments.size()>0){
            ArrayList<Integer> idList = new ArrayList<>();
            comments.forEach(comment -> idList.add(comment.getId()));
            UpdateWrapper<Comment> wrapper = new UpdateWrapper<>();
            wrapper.lambda().in(Comment::getId,idList)
                    .set(Comment::getStatus,1);
            commentService.update(wrapper);
            return Result.suc();
        }
        return Result.fail();

    }


    /** 删除*/
    @GetMapping("/deleteById")
    public Result delete(@RequestParam Integer id){
        return commentService.removeById(id)?Result.suc():Result.fail();
    }

    /** 分页查询*/
    @PostMapping("/listPage")
    public Result listPage(@RequestBody QueryPageParam param){

        HashMap hashMap = param.getParam();
        String title = (String)hashMap.get("title");
        String userId = (String)hashMap.get("userId");
        String roleId = (String)hashMap.get("roleId");

        IPage<Comment> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.apply("user.id=comment.user_id and comment.job_id=job.id");

        if (roleId.equals("3")){
            queryWrapper.apply("job.company_id = "+userId+"");
        }
        if(StringUtils.isNotBlank(title)&&!"null".equals(title)){
            queryWrapper.apply("and job.title like '%"+title+"%'");
        }

        IPage<Comment> result = commentService.pageComment(page, queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }

}
