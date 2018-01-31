package com.processingcenter.processingcenter.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by davlet on 1/31/18.
 */
@Configuration
@EnableWebMvc
@EnableJpaRepositories("com.processingcenter.processingcenter.repositories")
public class AppConfiguration {
    @Bean
    DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/pcdb");
        dataSource.setUsername("postgres");
        dataSource.setPassword("1");
        return dataSource;
    }
}
