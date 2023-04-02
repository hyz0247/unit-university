package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.ApprovalRecord;
import com.example.mapper.ApprovalRecordMapper;
import com.example.service.ApprovalRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-28
 */
@Service
public class ApprovalRecordServiceImpl extends ServiceImpl<ApprovalRecordMapper, ApprovalRecord> implements ApprovalRecordService {


    @Autowired
    private ApprovalRecordMapper approvalRecordMapper;

    @Override
    public IPage<ApprovalRecord> pageRecord(IPage<ApprovalRecord> page, Wrapper wrapper) {
        return approvalRecordMapper.pageRecord(page,wrapper);
    }
}
