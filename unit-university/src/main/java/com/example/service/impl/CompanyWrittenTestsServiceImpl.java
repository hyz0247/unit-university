package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.CompanyWrittenTests;
import com.example.mapper.CompanyWrittenTestsMapper;
import com.example.service.CompanyWrittenTestsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
@Service
public class CompanyWrittenTestsServiceImpl extends ServiceImpl<CompanyWrittenTestsMapper, CompanyWrittenTests> implements CompanyWrittenTestsService {

    @Autowired
    private CompanyWrittenTestsMapper companyWrittenTestsMapper;

    @Override
    public IPage pageQuestion(IPage<CompanyWrittenTests> page, Wrapper wrapper) {
        return companyWrittenTestsMapper.pageQuestion(page,wrapper);
    }
}
