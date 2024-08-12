/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestDescription.java,v 1.2 2003/09/17 07:26:59 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface defines the list of attributes that is required for request
 * description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 07:26:59 $ Tag: $Name: $
 */
public interface IRequestDescription extends Serializable {

	public IRequestLimitInfo[] getRequestLimitInfoList();;

	public Amount getTotalApprovedLimitAmt();

	public Amount getTotalActivatedLimitAmt();

	public IRequestCollateralInfo[] getRequestCollateralInfoList();

	public Amount getTotalCollateralCMVAmt();

	public void setRequestLimitInfoList(IRequestLimitInfo[] anIRequestLimitInfoList);

	public void setTotalApprovedLimitAmt(Amount aTotalApprovedLimitAmt);

	public void setTotalActivatedLimitAmt(Amount aTotalActivatedLimitAmt);

	public void setRequestCollateralInfoList(IRequestCollateralInfo[] anIRequestCollateralInfoList);

	public void setTotalCollateralCMVAmt(Amount aTotalCollateralCMVAmt);
}
