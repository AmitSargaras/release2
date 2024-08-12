package com.integrosys.cms.host.eai.security.bus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.customer.bus.CustomerIdInfo;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.property.PropertySecurity;
import com.integrosys.cms.host.eai.support.PersistentDaoSupport;
import com.integrosys.component.user.app.bus.UserException;

/**
 * ORM Based DAO using hibernate for collateral module
 * 
 * @author Chong Jun Yong
 * @since 18.08.2008
 */
public class SecurityDaoImpl extends PersistentDaoSupport implements ISecurityDao {
	private DBUtil dbUtil;

	public List searchPropertyCollaterals(List cmsCollateralIdList, Map propertyParameters) {
		Validate.notEmpty(propertyParameters, "'propertyParameters' must not be empty.");

		String titleNumber = (String) propertyParameters.get("titleNumber");
		propertyParameters.remove("titleNumber");

		DetachedCriteria criteria = DetachedCriteria.forClass(PropertySecurity.class);

		if (cmsCollateralIdList != null && !cmsCollateralIdList.isEmpty()) {
			criteria.add(Restrictions.in("CMSSecurityId", cmsCollateralIdList));
		}

		criteria.add(Restrictions.allEq(propertyParameters));
		criteria.add(Restrictions.like("titleNumber", titleNumber, MatchMode.START));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List searchCollateralByCollateralAndPledgor(Map securityParameters, Map pledgorParameters) {
		Validate.notEmpty(securityParameters, "'securityParameters' must not be empty.");

		DetachedCriteria criteria = DetachedCriteria.forClass(ApprovedSecurity.class);
		criteria.add(Restrictions.allEq(securityParameters));

		List result = getHibernateTemplate().findByCriteria(criteria);

		List cmsCollateralIdList = new ArrayList();
		List approvedSecurityList = new ArrayList();
		for (Iterator itr = result.iterator(); itr.hasNext();) {
			ApprovedSecurity sec = (ApprovedSecurity) itr.next();

			approvedSecurityList.add(sec);
			cmsCollateralIdList.add(new Long(sec.getCMSSecurityId()));
		}

		if (approvedSecurityList.isEmpty()) {
			return Collections.EMPTY_LIST;
		}

		Map cmsCollateralIdPledgorMap = new HashMap();
		if (pledgorParameters != null && !pledgorParameters.isEmpty()) {
			StringBuffer hqlBuf = new StringBuffer();
			hqlBuf.append("SELECT map.CMSSecurityId, plg.pledgorLegalName, plg.idInfo.idNumber FROM ");
			hqlBuf.append(SecurityPledgorMap.class.getName()).append(" map, ");
			hqlBuf.append(Pledgor.class.getName()).append(" plg ");
			hqlBuf.append("WHERE map.CMSPledgorId = plg.cmsId AND map.CMSSecurityId IN (");
			hqlBuf.append(prepareInExpression(cmsCollateralIdList)).append(") ");

			if (StringUtils.isNotBlank((String) pledgorParameters.get("pledgorLegalName"))) {
				hqlBuf.append("AND upper(plg.pledgorLegalName) like '%");
				hqlBuf.append(((String) pledgorParameters.get("pledgorLegalName")).toUpperCase()).append("%' ");
			}

			if (StringUtils.isNotBlank((String) pledgorParameters.get("idInfo.idNumber"))) {
				hqlBuf.append("AND plg.idInfo.idNumber = '");
				hqlBuf.append((String) pledgorParameters.get("idInfo.idNumber") + "'");
			}

			List pledgorResult = getHibernateTemplate().findByNamedParam(hqlBuf.toString(), new String[] {},
					new Object[] {});

			for (Iterator itr = pledgorResult.iterator(); itr.hasNext();) {
				Object[] rowResult = (Object[]) itr.next();

				Long cmsCollateralId = (Long) rowResult[0];

				Set pledgorSet = (Set) cmsCollateralIdPledgorMap.get(cmsCollateralId);
				if (pledgorSet == null) {
					pledgorSet = new HashSet();
				}

				Pledgor pledgor = new Pledgor();
				pledgor.setPledgorLegalName(String.valueOf(rowResult[1]));

				CustomerIdInfo idInfo = new CustomerIdInfo();
				idInfo.setIdNumber(String.valueOf(rowResult[2]));

				pledgor.setIdInfo(idInfo);
				pledgorSet.add(pledgor);

				cmsCollateralIdPledgorMap.put(cmsCollateralId, pledgorSet);
			}
		}

		List securityMessageList = new ArrayList();
		for (Iterator itr = approvedSecurityList.iterator(); itr.hasNext();) {

			ApprovedSecurity security = (ApprovedSecurity) itr.next();
			Set pledgors = (Set) cmsCollateralIdPledgorMap.get(new Long(security.getCMSSecurityId()));

			SecurityMessageBody secMsgBody = new SecurityMessageBody();
			secMsgBody.setSecurityDetail(security);

			Vector pledgorVector = new Vector();
			if (pledgors != null && !pledgors.isEmpty()) {
				pledgorVector.addAll(pledgors);

			}
			secMsgBody.setPledgor(pledgorVector);

			securityMessageList.add(secMsgBody);
		}

		return securityMessageList;
	}

	public List retrievePledgorsByCmsPledgorIds(Collection cmsPledgorIds) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Pledgor.class);
		criteria.add(Restrictions.in("cmsId", cmsPledgorIds));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List retrieveSharedSecurityListBySourceSecurityId(String sourceSecurityId, String sourceId) {
		Map parameters = new HashMap();
		parameters.put("sourceSecurityId", sourceSecurityId);
		parameters.put("sourceId", sourceId);

		DetachedCriteria criteria = DetachedCriteria.forClass(SecuritySource.class);
		criteria.add(Restrictions.allEq(parameters));
		criteria.add(Restrictions.ne("status", ICMSConstant.STATE_DELETED));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public CollateralParameter findCollateralParameterBySubTypeAndCountryCode(String subType, String countryCode) {
		Map parameters = new HashMap();
		parameters.put("securitySubTypeId", subType);
		parameters.put("countryIsoCode", countryCode);

		DetachedCriteria criteria = DetachedCriteria.forClass(CollateralParameter.class);
		criteria.add(Restrictions.allEq(parameters));

		List resultList = getHibernateTemplate().findByCriteria(criteria);
		if (resultList.isEmpty()) {
			return null;
		}
		return (CollateralParameter) resultList.get(0);
	}

	public String findSecuritySourceIdByCmsSecurityId(final long cmsSecurityId) {
		final String sql = "SELECT source_id FROM cms_security WHERE cms_collateral_id = :cmsSecurityId";

		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.setLong("cmsSecurityId", cmsSecurityId).uniqueResult();
			}

		});
	}

	/**
	 * Based on the list of cms collateral id, construct a comma separated
	 * values.
	 * 
	 * @param cmsCollateralIdList list of cms collateral id
	 * @return string value consist of cms collateral ids separated by the comma
	 */
	protected String prepareInExpression(List cmsCollateralIdList) {
		StringBuffer buf = new StringBuffer();

		for (Iterator itr = cmsCollateralIdList.iterator(); itr.hasNext();) {
			Long id = (Long) itr.next();
			buf.append(id);

			if (itr.hasNext()) {
				buf.append(", ");
			}
		}

		return buf.toString();
	}

	public List retrieveTradeInInformationByCmsSecurityId(long cmsSecurityId, boolean isActual) {
		DetachedCriteria criteria = DetachedCriteria.forEntityName((isActual) ? ENTITY_NAME_ACTUAL_TRADE_IN
				: ENTITY_NAME_STAGE_TRADE_IN);
		criteria.add(Restrictions.eq("cmsCollateralId", new Long(cmsSecurityId)));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	public boolean getSecurityMigreted (String tableName , long key) throws Exception
	 {
	    // tableName = "CMS_SECURITY";
	     String sql = "SELECT count(*) as count  "+
	                  "  FROM  "+tableName+"" +
	                //  " WHERE CMS_COLLATERAL_ID = "+key  ;
	     " WHERE CMS_COLLATERAL_ID = "+key+" and ISMIGRATED = 'Y'"  ;
	     
	     String value = null;
	     StringBuffer strBuffer = new StringBuffer(sql.trim());
	     try
	     {
	    	 dbUtil = new DBUtil();
				//println(strBuffer.toString());
				try {
					dbUtil.setSQL(strBuffer.toString());
					
				}
				catch (SQLException e) {
					throw new SearchDAOException("Could not set SQL query statement", e);
				}
				ResultSet rs = dbUtil.executeQuery();
				long  count = 0;
				//return count;
				while (rs.next()) {					
					count = Long.parseLong(rs.getString("count"));						
				}
				
	         if(count>0)
	         {
	         return true;
	         }
	         else
	         {
	        	 return false;
	         }
	     }
	     catch(Exception e)
	     {
	         throw new UserException("failed to retrieve MIGRATED SECURITY ", e);
	     }
	     finally{
	    	 dbUtil.close();
	     }
	 }
	
	public String findSecuritySubTypeIdByCmsSecurityId(final long cmsSecurityId) {
		final String sql = "SELECT SECURITY_SUB_TYPE_ID FROM cms_security WHERE cms_collateral_id = :cmsSecurityId";

		return (String) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.setLong("cmsSecurityId", cmsSecurityId).uniqueResult();
			}

		});
	}

	public Object findSecurityByMappingId(final long mappingId) {
		final String sql = "SELECT * FROM cms_security WHERE cms_collateral_id = :mappingId";
		
		return (Object) getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session) throws HibernateException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.setLong("mappingId", mappingId).uniqueResult();
			}
			
		});
	}
}
