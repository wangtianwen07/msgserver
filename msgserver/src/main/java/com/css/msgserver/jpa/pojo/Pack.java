package com.css.msgserver.jpa.pojo;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface Pack {
	int order() default 1;
	PackType type() default PackType.inout;
}
