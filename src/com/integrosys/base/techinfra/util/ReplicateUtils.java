package com.integrosys.base.techinfra.util;

import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.Validate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ClassUtils;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Utility to replicate a stateful value object get from persistence storage to
 * a stateless object. For <tt>java.util.Map</tt>, can consider to use Apache
 * Commons PropertyUtils
 * 
 * @author Chong Jun Yong
 * @since 1.1
 * @see org.springframework.beans.BeanUtils
 */
public abstract class ReplicateUtils {

	private static final Log logger = LogFactory.getLog(ReplicateUtils.class);

	/**
	 * Default Classes to be included for replication, ie.
	 * <ul>
	 * <li> <code>java.lang.String</code>
	 * <li> <code>java.util.Date</code>
	 * <li> <code>java.math.BigDecimal</code>
	 * <li> <code>com.integrosys.base.businfra.currency.Amount</code>
	 * <ul>
	 */
	private static final Class[] DEFAULT_EXCEPTION_CLASS = new Class[] { String.class, Date.class, BigDecimal.class,
			Amount.class };

	/**
	 * <p>
	 * Replicate object for <b>primitive type, {@link java.lang.String},
	 * {@link java.util.Date}, {@link java.math.BigDecimal} and
	 * {@link com.integrosys.base.businfra.currency.Amount} only</b> properties.
	 * So, property of bean with complex type, array type, collection type will
	 * be ignored.
	 * 
	 * <p>
	 * For complex type, <b>array type</b>, use this method separately. And for
	 * Collections type, use
	 * {@link #replicateCollectionObject(Collection, String[])} or
	 * {@link #replicateCollectionObjectWithSpecifiedImplClass(Collection, String[])}
	 * 
	 * <p>
	 * <b>NOTE</b> Instead of this 'hard-coded' replication for certain class
	 * type, please consider use
	 * {@link #replicateCollectionObject(Collection, String[], Class[])} for
	 * precise class type not to be filtered
	 * 
	 * @param object the object to be replicated
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @return the replicated the object, it's a new object
	 * @see #getAllCollectionTypedPropertyNames(Map)
	 * @see #getAllNonPrimitiveAndNonCollectionTypedPropertyNames(Map)
	 * @see #replicateCollectionObject(Collection, String[], Class[])
	 */
	public static Object replicateObject(Object object, String[] ignoredProperties) {
		return replicateObject(object, ignoredProperties, DEFAULT_EXCEPTION_CLASS);
	}

	/**
	 * <p>
	 * Replicate object for <b>primitive type only</b> properties unless
	 * parameter exceptionClass is given. So, property of bean with complex
	 * type, array type, collection type will be ignored.
	 * 
	 * <p>
	 * For complex type, <b>array type</b>, use this method separately. And for
	 * Collections type, use
	 * {@link #replicateCollectionObject(Collection, String[], Class[])} or
	 * {@link #replicateCollectionObjectWithSpecifiedImplClass(Collection, String[], Class[])}
	 * 
	 * @param object the object to be replicated
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @param exceptionClasses array of class object which will be the exception
	 *        class other than primitive class type, when doing replication
	 * @return the replicated the object, it's a new object
	 * @see #getAllCollectionTypedPropertyNames(Map)
	 * @see #getAllNonPrimitiveAndNonCollectionTypedPropertyNames(Map)
	 */
	public static Object replicateObject(Object object, String[] ignoredProperties, Class[] exceptionClasses) {
		Validate.notNull(object, "'target' must not be null.");

		if (ignoredProperties == null) {
			ignoredProperties = new String[0];
		}

		Map propertiesNameTypeMap = preparePropertiesNameTypeMap(object.getClass());

		List allIgnoredPropertyList = new ArrayList();

		String[] collectionTypedPropertyNames = getAllCollectionTypedPropertyNames(propertiesNameTypeMap);
		String[] nonPrimitiveAndNonCollectionTypedPropertyNames = getAllNonPrimitiveAndNonCollectionTypedPropertyNames(
				propertiesNameTypeMap, exceptionClasses);

		allIgnoredPropertyList.addAll(Arrays.asList(collectionTypedPropertyNames));
		allIgnoredPropertyList.addAll(Arrays.asList(nonPrimitiveAndNonCollectionTypedPropertyNames));
		allIgnoredPropertyList.addAll(Arrays.asList(ignoredProperties));

		Object target = BeanUtils.instantiateClass(object.getClass());
		BeanUtils.copyProperties(object, target, (String[]) allIgnoredPropertyList.toArray(new String[0]));

		return target;
	}

	/**
	 * <p>
	 * Replicate object for <b>Collection type only</b> properties. All objects
	 * in the collections have to be same class type.
	 * 
	 * <p>
	 * For each object in the collection, it will do a
	 * {@link #replicateObject(Object, String[])} and put in a new collection
	 * 
	 * @param collections collection of objects to be created
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @return replicated object collections, return <code>null</code> if <code>
	 *         collection</code>
	 *         is empty or null
	 * @see #replicateObject(Object, String[])
	 */
	public static Collection replicateCollectionObject(Collection collection, String[] ignoredProperties) {
		return replicateCollectionObject(collection, ignoredProperties, null);
	}

