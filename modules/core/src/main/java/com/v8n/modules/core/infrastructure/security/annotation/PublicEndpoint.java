package com.v8n.modules.core.infrastructure.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark endpoints as public.
 * Endpoints annotated with @PublicEndpoint will bypass Spring Security authentication.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PublicEndpoint {
}
