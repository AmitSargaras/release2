/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import javax.ejb.EJBLocalObject;

/**
 * Local interface to EBMFChecklistBean.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBMFChecklistLocal extends EJBLocalObject {
	/**
	 * Get the MF Checklist business object.
	 * 
	 * @return MF Checklist object
	 */
	public IMFChecklist getValue();
}