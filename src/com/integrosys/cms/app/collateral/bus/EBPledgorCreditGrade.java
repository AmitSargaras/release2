/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgorCreditGrade.java,v 1.1 2003/09/03 09:26:00 elango Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * Remote interface to EBPledgorCreditGradeBean.
 * 
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 09:26:00 $ Tag: $Name: $
 */
public interface EBPledgorCreditGrade extends EJBObject {
	/**
	 * Get the pledgor credit grade information.
	 * 
	 * @return pledgor credit grade
	 */
	public IPledgorCreditGrade getValue() throws RemoteException;

	/**
	 * Set the pledgor credit grade to this entity.
	 * 
	 * @param creditGrade is of type IPledgorCreditGrade
	 */
	public void setValue(IPledgorCreditGrade creditGrade) throws RemoteException;
}