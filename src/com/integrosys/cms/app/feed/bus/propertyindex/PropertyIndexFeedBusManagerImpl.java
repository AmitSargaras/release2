/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/PropertyIndexFeedBusManagerImpl.java,v 1.1 2003/08/20 10:59:05 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.rmi.RemoteException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/20 10:59:05 $ Tag: $Name: $
 */
public class PropertyIndexFeedBusManagerImpl extends AbstractPropertyIndexFeedBusManager {

	/**
	 * Gets all the entries in the property index group.
	 * @param id Identifies the property index index group.
	 * @return The group containing all the entries.
	 * @throws com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException
	 *         when there are errors in getting the entries.
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(long id) throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedBusManager().getPropertyIndexFeedGroup(id);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(String groupType, String subType)
			throws PropertyIndexFeedGroupException {

		try {
			return getSbPropertyIndexFeedBusManager().getPropertyIndexFeedGroup(groupType, subType);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Creates the property index feed group with all the entries.
	 * @param group The property index feed group to be created.
	 * @return The created property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in creating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup createPropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedBusManager().createPropertyIndexFeedGroup(group);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Updates the property index feed group with the entries. This is a
	 * replacement action.
	 * @param group The property index index feed group to update with.
	 * @return The updated property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in updating
	 *         the group.
	 */
	public IPropertyIndexFeedGroup updatePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedBusManager().updatePropertyIndexFeedGroup(group);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Deletes the property index index feed group and all its entries.
	 * @param group The property index feed group to delete with all its
	 *        entries.
	 * @return The deleted property index index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in deleting
	 *         the group.
	 */
	public IPropertyIndexFeedGroup deletePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			return getSbPropertyIndexFeedBusManager().deletePropertyIndexFeedGroup(group);
		}
		catch (RemoteException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Helper method to get the bus manager remote interface. Will be overridden
	 * in subclass.
	 * @return The bus manager remote interface.
	 */
	protected SBPropertyIndexFeedBusManager getSbPropertyIndexFeedBusManager() {
		return (SBPropertyIndexFeedBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_PROPERTY_INDEX_FEED_BUS_MANAGER_JNDI, SBPropertyIndexFeedBusManagerHome.class
						.getName());
	}
}
