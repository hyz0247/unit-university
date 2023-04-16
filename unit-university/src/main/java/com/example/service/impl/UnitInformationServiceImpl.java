package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.UnitInformation;
import com.example.mapper.UnitInformationMapper;
import com.example.service.UnitInformationService;
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
public class UnitInformationServiceImpl extends ServiceImpl<UnitInformationMapper, UnitInformation> implements UnitInformationService {

    @Autowired
    private UnitInformationMapper unitInformationMapper;

    @Override
    public List<UnitInformation> listUnit() {
        return unitInformationMapper.listUnit();
    }
}
