package org.adamrduffy.leselec

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter

@SpringBootApplication
class Application {
    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }

    @Bean
    UrlRewriteFilter rewriteFilter() {
        return new UrlRewriteFilter()
    }
}
