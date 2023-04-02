package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Collect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.Job;
import com.example.entity.User;
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
 * @since 2023-03-28
 */
@Mapper
public interface CollectMapper extends BaseMapper<Collect> {


    IPage<Job> listCollect(IPage<Job> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
