/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestCollateralInfo.java,v 1.1 2003/09/11 05:48:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;

/**
 * This interface defines the list of attributes that is required for request
 * collateral related information
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:48:55 $ Tag: $Name: $
 */
public interface IRequestCollateralInfo extends Serializable {
	public long getCollateralID();

	public ICollateralType getCollateralType();

	public ICollateralSubType getCollateralSubType();

	public String[] getNatureOfChargeList();

	public Amount getCollateralCMVAmt();

	public String getCheckListStatus();

	public void setCollateralID(long aCollateralID);

	public void setCollateralType(ICollateralType anICollateralType);

	public void setCollateralSubType(ICollateralSubType anICollateralSubType);

	public void setNatureOfChargeList(String[] aNatureOfChargeList);

	public void setCollateralCMVAmt(Amount aCollateralCMVAmt);

	public void setCheckListStatus(String aCheckListStatus);
}
