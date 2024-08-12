package com.integrosys.base.techinfra.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * <p>
 * Utility to manipulate the association of an entity.
 * 
 * <p>
 * Useful when when comparing two collections of two entity. Such as copying
 * from staging copy to actual copy.
 * 
 * @author Chong Jun Yong
 * @author Andy Wong
 * @since 1.1
 * @see org.springframework.beans.BeanUtil
 */
public abstract class EntityAssociationUtils {

	private static final Logger logger = LoggerFactory.getLogger(EntityAssociationUtils.class);

	/**
	 * This will default the boolean "isCreateCopyRequired" to true, i.e. return
	 * both create and update copy together.
	 * 
	 * @param baseCollection base collection of objects, normally the persistent
	 *        objects
	 * @param replicatedCollection replicated objects which copied from
	 *        persistent objects and having new going-to-be-created objects
	 * @param matchingProperties properties of bean to match objects between
	 *        baseCollection and replicatedCollection
	 * @param ignoredProperties ignored properties to be used to copy properties
	 *        from replicated object to base object if properties matched
	 * @see EntityAssociationUtils#synchronizeCollectionsByProperties(Collection,
	 *      Collection, String[], String[], boolean)
	 * 
	 */
	public static Collection synchronizeCollectionsByProperties(Collection baseCollection,
			Collection replicatedCollection, String[] matchingProperties, String[] ignoredProperties) {
		return synchronizeCollectionsByProperties(baseCollection, replicatedCollection, matchingProperties,
				ignoredProperties, true);
	}

	/**
	 * <p>
	 * Synchronize two collection based on properties passed in, this is useful
	 * to match items between staging copy and actual copy.
	 * 
	 * <p>
	 * Iteration will be based on replicatedCollections.
	 * 
	 * <p>
	 * Going-to-be-created objects will be added to the new collection,
	 * meanwhile for matched properties object between replicated and base,
	 * properties of replicated objects will be copied over to base.
	 * 
	 * <p>
	 * If there is empty inside replicatedCollection, <code>null</code> will be
	 * returned.
	 * 
	 * <p>
	 * Return type of collection will be java own collection type. Type to be
	 * determined is based on baseCollection parameter.Collection type currently
	 * supported:
	 * <ul>
	 * <li><code>java.util.List</code>, use <code>java.util.ArrayList</code>
	 * <li><code>java.util.Set</code>, use <code>java.util.HashSet</code>
	 * </ul>
	 * 
	 * <p>
	 * base collection [1, 2, 3, 4, 5] </br> replicated collection [1, 2, 3, 6]
	 * <p>
	 * [1, 2, 3, 6] will be returned, with 1, 2, 3 properties updated to the one
	 * in replicated collection
	 * 
	 * <p>
	 * <b>note:</b> for removed objects need to be updated instead of purged
	 * from persistent storage, ie. tagged to 'deleted', 'D', whatsoever, use
	 * {@link #retrieveRemovedItemsCollection(Collection, Collection, String[])}
	 * to retrieve the objects going to be tagged as deleted.
	 * <ol>
	 * <li>tagged the objects to deleted
	 * <li>add back to merged base collection
	 * </ol>
	 * 
	 * @param baseCollection base collection of objects, normally the persistent
	 *        objects
	 * @param replicatedCollection replicated objects which copied from
	 *        persistent objects and having new going-to-be-created objects
	 * @param matchingProperties properties of bean to match objects between
	 *        baseCollection and replicatedCollection
	 * @param ignoredProperties ignored properties to be used to copy properties
	 *        from replicated object to base object if properties matched
	 * @param isCreateCopyRequired flag to determine whether to add the items to
	 *        be created into the collection to be returned
	 * @return a new collection consists of latest copy of objects to be removed
	 *         or created, <b>note</b>, removed objects will not be in the
	 *         synchronized copy
	 * @throws IllegalArgumentException if there is error when invoking the read
	 *         or write method when doing copy.
	 */
	public static Collection synchronizeCollectionsByProperties(Collection baseCollection,
			Collection replicatedCollection, String[] matchingProperties, String[] ignoredProperties,
			boolean isCreateCopyRequired) {

		if ((replicatedCollection == null) || replicatedCollection.isEmpty()) {
			return null;
		}

		Validate.notEmpty(matchingProperties, "'properties' must not be null.");

		/*
		 * to check before object are being copy from replicated collection to a
		 * new collection.
		 */
		preCheckBeanProperties(replicatedCollection.toArray()[0], ignoredProperties);

		Class actualCreatedClass = Class.class;

		if (baseCollection == null) {
			return replicatedCollection;
		}
		Class collectionClass = baseCollection.getClass();

		if (Set.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = HashSet.class;
		}
		else if (List.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = ArrayList.class;
		}
		else {
			throw new IllegalArgumentException("collection class [" + collectionClass + "] not supported currently.");
		}

		Collection synchronizedCollection = (Collection) BeanUtils.instantiateClass(actualCreatedClass);

		Object[] baseObjects = baseCollection.toArray();
		Object[] replicatedObjects = replicatedCollection.toArray();

		for (int i = 0; i < replicatedObjects.length; i++) {
			Object replicatedObject = replicatedObjects[i];

			boolean foundMatchedBaseObject = false;
			for (int j = 0; (j < baseObjects.length) && !foundMatchedBaseObject; j++) {
				Object baseObject = baseObjects[j];

				boolean propertiesValueMatch = true;
				for (int k = 0; (k < matchingProperties.length) && propertiesValueMatch; k++) {
					try {
						Object replicatedObjectValue = BeanUtils.getPropertyDescriptor(replicatedObject.getClass(),
								matchingProperties[k]).getReadMethod().invoke(replicatedObject, new Object[0]);

						Object baseObjectValue = BeanUtils.getPropertyDescriptor(baseObject.getClass(),
								matchingProperties[k]).getReadMethod().invoke(baseObject, new Object[0]);

						if ((replicatedObjectValue == null) || (baseObjectValue == null)) {
							logger.warn("value to be compared to do matching is 'null' replicated ["
									+ replicatedObjectValue + "] base [" + baseObjectValue + "]");
							propertiesValueMatch = false;
							continue;
						}
						else {
							replicatedObjectValue = (Object) replicatedObjectValue.toString().trim();
							baseObjectValue = (Object) baseObjectValue.toString().trim();
						}

						if (!replicatedObjectValue.toString().equals(baseObjectValue.toString())) {
							propertiesValueMatch = false;
						}
					}
					catch (Throwable t) {
						throw new IllegalArgumentException("error when accessing property [" + matchingProperties[k]
								+ "] of class [" + replicatedObject.getClass() + "]; nested exception is " + t);
					}
				}

				if (propertiesValueMatch) {
					BeanUtils.copyProperties(replicatedObject, baseObject, ignoredProperties);
					synchronizedCollection.add(baseObject);
					foundMatchedBaseObject = true;
				}
			}

			if (!foundMatchedBaseObject && isCreateCopyRequired) {
				synchronizedCollection.add(replicatedObject);
			}
		}

		return synchronizedCollection;
	}

