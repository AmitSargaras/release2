package com.integrosys.cms.host.eai.limit;

/**
 * Exception to be raised when the Joint Borrower for the AA is not found in the
 * system.
 * 
 * @author User
 * 
 */
public class NoSuchJointBorrowerException extends EaiLimitProfileMessageException {

	private static final long serialVersionUID = 1717797959267901406L;

	private static final String NO_JOINT_BORROWER_FOUND_ERROR_CODE = "JB_NOT_FOUND";

	private String cifId;

	private String hostAANumer;

	private String losAANumber;

	/**
	 * Default constructor to provide, cif number, host AA number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id
	 * @param hostAANumber the host AA number, might be the same for same
	 *        customer from same place(center / branch)
	 * @param losAANumber the LOS AA number
	 */
	public NoSuchJointBorrowerException(String cifId, String hostAANumber, String losAANumber) {
		super("Joint Borrower customer info not found in the system; for CIF [" + cifId + "], Host AA Number ["
				+ hostAANumber + "], LOS AA Number [" + losAANumber + "].");
		this.cifId = cifId;
		this.hostAANumer = hostAANumber;
		this.losAANumber = losAANumber;
	}

	/**
	 * Default constructor to provide, cif number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id,
	 * @param losAANumber the LOS AA number
	 */
	public NoSuchJointBorrowerException(String cifId, String losAANumber) {
		super("Joint Borrower customer info not found in the system; for CIF [" + cifId + "], LOS AA Number ["
				+ losAANumber + "].");
		this.cifId = cifId;
		this.losAANumber = losAANumber;
	}

	public String getCifId() {
		return cifId;
	}

	public String getHostAANumer() {
		return hostAANumer;
	}

	public String getLosAANumber() {
		return losAANumber;
	}

	/**
	 * To append more message after detailed message has given, such as action
	 * to be taken if encounter this exception
	 */
	public String getMessage() {
		StringBuffer buf = new StringBuffer(super.getMessage());
		buf.append("; Please check whether Customer, CIF [").append(cifId).append("] ");
		buf.append("has been published successfully into CMS.");

		return buf.toString();
	}

	public String getErrorCode() {
		return NO_JOINT_BORROWER_FOUND_ERROR_CODE;
	}
}
