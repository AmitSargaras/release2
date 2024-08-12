package com.integrosys.cms.batch.mimb.limit;

import java.util.List;

/**
 * <p>
 * Data Access Object interface to be used by MIMB limit module
 * 
 * <p>
 * Use {@link #createLimitInfoItems(List)} for bulk insert of limit info feeds
 * to gain performance.
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 * 
 */
public interface ILimitInformationFileDAO {

	/**
	 * Create limit info feed into persistent storage, return back persisted
	 * info.
	 * 
	 * @param limitInformationItem limit info feed to be persisted
	 * @return limit info feed persisted
	 */
	public ILimitInformation createLimitInfoItem(ILimitInformation limitInformationItem);

	/**
	 * Create all customer feed in the list supplied
	 * 
	 * @param limitInfoFeedList list of limit info feeds to be persisted
	 */
	public void createLimitInfoItems(List limitInfoFeedList);

}
