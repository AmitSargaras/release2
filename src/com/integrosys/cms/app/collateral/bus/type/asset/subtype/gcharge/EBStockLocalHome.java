/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBStockLocalHome.java,v 1.2 2005/03/15 03:18:42 lini Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface to the details of a stock.
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/15 03:18:42 $ Tag: $Name: $
 */
public interface EBStockLocalHome extends EJBLocalHome {
	/**
	 * Create stock record.
	 * 
	 * @param stock of type IStock
	 * @return stock detail ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBStockLocal create(IStock stock) throws CreateException;

	/**
	 * Find the ejb by primary key, the assetGCStockID.
	 * 
	 * @param assetGCStockID stock id
	 * @return stock detail ejb object
	 * @throws javax.ejb.FinderException on error finding the ejb
	 */
	public EBStockLocal findByPrimaryKey(Long assetGCStockID) throws FinderException;

}