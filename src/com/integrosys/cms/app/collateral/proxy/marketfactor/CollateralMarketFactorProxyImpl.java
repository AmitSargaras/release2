package com.integrosys.cms.app.collateral.proxy.marketfactor;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.ITrxController;
import com.integrosys.base.businfra.transaction.ITrxParameter;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.marketfactor.IMFChecklistTrxValue;
import com.integrosys.cms.app.collateral.trx.marketfactor.OBMFChecklistTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxResult;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.transaction.OBCMSTrxParameter;

/**
 * Implementation of {@link ICollateralMarketFactorProxy}. Trx Controllers and
 * Collateral Business Manager are required.
 * 
 * TODO: dependency on CollateralProxyFactory to be removed
 * 
 * @author Chong Jun Yong
 * 
 */
public class CollateralMarketFactorProxyImpl implements ICollateralMarketFactorProxy {

	private ITrxController collateralMarketFactorTrxController;

	private ITrxController collateralMarketFactorReadController;

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralProxy collateralProxy;

	/**
	 * @return the collateralMarketFactorTrxController
	 */
	public ITrxController getCollateralMarketFactorTrxController() {
		return collateralMarketFactorTrxController;
	}

	/**
	 * @return the collateralMarketFactorReadController
	 */
	public ITrxController getCollateralMarketFactorReadController() {
		return collateralMarketFactorReadController;
	}

	/**
	 * @param collateralMarketFactorTrxController the
	 *        collateralMarketFactorTrxController to set
	 */
	public void setCollateralMarketFactorTrxController(ITrxController collateralMarketFactorTrxController) {
		this.collateralMarketFactorTrxController = collateralMarketFactorTrxController;
	}

	/**
	 * @param collateralMarketFactorReadController the
	 *        collateralMarketFactorReadController to set
	 */
	public void setCollateralMarketFactorReadController(ITrxController collateralMarketFactorReadController) {
		this.collateralMarketFactorReadController = collateralMarketFactorReadController;
	}

	public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
		this.actualCollateralBusManager = actualCollateralBusManager;
	}

	public ICollateralBusManager getActualCollateralBusManager() {
		return actualCollateralBusManager;
	}

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	public IMFChecklist getMFChecklist(long mFChecklistID) throws CollateralException {
		return getActualCollateralBusManager().getMFChecklist(mFChecklistID);

	}

	/**
	 * @see com.integrosys.cms.app.collateral.proxy.ICollateralProxy#getMFChecklistTrxValue
	 */
	public IMFChecklistTrxValue getMFChecklistTrxValue(ITrxContext ctx, long collateralID) throws CollateralException {
		ICollateralTrxValue colTrxVal = getCollateralProxy().getCollateralTrxValue(ctx, collateralID);
		if (colTrxVal == null) {
			return null;
		}

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MF_CHECKLIST);
		IMFChecklistTrxValue trxValue = new OBMFChecklistTrxValue();
		trxValue.setTrxReferenceID(String.valueOf(colTrxVal.getTransactionID()));

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorReadController(), param, trxValue);
	}

	public IMFChecklistTrxValue getMFChecklistTrxValueByTrxID(ITrxContext ctx, String trxID) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_READ_MF_CHECKLIST_BY_TRXID);
		IMFChecklistTrxValue trxValue = new OBMFChecklistTrxValue();
		trxValue.setTransactionID(trxID);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorReadController(), param, trxValue);
	}

	public IMFChecklistTrxValue makerCreateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue,
			IMFChecklist value) throws CollateralException {
		Validate.notNull(value, "Market Factor Checklist must not be null.");
		ICollateralTrxValue colTrxVal = getCollateralProxy().getCollateralTrxValue(ctx, value.getCollateralID());

		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CREATE_MF_CHECKLIST);
		if (trxValue == null) {
			trxValue = new OBMFChecklistTrxValue();
		}
		trxValue.setTrxReferenceID(String.valueOf(colTrxVal.getTransactionID()));
		trxValue.setStagingMFChecklist(value);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	public IMFChecklistTrxValue makerUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue,
			IMFChecklist value) throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_UPDATE_MF_CHECKLIST);
		trxValue.setStagingMFChecklist(value);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	public IMFChecklistTrxValue makerCloseCreateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_MF_CHECKLIST);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	public IMFChecklistTrxValue makerCloseUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_MAKER_CLOSE_UPDATE_MF_CHECKLIST);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	public IMFChecklistTrxValue checkerApproveUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_APPROVE_MF_CHECKLIST);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	public IMFChecklistTrxValue checkerRejectUpdateMFChecklist(ITrxContext ctx, IMFChecklistTrxValue trxValue)
			throws CollateralException {
		OBCMSTrxParameter param = new OBCMSTrxParameter();
		param.setAction(ICMSConstant.ACTION_CHECKER_REJECT_MF_CHECKLIST);

		trxValue = constructTrxValue(ctx, trxValue);

		return doInTrxController(getCollateralMarketFactorTrxController(), param, trxValue);
	}

	protected IMFChecklistTrxValue constructTrxValue(ITrxContext ctx, IMFChecklistTrxValue trxValue)
			throws CollateralException {
		if ((ctx != null) && (ctx.getCollateralID() != ICMSConstant.LONG_INVALID_VALUE)) {
			ICollateral col = getCollateralProxy().getCollateral(ctx.getCollateralID(), false);
			ctx.setCustomer(null);
			ctx.setLimitProfile(null);
			ctx.setTrxCountryOrigin(col.getCollateralLocation());
			trxValue.setLegalName(String.valueOf(col.getSCISecurityID()));
			trxValue.setCustomerName(col.getCollateralType().getTypeName() + " - "
					+ col.getCollateralSubType().getSubTypeName());
			trxValue.setLegalID(null);
			//trxValue.setCustomerID(ICMSConstant.LONG_INVALID_VALUE);
			//trxValue.setLimitProfileID(ICMSConstant.LONG_INVALID_VALUE);

		}

		trxValue.setTrxContext(ctx);

		return trxValue;
	}

	protected IMFChecklistTrxValue doInTrxController(ITrxController trxController, ITrxParameter param,
			IMFChecklistTrxValue trxValue) throws CollateralException {
		Validate.notNull(trxController, "'trxController' to do operation must not be null");
		Validate.notNull(param, "'param' to undergo operation must not be null");
		Validate.notNull(trxValue, "'trxValue' to undergo operation must not be null");

		ICMSTrxResult result = null;
		try {
			result = (ICMSTrxResult) trxController.operate(trxValue, param);
		}
		catch (Throwable t) {
			throw new CollateralException("failed to operate collateral workflow", t);
		}

		return (IMFChecklistTrxValue) result.getTrxValue();
	}

}
