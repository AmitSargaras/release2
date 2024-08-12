/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/cccertificate/trx/ICCCertificateTrxValue.java,v 1.1 2003/08/04 12:56:09 hltan Exp $
 */
package com.integrosys.cms.app.cccertificate.trx;

import com.integrosys.cms.app.cccertificate.bus.ICCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a cc certificate trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/04 12:56:09 $ Tag: $Name: $
 */
public interface ICCCertificateTrxValue extends ICMSTrxValue {
	/**
	 * Get the cc certificate business entity
	 * 
	 * @return ICCCertificate
	 */
	public ICCCertificate getCCCertificate();

	/**
	 * Get the staging cc certificate business entity
	 * 
	 * @return ICCCertificate
	 */
	public ICCCertificate getStagingCCCertificate();

	/**
	 * Set the cc certificate business entity
	 * 
	 * @param value is of type ICCCertificate
	 */
	public void setCCCertificate(ICCCertificate value);

	/**
	 * Set the staging cc certificate business entity
	 * 
	 * @param value is of type ICCCertificate
	 */
	public void setStagingCCCertificate(ICCCertificate value);
}