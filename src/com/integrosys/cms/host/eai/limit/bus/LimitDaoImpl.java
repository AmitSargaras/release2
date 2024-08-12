package com.integrosys.cms.host.eai.limit.bus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateCallback;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.support.PersistentDaoSupport;
import com.integrosys.cms.ui.limit.CategoryCodeConstant;

/**
 * ORM based DAO using hibernate for Limit Module
 * 
 * @author Chong Jun Yong
 * @since 18.08.2008
 */
public class LimitDaoImpl extends PersistentDaoSupport implements ILimitDao {

	public List retrieveAccountListByAccountIdAndAccountNo(List accountIdList, String accountNo, Class classRequired) {
		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(
				Restrictions.in("cmsId", accountIdList)).add(Restrictions.eq("externalSystemXReferenceId", accountNo));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List retrieveAccountPrimaryKeyListByCmsLimitIdAndStatus(long cmsLimitId, String status, Class classRequired) {
		StringBuffer hqlBuf = new StringBuffer();
		hqlBuf.append("select actMap.CMSXRefId from ");
		hqlBuf.append(classRequired.getName()).append(" actMap ");
		hqlBuf.append("where actMap.CMSLimitId = ? and actMap.CMSStatus = ? ");

		return getHibernateTemplate().find(hqlBuf.toString(), new Object[] { new Long(cmsLimitId), status });
	}

	public List retrieveLimitAccountMapByStatusAndLimitCmsIdList(String status, List limitCmsIdList, Class classRequired) {
		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(
				Restrictions.in("cmsLimitId", limitCmsIdList)).add(Restrictions.eq("status", status));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List retrieveNonDeletedObjectsListByLimitCmsIdList(List limitCmsIdList, String limitCmsIdFieldName,
			String statusIndicatorFieldName, Class classRequired) {
		DetachedCriteria criteria = DetachedCriteria.forClass(classRequired).add(
				Restrictions.in(limitCmsIdFieldName, limitCmsIdList)).add(
				Restrictions.ne(statusIndicatorFieldName, "D"));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List searchLimitProfileByHostApplicationNumberAndApplicationTypeAndApplicationLawType(String hostAANumber,
			String aaType, String aaLawType, List cmsSubProfileIdList) {
		Validate.notEmpty(cmsSubProfileIdList, "'cmsSubProfileIdList' must not be empty");

		DetachedCriteria criteria = DetachedCriteria.forClass(LimitProfile.class);

		Conjunction fullCriterion = Restrictions.conjunction();
		fullCriterion.add(Restrictions.in("cmsSubProfileId", cmsSubProfileIdList));

		if (StringUtils.isNotBlank(hostAANumber)) {
			fullCriterion.add(Restrictions.eq("hostAANumber", hostAANumber));
		}

		if (StringUtils.isNotBlank(aaType)) {
			fullCriterion.add(Restrictions.eq("AAType", aaType));
		}

		if (StringUtils.isNotBlank(aaLawType)) {
			fullCriterion.add(Restrictions.eq("AALawType", aaLawType));
		}

		criteria.add(fullCriterion);

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public LimitProfile searchLimitProfileByLosAANumberAndSource(String losAANumber, String applicationSource) {
		Validate.notEmpty(losAANumber, "losAANumber must not be empty");
		Validate.notEmpty(applicationSource, "losAANumber must not be empty");

		Map parameters = new HashMap();
		parameters.put("LOSAANumber", losAANumber);
		parameters.put("AASource", applicationSource);

		return (LimitProfile) this.retrieveObjectByParameters(parameters, LimitProfile.class);
	}

	public List retrieveLimitsOnlyByCmsLimitProfileId(Long cmsLimitProfileId) {
		Validate.notNull(cmsLimitProfileId, "cmsLimitProfileId must not be null");

		DetachedCriteria criteria = DetachedCriteria.forEntityName(ACTUAL_LIMIT_ENTITTY_NAME);
		criteria.add(Restrictions.eq("cmsLimitProfileId", cmsLimitProfileId));
		criteria.add(Restrictions.ne("status", ICMSConstant.STATE_DELETED));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public List retrieveStageChargeDetailIdListByActualChargeDetailIdList(Collection actualChargeDetailIds) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ChargeDetail.class);
		criteria.add(Restrictions.in("chargeDetailId", actualChargeDetailIds));

		Collection chargeDetailRefIdList = new ArrayList();

		List actualChargeDetailList = getHibernateTemplate().findByCriteria(criteria);
		for (Iterator itr = actualChargeDetailList.iterator(); itr.hasNext();) {
			ChargeDetail actualChargeDetail = (ChargeDetail) itr.next();

			chargeDetailRefIdList.add(new Long(actualChargeDetail.getCmsRefId()));
		}

		criteria = DetachedCriteria.forClass(StagingChargeDetail.class);
		criteria.add(Restrictions.in("cmsRefId", chargeDetailRefIdList));

		List stageChargeDetailIdList = new ArrayList();

		List stageChargeDetailList = getHibernateTemplate().findByCriteria(criteria);
		for (Iterator itr = stageChargeDetailList.iterator(); itr.hasNext();) {
			StagingChargeDetail stageChargeDetail = (StagingChargeDetail) itr.next();
			stageChargeDetailIdList.add(new Long(stageChargeDetail.getChargeDetailId()));
		}

		return stageChargeDetailIdList;
	}

	public List retrieveLimitChargeMapByChargeDetailId(long cmsChargeDetailId, boolean isActual) {
		DetachedCriteria criteria = null;
		if (isActual) {
			criteria = DetachedCriteria.forClass(LimitChargeMap.class);
		}
		else {
			criteria = DetachedCriteria.forClass(StagingLimitChargeMap.class);
		}

		criteria.add(Restrictions.eq("chargeDetailID", new Long(cmsChargeDetailId)));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public LimitGeneral retrieveLimitByLOSLimitId(String LOSLimitId) {
		HashMap parameters = new HashMap();
		parameters.put("LOSLimitId", LOSLimitId);

		Object limit = retrieveObjectByParameters(parameters, LimitGeneral.class);
		return (LimitGeneral) limit;
	}

	public LimitsApprovedSecurityMap retrieveLimitApprovedSecurityMapByCmsLimitIdAndCmsCollateralId(long cmsLimitId,
			long cmsCollateralId, boolean isActual) {
		DetachedCriteria criteria = DetachedCriteria.forClass((isActual) ? LimitsApprovedSecurityMap.class
				: StagingLimitsApprovedSecurityMap.class);

		criteria.add(Restrictions.eq("cmsLimitId", new Long(cmsLimitId)));
		criteria.add(Restrictions.eq("cmsSecurityId", new Long(cmsCollateralId)));
		criteria.add(Restrictions.ne("updateStatusIndicator", String.valueOf(IEaiConstant.DELETEINDICATOR)));

		List oneLimitsApprovedSecurityMap = getHibernateTemplate().findByCriteria(criteria, 0, 1);
		if (oneLimitsApprovedSecurityMap.isEmpty()) {
			return null;
		}

		return (LimitsApprovedSecurityMap) oneLimitsApprovedSecurityMap.get(0);

	}

	public List retrieveListOfActualLimitsApprovedSecurityMapByCmsLimitIdAndSourceIds(long cmsLimitId,
			String[] sourceIds) {
		DetachedCriteria criteria = DetachedCriteria.forClass(LimitsApprovedSecurityMap.class);
		criteria.add(Restrictions.eq("cmsLimitId", new Long(cmsLimitId)));
		criteria.add(Restrictions.ne("updateStatusIndicator", String.valueOf(IEaiConstant.DELETEINDICATOR)));
		if (!ArrayUtils.isEmpty(sourceIds)) {
			criteria.add(Restrictions.in("source", sourceIds));
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public String getAccountTypeByLimitProductTypeAndFacilityType(final String productTypeCode,
			final String facilityTypeCode) {
		Object accountType = getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				StringBuffer buf = new StringBuffer("SELECT DISTINCT f.account_type ");
				buf.append("FROM host_facility_type f, host_product_type p, common_code_category_entry com ");
				buf.append("WHERE p.status = 'ACTIVE' ");
				buf.append("AND p.source IN ('LNPAR2', 'DDPAR2', 'TFPAR2') ");
				buf.append("AND p.loan_type = ? ");
				buf.append("AND f.facility_code = ? ");
				buf.append("AND com.category_code = ? ");
				buf.append("AND com.active_status = '1' ");
				buf.append("AND p.loan_type = com.entry_code ");
				buf.append("AND com.ref_entry_code = f.facility_code ");
				buf.append("AND p.status = f.status ");

				SQLQuery sql = session.createSQLQuery(buf.toString());
				sql.setString(0, productTypeCode);
				sql.setString(1, facilityTypeCode);
				sql.setString(2, CategoryCodeConstant.FACILITY_PRODUCT_MAP);

				return sql.uniqueResult();
			}
		});

		if (accountType != null) {
			return accountType.toString();
		}

		return null;
	}
}