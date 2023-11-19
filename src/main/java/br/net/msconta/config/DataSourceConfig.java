package br.net.msconta.config;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

        // Configuração para o banco de dados de consulta
        @Primary
        @Bean(name = "queryDataSource")
        @ConfigurationProperties(prefix = "spring.datasource")
        public DataSource queryDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Primary
        @Bean(name = "queryEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("queryDataSource") DataSource dataSource) {
            return builder
                    .dataSource(dataSource)
                    .packages("br.net.msconta.model.Conta")
                    .persistenceUnit("queryPU")
                    .build();
        }

        @Primary
        @Bean(name = "queryTransactionManager")
        public PlatformTransactionManager queryTransactionManager(@Qualifier("queryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }

        // Configuração para o banco de dados de escrita
        @Bean(name = "writerDataSource")
        @ConfigurationProperties(prefix = "spring.second-datasource")
        public DataSource writerDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean(name = "writerEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean writerEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("writerDataSource") DataSource dataSource) {
            return builder
                    .dataSource(dataSource)
                    .packages("br.net.msconta.model.Movimentacao")
                    .persistenceUnit("writerPU")
                    .build();
        }

        @Bean(name = "writerTransactionManager")
        public PlatformTransactionManager writerTransactionManager(@Qualifier("writerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
}


