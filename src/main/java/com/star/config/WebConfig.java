package com.star.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.ejb.access.LocalStatelessSessionProxyFactoryBean;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import java.util.Properties;

/**
 * Created by hp on 2017/3/10.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.star")
@PropertySource("classpath:c3p0.properties")
@EnableTransactionManagement
public class WebConfig  extends WebMvcConfigurerAdapter{
    //定义数据源属性
    @Value("${c3p0.driverClass}")
    private String driverClass;
    @Value("${c3p0.jdbcUrl}")
    private String jdbcUrl;
    @Value("${c3p0.user}")
    private String user;
    @Value("${c3p0.password}")
    private String password;
    @Value("${c3p0.maxPoolSize}")
    private String maxPoolSize;

/*----------------使用@PropertySource获取配置文件必须的配置---------------------*/
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /*----------------用TransactionManager来管理Session，使用@Transactional的前提---------------------*/
    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
        return new HibernateTransactionManager(sessionFactory);
    }

//--------------------------配置Thymeleaf视图解析器开始-----------------------------
    //配置ViewResolver
    @Bean
    public ViewResolver viewResolver(SpringTemplateEngine engine){
        ThymeleafViewResolver resolver=new ThymeleafViewResolver();
        resolver.setTemplateEngine(engine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }


    //配置TemplateEngine
    @Bean
    public SpringTemplateEngine templateEngine(TemplateResolver resolver){
        SpringTemplateEngine engine=new SpringTemplateEngine();
        engine.setTemplateResolver(resolver);
        //注册安全方言，以便在Thymeleaf页面中使用Thymeleaf Security标签
        //使用之前要引入thymeleaf-extras-springsecurity4依赖
        engine.addDialect(new SpringSecurityDialect());
        return engine;
    }

    //配置TemplateResolver
    @Bean
    public TemplateResolver templateResolver(){
        TemplateResolver resolver=new ServletContextTemplateResolver();
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setTemplateMode("HTML5");
        return resolver;
    }
//--------------------------配置Thymeleaf视图解析器结束-----------------------------
    //配置数据源
    @Bean
    public DataSource dataSource() throws Exception {
        ComboPooledDataSource dataSource=new ComboPooledDataSource();
        dataSource.setDriverClass(driverClass);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        return dataSource;
    }


/*-------------------------配置Hibernate的SessionFactory-----------------------------------*/
    @Bean
    public LocalSessionFactoryBean sessionFactory(DataSource dataSource){
        LocalSessionFactoryBean sfb=new LocalSessionFactoryBean();
        sfb.setDataSource(dataSource);
        sfb.setPackagesToScan("com.star.domain");
        Properties props=new Properties();
        props.setProperty("dialect","org.hibernate.dialect.MySQL5Dislect");
        sfb.setHibernateProperties(props);
        return sfb;
    }
   /*-------------------------支持使用Spring的规范异常,与@Repository结合使用-----------------------------------*/
   @Bean
   public BeanPostProcessor persistenceTranslation(){
       return new PersistenceExceptionTranslationPostProcessor();
   }

    //配置静态资源处理
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }


}
