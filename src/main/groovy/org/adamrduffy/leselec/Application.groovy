package org.adamrduffy.leselec

import org.apache.commons.dbcp2.BasicDataSource
import org.hibernate.SessionFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.core.env.Environment
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter

@SpringBootApplication
class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class)

    @Autowired
    Environment env

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Bean
    UrlRewriteFilter rewriteFilter() {
        return new UrlRewriteFilter()
    }

    @Bean
    LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean()
        sessionFactory.setDataSource(dataSource())
        sessionFactory.setPackagesToScan("org.adamrduffy.leselec.dao")

        return sessionFactory
    }

    @Bean
    BasicDataSource dataSource() throws URISyntaxException {
        def databaseUrl = System.getenv("DATABASE_URL")
        LOGGER.info databaseUrl

        URI dbUri = new URI(databaseUrl)

        String username = dbUri.getUserInfo().split(":")[0]
        String password = dbUri.getUserInfo().split(":")[1]
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()

        BasicDataSource basicDataSource = new BasicDataSource()
        basicDataSource.setUrl(dbUrl)
        basicDataSource.setUsername(username)
        basicDataSource.setPassword(password)

        return basicDataSource
    }

    @Bean
    @Autowired
    HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager()
        txManager.setSessionFactory(sessionFactory)
        return txManager
    }

    @Bean
    PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor()
    }
}
