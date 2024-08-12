package com.integrosys.cms.app.generalparam.bus;

/**
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public abstract class AbstractGeneralParamBusManager implements IGeneralParamBusManager {
	
	public IGeneralParamDao generalParamDao;

	public IGeneralParamDao getGeneralParamDao() {
		return generalParamDao;
	}

	public void setGeneralParamDao(IGeneralParamDao generalParamDao) {
		this.generalParamDao = generalParamDao;
	}

	public IGeneralParamGroup getGeneralParamGroup(long id) throws GeneralParamGroupException {
		return getGeneralParamDao().getGeneralParamGroupByPrimaryKey(getGeneralParamGroupEntityName(), new Long(id));
	}

	public IGeneralParamGroup getGeneralParamGroup(String groupType) throws GeneralParamGroupException {
		return getGeneralParamDao().getGeneralParamGroupByGroupType(getGeneralParamGroupEntityName(), groupType);
	}

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws GeneralParamGroupException when there are errors in creating the
	 *         group.
	 */
	public IGeneralParamGroup createGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException {
		return getGeneralParamDao().createGeneralParamGroup(getGeneralParamGroupEntityName(), group);
	}

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws GeneralParamGroupException when there are errors in updating the
	 *         group.
	 */
	public IGeneralParamGroup updateGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException {
		return getGeneralParamDao().updateGeneralParamGroup(getGeneralParamGroupEntityName(), group);
	}

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws GeneralParamGroupException when there are errors in deleting the
	 *         group.
	 */
	public IGeneralParamGroup deleteGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException {
		getGeneralParamDao().deleteGeneralParamGroup(getGeneralParamGroupEntityName(), group);

		return group;
	}

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws GeneralParamEntryException on errors.
	 */
	public IGeneralParamEntry getGeneralParamEntryByRic(String ric) throws GeneralParamEntryException {
		return getGeneralParamDao().getGeneralParamEntryByRic(getGeneralParamEntryEntityName(), ric);
	}

	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode) throws GeneralParamEntryException {
		return getGeneralParamDao().getGeneralParamEntryByParamCodeActual(paramCode);
	}
	

	public abstract String getGeneralParamGroupEntityName();

	public abstract String getGeneralParamEntryEntityName();

}
