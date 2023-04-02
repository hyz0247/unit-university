package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Collect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.Job;
import com.example.mapper.CollectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-28
 */
public interface CollectService extends IService<Collect> {

    IPage<Job> listCollect(IPage<Job> page, Wrapper wrapper);

}
