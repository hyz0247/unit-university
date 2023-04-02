package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public IPage pageStu(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageStu(page,wrapper);
    }

    @Override
    public IPage pageUniver(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageUniver(page,wrapper);
    }

    @Override
    public IPage pageUnit(IPage<User> page, Wrapper wrapper) {
        return userMapper.pageUnit(page,wrapper);
    }

    @Override
    public List<User> listCollege(Integer affiliation) {
        return userMapper.listCollege(affiliation);
    }


}
