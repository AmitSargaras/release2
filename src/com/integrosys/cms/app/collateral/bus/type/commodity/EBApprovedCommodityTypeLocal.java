/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBApprovedCommodityTypeLocal.java,v 1.5 2004/08/18 02:39:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for approved commodity type entity bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/08/18 02:39:43 $ Tag: $Name: $
 */
public interface EBApprovedCommodityTypeLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of approved commodity type.
	 * 
	 * @return the object of approved commodity type
	 */
	public IApprovedCommodityType getValue();

	/**
	 * Persist the newly updated approved commodity type.
	 * 
	 * @param value an object of IApprovedCommodityType
	 */
	public void setValue(IApprovedCommodityType value);

	/**
	 * Soft delete this approved commodity type.
	 */
	public void softDelete();
}