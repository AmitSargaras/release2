package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author KC Chin
 * @since Aug 10 2010
 */
public abstract class SectorLimitUtils {
    protected static final Logger logger = LoggerFactory.getLogger(SectorLimitUtils.class);

    public static IMainSectorLimitParameter convertSetToList(IMainSectorLimitParameter main, boolean filterDelete) {
        List subList;
        subList = new ArrayList();
        if(main.getSubSectorSet() != null){
            for(Iterator s = main.getSubSectorSet().iterator(); s.hasNext();){
                ISubSectorLimitParameter sub = (ISubSectorLimitParameter) s.next();
                if(filterDelete && sub.getStatus() != null && sub.getStatus().equals(ICMSConstant.STATE_DELETED)){
                    continue;
                }
                sub = convertSetToList(sub, filterDelete);
                subList.add(sub);
            }
        }
        Collections.sort(subList, new SectorLimitComparator());
        main.setSubSectorList(subList);
        return main;
    }

    public static ISubSectorLimitParameter convertSetToList(ISubSectorLimitParameter sub, boolean filterDelete) {
        List ecoList;
        ecoList = new ArrayList();
        if(sub.getEcoSectorSet() != null){
            for(Iterator e = sub.getEcoSectorSet().iterator(); e.hasNext();){
                IEcoSectorLimitParameter eco = (IEcoSectorLimitParameter) e.next();
                if(filterDelete && eco.getStatus() != null && eco.getStatus().equals(ICMSConstant.STATE_DELETED)){
                    continue;
                }
                ecoList.add(eco);
            }
        }
        Collections.sort(ecoList, new SectorLimitComparator());
        sub.setEcoSectorList(ecoList);
        return sub;
    }

    public static Object convertSetToList(Object obj, boolean filterDelete) {
        if(obj instanceof IMainSectorLimitParameter)
            return  convertSetToList((IMainSectorLimitParameter) obj, filterDelete);
        else  if(obj instanceof ISubSectorLimitParameter)
             return  convertSetToList((ISubSectorLimitParameter) obj, filterDelete);
        else
            return obj; //no convertion
    }

    public static List convertSetToList(List instance, boolean filterDelete) {
        List mainList = new ArrayList();
        if(instance != null && instance.size() > 0){
            for(Iterator m = instance.iterator(); m.hasNext();){
                Object obj = convertSetToList(m.next(), filterDelete);
                mainList.add(obj);
            }
        }
        return mainList;
    }
    /*
        duplicate a new sector limit object from existing object. since sector limit contain 3 level, it need to duplicate one level by one level
    */
    public static IMainSectorLimitParameter replicateMainSectorLimitParameterForCreate(IMainSectorLimitParameter sectorLimit) {
        Set tempItemSet = new HashSet();
        if(sectorLimit.getSubSectorSet() != null){
            for (Iterator iterator = sectorLimit.getSubSectorSet().iterator(); iterator.hasNext();) {
                ISubSectorLimitParameter subSectorLimit = (ISubSectorLimitParameter) iterator.next();

                Set replicatedEcoSet = (Set) ReplicateUtils.replicateCollectionObjectWithSpecifiedImplClass(
                    subSectorLimit.getEcoSectorSet(), new String[] { "id" });
                subSectorLimit.setId(null);
                subSectorLimit.setEcoSectorSet(replicatedEcoSet);

                tempItemSet.add(subSectorLimit);
            }
        }
        IMainSectorLimitParameter replicatedSectorLimit = (IMainSectorLimitParameter) ReplicateUtils.replicateObject(sectorLimit, new String[] { "id", "versionTime"});

        if(tempItemSet != null && tempItemSet.size() > 0)
		    replicatedSectorLimit.setSubSectorSet(tempItemSet);

        return replicatedSectorLimit;
	}

