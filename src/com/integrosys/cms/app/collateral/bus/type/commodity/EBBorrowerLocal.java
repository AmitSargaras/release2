/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBBorrowerLocal.java,v 1.3 2004/08/17 11:57:46 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for borrower entity bean.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 11:57:46 $ Tag: $Name: $
 */
public interface EBBorrowerLocal extends EJBLocalObject {
	/**
	 * Get borrower business object.
	 * 
	 * @return IBorrower
	 */
	public IBorrower getValue();

	/**
	 * Persist newly updated borrower.
	 * 
	 * @param borrower of type IBorrower
	 */
	public void setValue(IBorrower borrower);

	/**
	 * Set status of this borrower.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}