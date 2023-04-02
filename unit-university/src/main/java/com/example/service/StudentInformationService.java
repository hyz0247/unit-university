package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.entity.StudentInformation;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-22
 */
public interface StudentInformationService extends IService<StudentInformation> {

    List<StudentInformation> selectStudentId(Wrapper wrapper);
}