	/**
	 * <p>
	 * Replicate object for <b>Collection type only</b> properties. All objects
	 * in the collections have to be same class type.
	 * 
	 * <p>
	 * For each object in the collection, it will do a
	 * {@link #replicateObject(Object, String[], Class[])} and put in a new
	 * collection
	 * 
	 * @param collections collection of objects to be created
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @param exceptionClasses array of class object which will be the exception
	 *        class other than primitive class type, when doing replication
	 * @return replicated object collections, return <code>null</code> if <code>
	 *         collection</code>
	 *         is empty or null
	 * @see #replicateObject(Object, String[], Class[])
	 */
	public static Collection replicateCollectionObject(Collection collection, String[] ignoredProperties,
			Class[] exceptionClasses) {
		if ((collection == null) || (collection.isEmpty())) {
			logger.warn("'collection' is null or empty, return null object");
			return null;
		}

		Object[] objects = collection.toArray();

		Validate.allElementsOfType(collection, objects[0].getClass(),
				"all elements in 'collection' has to be same type of [" + objects[0].getClass() + "]");

		Collection targetCollection = null;

		Class collectionClass = collection.getClass();
		if (SortedSet.class.isAssignableFrom(collectionClass)) {
			SortedSet theSortedSet = (SortedSet) collection;
			Comparator comparator = theSortedSet.comparator();
			targetCollection = new TreeSet(comparator);
		}

		if (targetCollection == null) {
			targetCollection = (Collection) BeanUtils.instantiateClass(collectionClass);
		}

		for (int i = 0; i < objects.length; i++) {
			Object replicatedObject = null;
			if (exceptionClasses == null) {
				replicatedObject = replicateObject(objects[i], ignoredProperties);
			}
			else {
				replicatedObject = replicateObject(objects[i], ignoredProperties, exceptionClasses);
			}
			targetCollection.add(replicatedObject);
		}

		return targetCollection;
	}

	/**
	 * <p>
	 * Replicate object for <b>Collection type only</b> properties.
	 * Implementations class of collection to be instantiate and returned will
	 * be java own type. All objects in the collections have to be same class
	 * type.
	 * 
	 * <p>
	 * Collection <b>must</b> be type of
	 * <ul>
	 * <li>{@link java.util.List}, using implementation class
	 * {@link java.util.ArrayList}
	 * <li>{@link java.util.Set}, using implementation class
	 * {@link java.util.HashSet}
	 * </ul>
	 * 
	 * <p>
	 * For each object in the collection, it will do a
	 * {@link #replicateObject(Object, String[])} and put in a new collection
	 * 
	 * @param collection collection of objects to be created
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @return replicated object collections, return <code>null</code> if <code>
	 *         collection</code>
	 *         is empty or null
	 * @see #replicateObject(Object, String[])
	 * @throws java.lang.IllegalArgumentException if collection type is either
	 *         not {@link java.util.List} or {@link java.util.Set}
	 */
	public static Collection replicateCollectionObjectWithSpecifiedImplClass(Collection collection,
			String[] ignoredProperties) {
		return replicateCollectionObjectWithSpecifiedImplClass(collection, ignoredProperties, null);
	}

	/**
	 * <p>
	 * Replicate object for <b>Collection type only</b> properties.
	 * Implementations class of collection to be instantiate and returned will
	 * be java own type. All objects in the collections have to be same class
	 * type.
	 * 
	 * <p>
	 * Collection <b>must</b> be type of
	 * <ul>
	 * <li>{@link java.util.List}, using implementation class
	 * {@link java.util.ArrayList}
	 * <li>{@link java.util.Set}, using implementation class
	 * {@link java.util.HashSet}
	 * <li>{@link java.util.SortedSet}, using implementation class
	 * {@link java.util.TreeSet} for the same comparator
	 * </ul>
	 * 
	 * <p>
	 * For each object in the collection, it will do a
	 * {@link #replicateObject(Object, String[], Class[])} and put in a new
	 * collection
	 * 
	 * @param collection collection of objects to be created
	 * @param ignoredProperties array of string of properties of a bean to be
	 *        ignored when doing replication
	 * @param exceptionClasses array of class object which will be the exception
	 *        class other than primitive class type, when doing replication
	 * @return replicated object collections, return <code>null</code> if <code>
	 *         collection</code>
	 *         is empty or null
	 * @see #replicateObject(Object, String[], Class[])
	 * @throws java.lang.IllegalArgumentException if collection type is either
	 *         not {@link java.util.List} or {@link java.util.Set}
	 */
	public static Collection replicateCollectionObjectWithSpecifiedImplClass(Collection collection,
			String[] ignoredProperties, Class[] exceptionClasses) {
		if ((collection == null) || (collection.isEmpty())) {
			logger.warn("'collection' is null or empty, return null object");
			return null;
		}

		Collection targetCollection = null;

		Class actualCreatedClass = Class.class;
		Class collectionClass = collection.getClass();
		if (SortedSet.class.isAssignableFrom(collectionClass)) {
			SortedSet theSortedSet = (SortedSet) collection;
			Comparator comparator = theSortedSet.comparator();
			targetCollection = new TreeSet(comparator);
		}
		if (Set.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = HashSet.class;
		}
		else if (List.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = ArrayList.class;
		}
		else {
			throw new IllegalArgumentException("collection class [" + collectionClass + "] not supported currently.");
		}

		Object[] objects = collection.toArray();
		Validate.allElementsOfType(collection, objects[0].getClass(),
				"all elements in 'collection' has to be same type of [" + objects[0].getClass() + "]");

		if (targetCollection == null) {
			targetCollection = (Collection) BeanUtils.instantiateClass(actualCreatedClass);
		}

		for (int i = 0; i < objects.length; i++) {
			Object replicatedObject = null;
			if (exceptionClasses == null) {
				replicatedObject = replicateObject(objects[i], ignoredProperties);
			}
			else {
				replicatedObject = replicateObject(objects[i], ignoredProperties, exceptionClasses);
			}

			targetCollection.add(replicatedObject);
		}

		return targetCollection;
	}

