package com.integrosys.cms.app.collateral.proxy.parameter;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.ICollateralSubTypeTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralParameterTrxValue;
import com.integrosys.cms.app.collateral.trx.parameter.OBCollateralSubTypeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Implementation of {@link ICollateralParameterProxy}. Required Transaction
 * Controller to be injected to handle workflow related transaction.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CollateralParameterProxyImpl implements ICollateralParameterProxy {

	private ITrxController collateralParamReadController;

	private ITrxController collateralParamTrxController;

	private ITrxController collateralSubTypeTrxController;

	private ITrxController collateralSubTypeReadController;

	/**
	 * @return the collateralParamReadController
	 */
	public ITrxController getCollateralParamReadController() {
		return collateralParamReadController;
	}

	/**
	 * @return the collateralParamTrxController
	 */
	public ITrxController getCollateralParamTrxController() {
		return collateralParamTrxController;
	}

	/**
	 * @return the collateralSubTypeTrxController
	 */
	public ITrxController getCollateralSubTypeTrxController() {
		return collateralSubTypeTrxController;
	}

	/**
	 * @return the collateralSubTypeReadController
	 */
	public ITrxController getCollateralSubTypeReadController() {
		return collateralSubTypeReadController;
	}

	/**
	 * @param collateralParamReadController the collateralParamReadController to
	 *        set
	 */
	public void setCollateralParamReadController(ITrxController collateralParamReadController) {
		this.collateralParamReadController = collateralParamReadController;
	}

	/**
	 * @param collateralParamTrxController the collateralParamTrxController to
	 *        set
	 */
	public void setCollateralParamTrxController(ITrxController collateralParamTrxController) {
		this.collateralParamTrxController = collateralParamTrxController;
	}

	/**
	 * @param collateralSubTypeTrxController the collateralSubTypeTrxController
	 *        to set
	 */
	public void setCollateralSubTypeTrxController(ITrxController collateralSubTypeTrxController) {
		this.collateralSubTypeTrxController = collateralSubTypeTrxController;
	}

	/**
	 * @param collateralSubTypeReadController the
	 *        collateralSubTypeReadController to set
	 */
	public void setCollateralSubTypeReadController(ITrxController collateralSubTypeReadController) {
		this.collateralSubTypeReadController = collateralSubTypeReadController;
	}

	/**
	 * Maker updates a list of collateral parameters.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @param colParams a list of security parameters
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue, ICollateralParameter[] colParams) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM);
		trxValue.setStagingCollateralParameters(colParams);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamTrxController(), param, trxValue);
	}

	/**
	 * Maker saves a list of collateral parameters.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @param colParams a list of security parameters
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerSaveCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue, ICollateralParameter[] colParams) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_COLPARAM);
		trxValue.setStagingCollateralParameters(colParams);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamTrxController(), param, trxValue);
	}

	/**
	 * Maker cancel security parameter updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @return the updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue makerCancelUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_COLPARAM);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamTrxController(), param, trxValue);
	}

	/**
	 * Checker approve security parameter updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue checkerApproveUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_COLPARAM);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamTrxController(), param, trxValue);
	}

	/**
	 * Checker reject security parameter updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security parameter transaction value
	 * @return updated security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue checkerRejectUpdateCollateralParameter(ITrxContext ctx,
			ICollateralParameterTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_COLPARAM);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamTrxController(), param, trxValue);
	}

	/**
	 * Gets the collateral parameter trx value by country code and security type
	 * code.
	 * 
	 * @param ctx transaction context
	 * @param countryCode country code
	 * @param typeCode security type code
	 * @return security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue getCollateralParameterTrxValue(ITrxContext ctx, String countryCode,
			String typeCode) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COLPARAM_BY_COUNTRY_COLTYPE);
		ICollateralParameterTrxValue trxValue = new OBCollateralParameterTrxValue();
		trxValue.setCollateralTypeCode(typeCode);
		trxValue.setCountryCode(countryCode);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamReadController(), param, trxValue);
	}

	/**
	 * Get the security parameter trx value by its transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return security parameter transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralParameterTrxValue getCollateralParameterTrxValue(ITrxContext ctx, String trxID)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_COLPARAM_BY_TRXID);
		ICollateralParameterTrxValue trxValue = new OBCollateralParameterTrxValue();
		trxValue.setTransactionID(trxID);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralParameterTrxValue) doInTrxController(getCollateralParamReadController(), param, trxValue);
	}

	/**
	 * Gets the collateral subtype trx value by collateral type code.
	 * 
	 * @param ctx transaction context
	 * @param colTypeCode collateral type code
	 * @return collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue getCollateralSubTypeTrxValue(ITrxContext ctx, String colTypeCode)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_SUBTYPE_BY_TYPECODE);
		ICollateralSubTypeTrxValue trxValue = new OBCollateralSubTypeTrxValue();
		trxValue.setCollateralTypeCode(colTypeCode);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeReadController(), param, trxValue);
	}

	/**
	 * Gets the collateral subtype trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue getCollateralSubTypeTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_SUBTYPE_BY_TRXID);
		ICollateralSubTypeTrxValue trxValue = new OBCollateralSubTypeTrxValue();
		trxValue.setTransactionID(trxID);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeReadController(), param, trxValue);
	}

	/**
	 * Maker updates a list of collateral subtypes.
	 * 
	 * @param ctx transaction context
	 * @param subtypes a list of collateral subtype objects to use for updating.
	 * @return updated collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerUpdateCollateralSubType(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxValue, ICollateralSubType[] subtypes) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_SUBTYPE);
		trxValue.setStagingCollateralSubTypes(subtypes);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeTrxController(), param, trxValue);
	}

	/**
	 * Maker saves a list of collateral subtypes.
	 * 
	 * @param ctx transaction context
	 * @param subtypes a list of collateral subtype objects to use for updating.
	 * @return updated collateral subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerSaveCollateralSubType(ITrxContext ctx, ICollateralSubTypeTrxValue trxValue,
			ICollateralSubType[] subtypes) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_SUBTYPE);
		trxValue.setStagingCollateralSubTypes(subtypes);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeTrxController(), param, trxValue);
	}

	/**
	 * Maker cancel security subtypes updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security subtype transaction value
	 * @return the updated security subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue makerCancelUpdateCollateralSubType(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_SUBTYPE);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeTrxController(), param, trxValue);
	}

	/**
	 * Checker approve security subtypes updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security subtype transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue checkerApproveUpdateCollateralSubType(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_SUBTYPE);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeTrxController(), param, trxValue);
	}

	/**
	 * Checker reject security subtypes updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security subtype transaction value
	 * @return updated security subtype transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralSubTypeTrxValue checkerRejectUpdateCollateralSubTypes(ITrxContext ctx,
			ICollateralSubTypeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_SUBTYPE);

		trxValue = constructTrxValue(ctx, trxValue);

		return (ICollateralSubTypeTrxValue) doInTrxController(getCollateralSubTypeTrxController(), param, trxValue);
	}

	protected ICollateralSubTypeTrxValue constructTrxValue(ITrxContext ctx, ICollateralSubTypeTrxValue trxValue) {
		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected ICollateralParameterTrxValue constructTrxValue(ITrxContext ctx, ICollateralParameterTrxValue trxValue) {
		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected ICMSTrxValue doInTrxController(ITrxController trxController, ITrxParameter param, ICMSTrxValue trxValue)
			throws CollateralException {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		try {
			ICMSTrxResult result = (ICMSTrxResult) trxController.operate(trxValue, param);
			return (ICMSTrxValue) result.getTrxValue();
		}
		catch (Throwable t) {
			throw new CollateralException("failed to operate collateral parameter workflow", t);
		}

	}
}
