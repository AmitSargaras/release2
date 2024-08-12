/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/sccertificate/trx/ISCCertificateTrxValue.java,v 1.1 2003/08/08 12:45:37 hltan Exp $
 */
package com.integrosys.cms.app.sccertificate.trx;

import com.integrosys.cms.app.sccertificate.bus.ISCCertificate;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This interface represents a sc certificate trx value.
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/08 12:45:37 $ Tag: $Name: $
 */
public interface ISCCertificateTrxValue extends ICMSTrxValue {
	/**
	 * Get the sc certificate business entity
	 * 
	 * @return ISCCertificate
	 */
	public ISCCertificate getSCCertificate();

	/**
	 * Get the staging sc certificate business entity
	 * 
	 * @return ISCCertificate
	 */
	public ISCCertificate getStagingSCCertificate();

	/**
	 * Set the sc certificate business entity
	 * 
	 * @param value is of type ISCCertificate
	 */
	public void setSCCertificate(ISCCertificate value);

	/**
	 * Set the staging sc certificate business entity
	 * 
	 * @param value is of type ISCCertificate
	 */
	public void setStagingSCCertificate(ISCCertificate value);
}