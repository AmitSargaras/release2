/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/profile/OBSupplier.java,v 1.3 2004/08/12 03:14:49 wltan Exp $
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

public class OBSupplier implements ISupplier, java.io.Serializable {

	private long supplierID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private long commonReferenceID = ICMSConstant.LONG_INVALID_VALUE;

	private String status;

	public OBSupplier() {
	}

	public OBSupplier(ISupplier iValue) {
		if (iValue != null) {
			AccessorUtil.copyValue(iValue, this);
		}
	}

	public OBSupplier(long supplierID, String name) {
		this.supplierID = supplierID;
		this.name = name;
	}

	public long getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(long supplierID) {
		this.supplierID = supplierID;
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
	 * overriden method to compare two objects of type OBRoutingMapEntry. This
	 * method typically checks for the object type and its toString() return
	 * value to evaluate the equality.
	 */
	/*
	 * public boolean equals(Object obj) { return ( obj instanceof OBSupplier &&
	 * obj.toString().equals( this.toString() ) ) ; }
	 */

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof OBSupplier)) {
			return false;
		}

		final OBSupplier obSupplier = (OBSupplier) o;

		if (commonReferenceID != obSupplier.commonReferenceID) {
			return false;
		}
		if (supplierID != obSupplier.supplierID) {
			return false;
		}
		if (name != null ? !name.equals(obSupplier.name) : obSupplier.name != null) {
			return false;
		}
		if (status != null ? !status.equals(obSupplier.status) : obSupplier.status != null) {
			return false;
		}

		return true;
	}

	public int hashCode() {
		int result;
		result = (int) (supplierID ^ (supplierID >>> 32));
		result = 29 * result + (name != null ? name.hashCode() : 0);
		result = 29 * result + (int) (commonReferenceID ^ (commonReferenceID >>> 32));
		result = 29 * result + (status != null ? status.hashCode() : 0);
		return result;
	}

}
