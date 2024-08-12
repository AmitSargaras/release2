/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanScheduleLocalHome.java,v 1.2 2004/08/18 08:00:51 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

/**
 * Defines loan agency loan schedule create and finder methods for local
 * clients.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/08/18 08:00:51 $ Tag: $Name: $
 */
public interface EBLoanScheduleLocalHome extends javax.ejb.EJBLocalHome {
	/**
	 * Create loan schedule.
	 * 
	 * @param loanSchedule of type ILoanSchedule
	 * @return local loan schedule ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBLoanScheduleLocal create(ILoanSchedule loanSchedule) throws CreateException;

	/**
	 * Find loan schedule by its primary key, the borrloan schedule id.
	 * 
	 * @param primaryKey loan schedule id
	 * @return local loan schedule ejb object
	 * @throws FinderException on error finding the loan schedule
	 */
	public EBLoanScheduleLocal findByPrimaryKey(Long primaryKey) throws FinderException;

	/**
	 * Find all loan schedules.
	 * 
	 * @return a Collection of loan schedule local ejb object
	 * @throws FinderException on error finding the loan schedules
	 */
	public Collection findAll() throws FinderException;
}