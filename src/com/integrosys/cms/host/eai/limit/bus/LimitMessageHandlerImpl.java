package com.integrosys.cms.host.eai.limit.bus;

import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class LimitMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {
	public final static String CUST_TRX_HANDLER = "integrosys.eai.trxhandler.collateral.CI002.";

	public final static String CUST_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.collateral.CI002.";

	public final static String CUST_REPUBLISH_TRX_HANDLER = "integrosys.eai.republishtrxhandler.collateral.CI002.";

	public final static String CUST_REPUBLISH_ACTUAL_TRX_HANDLER = "integrosys.eai.republishactualtrxhandler.collateral.CI002.";

	public final static String CUST_STAGING_TRX_HANDLER = "integrosys.eai.stagingtrxhandler.collateral.CI002.";

	public final static String CUST_VALIDATE_TRX_HANDLER = "integrosys.eai.validatetrxhandler.collateral.CI002.";

	protected String getTrxHandlerPropertyString() {
		return CUST_TRX_HANDLER;
	}

	protected String getActualHandlerPropertyString() {
		return CUST_ACTUAL_TRX_HANDLER;
	}

	protected String getStagingHandlerPropertyString() {
		return CUST_STAGING_TRX_HANDLER;
	}

	protected String getValidationHandlerPropertyString() {
		return CUST_VALIDATE_TRX_HANDLER;
	}

	protected String getRepublishActualHandlerPropertyString() {
		return CUST_REPUBLISH_ACTUAL_TRX_HANDLER;
	}

	protected String getRepublishTrxHandlerPropertyString() {
		return CUST_REPUBLISH_TRX_HANDLER;
	}
}
