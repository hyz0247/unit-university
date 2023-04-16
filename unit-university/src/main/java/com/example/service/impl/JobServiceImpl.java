package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.Job;
import com.example.entity.QuarterList;
import com.example.mapper.JobMapper;
import com.example.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.DataUtil;
import com.example.vo.BarVO;
import com.example.vo.DataVO;
import com.example.vo.PieVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public IPage pageList(IPage<Job> page, Wrapper wrapper) {
        return jobMapper.pageList(page,wrapper);
    }

    @Override
    public BarVO quarterList(Wrapper wrapper) {
        BarVO barVO = new BarVO();
        List<String> names = new ArrayList<>();
        List<DataVO> values = new ArrayList<>();
        QuarterList quarterList = jobMapper.quarterList(wrapper).get(0);
        //System.out.println(quarterList);
        names.add("第一季度");
        names.add("第二季度");
        names.add("第三季度");
        names.add("第四季度");
        //System.out.println(names);

        DataVO dataVO1 = new DataVO();DataVO dataVO2 = new DataVO();
        DataVO dataVO3 = new DataVO();DataVO dataVO4 = new DataVO();

        dataVO1.setValue(quarterList.getFirstQuarter());
        dataVO1.setItemStyle(DataUtil.createItemStyle(quarterList.getFirstQuarter()));
        dataVO2.setValue(quarterList.getSecondQuarter());
        dataVO2.setItemStyle(DataUtil.createItemStyle(quarterList.getSecondQuarter()));
        dataVO3.setValue(quarterList.getThirdQuarter());
        dataVO3.setItemStyle(DataUtil.createItemStyle(quarterList.getThirdQuarter()));
        dataVO4.setValue(quarterList.getFourthQuarter());
        dataVO4.setItemStyle(DataUtil.createItemStyle(quarterList.getFourthQuarter()));

        values.add(dataVO1);values.add(dataVO2);
        values.add(dataVO3);values.add(dataVO4);
        //System.out.println(values);

        barVO.setNames(names);
        barVO.setValues(values);
        return barVO;
    }

    @Override
    public List<PieVO> quarterListPie(Wrapper wrapper) {
        List<PieVO> pieVOList = new ArrayList<>();
        QuarterList quarterList = jobMapper.quarterList(wrapper).get(0);

        PieVO pieVO1 = new PieVO();PieVO pieVO2 = new PieVO();
        PieVO pieVO3 = new PieVO();PieVO pieVO4 = new PieVO();

        pieVO1.setValue(quarterList.getFirstQuarter());
        pieVO2.setValue(quarterList.getSecondQuarter());
        pieVO3.setValue(quarterList.getThirdQuarter());
        pieVO4.setValue(quarterList.getFourthQuarter());

        pieVO1.setName("第一季度");
        pieVO2.setName("第二季度");
        pieVO3.setName("第三季度");
        pieVO4.setName("第四季度");

        pieVO1.setItemStyle(DataUtil.createItemStyle(quarterList.getFirstQuarter()));
        pieVO2.setItemStyle(DataUtil.createItemStyle(quarterList.getSecondQuarter()));
        pieVO3.setItemStyle(DataUtil.createItemStyle(quarterList.getThirdQuarter()));
        pieVO4.setItemStyle(DataUtil.createItemStyle(quarterList.getFourthQuarter()));

        pieVOList.add(pieVO1);
        pieVOList.add(pieVO2);
        pieVOList.add(pieVO3);
        pieVOList.add(pieVO4);

        return pieVOList;
    }
}
