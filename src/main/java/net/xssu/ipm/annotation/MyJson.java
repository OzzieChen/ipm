package net.xssu.ipm.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bonult 
 * <i>类前边加上此注解才能进行josn化</i>
 */
@Target({ TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MyJson {

	String value() default "";
}
