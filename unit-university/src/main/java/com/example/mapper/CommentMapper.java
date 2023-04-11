package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    @Select("SELECT comment.* FROM comment,job WHERE comment.job_id = job.id and comment.status = '0' and job.company_id = #{userId}")
    List<Comment> unreadComment(Integer userId);

    IPage<Comment> pageComment(IPage<Comment> page, @Param(Constants.WRAPPER) Wrapper wrapper);

}
