/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/SBPropertyIndexFeedBusManagerBean.java,v 1.2 2003/09/02 06:58:17 btchng Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.RemoveException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/02 06:58:17 $ Tag: $Name: $
 * 
 * @ejb.bean name="SBPropertyIndexFeedBusManager"
 *           jndi-name="SBPropertyIndexFeedBusManagerHome"
 *           local-jndi-name="SBPropertyIndexFeedBusManagerLocalHome"
 *           view-type="remote" type="Stateless"
 * @ejb.transaction type="Required"
 */
public class SBPropertyIndexFeedBusManagerBean extends AbstractPropertyIndexFeedBusManager implements SessionBean {

	/**
	 * Gets all the entries in the property index group.
	 * @param id Identifies the property index group.
	 * @return The group containing all the entries.
	 * @throws com.integrosys.cms.app.feed.bus.propertyindex.PropertyIndexFeedGroupException
	 *         when there are errors in getting the entries.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(long id) throws PropertyIndexFeedGroupException {
		try {
			EBPropertyIndexFeedGroupHome home = getEbPropertyIndexFeedGroupHome();
			return home.findByPrimaryKey(new Long(id)).getValue();

		}
		catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			return null;
			// throw new PropertyIndexFeedGroupException("FinderException", e);
		}
		catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * 
	 * @param groupType
	 * @return
	 * @throws PropertyIndexFeedGroupException
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroup getPropertyIndexFeedGroup(String groupType, String subType)
			throws PropertyIndexFeedGroupException {
		try {

			DefaultLogger.debug(this, "groupType = " + groupType);
			DefaultLogger.debug(this, "subType = " + subType);

			EBPropertyIndexFeedGroupHome home = getEbPropertyIndexFeedGroupHome();
			return home.findByGroupTypeAndSubType(groupType, subType).getValue();

		}
		catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			return null;
			// throw new PropertyIndexFeedGroupException("FinderException", e);
		}
		catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
	}

	/**
	 * Creates the property index feed group with all the entries.
	 * @param group The property index feed group to be created.
	 * @return The created property index index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in creating
	 *         the group.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroup createPropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			EBPropertyIndexFeedGroupHome home = getEbPropertyIndexFeedGroupHome();
			EBPropertyIndexFeedGroup eb = home.create(group);
			IPropertyIndexFeedGroup propertyIndexFeedGroup = eb.getValue();

			DefaultLogger.debug(this, "number of incoming feed entries = " + group.getFeedEntries().length);
			if (propertyIndexFeedGroup.getFeedEntries() != null) {
				DefaultLogger.debug(this, "number of created feed entries " + "(should be 0) = "
						+ propertyIndexFeedGroup.getFeedEntries().length);
			}
			else {
				DefaultLogger.debug(this, "no created feed entries (correct).");
			}

			propertyIndexFeedGroup.setFeedEntries(group.getFeedEntries());
			eb.setValue(propertyIndexFeedGroup);
			return eb.getValue();
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("CreateException", e);
		}
		catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
		catch (ConcurrentUpdateException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("ConcurrentUpdateException", e);
		}
	}

	/**
	 * Updates the property index index feed group with the entries. This is a
	 * replacement action.
	 * @param group The property index feed group to update with.
	 * @return The updated property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in updating
	 *         the group.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroup updatePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			EBPropertyIndexFeedGroupHome home = getEbPropertyIndexFeedGroupHome();
			EBPropertyIndexFeedGroup eb = home.findByPrimaryKey(new Long(group.getPropertyIndexFeedGroupID()));
			eb.setValue(group);
			return eb.getValue();

		}
		catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			return null;
			// throw new PropertyIndexFeedGroupException("FinderException", e);
		}
		catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
		catch (ConcurrentUpdateException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("ConcurrentUpdateException", e);
		}
	}

	/**
	 * Deletes the property index index feed group and all its entries.
	 * @param group The property index index feed group to delete with all its
	 *        entries.
	 * @return The deleted property index feed group.
	 * @throws PropertyIndexFeedGroupException when there are errors in deleting
	 *         the group.
	 * 
	 * @ejb.interface-method view-type="remote"
	 */
	public IPropertyIndexFeedGroup deletePropertyIndexFeedGroup(IPropertyIndexFeedGroup group)
			throws PropertyIndexFeedGroupException {
		try {
			EBPropertyIndexFeedGroupHome home = getEbPropertyIndexFeedGroupHome();
			EBPropertyIndexFeedGroup eb = home.findByPrimaryKey(new Long(group.getPropertyIndexFeedGroupID()));
			IPropertyIndexFeedGroup entry = eb.getValue();
			eb.remove();
			return entry;

		}
		catch (FinderException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			return null;
			// throw new PropertyIndexFeedGroupException("FinderException", e);
		}
		catch (RemoteException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoteException", e);
		}
		catch (RemoveException e) {
			context.setRollbackOnly();
			DefaultLogger.error(this, "", e);
			throw new PropertyIndexFeedGroupException("RemoveException", e);
		}
	}

	/**
	 * @ejb.create-method view-type="remote"
	 */
	public void ejbCreate() {
	}

	public void setSessionContext(SessionContext sessionContext) throws EJBException {
		context = sessionContext;
	}

	public void ejbRemove() throws EJBException {
	}

	public void ejbActivate() throws EJBException {
	}

	public void ejbPassivate() throws EJBException {
	}

	protected EBPropertyIndexFeedEntryHome getEbPropertyIndexFeedEntryHome() {
		return (EBPropertyIndexFeedEntryHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_ENTRY_JNDI, EBPropertyIndexFeedEntryHome.class.getName());
	}

	protected EBPropertyIndexFeedGroupHome getEbPropertyIndexFeedGroupHome() {
		return (EBPropertyIndexFeedGroupHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_GROUP_JNDI, EBPropertyIndexFeedGroupHome.class.getName());
	}

	private SessionContext context;
}
