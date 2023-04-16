package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.JobType;
import com.example.mapper.JobTypeMapper;
import com.example.service.JobTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@Service
public class JobTypeServiceImpl extends ServiceImpl<JobTypeMapper, JobType> implements JobTypeService {

    @Autowired
    private JobTypeMapper jobTypeMapper;

    @Override
    public IPage<JobType> listPage(IPage<JobType> page, Wrapper wrapper) {
        return jobTypeMapper.listPage(page,wrapper);
    }
}
