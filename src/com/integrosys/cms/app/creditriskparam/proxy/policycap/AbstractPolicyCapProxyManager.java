package com.integrosys.cms.app.creditriskparam.proxy.policycap;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.OBPolicyCapGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.OBPolicyCapTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.policycap.PolicyCapGroupTrxControllerFactory;
import com.integrosys.cms.app.creditriskparam.trx.policycap.PolicyCapTrxControllerFactory;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public abstract class AbstractPolicyCapProxyManager implements IPolicyCapProxyManager {

	public IPolicyCapTrxValue getPolicyCapTrxValue(long groupID) throws PolicyCapException {

		DefaultLogger.debug(this, ">>>>>>>>>>> In getPolicyCapTrxValue");
		if (groupID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new PolicyCapException(
					"Invalid groupID. Unable to get IPolicyCapTrxValue object from invalid group id");
		}

		DefaultLogger.debug(this, "Reading from ReadPolicyCapOperation");
		IPolicyCapTrxValue trxValue = new OBPolicyCapTrxValue();
		trxValue.setReferenceID(String.valueOf(groupID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_POLICY_CAP);
		return operate(trxValue, param);
	}

	public IPolicyCapGroupTrxValue getPolicyCapGroupTrxValue(long policyCapGroupID) throws PolicyCapException {

		DefaultLogger.debug(this, ">>>>>>>>>>> In getPolicyCapGroupTrxValue");
		if (policyCapGroupID == ICMSConstant.LONG_INVALID_VALUE) {
			throw new PolicyCapException(
					"Invalid policyCapGroupID. Unable to get IPolicyCapGroupTrxValue object from invalid group id");
		}

		DefaultLogger.debug(this, "Reading from ReadPolicyCapGroupOperation");
		IPolicyCapGroupTrxValue trxValue = new OBPolicyCapGroupTrxValue();
		trxValue.setReferenceID(String.valueOf(policyCapGroupID));
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP_GROUP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_POLICY_CAP_GROUP);
		return operateGroup(trxValue, param);
	}

	public IPolicyCapTrxValue getPolicyCapTrxValueByTrxID(String trxID) throws PolicyCapException {

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> getPolicyCapTrxValueByTrxID: Trx ID=" + trxID);
		if ((trxID == null) || trxID.equals("")) {
			throw new PolicyCapException(
					"Invalid trxID. Unable to get IPolicyCapTrxValue object from invalid transaction id");
		}

		IPolicyCapTrxValue trxValue = new OBPolicyCapTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_POLICY_CAP_ID);
		return operate(trxValue, param);
	}

	public IPolicyCapGroupTrxValue getPolicyCapGroupTrxValueByTrxID(String trxID) throws PolicyCapException {

		DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>> getPolicyCapGroupTrxValueByTrxID: Trx ID=" + trxID);
		if ((trxID == null) || trxID.equals("")) {
			throw new PolicyCapException(
					"Invalid trxID. Unable to get IPolicyCapGroupTrxValue object from invalid transaction id");
		}

		IPolicyCapGroupTrxValue trxValue = new OBPolicyCapGroupTrxValue();
		trxValue.setTransactionID(trxID);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP_GROUP);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_POLICY_CAP_GROUP_ID);
		return operateGroup(trxValue, param);
	}

	/**
	 * Maker updates policy cap for an exchange
	 * @param ctx of ITrxContext type
	 * @param trxValue of IPolicyCapTrxValue type
	 * @param policyCapList of IPolicyCap[] type
	 * @return IPolicyCapTrxValue - the interface representing the policy cap
	 *         trx obj
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue makerUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue,
			IPolicyCap[] policyCapList) throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be rejected is null!!!");
		}
		if (policyCapList == null) {
			throw new PolicyCapException("The IPolicyCap to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, policyCapList);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP);
		return operate(trxValue, param);
	}

	/**
	 * Maker updates policy cap group for an exchange
	 * @param ctx of ITrxContext type
	 * @param trxValue of IPolicyCapTrxValue type
	 * @param policyCapList of IPolicyCap[] type
	 * @return IPolicyCapTrxValue - the interface representing the policy cap
	 *         trx obj
	 * @throws PolicyCapGroupException on errors
	 */
	public IPolicyCapGroupTrxValue makerUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue,
			IPolicyCapGroup policyCapGroup) throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be rejected is null!!!");
		}
		if (policyCapGroup == null) {
			throw new PolicyCapException("The IPolicyCapGroup to be updated is null !!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue, policyCapGroup);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_POLICY_CAP_GROUP);
		return operateGroup(trxValue, param);
	}

	/**
	 * Approve the policy cap update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be approved
	 * @return Approved Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue checkerApproveUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_POLICY_CAP);
		return operate(trxValue, param);
	}

	/**
	 * Approve the policy cap group update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap Group to be approved
	 * @return Approved Policy Cap Group
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue checkerApproveUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be approved is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_POLICY_CAP_GROUP);
		return operateGroup(trxValue, param);
	}

	/**
	 * Rejects the policy cap update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be rejected
	 * @return Rejected Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue checkerRejectUpdatePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be rejected is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_POLICY_CAP);
		return operate(trxValue, param);
	}

	/**
	 * Rejects the policy cap group update.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap Group to be rejected
	 * @return Rejected Policy Cap Group
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue checkerRejectUpdatePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be rejected is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_POLICY_CAP_GROUP);
		return operateGroup(trxValue, param);
	}

	/**
	 * Close the (rejected) policy cap.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be closed
	 * @return Closed Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapTrxValue makerClosePolicyCap(ITrxContext ctx, IPolicyCapTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be close is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_POLICY_CAP);
		return operate(trxValue, param);
	}

	/**
	 * Close the (rejected) policy cap group.
	 * @param ctx - Transaction Context to use
	 * @param trxValue - Policy Cap to be closed
	 * @return Closed Policy Cap
	 * @throws PolicyCapException on errors
	 */
	public IPolicyCapGroupTrxValue makerClosePolicyCapGroup(ITrxContext ctx, IPolicyCapGroupTrxValue trxValue)
			throws PolicyCapException {
		if (ctx == null) {
			throw new PolicyCapException("The anITrxContext is null!!!");
		}
		if (trxValue == null) {
			throw new PolicyCapException("The policyCapTrxValue to be close is null!!!");
		}
		trxValue = formulateTrxValue(ctx, trxValue);
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_POLICY_CAP_GROUP);
		return operateGroup(trxValue, param);
	}

	// **************** Helper Methods ************
	private IPolicyCapTrxValue operate(ITrxValue trxVal, ITrxParameter param) throws PolicyCapException {
		if (trxVal == null) {
			throw new PolicyCapException("ITrxValue is null!");
		}

		try {
			ITrxController controller = new PolicyCapTrxControllerFactory().getController(trxVal, param);
			if (controller == null) {
				throw new PolicyCapException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (IPolicyCapTrxValue) obj;

		}
		catch (TransactionException e) {
			rollback();
			throw new PolicyCapException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new PolicyCapException("Exception caught! " + e.toString(), e);
		}
	}

	private IPolicyCapGroupTrxValue operateGroup(ITrxValue trxVal, ITrxParameter param) throws PolicyCapException {
		if (trxVal == null) {
			throw new PolicyCapException("ITrxValue is null!");
		}

		try {
			ITrxController controller = new PolicyCapGroupTrxControllerFactory().getController(trxVal, param);
			if (controller == null) {
				throw new PolicyCapException("ITrxController is null!!!");
			}
			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return (IPolicyCapGroupTrxValue) obj;

		}
		catch (TransactionException e) {
			rollback();
			throw new PolicyCapException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			rollback();
			throw new PolicyCapException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Formulate the document item trx object
	 * @param anITrxContext - ITrxContext
	 * @param trxValue - IPolicyCapTrxValue
	 * @return IPolicyCapTrxValue - the policy cap trx interface formulated
	 */
	private IPolicyCapTrxValue formulateTrxValue(ITrxContext anITrxContext, IPolicyCapTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP);
		return trxValue;
	}

	/**
	 * Formulate the document item trx object
	 * @param anITrxContext - ITrxContext
	 * @param trxValue - IPolicyCapGroupTrxValue
	 * @return IPolicyCapGroupTrxValue - the policy cap trx interface formulated
	 */
	private IPolicyCapGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, IPolicyCapGroupTrxValue trxValue) {
		trxValue.setTrxContext(anITrxContext);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_POLICY_CAP_GROUP);
		return trxValue;
	}

	/**
	 * Formulate the policy cap Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param policyCapList - IPolicyCap
	 * @return IPolicyCapTrxValue - the policy cap trx interface formulated
	 */
	private IPolicyCapTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IPolicyCap[] policyCapList) {
		IPolicyCapTrxValue policyCapTrxValue = null;
		if (anICMSTrxValue != null) {
			policyCapTrxValue = new OBPolicyCapTrxValue(anICMSTrxValue);
		}
		else {
			policyCapTrxValue = new OBPolicyCapTrxValue();
		}
		policyCapTrxValue.setStagingPolicyCap(policyCapList);
		policyCapTrxValue = formulateTrxValue(anITrxContext, policyCapTrxValue);
		return policyCapTrxValue;
	}

	/**
	 * Formulate the policy cap Trx Object
	 * @param anITrxContext - ITrxContext
	 * @param anICMSTrxValue - ICMSTrxValue
	 * @param policyCapList - IPolicyCap
	 * @return IPolicyCapGroupTrxValue - the policy cap trx interface formulated
	 */
	private IPolicyCapGroupTrxValue formulateTrxValue(ITrxContext anITrxContext, ICMSTrxValue anICMSTrxValue,
			IPolicyCapGroup policyCapGroup) {
		IPolicyCapGroupTrxValue policyCapGroupTrxValue = null;
		if (anICMSTrxValue != null) {
			policyCapGroupTrxValue = new OBPolicyCapGroupTrxValue(anICMSTrxValue);
		}
		else {
			policyCapGroupTrxValue = new OBPolicyCapGroupTrxValue();
		}
		policyCapGroupTrxValue.setStagingPolicyCapGroup(policyCapGroup);
		policyCapGroupTrxValue = formulateTrxValue(anITrxContext, policyCapGroupTrxValue);
		return policyCapGroupTrxValue;
	}

	// *********************
	// Abstract Methods
	// *********************
	protected abstract void rollback() throws PolicyCapException;

	public abstract IPolicyCap[] getPolicyCapByExchange(String exchangeCode) throws PolicyCapException;

	public abstract IPolicyCapGroup getPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity)
			throws PolicyCapException;
}
