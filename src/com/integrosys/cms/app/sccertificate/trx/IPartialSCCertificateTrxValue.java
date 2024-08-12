/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/IPartialSCCertificateTrxValue.java,v 1.1 2003/08/11 12:51:02 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a partial sc certificate trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 12:51:02 $ Tag: $Name: $
 */
public interface IPartialSCCertificateTrxValue extends ICMSTrxValue {
	/**
	 * Get the partial sc certificate business entity
	 * 
	 * @return IPartialSCCertificate
	 */
	public IPartialSCCertificate getPartialSCCertificate();

	/**
	 * Get the staging partial sc certificate business entity
	 * 
	 * @return IPartialSCCertificate
	 */
	public IPartialSCCertificate getStagingPartialSCCertificate();

	/**
	 * Set the partial sc certificate business entity
	 * 
	 * @param value is of type IPartialSCCertificate
	 */
	public void setPartialSCCertificate(IPartialSCCertificate value);

	/**
	 * Set the staging partial sc certificate business entity
	 * 
	 * @param value is of type IPartialSCCertificate
	 */
	public void setStagingPartialSCCertificate(IPartialSCCertificate value);
}