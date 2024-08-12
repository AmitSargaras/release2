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
 * This is the Home interface to the EBThresholdRating Entity Bean
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface EBThresholdRatingHome extends EJBHome {

	/**
	 * Create a Threshold Rating Entity Bean
	 * 
	 * @param limitProfileID is the limit profile ID in long value
	 * @param value is the IThresholdRating object
	 * @return EBThresholdRating
	 * @throws CreateException, RemoteException
	 */
	public EBThresholdRating create(long limitProfileID, IThresholdRating value) throws CreateException,
			RemoteException;

	/**
	 * Find by primary Key, the threshold rating ID
	 * 
	 * @param pk is the Long value of the primary key
	 * @return EBThresholdRating
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBThresholdRating findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Find by agreement ID
	 * 
	 * @param agreementID is the Long value of the agreement ID
	 * @param status the status to be excluded in this find
	 * @return Collection of EBThresholdRatingLocal
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findByAgreement(Long agreementID, String status) throws FinderException, RemoteException;
}