package com.jiang.mall.service;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.model.pojo.Category;
import com.jiang.mall.model.request.AddCategoryReq;
import com.jiang.mall.model.vo.CategoryVO;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

/*
 * 描述： 分类目录Service
 */
public interface CategoryService {
    void  add(AddCategoryReq addCategoryReq);

    void update(Category updatCategory);

    void  delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}
