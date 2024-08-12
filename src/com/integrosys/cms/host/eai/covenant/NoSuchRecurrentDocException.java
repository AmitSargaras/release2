package com.integrosys.cms.host.eai.covenant;

/**
 * 
 * @author Thurein </br>
 * @version 1.0</br>
 * @since 20-Dec-2008</br> Exception to be thrown, when the recurrent doc can
 *        not be found for the given limit profile id and sub profile id.
 */
public class NoSuchRecurrentDocException extends EAIConvenantMessageException {

	private static final long serialVersionUID = 9104949771216985663L;

	private static final String RECURRENT_NOT_FOUND_ERROR_CODE = "RECDOC_NOT_FOUND";

	/**
	 * Default constructor to provide LOS AA Number and CIF Id
	 * 
	 * @param losAANumber the LOS AA Number
	 * @param cifId Customer Identifier Number
	 */
	public NoSuchRecurrentDocException(String losAANumber, String cifId) {
		super("RecurrentDoc not found in the system; for LOS AA Number [" + losAANumber + "], CIF Number [" + cifId
				+ "]");
	}

	public String getErrorCode() {
		return RECURRENT_NOT_FOUND_ERROR_CODE;
	}

}
