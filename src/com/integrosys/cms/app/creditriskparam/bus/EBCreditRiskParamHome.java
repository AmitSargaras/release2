/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/creditrixkparam/bus/EBCreditRiskParamHome.java,v 1.1 2007/02/12 08:34:09 shphoon Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * EBCreditRiskParamHome Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBCreditRiskParamHome extends EJBHome {

	/**
	 * Creates a CreditRiskParam entry
	 * @param aICreditRiskParam ICreditRiskParam
	 * @throws javax.ejb.CreateException is error at record creation
	 * @throws java.rmi.RemoteException on run-time errors
	 * @return String - the primary key ID
	 */
	EBCreditRiskParam create(ICreditRiskParam aICreditRiskParam) throws CreateException, RemoteException;

	/*
	 * Find by primary key which is the CreditRiskParam's ID
	 * 
	 * @param aICreditRiskParamID long
	 * 
	 * @return EBCreditRiskParam
	 * 
	 * @throws FinderException if not found
	 * 
	 * @throws RemoteException on run-time errors
	 */
	EBCreditRiskParam findByPrimaryKey(Long aICreditRiskParamID) throws FinderException, RemoteException;

	/**
	 * Finds by feed ID.
	 * @param feedId The feed ID.
	 * @return EBCreditRiskParam
	 * @throws javax.ejb.FinderException if not found.
	 * @throws java.rmi.RemoteException on run-time errors.
	 */
	EBCreditRiskParam findByFeedId(Long feedId) throws FinderException, RemoteException;

	/**
	 * Finds by param reference.
	 * @param paramRef The param ref Id.
	 * @return EBCreditRiskParam
	 * @throws javax.ejb.FinderException if not found.
	 * @throws java.rmi.RemoteException on run-time errors.
	 */
	Collection findByParamRef(Long paramRef) throws FinderException, RemoteException;
}
