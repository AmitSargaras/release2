package com.integrosys.cms.host.eai.customer.bus;

import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

/**
 * GEMS Customer enquiry by Name/ID
 * @author $Author: allen $<br>
 * @version $Id$
 */
public class CustomerSearchMultipleMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {
	// public final static String CUST_TRX_HANDLER =
	// "integrosys.eai.trxhandler.customer.CU002.";
	public final static String CUST_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.customer.CU002.";

	// public final static String CUST_REPUBLISH_TRX_HANDLER =
	// "integrosys.eai.republishtrxhandler.customer.CU002.";
	// public final static String CUST_REPUBLISH_ACTUAL_TRX_HANDLER =
	// "integrosys.eai.republishactualtrxhandler.customer.CU002.";
	// public final static String CUST_STAGING_TRX_HANDLER =
	// "integrosys.eai.stagingtrxhandler.customer.CU002.";
	public final static String CUST_VALIDATE_TRX_HANDLER = "integrosys.eai.validatetrxhandler.customer.CU002.";

	protected String getActualHandlerPropertyString() {
		return CUST_ACTUAL_TRX_HANDLER;
	}

	protected String getValidationHandlerPropertyString() {
		return CUST_VALIDATE_TRX_HANDLER;
	}

	protected String getStagingHandlerPropertyString() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getTrxHandlerPropertyString() {
		// TODO Auto-generated method stub
		return null;
	}

}
