package com.fortunebill.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * @Target(ElementType.TYPE)
 * @Retention(RetentionPolicy.RUNTIME)
 * @Documented
 * @Inherited
 * @SpringBootConfiguration
 * @EnableAutoConfiguration
 * @ComponentScan(excludeFilters = {
 *                @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
 *                @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
 * public @interface SpringBootApplication {
 *
 * @SpringBootConfiguration: Spring Boot的配置类
 *     标注在某个类上，表示这是一个Spring Boot的配置类：
 *     @Configuration 配置类上来标注这个注解：
 *         配置类 ——> 配置文件：配置类也是容器中的一个组件
 *
 * @EnableAutoConfiguration: 开启自动配置功能
 *     以前我们需要配置的东西，Spring Boot帮我们自动配置；@EnableAutoConfiguration告诉Spring Boot开启自动配置功能；这样自动配置才能生效
 *
 *     @AutoConfigurationPackage: 自动配置包
 *         @Import(AutoConfigurationPackages.Registrar.class): Spring的底层注解@Import，给容器中导入一个组件;
 *             导入的组件由AutoConfigurationPackages.Registrar.class指定，将主配置类(@SpringBootConfiguration)的所在包及下面所有子包里面的所有组件扫描扫Spring容器；
 *
 *     @Import(EnableAutoConfigurationImportSelector.class)
 *         给容器中导入组件
 *         EnableAutoConfigurationImportSelector: 这个类就是导入那些组件的选择器；
 *         将所有需要导入的组件以全类名的方式返回；这些组件就会被添加到容器中；
 *         会给容器中导入非常多的自动配置类(xxxAutoConfiguration)；就是给容器中导入这个场景需要的所有组件，并配置好这些组件；
 *         有了自动配置类，免去了我们手动编写配置、注入功能组件等工作；
 *
 *         SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, ClassLoader);
 *           Spring Boot在启动的时候从类路径下的META-INF/spring.factories中获取EnableAutoConfiguration指定的值；
 *             将这些值作为自动配置类导入到容器中，自动配置类就生效，帮我们进行自动配置工作；以前我们需要自己配置的东西，自动配置类都帮我们完成了；
 *
 *         J2EE的整体整合解决方案和自动配置都在spring-boot-autoconfigure-1.5.9.RELEASE.jar
 */

/**
 * @author Kavin
 * @date 2021年11月9日13:34:28
 */
@SpringBootApplication
public class HelloWorldMainApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloWorldMainApplication.class, args);
    }

}
