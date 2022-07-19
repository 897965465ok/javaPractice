package com.jiang.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiang.mall.common.ApiRestResponse;
import com.jiang.mall.common.Constant;
import com.jiang.mall.exception.MallException;
import com.jiang.mall.exception.MallExceptionEunm;
import com.jiang.mall.model.dao.ProductMapper;
import com.jiang.mall.model.pojo.Product;
import com.jiang.mall.model.query.ProductListQuery;
import com.jiang.mall.model.request.AddProductReq;
import com.jiang.mall.model.request.ProductListReq;
import com.jiang.mall.model.vo.CategoryVO;
import com.jiang.mall.service.CategoryService;
import com.jiang.mall.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述： 商品服务实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;

    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null) {
            throw new MallException(MallExceptionEunm.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        if (count == 0) {
            throw new MallException(MallExceptionEunm.INSERT_FAILED);
        }

    }

    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName(updateProduct.getName());
        if (productOld != null && !productOld.getId().equals(updateProduct.getId())) {
            throw new MallException(MallExceptionEunm.UPDATE_FAILED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count == 0) {
            throw new MallException(MallExceptionEunm.UPDATE_FAILED);
        }
    }

    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);
        if (productOld == null) {
            throw new MallException(MallExceptionEunm.DELETE_FAILED);
        }

        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MallException(MallExceptionEunm.DELETE_FAILED);
        }
    }

    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);

    }

    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListListForAdmin();
        PageInfo pageInfo = new PageInfo(products);

        return pageInfo;

    }

    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }


    @Override
    public PageInfo list(ProductListReq productListReq) {
        ProductListQuery productListQuery = new ProductListQuery();


        // 搜索处理
//        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
//            String keyword = new StringBuilder().
//                    append("%").
//                    append(productListReq.getKeyword()).
//                    append("%").
//                    toString();
//            productListQuery.setKeyword(keyword);
//        }

        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
            String keyword = new StringBuilder().append("%")
                    .append(productListReq.getKeyword())
                    .append("%").toString();
            productListQuery.setKeyword(keyword);
        }





        // 目录处理, 如果查询某个目录下的商品，不仅需要查出该目录下的，
        // 还需要把所有子目录的所有商品都查出来，所以要拿到一个目录ID的list

        if (productListReq.getCategoryId() != null) {
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(
                    productListReq.getCategoryId()
            );
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }
        // 排序处理
        String orderBy = productListReq.getOrderBy();

        if (Constant.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)){
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize(),orderBy);
        }else {
            PageHelper.startPage(productListReq.getPageNum(),productListReq.getPageSize());
        }

       List<Product> productList =  productMapper.selectListList(productListQuery);
        PageInfo pageInfo = new PageInfo(productList);
        return  pageInfo;

    }

    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null) {
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildrenCategory(), categoryIds);
            }


        }
    }


}
