/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.proxy;

import java.util.Date;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.interestrate.bus.IInterestRate;
import com.integrosys.cms.app.interestrate.bus.InterestRateException;
import com.integrosys.cms.app.interestrate.trx.IInterestRateTrxValue;
import com.integrosys.cms.app.interestrate.trx.InterestRateTrxControllerFactory;
import com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxParameter;
import com.integrosys.cms.app.interestrate.trx.OBInterestRateTrxValue;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * This class implements the services that are available in CMS with respect to
 * the interestrate life cycle.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractInterestRateProxy implements IInterestRateProxy {
	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @param intRates a list of interest rate objects
	 * @return transaction value
	 */
	private IInterestRateTrxValue constructTrxValue(ITrxContext ctx, ICMSTrxValue trxValue, IInterestRate[] intRates) {
		IInterestRateTrxValue intRateTrxValue = null;
		if (trxValue != null) {
			intRateTrxValue = new OBInterestRateTrxValue(trxValue);
		}
		else {
			intRateTrxValue = new OBInterestRateTrxValue();
		}
		intRateTrxValue.setStagingInterestRates(intRates);
		intRateTrxValue = constructTrxValue(ctx, intRateTrxValue);
		return intRateTrxValue;
	}

	/**
	 * Helper method to contruct transaction value.
	 * 
	 * @param ctx of type ITrxContext
	 * @param trxValue of type ITrxValue
	 * @return transaction value
	 */
	private IInterestRateTrxValue constructTrxValue(ITrxContext ctx, IInterestRateTrxValue trxValue) {
		trxValue.setTrxContext(ctx);
		trxValue.setTransactionType(ICMSConstant.INSTANCE_INT_RATE);
		return trxValue;
	}

	/**
	 * Helper method to operate transactions.
	 * 
	 * @param trxVal is of type ITrxValue
	 * @param param is of type ITrxParameter
	 * @throws InterestRateException on errors encountered
	 */
	private ITrxValue operate(ITrxValue trxVal, ITrxParameter param) throws InterestRateException {
		if (trxVal == null) {
			throw new InterestRateException("IInterestRateTrxValue is null!");
		}

		try {
			ITrxController controller = null;

			if (trxVal instanceof IInterestRateTrxValue) {
				controller = (new InterestRateTrxControllerFactory()).getController(trxVal, param);
			}
			else {
				controller = (new InterestRateTrxControllerFactory()).getController(trxVal, param);
			}

			if (controller == null) {
				throw new InterestRateException("ITrxController is null!");
			}

			ITrxResult result = controller.operate(trxVal, param);
			ITrxValue obj = result.getTrxValue();
			return obj;
		}
		catch (InterestRateException e) {
			e.printStackTrace();
			rollback();
			throw e;
		}
		catch (TransactionException e) {
			e.printStackTrace();
			rollback();
			throw new InterestRateException("TransactionException caught! " + e.toString(), e);
		}
		catch (Exception e) {
			e.printStackTrace();
			rollback();
			throw new InterestRateException("Exception caught! " + e.toString(), e);
		}
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws InterestRateException on errors encountered
	 */
	protected abstract void rollback() throws InterestRateException;

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#getInterestRateTrxValue
	 */
	public IInterestRateTrxValue getInterestRateTrxValue(ITrxContext ctx, String intRateType, Date monthYear)
			throws InterestRateException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_INT_RATE);
		OBInterestRateTrxValue trxValue = new OBInterestRateTrxValue();
		trxValue.setIntRateType(intRateType);
		trxValue.setMonthYear(monthYear);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#getInterestRateTrxValueByTrxID
	 */
	public IInterestRateTrxValue getInterestRateTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws InterestRateException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_INT_RATE_BY_TRXID);
		OBInterestRateTrxValue trxValue = new OBInterestRateTrxValue();
		trxValue.setTransactionID(trxID);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxValue), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerUpdateInterestRate
	 */
	public IInterestRateTrxValue makerUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] intRates) throws InterestRateException {
		OBInterestRateTrxParameter param = new OBInterestRateTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_INT_RATE);
		trxVal.setStagingInterestRates(intRates);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxVal, intRates), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerSaveInterestRate
	 */
	public IInterestRateTrxValue makerSaveInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal,
			IInterestRate[] intRates) throws InterestRateException {
		OBInterestRateTrxParameter param = new OBInterestRateTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_INT_RATE);
		trxVal.setStagingInterestRates(intRates);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#makerCancelUpdateInterestRate
	 */
	public IInterestRateTrxValue makerCancelUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		OBInterestRateTrxParameter param = new OBInterestRateTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_INT_RATE);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#checkerApproveUpdateInterestRate
	 */
	public IInterestRateTrxValue checkerApproveUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		OBInterestRateTrxParameter param = new OBInterestRateTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_INT_RATE);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

	/**
	 * @see com.integrosys.cms.app.interestrate.proxy.IInterestRateProxy#checkerRejectUpdateInterestRate
	 */
	public IInterestRateTrxValue checkerRejectUpdateInterestRate(ITrxContext ctx, IInterestRateTrxValue trxVal)
			throws InterestRateException {
		OBInterestRateTrxParameter param = new OBInterestRateTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_INT_RATE);
		return (IInterestRateTrxValue) operate(constructTrxValue(ctx, trxVal), param);
	}

}