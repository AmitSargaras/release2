/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBGuarantor.java,v 1.5 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * A class represents loan guarantor.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public class OBGuarantor implements IGuarantor {
	private long guarantorID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private String status;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default constructor.
	 */
	public OBGuarantor() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGuarantor
	 */
	public OBGuarantor(IGuarantor obj) {
		this();
		if (obj != null) {
			AccessorUtil.copyValue(obj, this);
		}
	}

	/**
	 * Get guarantor id.
	 * 
	 * @return long
	 */
	public long getGuarantorID() {
		return guarantorID;
	}

	/**
	 * Set guarantor id.
	 * 
	 * @param guarantorID of type long
	 */
	public void setGuarantorID(long guarantorID) {
		this.guarantorID = guarantorID;
	}

	/**
	 * Get guarantor name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set guarantor name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get guarantor status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set guarantor status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get common reference of actual and staging guarantor.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference of actual and staging guarantor.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
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
		String hash = String.valueOf(guarantorID);
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
		else if (!(obj instanceof IGuarantor)) {
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