/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/insurance/EBCDSItemLocalHome.java,v 1.1 2005/09/29 09:39:37 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.insurance;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface for credit default swap (CDS).
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/09/29 09:39:37 $ Tag: $Name: $
 */
public interface EBCDSItemLocalHome extends EJBLocalHome {
	/**
	 * Create a new Credit Default Swaps item.
	 * 
	 * @param equity of type ICDSItem
	 * @return local credit default swaps ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBCDSItemLocal create(ICDSItem item) throws CreateException;

	/**
	 * Find the credit default swaps entity bean by its primary key.
	 * 
	 * @param pk credit default swaps id
	 * @return local credit default swaps ejb object
	 * @throws FinderException on error finding the equity
	 */
	public EBCDSItemLocal findByPrimaryKey(Long pk) throws FinderException;

	/**
	 * Find the credit default swaps by its reference id.
	 * 
	 * @param refID reference id for staging and actual data
	 * @return local credit default swaps ejb object
	 * @throws FinderException on error finding the credit default swaps
	 */
	public EBCDSItemLocal findByRefID(long refID) throws FinderException;
}