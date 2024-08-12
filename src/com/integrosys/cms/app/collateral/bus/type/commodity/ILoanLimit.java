/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/ILoanLimit.java,v 1.4 2006/08/14 04:12:20 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * This interface represents loan agency limit.
 * 
 * @author $Author: nkumar $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/08/14 04:12:20 $ Tag: $Name: $
 */
public interface ILoanLimit extends java.io.Serializable {

	/**
	 * Gets the loan limit id.
	 * 
	 * @return long
	 */
	public long getLoanLimitID();

	/**
	 * Sets the loan limit id.
	 * 
	 * @param loanLimitID
	 */
	public void setLoanLimitID(long loanLimitID);

	/**
	 * Gets the limit id associated with this loan limit.
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Sets the limit id associated with this loan limit.
	 * 
	 * @param limitID
	 */
	public void setLimitID(long limitID);

	/**
	 * Get common reference for actual and staging limit.
	 * 
	 * @return long
	 */
	public long getCommonReferenceID();

	/**
	 * Set common reference for actual and staging limit.
	 * 
	 * @param commonReferenceID of type long
	 */
	public void setCommonReferenceID(long commonReferenceID);

	/**
	 * Get limit status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set limit status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Gets the coBorrowerLimitID.
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID();

	/**
	 * Sets the coBorrowerLimitID.
	 * @param coBorrowerLimitID
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID);

	/**
	 * Gets the customer category.
	 * 
	 * @return String
	 */
	public String getCustomerCategory();

	/**
	 * Sets the customer category
	 * @param customerCategory
	 */
	public void setCustomerCategory(String customerCategory);

}
