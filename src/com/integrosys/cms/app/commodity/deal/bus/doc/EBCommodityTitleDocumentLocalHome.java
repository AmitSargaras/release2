/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/doc/EBCommodityTitleDocumentLocalHome.java,v 1.2 2004/06/04 03:53:33 hltan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.doc;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines title document create and finder methods for local clients.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 03:53:33 $ Tag: $Name: $
 */
public interface EBCommodityTitleDocumentLocalHome extends EJBLocalHome {
	/**
	 * Create commodity title document record.
	 * 
	 * @param titleDoc of type ICommodityTitleDocument
	 * @return title document ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBCommodityTitleDocumentLocal create(ICommodityTitleDocument titleDoc) throws CreateException;

	/**
	 * Find commodity title document by its primary key, the title doc id.
	 * 
	 * @param titleDocID title document id
	 * @return title document ejb object
	 * @throws FinderException on error finding the title doc
	 */
	public EBCommodityTitleDocumentLocal findByPrimaryKey(Long titleDocID) throws FinderException;
}
