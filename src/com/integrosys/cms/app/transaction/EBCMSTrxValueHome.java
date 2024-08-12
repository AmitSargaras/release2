/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/EBCMSTrxValueHome.java,v 1.5 2003/08/18 07:11:28 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

/**
 * This is the Home interface to the EBCMSTrxValue Entity Bean
 * 
 * @author Alfred Lee
 */
public interface EBCMSTrxValueHome extends EJBHome {
	/**
	 * Create a transaction
	 * 
	 * @param value is of type ICMSTrxValue
	 * @return EBCMSTrxValue
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBCMSTrxValue create(ICMSTrxValue value) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the transaction ID
	 * 
	 * @param pk is the String value of the transaction ID
	 * @return EBCMSTrxValue
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCMSTrxValue findByPrimaryKey(String pk) throws FinderException, RemoteException;

	/**
	 * Find by reference ID, the key of the biz record
	 * 
	 * @param aReferenceID is the long value of the reference ID
	 * @param aTransactionType us the String value of the transaction type
	 * @return EBCMSTrxValue
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCMSTrxValue findByRefIDAndTrxType(Long aReferenceID, String aTransactionType) throws FinderException,
			RemoteException;

	/**
	 * Find by stage reference ID, the key of the biz record
	 * 
	 * @param aStageReferenceID is the long value of the stage reference ID
	 * @param aTransactionType us the String value of the transaction type
	 * @return EBCMSTrxValue
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public EBCMSTrxValue findByStageRefIDAndTrxType(Long aStageReferenceID, String aTransactionType)
			throws FinderException, RemoteException;

	/**
	 * Find all transactions given a parent transaction ID
	 * 
	 * @param parentTrxID is of type long
	 * @param trxType the String value of the transaction type
	 * @return Collection of EBCMSTrxValue objects
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findByParentTrxID(Long parentTrxID, String trxType) throws FinderException, RemoteException;

	/**
	 * Find working transaction, the status <> CLOSED by transaction type
	 *
	 * @param trxType us the String value of the transaction type
	 * @return Collection of EBCMSTrxValue
	 * @throws FinderException on error
	 * @throws RemoteException
	 */
	public Collection findWorkingTrxByTrxType(String trxType) throws FinderException, RemoteException;    
}