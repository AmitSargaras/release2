/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/SBPropertyIndexFeedBusManager.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 */
public interface SBPropertyIndexFeedBusManager extends EJBObject {

	/**
	 * Gets all the entries in the property index index group.
	 * @param id Identifies the property index group.
	 * @return The group containing all the entries.
	 * @throws com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException
	 *         when there are errors in getting the entries.
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(long id) throws PropertyIndexFeedGroupException,
			RemoteException;

	/**
	 * Gets all the entries in the property index group.
	 * @param groupType Identifies the property index index group.
	 * @param subType Identifies the sub type.
	 * @return The group containing all the entries.
	 * @throws PropertyIndexFeedGroupException when there are errors in getting
	 *         the entries.
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(String groupType, String subType)
			throws PropertyIndexFeedGroupException, RemoteException;

	/**
	 * Creates the property index feed group with all the entries.
	 * @param group The property index index feed group to be created.
	 * @return The created property index index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in creating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup createPropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException, RemoteException;

	/**
	 * Updates the property index feed group with the entries. This is a
	 * replacement action.
	 * @param group The property index feed group to update with.
	 * @return The updated property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in updating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup updatePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException, RemoteException;

	/**
	 * Deletes the property index index index feed group and all its entries.
	 * @param group The property index feed group to delete with all its
	 *        entries.
	 * @return The deleted property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in deleting
	 *         the group.
	 */
	public IPropertyIndexFeedGroup deletePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException, RemoteException;

}