	/**
	 * <p>
	 * To get the non primitive(include <code>java.lang.String</code>) and non
	 * collection typed bean property names based on the property
	 * name-type.class map.
	 * 
	 * <p>
	 * <b>NOTE:</b> If there is any non-primitive classes to be included,
	 * provide it in the <code>exceptClasses</code> parameter
	 * 
	 * @see org.springframework.util.ClassUtils#isPrimitiveOrWrapper(Class)
	 * @param propertiesNameTypeMap bean's property name-type.class map
	 * @param exceptClasses class array to be included, used to check against
	 *        the type.class in the map
	 * @return a string array consists of the non primitive and non collection
	 *         typed bean property names
	 */
	public static String[] getAllNonPrimitiveAndNonCollectionTypedPropertyNames(Map propertiesNameTypeMap,
			Class[] exceptClasses) {
		Validate.notNull(propertiesNameTypeMap, "'propertiesNameTypeMap' must not be null.");

		List propertyNameList = new ArrayList();

		Set entriesSet = propertiesNameTypeMap.entrySet();
		Iterator itrEntries = entriesSet.iterator();

		while (itrEntries.hasNext()) {
			Map.Entry entry = (Map.Entry) itrEntries.next();
			String propertyName = (String) entry.getKey();
			Class propertyTypeClass = (Class) entry.getValue();

			if (!ClassUtils.isPrimitiveOrWrapper(propertyTypeClass) && !propertyTypeClass.isArray()
					&& !Collection.class.isAssignableFrom(propertyTypeClass)
					&& !isClassAssignableWithAnyOfClasses(propertyTypeClass, exceptClasses)) {
				propertyNameList.add(propertyName);
			}
		}

		return (String[]) propertyNameList.toArray(new String[0]);
	}

	/**
	 * To get the collection typed bean property names based on the property
	 * name-type.class map
	 * 
	 * @param propertiesNameTypeMap bean's property name-type.class map
	 * @return a string array consists collection typed bean property names
	 */
	public static String[] getAllCollectionTypedPropertyNames(Map propertiesNameTypeMap) {
		Validate.notNull(propertiesNameTypeMap, "'propertiesNameTypeMap' must not be null.");

		List propertyNameList = new ArrayList();

		Set entriesSet = propertiesNameTypeMap.entrySet();
		Iterator itrEntries = entriesSet.iterator();

		while (itrEntries.hasNext()) {
			Map.Entry entry = (Map.Entry) itrEntries.next();
			String propertyName = (String) entry.getKey();
			Class propertyTypeClass = (Class) entry.getValue();

			if (Collection.class.isAssignableFrom(propertyTypeClass)) {
				propertyNameList.add(propertyName);
			}
		}

		return (String[]) propertyNameList.toArray(new String[0]);
	}

	/**
	 * To get the a bean's property name-type.class map
	 * 
	 * @param target class to be used for getting all properties descriptor of
	 *        the class type itself
	 * @return a map consists of name-class map, which is bean's property
	 *         name-class.type map
	 * @see java.beans.PropertyDescriptor
	 */
	public static Map preparePropertiesNameTypeMap(Class target) {
		Validate.notNull(target, "'target' must not be null.");

		PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(target);

		Map propertiesNameTypeMap = new HashMap();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			String propertyName = propertyDescriptors[i].getName();
			Class propertyType = propertyDescriptors[i].getPropertyType();

			propertiesNameTypeMap.put(propertyName, propertyType);
		}

		return propertiesNameTypeMap;
	}

	/**
	 * To check whether the target class is instance of any class in the array
	 * 
	 * @param target the target class to be validated
	 * @param classes the array consists of class to be used to validate the
	 *        target class
	 * @return true if class is instance of any class in the array, else false
	 */
	protected static boolean isClassAssignableWithAnyOfClasses(Class target, Class[] classes) {
		Validate.notNull(target, "'target' must not be null");

		if (classes == null) {
			return false;
		}

		for (int i = 0; i < classes.length; i++) {
			if ((classes[i].isAssignableFrom(target))) {
				return true;
			}
		}

		return false;
	}

}
