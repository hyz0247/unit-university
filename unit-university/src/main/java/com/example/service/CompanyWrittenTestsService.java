package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.CompanyWrittenTests;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
public interface CompanyWrittenTestsService extends IService<CompanyWrittenTests> {

    IPage pageQuestion(IPage<CompanyWrittenTests> page, Wrapper wrapper);
}
