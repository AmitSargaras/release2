/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBFinancingDocLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBFinancingDocBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBFinancingDocLocal extends EJBLocalObject {
	/**
	 * Get the deal financing document.
	 * 
	 * @return financing document object
	 */
	public IFinancingDoc getValue();

	/**
	 * Set the deal financing document business object.
	 * 
	 * @param doc is of type IFinancingDoc
	 */
	public void setValue(IFinancingDoc doc);

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}