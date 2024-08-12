/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/stockindex/IStockIndexFeedBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.stockindex;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/03 03:27:23 $ Tag: $Name: $
 */
public interface IStockIndexFeedBusManager {

	/**
	 * Gets all the entries in the stock index index group.
	 * @param id Identifies the stock index group.
	 * @return The group containing all the entries.
	 * @throws StockIndexFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroup(long id) throws StockIndexFeedGroupException;

	/**
	 * Gets all the entries in the stock index group.
	 * @param groupType Identifies the stock index group.
	 * @param subType Identifies the subtype.
	 * @return The group containing all the entries.
	 * @throws StockIndexFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IStockIndexFeedGroup getStockIndexFeedGroup(String groupType, String subType)
			throws StockIndexFeedGroupException;

	/**
	 * Creates the stock index feed group with all the entries.
	 * @param group The stock index feed group to be created.
	 * @return The created stock index feed group.
	 * @throws StockIndexFeedGroupException when there are errors in creating
	 *         the group.
	 */
	public IStockIndexFeedGroup createStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException;

	/**
	 * Updates the stock index feed group with the entries. This is a
	 * replacement action.
	 * @param group The stock index feed group to update with.
	 * @return The updated stock index index feed group.
	 * @throws StockIndexFeedGroupException when there are errors in updating
	 *         the group.
	 */
	public IStockIndexFeedGroup updateStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException;

	/**
	 * Deletes the stock index feed group and all its entries.
	 * @param group The stock index feed group to delete with all its entries.
	 * @return The deleted stock index index feed group.
	 * @throws StockIndexFeedGroupException when there are errors in deleting
	 *         the group.
	 */
	public IStockIndexFeedGroup deleteStockIndexFeedGroup(IStockIndexFeedGroup group)
			throws StockIndexFeedGroupException;

	/**
	 * Gets the stock index feed entry by ric.
	 * @param ric The RIC.
	 * @return The stock index feed entry having the RIC or <code>null</code>.
	 * @throws StockIndexFeedEntryException on errors.
	 */
	public IStockIndexFeedEntry getStockIndexFeedEntryByRic(String ric) throws StockIndexFeedEntryException;

	/**
	 * <p>
	 * Update from a image copy to a working copy, will be used for updating
	 * actual copy using staging copy.
	 * 
	 * <p>
	 * This normally will get called from transaction engine, operation which is
	 * checker approve update.
	 * 
	 * @param workingCopy working copy, which in cms context is actual copy
	 * @param imageCopy image copy, which in cms context is staging copy
	 * @return the updated working copy
	 * @throws StockIndexFeedEntryException
	 */
	public IStockIndexFeedGroup updateToWorkingCopy(IStockIndexFeedGroup workingCopy, IStockIndexFeedGroup imageCopy)
			throws StockIndexFeedGroupException;
}
