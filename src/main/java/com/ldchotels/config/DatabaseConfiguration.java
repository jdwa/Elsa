package com.ldchotels.config;

import io.github.jhipster.config.JHipsterConstants;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories("com.ldchotels.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {
    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    @Autowired
    private Environment env;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        log.info("Default DataSource : " + env.getProperty("spring.datasource.url"));
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(defaultDataSource()).packages("com.ldchotels.domain").persistenceUnit("default").build();
    }

    @Bean(name = "transactionManager")
    @Primary
    public JpaTransactionManager defaultTransactionManager(
        @Qualifier("entityManagerFactory") final LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean
    @ConfigurationProperties("protel.datasource")
    public DataSourceProperties protelDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("protel.datasource")
    public DataSource protelDataSource() {
        log.info("Protel DataSource : " + env.getProperty("protel.datasource.url"));
        return protelDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "protelEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean protelEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder.dataSource(protelDataSource()).packages("com.ldchotels.domain.protel").persistenceUnit("protel").build();
    }

    @Bean(name = "protelTransactionManager")
    public JpaTransactionManager protelTransactionManager(
        @Qualifier("protelEntityManagerFactory") final LocalContainerEntityManagerFactoryBean protelEntityManagerFactory
    ) {
        return new JpaTransactionManager(protelEntityManagerFactory.getObject());
    }
}