    /*
        compare old set and new set, any object not appear in new set should be mark as status delete in old set
     */
    private static Set setEcoSectorLimitParameterDeleteStatus(Set oldSet, Set newSet){
        boolean found;
        if(oldSet != null && oldSet.size() > 0){
            for(Iterator b = oldSet.iterator(); b.hasNext();){
                IEcoSectorLimitParameter oldEcoSet = (IEcoSectorLimitParameter) b.next();
                found = false;
                if(oldEcoSet != null && oldEcoSet.getStatus() != null && oldEcoSet.getStatus() != ICMSConstant.STATE_DELETED){
                    if(newSet != null){
                        for(Iterator r = newSet.iterator(); r.hasNext();){
                            IEcoSectorLimitParameter replicateEcoSector = (IEcoSectorLimitParameter) r.next();
                            if(replicateEcoSector != null && replicateEcoSector.getCmsRefId() == oldEcoSet.getCmsRefId()){
                                found = true;
                                break;
                            }
                        }
                    }
                    if(!found){
                        oldEcoSet.setStatus(ICMSConstant.STATE_DELETED);
                    }
                }
            }
        }
        return oldSet;
    }
    /*
        compare old set and new set, any object not appear in new set should be mark as status delete in old set
     */
    private static Set setSubSectorLimitParameterDeleteStatus(Set oldSet, Set newSet){
        boolean found;
        if(oldSet != null && oldSet.size() > 0){
            for(Iterator b = oldSet.iterator(); b.hasNext();){
                ISubSectorLimitParameter oldSubSector = (ISubSectorLimitParameter) b.next();
                found = false;
                if(oldSubSector != null && oldSubSector.getStatus() != null && oldSubSector.getStatus() != ICMSConstant.STATE_DELETED){
                    if(newSet != null){
                        for(Iterator r = newSet.iterator(); r.hasNext();){
                            ISubSectorLimitParameter replicateSubSector = (ISubSectorLimitParameter) r.next();
                            if(replicateSubSector != null && replicateSubSector.getCmsRefId() == oldSubSector.getCmsRefId()){
                                Set ecoSet = oldSubSector.getEcoSectorSet();
                                if(ecoSet != null && ecoSet.size() > 0){
                                    ecoSet = setEcoSectorLimitParameterDeleteStatus(ecoSet, replicateSubSector.getEcoSectorSet());
                                    oldSubSector.setEcoSectorSet(ecoSet);
                                }
                                found = true;
                                break;
                            }
                        }
                    }
                    if(!found){
                        Set ecoSet = oldSubSector.getEcoSectorSet();
                        ecoSet = setEcoSectorLimitParameterDeleteStatus(ecoSet, null);
                        oldSubSector.setEcoSectorSet(ecoSet);
                        oldSubSector.setStatus(ICMSConstant.STATE_DELETED);
                    }
                }
            }
        }
        return oldSet;
    }
    /*
        replicate object for update by comparing new object to existing object.

        step 1, compare existing and new object subsector item and econ item, any object not appear in new set should be mark as status delete in existing set
        step 2, set existing attribute to new attribute
        step 3, loop and set existing sub sector item and eco item to new attribute. new object adding in sub sector will return by synchronizeCollectionsByProperties in newItemSet variable
        step 4, append the newItemSet to existing limit sub sector set, replication completed
     */
    public static IMainSectorLimitParameter replicateMainSectorLimitParameterForUpdate(IMainSectorLimitParameter sectorLimit, IMainSectorLimitParameter existingLimit) {

        existingLimit.setSubSectorSet(setSubSectorLimitParameterDeleteStatus(existingLimit.getSubSectorSet(), sectorLimit.getSubSectorSet()));
        
        existingLimit.setStatus(sectorLimit.getStatus());
        existingLimit.setSectorCode(sectorLimit.getSectorCode());
        existingLimit.setLoanPurposeCode(sectorLimit.getLoanPurposeCode());
        existingLimit.setConventionalBankPercentage(sectorLimit.getConventionalBankPercentage());
        existingLimit.setInvestmentBankPercentage(sectorLimit.getInvestmentBankPercentage());
        existingLimit.setLimitPercentage(sectorLimit.getLimitPercentage());
        existingLimit.setIslamicBankPercentage(sectorLimit.getIslamicBankPercentage());

        Set amendedSet =  sectorLimit.getSubSectorSet();
        if(amendedSet != null && amendedSet.size() > 0){
            Set newItemSet = (Set) synchronizeCollectionsByProperties(existingLimit.getSubSectorSet(),
                    amendedSet, new String[] { "cmsRefId" }, new String[] { "id", "mainSectorLimitId" }, true);

            Set existingSet = existingLimit.getSubSectorSet();

            if(newItemSet != null && newItemSet.size() > 0){
                if(existingLimit.getSubSectorSet() != null){
                    existingSet.addAll(newItemSet);
                    existingLimit.setSubSectorSet(existingSet);
                }else{
                    existingLimit.setSubSectorSet(newItemSet);
                }
            }
        }

        return existingLimit;
	}

