package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Comment;
import com.example.entity.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-04-05
 */
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

    IPage<Messages> pageMessages(IPage<Messages> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
