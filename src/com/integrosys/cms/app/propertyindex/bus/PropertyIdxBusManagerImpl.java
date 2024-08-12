package com.integrosys.cms.app.propertyindex.bus;

import java.util.*;
import java.beans.PropertyDescriptor;

import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.base.techinfra.util.AccessorUtil;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA. User: Andy Wong Date: Sep 11, 2008 Time: 3:11:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class PropertyIdxBusManagerImpl extends AbstractPropertyIdxBusManager {
    protected static final Logger logger = LoggerFactory.getLogger(PropertyIdxBusManagerImpl.class);

	public String getPropertyIdxEntityName() {
		return IPropertyIdxDao.ACTUAL_PROPERTY_IDX_ENTITY_NAME;
	}

	public IPropertyIdx updateToWorkingCopy(IPropertyIdx workingCopy, IPropertyIdx imageCopy)
			throws PropertyIdxException {
		Set tempItemSet = new HashSet();

		for (Iterator iterator = imageCopy.getPropertyIdxItemList().iterator(); iterator.hasNext();) {
			OBPropertyIdxItem propertyIdxItem = (OBPropertyIdxItem) iterator.next();

			Set replicatedDistrictSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
					propertyIdxItem.getDistrictList(), new String[] { "propertyIdxDistrictCodeId" });
			Set replicatedMukimSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
					propertyIdxItem.getMukimList(), new String[] { "propertyIdxMukimCodeId" });
			Set replicatedPropertyTypeSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
					propertyIdxItem.getPropertyTypeList(), new String[] { "propertyIdxPropertyTypeId" });

			propertyIdxItem.setPropertyIdxItemId(0);
			propertyIdxItem.setDistrictList(replicatedDistrictSet);
			propertyIdxItem.setMukimList(replicatedMukimSet);
			propertyIdxItem.setPropertyTypeList(replicatedPropertyTypeSet);
			tempItemSet.add(propertyIdxItem);
		}

		Set mergedItemsSet = (Set) synchronizeCollectionsByProperties(workingCopy
				.getPropertyIdxItemList(), tempItemSet, new String[] { "cmsRefId" },
				new String[] { "propertyIdxItemId" }, true);

		workingCopy.getPropertyIdxItemList().clear();
		if (mergedItemsSet != null) {
			workingCopy.getPropertyIdxItemList().addAll(mergedItemsSet);
		}

		Set replicatedStageSecSubTypesSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
				imageCopy.getPropertyIdxSecSubTypeList(), new String[] { "propertyIdxSecSubTypeId" });

		workingCopy.getPropertyIdxSecSubTypeList().clear();
		if (replicatedStageSecSubTypesSet != null) {
			workingCopy.getPropertyIdxSecSubTypeList().addAll(replicatedStageSecSubTypesSet);
		}

		AccessorUtil.copyValue(imageCopy, workingCopy, new String[] { "PropertyIdxId", "PropertyIdxSecSubTypeList",
                "PropertyIdxItemList", "PropertyIdxSecSubTypeCol" });
		IPropertyIdx updated = updatePropertyIdx(workingCopy);
		Set updatePropertyIdxEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(updated
				.getPropertyIdxItemList(), "cmsRefId", Long.class, "propertyIdxItemId");
		workingCopy.getPropertyIdxItemList().clear();
		if (updatePropertyIdxEntryRefSet != null) {
			updated.getPropertyIdxItemList().addAll(updatePropertyIdxEntryRefSet);
		}

		return updatePropertyIdx(updated);
	}

    public static Collection synchronizeCollectionsByProperties(Collection baseCollection,
			Collection replicatedCollection, String[] matchingProperties, String[] ignoredProperties,
            boolean isCreateCopyRequired) {

//        DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "matchingProperties is null ?" + (matchingProperties==null));

        if ((replicatedCollection == null) || replicatedCollection.isEmpty()) {
			logger.warn("'replicatedCollection' is null or empty, 'null' will be returned.");
			return null;
		}

        Validate.notEmpty(matchingProperties, "'properties' must not be null.");

        /*
		 * to check before object are being copy from replicated collection to a
		 * new collection.
		 */
		preCheckBeanProperties(replicatedCollection.toArray()[0], ignoredProperties);

		Class actualCreatedClass = Class.class;
		//Andy Wong: bean collection might be null, return replicated collection without sync

//        DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "baseCollection == null ?? " + (baseCollection == null));
        if(baseCollection == null)
            return replicatedCollection;
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
						Object replicatedObjectValue = PropertyUtils.getProperty(replicatedObject,
								matchingProperties[k]);
						Object baseObjectValue = PropertyUtils.getProperty(baseObject, matchingProperties[k]);

                        if ((replicatedObjectValue == null) || (baseObjectValue == null)) {
							logger.warn("value to be compared to do matching is 'null' replicated ["
									+ replicatedObjectValue + "] base [" + baseObjectValue + "]");
							propertiesValueMatch = false;
							continue;
						} else {
                            replicatedObjectValue = (Object)replicatedObjectValue.toString().trim();
                            baseObjectValue = (Object)baseObjectValue.toString().trim();
                        }

//                        DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "GOT match ??????? ******** '"
//                                + replicatedObjectValue.toString() + "' ~ '" + baseObjectValue.toString() + "'");
                        if (!replicatedObjectValue.toString().equals(baseObjectValue.toString())) {
//                            DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "SET propertiesValueMatch to false ******** ");
                            propertiesValueMatch = false;
						}
					}
					catch (Throwable t) {
						logger.error("error when accessing property [" + matchingProperties[k] + "]", t);
						throw new IllegalArgumentException("error when accessing property [" + matchingProperties[k]
								+ "], " + t.getMessage());
					}
				}

//                DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "K + propertiesValueMatch ******** " + propertiesValueMatch + "~" + j);
                if (propertiesValueMatch) {
					//BeanUtils.copyProperties(replicatedObject, baseObject, ignoredProperties);
					synchronizedCollection.add(baseObject);
					foundMatchedBaseObject = true;
				}
			}


//            DefaultLogger.debug("com.integrosys.base.techinfra.util.EntityAssociationUtils", "foundMatchedBaseObject : " + foundMatchedBaseObject + " ` " + i);

            if (!foundMatchedBaseObject && isCreateCopyRequired) {
				synchronizedCollection.add(replicatedObject);
			}
		}

		return synchronizedCollection;
	}

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