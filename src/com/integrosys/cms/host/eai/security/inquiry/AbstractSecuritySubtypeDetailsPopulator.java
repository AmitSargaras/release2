package com.integrosys.cms.host.eai.security.inquiry;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.security.SecurityMessageBody;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.security.bus.ISecurityDao;
import com.integrosys.cms.host.eai.security.bus.SecurityInsurancePolicy;
import com.integrosys.cms.host.eai.security.bus.SecurityValuation;

/**
 * Abstract implementation of <code>SecuritySubtypeDetailsPopulater</code> which
 * interface with persistent storage, and let sub class to do the casting and
 * return the class required to retrieve the sub type object.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractSecuritySubtypeDetailsPopulator implements SecuritySubtypeDetailsPopulator {

	private ISecurityDao securityDao;

	public final void setSecurityDao(ISecurityDao securityDao) {
		this.securityDao = securityDao;
	}

	public final ISecurityDao getSecurityDao() {
		return securityDao;
	}

	public final void prepareSecuritySubtypeDetails(ApprovedSecurity sec, SecurityMessageBody securityMsgBody) {
		long cmsSecurityId = sec.getCMSSecurityId();

		Object securitySubtypeObject = getSecurityDao().retrieve(new Long(cmsSecurityId), getSecuritySubtypeClass());
		doPopulateMessageBody(securityMsgBody, securitySubtypeObject);
	}

	/**
	 * To populate valuation details into message body, not applicable for clean
	 * collateral only
	 * 
	 * @param securityMsgBody the message body to be populated the valuation
	 *        details, security details must exists for the cms collateral id.
	 */
	protected void populateValuationDetails(SecurityMessageBody securityMsgBody) {
		ApprovedSecurity sec = securityMsgBody.getSecurityDetail();

		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("CMSSecurityId", new Long(sec.getCMSSecurityId()));
		parameters.put("sourceType", ICMSConstant.VALUATION_SOURCE_TYPE_S);

		List valuationList = getSecurityDao().retrieveObjectsListByParameters(parameters, SecurityValuation.class);
		if (valuationList != null && !valuationList.isEmpty()) {

			for (Iterator itr = valuationList.iterator(); itr.hasNext();) {
				SecurityValuation val = (SecurityValuation) itr.next();
				val.setLOSSecurityId(sec.getLOSSecurityId());
			}

			Vector valuationDetails = new Vector(valuationList);
			securityMsgBody.setValuationDetail(valuationDetails);
		}
	}

	/**
	 * To populate insurance policy details into message body, applicable for
	 * AB, PT, OT.
	 * 
	 * @param securityMsgBody the message body to be populated the valuation
	 *        details, security details must exists for the cms collateral id.
	 */
	protected void populateInsurancePolicyDetails(SecurityMessageBody securityMsgBody) {
		ApprovedSecurity sec = securityMsgBody.getSecurityDetail();

		Map parameters = new HashMap();
		parameters.clear();
		parameters.put("CMSSecurityId", new Long(sec.getCMSSecurityId()));

		List insurancePolicyList = getSecurityDao().retrieveObjectsListByParameters(parameters,
				SecurityInsurancePolicy.class);
		if (insurancePolicyList != null && !insurancePolicyList.isEmpty()) {

			for (Iterator itr = insurancePolicyList.iterator(); itr.hasNext();) {
				SecurityInsurancePolicy insr = (SecurityInsurancePolicy) itr.next();
				insr.setSecurityId(sec.getLOSSecurityId());
			}

			Vector insurancePolicyDetails = new Vector(insurancePolicyList);
			securityMsgBody.setInsurancePolicyDetail(insurancePolicyDetails);
		}
	}

	/**
	 * Populate security subtype object which retrieved in earlier process into
	 * message body supplied.
	 * 
	 * @param securityMsgBody message body to be populated
	 * @param securitySubtypeObject the security subtype object to be used to
	 *        populate into message body.
	 */
	protected abstract void doPopulateMessageBody(SecurityMessageBody securityMsgBody, Object securitySubtypeObject);

	/**
	 * return the class required to interface with the persistent framework
	 * 
	 * @return the class of the subtype object
	 */
	protected abstract Class getSecuritySubtypeClass();

}