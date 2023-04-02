package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.example.entity.StudentInformation;
import com.example.mapper.StudentInformationMapper;
import com.example.service.StudentInformationService;
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
 * @since 2023-03-22
 */
@Service
public class StudentInformationServiceImpl extends ServiceImpl<StudentInformationMapper, StudentInformation> implements StudentInformationService {

    @Autowired
    private StudentInformationMapper studentInformationMapper;

    @Override
    public List<StudentInformation> selectStudentId(Wrapper wrapper) {
        return studentInformationMapper.selectStudentId(wrapper);
    }
}
