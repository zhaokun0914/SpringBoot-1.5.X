package com.fortunebill.springboot.config;

import com.fortunebill.springboot.filter.MyFilter;
import com.fortunebill.springboot.listener.MyListener;
import com.fortunebill.springboot.servlet.MyServlet;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.Arrays;

/**
 * @author Kavin
 * @date 2021年11月16日15:21:48
 */
@Configuration
public class MyServertConfig {

    /**
     * 自定义容器的配置
     */
    @Bean
    public EmbeddedServletContainerCustomizer embeddedServletContainerCustomizer() {
        return (ConfigurableEmbeddedServletContainer container) -> {
            // 定制嵌入式的Servlet容器相关规则
            container.setPort(8083);
        };
    }

    // 注册三大组件
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new MyServlet(), "/myServlet");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter(ServletRegistrationBean myServlet) {
        // FilterRegistrationBean registrationBean = new FilterRegistrationBean(new MyFilter(), myServlet);
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new MyFilter());
        registrationBean.setUrlPatterns(Arrays.asList("/myFilter","/hello"));
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<MyListener> myListener() {
        ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<>(new MyListener());
        return registrationBean;
    }


}