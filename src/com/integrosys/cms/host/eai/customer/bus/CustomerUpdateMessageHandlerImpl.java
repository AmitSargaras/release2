package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

public class CustomerUpdateMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {
	public final static String CUST_TRX_HANDLER = "integrosys.eai.trxhandler.customer.CU003.";

	public final static String CUST_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.customer.CU003.";

	public final static String CUST_REPUBLISH_TRX_HANDLER = "integrosys.eai.republishtrxhandler.customer.CU003.";

	public final static String CUST_REPUBLISH_ACTUAL_TRX_HANDLER = "integrosys.eai.republishactualtrxhandler.customer.CU003.";

	public final static String CUST_STAGING_TRX_HANDLER = "integrosys.eai.stagingtrxhandler.customer.CU003.";

	public final static String CUST_VALIDATE_TRX_HANDLER = "integrosys.eai.validatetrxhandler.customer.CU003.";

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
