package com.integrosys.cms.app.collateral.proxy.assetlife;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.collateral.trx.assetlife.ICollateralAssetLifeTrxValue;
import com.integrosys.cms.app.collateral.trx.assetlife.OBCollateralAssetLifeTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Implementation of {@lin ICollateralAssetLifeProxy} to provide interface to
 * asset life workflow engine.
 * 
 * @author Chong Jun Yong
 * 
 */
public class CollateralAssetLifeProxyImpl implements ICollateralAssetLifeProxy {

	private ITrxController collateralAssetLifeTrxController;

	private ITrxController collateralAssetLifeReadController;

	/**
	 * @return the collateralAssetLifeTrxController
	 */
	public ITrxController getCollateralAssetLifeTrxController() {
		return collateralAssetLifeTrxController;
	}

	/**
	 * @return the collateralAssetLifeReadController
	 */
	public ITrxController getCollateralAssetLifeReadController() {
		return collateralAssetLifeReadController;
	}

	/**
	 * @param collateralAssetLifeTrxController the
	 *        collateralAssetLifeTrxController to set
	 */
	public void setCollateralAssetLifeTrxController(ITrxController collateralAssetLifeTrxController) {
		this.collateralAssetLifeTrxController = collateralAssetLifeTrxController;
	}

	/**
	 * @param collateralAssetLifeReadController the
	 *        collateralAssetLifeReadController to set
	 */
	public void setCollateralAssetLifeReadController(ITrxController collateralAssetLifeReadController) {
		this.collateralAssetLifeReadController = collateralAssetLifeReadController;
	}

	/**
	 * Gets the collateral assetlife trx value.
	 * 
	 * @param ctx transaction context
	 * @return collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue getCollateralAssetLifeTrxValue(ITrxContext ctx) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_ASSETLIFE);
		ICollateralAssetLifeTrxValue trxValue = new OBCollateralAssetLifeTrxValue();

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeReadController(), param, trxValue);
	}

	/**
	 * Gets the collateral assetlife trx value by transaction id.
	 * 
	 * @param ctx transaction context
	 * @param trxID transaction id
	 * @return collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue getCollateralAssetLifeTrxValueByTrxID(ITrxContext ctx, String trxID)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_ASSETLIFE_BY_TRXID);
		ICollateralAssetLifeTrxValue trxValue = new OBCollateralAssetLifeTrxValue();
		trxValue.setTransactionID(trxID);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeReadController(), param, trxValue);
	}

	/**
	 * Maker updates a list of collateral assetlifes.
	 * 
	 * @param ctx transaction context
	 * @param assetlifes a list of collateral assetlife objects to use for
	 *        updating.
	 * @return updated collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxValue, ICollateralAssetLife[] assetlifes) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_ASSETLIFE);
		trxValue.setStagingCollateralAssetLifes(assetlifes);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeTrxController(), param, trxValue);
	}

	/**
	 * Maker saves a list of collateral assetlifes.
	 * 
	 * @param ctx transaction context
	 * @param assetlifes a list of collateral assetlife objects to use for
	 *        updating.
	 * @return updated collateral assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerSaveCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxValue, ICollateralAssetLife[] assetlifes) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_SAVE_ASSETLIFE);
		trxValue.setStagingCollateralAssetLifes(assetlifes);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeTrxController(), param, trxValue);
	}

	/**
	 * Maker cancel security assetlife updated by him/her, or rejected by a
	 * checker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security assetlife transaction value
	 * @return the updated security assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue makerCancelUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CANCEL_ASSETLIFE);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeTrxController(), param, trxValue);
	}

	/**
	 * Checker approve security assetlife updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security assetlife transaction value
	 * @return updated transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue checkerApproveUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_ASSETLIFE);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeTrxController(), param, trxValue);
	}

	/**
	 * Checker reject security assetlife updated by a maker.
	 * 
	 * @param ctx transaction context
	 * @param trxValue security assetlife transaction value
	 * @return updated security assetlife transaction value
	 * @throws CollateralException on errors encountered
	 */
	public ICollateralAssetLifeTrxValue checkerRejectUpdateCollateralAssetLife(ITrxContext ctx,
			ICollateralAssetLifeTrxValue trxValue) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_ASSETLIFE);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralAssetLifeTrxController(), param, trxValue);
	}

	protected ICollateralAssetLifeTrxValue constructTrxValue(ITrxContext ctx, ICollateralAssetLifeTrxValue trxValue) {
		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected ICollateralAssetLifeTrxValue doInTrxController(ITrxController trxController, ITrxParameter param,
			ICollateralAssetLifeTrxValue trxValue) throws CollateralException {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		try {
			ICMSTrxResult result = (ICMSTrxResult) trxController.operate(trxValue, param);
			return (ICollateralAssetLifeTrxValue) result.getTrxValue();
		}
		catch (TransactionException ex) {
			throw new CollateralException("failed to operate collateral asset life workflow", ex);
		}
	}
}
