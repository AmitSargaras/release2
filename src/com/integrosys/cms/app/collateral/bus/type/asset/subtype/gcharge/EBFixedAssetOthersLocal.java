/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBFixedAssetOthersLocal.java,v 1.2 2005/03/17 06:08:34 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * Entity bean local interface to the details of a fixed asset/others.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/03/17 06:08:34 $ Tag: $Name: $
 */
public interface EBFixedAssetOthersLocal extends EJBLocalObject {
	/**
	 * Get the fixed asset/others business object.
	 * 
	 * @return IFixedAssetOthers
	 */
	public IFixedAssetOthers getValue();

	/**
	 * Set the fixed asset/others business object.
	 * 
	 * @param fao is of type IFixedAssetOthers
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 */
	public void setValue(IFixedAssetOthers fao) throws ConcurrentUpdateException;

	/**
	 * Set status of the fixed asset/others business object.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Soft delete of the fixed asset/others business object.
	 * 
	 * @param fao is of type IFixedAssetOthers
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 */
	public void deleteFixedAssetOthers(IFixedAssetOthers fao) throws ConcurrentUpdateException;
}