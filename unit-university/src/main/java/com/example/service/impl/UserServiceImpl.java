package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.entity.User;
import com.example.entity.UserList;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
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

    @Override
    public BarVO listRoleId() {

        BarVO barVO = new BarVO();
        List<String> names = new ArrayList<>();
        List<DataVO> values = new ArrayList<>();
        List<UserList> userLists = userMapper.listRoleId();
        for (UserList userList:userLists){
            names.add(userList.getRoleName());
            DataVO dataVO = new DataVO();
            dataVO.setValue(userList.getCount());
            dataVO.setItemStyle(DataUtil.createItemStyle(userList.getCount()));
            values.add(dataVO);
        }
        barVO.setNames(names);
        barVO.setValues(values);

        return barVO;
    }

    @Override
    public List<PieVO> listRoleIdPie() {

        List<PieVO> pieVOList = new ArrayList<>();
        List<UserList> userLists = userMapper.listRoleId();

        for (UserList userList:userLists){
            PieVO pieVO = new PieVO();
            pieVO.setName(userList.getRoleName());
            pieVO.setValue(userList.getCount());
            pieVO.setItemStyle(DataUtil.createItemStyle(userList.getCount()));
            pieVOList.add(pieVO);
        }

        return pieVOList;
    }


}
