package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised when the AA provided having a CIF which doesn't match
 * with the CIF in the system.
 * @author Chong Jun Yong
 * 
 */
public class CifMismatchException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = 7894479069280491584L;

	/**
	 * Constructor to provide LOS AA number, AA Source, CIF in the message and
	 * Actual CIF in the System
	 * @param losAaNumber LOS AA Number
	 * @param aaSource LOS AA Source
	 * @param expectedCif CIF provided in the message
	 * @param actualCif CIF in the system
	 */
	public CifMismatchException(String losAaNumber, String aaSource, String expectedCif, String actualCif) {
		super("AA with LOS AA Number [" + losAaNumber + "] Source [" + aaSource + "] is belong Customer with CIF ["
				+ actualCif + "], but CIF [" + expectedCif + "] is provided in the message.");
	}
}
