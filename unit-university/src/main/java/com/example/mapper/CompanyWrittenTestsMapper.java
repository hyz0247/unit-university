package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.ApprovalRecord;
import com.example.entity.CompanyWrittenTests;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-04-04
 */
@Mapper
public interface CompanyWrittenTestsMapper extends BaseMapper<CompanyWrittenTests> {

    IPage<CompanyWrittenTests> pageQuestion(IPage<CompanyWrittenTests> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
