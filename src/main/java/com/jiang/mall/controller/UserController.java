package com.jiang.mall.controller;

import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.common.Constant;
import com.jiang.mall.exception.MallException;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.pojo.User;
import com.jiang.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 描述：用户控制器
 */
@Controller
@ResponseBody
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/test")
    public User personalPage() {
        return userService.getUser(2);
    }

    /**
     * 注册
     * @param userName
     * @param password
     * @return
     * @throws MallException
     */
    @PostMapping("/register")
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password") String password) throws MallException {
        System.out.println(StringUtils.hasLength(userName));

        if (!StringUtils.hasLength(userName)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_USER_NAME);
        }
        if (!StringUtils.hasLength(password)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_PASSWORD);
        }
        if (password.length() < 8) {
            return ApiRestResponse.error(MallExceptionEunm.PASSWORD_TO_SHORT);
        }
        userService.register(userName, password);
        return ApiRestResponse.success();

    }

    /**
     * 登录
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session) throws MallException {
        if (!StringUtils.hasLength(userName)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_USER_NAME);
        }
        if (!StringUtils.hasLength(password)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        user.setPassword(null);
        // 设置session
        session.setAttribute(Constant.MALL_USER, user);

        return ApiRestResponse.success(user);
    }

    /**
     * 更新签名
     * @param session
     * @param signature
     * @return
     * @throws MallException
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse updateUserInfo(HttpSession session, @RequestParam String signature) throws MallException {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);

        System.out.println(currentUser);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateInformation(user);
        return ApiRestResponse.success();
    }

    /**
     * 登出 清楚session
     *
     * @param session
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登录
     * @param userName
     * @param password
     * @param session
     * @return
     * @throws MallException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            HttpSession session) throws MallException {
        if (!StringUtils.hasLength(userName)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_USER_NAME);
        }
        if (!StringUtils.hasLength(password)) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        if (userService.checkAdminRole(user)) {
            user.setPassword(null);
            // 设置session
            session.setAttribute(Constant.MALL_USER, user);
            return ApiRestResponse.success(user);
        } else {
            return ApiRestResponse.error(MallExceptionEunm.NEED_ADMIN);
        }


    }

}
