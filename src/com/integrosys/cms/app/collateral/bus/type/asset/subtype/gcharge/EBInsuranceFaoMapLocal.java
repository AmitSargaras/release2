/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBInsuranceFaoMapLocal.java,v 1.1 2005/03/17 07:06:15 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import javax.ejb.EJBLocalObject;

/**
 * Entity bean local interface for insurance info.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/03/17 07:06:15 $ Tag: $Name: $
 */
public interface EBInsuranceFaoMapLocal extends EJBLocalObject {
	/**
	 * Get Insurance Fao map
	 * 
	 * @return insurance Fao map
	 */
	public IGenChargeMapEntry getValue();

	/**
	 * Set insurance Fao map.
	 * 
	 * @param mapEntry of type IGenChargeMapEntry
	 */
	public void setValue(IGenChargeMapEntry mapEntry);

	/**
	 * Set status of the insurance Fao map.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}