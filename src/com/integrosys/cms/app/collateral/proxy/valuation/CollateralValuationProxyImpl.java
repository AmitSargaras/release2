package com.integrosys.cms.app.collateral.proxy.valuation;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.trx.valuation.IValuationTrxValue;
import com.integrosys.cms.app.collateral.trx.valuation.OBValuationTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

public class CollateralValuationProxyImpl implements ICollateralValuationProxy {

	public ITrxController collateralValuationTrxController;

	public ITrxController collateralValuationReadController;

	/**
	 * @return the collateralValuationTrxController
	 */
	public ITrxController getCollateralValuationTrxController() {
		return collateralValuationTrxController;
	}

	/**
	 * @param collateralValuationTrxController the
	 *        collateralValuationTrxController to set
	 */
	public void setCollateralValuationTrxController(ITrxController collateralValuationTrxController) {
		this.collateralValuationTrxController = collateralValuationTrxController;
	}

	/**
	 * @return the collateralValuationReadController
	 */
	public ITrxController getCollateralValuationReadController() {
		return collateralValuationReadController;
	}

	/**
	 * @param collateralValuationReadController the
	 *        collateralValuationReadController to set
	 */
	public void setCollateralValuationReadController(ITrxController collateralValuationReadController) {
		this.collateralValuationReadController = collateralValuationReadController;
	}

	/**
	 * Get the valuation transaction value given its valuation id.
	 * 
	 * @param ctx transaction context
	 * @param valuationID valuation id
	 * @return valuation trasaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue getValuationTrxValue(ITrxContext ctx, long valuationID) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_VAL_BY_VALID);
		IValuationTrxValue trxValue = new OBValuationTrxValue();
		trxValue.setReferenceID(String.valueOf(valuationID));

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationReadController(), param, trxValue);
	}

	/**
	 * Get valuation transaction value given its transaction reference id.
	 * 
	 * @param ctx transaction context
	 * @param parentTrxID transaction ref id of valuation, i.e collateral trx id
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue getValuationTrxValueByTrxRefID(ITrxContext ctx, String parentTrxID)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_VAL_BY_TRXREFID);
		IValuationTrxValue trxValue = new OBValuationTrxValue();
		trxValue.setTrxReferenceID(parentTrxID);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationReadController(), param, trxValue);
	}

	/**
	 * Revaluate a collateral manually by using feeds for price, eg. stocks
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualFeedsCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANUAL_FEEDS_CREATE_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Revaluate a collateral manually by using feeds for price, eg. stocks
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualFeedsUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue, IValuation val)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANUAL_FEEDS_UPDATE_REVAL);
		trxValue.setValuation(val);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Revaluate a collateral manually. It requires a valuer to provide
	 * valuation, e.g. property.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANUAL_CREATE_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Revaluate a collateral manually. The update will create new valuation,
	 * the existing valuation values will become valuation history.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue, IValuation val)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MANUAL_UPDATE_REVAL);
		trxValue.setStagingValuation(val);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Resubmit the revaluation rejected by a checker.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualResubmitRevaluation(ITrxContext ctx, IValuationTrxValue trxValue, IValuation val)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxValue.getFromState().equals(ICMSConstant.STATE_PENDING_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MANUAL_RESUBMIT_CREATE_REVAL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MANUAL_RESUBMIT_UPDATE_REVAL);
		}
		trxValue.setStagingValuation(val);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Cancel the revaluation rejected by a checker.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue of type IValuationTrxValue
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue manualCancelRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		if (trxValue.getFromState().equals(ICMSConstant.STATE_PENDING_CREATE)) {
			param.setAction(ICMSConstant.ACTION_MANUAL_CANCEL_CREATE_REVAL);
		}
		else {
			param.setAction(ICMSConstant.ACTION_MANUAL_CANCEL_UPDATE_REVAL);
		}

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Checker approve collateral revaluated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue checkerApproveRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * Checker rejects collateral revaluated by maker.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue checkerRejectRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * System Close Revaluation as the valuable entity has been removed.
	 * 
	 * @param ctx transaction context object
	 * @param trxValue valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemCloseRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CLOSE_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * System creates revaluation, e.g. stocks
	 * 
	 * @param ctx transaction context
	 * @param trxValue valuation transaction value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemCreateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_CREATE_REVAL);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	/**
	 * System updates revaluation e.g. stocks
	 * 
	 * @param ctx transaction context
	 * @param trxValue valuation transaction value
	 * @param val new valuation value
	 * @return valuation transaction value
	 * @throws CollateralException on errors encountered
	 */
	public IValuationTrxValue systemUpdateRevaluation(ITrxContext ctx, IValuationTrxValue trxValue, IValuation val)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_SYSTEM_UPDATE_REVAL);
		trxValue.setValuation(val);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralValuationTrxController(), param, trxValue);
	}

	protected IValuationTrxValue constructTrxValue(ITrxContext ctx, IValuationTrxValue trxValue) {
		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected IValuationTrxValue doInTrxController(ITrxController trxController, ITrxParameter param,
			IValuationTrxValue trxValue) throws CollateralException {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		try {
			ICMSTrxResult result = (ICMSTrxResult) trxController.operate(trxValue, param);

			return (IValuationTrxValue) result.getTrxValue();
		}
		catch (Throwable t) {
			throw new CollateralException("failed to operate collateral valuation workflow.", t);
		}

	}

}
