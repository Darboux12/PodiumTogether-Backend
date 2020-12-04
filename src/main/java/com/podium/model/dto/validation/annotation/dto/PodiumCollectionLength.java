package com.podium.model.dto.validation.annotation.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PodiumCollectionLength {
    int min() default 0;
    int max() default 100000;
}
