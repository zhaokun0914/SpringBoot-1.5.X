# 自动配置原理

## 1、自动配置原理：

1）、SpringBoot启动的时候加载主配置类，开启了自动配置功能 ``@EnableAutoConfiguration``

2）、**@EnableAutoConfiguration 作用**：

- 利用EnableAutoConfigurationImportSelector给容器中导入一些组件？

- 可以查看selectImports()方法的内容；

- List configurations = getCandidateConfigurations(annotationMetadata, attributes);获取候选的配置

  - ```
    SpringFactoriesLoader.loadFactoryNames()
    扫描所有jar包类路径下 META‐INF/spring.factories
    把扫描到的这些文件的内容包装成properties对象
    从properties中获取到EnableAutoConfiguration.class类（类名）对应的值，然后把他们添加在容器
    中
    ```

  - 将类路径下 META-INF/spring.factories 里面配置的所有EnableAutoConfiguration的值加入到了容器中

    ```
    # Auto Configure
    org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
    org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
    org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
    org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration,\
    org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
    org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration,\
    org.springframework.boot.autoconfigure.cassandra.CassandraAutoConfiguration,\
    org.springframework.boot.autoconfigure.cloud.CloudAutoConfiguration,\
    org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration,\
    org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration,\
    org.springframework.boot.autoconfigure.couchbase.CouchbaseAutoConfiguration,\
    org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.cassandra.CassandraRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.couchbase.CouchbaseRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.ldap.LdapDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.ldap.LdapRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.neo4j.Neo4jRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.solr.SolrRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.rest.RepositoryRestMvcAutoConfiguration,\
    org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration,\
    org.springframework.boot.autoconfigure.elasticsearch.jest.JestAutoConfiguration,\
    org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration,\
    org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration,\
    org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration,\
    org.springframework.boot.autoconfigure.hateoas.HypermediaAutoConfiguration,\
    org.springframework.boot.autoconfigure.hazelcast.HazelcastAutoConfiguration,\
    org.springframework.boot.autoconfigure.hazelcast.HazelcastJpaDependencyAutoConfiguration,\
    org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration,\
    org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration,\
    org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration,\
    org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration,\
    org.springframework.boot.autoconfigure.jdbc.JndiDataSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.jdbc.XADataSourceAutoConfiguration,\
    org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration,\
    org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration,\
    org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration,\
    org.springframework.boot.autoconfigure.jms.JndiConnectionFactoryAutoConfiguration,\
    org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration,\
    org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration,\
    org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration,\
    org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration,\
    org.springframework.boot.autoconfigure.jersey.JerseyAutoConfiguration,\
    org.springframework.boot.autoconfigure.jooq.JooqAutoConfiguration,\
    org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration,\
    org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration,\
    org.springframework.boot.autoconfigure.ldap.LdapAutoConfiguration,\
    org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration,\
    org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
    org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
    org.springframework.boot.autoconfigure.mobile.DeviceResolverAutoConfiguration,\
    org.springframework.boot.autoconfigure.mobile.DeviceDelegatingViewResolverAutoConfiguration,\
    org.springframework.boot.autoconfigure.mobile.SitePreferenceAutoConfiguration,\
    org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration,\
    org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration,\
    org.springframework.boot.autoconfigure.mustache.MustacheAutoConfiguration,\
    org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,\
    org.springframework.boot.autoconfigure.reactor.ReactorAutoConfiguration,\
    org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration,\
    org.springframework.boot.autoconfigure.security.SecurityFilterAutoConfiguration,\
    org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration,\
    org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration,\
    org.springframework.boot.autoconfigure.sendgrid.SendGridAutoConfiguration,\
    org.springframework.boot.autoconfigure.session.SessionAutoConfiguration,\
    org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration,\
    org.springframework.boot.autoconfigure.social.FacebookAutoConfiguration,\
    org.springframework.boot.autoconfigure.social.LinkedInAutoConfiguration,\
    org.springframework.boot.autoconfigure.social.TwitterAutoConfiguration,\
    org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration,\
    org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration,\
    org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration,\
    org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration,\
    org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.EmbeddedServletContainerAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.HttpEncodingAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.ServerPropertiesAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration,\
    org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration,\
    org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration,\
    org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration,\
    org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration
    ```
每一个这样的 xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置；
  
3）、每一个自动配置类进行自动配置功能；

4）、以HttpEncodingAutoConfiguration（Http编码自动配置）为例解释自动配置原理；

```
@Configuration // 表示这是一个配置类 -> 和以前编写的配置文件一样，也可以给容器中添加组件

@EnableConfigurationProperties(HttpEncodingProperties.class) // 启用指定类的EnableConfigurationProperties功能；将配置文件中对应的值和HttpEncodingProperties绑定起来；并把HttpEncodingProperties加入到ioc容器中

@ConditionalOnWebApplication // Spring底层@Conditional注解（Spring注解版），根据不同的条件，如果满足指定的条件，整个配置类里面的配置就会生效； 判断当前应用是否是web应用，如果是，当前配置类生效

@ConditionalOnClass(CharacterEncodingFilter.class) //判断当前项目有没有CharacterEncodingFilter这个类；SpringMVC中进行乱码解决的过滤器；

@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)
// 判断配置文件中是否存在某个配置 spring.http.encoding.enabled；如果不存在，判断也是成立的
// 即使我们配置文件中不配置spring.http.encoding.enabled=true，也是默认生效的；

public class HttpEncodingAutoConfiguration {

    // 他已经和SpringBoot的配置文件映射了
    private final HttpEncodingProperties properties;
	
    //只有一个有参构造器的情况下，参数的值就会从容器中拿
    public HttpEncodingAutoConfiguration(HttpEncodingProperties properties) {
    this.properties = properties;
    }
    
    @Bean //给容器中添加一个组件，这个组件的某些值需要从properties中获取
    @ConditionalOnMissingBean(CharacterEncodingFilter.class) //判断容器没有这个组件？
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
        filter.setEncoding(this.properties.getCharset().name());
        filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
        filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
        return filter;
    }
```

