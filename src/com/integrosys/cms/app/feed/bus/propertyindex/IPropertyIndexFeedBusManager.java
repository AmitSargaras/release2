/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/IPropertyIndexFeedBusManager.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.io.Serializable;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 */
public interface IPropertyIndexFeedBusManager extends Serializable {

	/**
	 * Gets all the entries in the property index index group.
	 * @param id Identifies the property index group.
	 * @return The group containing all the entries.
	 * @throws PropertyIndexFeedGroupException when there are errors in getting
	 *         the entries.
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(long id) throws PropertyIndexFeedGroupException;

	/**
	 * Gets all the entries in the property index group.
	 * @param groupType Identifies the property index group.
	 * @param subType Identifies the subtype.
	 * @return The group containing all the entries.
	 * @throws PropertyIndexFeedGroupException when there are errors in getting
	 *         the entries.
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(String groupType, String subType)
			throws PropertyIndexFeedGroupException;

	/**
	 * Creates the property index feed group with all the entries.
	 * @param group The property index feed group to be created.
	 * @return The created property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in creating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup createPropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException;

	/**
	 * Updates the property index feed group with the entries. This is a
	 * replacement action.
	 * @param group The property index feed group to update with.
	 * @return The updated property index index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in updating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup updatePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException;

	/**
	 * Deletes the property index feed group and all its entries.
	 * @param group The property index feed group to delete with all its
	 *        entries.
	 * @return The deleted property index index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in deleting
	 *         the group.
	 */
	public IPropertyIndexFeedGroup deletePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException;

}
