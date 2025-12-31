package com.eespindola.cafeteria.gestor.usuarios.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
public class DataSourceConfig {

  @Bean("jdbcDataSourceProperties")
  @ConfigurationProperties(prefix = "oracle")
  public DataSourceProperties oracleDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean("jdbcDataSource")
  public DataSource oracleDataSource(
          @Qualifier("jdbcDataSourceProperties") DataSourceProperties properties
  ) {
    return properties
            .initializeDataSourceBuilder()
            .build();
  }

  @Bean("jdbcTemplate")
  public JdbcTemplate oracleJdbcTemplate(@Qualifier("jdbcDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
