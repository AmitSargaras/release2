/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBFixedAssetOthersLocalHome.java,v 1.1 2005/03/16 05:09:15 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Entity bean local home interface to the details of a fixed asset/others.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/16 05:09:15 $ Tag: $Name: $
 */
public interface EBFixedAssetOthersLocalHome extends EJBLocalHome {
	/**
	 * Create stock record.
	 * 
	 * @param fao of type IFixedAssetOthers
	 * @return fixed asset/others detail ejb object
	 * @throws javax.ejb.CreateException on error creating the ejb
	 */
	public EBFixedAssetOthersLocal create(IFixedAssetOthers fao) throws CreateException;

	/**
	 * Find the ejb by primary key, the assetGCStockID.
	 * 
	 * @param assetGCFixedAssetOthersID fao id
	 * @return fixed asset/others detail ejb object
	 * @throws javax.ejb.FinderException on error finding the ejb
	 */
	public EBFixedAssetOthersLocal findByPrimaryKey(Long assetGCFixedAssetOthersID) throws FinderException;

}