package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Job;
import com.example.entity.JobType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@Mapper
public interface JobTypeMapper extends BaseMapper<JobType> {

    IPage<JobType> listPage(IPage<JobType> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
