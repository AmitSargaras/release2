package com.integrosys.base.techinfra.context;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.web.context.ContextLoader;

/**
 * <p>
 * Utility class for easier accessing Spring Application Context. Not supposed
 * to be called under a environment having dependency injection capabilities,
 * but sometimes we need to use this BeanHouse to retrieve the bean registered
 * in the Spring context, inside a bean/object not manageable by the context,
 * such as EJB, servlet's component.
 * 
 * <p>
 * This BeanHouse will retrieve the Spring's context from
 * {@link #setApplicationContext(ApplicationContext)} or
 * {@link #setOwnApplicationContext(ApplicationContext)} or
 * {@link org.springframework.web.context.ContextLoader#getCurrentWebApplicationContext()}
 * 
 * <p>
 * For Unit Testing, if BeanHouse is required, please consider to use
 * {@link org.springframework.context.support.GenericApplicationContext} and put
 * into {@link #setOwnApplicationContext(ApplicationContext)}.
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * 
 */
public final class BeanHouse implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(BeanHouse.class);

	/** The application context loaded */
	private static ApplicationContext ownApplicationContext;

	/**
	 * To return the bean object given the bean name provided, return null if
	 * there is no bean registered under the 'beanName' supplied.
	 * 
	 * @param beanName the bean name associated to the bean object required.
	 * @return bean object managed by application context, <code>null</code> if
	 *         there no bean registered for the beanName.
	 */
	public static Object get(String beanName) {
		try {
			return getApplicationContext().getBean(beanName);
		}
		catch (NoSuchBeanDefinitionException e) {
			logger.warn("There is no bean definition registered for the beanName supplied [" + beanName
					+ "], returning 'null'.");
			return null;
		}
	}

	/**
	 * To indicate whether the bean object given the bean name provided is
	 * singleton object
	 * 
	 * @param beanName the bean name associated to the bean object required.
	 * @return whether bean object is singleton object
	 */
	public static boolean isSingleton(String beanName) {
		return getApplicationContext().isSingleton(beanName);
	}

	/**
	 * Return all bean definition names of the beans managed by the application
	 * context
	 * 
	 * @return all bean definition names
	 */
	public static String[] getNames() {
		return getApplicationContext().getBeanDefinitionNames();
	}

	/**
	 * To return all the bean names for the class type provided
	 * 
	 * @param cls class of the bean required
	 * @return all the bean names managed by the application context which are
	 *         instance of the class provided
	 */
	public static String[] getNamesForType(Class cls) {
		return getApplicationContext().getBeanNamesForType(cls);
	}

	/**
	 * Retrieve the resources which can be further used in the application, it
	 * can be any resource in the server.
	 * 
	 * @param locationPattern ant pattern of the resource location
	 * @return all the resources match the ant pattern location supplied
	 * @throws IllegalStateException if there is any i/o error when retrieving
	 *         the resources
	 */
	public static Resource[] getResources(String locationPattern) throws IllegalStateException {
		try {
			return getApplicationContext().getResources(locationPattern);
		}
		catch (IOException e) {
			logger.warn("There is no any resource match the location pattern supplied [" + locationPattern
					+ "], returning 'null'.");
			return null;
		}
	}

	/**
	 * Refresh application context, especially after a reload or rewrite of
	 * property file, context need to be refreshed to take the latest value for
	 * the beans.
	 */
	public static void refreshContext() {
		synchronized (ownApplicationContext) {
			if (ownApplicationContext instanceof ConfigurableApplicationContext) {
				logger.info("Starting to refresh Application Context");
				((ConfigurableApplicationContext) ownApplicationContext).refresh();
			}
		}
	}

	/**
	 * Probably to be used when doing unit testing, and inject instance of
	 * {@link org.springframework.context.support.GenericApplicationContext}.
	 * 
	 * @param context instance of {@link ApplicationContext}
	 * @see org.springframework.context.support.GenericApplicationContext
	 */
	public static void setOwnApplicationContext(ApplicationContext context) {
		ownApplicationContext = context;
	}

	protected static ApplicationContext getApplicationContext() {
		return (ownApplicationContext == null) ? ContextLoader.getCurrentWebApplicationContext()
				: ownApplicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		BeanHouse.ownApplicationContext = applicationContext;
	}

}