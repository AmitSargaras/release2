/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.util.List;

/**
 * Group exposure DAO
 * @author skchai
 *
 */
public interface IGroupExposureDAO {

	/**
	 * Get list of customer Ids that is related to the parent subprofile id
	 * in terms of share holder, directors or key management
	 * @param parentSubProfileId
	 * @return
	 */
	public List getCustomerRelationshipIds(long parentSubProfileId);
}
