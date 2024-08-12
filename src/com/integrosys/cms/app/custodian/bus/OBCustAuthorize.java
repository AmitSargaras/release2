/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Implementation class for the ICustAutorize interface
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/03 03:49:26 $ Tag: $Name: $
 */
public class OBCustAuthorize implements ICustAuthorize {
	private long custAuthorizeId;

	private long custodianId;

	private String authorizeRole;

	private String authorizeName;

	private String signNum;

	private Date authzDate;

	private String operation;

	private long checkListItemRef;

	public long getCustAuthorizeId() {
		return custAuthorizeId;
	}

	public void setCustAuthorizeId(long custAuthorizeId) {
		this.custAuthorizeId = custAuthorizeId;
	}

	public long getCustodianId() {
		return custodianId;
	}

	public void setCustodianId(long custodianId) {
		this.custodianId = custodianId;
	}

	public String getAuthorizeRole() {
		return authorizeRole;
	}

	public void setAuthorizeRole(String authorizeRole) {
		this.authorizeRole = authorizeRole;
	}

	public String getAuthorizeName() {
		return authorizeName;
	}

	public void setAuthorizeName(String authorizeName) {
		this.authorizeName = authorizeName;
	}

	public String getSignNum() {
		return signNum;
	}

	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}

	public Date getAuthzDate() {
		return authzDate;
	}

	public void setAuthzDate(Date authzDate) {
		this.authzDate = authzDate;
	}

	public String getOperation() {
		return operation;
	}

	public long getCheckListItemRef() {
		return checkListItemRef;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setCheckListItemRef(long aCheckListItemRef) {
		this.checkListItemRef = aCheckListItemRef;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
