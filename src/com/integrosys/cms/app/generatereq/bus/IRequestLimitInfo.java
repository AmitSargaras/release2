/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/IRequestLimitInfo.java,v 1.2 2003/09/17 07:26:59 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//java
import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface defines the list of attributes that is required for request
 * limit related information
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 07:26:59 $ Tag: $Name: $
 */
public interface IRequestLimitInfo extends Serializable {
	public long getLimitID();

	public String getLimitType();

	public Amount getApprovedLimitAmt();

	public Amount getActivatedLimitAmt();

	public void setLimitID(long aLimitID);

	public void setLimitType(String aLimitType);

	public void setApprovedLimitAmt(Amount anApprovedLimitAmt);

	public void setActivatedLimitAmt(Amount anActivatedLimitAmt);

}
