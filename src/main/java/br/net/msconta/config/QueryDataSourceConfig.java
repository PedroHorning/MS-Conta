package br.net.msconta.config;

import javax.sql.DataSource;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "br.net.msconta.repository",
        entityManagerFactoryRef = "queryEntityManagerFactory",
        transactionManagerRef = "queryTransactionManager"
)
public class QueryDataSourceConfig {

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
                Map<String, Object> properties = new HashMap<>();
                properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            return builder
                    .dataSource(dataSource)
                    .packages("br.net.msconta.model.Conta")
                    .persistenceUnit("queryPU")
                    .properties(properties)
                    .build();
        }

        @Primary
        @Bean(name = "queryTransactionManager")
        public PlatformTransactionManager queryTransactionManager(@Qualifier("queryEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
}


