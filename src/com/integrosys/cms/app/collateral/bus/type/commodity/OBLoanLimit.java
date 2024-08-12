/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBLoanLimit.java,v 1.6 2006/08/25 10:22:57 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents loan agency's loan limit.
 * 
 * @author $Author: nkumar $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/25 10:22:57 $ Tag: $Name: $
 */
public class OBLoanLimit implements ILoanLimit {

	private long loanLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private long commonReferenceID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long coBorrowerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory;

	/**
	 * Gets the loan limit id.
	 * 
	 * @return long
	 */
	public long getLoanLimitID() {
		return loanLimitID;
	}

	/**
	 * Sets the loan limit id.
	 * 
	 * @param loanLimitID
	 */
	public void setLoanLimitID(long loanLimitID) {
		this.loanLimitID = loanLimitID;
	}

	/**
	 * Gets the limit id associated with this loan limit.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Sets the limit id associated with this loan limit.
	 * 
	 * @param limitID
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	/**
	 * Gets the common reference id.
	 * 
	 * @return long
	 */
	public long getCommonReferenceID() {
		return commonReferenceID;
	}

	/**
	 * Sets the common reference id.
	 * 
	 * @param commonReferenceID
	 */
	public void setCommonReferenceID(long commonReferenceID) {
		this.commonReferenceID = commonReferenceID;
	}

	/**
	 * Gets the status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the coBorrowerLimitID.
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID() {
		return coBorrowerLimitID;
	}

	/**
	 * Sets the coBorrowerLimitID.
	 * @param coBorrowerLimitID
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		this.coBorrowerLimitID = coBorrowerLimitID;
	}

	public String getCustomerCategory() {
		return customerCategory;

	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

}
