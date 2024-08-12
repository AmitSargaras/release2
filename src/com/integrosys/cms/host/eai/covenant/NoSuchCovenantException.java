package com.integrosys.cms.host.eai.covenant;

/**
 * 
 * @author Thurein</br>
 * @serial 20-Dec-2008</br>
 * @version 1.0 </br> Exception to be thrown when the covent item can not be
 *          found for the given covenant item id.
 * 
 */
public class NoSuchCovenantException extends EAIConvenantMessageException {

	private static final long serialVersionUID = 3861805418619471673L;

	private static final String COVENANT_NOT_FOUND_ERROR_CODE = "COV_NOT_FOUND";

	public NoSuchCovenantException(Long covenantID) {
		super("Covenant Item not found for the covenant item id: [" + covenantID + "]");
	}

	public NoSuchCovenantException(String msg) {
		super(msg);
	}

	public String getErrorCode() {
		return COVENANT_NOT_FOUND_ERROR_CODE;
	}

}
