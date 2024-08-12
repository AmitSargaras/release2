/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanScheduleLocal.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

/**
 * Local interface for loan schedule entity bean.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBLoanScheduleLocal extends javax.ejb.EJBLocalObject {

	/**
	 * Get loan schedule business object.
	 * 
	 * @return ILoanSchedule
	 */
	public ILoanSchedule getValue();

	/**
	 * Persist newly updated loan schedule.
	 * 
	 * @param loanSchedule of type ILoanSchedule
	 */
	public void setValue(ILoanSchedule loanSchedule);

	/**
	 * Set status of this loan schedule.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}