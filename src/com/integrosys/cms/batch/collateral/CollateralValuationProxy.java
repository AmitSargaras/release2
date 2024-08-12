package com.integrosys.cms.batch.collateral;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.bus.OBValuation;
import com.integrosys.cms.app.collateral.proxy.valuation.ICollateralValuationProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.valuation.IValuationTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.batch.collateral.dao.CollateralValuationDAOFactory;
import com.integrosys.cms.batch.collateral.dao.ICollateralValuationConstants;
import com.integrosys.cms.batch.collateral.dao.ICollateralValuationDAO;
import com.integrosys.cms.batch.common.BatchJobTrxUtil;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.CollateralValuationProxy.java
 */
public class CollateralValuationProxy implements ICollateralValuationConstants {

	private ICollateralValuationProxy collateralValuationProxy;

	private static CollateralValuationProxy instance;

	public void setCollateralValuationProxy(ICollateralValuationProxy collateralValuationProxy) {
		this.collateralValuationProxy = collateralValuationProxy;
	}

	public ICollateralValuationProxy getCollateralValuationProxy() {
		return collateralValuationProxy;
	}

	private CollateralValuationProxy() {
	}

	public static CollateralValuationProxy getInstance() {
		if (instance == null) {
			instance = new CollateralValuationProxy();
		}
		return instance;
	}

	public void valuatePropertyCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, PROPERTY_COLLATERAL);
	}

	public void valuateAssetCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, ASSET_COLLATERAL);
	}

	public void valuateGeneralChargeCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, GENERALCHARGE_COLLATERAL);
	}

	public void valuatePDCCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, PDC_COLLATERAL);
	}

	public void valuateCashCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, CASH_COLLATERAL);
	}

	public void valuateInsuranceCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, INSURANCE_COLLATERAL);
	}

	public void valuateGuaranteeCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, GUARANTEE_COLLATERAL);
	}

	public void valuateDocumentCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, DOCUMENT_COLLATERAL);
	}

	public void valuateCommodityCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, COMMODITY_COLLATERAL);
	}

	public void valuateMarketableCollateral(String countryCode, SessionContext context) throws Exception {
		valuateCollateral(countryCode, context, MARKETABLE_COLLATERAL);
	}

	private void valuateCollateral(String countryCode, SessionContext context, int valuationType) throws Exception {
		DefaultLogger.debug(this, " - Valuation Type : " + valuationType);
		BatchJobTrxUtil trxUtil = new BatchJobTrxUtil(context);
		try {
			trxUtil.beginUserTrx();
			ICollateralValuationDAO dao = CollateralValuationDAOFactory.getDAO(valuationType);
			long start = System.currentTimeMillis();
			ICollateralTrxValue[] trxValueArray = dao.getCollateralTrxValues(countryCode);
			trxUtil.commitUserTrx();

			DefaultLogger.debug(this, "Total Search - Time: " + (System.currentTimeMillis() - start));
			DefaultLogger.debug(this, " - To revaluate : " + trxValueArray.length);
			start = System.currentTimeMillis();
			for (int index = 0; index < trxValueArray.length; index++) {
				try {
					trxUtil.beginUserTrx();
					dao.updateCollateralValuation(trxValueArray[index]);
					revaluateCollateral(trxValueArray[index]);
					trxUtil.commitUserTrx();
				}
				catch (Exception e) {
					e.printStackTrace();
					DefaultLogger.error(this, "Valuation Error !", e);
					trxUtil.rollbackUserTrx();
				}
			}
			DefaultLogger.debug(this, "Total Update - Time : " + (System.currentTimeMillis() - start));
		}
		catch (Exception e) {
			e.printStackTrace();
			trxUtil.rollbackUserTrx();
			throw e;
		}
	}

	private boolean revaluateCollateral(ICollateralTrxValue newTrxVal) throws Exception {
		ICollateral newColl = newTrxVal.getCollateral();
		long refID = Long.parseLong(newTrxVal.getReferenceID());

		IValuationTrxValue valuationTrx = getCollateralValuationProxy().getValuationTrxValueByTrxRefID(
				new OBTrxContext(), newTrxVal.getTransactionID());
		IValuation valuation = valuationTrx.getValuation();
		boolean isCreate = false;
		if (valuation == null) {
			isCreate = true;
			valuation = new OBValuation();
		}
		AccessorUtil.copyValue(newColl.getValuation(), valuation);

		valuation.setCollateralID(refID);
		if (isCreate) {
			valuationTrx.setValuation(valuation);
			getCollateralValuationProxy().systemCreateRevaluation(new OBTrxContext(), valuationTrx);
		}
		else {
			getCollateralValuationProxy().systemUpdateRevaluation(new OBTrxContext(), valuationTrx, valuation);
		}
		return true;
	}
}
