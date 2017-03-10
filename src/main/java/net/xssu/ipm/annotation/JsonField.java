package net.xssu.ipm.annotation;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bonult 自定义Field转换，使用前需要先注册转换器
 */
@Target({ FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonField {

	String convert();

	String note() default "";

	byte value() default 0;

}
