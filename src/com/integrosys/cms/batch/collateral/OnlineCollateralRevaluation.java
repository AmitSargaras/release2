/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/collateral/OnlineCollateralRevaluation.java,v 1.31 2006/06/15 09:18:21 hmbao Exp $
 */
package com.integrosys.cms.batch.collateral;

import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;

/**
 * A batch program to revaluate those collaterals with online revaluation.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.31 $
 * @since $Date: 2006/06/15 09:18:21 $ Tag: $Name: $
 */
public class OnlineCollateralRevaluation extends AbstractMonitorAdapter {

	public void start(String countryCode, SessionContext context) throws EventMonitorException {
		DefaultLogger.debug(this, "- Start OnlineCollateralRevaluation Job -");
		try {
			CollateralValuationProxy proxy = CollateralValuationProxy.getInstance();
			proxy.valuatePropertyCollateral(countryCode, context);
			proxy.valuateAssetCollateral(countryCode, context);
			proxy.valuateGeneralChargeCollateral(countryCode, context);
		}
		catch (Exception e) {
			throw new EventMonitorException(e);
		}
	}
}
