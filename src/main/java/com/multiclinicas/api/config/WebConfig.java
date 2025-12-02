package com.multiclinicas.api.config;

import com.multiclinicas.api.config.tenant.TenantInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantInterceptor)
                // Aplica a validação em todas as rotas da API
                .addPathPatterns("/**")
                // Exclui rotas que não dependem de contexto de clínica
                .excludePathPatterns(
                        "/clinicas/**",
                        "/actuator/**");
    }
}
