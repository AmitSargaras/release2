/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/base/techinfra/util/SimpleReflectHelper.java,v 1.1.2.1 2007/03/27 07:40:44 jychong Exp $
 */

package com.integrosys.base.techinfra.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Purpose: to serve the set value into setXXX method Description: this class
 * only support value to be set in String type which will be converted
 * internally
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.1.2.1 $
 * @since $Date: 2007/03/27 07:40:44 $ Tag: $Name: cms_prod_r_1_5 $
 */
public abstract class SimpleReflectHelper {

	private static HashMap primitiveClassMap;

	static {
		String[] primitiveType = new String[] { "boolean", "byte", "char", "short", "int", "long", "float", "double" };
		Class[] primitiveWrapperClz = new Class[] { Boolean.class, Byte.class, Character.class, Short.class,
				Integer.class, Long.class, Float.class, Double.class };
		primitiveClassMap = new HashMap();

		for (int i = 0; i < primitiveType.length; i++) {
			primitiveClassMap.put(primitiveType[i], primitiveWrapperClz[i]);
		}
	}

	/**
	 * Description: to set value into aOB by invoke the set method
	 * 
	 * @param value the String value, will be converted internally to suit
	 *        setXXX paramType
	 * @param obObj the OB object which it's setXXX method will be invoked
	 * @param simpleMethodName the method name of XXX
	 */
	public static void setValueInObByMethodName(String value, Object obObj, String simpleMethodName) {
		Class paramTypeClass = getParamTypeClassByGetMethod(obObj.getClass(), "get" + simpleMethodName);
		Method theSetMethod = getMethodByNameAndSingleParamType("set" + simpleMethodName, paramTypeClass, obObj
				.getClass());

		try {
			Object param = getParamObjectForStrValueByParamType(paramTypeClass, value);
			if (theSetMethod.invoke(obObj, new Object[] { param }) != null) {
				throw new IllegalArgumentException("Void Method return something. " + obObj.getClass().getName() + "."
						+ theSetMethod.getName());
			}
		}
		catch (IllegalAccessException iae) {
			iae.printStackTrace();
			throw new IllegalArgumentException("IllegalAccessException encountered: " + iae.toString());
		}
		catch (InvocationTargetException ite) {
			ite.printStackTrace();
			throw new IllegalArgumentException("InvocationTargetException encountered: " + ite.toString());
		}
	}

	/**
	 * Description: to set value into aOB by invoke the set method
	 * 
	 * @param clazz the OB class
	 * @param startsWith either 'get' or 'set'
	 * @return a HashMap with <String, Method> pair
	 */
	public static HashMap getNameMethodMap(Class clazz, String startsWith) {
		if (!"set".equals(startsWith) && !"get".equals(startsWith)) {
			throw new IllegalArgumentException("Please set \"set\" or \"get\".");
		}

		HashMap hmap = new HashMap();
		Method[] methods = clazz.getMethods();

		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			if (methodName.startsWith(startsWith)) {
				Class[] paramTypes = methods[i].getParameterTypes();
				if (paramTypes.length == 1) {
					hmap.put(methodName.substring(3), methods[i]);
				}
			}
		}

		return hmap;
	}

	/**
	 * Description: to get parameter type class by getter
	 * 
	 * @param clz the OB class
	 * @param methodName the getter method name, must start with "get"
	 * @return class of parameter type
	 */
	public static Class getParamTypeClassByGetMethod(Class clz, String methodName) {
		if (!methodName.startsWith("get")) {
			throw new IllegalArgumentException("[" + methodName + "] not a Getter method.");
		}

		try {
			Method getterMethod = clz.getMethod(methodName, (Class[])null);
			return getterMethod.getReturnType();
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException("NoSuchMethodException encountered: " + methodName + " not found in "
					+ clz.getName() + ".");
		}
	}

	protected static Class getParamTypeClassByStrKey(String key) {
		try {
			Class primitiveWrapperClz = (Class) primitiveClassMap.get(key);
			if (primitiveWrapperClz != null) {
				Field typeField = primitiveWrapperClz.getField("TYPE");
				DefaultLogger.debug("com.integrosys.base.techinfra.util.SimpleReflectHelper", "typeField = "
						+ typeField);
				return (Class) typeField.get(null);
			}
			return Class.forName(key);
		}
		catch (ClassNotFoundException cnfe) {
			throw new IllegalArgumentException("Can't locate class: " + key + ". Please check classpath.");
		}
		catch (NoSuchFieldException nsfe) {
			throw new IllegalArgumentException("Can't locate TYPE field for primitive wrapper class: " + key + ".");
		}
		catch (IllegalAccessException iae) {
			throw new IllegalArgumentException("Can't access TYPE field for primitive wrapper class: " + key + ".");
		}
	}

	protected static Method getMethodByNameAndSingleParamType(String methodName, Class paramType, Class clazz) {
		try {
			return clazz.getMethod(methodName, new Class[] { paramType });
		}
		catch (NoSuchMethodException nsme) {
			throw new IllegalArgumentException("NoSuchMethodException encountered: " + methodName + " not found in "
					+ clazz.getName() + ", for paramType: " + paramType.getName());
		}
	}

	protected static Object getParamObjectForStrValueByParamType(Class paramType, String value)
			throws IllegalAccessException, InvocationTargetException {
		String paramTypeName = paramType.getName();
		if (paramType.isPrimitive()) {
			Class clazz = (Class) primitiveClassMap.get(paramTypeName);
			DefaultLogger.debug("com.integrosys.base.techinfra.util.SimpleReflecrHelper", "clazz = " + clazz
					+ " value = " + value);
			try {
				Constructor conztructor = clazz.getConstructor(new Class[] { String.class });
				DefaultLogger.debug("com.integrosys.base.techinfra.util.SimpleReflecrHelper", "conztructor = "
						+ conztructor);
				return conztructor.newInstance(new Object[] { value });
			}
			catch (NoSuchMethodException nsme) {
				throw new IllegalArgumentException("NoSuchMethodException encountered: " + nsme.toString());
			}
			catch (InstantiationException ie) {
				throw new IllegalArgumentException("InstantiationException encountered: " + ie.toString());
			}
		}
		else {
			if ("java.lang.String".equals(paramTypeName)) {
				return value;
			}
			else if ("java.util.Date".equals(paramTypeName)) {
				String dateFormat = PropertyManager.getValue("feed.date.format", "yyyyMMdd");
				DefaultLogger.debug("com.integrosys.base.techinfra.util.SimpleReflectHelper Value ", value);
				DefaultLogger.debug("com.integrosys.base.techinfra.util.SimpleReflectHelper", "PARSING DATE"
						+ DateUtil.parseDate(dateFormat, value));
				if ((value == null) || value.trim().equals("")) {
					return null;
				}
				else {
					return DateUtil.parseDate(dateFormat, value);
				}
			}
			else {
				throw new IllegalArgumentException("Unknown Parameter Type: " + paramType + " | value: " + value);
			}
		}
	}
}