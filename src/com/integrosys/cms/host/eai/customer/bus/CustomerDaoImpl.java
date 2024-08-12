package com.integrosys.cms.host.eai.customer.bus;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.integrosys.cms.host.eai.support.PersistentDaoSupport;

/**
 * ORM based DAO using hibernate for customer module
 * 
 * @author Chong Jun Yong
 * @since 18.08.2008
 * 
 */
public class CustomerDaoImpl extends PersistentDaoSupport implements ICustomerDao {

	public List searchCustomerByCustomerIdInfo(CustomerIdInfo idInfo) {
		Validate.notNull(idInfo, "idInfo criteria must not be null.");

		DetachedCriteria criteria = DetachedCriteria.forClass(MainProfile.class);

		criteria.add(prepareThreeIdInfosCriterion(idInfo));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	public MainProfile searchMainProfileByCIFAndCIFSource(String cifNO, String cifSource) {
		Validate.notNull(cifNO, "cifNO must not be null.");
		Validate.notNull(cifSource, "cifSource must not be null.");
		HashMap params = new HashMap();
		params.put("CIFId", cifNO);
		params.put("source", cifSource);
		MainProfile temp = (MainProfile) retrieveObjectByParameters(params, MainProfile.class);
		return temp;

	}

	public List searchCustomerByCustomerIdInfoAndShortNameAndCifSource(CustomerIdInfo idInfo, String shortCustomerName,
			String cifSource, String cifId) {
		Validate.notNull(idInfo, "idInfo criteria must not be null.");
		Validate.isTrue((shortCustomerName != null || cifSource != null || cifId != null),
				"either 'shortCustomerName' or 'cifSource' or 'cifId' must be provided.");

		DetachedCriteria criteria = DetachedCriteria.forClass(MainProfile.class);

		Conjunction fullCriterion = Restrictions.conjunction();

		fullCriterion.add(prepareThreeIdInfosCriterion(idInfo));

		if (StringUtils.isNotBlank(shortCustomerName)) {
			fullCriterion.add(Restrictions.eq("customerNameShort", shortCustomerName));
		}

		if (StringUtils.isNotBlank(cifSource)) {
			fullCriterion.add(Restrictions.eq("source", cifSource));
		}

		if (StringUtils.isNotBlank(cifId)) {
			fullCriterion.add(Restrictions.eq("CIFId", cifId));
		}

		criteria.add(fullCriterion);

		return getHibernateTemplate().findByCriteria(criteria);
	}

	protected Criterion prepareThreeIdInfosCriterion(CustomerIdInfo idInfo) {
		Criterion idInfo1Criterion = prepareIdInfoCriteria(idInfo, "idInfo1");
		Criterion idInfo2Criterion = prepareIdInfoCriteria(idInfo, "idInfo2");
		Criterion idInfo3Criterion = prepareIdInfoCriteria(idInfo, "idInfo3");

		Disjunction threeIdInfosCriterion = Restrictions.disjunction();
		threeIdInfosCriterion.add(idInfo1Criterion);
		threeIdInfosCriterion.add(idInfo2Criterion);
		threeIdInfosCriterion.add(idInfo3Criterion);

		return threeIdInfosCriterion;
	}

	/**
	 * <p>
	 * To prepare the criteria from the customer ID info, basically because not
	 * all fields in the object are provided from the search. So need to filter
	 * those null value.
	 * <p>
	 * If one of the field in the idInfo provided is null, it will not be used
	 * as part of the criteria. And all fields match the criteria will be used
	 * as conjunction criteria, ie <code>and</code>
	 * 
	 * @param idInfo the customer ID info
	 * @param idInfoPropertyName ID info field name in the MainProfile object,
	 *        there maybe multiple field in a object.
	 * @return the criteria to be used to search customer
	 * @see org.hibernate.criterion.Conjunction
	 */
	private Criterion prepareIdInfoCriteria(CustomerIdInfo idInfo, String idInfoPropertyName) {
		Conjunction idInfoCriterion = Restrictions.conjunction();

		if (idInfo.getIdNumber() != null) {
			idInfoCriterion.add(Restrictions.eq(idInfoPropertyName + ".idNumber", idInfo.getIdNumber()));
		}

		if (idInfo.getCountryIssued() != null) {
			idInfoCriterion.add(Restrictions.eq(idInfoPropertyName + ".countryIssued", idInfo.getCountryIssued()));
		}

		if (idInfo.getJDOIdStartDate() != null) {
			idInfoCriterion.add(Restrictions.eq(idInfoPropertyName + ".JDOIdStartDate", idInfo.getJDOIdStartDate()));
		}

		if (idInfo.getIdType() != null) {
			idInfoCriterion.add(Restrictions.eq(idInfoPropertyName + ".idType", idInfo.getIdType()));
		}

		return idInfoCriterion;
	}
}
