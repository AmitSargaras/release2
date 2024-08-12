package com.integrosys.cms.batch.mimb.customer;

import java.util.List;

/**
 * <p>
 * Data Access Object to be used by MIMB customer module.
 * 
 * <p>
 * Use {@link #createCustomerInfoItems(List)} for bulk insert of customer feed
 * to gain performance
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public interface ICustomerInfoDAO {

	/**
	 * Create customer feed into persistent storage, return back persisted info.
	 * 
	 * @param customerInfoItem customer feed info
	 * @return customer feed info persisted
	 */
	public ICustomerInfo createCustomerInfoItem(ICustomerInfo customerInfoItem);

	/**
	 * Create all customer feed in the list supplied
	 * 
	 * @param customerInfoItemFeedList list of customer feed to be persisted
	 */
	public void createCustomerInfoItems(List customerInfoItemFeedList);

}
