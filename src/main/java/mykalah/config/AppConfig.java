package mykalah.config;

import org.springframework.context.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan({"mykalah.mvc, mykalah.data, mykalah.service, mykalah.config"})
@EnableJpaRepositories (basePackages = "mykalah.data")
@EnableTransactionManagement (proxyTargetClass = true)
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

    @Bean(name = "entityManagerFactory")
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
        jpaProp.put("hibernate.hbm2ddl.auto", "create-drop");
        jpaProp.put("hibernate.show_sql", Boolean.parseBoolean("true"));
        jpaProp.put("hibernate.connection.charset", "UTF-8");
        jpaProp.put("hibernate.connection.release_mode", "auto");
        jpaProp.put("javax.persistence.validation.mode", "callback");
        factoryBean.setJpaProperties(jpaProp);
        factoryBean.afterPropertiesSet();
        return factoryBean;
    }

    @Bean
    public ViewResolver viewResolver() {

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers( final ResourceHandlerRegistry registry ) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

}
