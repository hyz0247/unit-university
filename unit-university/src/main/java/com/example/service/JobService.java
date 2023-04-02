package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.mapper.JobMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
public interface JobService extends IService<Job> {

    IPage pageList(IPage<Job> page,Wrapper wrapper);
}
