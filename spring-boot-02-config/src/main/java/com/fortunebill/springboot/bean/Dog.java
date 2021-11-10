package com.fortunebill.springboot.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/*
 * 将配置的每一个属性的值映射到这个bean中
 *
 * 1：@PropertySource(value = {"classpath:static/config/authorSetting.properties"},
 *                 ignoreResourceNotFound = false,
 *                 encoding = "UTF-8",
 *                 name = "authorSetting.properties")
 *     1、value：指明加载配置文件的路径。
 *     2、ignoreResourceNotFound：指定的配置文件不存在是否报错，默认是false。当设置为 true 时，若该文件不存在，程序不会报错。
 *        实际项目开发中，最好设置 ignoreResourceNotFound 为 false。
 *     3、encoding：指定读取属性文件所使用的编码，我们通常使用的是UTF-8。
 *
 * 2：当我们使用 @Value 需要注入的值较多时，代码就会显得冗余，于是 @ConfigurationProperties 登场了
 *
 * 3：区别
 * |                | @ConfigurationProperties | @Value       |
 * | -------------- | ------------------------ | ------------ |
 * | 功能           | 批量注入配置文件中的属性 | 一个一个指定 |
 * | 松散绑定       | 支持                     | 不支持       |
 * | SpEL           | 不支持                   | 支持         |
 * | JSR303数据校验 | 支持                     | 不支持       |
 * | 复杂类型封装   | 支持                     | 不支持       |
 *
 * 总结
 *     如果我们只是在某个业务逻辑中需要获取一下配置文件中的某项值，使用@Value
 *     如果我们专门编写了一个javaBean来和配置文件进行映射，我们就直接使用@ConfigurationProperties
 */
/**
 *  @author Kavin
 *  @date 2021年11月9日18:59:18
 */
@Component
@PropertySource(value = {"classpath:static/config/dog.properties"}, ignoreResourceNotFound = false, encoding = "UTF-8")
public class Dog {

    /*
     * <bean class="Person">
     *     <property name="lastName" value="${key}从环境变量、配置文件中获取值/#{SpEL}/字面量"></property>
     * </bean>
     */
    @Value("${dog.name}")
    private String name;
    @Value("#{1*2}")
    private Integer age;
    @Value("true")
    private boolean isLive;

    private Map<String, Object> maps;

    private List<Object> lists;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getLists() {
        return lists;
    }

    public void setLists(List<Object> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", live=" + isLive +
                '}';
    }
}
