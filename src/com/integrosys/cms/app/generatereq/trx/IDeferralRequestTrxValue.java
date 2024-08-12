/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/IDeferralRequestTrxValue.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

import com.integrosys.cms.app.generatereq.bus.IDeferralRequest;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a deferral request trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public interface IDeferralRequestTrxValue extends ICMSTrxValue {
	/**
	 * Get the deferral request business entity
	 * 
	 * @return IDeferralRequest
	 */
	public IDeferralRequest getDeferralRequest();

	/**
	 * Get the staging deferral request business entity
	 * 
	 * @return IDeferralRequest
	 */
	public IDeferralRequest getStagingDeferralRequest();

	/**
	 * Set the deferral request business entity
	 * 
	 * @param value is of type IDeferralRequest
	 */
	public void setDeferralRequest(IDeferralRequest value);

	/**
	 * Set the staging deferral request business entity
	 * 
	 * @param value is of type IDeferralRequest
	 */
	public void setStagingDeferralRequest(IDeferralRequest value);
}