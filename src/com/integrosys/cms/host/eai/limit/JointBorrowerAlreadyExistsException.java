package com.integrosys.cms.host.eai.limit;

import com.integrosys.cms.host.eai.RecordAlreadyExistsException;

/**
 * Exception to be raised whereby the joint borrower for a AA / Limit Profile is
 * already in the system, but trying to insert again.
 * 
 * @author Chong Jun Yong
 * 
 */
public class JointBorrowerAlreadyExistsException extends RecordAlreadyExistsException {

	private static final long serialVersionUID = -4249840284120719530L;

	private static final String JOINT_BORROWER_ALREADY_EXISTS_ERROR_CODE = "JOINT_BORROW_EXISTS";

	private String cifId;

	private String losAANumber;

	/**
	 * Default constructor to provide, cif number, LOS AA number
	 * 
	 * @param cifId the cif number, customer id,
	 * @param losAANumber the LOS AA number
	 */
	public JointBorrowerAlreadyExistsException(String cifId, String losAANumber) {
		super("Joint Borrower already exists in the system; for CIF [" + cifId + "] LOS AA Number [" + losAANumber
				+ "].");
		this.cifId = cifId;
		this.losAANumber = losAANumber;
	}

	public String getCifId() {
		return cifId;
	}

	public String getLosAANumber() {
		return losAANumber;
	}

	public String getErrorCode() {
		return JOINT_BORROWER_ALREADY_EXISTS_ERROR_CODE;
	}
}