	/**
	 * <p>
	 * Copy primary key value to reference id for all the objects in the
	 * collection, if and only if reference id is less than or equal to zero (0)
	 * 
	 * <p>
	 * Return type of collection will be java own collection type. Collection
	 * type currently supported:
	 * <ul>
	 * <li><code>java.util.List</code>, use <code>java.util.ArrayList</code>
	 * <li><code>java.util.Set</code>, use <code>java.util.HashSet</code>
	 * </ul>
	 * 
	 * <p>
	 * Basically, both class type of primary key and reference id need to be
	 * same type. But it will wrapped to the necessary type of reference id
	 * using toString() method.
	 * 
	 * @param baseCollection collection of objects which undergoing primary key
	 *        value copying
	 * @param referenceIdPropertyName bean's property name of the object for
	 *        reference id
	 * @param referenceIdPropertyType reference id class type
	 * @param primaryKeyPropertyName bean's property name of the object for
	 *        primary key
	 * @return a new collection which have the reference id set properly, else
	 *         <code>null</code> if baseCollection is null or empty
	 * @throws IllegalArgumentException if there is error when invoking the read
	 *         or write method when doing copy.
	 */
	public static Collection copyReferenceIdUsingPrimaryKey(Collection baseCollection, String referenceIdPropertyName,
			Class referenceIdPropertyType, String primaryKeyPropertyName) {
		Validate.notNull(referenceIdPropertyName, "'referenceIdPropertyName' must not be null.");
		Validate.notNull(primaryKeyPropertyName, "'primaryKeyPropertyName' must not be null.");

		if ((baseCollection == null) || baseCollection.isEmpty()) {
			return null;
		}

		Class actualCreatedClass = Class.class;
		Class collectionClass = baseCollection.getClass();
		if (Set.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = HashSet.class;
		}
		else if (List.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = ArrayList.class;
		}
		else {
			throw new IllegalArgumentException("collection class [" + collectionClass + "] not supported currently.");
		}

		Collection updatedCollection = (Collection) BeanUtils.instantiateClass(actualCreatedClass);

		for (Iterator itr = baseCollection.iterator(); itr.hasNext();) {
			Object anObject = itr.next();

			try {
				PropertyDescriptor referenceIdProperty = BeanUtils.getPropertyDescriptor(anObject.getClass(),
						referenceIdPropertyName);

				Object referenceIdvalue = referenceIdProperty.getReadMethod().invoke(anObject, new Object[0]);

				Object primaryKeyValue = BeanUtils.getPropertyDescriptor(anObject.getClass(), primaryKeyPropertyName)
						.getReadMethod().invoke(anObject, new Object[0]);

				if (Long.class.isAssignableFrom(referenceIdPropertyType)) {
					Long longValue = (Long) referenceIdvalue;
					if ((longValue == null) || (longValue.longValue() <= 0)) {
						referenceIdProperty.getWriteMethod().invoke(anObject, new Object[] { primaryKeyValue });
					}
				}
				else if (Integer.class.isAssignableFrom(referenceIdPropertyType)) {
					Integer integerValue = (Integer) referenceIdvalue;
					if ((integerValue == null) || (integerValue.intValue() <= 0)) {
						referenceIdProperty.getWriteMethod().invoke(anObject, new Object[] { primaryKeyValue });
					}
				}
				else {
					logger.warn("not able to determine the reference id class type [" + referenceIdPropertyType + "]");
				}
			}
			catch (Throwable t) {
				throw new IllegalArgumentException("error when accessing property [" + referenceIdPropertyName
						+ "] or [" + primaryKeyPropertyName + "] of class [" + anObject.getClass()
						+ "]; nested exception is " + t);
			}

			updatedCollection.add(anObject);
		}

		return updatedCollection;
	}

