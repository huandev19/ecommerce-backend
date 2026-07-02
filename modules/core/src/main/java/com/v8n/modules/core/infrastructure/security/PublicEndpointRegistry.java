package com.v8n.modules.core.infrastructure.security;

import com.v8n.modules.core.infrastructure.security.annotation.PublicEndpoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Registry that collects all @PublicEndpoint methods at startup
 * and provides an efficient request matching mechanism.
 * This replaces the runtime HandlerMappingIntrospector approach
 * which can be unreliable for nested path variables.
 */
@Component
public class PublicEndpointRegistry implements InitializingBean {

    private final RequestMappingHandlerMapping requestMappingHandlerMapping;
    private final Set<AntPathRequestMatcher> publicEndpointMatchers = new HashSet<>();

    public PublicEndpointRegistry(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
    }

    @Override
    public void afterPropertiesSet() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (handlerMethod.hasMethodAnnotation(PublicEndpoint.class)) {
                RequestMappingInfo mappingInfo = entry.getKey();
                // Spring MVC 3.x+ may use getPathPatternsCondition instead of getPatternsCondition
                if (mappingInfo.getPathPatternsCondition() != null) {
                    mappingInfo.getPathPatternsCondition().getPatterns()
                            .forEach(pattern -> publicEndpointMatchers.add(new AntPathRequestMatcher(pattern.getPatternString())));
                } else if (mappingInfo.getPatternsCondition() != null) {
                    mappingInfo.getPatternsCondition().getPatterns()
                            .forEach(pattern -> publicEndpointMatchers.add(new AntPathRequestMatcher(pattern)));
                }
            }
        }
    }

    /**
     * Checks if the given request matches any registered @PublicEndpoint path.
     */
    public boolean matches(HttpServletRequest request) {
        return publicEndpointMatchers.stream().anyMatch(matcher -> matcher.matches(request));
    }

    /**
     * Returns the number of registered public endpoints (useful for debugging/logging).
     */
    public int size() {
        return publicEndpointMatchers.size();
    }
}