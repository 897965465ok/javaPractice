package com.jiang.mall.service;

import com.github.pagehelper.PageInfo;
import com.jiang.mall.model.pojo.Category;
import com.jiang.mall.model.pojo.Product;
import com.jiang.mall.model.request.AddCategoryReq;
import com.jiang.mall.model.request.AddProductReq;
import com.jiang.mall.model.request.ProductListReq;
import com.jiang.mall.model.vo.CategoryVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/*
 * 描述： 商品Service
 */
public interface ProductService {

    void  add(AddProductReq addProductReq);

    void  update(Product updateProduct);

    void  delete(Integer id);

    void batchUpdateSellStatus(@RequestParam Integer[] ids, @RequestParam Integer sellStatus);

    PageInfo  listForAdmin(Integer pageNum, Integer pageSize);

    Product detail(Integer id);

    PageInfo list(ProductListReq productListReq);
}
