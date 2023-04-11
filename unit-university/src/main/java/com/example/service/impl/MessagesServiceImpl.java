package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Messages;
import com.example.mapper.MessagesMapper;
import com.example.service.MessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-04-05
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements MessagesService {

    @Autowired
    private MessagesMapper messagesMapper;


    @Override
    public IPage<Messages> pageMessages(IPage<Messages> page, Wrapper wrapper) {
        return messagesMapper.pageMessages(page,wrapper);
    }
}
