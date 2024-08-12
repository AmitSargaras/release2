/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/documentlocation/trx/ICCDocumentLocationTrxValue.java,v 1.1 2004/02/17 02:12:37 hltan Exp $
 */
package com.integrosys.cms.app.documentlocation.trx;

import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a CC document location trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2004/02/17 02:12:37 $ Tag: $Name: $
 */
public interface ICCDocumentLocationTrxValue extends ICMSTrxValue {
	/**
	 * Get the CC document location business entity
	 * 
	 * @return ICCDocumentLocation
	 */
	public ICCDocumentLocation getCCDocumentLocation();

	/**
	 * Get the staging CC document location business entity
	 * 
	 * @return ICCDocumentLocation
	 */
	public ICCDocumentLocation getStagingCCDocumentLocation();

	/**
	 * Set the CC document location business entity
	 * 
	 * @param value is of type ICCDocumentLocation
	 */
	public void setCCDocumentLocation(ICCDocumentLocation value);

	/**
	 * Set the staging CC document location business entity
	 * 
	 * @param value is of type ICCDocumentLocation
	 */
	public void setStagingCCDocumentLocation(ICCDocumentLocation value);
}