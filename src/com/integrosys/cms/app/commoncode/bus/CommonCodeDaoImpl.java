package com.integrosys.cms.app.commoncode.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.bus.OBCommonCodeEntry;

/**
 * Implementation of {@link ICommonCodeDao} using Spring Framework hibernate
 * routine
 * 
 * @author Cynthia Zhou
 * @author Chong Jun Yong
 * @author Andy Wong
 * @author Phoon Sai Heng
 * @since Sep 30, 2008
 */
public class CommonCodeDaoImpl extends HibernateDaoSupport implements ICommonCodeDao {

	public Collection update(final String entityName, final Collection entryList) {
		getHibernateTemplate().clear();
		return (Collection) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				List resultList = new ArrayList();
				checkWriteOperationAllowed(session);
				for (Iterator it = entryList.iterator(); it.hasNext();) {
					OBCommonCodeEntry ccEntry = (OBCommonCodeEntry) it.next();
					// trimEntryAtrributes (ccEntry);
					if (ccEntry.getEntryId() == ICMSConstant.LONG_INVALID_VALUE) {
						Object pk = session.save(entityName, ccEntry);
						ccEntry.setEntryId(((Long) pk).longValue());
					}
					else {
						session.update(entityName, ccEntry);
					}
					resultList.add(ccEntry);
				}
				return resultList;
			}
		});
	}

	public Collection updateStaging(final String entityName, final Collection entryList) {
		getHibernateTemplate().clear();
		return (Collection) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				List resultList = new ArrayList();
				checkWriteOperationAllowed(session);
				for (Iterator it = entryList.iterator(); it.hasNext();) {
					OBCommonCodeEntry ccEntry = (OBCommonCodeEntry) it.next();
					// trimEntryAtrributes (ccEntry);
					if (ccEntry.getStageId() == null) {
						Object pk = session.save(entityName, ccEntry);
						ccEntry.setStageId((Long) pk);
					}
					else {
						session.update(entityName, ccEntry);
					}
					resultList.add(ccEntry);
				}
				return resultList;
			}
		});
	}

	public List getEntityEntryByEntityName(String entityName) {

		Map parametersMap = new HashMap();

		DefaultLogger.debug(this, "entityName HERE ;;;;;;; " + entityName);

		if ("paramLocalHostLoanProductType".equals(entityName)
				|| "paramLocalHostCollateralProductType".equals(entityName)
				|| "paramLocalHostTradeFinanceProductType".equals(entityName)
                || "paramLocalExtOfProductType".equals(entityName)) {
			// default reference entry code for [Production Type] for record
			// searching, based on remoteEntityName

			String sourceCode = "";
			if ("paramLocalHostLoanProductType".equals(entityName)) {
				sourceCode = "LNPAR2";
			}
			else if ("paramLocalHostCollateralProductType".equals(entityName)) {
				sourceCode = "DDPAR2";
			}
			else if ("paramLocalHostTradeFinanceProductType".equals(entityName)) {
				sourceCode = "TFPAR2";
			}
            else if ("paramLocalExtOfProductType".equals(entityName)) {
                sourceCode = "LNPARL";
            }

			parametersMap.put("source", sourceCode);
		}

		// DetachedCriteria criteria =
		// DetachedCriteria.forEntityName(entityName);
		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		DefaultLogger.debug(this, "resultList.isEmpty() = " + (resultList.isEmpty()));
		DefaultLogger.debug(this, "resultList.size() ******** " + (resultList.size()));

		if (resultList.isEmpty()) {
			return null;
		}

		return resultList;
	}

	public List getCommonCodeEntryByCategorySource(String entityName, String categoryCode, String source,
			String remoteEntityName) {
		Map parametersMap = new HashMap();
		parametersMap.put("categoryCode", categoryCode);
		// parametersMap.put("ACTIVE_STATUS", new Character('1'));
		// //requirement: revive the deleted
		parametersMap.put("entrySource", source);

		DefaultLogger.debug(this, "remoteEntityName HERE ------- " + remoteEntityName);

		if ("paramLoanProductType".equals(remoteEntityName) || "paramCollateralProductType".equals(remoteEntityName)
				|| "paramTradeFinanceProductType".equals(remoteEntityName) || "paramExtOfProductType".equals(remoteEntityName)) {
			// default reference entry code for [Production Type] for record
			// searching, based on remoteEntityName

			String refEntryCode = "";
			if ("paramLoanProductType".equals(remoteEntityName)) {
				refEntryCode = "LNPAR2";
			}
			else if ("paramCollateralProductType".equals(remoteEntityName)) {
				refEntryCode = "DDPAR2";
			}
			else if ("paramTradeFinanceProductType".equals(remoteEntityName)) {
				refEntryCode = "TFPAR2";
			}
            else if ("paramProductType".equals(remoteEntityName)) {
                refEntryCode = "LNPARL";
            }

			parametersMap.put("refEntryCode", refEntryCode);
		}

		DefaultLogger.debug(this, "entityName = " + entityName);
		DefaultLogger.debug(this, "categoryCode = " + categoryCode);
		DefaultLogger.debug(this, "source = " + source);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		DefaultLogger.debug(this, "resultList.isEmpty() = " + (resultList.isEmpty()));
		DefaultLogger.debug(this, "resultList.size() HERE ====== " + resultList.size());

		return (resultList.isEmpty()) ? null : resultList;

	}

	public long getCategoryCodeId(String entityName, String categoryCode) {
		Map parametersMap = new HashMap();
		parametersMap.put("commonCategoryCode", categoryCode);
		parametersMap.put("activeStatus", "A");

		DefaultLogger.debug(this, " getCategoryCodeId - categoryCode = " + categoryCode);
		DefaultLogger.debug(this, " getCategoryCodeId - entityName = " + entityName);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		Iterator it = resultList.iterator();
		if (it.hasNext()) {
			OBCommonCodeType category = (OBCommonCodeType) it.next();
			return category.getCommonCategoryId();
		}

		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Port from HibernateTemplate
	 * <p/>
	 * <p/>
	 * Check whether write operations are allowed on the given Session.
	 * <p/>
	 * Default implementation throws an InvalidDataAccessApiUsageException in
	 * case of <code>FlushMode.NEVER/MANUAL</code>. Can be overridden in
	 * subclasses.
	 * 
	 * @param session current Hibernate Session
	 * @throws InvalidDataAccessApiUsageException if write operations are not
	 *         allowed
	 * @see #setCheckWriteOperations
	 * @see #getFlushMode()
	 * @see #FLUSH_EAGER
	 * @see org.hibernate.Session#getFlushMode()
	 * @see org.hibernate.FlushMode#NEVER
	 * @see org.hibernate.FlushMode#MANUAL
	 */
	protected void checkWriteOperationAllowed(Session session) throws InvalidDataAccessApiUsageException {
		if (getHibernateTemplate().isCheckWriteOperations()
				//&& getHibernateTemplate().getFlushMode() != HibernateTemplate.FLUSH_EAGER
				&& session.getFlushMode().lessThan(FlushMode.COMMIT)) {
			throw new InvalidDataAccessApiUsageException(
					"Write operations are not allowed in read-only mode (FlushMode.NEVER/MANUAL): "
							+ "Turn your Session into FlushMode.COMMIT/AUTO or remove 'readOnly' marker from transaction definition.");
		}
	}

	/**
	 * 
	 * @param entityName
	 * @param entryList
	 * @return
	 */
	public Collection updateTable(final String entityName, final Collection entryList) {
        getHibernateTemplate().clear();
		return (Collection) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				List resultList = new ArrayList();
				checkWriteOperationAllowed(session);
				for (Iterator it = entryList.iterator(); it.hasNext();) {
                    Object ob =  it.next();

                    session.saveOrUpdate(entityName, ob);
					resultList.add(ob);
				}
				return resultList;
			}
		});
	}
	
	public Boolean isDuplicateRecord(String entityName, String categoryCode,String cpsId) {
		
		Boolean isDuplicate = false;

		DefaultLogger.debug(this, " getCategoryCodeId - categoryCode = " + categoryCode);
		DefaultLogger.debug(this, " getCategoryCodeId - entityName = " + entityName);
		
		Criterion c1 = Restrictions.eq("cpsId", cpsId);
        Criterion c2 = Restrictions.eq("entryCode", cpsId);

		DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName)
					.add(Restrictions.eq("categoryCode", categoryCode))
					.add(Restrictions.or(c1, c2));
		List resultList = getHibernateTemplate().findByCriteria(criteria);

		if (resultList!=null && !resultList.isEmpty() && resultList.size()>0){
			isDuplicate = true;
		}
		
		return isDuplicate;

	}
	
	@SuppressWarnings("unchecked")
	public Boolean checkEntryCodeAvailable(String entityName, String categoryCode,String entryCode) {
		
		Boolean recordAvailable = false;
		try{
			Map parametersMap = new HashMap();
			parametersMap.put("categoryCode", categoryCode);
			parametersMap.put("activeStatusStr", "1");
			parametersMap.put("entryCode", entryCode);
	
			DefaultLogger.debug(this, " getCategoryCodeId - categoryCode = " + categoryCode);
			DefaultLogger.debug(this, " getCategoryCodeId - entityName = " + entityName);
	
			DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName).add(Restrictions.allEq(parametersMap));
			List<Object> resultList = (List<Object>)getHibernateTemplate().findByCriteria(criteria);
	
			if(resultList!=null && resultList.size()>0){
				recordAvailable = true;
			}
	
		}catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "checkEntryCodeAvailable() :::"+e.getMessage());
		}
		
		return recordAvailable;
	}
}
