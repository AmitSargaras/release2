/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/finance/EBReceiptReleaseLocalHome.java,v 1.1 2004/09/07 07:36:30 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus.finance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines create and finder methods for local clients to warehouse receipt
 * details.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/09/07 07:36:30 $ Tag: $Name: $
 */
public interface EBReceiptReleaseLocalHome extends EJBLocalHome {
	/**
	 * Create release details.
	 * 
	 * @param release of type IReceiptRelease
	 * @return receipt release ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBReceiptReleaseLocal create(IReceiptRelease release) throws CreateException;

	/**
	 * Find release details by its primary key, the release id.
	 * 
	 * @param pk release id
	 * @return receipt release ejb object
	 * @throws FinderException on error finding the release details
	 */
	public EBReceiptReleaseLocal findByPrimaryKey(Long pk) throws FinderException;
}
