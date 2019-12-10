package com.viettel.nims.transmission.commom.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SheetSerializable {
	final String STRING_DEFAUL ="";
	final int NUM_DEFAULT = 0;

	int sheetDataIndex() default NUM_DEFAULT;

	int startRow() default NUM_DEFAULT;

  int totalCols() default NUM_DEFAULT;

  int resultCols() default  NUM_DEFAULT;

  int flagRow() default  NUM_DEFAULT;





}
