package br.net.msconta.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "br.net.msconta.repository",
        entityManagerFactoryRef = "writerEntityManagerFactory",
        transactionManagerRef = "writerTransactionManager"
)
public class WriterDataSourceConfig {

        // Configuração para o banco de dados de escrita
        @Bean(name = "writerDataSource")
        @ConfigurationProperties(prefix = "spring.second-datasource")
        public DataSource writerDataSource() {
            return DataSourceBuilder.create().build();
        }

        @Bean(name = "writerEntityManagerFactory")
        public LocalContainerEntityManagerFactoryBean writerEntityManagerFactory(EntityManagerFactoryBuilder builder, @Qualifier("writerDataSource") DataSource dataSource) {
            Map<String, Object> properties = new HashMap<>();
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            return builder
                    .dataSource(dataSource)
                    .packages("br.net.msconta.model.Movimentacao")
                    .persistenceUnit("writerPU")
                    .properties(properties)
                    .build();
        }

        @Bean(name = "writerTransactionManager")
        public PlatformTransactionManager writerTransactionManager(@Qualifier("writerEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
            return new JpaTransactionManager(entityManagerFactory);
        }
}


