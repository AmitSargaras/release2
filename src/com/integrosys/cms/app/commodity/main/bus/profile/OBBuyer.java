/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/OBBuyer.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
 */

package com.integrosys.cms.app.commodity.main.bus.profile;

/**
 * @author dayanand $
 * @version  $
 * @since $Date: 2004/08/12 03:14:49 $
 * Tag: $Name:  $
 */

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBBuyer implements IBuyer, java.io.Serializable {

	private long buyerID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private long commonReferenceID = ICMSConstant.LONG_INVALID_VALUE;

	private String status;

	public OBBuyer() {
	}

	public OBBuyer(IBuyer iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public OBBuyer(long buyerID, String name) {
		this.buyerID = buyerID;
		this.name = name;
	}

	public long getBuyerID() {
		return buyerID;
	}

	public void setBuyerID(long buyerID) {
		this.buyerID = buyerID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCommonReferenceID() {
		return commonReferenceID;
	}

	public void setCommonReferenceID(long commonReferenceID) {
		this.commonReferenceID = commonReferenceID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	 * overriden method to compare two objects of type OBBuyer. This method
	 * typically checks for the object type and its toString() return value to
	 * evaluate the equality.
	 */
	/*
	 * public boolean equals(Object obj) { return ( obj instanceof OBBuyer &&
	 * obj.toString().equals( this.toString() ) ) ; }
	 */

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBBuyer)) {
			return false;
		}

		final OBBuyer obBuyer = (OBBuyer) o;

		if (buyerID != obBuyer.buyerID) {
			return false;
		}
		if (commonReferenceID != obBuyer.commonReferenceID) {
			return false;
		}
		if (name != null ? !name.equals(obBuyer.name) : obBuyer.name != null) {
			return false;
		}
		if (status != null ? !status.equals(obBuyer.status) : obBuyer.status != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (int) (buyerID ^ (buyerID >>> 32));
		result = 29 * result + (name != null ? name.hashCode() : 0);
		result = 29 * result + (int) (commonReferenceID ^ (commonReferenceID >>> 32));
		result = 29 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

}
