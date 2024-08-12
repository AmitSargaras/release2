/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

//import com.integrosys.base.businfra.search.*;
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBTradingAgreement Entity Bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBTradingAgreementHome extends EJBHome {

	/**
	 * Create a trading agreement entity bean
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the ITradingAgreement object
	 * @return EBTradingAgreement
	 * @throws CreateException, RemoteException
	 */
	public EBTradingAgreement create(long limitProfileID, ITradingAgreement value) throws CreateException,
			RemoteException;

	/**
	 * Find by primary Key, the agreement ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBTradingAgreement
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBTradingAgreement findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by limit profile ID
	 * 
	 * @param profileID is the Long value of the limit profile ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBTradingAgreement
	 * @throws FinderException, RemoteException on error
	 */
	public Collection findByLimitProfile(Long profileID, String status) throws FinderException, RemoteException;
}