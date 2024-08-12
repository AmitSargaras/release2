/**
 * 
 */
package com.integrosys.cms.app.creditriskparam.bus.internalcreditrating;

import java.util.List;

/**
 * @author priya
 *
 */
public interface IInternalCreditRatingBusManager {
	
	public List getAllInternalCreditRating() throws InternalCreditRatingException;

	public List getInternalCreditRatingByGroupId(long groupId) throws InternalCreditRatingException;

	public List createInternalCreditRating(List iCRList) throws InternalCreditRatingException;

	public List updateInternalCreditRating (List iCRList) throws InternalCreditRatingException;
	
}
