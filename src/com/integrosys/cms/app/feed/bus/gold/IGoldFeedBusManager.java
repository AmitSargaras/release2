package com.integrosys.cms.app.feed.bus.gold;

public interface IGoldFeedBusManager {

	/**
	 * Gets all the entries in the gold group.
	 * @param id Identifies the gold group.
	 * @return The group containing all the entries.
	 * @throws GoldFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IGoldFeedGroup getGoldFeedGroup(long id) throws GoldFeedGroupException;

	/**
	 * Gets all the entries in the gold group.
	 * @param groupType Identifies the gold group.
	 * @return The group containing all the entries.
	 * @throws GoldFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IGoldFeedGroup getGoldFeedGroup(String groupType) throws GoldFeedGroupException;

	/**
	 * Creates the gold feed group with all the entries.
	 * @param group The gold feed group to be created.
	 * @return The created gold feed group.
	 * @throws GoldFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IGoldFeedGroup createGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException;

	/**
	 * Updates the gold feed group with the entries. This is a replacement
	 * action.
	 * @param group The gold feed group to update with.
	 * @return The updated gold feed group.
	 * @throws GoldFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IGoldFeedGroup updateGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException;

	/**
	 * Deletes the gold feed group and all its entries.
	 * @param group The gold feed group to delete with all its entries.
	 * @return The deleted gold feed group.
	 * @throws GoldFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IGoldFeedGroup deleteGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException;

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
	public IGoldFeedGroup updateToWorkingCopy(IGoldFeedGroup workingCopy, IGoldFeedGroup imageCopy)
			throws GoldFeedGroupException;
}
