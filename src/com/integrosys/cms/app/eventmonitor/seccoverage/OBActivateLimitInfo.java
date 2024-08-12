/**
 * This interface represents an Limit Profile.
 *
 * @author $Author: jzhan $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/04/28 11:12:52 $
 * Tag: $Name:  $
 */
package com.integrosys.cms.app.eventmonitor.seccoverage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class OBActivateLimitInfo implements Serializable {
	private String limitID;

	private String sciLmtID;

	private String approvedLimitAmount;

	private String activatedLimitAmount;

	private String operationalAmount;

	private String limitCurrency;

	private String limitType;

	private String outerLimitID;

	private String requiredCoverage;

	private boolean hasCommondity;

	private double activateLmtDerived;

	private Map secApportionment;

	private Map secSecurityCoverage;

	private List innerLimitList; // for outer limit, the list will contain all
									// inner limits tied to it

	public OBActivateLimitInfo() {
		innerLimitList = new ArrayList();
		secApportionment = new HashMap();
		secSecurityCoverage = new HashMap();
	}

	/**
	 * @return Returns the activatedLimitAmount.
	 */
	public String getActivatedLimitAmount() {
		return activatedLimitAmount;
	}

	/**
	 * @param activatedLimitAmount The activatedLimitAmount to set.
	 */
	public void setActivatedLimitAmount(String activatedLimitAmount) {
		this.activatedLimitAmount = activatedLimitAmount;
	}

	/**
	 * @return Returns the approvedLimitAmount.
	 */
	public String getApprovedLimitAmount() {
		return approvedLimitAmount;
	}

	/**
	 * @param approvedLimitAmount The approvedLimitAmount to set.
	 */
	public void setApprovedLimitAmount(String approvedLimitAmount) {
		this.approvedLimitAmount = approvedLimitAmount;
	}

	/**
	 * @return Returns the limitCurrency.
	 */
	public String getLimitCurrency() {
		return limitCurrency;
	}

	/**
	 * @param limitCurrency The limitCurrency to set.
	 */
	public void setLimitCurrency(String limitCurrency) {
		this.limitCurrency = limitCurrency;
	}

	/**
	 * @return Returns the limitId.
	 */
	public String getLimitID() {
		return limitID;
	}

	/**
	 * @param limitId The limitId to set.
	 */
	public void setLimitID(String limitID) {
		this.limitID = limitID;
	}

	/**
	 * @return Returns the limitType.
	 */
	public String getLimitType() {
		return limitType;
	}

	/**
	 * @param limitType The limitType to set.
	 */
	public void setLimitType(String limitType) {
		this.limitType = limitType;
	}

	/**
	 * @return Returns the outerLimitId.
	 */
	public String getOuterLimitID() {
		return outerLimitID;
	}

	/**
	 * @param outerLimitId The outerLimitId to set.
	 */
	public void setOuterLimitID(String outerLimitID) {
		this.outerLimitID = outerLimitID;
	}

	/**
	 * @return Returns the requiredCoverage.
	 */
	public String getRequiredCoverage() {
		return requiredCoverage;
	}

	/**
	 * @param requiredCoverage The requiredCoverage to set.
	 */
	public void setRequiredCoverage(String requiredCoverage) {
		this.requiredCoverage = requiredCoverage;
	}

	public void addInnerLimit(OBActivateLimitInfo innerLimit) {
		// we can only add inner limit for outer limit
		if (ICMSConstant.CCC_OUTER_LIMIT.equals(this.getLimitType())) {
			innerLimitList.add(innerLimit);
		}
	}

	/**
	 * @return Returns the innerLimitList.
	 */
	public List getInnerLimitList() {
		return innerLimitList;
	}

	/**
	 * @return Returns the sciLmtID.
	 */
	public String getSciLmtID() {
		return sciLmtID;
	}

	/**
	 * @param sciLmtID The sciLmtID to set.
	 */
	public void setSciLmtID(String sciLmtID) {
		this.sciLmtID = sciLmtID;
	}

	/**
	 * @return Returns the hasCommondity.
	 */
	public boolean getHasCommondity() {
		return hasCommondity;
	}

	/**
	 * @param hasCommondity The hasCommondity to set.
	 */
	public void setHasCommondity(boolean hasCommondity) {
		this.hasCommondity = hasCommondity;
	}

	/**
	 * @return Returns the operationalAmount.
	 */
	public String getOperationalAmount() {
		return operationalAmount;
	}

	/**
	 * @param operationalAmount The operationalAmount to set.
	 */
	public void setOperationalAmount(String operationalAmount) {
		this.operationalAmount = operationalAmount;
	}

	public void addApportionmentForSec(String securityId, double apportionment) {
		secApportionment.put(securityId, new Double(apportionment));
	}

	public void addSecurityCoverageForSec(String securityId, double coverage) {
		secSecurityCoverage.put(securityId, new Double(coverage));
	}

	/**
	 * @return Returns the secApportionment.
	 */
	public Map getSecApportionment() {
		return secApportionment;
	}

	/**
	 * @return Returns the secSecurityCoverage.
	 */
	public Map getSecSecurityCoverage() {
		return secSecurityCoverage;
	}

	/**
	 * @return Returns the activateLmtDerived.
	 */
	public double getActivateLmtDerived() {
		return activateLmtDerived;
	}

	/**
	 * @param activateLmtDerived The activateLmtDerived to set.
	 */
	public void setActivateLmtDerived(double activateLmtDerived) {
		this.activateLmtDerived = activateLmtDerived;
	}

	public double getActualSecCoverageForLimit() {
		double result = 0;
		Iterator iter = secSecurityCoverage.values().iterator();
		while (iter.hasNext()) {
			Double nextSecCoverageForLimit = (Double) (iter.next());
			result = result + nextSecCoverageForLimit.doubleValue();
		}
		return result * 100;
	}
}
