package com.jiang.mall.service.impl;

import com.jiang.mall.exception.MallException;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.dao.UserMapper;
import com.jiang.mall.model.pojo.User;
import com.jiang.mall.service.UserService;
import com.jiang.mall.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;


/**
 * UserService 实现类
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUser(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void register(String userName, String password) throws MallException {
        // 查询用户名是否存在
        User result = userMapper.selectByName(userName);
        if (result != null) {
            throw new MallException(MallExceptionEunm.NAME_EXISTED);
        } else {
            User user = new User();
            user.setUsername(userName);
            try {
                user.setPassword(MD5Utils.getMD5Str(password));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            int count = userMapper.insertSelective(user);
            if (count == 0) {
                throw new MallException(MallExceptionEunm.INSERT_FAILED);
            }
        }
    }

    @Override
    public User login(String userName, String password) throws MallException {
        String md5Password = null;
        try {
            md5Password = MD5Utils.getMD5Str(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        User user = userMapper.selectLogin(userName, md5Password);
        if (user == null) {

            throw new MallException(MallExceptionEunm.WRONG_PASSWORD);
        }
        return user;
    }

    // 更新个性签名
    @Override
    public void updateInformation(User user) throws MallException {
        int updateCount = userMapper.updateByPrimaryKeySelective(user);
        if (updateCount > 1) {
            throw new MallException(MallExceptionEunm.UPDATE_FAILED);
        }
    }

    @Override
    public  boolean checkAdminRole( User user){

        return  user.getRole().equals(2);

    }

}
