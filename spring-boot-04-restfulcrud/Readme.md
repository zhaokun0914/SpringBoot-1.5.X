# Web开发

## 简介

使用SpringBoot；

**1）、创建SpringBoot应用，选中我们需要的模块；**

**2）、SpringBoot已经默认将这些场景配置好了，只需要在配置文件中指定少量配置就可以运行起来**

**3）、自己编写业务代码；**

**自动配置原理？**

这个场景SpringBoot帮我们配置了什么？能不能修改？能修改哪些配置？能不能扩展？

```
xxxxAutoConfiguration：帮我们给容器中自动[配置组件]；
xxxxProperties:[配置类]来[封装][配置文件]里的内容；
```

## SpringBoot对静态资源的映射规则

```
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties implements ResourceLoaderAware {
// 可以设置和资源有关的参数、缓存时间等
```

```
public class WebMvcAutoConfiguration {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
            return;
        }
        
        // webjar资源映射
        Integer cachePeriod = this.resourceProperties.getCachePeriod();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                                                         .addResourceLocations("classpath:/META-INF/resources/webjars/")
                                                         .setCachePeriod(cachePeriod));
        }
        
        // 静态资源映射
        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                                                         .addResourceLocations(this.resourceProperties.getStaticLocations())
                                                         .setCachePeriod(cachePeriod));
        }
    }
    
    @Bean
    public WelcomePageHandlerMapping welcomePageHandlerMapping(ResourceProperties resourceProperties) {
        return new WelcomePageHandlerMapping(resourceProperties.getWelcomePage(),
                                             this.mvcProperties.getStaticPathPattern());
    }
    
    // 配置喜欢的图标
    @Configuration
    @ConditionalOnProperty(value = "spring.mvc.favicon.enabled", matchIfMissing = true)
    public static class FaviconConfiguration {

        private final ResourceProperties resourceProperties;

        public FaviconConfiguration(ResourceProperties resourceProperties) {
            this.resourceProperties = resourceProperties;
        }

        @Bean
        public SimpleUrlHandlerMapping faviconHandlerMapping() {
            SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
            mapping.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            // 所有 **/favicon.ico
            mapping.setUrlMap(Collections.singletonMap("**/favicon.ico", faviconRequestHandler()));
            return mapping;
        }

        @Bean
        public ResourceHttpRequestHandler faviconRequestHandler() {
            ResourceHttpRequestHandler requestHandler = new ResourceHttpRequestHandler();
            requestHandler.setLocations(this.resourceProperties.getFaviconLocations());
            return requestHandler;
        }

    }
}
```

**1）、所有 `/webjars/**` ，都去 `classpath:/META-INF/resources/webjars/` 找资源；**

`webjars`：以jar包的方式引入静态资源；

http://www.webjars.org/

http://localhost:8080/webjars/jquery/3.6.0/jquery.js

```
<!‐‐引入jquery‐webjar‐‐>在访问的时候只需要写webjars下面资源的名称即可
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.6.0</version>
</dependency>
```

**2）、"/**" 访问当前项目的任何资源，如果没人处理，都去（静态资源文件夹）找映射**

```
"classpath:/META-INF/resources/",
"classpath:/resources/",
"classpath:/static/",
"classpath:/public/"
"/"：当前项目的根路径，java文件夹和resource文件夹都是类路径的根路径
```

`localhost:8080/abc` -> 去`静态资源文件夹`里面找abc

**3）、欢迎页； 静态资源文件夹下的所有index.html页面；被"/\*\*"映射；**

localhost:8080/ 找index页面

**4）、所有的 \*\*/favicon.ico 都是在静态资源文件下找；**

## 模板引擎

JSP、Velocity、Freemarker、Thymeleaf

Spring Boot推荐Thymeleaf

语法更简单，功能更强大

**1、引入thymeleaf**

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<!-- 切换thymeleaf的版本 -->
<properties>
    <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
    <!-- 布局功能的支持程序 thymeleaf主程序为3.x时layout要用2以上版本 -->
    <!-- thymeleaf2 layout1 -->
    <!-- thymeleaf3 layout2 -->
    <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
