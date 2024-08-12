/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * LimitChargeMap
 *
 * Created on 5:39:54 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.host.eai.limit.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Apr 24, 2007 Time: 5:39:54 PM
 */
public class LimitChargeMap implements java.io.Serializable {

	private long chargeMapID; // internal key

	private long limitID; // internal key

	private Long coborrowerLimitID;

	private long chargeID; // internal key - ?

	private long chargeDetailID; // internal key

	private long collateralID; // internal key

	private String status = ICMSConstant.STATE_ACTIVE;

	private String customerCategory = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER;

	private long limitProfileId;

	/**
	 * Default Constructor.
	 */
	public LimitChargeMap() {
		super();
	}

	/**
	 * Get limit charge map's primary key.
	 * 
	 * @return long
	 */
	public long getChargeMapID() {
		return chargeMapID;
	}

	/**
	 * Set limit charge map's primary key.
	 * 
	 * @param chargeMapID of type long
	 */
	public void setChargeMapID(long chargeMapID) {
		this.chargeMapID = chargeMapID;
	}

	/**
	 * Get cms limit id.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Set cms limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public Long getCoborrowerLimitID() {
		return coborrowerLimitID;
	}

	public void setCoborrowerLimitID(Long coboLimitID) {
		this.coborrowerLimitID = coboLimitID;
	}

	/**
	 * Get limit security lingkage id.
	 * 
	 * @return long
	 */
	public long getChargeID() {
		return chargeID;
	}

	/**
	 * Set limit security linkage id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID) {
		this.chargeID = chargeID;
	}

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID() {
		return chargeDetailID;
	}

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		this.chargeDetailID = chargeDetailID;
	}

	/**
	 * Get collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Set collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public long getLimitProfileId() {
		return limitProfileId;
	}

	public void setLimitProfileId(long limitProfileId) {
		this.limitProfileId = limitProfileId;
	}

}
