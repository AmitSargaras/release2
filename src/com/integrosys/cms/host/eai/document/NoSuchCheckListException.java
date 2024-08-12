package com.integrosys.cms.host.eai.document;

/**
 * Exception to be raised when the required CheckList doesn't exist
 * 
 * @author Iwan Satria
 * @since 1.0
 */
public class NoSuchCheckListException extends EAIDocumentMessageException {

	private static final long serialVersionUID = -1342424003894947442L;

	private static final String NO_CHECKLIST_ERROR_CODE = "CHKLST_NOT_FOUND";

	/**
	 * Constructor to provide checklist internal key
	 * 
	 * @param checklistId checklist id
	 */
	public NoSuchCheckListException(long checklistId) {
		super("No checklist found in the system; for internal key [" + checklistId + "]");
	}

	/**
	 * Constructor to provide AA number and Collateral internal key whenever
	 * there is no checklist found for the collateral
	 * 
	 * @param aaNumber
	 * @param cmsCollateralId
	 */
	public NoSuchCheckListException(String aaNumber, long cmsCollateralId) {
		super("No collateral checklist found in the system; for LOS AA Number [" + aaNumber
				+ "], collateral internal key [" + cmsCollateralId + "]");
	}

	/**
	 * <p>
	 * Constructor to provide AA number, cif number and customer type.
	 * <p>
	 * Customer type possible values, MAIN_BORROWER, JOINT_BORROWER,
	 * CO_BORROWER, PLEDGOR
	 * 
	 * @param aaNumber LOS AA Number
	 * @param cifNumber Customer identity number
	 * @param customerType customer type from the AA specific
	 */
	public NoSuchCheckListException(String aaNumber, String cifNumber, String customerType) {
		super("No " + customerType + " checklist found in the system; for LOS AA Number [" + aaNumber
				+ "], CIF Number [" + cifNumber + "]");
	}

	public String getErrorCode() {
		return NO_CHECKLIST_ERROR_CODE;
	}
}
