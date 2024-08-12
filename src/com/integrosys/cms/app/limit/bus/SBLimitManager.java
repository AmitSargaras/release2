/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/SBLimitManager.java,v 1.18 2006/09/23 08:05:52 hmbao Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;

/**
 * This is the remote interface to the SBLimitManager session bean.
 * 
 * @author $Author: hmbao $
 * @version $Revision: 1.18 $
 * @since $Date: 2006/09/23 08:05:52 $ Tag: $Name: $
 */
public interface SBLimitManager extends EJBObject {
	/**
	 * Create Limit Profile. This method does not create the limits details.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimitProfile createLimitProfile(ILimitProfile value) throws LimitException, RemoteException;

	/**
	 * Create a single limit
	 * 
	 * @param value is of type ILimit
	 * @return ILimit
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimit createLimit(ILimit value) throws LimitException, RemoteException;

	/**
	 * Create a list of limits.
	 * 
	 * @param lmts of type ILimit[]
	 * @return ILimit[]
	 * @throws LimitException on any errors encountered
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public ILimit[] createLimits(ILimit[] lmts) throws LimitException, RemoteException;

	/**
	 * Create a single CoBorrower limit
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICoBorrowerLimit
	 * @throws LimitException, RemoteException on errors
	 */
	public ICoBorrowerLimit createCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException, RemoteException;

	/**
	 * Create Limits. This method creates the Limits contained in a Limit
	 * Profile.
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimitProfile createLimits(ILimitProfile value) throws LimitException, RemoteException;

	/**
	 * create co borrower limits
	 * @param limits
	 * @return
	 * @throws LimitException
	 * @throws RemoteException
	 */
	public ICoBorrowerLimit[] createCoBorrowerLimits(ICoBorrowerLimit[] limits) throws LimitException, RemoteException;

	/**
	 * Update limit profile information
	 * 
	 * @param value is of type ILimitProfile
	 * @return ILimitProfile
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimitProfile updateLimitProfile(ILimitProfile value) throws LimitException, RemoteException;

	/**
	 * Update limit information
	 * 
	 * @param value is of type ILimit
	 * @return ILimit
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimit updateLimit(ILimit value) throws LimitException, RemoteException;

	/**
	 * Update operational limit.
	 * 
	 * @param limit is of type ILimit
	 * @return ILimit
	 * @throws LimitException on errors
	 * @throws RemoteException on error during remote method call
	 */
	public ILimit updateOperationalLimit(ILimit limit) throws LimitException, RemoteException;

	/**
	 * Update CoBorrower limit information
	 * 
	 * @param value is of type ICoBorrowerLimit
	 * @return ICoBorrowerLimit
	 * @throws LimitException, RemoteException on errors
	 */
	public ICoBorrowerLimit updateCoBorrowerLimit(ICoBorrowerLimit value) throws LimitException, RemoteException;

	/**
	 * Delete limit profile. This method will cascade delete the limits too.
	 * 
	 * @param value is of type ILimitProfile
	 * @throws LimitException, RemoteException on errors
	 */
	public void deleteLimitProfile(ILimitProfile value) throws LimitException, RemoteException;

	/*
	 * Soft Delete limit profile. This method will cascade delete the limits
	 * too.
	 * 
	 * @param value is of type ILimitProfile
	 * 
	 * @throws LimitException, RemoteException on errors
	 */
	public ILimitProfile removeLimitProfile(ILimitProfile value) throws LimitException, RemoteException;

	/**
	 * Get a limit profile given a limit profile ID
	 * 
	 * @param limitProfileID is of type long
	 * @return ILimitProfile
	 * @throws LimitException, RemoteException on error
	 */
	public ILimitProfile getLimitProfile(long limitProfileID) throws LimitException, RemoteException;

	/**
	 * Get a limit given a limit ID
	 * 
	 * @param limitID is of type long
	 * @return ILimit
	 * @throws LimitException, RemoteException on error
	 */
	public ILimit getLimit(long limitID) throws LimitException, RemoteException;

	/**
	 * reset the given limit objects.
	 * 
	 * @param lmts is of type ILimit[]
	 * @param excludeMethods a list of methods to be excluded in resetting the
	 *        limits
	 * @throws LimitException on error getting the limits
	 * @throws RemoteException on any unexpected error during remote method call
	 */
	public ILimit[] resetLimits(ILimit[] lmts, String[] excludeMethods) throws LimitException, RemoteException;

	/**
	 * reset co borrower limits
	 * @param coBorrowerLimits
	 * @throws LimitException
	 * @throws RemoteException
	 */
	public ICoBorrowerLimit[] resetCoBorrowerLimits(ICoBorrowerLimit[] coBorrowerLimits) throws LimitException,
			RemoteException;

	/**
	 * Get a CoBorrower limit given a limit ID
	 * 
	 * @param limitID is of type long and is the CoBorrower Limit ID
	 * @return ICoBorrowerLimit
	 * @throws LimitException, RemoteException on error
	 */
	public ICoBorrowerLimit getCoBorrowerLimit(long limitID) throws LimitException, RemoteException;

	public List getFSVBalForMainborrowLimit(String limitId) throws RemoteException;

	public List getFSVBalForCoborrowLimit(String limitId) throws RemoteException;

	public ILimit createLimitWithAccounts(ICMSCustomer customer, ILimit value) throws LimitException, RemoteException;

	public ILimit createLimitWithAccounts(ILimit value) throws LimitException, RemoteException;

	public ILimit updateLimitWithAccounts(ICMSCustomer customer, ILimit originLimit, ILimit value)
			throws LimitException, RemoteException;

	public ILimit updateLimitWithAccounts(ILimit originLimit, ILimit value) throws LimitException, RemoteException;

	public void processCheckList(String limitId) throws RemoteException;
	
	public ILimit updateLimitWithUdfAccounts(ILimit originLimit, ILimit value) throws LimitException, RemoteException;
	
	
}