	/**
	 * <p>
	 * Retrieve objects being removed in base collection as against to
	 * replicated collection.
	 * 
	 * <p>
	 * base collection [1, 2, 3, 4, 5] </br> replicated collection [1, 2, 3]
	 * <p>
	 * [4, 5] will be returned.
	 * 
	 * @param baseCollection collection of objects which to search for
	 *        going-to-be removed items.
	 * @param replicatedCollection collection of objects to check against
	 * @param matchingProperties properties to match objects between 2
	 *        collections
	 * @return removed objects collection found in base collection
	 * @throws IllegalArgumentException if there is error when invoking the read
	 *         or write method when doing copy.
	 */
	public static Collection retrieveRemovedObjectsCollection(Collection baseCollection,
			Collection replicatedCollection, String[] matchingProperties) {
		Validate.notEmpty(matchingProperties, "'properties' must not be null.");

		if ((replicatedCollection == null) || replicatedCollection.isEmpty()) {
			return baseCollection;
		}

		if (baseCollection == null) {
			return new ArrayList();
		}

		Class actualCreatedClass = Class.class;
		Class collectionClass = baseCollection.getClass();
		if (Set.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = HashSet.class;
		}
		else if (List.class.isAssignableFrom(collectionClass)) {
			actualCreatedClass = ArrayList.class;
		}
		else {
			throw new IllegalArgumentException("collection class [" + collectionClass + "] not supported currently.");
		}

		Collection removedObjectsCollection = (Collection) BeanUtils.instantiateClass(actualCreatedClass);

		Object[] baseObjects = baseCollection.toArray();
		Object[] replicatedObjects = replicatedCollection.toArray();

		for (int i = 0; i < baseObjects.length; i++) {
			Object baseObject = baseObjects[i];

			boolean foundMatchedReplicatedObject = false;
			for (int j = 0; (j < replicatedObjects.length) && !foundMatchedReplicatedObject; j++) {
				Object replicatedObject = replicatedObjects[j];

				boolean propertiesValueMatch = true;
				for (int k = 0; (k < matchingProperties.length) && propertiesValueMatch; k++) {
					try {
						Object replicatedObjectValue = BeanUtils.getPropertyDescriptor(replicatedObject.getClass(),
								matchingProperties[k]).getReadMethod().invoke(replicatedObject, new Object[0]);

						Object baseObjectValue = BeanUtils.getPropertyDescriptor(baseObject.getClass(),
								matchingProperties[k]).getReadMethod().invoke(baseObject, new Object[0]);

						if ((replicatedObjectValue == null) || (baseObjectValue == null)) {
							logger.warn("value to be compared to do matching is 'null' replicated ["
									+ replicatedObjectValue + "] base [" + baseObjectValue + "]");
							continue;
						}
						else {
							replicatedObjectValue = (Object) replicatedObjectValue.toString().trim();
							baseObjectValue = (Object) baseObjectValue.toString().trim();
						}

						if (!replicatedObjectValue.toString().equals(baseObjectValue.toString())) {
							propertiesValueMatch = false;
						}
					}
					catch (Throwable t) {
						throw new IllegalArgumentException("error when accessing property [" + matchingProperties[k]
								+ "] of class [" + replicatedObject.getClass() + "]; nested exception is " + t);
					}
				}

				if (propertiesValueMatch) {
					foundMatchedReplicatedObject = true;
				}
			}

			if (!foundMatchedReplicatedObject) {
				removedObjectsCollection.add(baseObject);
			}
		}

		return removedObjectsCollection;
	}

	/**
	 * <p>
	 * To check against the object whether having the properties. To be used
	 * before trying to copy value to another bean.
	 * 
	 * <p>
	 * This must be performed, before a collection of objects to be copied to a
	 * new collection. Or else, application might encounter unexpected error.
	 * 
	 * @param object object to be checked against
	 * @param properties properties that 'should' exist in object bean
	 *        properties
	 */
	protected static void preCheckBeanProperties(Object object, String[] properties) {
		if (ArrayUtils.isEmpty(properties)) {
			return;
		}

		for (int i = 0; i < properties.length; i++) {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(object.getClass(), properties[i]);
			if (pd == null) {
				throw new IllegalStateException("class [" + object.getClass() + "] doesn't have property of ["
						+ properties[i] + "], your app might not be working properly in this case.");
			}
		}
	}
}
