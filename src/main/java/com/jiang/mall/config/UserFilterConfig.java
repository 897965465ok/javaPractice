package com.jiang.mall.config;


import com.jiang.mall.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * user过滤器的配置
 * 需要两个方法
 */
@Configuration
public class UserFilterConfig {
    @Bean
    public UserFilter userFilter() {
        return new UserFilter();
    }
    @Bean(name =  "userFilterConf")
    public  FilterRegistrationBean userFilterConfig() {

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        // 设置adminFilter类作为过滤器
        filterRegistrationBean.setFilter(userFilter());
        filterRegistrationBean.addUrlPatterns("/cart/*");
        filterRegistrationBean.addUrlPatterns("/order/*");
        // 设置(过滤器配置名字) 区分不同的配置
        filterRegistrationBean.setName("userFilterConf");
        return filterRegistrationBean;
    }

}