根据当前不同的条件判断，决定这个配置类是否生效？

一但这个配置类生效，这个配置类就会给容器中添加各种组件。

这些``组件的属性``是从对应的``properties类中获取的``，这些``类里面的每一个属性``又是``和配置文件绑定``的；

5）、所有配置文件中``能配置的属性``都是``在xxxProperties类中封装着``；``配置文件``能配置什么就``可以参照``某个功能对应的``这个属性类``

```
@ConfigurationProperties(prefix = "spring.http.encoding") // 从配置文件中获取指定的值和bean的属性进行绑定
public class HttpEncodingProperties {
```

**精髓：**

**1）、SpringBoot启动会加载大量的自动配置类**

**2）、我们看我们需要的功能有没有SpringBoot默认写好的自动配置类；**

**3）、我们再来看这个自动配置类中到底配置了哪些组件；（只要我们要用的组件有，我们就不需要再来配置了）**

**4）、给容器中自动配置类添加组件的时候，会从properties类中获取某些属性。我们就可以在配置文件中指定这些属性的值；**

xxxxAutoConfigurartion：自动配置的类；

给容器中添加组件

xxxxProperties:封装配置文件中相关属性；

## 2、细节

1、@Conditional派生注解（Spring注解版原生的@Conditional作用）

作用：必须是@Conditional指定的条件成立，才给容器中添加组件，配置配里面的所有内容才生效；

| @Conditional扩展注解            | 作用（判断是否满足当前指定条件）                 |
| ------------------------------- | ------------------------------------------------ |
| @ConditionalOnJava              | 系统的java版本是否符合要求                       |
| @ConditionalOnBean              | 容器中存在指定Bean；                             |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean；                           |
| @ConditionalOnExpression        | 满足SpEL表达式指定                               |
| @ConditionalOnClass             | 系统中有指定的类                                 |
| @ConditionalOnMissingClass      | 系统中没有指定的类                               |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者这个Bean是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值                   |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件                     |
| @ConditionalOnWebApplication    | 当前是web环境                                    |
| @ConditionalOnNotWebApplication | 当前不是web环境                                  |
| @ConditionalOnJndi              | JNDI存在指定项                                   |

**自动配置类必须在一定的条件下才能生效；**

我们怎么知道哪些自动配置类生效；

**``我们可以通过启用 debug=true属性；来让控制台打印自动配置报告``**，这样我们就可以很方便的知道哪些自动配置类生效；

```
=========================
AUTO-CONFIGURATION REPORT
=========================


Positive matches: （自动配置类启用的）
-----------------

   DispatcherServletAutoConfiguration matched:
      - @ConditionalOnClass found required class 'org.springframework.web.servlet.DispatcherServlet'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)

   DispatcherServletAutoConfiguration.DispatcherServletConfiguration matched:
      - @ConditionalOnClass found required class 'javax.servlet.ServletRegistration'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - Default DispatcherServlet did not find dispatcher servlet beans (DispatcherServletAutoConfiguration.DefaultDispatcherServletCondition)

   DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration matched:
      - @ConditionalOnClass found required class 'javax.servlet.ServletRegistration'; @ConditionalOnMissingClass did not find unwanted class (OnClassCondition)
      - DispatcherServlet Registration did not find servlet registration bean (DispatcherServletAutoConfiguration.DispatcherServletRegistrationCondition)

   DispatcherServletAutoConfiguration.DispatcherServletRegistrationConfiguration#dispatcherServletRegistration matched:
      - @ConditionalOnBean (names: dispatcherServlet; types: org.springframework.web.servlet.DispatcherServlet; SearchStrategy: all) found beans 'dispatcherServlet', 'dispatcherServlet' (OnBeanCondition)

   EmbeddedServletContainerAutoConfiguration matched:
      - @ConditionalOnWebApplication (required) found StandardServletEnvironment (OnWebApplicationCondition)


Negative matches: （没有启动，没有匹配成功的自动配置类）
-----------------

   ActiveMQAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required classes 'javax.jms.ConnectionFactory', 'org.apache.activemq.ActiveMQConnectionFactory' (OnClassCondition)

   AopAutoConfiguration:
      Did not match:
         - @ConditionalOnClass did not find required classes 'org.aspectj.lang.annotation.Aspect', 'org.aspectj.lang.reflect.Advice' (OnClassCondition)


Exclusions: （排除的）
-----------

    None


Unconditional classes: （无条件的类）
----------------------

    org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration

    org.springframework.boot.autoconfigure.web.WebClientAutoConfiguration

    org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration

    org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration
```

