/**
 * 
 */
package com.integrosys.cms.app.custexposure.bus;

import java.io.Serializable;

/**
 * @author skchai
 *
 */
public interface ICustExposureBusManager extends Serializable {

	/**
	 * Get Customer Exposure object. This returns the object without the sorting the ILimit yet
	 * @param subProfileId
	 * @return
	 * @throws CustExposureException
	 */
	public ICustExposure getCustExposure(long subProfileId) throws CustExposureException;
}
