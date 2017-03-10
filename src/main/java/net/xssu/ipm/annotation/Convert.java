package net.xssu.ipm.annotation;


/**
 * @author bonult
 * @description TODO
 * @version v1.0
 * @date 2016年12月8日 下午10:06:21
 */
//@FunctionalInterface
public interface Convert {

	String transform(Object field, String convert, String note);

}
