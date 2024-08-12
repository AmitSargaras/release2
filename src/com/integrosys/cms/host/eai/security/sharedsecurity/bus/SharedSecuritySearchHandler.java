package com.integrosys.cms.host.eai.security.sharedsecurity.bus;

/**
 * Search Handler for the shared security, can be subclasses for earch
 * collateral sub type to do the granual action.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface SharedSecuritySearchHandler {
	/**
	 * Search the the security based on search criteria having search details.
	 * 
	 * @param searchCriteria search criteria, might have the advanced criteria
	 *        for collateral type
	 * @return array of shared security result item, empty array will be
	 *         returned if there is no collateral match the criteria.
	 */
	public SharedSecurityResultItem[] searchAndGenerateSharedSecurity(SharedSecuritySearch searchCriteria);
}
