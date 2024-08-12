/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/EBLoanAgencyLocal.java,v 1.4 2004/08/17 07:36:19 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import javax.ejb.EJBLocalObject;

/**
 * Defines LoanAgency home methods for clients.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 07:36:19 $ Tag: $Name: $
 */
public interface EBLoanAgencyLocal extends EJBLocalObject {
	/**
	 * Retrieve an instance of loan and agency.
	 * 
	 * @return commodity loan Agency
	 */
	public ILoanAgency getValue();

	/**
	 * Persist the changes of commodity loan agency.
	 * 
	 * @param value of type ILoanAgency
	 */
	public void setValue(ILoanAgency value);

	/**
	 * Soft delete the loan and agency.
	 */
	public void softDelete();
}