package com.integrosys.cms.host.eai.security;

/**
 * Exception to be thrown when the pledgor is not found in the system.
 * @author Thurein
 * 
 */
public class NoSuchPledgorException extends EAISecurityMessageException {

	private static final long serialVersionUID = -6066964160437169421L;

	private static final String NO_PLDG_FOUND_ERROR_CODE = "NO_PLDG_FOUND";

	public NoSuchPledgorException(long CMSPledgorID) {
		super("Pledgor not found in the system; CMS Pledgor Key [" + CMSPledgorID + "]");
	}

	public NoSuchPledgorException(String cifId) {
		super("Pledgor not found in the system; CIF Number [" + cifId + "]");
	}

	public String getErrorCode() {
		return NO_PLDG_FOUND_ERROR_CODE;
	}

}
