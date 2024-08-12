/*
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 */
package com.integrosys.cms.app.creditriskparam.bus;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * EBCreditRiskParam Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface EBCreditRiskParam extends EJBObject {

	/**
	 * Returns the primary key
	 * @return String - the primary key ID
	 */
	long getParameterId() throws RemoteException;

	long getFeedId() throws RemoteException;

	long getParameterRef() throws RemoteException;

	/**
	 * Returns the value object representing this entity bean
	 */
	ICreditRiskParam getValue() throws RemoteException;

	/**
	 * Sets the entity using its corresponding value object.
	 */
	void setValue(ICreditRiskParam aICreditRiskParam) throws RemoteException, ConcurrentUpdateException;
}
