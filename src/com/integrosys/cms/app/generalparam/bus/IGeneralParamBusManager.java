/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/bond/IBondFeedBusManager.java,v 1.2 2003/09/03 03:27:23 btchng Exp $
 */
package com.integrosys.cms.app.generalparam.bus;

/**
 * <p>
 * Interface of business manager for bond feed
 * 
 * <p>
 * <b>NOTE</b> All calling to persistence storage should come through here, be
 * it JDBC, ORM, or even Entity Bean.
 * 
* @author $Author: Dattatray Thorat $
 * @version $Revision: 1.4 $
 * @since $Date: 2011/05/10 10:45:20 $ Tag: $Name%
 */
public interface IGeneralParamBusManager {

	/**
	 * Gets all the entries in the bond group.
	 * @param id Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws GeneralParamGroupException when there are errors in getting the
	 *         entries.
	 */
	public IGeneralParamGroup getGeneralParamGroup(long id) throws GeneralParamGroupException;

	/**
	 * Gets all the entries in the bond group.
	 * @param groupType Identifies the bond group.
	 * @return The group containing all the entries.
	 * @throws GeneralParamGroupException when there are errors in getting the
	 *         entries.
	 */
	public IGeneralParamGroup getGeneralParamGroup(String groupType) throws GeneralParamGroupException;

	/**
	 * Creates the bond feed group with all the entries.
	 * @param group The bond feed group to be created.
	 * @return The created bond feed group.
	 * @throws GeneralParamGroupException when there are errors in creating the
	 *         group.
	 */
	public IGeneralParamGroup createGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException;

	/**
	 * Updates the bond feed group with the entries. This is a replacement
	 * action.
	 * @param group The bond feed group to update with.
	 * @return The updated bond feed group.
	 * @throws GeneralParamGroupException when there are errors in updating the
	 *         group.
	 */
	public IGeneralParamGroup updateGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException;

	/**
	 * Deletes the bond feed group and all its entries.
	 * @param group The bond feed group to delete with all its entries.
	 * @return The deleted bond feed group.
	 * @throws GeneralParamGroupException when there are errors in deleting the
	 *         group.
	 */
	public IGeneralParamGroup deleteGeneralParamGroup(IGeneralParamGroup group) throws GeneralParamGroupException;

	/**
	 * Gets the bond feed entry by ric.
	 * @param ric The RIC.
	 * @return The bond feed entry having the RIC or <code>null</code>.
	 * @throws GeneralParamEntryException on errors.
	 */
	public IGeneralParamEntry getGeneralParamEntryByRic(String ric) throws GeneralParamEntryException;

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
	 * @throws GeneralParamGroupException
	 */
	public IGeneralParamGroup updateToWorkingCopy(IGeneralParamGroup workingCopy, IGeneralParamGroup imageCopy)
			throws GeneralParamGroupException;

	public IGeneralParamEntry getGeneralParamEntryByParamCodeActual(String paramCode) throws GeneralParamEntryException;
}
