package org.adamrduffy.leselec

import org.apache.commons.dbcp2.BasicDataSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter

@SpringBootApplication
class Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class)

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Bean
    UrlRewriteFilter rewriteFilter() {
        return new UrlRewriteFilter()
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
}
