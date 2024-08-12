/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.custodian.bus;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the cust authorize attributes that are persisted in
 * cust authorize entity..
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/07/03 03:49:26 $ Tag: $Name: $
 */
public interface ICustAuthorize extends Serializable {

	public long getCustAuthorizeId();

	public long getCustodianId();

	public String getAuthorizeRole();

	public String getAuthorizeName();

	public String getSignNum();

	public Date getAuthzDate();

	public String getOperation();

	public long getCheckListItemRef();

	public void setCustAuthorizeId(long custAuthorizeId);

	public void setCustodianId(long custodianId);

	public void setAuthorizeRole(String authorizeRole);

	public void setAuthorizeName(String authorizeName);

	public void setSignNum(String signNum);

	public void setAuthzDate(Date authzDate);

	public void setOperation(String operation);

	public void setCheckListItemRef(long aCheckListItemRef);

}
