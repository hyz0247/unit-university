package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Job;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
public interface CommentService extends IService<Comment> {

    List<Comment> unreadComment(Integer userId);

    IPage<Comment> pageComment(IPage<Comment> page, Wrapper wrapper);
}
