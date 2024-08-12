/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBApprovedCommodityType.java,v 1.4 2004/08/18 02:35:05 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * A class represents approved commodity type.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/18 02:35:05 $ Tag: $Name: $
 */
public class OBApprovedCommodityType implements IApprovedCommodityType {
	private long approvedCommodityTypeID;

	private IProfile profile;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default constructor.
	 */
	public OBApprovedCommodityType() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IApprovedCommodityType
	 */
	public OBApprovedCommodityType(IApprovedCommodityType obj) {
		this();
		if (obj != null) {
			AccessorUtil.copyValue(obj, this);
		}
	}

	/**
	 * Get approved commodity type id.
	 * 
	 * @return long
	 */
	public long getApprovedCommodityTypeID() {
		return approvedCommodityTypeID;
	}

	/**
	 * Set approved commodity type id.
	 * 
	 * @param approvedCommodityTypeID of type long
	 */
	public void setApprovedCommodityTypeID(long approvedCommodityTypeID) {
		this.approvedCommodityTypeID = approvedCommodityTypeID;
	}

	/**
	 * Get approved commodity type profile.
	 * 
	 * @return IProfile
	 */
	public IProfile getProfile() {
		return profile;
	}

	/**
	 * Set approved commodity type profile.
	 * 
	 * @param profile of type IProfile
	 */
	public void setProfile(IProfile profile) {
		this.profile = profile;
	}

	/**
	 * Get common reference of actual and staging approved commodity type.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference of actual and staging approved commodity type.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * Get approved commodity type status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set approved commodity type status.
	 * 
	 * @param status of type Status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(approvedCommodityTypeID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof IApprovedCommodityType)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}