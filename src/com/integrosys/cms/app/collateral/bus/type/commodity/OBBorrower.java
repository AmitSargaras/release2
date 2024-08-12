/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBBorrower.java,v 1.5 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * A class represents loan agency's borrower.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public class OBBorrower implements IBorrower {
	private long borrowerID = ICMSConstant.LONG_INVALID_VALUE;

	private String name;

	private String status;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default constructor.
	 */
	public OBBorrower() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IBorrower
	 */
	public OBBorrower(IBorrower obj) {
		this();
		if (obj != null) {
			AccessorUtil.copyValue(obj, this);
		}
	}

	/**
	 * Get borrower id.
	 * 
	 * @return long
	 */
	public long getBorrowerID() {
		return borrowerID;
	}

	/**
	 * Set borrower id.
	 * 
	 * @param borrowerID of type long
	 */
	public void setBorrowerID(long borrowerID) {
		this.borrowerID = borrowerID;
	}

	/**
	 * Get borrower name.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set borrower name.
	 * 
	 * @param name of type String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get borrower status, active or deleted.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set borrower status, active or deleted.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get common reference for actual and staging borrower.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference for actual and staging borrower.
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
		String hash = String.valueOf(borrowerID);
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
		else if (!(obj instanceof IBorrower)) {
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
