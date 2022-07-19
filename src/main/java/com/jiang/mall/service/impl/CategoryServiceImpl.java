package com.jiang.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.mall.exception.MallException;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.dao.CategoryMapper;
import com.jiang.mall.model.pojo.Category;
import com.jiang.mall.model.request.AddCategoryReq;
import com.jiang.mall.model.vo.CategoryVO;
import com.jiang.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/*
 * 描述： 目录分类Service实现类
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new MallException(MallExceptionEunm.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new MallException(MallExceptionEunm.CREATE_FAILED);

        }
    }

    @Override
    public void update(Category updatCategory) {
        if (updatCategory.getName() != null) {

            Category categoryOld = categoryMapper.selectByName(updatCategory.getName());

            if (categoryOld != null && !categoryOld.getId().equals(updatCategory.getId())) {
                throw new MallException(MallExceptionEunm.NAME_EXISTED);
            }
            int count = categoryMapper.updateByPrimaryKeySelective(updatCategory);
            if (count == 0) {
                throw new MallException(MallExceptionEunm.UPDATE_FAILED);
            }
        }

    }

    @Override
    public void delete(Integer id) {

        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if (categoryOld == null) {
            throw new MallException(MallExceptionEunm.DELETE_FAILED);
        }

        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEunm.DELETE_FAILED);
        }

    }


    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        // DESC表示降序
        PageHelper.startPage(pageNum, pageSize, "id,order_num");

        List<Category> categories = categoryMapper.selectList();
        PageInfo pageinfo = new PageInfo<>(categories);
        return pageinfo;
    }

    @Override
    @Cacheable(value = "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFindCategories(categoryVOList, parentId);
        return categoryVOList;
    }

    private void recursivelyFindCategories(List<CategoryVO> categoryVOList, Integer parentId) {
        List<Category> categoryList = categoryMapper.selectcategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category, categoryVO);
                categoryVOList.add(categoryVO);

                recursivelyFindCategories(categoryVO.getChildrenCategory(), categoryVO.getId());
            }
        }

    }

}
