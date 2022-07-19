package com.jiang.mall.config;

import com.jiang.mall.filter.AdminFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Admin过滤器的配置
 * 需要两个方法
 */
@Configuration
public class AdminFilterConfig {

    @Bean
    public AdminFilter adminFilter() {

        return new AdminFilter();

    }
    @Bean(name =  "adminFilterConf")
    public  FilterRegistrationBean adminFilterConfig() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 设置adminFilter类作为过滤器
        filterRegistrationBean.setFilter(adminFilter());
        filterRegistrationBean.addUrlPatterns("/admin/category/*");
        filterRegistrationBean.addUrlPatterns("/admin/product/*");
        filterRegistrationBean.addUrlPatterns("/admin/order/*");
        // 设置(过滤器配置名字) 区分不同的配置
        filterRegistrationBean.setName("adminFilterConf");
        return filterRegistrationBean;
    }


}
