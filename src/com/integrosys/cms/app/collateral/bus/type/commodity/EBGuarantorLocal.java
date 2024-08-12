/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBGuarantorLocal.java,v 1.3 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Local interface for guarantor entity bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface EBGuarantorLocal extends javax.ejb.EJBLocalObject {
	/**
	 * Get guarantor business object.
	 * 
	 * @return IGuarantor
	 */
	public IGuarantor getValue();

	/**
	 * Persist newly updated guarantor.
	 * 
	 * @param guarantor of type IGuarantor
	 */
	public void setValue(IGuarantor guarantor);

	/**
	 * Set status of this guarantor.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}