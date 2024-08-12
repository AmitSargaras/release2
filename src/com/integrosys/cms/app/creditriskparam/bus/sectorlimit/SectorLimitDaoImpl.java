package com.integrosys.cms.app.creditriskparam.bus.sectorlimit;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import org.hibernate.criterion.*;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.*;

public class SectorLimitDaoImpl extends HibernateDaoSupport implements ISectorLimitDao {

    public List findAll(String entityName)throws SectorLimitException{
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                .add(Restrictions.ne("status", ICMSConstant.STATE_DELETED))
                .addOrder(Order.asc("sectorCode"));

	    List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
    }

    public List findBySectorCode(String entityName, String sectorCode)throws SectorLimitException{
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                .add(Restrictions.ne("status", ICMSConstant.STATE_DELETED))
                .add(Restrictions.eq("sectorCode", sectorCode))
                .addOrder(Order.asc("sectorCode"));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
    }

    public List getAllSectorLimit(String entityName) throws SectorLimitException {
        return SectorLimitUtils.convertSetToList(findAll(entityName), true);
    }

    public List getAllSubSectorLimit(String entityName) throws SectorLimitException {
        return SectorLimitUtils.convertSetToList(findAll(entityName), true);
    }

    public List getAllEcoSectorLimit(String entityName) throws SectorLimitException {
        return SectorLimitUtils.convertSetToList(findAll(entityName), true);
    }

    public Object findByPrimaryKey(String entityName, Long key) throws SectorLimitException{
        return getHibernateTemplate().get(entityName, key);
    }

    public IMainSectorLimitParameter getLimitById(String entityName,long id) throws SectorLimitException{
        return (IMainSectorLimitParameter) SectorLimitUtils.convertSetToList(findByPrimaryKey(entityName, new Long(id)), true);
    }

    public IMainSectorLimitParameter getMainSectorLimitBySectorCode(String entityName,String code) throws SectorLimitException{
        return (IMainSectorLimitParameter) SectorLimitUtils.convertSetToList(findBySectorCode(entityName, code), true);
    }

    public ISubSectorLimitParameter getSubSectorLimitBySectorCode(String entityName,String code) throws SectorLimitException {
         return (ISubSectorLimitParameter) SectorLimitUtils.convertSetToList(findBySectorCode(entityName, code), true);
    }

    public IEcoSectorLimitParameter getEcoSectorLimitBySectorCode(String entityName,String code)throws SectorLimitException  {
        return (IEcoSectorLimitParameter) findBySectorCode(entityName, code); 
    }

    public IMainSectorLimitParameter createLimit(String entityName,IMainSectorLimitParameter sectorLimit) throws SectorLimitException {
        sectorLimit.setId(null);
        if (sectorLimit.getStatus() == null) {
            sectorLimit.setStatus(ICMSConstant.STATE_ACTIVE);
        }

        sectorLimit = preMainSectorLimitProcess(sectorLimit, true);

        Long key = (Long) getHibernateTemplate().save(entityName, sectorLimit);

        DefaultLogger.debug(this, "Creating main sector limit with ID: " + key);

        if (sectorLimit.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE) {
            sectorLimit.setCmsRefId(key.longValue());
        }
        postMainSectorLimitProcess(sectorLimit);

        getHibernateTemplate().update(entityName, sectorLimit);

        return sectorLimit;
    }

    public IMainSectorLimitParameter updateLimit(String entityName,IMainSectorLimitParameter sectorLimit) throws SectorLimitException {
        IMainSectorLimitParameter existingLimit = (IMainSectorLimitParameter) findByPrimaryKey(entityName, sectorLimit.getId());
        try {
            checkVersionMismatch(entityName, sectorLimit, new Long(existingLimit.getVersionTime()));
        } catch (VersionMismatchException e) {
            throw new SectorLimitException("VersionMismatchException found : " + e);
        }
        preMainSectorLimitProcess(sectorLimit, true);

        existingLimit = SectorLimitUtils.replicateMainSectorLimitParameterForUpdate(sectorLimit, existingLimit);

        getHibernateTemplate().update(entityName, existingLimit);

        postMainSectorLimitProcess(existingLimit);

        getHibernateTemplate().update(entityName, existingLimit);

        return existingLimit;
    }
    /*
        use to check version time, to ensure the object query by hibernate on few minute ago is still the same version since no read lock had been issue
     */
    private void checkVersionMismatch(String entityName, IMainSectorLimitParameter iMainSectorLimitParameter, Long verstionTime) throws VersionMismatchException {
        long currentVersionTime = 0;
        if(verstionTime == null){
            currentVersionTime = getVersionTime(entityName, iMainSectorLimitParameter.getId());
        }else{
            currentVersionTime = verstionTime.longValue();
        }
        if (currentVersionTime != iMainSectorLimitParameter.getVersionTime()) {
            VersionMismatchException e = new VersionMismatchException("Mismatch timestamp");
            throw e;
        }
    }

