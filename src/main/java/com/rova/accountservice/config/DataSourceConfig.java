package com.rova.accountservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static com.rova.accountservice.util.Constants.DRIVER_CLASS_NAME;
import static com.rova.accountservice.util.Constants.JDBC_H2_URL;

public class DataSourceConfig {

    @Primary
    @Bean(name = "primaryDataSource")
    public DataSource dataSource() throws JsonProcessingException {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(DRIVER_CLASS_NAME);
        dataSourceBuilder.url(JDBC_H2_URL);
        return dataSourceBuilder.build();
    }
}
