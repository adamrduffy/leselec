package org.adamrduffy.leselec

import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebMvcConfigure extends WebMvcConfigurerAdapter {
    @Override
    void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html")
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE)
        super.addViewControllers(registry)
    }
}
