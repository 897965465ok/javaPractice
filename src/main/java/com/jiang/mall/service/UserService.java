package com.jiang.mall.service;

import com.jiang.mall.exception.MallException;
import com.jiang.mall.model.pojo.User;

/**
 * 描述： UserService
 */
public interface UserService {
    User getUser(Integer id);
    void  register(String userName,String password) throws MallException;

    User login(String userName, String password) throws MallException;

    // 更新个性签名
    void  updateInformation(User user) throws MallException;

    boolean checkAdminRole(User user);
}
