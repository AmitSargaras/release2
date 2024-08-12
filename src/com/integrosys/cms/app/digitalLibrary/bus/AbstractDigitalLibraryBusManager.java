package com.integrosys.cms.app.digitalLibrary.bus;

/**
 * @author btchng
 * @author Chong Jun Yong
 * @since 2003/08/06
 */
public abstract class AbstractDigitalLibraryBusManager implements IDigitalLibraryBusManager {
	public IDigitalLibraryDao digitalLibraryDao;

	/**
	 * @return the digitalLibraryDao
	 */
	public IDigitalLibraryDao getDigitalLibraryDao() {
		return digitalLibraryDao;
	}

	/**
	 * @param digitalLibraryDao the digitalLibraryDao to set
	 */
	public void setDigitalLibraryDao(IDigitalLibraryDao digitalLibraryDao) {
		this.digitalLibraryDao = digitalLibraryDao;
	}

	public IDigitalLibraryGroup getDigitalLibraryGroup(long id) throws DigitalLibraryException {
		return getDigitalLibraryDao().getDigitalLibraryGroupByPrimaryKey(getDigitalLibraryGroupEntityName(), new Long(id));
	}

	public IDigitalLibraryGroup getDigitalLibraryGroup(String groupType) throws DigitalLibraryException {
		return getDigitalLibraryDao().getDigitalLibraryGroupByGroupType(getDigitalLibraryGroupEntityName(), groupType);
	}

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws DigitalLibraryException when there are errors in creating the
	 *         group.
	 */
	public IDigitalLibraryGroup createDigitalLibraryGroup(IDigitalLibraryGroup group) throws DigitalLibraryException {
		return getDigitalLibraryDao().createDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);
	}

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws DigitalLibraryException when there are errors in updating the
	 *         group.
	 */
	public IDigitalLibraryGroup updateDigitalLibraryGroup(IDigitalLibraryGroup group) throws DigitalLibraryException {
		return getDigitalLibraryDao().updateDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);
	}

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws DigitalLibraryException when there are errors in deleting the
	 *         group.
	 */
	public IDigitalLibraryGroup deleteDigitalLibraryGroup(IDigitalLibraryGroup group) throws DigitalLibraryException {
		getDigitalLibraryDao().deleteDigitalLibraryGroup(getDigitalLibraryGroupEntityName(), group);

		return group;
	}

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws DigitalLibraryEntryException on errors.
	 */
	public IDigitalLibraryEntry getDigitalLibraryEntryByRic(String ric) throws DigitalLibraryEntryException {
		return getDigitalLibraryDao().getDigitalLibraryEntryByRic(getDigitalLibraryEntryEntityName(), ric);
	}

	public abstract String getDigitalLibraryGroupEntityName();

	public abstract String getDigitalLibraryEntryEntityName();

}