    private long getVersionTime(String entityName, Long id){

        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                .setProjection(Projections.property("versionTime"))
                .add(Restrictions.eq("id", id));

		Long resultList = (Long) getHibernateTemplate().findByCriteria(criteria).get(0);

		return resultList.longValue();
    }

    /*
        Set all status into active iff null
        Set id to null if is new instance so hibernate will insert as a new object instead treat it as update
     */
    private IMainSectorLimitParameter preMainSectorLimitProcess(IMainSectorLimitParameter iMainSectorLimitParameter, boolean newInstance) {
        Set subSectorSet = iMainSectorLimitParameter.getSubSectorSet();

        if (subSectorSet != null && subSectorSet.size() > 0) {
            Iterator iterator = subSectorSet.iterator();
            while (iterator.hasNext()) {
                ISubSectorLimitParameter subSectorLimitParameter = (ISubSectorLimitParameter) iterator.next();
                if(newInstance){
                    subSectorLimitParameter.setId(null); //make sure it is null since it may create a record when close rejected sector limit   
                }
                if(subSectorLimitParameter.getStatus()==null)
                    subSectorLimitParameter.setStatus(ICMSConstant.STATE_ACTIVE);
                subSectorLimitParameter.setEcoSectorSet(preEcoSectorLimitProcess(subSectorLimitParameter.getEcoSectorSet(), newInstance));
            }
            iMainSectorLimitParameter.setSubSectorSet(subSectorSet);
        }else if (subSectorSet != null && subSectorSet.size() == 0) {
            iMainSectorLimitParameter.setSubSectorSet(null);
        }
        return iMainSectorLimitParameter;
    }
    /*
        basically use for after insert an new instance to update cmsRefId to id
     */
    private IMainSectorLimitParameter postMainSectorLimitProcess(IMainSectorLimitParameter iMainSectorLimitParameter) {
        Set subSectorSet = iMainSectorLimitParameter.getSubSectorSet();

        if (subSectorSet != null && subSectorSet.size() > 0) {
            Iterator iterator = subSectorSet.iterator();
            while (iterator.hasNext()) {
                ISubSectorLimitParameter subSectorLimitParameter = (ISubSectorLimitParameter) iterator.next();
                if (subSectorLimitParameter.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE){
                    subSectorLimitParameter.setCmsRefId(subSectorLimitParameter.getId().longValue());
                }
                subSectorLimitParameter.setEcoSectorSet(postEcoSectorLimitProcess(subSectorLimitParameter.getEcoSectorSet()));
            }
            iMainSectorLimitParameter.setSubSectorSet(subSectorSet);
        }else if (subSectorSet != null && subSectorSet.size() == 0) {
            iMainSectorLimitParameter.setSubSectorSet(null);
        }
        return iMainSectorLimitParameter;
    }
    /*
        Set all status into active iff null
        Set id to null if is new instance so hibernate will insert as a new object instead treat it as update
     */
    private Set preEcoSectorLimitProcess(Set ecoSectorSet, boolean newInstance) {

        if (ecoSectorSet != null && ecoSectorSet.size() > 0) {
            Iterator iterator = ecoSectorSet.iterator();
            while (iterator.hasNext()) {
                IEcoSectorLimitParameter ecoSector = (IEcoSectorLimitParameter) iterator.next();
                if(newInstance){
                    ecoSector.setId(null);   
                }
                if(ecoSector.getStatus()==null)
                    ecoSector.setStatus(ICMSConstant.STATE_ACTIVE);
            }
        }
        return ecoSectorSet;
    }
    /*
        basically use for after insert an new instance to update cmsRefId to id
     */
    private Set postEcoSectorLimitProcess(Set ecoSectorSet) {

        if (ecoSectorSet != null && ecoSectorSet.size() > 0) {
            Iterator iterator = ecoSectorSet.iterator();
            while (iterator.hasNext()) {
                IEcoSectorLimitParameter ecoSector = (IEcoSectorLimitParameter) iterator.next();
                if (ecoSector.getCmsRefId() == ICMSConstant.LONG_INVALID_VALUE){
                    ecoSector.setCmsRefId(ecoSector.getId().longValue());
                }
            }
        }
        return ecoSectorSet;
    }
}