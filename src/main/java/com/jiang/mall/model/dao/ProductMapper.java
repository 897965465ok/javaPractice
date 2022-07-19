package com.jiang.mall.model.dao;

import com.jiang.mall.model.pojo.Product;
import com.jiang.mall.model.query.ProductListQuery;
import com.jiang.mall.model.request.ProductListReq;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);

    Product selectByName(String name);

    int batchUpdateSellStatus (@Param("ids") Integer[] ids, @Param("sellStatus") Integer sellStatus);

    List<Product> selectListListForAdmin();

    List<Product> selectListList( @Param("query") ProductListQuery query);

}