</properties>
```

**2、Thymeleaf使用**

```
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {

	private static final Charset DEFAULT_ENCODING = Charset.forName("UTF-8");

	private static final MimeType DEFAULT_CONTENT_TYPE = MimeType.valueOf("text/html");

	public static final String DEFAULT_PREFIX = "classpath:/templates/";

	public static final String DEFAULT_SUFFIX = ".html";
	
	// 只要我们把HTML页面放在classpath:/templates/，thymeleaf就能自动渲染；
```

**使用：**

**1、导入thymeleaf的名称空间，开启语法提示**

```
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

**2、使用thymeleaf语法；**

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF‐8">
    <title>Title</title>
</head>
<body>
    <h1>成功！</h1>
    <!‐‐th:text 将div里面的文本内容设置为 ‐‐>
    <div th:text="${hello}">这是显示欢迎信息</div>
</body>
</html>
```

**3、语法规则**

1、th:text：改变当前元素里面的文本内容

th:任意html属性：来替换原生属性的值

| 顺序 | 特性                    | 属性                                                         |
| :--- | :---------------------- | :----------------------------------------------------------- |
| 1    | 包含片段（jsp:include） | `th:insert`<br />`th:replace`                                |
| 2    | 迭代片段（c:forEach）   | `th:each`                                                    |
| 3    | 条件计算（c:if）        | `th:if`<br />`th:unless`<br />`th:switch`<br />`th:case`     |
| 4    | 局部变量定义（c:set）   | `th:object`<br />`th:with`                                   |
| 5    | 任意属性修改            | `th:attr`<br />`th:attrprepend`(前面追加)<br />`th:attrappend`(后面追加) |
| 6    | 具体属性修改            | `th:value`<br />`th:href`<br />`th:src`<br />`...`           |
| 7    | 修改标签体内容          | `th:text`(转义特殊字符)<br />`th:utext`(不转义特殊字符)      |
| 8    | 片段声明                | `th:fragment`                                                |
| 9    | 片段删除                | `th:remove`                                                  |

2、表达式

```
Simple expressions:（表达式语法）
    Variable Expressions: ${...} -> 获取变量值；OGNL
        1）获取对象的属性、调用方法
            ${person.father.name}
            ${person['father']['name']}
            ${countriesByCode.ES}
            ${personsByName['Stephen Zucchini'].age}
            ${personsArray[0].name}
            ${person.createCompleteName()}
            ${person.createCompleteNameWithSeparator('-')}
        2）使用内置的基本对象
            #ctx：                                上下文[对象]
                ${#ctx.locale}
                ${#ctx.variableNames}
                ${#ctx.request}
                ${#ctx.response}
                ${#ctx.session}
                ${#ctx.servletContext}
                param(这个是名称空间)
                    ${param.foo}                  // 使用请求参数 'foo' 的值检索 String[]
                    ${param.size()}
                    ${param.isEmpty()}
                    ${param.containsKey('foo')}
                    ...
                session(这个是名称空间)
                    ${session.foo}                // 检索 session 属性 'foo'
                    ${session.size()}
                    ${session.isEmpty()}
                    ${session.containsKey('foo')}
                    ...
                application(这个是名称空间)
                    ${application.foo}            // 检索 ServletContext 属性“foo”
                    ${application.size()}
                    ${application.isEmpty()}
                    ${application.containsKey('foo')}
                    ...
            #vars:                               上下文[变量]
            #locale：                            上下文[语言环境]
                ${#locale}
            在 Web 环境中，还可以直接访问以下对象（注意这些是对象，而不是映射/命名空间）：
            #request：                           （仅在 Web 上下文中）HttpServletRequest 对象
                ${#request.getAttribute('foo')}
                ${#request.getParameter('foo')}
                ${#request.getContextPath()}
                ${#request.getRequestName()}
                ...
            #response：                          （仅在 Web 上下文中）HttpServletResponse 对象
            #session：                           （仅在 Web 上下文中）HttpSession 对象
                ${#session.getAttribute('foo')}
                ${#session.id}
                ${#session.lastAccessedTime}
                ...
            #servletContext：                    （仅在 Web 上下文中）ServletContext 对象
                ${#servletContext.getAttribute('foo')}
                ${#servletContext.contextPath}
                ...
        3）内置的一些工具对象
            #execInfo:     有关正在处理的模板的信息。
            #messages:     在变量表达式中获取外部化消息的方法，与使用 #{…} 语法获取它们的方式相同。
            #uris:         转义部分 URL/URI 的方法
            #conversions:  执行配置的转换服务的方法（如果有的话）。
            #dates:        java.util.Date 对象的方法：格式化、组件提取等。
            #calendars:    类似于 #dates，但用于 java.util.Calendar 对象。
            #numbers:      格式化数字对象的方法。
            #strings:      String 对象的方法：contains、startsWith、prepending/appending 等。
            #objects:      对象的一般方法。
            #bools:        布尔判断的方法。
            #arrays:       数组的方法。
            #lists:        列表的方法。
            #sets:         集合的方法。
            #maps:         地图的方法。
            #aggregates:   在数组或集合上创建聚合的方法。
            #ids:          处理可能重复的 id 属性的方法（例如，作为迭代的结果）。
            
    Selection Variable Expressions: *{...} -> 选择表达式：和${}在功能上是一样
        补充：配合 th:object="${session.user}
        <div th:object="${session.user}">
            <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
            <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p>
            <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
        </div>
        
    Message Expressions: #{...} -> 获取国际化内容
    
    Link URL Expressions: @{...} -> 定义URL
        @{/order/process(execId=${execId}, execType='FAST')}
        
    Fragment Expressions: ~{...} -> 片段引用表达式
        <div th:insert="~{commons :: main}">...</div>
    
Literals:（字面量）
    Text literals: 'one text', 'Another one!',…
    Number literals: 0, 34, 3.0, 12.3,…
    Boolean literals: true, false
    Null literal: null
    Literal tokens: one, sometext, main,…
    
Text operations:（文本操作）
    String concatenation: +
    Literal substitutions: |The name is ${name}|
    
Arithmetic operations:（数学运算）
    Binary operators: +, -, *, /, %
    Minus sign (unary operator): -
    
Boolean operations:（布尔运算）
    Binary operators: and, or
    Boolean negation (unary operator): !, not
    
Comparisons and equality:（比较运算）
    Comparators: >, <, >=, <= (gt, lt, ge, le)
    Equality operators: ==, != (eq, ne)

Conditional operators:（条件运算）
    If-then: (if) ? (then)
    If-then-else: (if) ? (then) : (else)
    Default: (value) ?: (defaultvalue)

Special tokens:
    No-Operation: _
```

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF‐8">
    <title>Title</title>
</head>
    <body>
        <h1>成功！</h1>
        <!‐‐th:text 将div里面的文本内容设置为 ‐‐>
        <div id="div01" class="myDiv" th:id="${hello}" th:class="${hello}" th:text="${hello}">这是显示欢迎信息</div>

        <hr/>

        <div >[[${hello}]]</div><!-- th:text 转义特殊字符 -->
        <div >[(${hello})]</div><!-- th:utext 不转义特殊字符 -->

        <hr/>

        <!-- th:each 在那个标签上，那个标签就重复几次 -->
        <div th:each="user : ${users}" th:text="${user}"></div>
        <div>
            <span th:each="user : ${users}" th:text="${user}"></span>
        </div>
    </body>
</html>

```



## SpringMVC自动配置

### 1、Spring MVC auto-configuration

[Spring Boot Reference Guide](https://docs.spring.io/spring-boot/docs/1.5.9.RELEASE/reference/htmlsingle/#boot-features-spring-mvc)

### 27.1.1 Spring MVC auto-configuration

Spring Boot 自动配置好了SpringMVC

以下是SpringBoot对SpringMVC的默认配置:（`WebMvcAutoConfiguration`）

1. 包含`ContentNegotiatingViewResolver` 和 `BeanNameViewResolver` beans.
    - 自动配置了ViewResolver（视图解析器：根据方法的返回值得到视图对象（View），视图对象决定如何渲染（转发？重定向？））
    - `ContentNegotiatingViewResolver`：组合所有的视图解析器的；
    - 如何定制？：我们可以自己给容器中添加一个视图解析器；自动的将其组合进来；
2. 支持提供静态资源，包括对 WebJars 的支持（见下文）.静态资源文件夹路径，webjars
3. 自动注册了`Converter `, `GenericConverter `, `Formatter` beans.
    - Converter：转换器； public String hello(User user)：类型转换使用Converter
    - `Formatter `格式化器； 2017.12.17===Date；

```
@Bean
@ConditionalOnProperty(prefix = "spring.mvc", name = "date-format")    // 在文件中配置日期格式化的规则，如果配置了则注册此组件，否则不注册
public Formatter<Date> dateFormatter() {
    return new DateFormatter(this.mvcProperties.getDateFormat());      // 日期格式化组件
}
```

自己添加的格式化器转换器，我们只需要放在容器中即可

4. 支持 `HttpMessageConverters `(see below).
    - `HttpMessageConverter`：SpringMVC用来转换Http请求和响应的；User---Json；
    - `HttpMessageConverters `是从容器中确定；获取所有的`HttpMessageConverter`；
      自己给容器中添加`HttpMessageConverter`，只需要将自己的组件注册到容器中（@Bean、@Component）
5. 自动注册 `MessageCodesResolver `(see below).定义错误代码生成规则
6. 静态`index.html` 支持. 静态首页访问
7. 定制`Favicon`支持(see below). favicon.ico
8. 自动使用 `ConfigurableWebBindingInitializer `bean (see below).

我们可以配置一个`ConfigurableWebBindingInitializer`来替换默认的；（添加到容器）

  ```
初始化WebDataBinder；
请求数据=====JavaBean；
  ```



`org.springframework.boot.autoconfigure.web`：web的所有自动场景；

如果您想保留 SpringBoot MVC 功能，并且只想添加额外的 MVC 配置（拦截器、格式化程序、视图控制器等），您可以添加一个继承 `WebMvcConfigurerAdapter `类型的 `@Configuration` 类，但不要添加 `@EnableWebMvc`。

如果您希望提供 `RequestMappingHandlerMapping`、`RequestMappingHandlerAdapter `或 `ExceptionHandlerExceptionResolver `的自定义实例，您可以声明一个继承 `WebMvcRegistrationsAdapter ` 类的组件。

如果你想完全控制 Spring MVC，你可以添加一个带有 `@EnableWebMvc` 注解的 `@Configuration` 类。

### 2、扩展SpringMVC

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <mvc:view-controller path="/hello" view-name="success"/>
    <mvc:interceptors>
        <mvc:interceptor>
            <mvc:mapping path="/hello"/>
            <bean></bean>
        </mvc:interceptor>
    </mvc:interceptors>
    
</beans>
```

编写一个配置类（`@Configuration`），是`WebMvcConfigurerAdapter`类型；不能标注`@EnableWebMvc`;

既保留了所有的自动配置，也能用我们扩展的配置

```
// 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
// SpringBoot2 用这种方式 public class MyMvcConfig implements WebMvcConfigurer，因为SpringBoot2是基于Java8的，因此接口中用了default实现，不需要WebMvcConfigurerAdapter类了
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/fortunebill").setViewName("success");
    }
}
```

原理：

1. `WebMvcAutoConfigurationAdapter`是`SpringMVC`的自动配置类
2. 在做其他自动配置时会导入；`@Import(EnableWebMvcConfiguration.class)`

```
@Configuration
@Import(EnableWebMvcConfiguration.class)
@EnableConfigurationProperties({ WebMvcProperties.class, ResourceProperties.class })
public static class WebMvcAutoConfigurationAdapter extends WebMvcConfigurerAdapter {
}

@Configuration
public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration {

}

@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {

	private final WebMvcConfigurerComposite configurers = new WebMvcConfigurerComposite();

    // 从容器中获取所有的 WebMvcConfigurer
	@Autowired(required = false)
	public void setConfigurers(List<WebMvcConfigurer> configurers) {
		if (!CollectionUtils.isEmpty(configurers)) {
			this.configurers.addWebMvcConfigurers(configurers);
		}
	}
	
	@Override
	protected void addViewControllers(ViewControllerRegistry registry) {
		this.configurers.addViewControllers(registry);
	}
	
	// configurers对象里的实现方法，将所有的WebMvcConfigurer相关配置都来一起调用
	// @Override
	// public void addViewControllers(ViewControllerRegistry registry) {
	// 	for (WebMvcConfigurer delegate : this.delegates) {
	// 		delegate.addViewControllers(registry);
	// 	}
	// }
}
```

3. 容器中所有的WebMvcConfigurer都会一起起作用
4. 我们的配置类也会被调用

效果：SpringMVC的自动配置和我们的扩展配置都会起作用；

### 3、全面接管SpringMVC

SpringBoot对SpringMVC的自动配置不需要了，所有都是我们自己配；所有的SpringMVC的自动配置都失效了

**我们需要在配置类中添加@EnableWebMvc即可**

原理：

	为什么添加了`@EnableWebMvc`，自动配置就失效了？

1. `@EnableWebMvc`注解的核心

```
@Import({DelegatingWebMvcConfiguration.class})
public @interface EnableWebMvc {
```

2.

```
@Configuration
public class DelegatingWebMvcConfiguration extends WebMvcConfigurationSupport {
```

3.

```
@Configuration
@ConditionalOnWebApplication
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class, WebMvcConfigurerAdapter.class })
// 容器中没有这个组件的时候，这个自动配置类才生效
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)
@AutoConfigureAfter({ DispatcherServletAutoConfiguration.class, ValidationAutoConfiguration.class })
public class WebMvcAutoConfiguration {
```

4. `@EnableWebMvc`将`WebMvcConfigurationSupport`组件导入进来
5. 导入的`WebMvcConfigurationSupport`只是`SpringMVC`最基本的功能

## 如何修改SpringBoot的默认配置

模式

1、Spring Boot在自动配置很多组件的时候，先看容器中有没有用户配置的（@Bean、@Component）如果有就用用户配置的，如果没有，才自动配置。

    如果有些组件可以有多个（例如：ViewResolver），SpringBoot会将用户配置的和SpringBoot默认的组合起来。

2、在SpringBoot中会有非常多的xxxConfigurer帮助我们进行扩展配置

3、在SpringBoot中会有很多的xxxCustomizer帮助我们进行定制配置

## RestfulCRUD

### 1、默认访问首页

```
// 使用WebMvcConfigurerAdapter可以来扩展SpringMVC的功能
// @EnableWebMvc   不要接管SpringMVC
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 浏览器发送 /fortunebill 请求来到 success
        registry.addViewController("/fortunebill").setViewName("success");
        // 设置登录页面
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/index.html").setViewName("login");
    }
}
```

### 2、国际化

过去使用SpringMVC时的步骤

1. 编写国际化配置文件
2. 使用ResourceBundleMessageSource管理国际化资源文件
3. 在页面使用fmt:message取出国际化内容

使用SpringBoot的步骤

1. 编写国际化配置文件，抽取页面需要显示的国际化消息

```
login.properties
login.password=密码
login.remember_me=记住我
login.sign_in=注册
login.tip=登录
login.username=用户名


login_en_US.properties
login.password=Password
login.remember_me=remember-me
login.sign_in=Sign in
login.tip=Please sign in
login.username=Username


login_zh_CN.properties
login.password=密码
login.remember_me=记住我
login.sign_in=注册
login.tip=登录
login.username=用户名
```

2. SpringBoot自动配置好了管理国际化资源文件的组件

```
@ConfigurationProperties(prefix = "spring.messages")
public class MessageSourceAutoConfiguration {
    
    /**
     * 以逗号分隔的基本名称列表（本质上是一个完全限定的类路径位置），每个都遵循 ResourceBundle 约定，并宽松地支持基于斜杠的位置。
     * 如果它不包含包限定符（例如“org.mypackage”），它将从类路径根目录解析。
     */
    private String basename = "messages";  
    // 我们的配置文件可以直接放在类路径下叫messages.properties的文件中；
        
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        if (StringUtils.hasText(this.basename)) {
            // 设置国际化资源文件的基础名（去掉语言国家代码的）
            messageSource.setBasenames(StringUtils.commaDelimitedListToStringArray(
                    StringUtils.trimAllWhitespace(this.basename)));
        }
        if (this.encoding != null) {
            messageSource.setDefaultEncoding(this.encoding.name());
        }
        messageSource.setFallbackToSystemLocale(this.fallbackToSystemLocale);
        messageSource.setCacheSeconds(this.cacheSeconds);
        messageSource.setAlwaysUseMessageFormat(this.alwaysUseMessageFormat);
        return messageSource;
    }
```

3. 去页面获取国际化的值

```
spring.messages.basename=i18n/login
```

```
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<meta name="description" content="">
		<meta name="author" content="">
		<title>Signin Template for Bootstrap</title>
		<!-- Bootstrap core CSS -->
		<link href="asserts/css/bootstrap.min.css" th:href="@{/webjars/bootstrap/4.0.0/css/bootstrap.min.css}" rel="stylesheet">
		<!-- Custom styles for this template -->
		<link href="asserts/css/signin.css" th:href="@{/asserts/css/signin.css}" rel="stylesheet">
	</head>

	<body class="text-center">
		<form class="form-signin" action="dashboard.html">
			<img class="mb-4" src="asserts/img/bootstrap-solid.svg" th:src="@{/asserts/img/bootstrap-solid.svg}" alt="" width="72" height="72">
			<h1 class="h3 mb-3 font-weight-normal" th:text="#{login.tip}"></h1>
			<label class="sr-only">Username</label>
			<input type="text" class="form-control" placeholder="Username" th:placeholder="#{login.username}" required="" autofocus="">
			<label class="sr-only">Password</label>
			<input type="password" class="form-control" placeholder="Password" th:placeholder="#{login.password}" required="">
			<div class="checkbox mb-3">
				<label>
          <input type="checkbox" value="remember-me">[[#{login.remember_me}]]
        </label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit" th:text="#{login.sign_in}">Sign in</button>
			<p class="mt-5 mb-3 text-muted">© 2017-2018</p>
			<a class="btn btn-sm">中文</a>
			<a class="btn btn-sm">English</a>
		</form>

	</body>

</html>
```

效果：根据浏览器语言设置的信息切换了国际化

原理：

	国际化Locale（区域信息对象）；LocaleResolver（获取区域信息对象）；

```
@Bean
@ConditionalOnMissingBean // 如果容器中没有 LocaleResolver 这个bean则配置这个组件，否则用用户配置的 LocaleResolver
@ConditionalOnProperty(prefix = "spring.mvc", name = "locale")
public LocaleResolver localeResolver() {
  if (this.mvcProperties.getLocaleResolver() == WebMvcProperties.LocaleResolver.FIXED) {
    return new FixedLocaleResolver(this.mvcProperties.getLocale());
  }
  AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
  localeResolver.setDefaultLocale(this.mvcProperties.getLocale());
  return localeResolver;
}
默认的就是根据请求头带来的区域信息获取Locale进行国际化
```

4. 点击链接切换国际化

```
<a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>
<a class="btn btn-sm" th:href="@{/index.html(l='en_US')}">English</a>

/**
 * 可以在连接上携带区域信息
 */
public class MyLocaleResolver implements LocaleResolver {
    
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String l = request.getParameter("l");
        Locale locale = Locale.getDefault();
        if(!StringUtils.isEmpty(l)){
            String[] split = l.split("_");
            locale = new Locale(split[0],split[1]);
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}

@Bean
public LocaleResolver localeResolver(){
    return new MyLocaleResolver();
}
```

### 3、登陆

开发期间模板引擎页面修改以后，要实时生效

1. 禁用模板引擎的缓存

```
# 禁用缓存
spring.thymeleaf.cache=false 
```

2. 页面修改完成以后ctrl+f9：重新编译；

登陆错误消息的显示

```
<p style="color: red" th:text="${msg}" th:if="${not #strings.isEmpty(msg)}"></p>
```

### 4、拦截器进行登陆检查

拦截器

```
/**
 * 登录检查
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.setAttribute("msg", "当前没有权限，请登录");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

```

注册拦截器

```
/**
 * Spring Boot 配置文件
 */
@Configuration
public class MyMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor())
                // 该拦截器拦截所有请求
                // 对于静态资源：*.css , *.js等，SpringBoot已经做好了静态资源映射
                .addPathPatterns("/**")
                // 排除以下请求
                .excludePathPatterns("/")             // 主页跳转
                .excludePathPatterns("/index.html")   // 主页跳转
                .excludePathPatterns("/user/login");  // 登录请求
    }
}
```

### 5、CRUD-员工列表

实验要求：

1. **RestfulCRUD：CRUD满足Rest风格**

URI：  /资源名称/资源标识       HTTP请求方式区分对资源CRUD操作

|      | 普通CRUD（uri来区分操作） | RestfulCRUD       |
| ---- | ------------------------- | ----------------- |
| 查询 | getEmp                    | emp---GET         |
| 添加 | addEmp?xxx                | emp---POST        |
| 修改 | updateEmp?id=xxx&xxx=xx   | emp/{id}---PUT    |
| 删除 | deleteEmp?id=1            | emp/{id}---DELETE |

2. **实验的请求架构**

| 实验功能                             | 请求URI | 请求方式 |
| ------------------------------------ | ------- | -------- |
| 查询所有员工                         | emps    | GET      |
| 查询某个员工(来到修改页面)           | emp/1   | GET      |
| 来到添加页面                         | emp     | GET      |
| 添加员工                             | emp     | POST     |
| 来到修改页面（查出员工进行信息回显） | emp/1   | GET      |
| 修改员工                             | emp     | PUT      |
| 删除员工                             | emp/1   | DELETE   |

2. **员工列表**

thymeleaf公共页面元素抽取

```
1、抽取公共片段
<div th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</div>

2、引入公共片段
<div th:insert="~{footer :: copy}"></div>
~{templatename::selector}：模板名::选择器
~{templatename::fragmentname}:模板名::片段名

3、默认效果：
insert的公共片段在div标签中
如果使用th:insert等属性进行引入，可以不用写~{}：
行内写法可以加上：[[~{}]];[(~{})]；
```

3. **三种引入公共片段的th属性**

**th:insert**：将公共片段整个插入到声明引入的元素中

**th:replace**：将声明引入的元素替换为公共片段

**th:include**：将被引入的片段的内容包含进这个标签中

```
<footer th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</footer>

<!--引入方式-->
<div th:insert="footer :: copy"></div>
<div th:replace="footer :: copy"></div>
<div th:include="footer :: copy"></div>

<!-- insert 效果-->
<div>
    <footer>
        &copy; 2011 The Good Thymes Virtual Grocery
    </footer>
</div>

<!-- replace 效果-->
<footer>
    &copy; 2011 The Good Thymes Virtual Grocery
</footer>

<!-- include 效果-->
<div>
    &copy; 2011 The Good Thymes Virtual Grocery
</div>
```

4. **引入片段的时候传入参数**

```
<nav class="col-md-2 d-none d-md-block bg-light sidebar" id="sidebar">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active"
                   th:class="${activeUri=='main.html'?'nav-link active':'nav-link'}"
                   href="#" th:href="@{/main.html}">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none"
                         stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"
                         class="feather feather-home">
                        <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                        <polyline points="9 22 9 12 15 12 15 22"></polyline>
                    </svg>
                    Dashboard <span class="sr-only">(current)</span>
                </a>
            </li>

<!--引入侧边栏;传入参数-->
<div th:replace="commons/bar::#sidebar(activeUri='emps')"></div>
```

### 6、CRUD-员工添加

### 7、CRUD-员工修改

### 8、CRUD-员工删除

## 错误处理机制

### 1、SpringBoot默认的错误处理机制

### 2、如果定制错误响应

#### 	1、如何定制错误的页面

#### 	2、如何定制错误的json数据

#### 	3、将我们的定制数据携带出去

## 配置嵌入式Servlet容器

### 1、如何定制和修改Servlet容器的相关配置

### 2、注册Servlet三大组件【Servlet、Filter、Listener】

### 3、替换为其他嵌入式Servlet容器

### 4、嵌入式Servlet容器自动配置原理

### 5、嵌入式Servlet容器启动原理

## 使用外置Servlet容器

### 1、步骤

### 2、原理
