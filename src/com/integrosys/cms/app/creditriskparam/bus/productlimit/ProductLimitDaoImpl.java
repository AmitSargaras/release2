package com.integrosys.cms.app.creditriskparam.bus.productlimit;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.EntityAssociationUtils;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductLimitDaoImpl extends HibernateDaoSupport implements IProductLimitDao {

    public List findAll(String entityName)throws ProductLimitException{
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                .add(Restrictions.ne("status", ICMSConstant.STATE_DELETED))
                .addOrder(Order.asc("referenceCode"));

	    List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
    }

    public Object findByRefCode(String entityName, String referenceCode)throws ProductLimitException{
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
                .add(Restrictions.ne("status", ICMSConstant.STATE_DELETED))
                .add(Restrictions.eq("referenceCode", referenceCode))
                .addOrder(Order.asc("referenceCode"));

		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList.isEmpty() || resultList.size() == 0) {
			return null;
		}else{
            return resultList.get(0);
        }
    }

    public Object findByPrimaryKey(String entityName, Long key) throws ProductLimitException{
        return getHibernateTemplate().get(entityName, key);
    }

    public IProductProgramLimitParameter createLimit(String entityName,IProductProgramLimitParameter productLimit) throws ProductLimitException {
        Long key = (Long) getHibernateTemplate().save(entityName, productLimit);

        return productLimit;
    }

    public Long updateLimit(String entityName,IProductProgramLimitParameter productLimit) throws ProductLimitException {

        getHibernateTemplate().update(entityName, productLimit);

        return productLimit.getId();
    }

       /* use to check version time, to ensure the object query by hibernate on few minute ago is still the same version since no read lock had been issue*/

    public void checkVersionMismatch(String entityName, IProductProgramLimitParameter productLimit, Long verstionTime) throws VersionMismatchException {
        long currentVersionTime = 0;
        if(verstionTime == null){
            currentVersionTime = getVersionTime(entityName, productLimit.getId());
        }else{
            currentVersionTime = verstionTime.longValue();
        }
        if (currentVersionTime != productLimit.getVersionTime()) {
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
}