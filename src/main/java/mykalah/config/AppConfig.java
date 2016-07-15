package mykalah.config;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.JstlView;

import java.util.Properties;

import org.springframework.web.servlet.view.UrlBasedViewResolver;

//import mykalah.config.DataConfiguration;

//import org.springframework.data.repository.support.DomainClassConverter;

@Configuration
@EnableWebMvc
@ComponentScan({"mykalah.mvc, mykalah.data, mykalah.service"})
@EnableJpaRepositories (basePackages = "mykalah.data")

public class AppConfig extends WebMvcConfigurerAdapter {

    @Bean(name = "myDataSource")
    public DriverManagerDataSource myDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/mykalah?useUnicode=true&amp;characterEncoding=UTF8&amp;characterSetResults=UTF-8");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("test");
        return driverManagerDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(myDataSource());
        factoryBean.setPersistenceUnitName("KalahJPA");
        HibernateJpaVendorAdapter adapter =  new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(false);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
        factoryBean.setJpaVendorAdapter(adapter);
        factoryBean.setPackagesToScan("mykalah");
        Properties jpaProp = new Properties();
        jpaProp.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        jpaProp.put("hibernate.hbm2ddl.auto", "update");
        jpaProp.put("hibernate.show_sql", Boolean.parseBoolean("true"));
        jpaProp.put("hibernate.connection.charset", "UTF-8");
        jpaProp.put("hibernate.connection.release_mode", "auto");
        jpaProp.put("javax.persistence.validation.mode", "callback");
        factoryBean.setJpaProperties(jpaProp);
        factoryBean.afterPropertiesSet();
      //  factoryBean.setLoadTimeWeaver(new InstrumentationLoadTimeWeaver());
        return factoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }


    @Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setContentType("text/html;charset=UTF-8");
        resolver.setPrefix("WEB-INF/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        resolver.setOrder(1);
        return resolver;
    }

    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }


    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
