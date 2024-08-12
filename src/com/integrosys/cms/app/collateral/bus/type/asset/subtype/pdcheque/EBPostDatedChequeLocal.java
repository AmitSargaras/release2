/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/pdcheque/EBPostDatedChequeLocal.java,v 1.2 2003/10/23 06:21:00 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for post dated cheque.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/23 06:21:00 $ Tag: $Name: $
 */
public interface EBPostDatedChequeLocal extends EJBLocalObject {
	/**
	 * Get post dated cheque.
	 * 
	 * @return post dated cheque
	 */
	public IPostDatedCheque getValue();

	/**
	 * Set post dated cheque.
	 * 
	 * @param cheque of type IPostDatedCheque
	 */
	public void setValue(IPostDatedCheque cheque);

	/**
	 * Set status of the postdated cheque.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}