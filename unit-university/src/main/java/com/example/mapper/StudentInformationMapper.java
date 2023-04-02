package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.example.entity.StudentInformation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hyz02
 * @since 2023-03-22
 */
@Mapper
public interface StudentInformationMapper extends BaseMapper<StudentInformation> {


    List<StudentInformation> selectStudentId(@Param(Constants.WRAPPER) Wrapper wrapper);

}
