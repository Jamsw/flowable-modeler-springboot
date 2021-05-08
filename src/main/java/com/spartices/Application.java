package com.spartices;

import org.flowable.ui.common.security.ApiHttpSecurityCustomizer;
import org.flowable.ui.common.security.DefaultApiHttpSecurityCustomizer;
import org.flowable.ui.common.security.FlowableUiSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@SpringBootApplication(exclude = {FlowableUiSecurityAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(
            prefix = "flowable.common.app.security",
            name = {"type"},
            havingValue = "idm",
            matchIfMissing = true
    )
    public ApiHttpSecurityCustomizer defaultApiHttpSecurityCustomizer() {
        return new DefaultApiHttpSecurityCustomizer();
    }
}
