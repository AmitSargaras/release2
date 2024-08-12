package com.integrosys.cms.app.creditriskparam.proxy.policycap;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public interface SBPolicyCapProxyManager extends EJBObject {

	/**
	 * Maker updates policy cap for an exchange
	 * @param ctx of ITrxContext type
	 * @param trxValue of IPolicyCapTrxValue type
	 * @param policyCapList of IPolicyCap type
	 * @return IPolicyCapTrxValue - the interface representing the policy cap
	 *         trx obj
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue makerUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue,
			IPolicyCap[] policyCapList) throws PolicyCapException, RemoteException;

	/**
	 * Maker updates policy cap group
	 * @param ctx of ITrxContext type
	 * @param trxValue of IPolicyCapGroupTrxValue type
	 * @param policyCapGroup of IPolicyCapGroup type
	 * @return IPolicyCapGroupTrxValue - the interface representing the policy
	 *         cap trx obj
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue makerUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue,
			IPolicyCapGroup policyCapGroup) throws PolicyCapException, RemoteException;

	/**
	 * Approve the policy cap update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be approved
	 * @return Approved Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue checkerApproveUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Approve the policy cap group update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap Group to be approved
	 * @return Approved Policy Cap Group
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue checkerApproveUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Rejects the policy cap update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be rejected
	 * @return Rejected Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue checkerRejectUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Rejects the policy cap group update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap Group to be rejected
	 * @return Rejected Policy Cap Group
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue checkerRejectUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Close the (rejected) policy cap.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be closed
	 * @return Closed Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue makerClosePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Close the (rejected) policy cap group.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be closed
	 * @return Closed Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue makerClosePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap Exchange by the Exchange Code
	 * @param exchangeCode of String type
	 * @return IExchangePolicyCap - the Policy Cap Exchange
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCap[] getPolicyCapByExchange(String exchangeCode) throws PolicyCapException, RemoteException;

	// public Collection getPolicyCapByExchange(String exchangeCode) throws
	// PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap Exchange by the Exchange Code
	 * @param exchangeCode of String type
	 * @param bankEntity of String type
	 * @return IExchangePolicyCap - the Policy Cap Exchange
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroup getPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity)
			throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap List
	 * @param groupID - Group ID used to retrieve trxValue
	 * @return IPolicyCapTrxValue
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue getPolicyCapTrxValue(long groupID) throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap Group with the primary id
	 * @param policyCapGroupID - policyCapGroup ID used to retrieve trxValue
	 * @return IPolicyCapGroupTrxValue
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue getPolicyCapGroupTrxValue(long policyCapGroupID) throws PolicyCapException,
			RemoteException;

	/**
	 * Get the Policy Cap Transaction Object by the Transaction ID
	 * @param trxID of String type
	 * @return IPolicyCapTrxValue - the Policy Cap List for the given trxID
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCapTrxValue getPolicyCapTrxValueByTrxID(String trxID) throws PolicyCapException, RemoteException;

	/**
	 * Get the Policy Cap Group Transaction Object by the Transaction ID
	 * @param trxID of String type
	 * @return IPolicyCapGroupTrxValue - the Policy Cap List for the given trxID
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCapGroupTrxValue getPolicyCapGroupTrxValueByTrxID(String trxID) throws PolicyCapException,
			RemoteException;
}
