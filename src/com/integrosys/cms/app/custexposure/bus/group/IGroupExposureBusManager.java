/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus.group;

import java.io.Serializable;

import com.integrosys.cms.app.custexposure.bus.CustExposureException;

/**
 * @author skchai
 *
 */
public interface IGroupExposureBusManager extends Serializable {

	/**
	 * Get Group Exposure object. This returns the group exposure with the first 5
	 * entity exposure retrieved
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	public IGroupExposure getGroupExposure(long groupId) throws CustExposureException;
}
