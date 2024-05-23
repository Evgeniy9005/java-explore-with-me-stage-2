package ru.practicum;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import javax.sql.DataSource;

//@PropertySource("classpath:/ewm-stats/service/src/main/resources/application.properties")
//@PropertySource("/ewm-stats/service/src/main/resources/application.properties")
//@PropertySource("classpath:app.properties")
//ewm-stats/service/src/main/resources
//@Configuration
public class Config implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        factory.setPort(9090);
        //spring.datasource.url
    }

   /* @Bean()
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.url("jdbc:h2:file:./ewm-stats/service/data/stats");
       // dataSourceBuilder.url("jdbc:h2:file:./data/stats");
        dataSourceBuilder.username("test");
        dataSourceBuilder.password("test");
        return dataSourceBuilder.build();
    }*/

  /*  @Bean
    public static PropertySourcesPlaceholderConfigurer config() {
        return new PropertySourcesPlaceholderConfigurer();
    }*/
}