/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBCommodityTitleDocumentLocal.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface to EBCommodityTitleDocumentBean.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBCommodityTitleDocumentLocal extends EJBLocalObject {
	/**
	 * Get commodity title document.
	 * 
	 * @return commodity title document
	 */
	public ICommodityTitleDocument getValue();

	/**
	 * Set commodity title document.
	 * 
	 * @param titleDoc is of type ICommodityTitleDocument
	 */
	public void setValue(ICommodityTitleDocument titleDoc);

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}