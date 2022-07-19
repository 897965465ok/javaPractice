package com.jiang.mall.model.query;

import java.util.List;

/**
 * 查询商品列表
 * 为什么建立这样一个查询？
 *
 */
public class ProductListQuery {
    private  String keyword;
    private List<Integer> categoryIds;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<Integer> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<Integer> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
