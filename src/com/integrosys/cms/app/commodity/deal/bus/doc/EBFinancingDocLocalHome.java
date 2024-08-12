/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBFinancingDocLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines financing doc create and finder methods for local clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBFinancingDocLocalHome extends EJBLocalHome {
	/**
	 * Create deal financing documentation record.
	 * 
	 * @param doc of type IFinancingDoc
	 * @return commodity financing document ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBFinancingDocLocal create(IFinancingDoc doc) throws CreateException;

	/**
	 * Find commodity financing doc by primary key, the document id.
	 * 
	 * @param docID financing document id
	 * @return commodity financing document ejb object
	 * @throws FinderException on error finding the deal
	 */
	public EBFinancingDocLocal findByPrimaryKey(Long docID) throws FinderException;
}
