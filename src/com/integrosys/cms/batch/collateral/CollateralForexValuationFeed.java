/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/collateral/CollateralForexValuationFeed.java,v 1.43 2006/06/15 09:18:21 hmbao Exp $
 */
package com.integrosys.cms.batch.collateral;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;

/**
 * A batch program to get forex value for collateral valuation.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.43 $
 * @since $Date: 2006/06/15 09:18:21 $ Tag: $Name: $
 */
public class CollateralForexValuationFeed extends AbstractMonitorAdapter {

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start CollateralForexValuationFeed Job -");
		try {
			CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
			proxy.valuatePDCCollateral(countryCode, context);
			proxy.valuateCashCollateral(countryCode, context);
			proxy.valuateInsuranceCollateral(countryCode, context);
			proxy.valuateGuaranteeCollateral(countryCode, context);
			proxy.valuateDocumentCollateral(countryCode, context);
		}
		catch (Exception e) {
			throw new EventMonitorException(e);
		}
	}
}
