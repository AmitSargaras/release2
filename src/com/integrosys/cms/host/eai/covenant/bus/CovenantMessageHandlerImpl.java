package com.integrosys.cms.host.eai.covenant.bus;

import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

public class CovenantMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {

	public final static String COVENANT_TRX_HANDLER = "integrosys.eai.trxhandler.covenant.";

	public final static String COVENANT_REPUBLISH_TRX_HANDLER = "integrosys.eai.trxhandler.covenant.republish.";

	public final static String COVENANT_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.covenant.";

	public final static String COVENANT_REPUBLISH_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.covenant.republish.";

	public final static String COVENANT_STAGING_TRX_HANDLER = "integrosys.eai.stagingtrxhandler.covenant.";

	public final static String COVENANT_VALIDATE_TRX_HANDLER = "integrosys.eai.validatetrxhandler.covenant.";
	
	/**
	 * Get covenant transaction handler.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_TRX_HANDLER
	 */
	protected String getTrxHandlerPropertyString() {
		return COVENANT_TRX_HANDLER;
	}

	/**
	 * Get covenant transaction handler for republishing.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_REPUBLISH_TRX_HANDLER;
	 */
	protected String getRepublishTrxHandlerPropertyString() {
		return COVENANT_REPUBLISH_TRX_HANDLER;
	}

	/**
	 * Get handler for actual covenant transaction.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_ACTUAL_TRX_HANDLER
	 */
	protected String getActualHandlerPropertyString() {
		return COVENANT_ACTUAL_TRX_HANDLER;
	}

	/**
	 * Get handler for republishing of actual covenant transaction.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_REPUBLISH_ACTUAL_TRX_HANDLER
	 */
	protected String getRepublishActualHandlerPropertyString() {
		return COVENANT_REPUBLISH_ACTUAL_TRX_HANDLER;
	}

	/**
	 * Get handler for staging covenant transaction.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_STAGING_TRX_HANDLER
	 */
	protected String getStagingHandlerPropertyString() {
		return COVENANT_STAGING_TRX_HANDLER;
	}

	/**
	 * Get handler for covenant validation.
	 * 
	 * @return SCISecurityMessageManager.COVENANT_VALIDATE_TRX_HANDLER
	 */
	protected String getValidationHandlerPropertyString() {
		return COVENANT_VALIDATE_TRX_HANDLER;
	}

}
