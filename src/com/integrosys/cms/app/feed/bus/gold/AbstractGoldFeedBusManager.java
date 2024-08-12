package com.integrosys.cms.app.feed.bus.gold;

public abstract class AbstractGoldFeedBusManager implements IGoldFeedBusManager {
	private IGoldDao goldDao;

	/**
	 * @return the goldDao
	 */
	public IGoldDao getGoldDao() {
		return goldDao;
	}

	/**
	 * @param goldDao the goldDao to set
	 */
	public void setGoldDao(IGoldDao goldDao) {
		this.goldDao = goldDao;
	}

	/**
	 * Gets all the entries in the gold group.
	 * @param id Identifies the gold group.
	 * @return The group containing all the entries.
	 * @throws GoldFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IGoldFeedGroup getGoldFeedGroup(long id) throws GoldFeedGroupException {
		return getGoldDao().getGoldFeedGroupByPrimaryKey(getGoldFeedGroupEntityName(), new Long(id));
	}

	public IGoldFeedGroup getGoldFeedGroup(String type) throws GoldFeedGroupException {
		return getGoldDao().getGoldFeedGroupByType(getGoldFeedGroupEntityName(), type);
	}

	/**
	 * Creates the gold feed group with all the entries.
	 * @param group The gold feed group to be created.
	 * @return The created gold feed group.
	 * @throws GoldFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IGoldFeedGroup createGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException {
		return getGoldDao().createGoldFeedGroup(getGoldFeedGroupEntityName(), group);
	}

	/**
	 * Updates the gold feed group with the entries. This is a replacement
	 * action.
	 * @param group The gold feed group to update with.
	 * @return The updated gold feed group.
	 * @throws GoldFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IGoldFeedGroup updateGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException {
		return getGoldDao().updateGoldFeedGroup(getGoldFeedGroupEntityName(), group);
	}

	/**
	 * Deletes the gold feed group and all its entries.
	 * @param group The gold feed group to delete with all its entries.
	 * @return The deleted gold feed group.
	 * @throws GoldFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IGoldFeedGroup deleteGoldFeedGroup(IGoldFeedGroup group) throws GoldFeedGroupException {
		getGoldDao().deleteGoldFeedGroup(getGoldFeedGroupEntityName(), group);
		return group;
	}
	/**
	 * to be implemented by sub class, to provide the entity name of gold feed
	 * group. Different for actual and staging copy
	 * 
	 * @return the entity name of gold feed group
	 */
	public abstract String getGoldFeedGroupEntityName();
}
