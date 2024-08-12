/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBSubLimit.java,v 1.7 2004/08/19 03:09:22 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents loan agency sub-limit.
 * 
 * @author wltan $
 * @version $
 * @since $Date: 2004/08/19 03:09:22 $ Tag: $Name: $
 */
public class OBSubLimit implements ISubLimit {
	private long subLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private Amount amount;

	private String facilityType; // commoncode - list

	private String status = ICMSConstant.STATE_ACTIVE;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBSubLimit() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param iValue is of type ISubLimit
	 */
	public OBSubLimit(ISubLimit iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	/**
	 * Get sub-limit id.
	 * 
	 * @return long
	 */
	public long getSubLimitID() {
		return subLimitID;
	}

	/**
	 * Set sub-limit id.
	 * 
	 * @param subLimitID of type long
	 */
	public void setSubLimitID(long subLimitID) {
		this.subLimitID = subLimitID;
	}

	/**
	 * Get sub-limit amount.
	 * 
	 * @return Amount
	 */
	public Amount getAmount() {
		return amount;
	}

	/**
	 * Set sub-limit amount.
	 * 
	 * @param amount of type Amount
	 */
	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	/**
	 * Get sub-limit facility type.
	 * 
	 * @return String
	 */
	public String getFacilityType() {
		return facilityType;
	}

	/**
	 * Set sub-limit facility type.
	 * 
	 * @param type of type String
	 */
	public void setFacilityType(String type) {
		this.facilityType = type;
	}

	/**
	 * Get sub-limit status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set sub-limit status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get common reference for actual and staging sub-limit.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference for actual and staging sub-limit.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * returns the String representation of the object. It overrides the
	 * java.lang.Object class method to return values of all fields.
	 * @return String object representing values of all fields concatenated in a
	 *         pattern.
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * overriden method to compare two objects of type OBRoutingMapEntry. This
	 * method typically checks for the object type and its toString() return
	 * value to evaluate the equality.
	 */
	public boolean equals(Object obj) {
		return ((obj instanceof OBSubLimit) && obj.toString().equals(this.toString()));
	}

}
