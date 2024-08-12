/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/SCISecurityMessageManager.java,v 1.4 2003/11/15 06:13:36 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus;

import com.integrosys.cms.host.eai.core.AbstractWorkflowAwareMessageHandler;

/**
 * Security message manager.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/11/15 06:13:36 $ Tag: $Name: $
 */
public class SecurityMessageHandlerImpl extends AbstractWorkflowAwareMessageHandler {
	public final static String SECURITY_TRX_HANDLER = "integrosys.eai.trxhandler.security.";

	public final static String SECURITY_REPUBLISH_TRX_HANDLER = "integrosys.eai.trxhandler.security.republish.";

	public final static String SECURITY_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.security.";

	public final static String SECURITY_REPUBLISH_ACTUAL_TRX_HANDLER = "integrosys.eai.actualtrxhandler.security.republish.";

	public final static String SECURITY_STAGING_TRX_HANDLER = "integrosys.eai.stagingtrxhandler.security.";

	public final static String SECURITY_VALIDATE_TRX_HANDLER = "integrosys.eai.validatetrxhandler.security.";

	
	

	/**
	 * Get security transaction handler.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_TRX_HANDLER
	 */
	protected String getTrxHandlerPropertyString() {
		return SECURITY_TRX_HANDLER;
	}

	/**
	 * Get security transaction handler for republishing.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_REPUBLISH_TRX_HANDLER;
	 */
	protected String getRepublishTrxHandlerPropertyString() {
		return SECURITY_REPUBLISH_TRX_HANDLER;
	}

	/**
	 * Get handler for actual security transaction.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_ACTUAL_TRX_HANDLER
	 */
	protected String getActualHandlerPropertyString() {
		return SECURITY_ACTUAL_TRX_HANDLER;
	}

	/**
	 * Get handler for republishing of actual security transaction.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_REPUBLISH_ACTUAL_TRX_HANDLER
	 */
	protected String getRepublishActualHandlerPropertyString() {
		return SECURITY_REPUBLISH_ACTUAL_TRX_HANDLER;
	}

	/**
	 * Get handler for staging security transaction.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_STAGING_TRX_HANDLER
	 */
	protected String getStagingHandlerPropertyString() {
		return SECURITY_STAGING_TRX_HANDLER;
	}

	/**
	 * Get handler for security validation.
	 * 
	 * @return SCISecurityMessageManager.SECURITY_VALIDATE_TRX_HANDLER
	 */
	protected String getValidationHandlerPropertyString() {
		return SECURITY_VALIDATE_TRX_HANDLER;
	}
}
