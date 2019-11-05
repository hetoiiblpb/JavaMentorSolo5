package ru.hetoiiblpb.util;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@org.springframework.context.annotation.Configuration
@ComponentScan(basePackages = "ru.hetoiiblpb")
@EnableTransactionManagement
@PropertySource(value = "classpath:config.properties")

public class DBHelper {
    private Environment environment;
    private static Properties properties;
    private static DBHelper instance;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("serverTimezone", environment.getRequiredProperty("serverTimezone"));
        properties.put("useSSL", environment.getRequiredProperty("useSSL"));
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("driverClass"));
        dataSource.setUrl(environment.getRequiredProperty("url"));
        dataSource.setUsername(environment.getRequiredProperty("hibernate.connection.username"));
        dataSource.setPassword(environment.getRequiredProperty("hibernate.connection.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("ru.hetoiiblpb.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    public static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
            } catch (IOException e) {
                e.printStackTrace();
                throw new IllegalStateException();
            }
        }
        return properties;
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }



}