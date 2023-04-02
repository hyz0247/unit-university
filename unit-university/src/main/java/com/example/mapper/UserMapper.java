package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-03-14
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


    IPage<User> pageStu(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<User> pageUniver(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    IPage<User> pageUnit(IPage<User> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    List<User> listCollege(Integer affiliation);
}
