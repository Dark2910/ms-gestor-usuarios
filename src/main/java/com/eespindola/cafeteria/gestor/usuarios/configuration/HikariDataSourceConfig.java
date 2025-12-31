package com.eespindola.cafeteria.gestor.usuarios.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:connection.properties")
public class HikariDataSourceConfig {

  @Primary
  @Bean("hikariDataSourceProperties")
  @ConfigurationProperties(prefix = "oracle")
  public DataSourceProperties oracleDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean("hikariDataSource")
  public HikariDataSource oracleDataSource(
          @Qualifier("hikariDataSourceProperties") DataSourceProperties properties
  ) {
    return properties
            .initializeDataSourceBuilder()
            .type(HikariDataSource.class)
            .build();
  }

  @Primary
  @Bean("hikariTemplate")
  public JdbcTemplate oracleJdbcTemplate(@Qualifier("hikariDataSource") DataSource dataSource) {
    return new JdbcTemplate(dataSource);
  }

}
