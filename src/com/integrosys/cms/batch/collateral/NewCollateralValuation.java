package com.integrosys.cms.batch.collateral;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-5-31
 * @Tag com.integrosys.cms.batch.collateral.NewCollateralValuation.java
 */
public class NewCollateralValuation extends AbstractMonitorAdapter {

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start Job -");
		try {
			doOnlineWork(countryCode, context);
			doForexWork(countryCode, context);
			doCommodityWork(countryCode, context);
			doMarketableWork(countryCode, context);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new EventMonitorException(e);
		}
	}

	private void doOnlineWork(String countryCode, SessionContext context) throws Exception {
		CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
		proxy.valuatePropertyCollateral(countryCode, context);
		proxy.valuateAssetCollateral(countryCode, context);
		proxy.valuateGeneralChargeCollateral(countryCode, context);
	}

	private void doForexWork(String countryCode, SessionContext context) throws Exception {
		CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
		proxy.valuatePDCCollateral(countryCode, context);
		proxy.valuateCashCollateral(countryCode, context);
		proxy.valuateInsuranceCollateral(countryCode, context);
		proxy.valuateGuaranteeCollateral(countryCode, context);
		proxy.valuateDocumentCollateral(countryCode, context);
	}

	private void doCommodityWork(String countryCode, SessionContext context) throws Exception {
		CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
		proxy.valuateCommodityCollateral(countryCode, context);
	}

	private void doMarketableWork(String countryCode, SessionContext context) throws Exception {
		CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
		proxy.valuateMarketableCollateral(countryCode, context);
	}
}
