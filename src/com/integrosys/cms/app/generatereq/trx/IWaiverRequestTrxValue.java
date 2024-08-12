/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/trx/IWaiverRequestTrxValue.java,v 1.1 2003/09/11 05:49:17 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.trx;

import com.integrosys.cms.app.generatereq.bus.IWaiverRequest;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a waiver request trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/11 05:49:17 $ Tag: $Name: $
 */
public interface IWaiverRequestTrxValue extends ICMSTrxValue {
	/**
	 * Get the waiver request business entity
	 * 
	 * @return IWaiverRequest
	 */
	public IWaiverRequest getWaiverRequest();

	/**
	 * Get the staging waiver request business entity
	 * 
	 * @return IWaiverRequest
	 */
	public IWaiverRequest getStagingWaiverRequest();

	/**
	 * Set the waiver request business entity
	 * 
	 * @param value is of type IWaiverRequest
	 */
	public void setWaiverRequest(IWaiverRequest value);

	/**
	 * Set the staging waiver request business entity
	 * 
	 * @param value is of type IWaiverRequest
	 */
	public void setStagingWaiverRequest(IWaiverRequest value);
}