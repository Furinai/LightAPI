package cn.linter.light.config;

import cn.linter.light.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring MVC 配置
 *
 * @author wangxiaoyang
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {

    public static final String ALL_PATTERN = "*";

    private final AuthInterceptor authInterceptor;

    @Value("${light.auth.path}")
    private String[] authPath;

    @Value("${light.auth.excluded-path}")
    private String[] authExcludedPath;

    @Value("${light.cors.mapping}")
    private String corsMapping;

    @Value("${light.cors.allowed-origins}")
    private String[] corsAllowedOrigins;

    @Value("${light.cors.allowed-methods}")
    private String[] corsAllowedMethods;

    @Value("${light.cors.allowed-headers}")
    private String[] corsAllowedHeaders;

    public WebConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns(authPath)
                .excludePathPatterns(authExcludedPath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping(corsMapping);
        if (validPattern(corsAllowedOrigins)) {
            corsRegistration.allowedOrigins(corsAllowedOrigins);
        }
        if (validPattern(corsAllowedMethods)) {
            corsRegistration.allowedMethods(corsAllowedMethods);
        }
        if (validPattern(corsAllowedHeaders)) {
            corsRegistration.allowedHeaders(corsAllowedHeaders);
        }
    }

    private boolean validPattern(String[] corsAllowedConfig) {
        return corsAllowedConfig.length > 0 && !ALL_PATTERN.equals(corsAllowedConfig[0]);
    }

}