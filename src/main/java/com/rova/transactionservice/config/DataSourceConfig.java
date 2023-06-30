package com.rova.transactionservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

import static com.rova.transactionservice.util.Constants.DRIVER_CLASS_NAME;
import static com.rova.transactionservice.util.Constants.JDBC_H2_URL;
@Configuration
@RequiredArgsConstructor
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
