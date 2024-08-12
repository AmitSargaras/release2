package com.integrosys.cms.host.eai.security;

/**
 * Exception to be raised whenever there is insurance policy not found in the
 * system.
 * 
 * @author Chong Jun Yong
 * 
 */
public class NoSuchInsurancePolicyException extends EAISecurityMessageException {

	private static final long serialVersionUID = -4874703080260933330L;

	private static final String NO_INSR_POLICY_FOUND_ERROR_CODE = "NO_INSR_FOUND";

	/**
	 * Default constructor to provide error such that CMS insurance policy is
	 * not found.
	 * 
	 * @param cmsSecurityId CMS security internal key
	 * @param cmsInsurancePolicyId CMS insurance policy internal key
	 */
	public NoSuchInsurancePolicyException(long cmsSecurityId, long cmsInsurancePolicyId) {
		super("Insurance Policy not found in the system; CMS Security Key [" + cmsSecurityId
				+ "], CMS Insurance Policy key [" + cmsInsurancePolicyId + "]");
	}

	public String getErrorCode() {
		return NO_INSR_POLICY_FOUND_ERROR_CODE;
	}
}
