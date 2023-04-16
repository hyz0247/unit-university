package com.example.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.entity.QuarterList;
import com.example.vo.BarVO;
import com.example.vo.PieVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
public interface JobService extends IService<Job> {

    IPage pageList(IPage<Job> page,Wrapper wrapper);

    BarVO quarterList(Wrapper wrapper);

    List<PieVO> quarterListPie(Wrapper wrapper);
}
