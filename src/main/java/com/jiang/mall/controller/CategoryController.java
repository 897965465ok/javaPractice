package com.jiang.mall.controller;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.common.Constant;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.pojo.Category;
import com.jiang.mall.model.pojo.User;
import com.jiang.mall.model.request.AddCategoryReq;
import com.jiang.mall.model.request.UpdateCategoryReq;
import com.jiang.mall.model.vo.CategoryVO;
import com.jiang.mall.service.CategoryService;
import com.jiang.mall.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/*
 * 目录Contrller
 */
@Controller
public class CategoryController {
    @Autowired
    UserService userService;
    @Autowired
    CategoryService categoryService;

    /**
     * 后台添加目录
     *
     * @param session
     * @param addCategoryReq
     * @return
     */
    @ApiOperation("后台添加目录")
    @PostMapping("admin/category/add")
    @ResponseBody
    public ApiRestResponse addCategory(HttpSession session, @Valid @RequestBody AddCategoryReq addCategoryReq) {
        User currentUser = (User) session.getAttribute(Constant.MALL_USER);
        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_LOGIN);
        }
        // 校验是否管理员
        boolean admin = userService.checkAdminRole(currentUser);
        if (admin) {
            categoryService.add(addCategoryReq);
            return ApiRestResponse.success();
        }
        {
            return ApiRestResponse.error(MallExceptionEunm.NEED_ADMIN);
        }

    }

    @ApiOperation("后台更新目录")
    @PostMapping("admin/category/update")
    @ResponseBody
    public ApiRestResponse updateCategory(@Valid @RequestBody UpdateCategoryReq updateCategoryReq, HttpSession session) {

        User currentUser = (User) session.getAttribute(Constant.MALL_USER);

        if (currentUser == null) {
            return ApiRestResponse.error(MallExceptionEunm.NEED_LOGIN);
        }
        // 校验是否管理员
        boolean admin = userService.checkAdminRole(currentUser);
        if (admin) {
            Category category = new Category();
            BeanUtils.copyProperties(updateCategoryReq, category);
            categoryService.update(category);
            return ApiRestResponse.success();
        }
        {
            return ApiRestResponse.error(MallExceptionEunm.NEED_ADMIN);
        }

    }

    @ApiOperation("删除目录")
    @PostMapping("admin/category/delete")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id) {
        categoryService.delete(id);
        return ApiRestResponse.success();
    }

    @ApiOperation("后台目录列表")
    @GetMapping("admin/category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {

        PageInfo pages = categoryService.listForAdmin(pageNum, pageSize);

        return ApiRestResponse.success(pages);
    }

    @ApiOperation("前台目录列表")
    @GetMapping("category/list")
    @ResponseBody
    public ApiRestResponse listCategoryForCustomer() {

        List<CategoryVO> categoryVOS = categoryService.listCategoryForCustomer(0);

        return ApiRestResponse.success(categoryVOS);
    }

}
