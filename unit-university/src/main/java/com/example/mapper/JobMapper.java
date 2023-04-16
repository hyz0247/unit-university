package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.entity.QuarterList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {

    IPage<Job> pageList(IPage<Job> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    List<QuarterList> quarterList(@Param(Constants.WRAPPER) Wrapper wrapper);
}
