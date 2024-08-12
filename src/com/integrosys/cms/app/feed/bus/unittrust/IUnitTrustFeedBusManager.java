/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/unittrust/IUnitTrustFeedBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.unittrust;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/03 03:27:23 $ Tag: $Name: $
 */
public interface IUnitTrustFeedBusManager {

	/**
	 * Gets all the entries in the unit trust group.
	 * @param id Identifies the unit trust group.
	 * @return The group containing all the entries.
	 * @throws UnitTrustFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroup(long id) throws UnitTrustFeedGroupException;

	/**
	 * Gets all the entries in the unit trust group.
	 * @param groupType Identifies the unit trustgroup.
	 * @param subType Identifies the subtype.
	 * @return The group containing all the entries.
	 * @throws UnitTrustFeedGroupException when there are errors in getting the
	 *         entries.
	 */
	public IUnitTrustFeedGroup getUnitTrustFeedGroup(String groupType, String subType)
			throws UnitTrustFeedGroupException;

	/**
	 * Creates the unit trust feed group with all the entries.
	 * @param group The unit trust feed group to be created.
	 * @return The created unit trust feed group.
	 * @throws UnitTrustFeedGroupException when there are errors in creating the
	 *         group.
	 */
	public IUnitTrustFeedGroup createUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException;

	/**
	 * Updates the unit trust feed group with the entries. This is a replacement
	 * action.
	 * @param group The unit trust feed group to update with.
	 * @return The updated unit trustfeed group.
	 * @throws UnitTrustFeedGroupException when there are errors in updating the
	 *         group.
	 */
	public IUnitTrustFeedGroup updateUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException;

	/**
	 * Deletes the unit trust feed group and all its entries.
	 * @param group The unit trust feed group to delete with all its entries.
	 * @return The deleted unit trust feed group.
	 * @throws UnitTrustFeedGroupException when there are errors in deleting the
	 *         group.
	 */
	public IUnitTrustFeedGroup deleteUnitTrustFeedGroup(IUnitTrustFeedGroup group) throws UnitTrustFeedGroupException;

	/**
	 * Gets the unit trust feed entry by ric.
	 * @param ric The RIC.
	 * @return The unit trust feed entry having the RIC or <code>null</code>.
	 * @throws UnitTrustFeedEntryException on errors.
	 */
	public IUnitTrustFeedEntry getUnitTrustFeedEntryByRic(String ric) throws UnitTrustFeedEntryException;

	/**
	 * <p>
	 * Update from a image copy to a working copy. normally this will be used
	 * for updating actual copy using staging copy.
	 * 
	 * <p>
	 * This normally will call from transaction engine, operation which is
	 * checker approve update.
	 * 
	 * @param workingCopy working copy, which in cms context is actual copy
	 * @param imageCopy image copy, which in cms context is staging copy
	 * @return the updated working copy
	 * @throws UnitTrustFeedGroupException
	 */
	public IUnitTrustFeedGroup updateToWorkingCopy(IUnitTrustFeedGroup workingCopy, IUnitTrustFeedGroup imageCopy)
			throws UnitTrustFeedGroupException;
}
