package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-14
 */
public interface UserService extends IService<User> {


    IPage pageStu(IPage<User> page, Wrapper wrapper);

    IPage pageUniver(IPage<User> page, Wrapper wrapper);

    IPage pageUnit(IPage<User> page, Wrapper wrapper);

    List<User> listCollege(Integer affiliation);

}
