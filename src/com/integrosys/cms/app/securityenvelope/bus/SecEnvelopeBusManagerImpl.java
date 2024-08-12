package com.integrosys.cms.app.securityenvelope.bus;

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
 * Created by IntelliJ IDEA. User: Erene Wong Date: Jan 25, 2010 Time: 5:19:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class SecEnvelopeBusManagerImpl extends AbstractSecEnvelopeBusManager {
    protected static final Logger logger = LoggerFactory.getLogger(SecEnvelopeBusManagerImpl.class);

	public String getSecEnvelopeEntityName() {
		return ISecEnvelopeDao.ACTUAL_SECURITY_ENVELOPE_ENTITY_NAME;
	}

	public ISecEnvelope updateToWorkingCopy(ISecEnvelope workingCopy, ISecEnvelope imageCopy)
			throws SecEnvelopeException {
		Set tempItemSet = new HashSet();

		for (Iterator iterator = imageCopy.getSecEnvelopeItemList().iterator(); iterator.hasNext();) {
			OBSecEnvelopeItem secEnvelopeItem = (OBSecEnvelopeItem) iterator.next();

			secEnvelopeItem.setSecEnvelopeItemId(0);
			tempItemSet.add(secEnvelopeItem);
		}

		Set mergedItemsSet = (Set) synchronizeCollectionsByProperties(workingCopy
				.getSecEnvelopeItemList(), tempItemSet, new String[] { "secEnvelopeRefId", "secEnvelopeItemAddr", "secEnvelopeItemCab", "secEnvelopeItemDrw" },
				new String[] { "secEnvelopeItemId" }, true);

		workingCopy.getSecEnvelopeItemList().clear();           
		if (mergedItemsSet != null) {
			workingCopy.getSecEnvelopeItemList().addAll(mergedItemsSet);
		}

		ISecEnvelope updated = updateSecEnvelope(workingCopy);
		Set updateSecEnvelopeEntryRefSet = (Set) EntityAssociationUtils.copyReferenceIdUsingPrimaryKey(updated
				.getSecEnvelopeItemList(), "secEnvelopeRefId", Long.class, "secEnvelopeItemId");
		workingCopy.getSecEnvelopeItemList().clear();
		if (updateSecEnvelopeEntryRefSet != null) {
			updated.getSecEnvelopeItemList().addAll(updateSecEnvelopeEntryRefSet);
		}

		return updateSecEnvelope(updated);
	}

    public static Collection synchronizeCollectionsByProperties(Collection baseCollection,
			Collection replicatedCollection, String[] matchingProperties, String[] ignoredProperties,
            boolean isCreateCopyRequired) {


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

                        if (!replicatedObjectValue.toString().equals(baseObjectValue.toString())) {
                            propertiesValueMatch = false;
						}
					}
					catch (Throwable t) {
						logger.error("error when accessing property [" + matchingProperties[k] + "]", t);
						throw new IllegalArgumentException("error when accessing property [" + matchingProperties[k]
								+ "], " + t.getMessage());
					}
				}
        if (propertiesValueMatch) {
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