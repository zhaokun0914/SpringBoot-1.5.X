package com.fortunebill.springboot.config;

import com.fortunebill.springboot.component.MyLocaleResolver;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Kavin
 * @date 2021年11月12日14:59:45
 */
//@EnableWebMvc
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/fortunebill").setViewName("success");
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
        registry.addViewController("/main.html").setViewName("dashboard");
    }

    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    //     registry.addInterceptor(new LoginHandlerInterceptor())
    //             // 该拦截器拦截所有请求
    //             .addPathPatterns("/**")
    //             // 排除以下请求
    //             .excludePathPatterns("/")
    //             .excludePathPatterns("/index.html")
    //             .excludePathPatterns("/user/login");
    // }

    @Bean
    public LocaleResolver localeResolver() {
        return new MyLocaleResolver();
    }
}

