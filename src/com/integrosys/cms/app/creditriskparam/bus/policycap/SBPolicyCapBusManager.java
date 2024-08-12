package com.integrosys.cms.app.creditriskparam.bus.policycap;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface SBPolicyCapBusManager extends EJBObject {

	/**
	 * Create a list of policy cap
	 * @param policyCapList - IPolicyCap[]
	 * @return IPolicyCap - the policy cap being created
	 * @throws PolicyCapException on errors
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCap[] create(IPolicyCap[] policyCapList) throws PolicyCapException, RemoteException;

	/**
	 * Update a list of policy cap
	 * @param policyCapList - IPolicyCap[]
	 * @return ICheckList - the policy cap being updated
	 * @throws PolicyCapException on errors
	 * @throws ConcurrentUpdateException on concurrent updates
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCap[] update(IPolicyCap[] policyCapList) throws ConcurrentUpdateException, PolicyCapException,
			RemoteException;

	/**
	 * Get the Policy Cap by the Exchange Code
	 * @param exchangeCode of String type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCap[] getPolicyCapByExchange(String exchangeCode) throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap List
	 * @param groupID - Group ID used to retrieve the trxValue
	 * @return IPolicyCapTrxValue
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCap[] getPolicyCapByGroupID(Long groupID) throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap by the Transaction ID
	 * @param ctx
	 * @param trxID of String type
	 * @return IPolicyCapTrxValue - the Policy Cap List
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 * @throws RemoteException on remote errors
	 */
	public IPolicyCapTrxValue getPolicyCapTrxValueByTrxID(ITrxContext ctx, String trxID) throws PolicyCapException,
			RemoteException;;

	// TODO IPolicyCapGroupTrxValue

	/**
	 * Crate a new Policy Cap Group
	 * @return IPolicyCapGroup
	 * @param policyCapGroup
	 */
	public IPolicyCapGroup createPolicyCapGroup(IPolicyCapGroup policyCapGroup) throws PolicyCapException,
			RemoteException;

	/**
	 * Update an existing Policy Cap Group
	 * @param policyCapGroup
	 * @return
	 * @throws ConcurrentUpdateException
	 * @throws PolicyCapException
	 * @throws RemoteException
	 */
	public IPolicyCapGroup updatePolicyCapGroup(IPolicyCapGroup policyCapGroup) throws ConcurrentUpdateException,
			PolicyCapException, RemoteException;

	/**
	 * Get Policy Cap Group by given stock exchange code and bank entity
	 * @param exchangeCode
	 * @param bankEntity
	 * @return
	 * @throws PolicyCapException on errors
	 * @throws RemoteException on remote err
	 */
	public IPolicyCapGroup getPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity)
			throws PolicyCapException, RemoteException;

	/**
	 * Get policy cap group by policycapgroup
	 * @param policyCapId
	 * @return
	 * @throws PolicyCapException
	 * @throws RemoteException
	 */
	public IPolicyCapGroup getPolicyCapGroup(Long policyCapId) throws PolicyCapException, RemoteException;

}
