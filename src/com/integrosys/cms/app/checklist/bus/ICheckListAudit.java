/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListAudit.java,v 1.2 2003/10/29 11:29:11 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;

/**
 * This interface defines the list of attributes that is required for audit
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/29 11:29:11 $ Tag: $Name: $
 */
public interface ICheckListAudit extends Serializable {
	public String getCustomerCategory();

	public long getCustomerID();

	public String getCustomerName();

	public long getCheckListID();

	public long getCollateralID();

	public ICollateral getCollateral();

	public String getCollateralRef();

	public ICollateralType getCollateralType();

	public ICollateralSubType getCollateralSubType();

	public ILimitCharge[] getLimitChargeList();

	public IAuditItem[] getAuditItemList();

	public void setCustomerCategory(String aCustomerCategory);

	public void setCustomerID(long aCustomerID);

	public void setCustomerName(String aCustomerName);

	public void setCheckListID(long aCheckListID);

	public void setCollateralID(long aCollateralID);

	public void setCollateral(ICollateral anICollateral);

	public void setAuditItemList(IAuditItem[] anIAuditItemList);
}
