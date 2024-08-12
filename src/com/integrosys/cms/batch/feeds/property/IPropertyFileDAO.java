package com.integrosys.cms.batch.feeds.property;

import java.util.List;

import com.integrosys.cms.app.collateral.bus.IValuation;

/**
 * <p>
 * Data Access Object interface to be used by Property valuation feeds module.
 * 
 * <p>
 * Use {@link #createPropertyItems(String, List)} for bulk insert of property
 * valuation fees to gain performance
 * 
 * @author Forbys Wei
 * @author Chong Jun Yong
 */
public interface IPropertyFileDAO {

	/**
	 * Create facility feed into persistent storage, return back persisted info.
	 * 
	 * @param entityName entity name of the persisted item
	 * @param valuationItem property valuation feed info
	 * @return property valuation feed info persisted
	 */
	public IValuation createPropertyItem(String entityName, IValuation valuationItem);

	/**
	 * Create all property valuation feeds in the list supplied
	 * 
	 * @param entityName entity name of the persisted item
	 * @param valuationFeedsList list of property valuation feed to be persisted
	 */
	public void createPropertyItems(String entityName, List valuationFeedsList);

    /**
     * Save or update all the property feeds in the list supplied
     *
     * @param propertyFeedList
     */
    public void saveOrUpdatePropertyFeeds(final List propertyFeedList);

}
