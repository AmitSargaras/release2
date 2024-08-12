/**
 * 
 */
package com.integrosys.cms.app.ws.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author bhushan.malankar
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface CLIMSWebServiceMethod {

}
