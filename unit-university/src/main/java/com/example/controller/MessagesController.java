package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.commmon.QueryPageParam;
import com.example.commmon.Result;
import com.example.entity.Comment;
import com.example.entity.Messages;
import com.example.service.CommentService;
import com.example.service.MessagesService;
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
 * @since 2023-04-05
 */
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private CommentService commentService;


    /** 添加*/
    @PostMapping("/save")
    public Result save(@RequestBody Messages messages){
        return messagesService.save(messages)?Result.suc():Result.fail();
    }

    /** 未读的消息有几条*/
    @GetMapping("unread")
    public Result unread(@RequestParam Integer userId){
        HashMap hashMap = new HashMap<>();
        List<Messages> msg = messagesService.lambdaQuery()
                .eq(Messages::getReceiverId,userId)
                .eq(Messages::getStatus, "0").list();
        List<Messages> infoMsg = messagesService.lambdaQuery()
                .eq(Messages::getReceiverId,userId)
                .eq(Messages::getStatus, "0").eq(Messages::getType, "通知").list();
        List<Messages> myMsg = messagesService.lambdaQuery()
                .eq(Messages::getStatus, "0")
                .eq(Messages::getReceiverId,userId)
                .eq(Messages::getType, "私信").list();
        List<Comment> comments = commentService.unreadComment(userId);
        if (msg.size()>0){
            hashMap.put("msg",msg);
        }
        if (infoMsg.size()>0){
            hashMap.put("infoMsg",infoMsg);
        }
        if (myMsg.size()>0){
            hashMap.put("myMsg",myMsg);
        }
        if (comments.size()>0){
            hashMap.put("comments",comments);
        }
        return Result.suc(hashMap);
    }

    /** 未读改为已读*/
    @GetMapping("readMsg")
    public Result readMsg(@RequestParam List<String> idList){
        UpdateWrapper<Messages> wrapper = new UpdateWrapper<>();
        wrapper.lambda().in(Messages::getId,idList)
                .set(Messages::getStatus,1);

        return messagesService.update(wrapper)?Result.suc():Result.fail();

    }

    /** 通知未读改为已读*/
    @GetMapping("readInfo")
    public Result readInfo(@RequestParam Integer userId){
        UpdateWrapper<Messages> wrapper = new UpdateWrapper<>();
        List<Messages> messagesList = messagesService.lambdaQuery()
                .eq(Messages::getReceiverId,userId)
                .eq(Messages::getStatus, "0").eq(Messages::getType, "通知").list();

        if (messagesList.size()>0){
            ArrayList<Integer> idList = new ArrayList<>();
            messagesList.forEach(messages -> idList.add(messages.getId()));
            wrapper.lambda().in(Messages::getId,idList)
                    .set(Messages::getStatus,1);
            messagesService.update(wrapper);
            return Result.suc();
        }
        return Result.fail();
    }

    /** 私信未读改为已读*/
    @GetMapping("readPrivate")
    public Result readPrivate(@RequestParam Integer userId,Integer id){
        UpdateWrapper<Messages> wrapper = new UpdateWrapper<>();
        List<Messages> messagesList = messagesService.lambdaQuery()
                .eq(Messages::getReceiverId,userId)
                .eq(Messages::getSenderId,id)
                .eq(Messages::getStatus, "0")
                .eq(Messages::getType, "私信").list();

        if (messagesList.size()>0){
            ArrayList<Integer> idList = new ArrayList<>();
            messagesList.forEach(messages -> idList.add(messages.getId()));
            wrapper.lambda().in(Messages::getId,idList)
                    .set(Messages::getStatus,1);
            messagesService.update(wrapper);
            return Result.suc();
        }
        return Result.fail();
    }

    /** 分页查询*/
    @PostMapping("listPage")
    public Result list(@RequestBody QueryPageParam param){
        HashMap hashMap = param.getParam();
        String userId = (String)hashMap.get("userId");

        IPage<Messages> page = new Page<>() ;
        page.setCurrent(param.getPageNum());
        page.setSize(param.getPageSize());

        LambdaQueryWrapper<Messages> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Messages::getType, "通知").eq(Messages::getReceiverId, userId);

        queryWrapper.apply("messages.sender_id = user.id");

        IPage<Messages> result = messagesService.pageMessages(page, queryWrapper);

        return Result.suc(result.getRecords(),result.getTotal());
    }


}
