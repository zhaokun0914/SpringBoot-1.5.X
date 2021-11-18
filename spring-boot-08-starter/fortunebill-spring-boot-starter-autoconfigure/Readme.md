# 自定义starter

starter：

​ 1、这个场景需要使用到的依赖是什么？

​ 2、如何编写自动配置

```java
@Configuration  //指定这个类是一个配置类
@ConditionalOnXXX  //在指定条件成立的情况下自动配置类生效
@AutoConfigureAfter  //指定自动配置类的顺序
@Bean  //给容器中添加组件

@ConfigurationPropertie结合相关xxxProperties类来绑定相关的配置
@EnableConfigurationProperties //让xxxProperties生效加入到容器中

自动配置类要能加载
将需要启动就加载的自动配置类，配置在META-INF/spring.factories
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
```

​ 3、模式：

**启动器**只用来做依赖导入；

**专门**来写一个**自动配置模块**；

**启动器**依赖自动配置；别人只需要**引入启动器**（starter）

mybatis-spring-boot-starter -> 自定义启动器名-spring-boot-starter

步骤：

1）、启动器模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.fortunebill</groupId>
    <artifactId>fortunebill-spring-boot-starter</artifactId>
    <version>1.0.0</version>

    <!-- 启动器 -->
    <dependencies>
        <!-- 引入自动配置模块 -->
        <dependency>
            <groupId>com.fortunebill</groupId>
            <artifactId>fortunebill-spring-boot-starter-autoconfigure</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>

</project>

```

2）、自动配置模块

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.fortunebill</groupId>
    <artifactId>fortunebill-spring-boot-starter-autoconfigure</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>fortunebill-spring-boot-starter-autoconfigure</name>
    <description>fortunebill-spring-boot-starter-autoconfigure</description>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.9.RELEASE</version>
        <relativePath/>
    </parent>

    <dependencies>
        <!-- 引入spring boot starter 所有starter的基本配置 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
    </dependencies>

</project>

```

```java
package com.fortunebill.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Kavin
 * @date 2021年11月18日15:45:44
 */
@ConfigurationProperties(prefix = "fortunebill.hello")
public class HelloProperties {

    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

```

```java
package com.fortunebill.starter;

/**
 * @author Kavin
 * @date 2021年11月18日15:45:44
 */
public class HelloService {

    HelloProperties helloProperties;

    public HelloService() {
    }

    public HelloService(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHellAtguigu(String name) {
        return helloProperties.getPrefix() + "-" + name + helloProperties.getSuffix();
    }
}

```

```java
package com.fortunebill.starter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kavin
 * @date 2021年11月18日15:45:33
 */
@Configuration
@ConditionalOnWebApplication //web应用才生效
@EnableConfigurationProperties(HelloProperties.class)
public class HelloServiceAutoConfiguration {

    private HelloProperties helloProperties;

    public HelloServiceAutoConfiguration(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    @Bean
    public HelloService helloService() {
        return new HelloService(helloProperties);
    }
}

```

# 更多SpringBoot整合示例

https://github.com/spring-projects/spring-boot/tree/master/spring-boot-samples



