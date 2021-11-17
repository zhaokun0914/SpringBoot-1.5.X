package com.fortunebill.springboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author Kavin
 * @date 2021年11月17日11:25:27
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * 配置应用程序。
     * 通常您需要做的就是添加源（例如配置类），因为其他设置具有合理地默认值。
     * 您可以选择（例如）添加默认命令行参数，或设置活动的 Spring 配置文件。
     *
     * @param application – 应用程序上下文的构建器
     * @return 应用程序构建器
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBoot04JspApplication.class);
    }

}
