/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/EBPledgorCreditGradeHome.java,v 1.1 2003/09/03 09:26:00 elango Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * Entity bean remote home interface for EBPledgorCreditGradeBean.
 * 
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/09/03 09:26:00 $ Tag: $Name: $
 */
public interface EBPledgorCreditGradeHome extends EJBHome {
	/**
	 * Called by the client to create pledgor credit grade
	 * 
	 * @param pledgor of type IPledgorCreditGrade
	 * @return pledgor credit grade ejb object
	 * @throws CreateException on error while creating the pledgor credit grade
	 * @throws RemoteException on any other error during remote method call
	 */
	public EBPledgorCreditGrade create(IPledgorCreditGrade pledgor) throws CreateException, RemoteException;

	/**
	 * Find the pledgor credit grade by its primary key.
	 * 
	 * @param pk the pledgor credit grade primary key
	 * @return pledgor credit grade ejb object
	 * @throws FinderException on error while finding the pledgor credit grade
	 * @throws RemoteException on error during remote method call
	 */
	public EBPledgorCreditGrade findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find the pledgor credit grades given the pledgor id.
	 * 
	 * @param pledgorID pledgor id
	 * @return a collection of EBPledgorCreditGrade
	 * @throws FinderException on error finding the credit grades
	 * @throws RemoteException on error during remote method call
	 */
	public Collection findByCMSPledgorID(long pledgorID) throws FinderException, RemoteException;
}