    /*
        modify from EntityAssociationUtils.synchronizeCollectionsByProperties

        changes
        1, able to synchronize 2 level of collection
        2, any object not found in replicatedCollection by cross with baseCollection are will be return as seperate collection
     */
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
                            replicatedObjectValue = (Object) replicatedObjectValue.toString().trim();
                            baseObjectValue = (Object) baseObjectValue.toString().trim();
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
                    Set baseSet = null, workingSet = null;
                    if(baseObject instanceof ISubSectorLimitParameter){

                        baseSet = ((ISubSectorLimitParameter)baseObject).getEcoSectorSet();
                        workingSet = ((ISubSectorLimitParameter)replicatedObject).getEcoSectorSet();
                        Set additional = (Set) synchronizeCollectionsByProperties(baseSet, workingSet, new String[] { "cmsRefId" }, new String[] { "id", "subSectorLimitId" }, true);
                        if(additional != null && additional.size() > 0){
                            if(baseSet != null)
                                baseSet.addAll(additional);
                            else
                                baseSet = additional;
                        }
                        BeanUtils.copyProperties(replicatedObject, baseObject, ignoredProperties);
                        ((ISubSectorLimitParameter)baseObject).setEcoSectorSet(baseSet);
                    }else{
                        BeanUtils.copyProperties(replicatedObject, baseObject, ignoredProperties);
                    }
					foundMatchedBaseObject = true;
				}
			}

            if (!foundMatchedBaseObject && isCreateCopyRequired) {
                if(replicatedObject instanceof ISubSectorLimitParameter){
                    Set workingSet = null;

                    workingSet = ((ISubSectorLimitParameter) replicatedObject).getEcoSectorSet();
                    replicatedObject = ReplicateUtils.replicateObject(replicatedObject, ignoredProperties);
                    if(workingSet != null)
                        ((ISubSectorLimitParameter)replicatedObject).setEcoSectorSet(workingSet);
                    }
                else
                    replicatedObject = ReplicateUtils.replicateObject(replicatedObject, ignoredProperties);

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

    /*
        to set all sub sector status and econ status into defined status
     */
    public static IMainSectorLimitParameter setAllSubSectorStatus(IMainSectorLimitParameter iMainSectorLimitParameter, String status) {
        Set subSectorSet = iMainSectorLimitParameter.getSubSectorSet();

        if (subSectorSet != null && subSectorSet.size() > 0) {
            Iterator iterator = subSectorSet.iterator();
            while (iterator.hasNext()) {
                ISubSectorLimitParameter subSectorLimitParameter = (ISubSectorLimitParameter) iterator.next();
                subSectorLimitParameter.setStatus(status);
                subSectorLimitParameter.setEcoSectorSet(setAllEcoSectorStatus(subSectorLimitParameter.getEcoSectorSet(), status));
            }
            iMainSectorLimitParameter.setSubSectorSet(subSectorSet);
        }else if (subSectorSet != null && subSectorSet.size() == 0) {
            iMainSectorLimitParameter.setSubSectorSet(null);
        }
        return iMainSectorLimitParameter;
    }

    /*
        to set all sub sector status and econ status into defined status
     */
    public static Set setAllEcoSectorStatus(Set ecoSectorSet, String status) {
        if (ecoSectorSet != null && ecoSectorSet.size() > 0) {
            Iterator iterator = ecoSectorSet.iterator();
            while (iterator.hasNext()) {
                IEcoSectorLimitParameter ecoSector = (IEcoSectorLimitParameter) iterator.next();
                ecoSector.setStatus(status);
            }
        }
        return ecoSectorSet;
    }


}