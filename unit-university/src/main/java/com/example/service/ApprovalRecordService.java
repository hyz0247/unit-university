package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.ApprovalRecord;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-28
 */
public interface ApprovalRecordService extends IService<ApprovalRecord> {

    IPage<ApprovalRecord> pageRecord(IPage<ApprovalRecord> page, Wrapper wrapper);
}
