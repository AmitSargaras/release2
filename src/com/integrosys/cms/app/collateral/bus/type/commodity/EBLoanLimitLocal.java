/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanLimitLocal.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBLocalObject;

/**
 * Local interface for loan agency limit entity bean.
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBLoanLimitLocal extends EJBLocalObject {

	/**
	 * Get loan agency limit business object.
	 * 
	 * @return IBorrower
	 */
	public ILoanLimit getValue();

	/**
	 * Persist newly updated loan agency limit.
	 * 
	 * @param loanLimit of type ILoanLimit
	 */
	public void setValue(ILoanLimit loanLimit);

	/**
	 * Set status of this loan agency limit.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}
