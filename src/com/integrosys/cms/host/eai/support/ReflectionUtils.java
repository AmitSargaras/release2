package com.integrosys.cms.host.eai.support;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.integrosys.cms.host.eai.EAIProcessFailedException;

/**
 * Helper to do reflections on the object, mainly, copying object properties
 * from a source to a target, and copy value from one property to another one
 * for a same object.
 * @author Chong Jun Yong
 * 
 */
public abstract class ReflectionUtils {

	/**
	 * <p>
	 * Based on the list of properties given, copy the value corresponding to
	 * the properties from a source to a target object.
	 * <p>
	 * Should consider to copy simple value over from source to target, instead
	 * of copy a source property which is a subclass of
	 * <tt>java.util.Collection</tt> to target. This shouldn't happen, instead,
	 * loop through the collection, and copy one by one.
	 * @param source a source object to copy its value from
	 * @param target a target object to be copied the value to
	 * @param properties list of properties required to read the value from
	 *        source and to target
	 */
	public static void copyValuesByProperties(Object source, Object target, Collection properties) {
		for (Iterator iterator = properties.iterator(); iterator.hasNext();) {
			String property = (String) iterator.next();

			try {
				if (StringUtils.contains(property, '.')) {
					String parentProperty = getParentProperty(property);
					if (PropertyUtils.getProperty(source, parentProperty) != null) {
						if (PropertyUtils.getProperty(target, parentProperty) == null) {
							// getting parent class, and instantiate a new one.
							Object parent = PropertyUtils.getProperty(source, parentProperty);
							Class parentClass = parent.getClass();
							Object newParent = BeanUtils.instantiateClass(parentClass);

							PropertyUtils.setProperty(target, parentProperty, newParent);
							PropertyUtils.setProperty(target, property, PropertyUtils.getProperty(source, property));
						}
						else {
							PropertyUtils.setProperty(target, property, PropertyUtils.getProperty(source, property));
						}
					}
				}
				else {
					PropertyUtils.setProperty(target, property, PropertyUtils.getProperty(source, property));
				}
			}
			catch (InvocationTargetException ex) {
				throw new EAIProcessFailedException("failed to copy value from source [" + source + "] to target ["
						+ target + "], for property [" + property + "]", ex.getCause());
			}
			catch (Exception ex) {
				throw new EAIProcessFailedException("failed to copy value from source [" + source + "] to target ["
						+ target + "], for property [" + property + "]", ex);
			}
		}
	}

	/**
	 * For an object, copy it's value from one property to another property,
	 * both type must be same, else unexpected exception will be raised.
	 * @param target a target object to copy it's value
	 * @param fromProperty property to be read from
	 * @param toProperty property to be copied to
	 */
	public static void copyValuesWithinObject(Object target, String fromProperty, String toProperty) {
		PropertyDescriptor readPd = BeanUtils.getPropertyDescriptor(target.getClass(), fromProperty);
		PropertyDescriptor writePd = BeanUtils.getPropertyDescriptor(target.getClass(), toProperty);

		try {
			Object fromValue = readPd.getReadMethod().invoke(target, new Object[0]);
			writePd.getWriteMethod().invoke(target, new Object[] { fromValue });
		}
		catch (IllegalArgumentException ex) {
			throw new EAIProcessFailedException("cannot copy value from property [" + fromProperty + "] to property ["
					+ toProperty + "] on class [" + target.getClass() + "]", ex);
		}
		catch (IllegalAccessException ex) {
			throw new EAIProcessFailedException("failed to access either from property [" + fromProperty
					+ "] or to property [" + toProperty + "] on class [" + target.getClass() + "]", ex);
		}
		catch (InvocationTargetException ex) {
			throw new EAIProcessFailedException("failed to invoke method on either from property [" + fromProperty
					+ "] or to property [" + toProperty + "] on class [" + target.getClass() + "]", ex.getCause());
		}
	}

	/**
	 * For every object in the targes given, copy it's value from one property
	 * to another property, both type must be same, else unexpected exception
	 * will be raised.
	 * @param targets a collection of objects
	 * @param fromProperty property to be read from
	 * @param toProperty property to be copied to
	 */
	public static void copyValuesWithinObjects(Collection targets, String fromProperty, String toProperty) {
		if (targets != null && !targets.isEmpty()) {
			for (Iterator itr = targets.iterator(); itr.hasNext();) {
				copyValuesWithinObject(itr.next(), fromProperty, toProperty);
			}
		}
	}

	private static String getParentProperty(String property) {
		String[] str = StringUtils.split(property, ".");
		if (str.length > 0) {
			return str[0];
		}
		else {
			return null;
		}

	}
}