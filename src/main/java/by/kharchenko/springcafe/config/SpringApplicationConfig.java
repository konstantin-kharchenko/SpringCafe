package by.kharchenko.springcafe.config;

import by.kharchenko.springcafe.controller.interceptor.AuthenticateAdminInterceptor;
import by.kharchenko.springcafe.controller.interceptor.AuthenticateClientInterceptor;
import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Locale;
import java.util.Properties;


/*
* This class is config. In this class beans are created.
*/
@Configuration
@ComponentScan(basePackages = "by.kharchenko.springcafe")
@EnableWebMvc
@EnableTransactionManagement
public class SpringApplicationConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    public SpringApplicationConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /*
    *This Bean is needed in order to tell which folder the files
    * are in and what their extension is. it is it that allows
    * you not to write the file extension when going to the page
    */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/view/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /*
     *This Bean is needed to create engine
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }

    /*
     *This method is needed to connect thymeleaf
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry resolverRegistry) {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        resolverRegistry.viewResolver(viewResolver);
    }

    /*
     *This Bean is needed to describe the data resource
     */
    @Bean
    public BasicDataSource dataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/spring-cafe");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("BPpb23-=");
        return basicDataSource;
    }

    private Properties properties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    /*
    * This Bean is needed to get Session Factory for Hibernate
    */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("by.kharchenko.springcafe.model.entity");
        sessionFactory.setHibernateProperties(properties());
        return sessionFactory;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return sessionFactory().getObject();
    }

    /*
    * This Ben is needed to connect session in hibernate
    */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    /*
    * This Bean needed to get properties for localization
    */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename("classpath:pagecontent");
        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
        return reloadableResourceBundleMessageSource;
    }

    /*
     * This Bean is necessary to respond to a change in locale by the client
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new AuthenticateClientInterceptor()).addPathPatterns("/client/*");
        registry.addInterceptor(new AuthenticateAdminInterceptor()).addPathPatterns("/admin/*");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /*
    * This Bean needed to save locale in cookie
    */
    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        localeResolver.setCookieMaxAge(3600);
        localeResolver.setCookieName("locale");
        return localeResolver;
    }

    private Properties mailProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtps.auth", "true");
        return properties;
    }

    /*
    * This Bean needed to mail. In this Bean set mail configurations
    */
    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.mail.ru");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("cafe.from.app@mail.ru");
        javaMailSender.setPassword("hcrdj3x3ZaBxIq2c6DA1");
        javaMailSender.setProtocol("smtps");
        javaMailSender.setJavaMailProperties(mailProperties());
        return javaMailSender;
    }

    /*
    * This Bean need to send files from client
    */
    @Bean
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setDefaultEncoding("UTF-8");
        commonsMultipartResolver.setMaxUploadSizePerFile(5242440);
        return commonsMultipartResolver;
    }

}
