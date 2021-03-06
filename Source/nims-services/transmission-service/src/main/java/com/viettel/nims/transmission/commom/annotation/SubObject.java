package com.viettel.nims.transmission.commom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubObject {

	final int NUM_DEFAULT = -1;

	final String STRING_DEFAULT = "";

	String name() default STRING_DEFAULT;

	int level() default NUM_DEFAULT;

	String nameParent() default STRING_DEFAULT;

	String type() default STRING_DEFAULT;
}
