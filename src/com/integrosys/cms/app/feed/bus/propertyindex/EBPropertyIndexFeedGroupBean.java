/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedGroupBean.java,v 1.4 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 * 
 * @ejb.bean name="EBPropertyIndexFeedGroup"
 *           jndi-name="EBPropertyIndexFeedGroupHome"
 *           local-jndi-name="EBPropertyIndexFeedGroupLocalHome"
 *           view-type="both" type="CMP" reentrant="false" cmp-version="2.x"
 *           schema="EBPropertyIndexFeedGroup"
 *           primkey-field="cmpPropertyIndexFeedGroupID"
 * @ejb.ejb-ref ejb-name="EBPropertyIndexFeedEntry" view-type="local"
 *              ref-name="EBPropertyIndexFeedEntry"
 * @ejb.persistence table-name="CMS_FEED_GROUP"
 * @ejb.transaction type="Required"*
 */
public abstract class EBPropertyIndexFeedGroupBean implements EntityBean, IPropertyIndexFeedGroup {

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="FEED_GROUP_ID"
	 * @ejb.pk-field
	 */
	public abstract Long getCmpPropertyIndexFeedGroupID();

	/**
	 * 
	 * @param anPropertyIndexFeedGroupID
	 */
	public abstract void setCmpPropertyIndexFeedGroupID(Long anPropertyIndexFeedGroupID);

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="GROUP_TYPE"
	 */
	public abstract String getType();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="GROUP_SUB_TYPE"
	 */
	public abstract String getSubType();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="CMS_VERSION_TIME"
	 */
	public abstract long getVersionTime();

	/**
	 * 
	 * @return
	 * @ejb.relation name="PropertyIndexFeedGroup-PropertyIndexFeedEntries"
	 *               role-
	 *               name="PropertyIndexFeedGroup-Has-PropertyIndexFeedEntries"
	 *               target-ejb="EBPropertyIndexFeedEntry" target-role-name=
	 *               "PropertyIndexFeedEntry-BelongsTo-PropertyIndexFeedGroup"
	 *               target-cascade-delete="yes"
	 * @weblogic.target-column-map foreign-key-column="FEED_GROUP_ID"
	 *                             key-column="FEED_GROUP_ID"
	 */
	public abstract Collection getCmrPropertyIndexFeedEntry();

	/**
	 * 
	 * @param c
	 */
	public abstract void setCmrPropertyIndexFeedEntry(Collection c);

	/**
	 * Create a document item Information
	 * @param aPropertyIndexFeedGroup
	 * @return Long - the document item ID (primary key)
	 * @throws CreateException on error
	 * 
	 * @ejb.create-method view-type="both"
	 */
	public Long ejbCreate(IPropertyIndexFeedGroup aPropertyIndexFeedGroup) throws CreateException {
		if (aPropertyIndexFeedGroup == null) {
			throw new CreateException("PropertyIndexFeedGroup is null!");
		}

		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			DefaultLogger.debug(this, "Sequence Name: " + getSequenceName());

			// Old code commented below.

			// if (aPropertyIndexFeedGroup.getPropertyIndexFeedGroupID() ==
			// com.integrosys
			// .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			// pk = Long.parseLong((new SequenceManager()).getSeqNum(
			// getSequenceName(), true));
			// } else {
			// pk = aPropertyIndexFeedGroup.getPropertyIndexFeedGroupID();
			// }

			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));

			DefaultLogger.debug(this, "Item to be inserted: " + aPropertyIndexFeedGroup);
			AccessorUtil.copyValue(aPropertyIndexFeedGroup, this, EXCLUDE_METHOD);
			//createPropertyIndexFeedEntry(Arrays.asList(aPropertyIndexFeedGroup
			// .getFeedEntries()));
			DefaultLogger.debug(this, "PK: " + pk);
			setPropertyIndexFeedGroupID(pk);
			setVersionTime(VersionGenerator.getVersionNumber());

			return null;

		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param aPropertyIndexFeedGroup
	 */
	public void ejbPostCreate(IPropertyIndexFeedGroup aPropertyIndexFeedGroup) {
	}

	/**
	 * Return the Interface representation of this object
	 * @return IPropertyIndexFeedGroup
	 * 
	 * @ejb.interface-method view-type="both"
	 */
	public IPropertyIndexFeedGroup getValue() throws PropertyIndexFeedGroupException {
		OBPropertyIndexFeedGroup value = new OBPropertyIndexFeedGroup();
		AccessorUtil.copyValue(this, value);
		value.setFeedEntries(retrievePropertyIndexFeedEntry());

		return value;
	}

	/**
	 * Persist a PropertyIndexFeedGroup
	 * @param aPropertyIndexFeedGroup
	 * @throws ConcurrentUpdateException if enctr concurrent update
	 * 
	 * @ejb.interface-method view-type="both"
	 */
	public void setValue(IPropertyIndexFeedGroup aPropertyIndexFeedGroup) throws ConcurrentUpdateException,
			PropertyIndexFeedGroupException {
		try {
			if (getVersionTime() != aPropertyIndexFeedGroup.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}

			AccessorUtil.copyValue(aPropertyIndexFeedGroup, this, EXCLUDE_METHOD);

			updatePropertyIndexFeedEntry(aPropertyIndexFeedGroup.getFeedEntries());

			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new PropertyIndexFeedGroupException("Exception in setValue: " + ex.toString());
		}
	}

	public IPropertyIndexFeedEntry[] getFeedEntries() {
		// return
		// (IPropertyIndexFeedEntry[])getCmrPropertyIndexFeedEntry().toArray(
		// new IPropertyIndexFeedEntry[0]);
		return null;
	}

	public void setFeedEntries(IPropertyIndexFeedEntry[] param) {
		// setCmrPropertyIndexFeedEntry(Arrays.asList(param));
	}

	/**
	 * @return long - the item ID
	 */
	public long getPropertyIndexFeedGroupID() {

		DefaultLogger.debug(this, "entering getPropertyIndexFeedGroupID()");

		if (getCmpPropertyIndexFeedGroupID() != null) {
			return getCmpPropertyIndexFeedGroupID().longValue();
		}

		DefaultLogger.debug(this, "exiting getPropertyIndexFeedGroupID()");

		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * @param anPropertyIndexFeedGroupID - long
	 */
	public void setPropertyIndexFeedGroupID(long anPropertyIndexFeedGroupID) {
		setCmpPropertyIndexFeedGroupID(new Long(anPropertyIndexFeedGroupID));
	}

	/**
	 * Helper method to get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_FEED_FEED_GROUP;
	}

	/**
	 * Helper method to get EB Local Home for PropertyIndexFeedEntry
	 */
	protected EBPropertyIndexFeedEntryLocalHome getEBPropertyIndexFeedEntryLocalHome()
			throws PropertyIndexFeedGroupException {
		EBPropertyIndexFeedEntryLocalHome home = (EBPropertyIndexFeedEntryLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_PROPERTY_INDEX_FEED_ENTRY_LOCAL_JNDI, EBPropertyIndexFeedEntryLocalHome.class
						.getName());

		if (home != null) {
			return home;
		}

		throw new PropertyIndexFeedGroupException("EBPropertyIndexFeedEntryItemLocal is null!");
	}

	/**
	 * Create the list of PropertyIndexFeedEntry under the current
	 * PropertyIndexFeedGroup
	 * @param aPropertyIndexFeedEntryList - List of IPropertyIndexFeedEntry to
	 *        be inserted into child EB
	 * @throws PropertyIndexFeedGroupException on errors
	 */
	private void createPropertyIndexFeedEntry(List aPropertyIndexFeedEntryList) throws PropertyIndexFeedGroupException {

		DefaultLogger.debug(this, "entering createPropertyIndexFeedEntry(<<List>>)...");

		if ((aPropertyIndexFeedEntryList == null) || (aPropertyIndexFeedEntryList.size() == 0)) {
			return; // do nothing
		}

		Collection col = getCmrPropertyIndexFeedEntry();
		Iterator iter = aPropertyIndexFeedEntryList.iterator();

		try {
			EBPropertyIndexFeedEntryLocalHome home = getEBPropertyIndexFeedEntryLocalHome();

			DefaultLogger.debug(this, "the runtime class of ebfeedentrylocalhome is " + home.getClass().getName());

			while (iter.hasNext()) {
				IPropertyIndexFeedEntry obj = (IPropertyIndexFeedEntry) iter.next();
				DefaultLogger.debug(this, "Creating PropertyIndexFeedEntry ID: " + obj.getPropertyIndexFeedEntryID());

				// EBPropertyIndexFeedEntryLocal local = home.create(
				// new Long(getPropertyIndexFeedGroupID()), obj);
				EBPropertyIndexFeedEntryLocal local = home.create(obj);

				col.add(local); // container managed persistence

			}
		}
		catch (Exception ex) {
			throw new PropertyIndexFeedGroupException("Exception in createPropertyIndexFeedGroup: " + ex.toString());
		}
	}

	/**
	 * To retrieve the list of PropertyIndexFeedEntry
	 * @return IPropertyIndexFeedEntry[] - the list of IPropertyIndexFeedEntry
	 * @throws PropertyIndexFeedGroupException on error
	 */
	private IPropertyIndexFeedEntry[] retrievePropertyIndexFeedEntry() throws PropertyIndexFeedGroupException {

		DefaultLogger.debug(this, "entering retrievePropertyIndexFeedEntry()...");

		try {
			Collection col = getCmrPropertyIndexFeedEntry();

			if ((col == null) || (col.size() == 0)) {

				DefaultLogger.debug(this, "no feed entries.");

				return null;

			}
			else {
				ArrayList lPropertyIndexFeedEntryList = new ArrayList();
				Iterator iter = col.iterator();

				while (iter.hasNext()) {
					EBPropertyIndexFeedEntryLocal local = (EBPropertyIndexFeedEntryLocal) iter.next();
					if (!local.isDeletedInd()) {
						IPropertyIndexFeedEntry obj = local.getValue();
						lPropertyIndexFeedEntryList.add(obj);
					}
				}

				DefaultLogger.debug(this, "number of feed" + "entries = " + lPropertyIndexFeedEntryList.size());

				return (IPropertyIndexFeedEntry[]) lPropertyIndexFeedEntryList.toArray(new IPropertyIndexFeedEntry[0]);
			}
		}
		catch (Exception ex) {
			throw new PropertyIndexFeedGroupException("Exception at retrievePropertyIndexFeedEntry: " + ex.toString());
		}
	}

	private void updatePropertyIndexFeedEntry(IPropertyIndexFeedEntry[] aIPropertyIndexFeedEntryList)
			throws PropertyIndexFeedGroupException {

		DefaultLogger.debug(this, "entering updatePropertyIndexFeedEntry(<<IPropertyIndexFeedEntry[]>>)...");

		try {
			Collection col = getCmrPropertyIndexFeedEntry();

			if (aIPropertyIndexFeedEntryList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deletePropertyIndexFeedEntry(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createPropertyIndexFeedEntry(Arrays.asList(aIPropertyIndexFeedEntryList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				// Looping through the current children.
				while (iter.hasNext()) {
					// Get current child.
					EBPropertyIndexFeedEntryLocal local = (EBPropertyIndexFeedEntryLocal) iter.next();
					// Get current child's id.
					long propertyIndexFeedEntryRef = local.getPropertyIndexFeedEntryRef();

					DefaultLogger.debug(this, "property index feed entry id from cmr = " + propertyIndexFeedEntryRef);

					boolean update = false;

					// For each incoming child.
					for (int ii = 0; ii < aIPropertyIndexFeedEntryList.length; ii++) {
						IPropertyIndexFeedEntry newOB = aIPropertyIndexFeedEntryList[ii];

						// If any of the incoming child's id matches a
						// current child's id.
						if (newOB.getPropertyIndexFeedEntryRef() == propertyIndexFeedEntryRef) {
							// perform update

							DefaultLogger.debug(this, "updating child value.");

							local.setValue(newOB);

							update = true;

							// Stop going through the incoming chilren.
							break;
						}
					}

					// There is no match from the incoming children for the
					// current child in consideration inside the while loop.
					// Therefore must delete.
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}

				// Go through each incoming child and if it does not match
				// any of the current children on the id,
				// then it must be added.
				for (int ii = 0; ii < aIPropertyIndexFeedEntryList.length; ii++) {

					// The below will not work when staging comes into
					// consideration...
					// if (aIPropertyIndexFeedEntryList[ii].
					// getPropertyIndexFeedEntryRef() ==
					// com.integrosys.cms.app.common.constant.ICMSConstant.
					// LONG_INVALID_VALUE) {
					// createList.add(aIPropertyIndexFeedEntryList[ii]);
					// }

					iter = col.iterator();
					boolean found = false;
					// Go through the current children until the incoming
					// child is matched or the last current child is
					// reached.
					while (iter.hasNext() && !found) {
						if (aIPropertyIndexFeedEntryList[ii].getPropertyIndexFeedEntryRef() == ((EBPropertyIndexFeedEntryLocal) iter
								.next()).getPropertyIndexFeedEntryRef()) {
							found = true;
						}
					}

					if (!found) {
						createList.add(aIPropertyIndexFeedEntryList[ii]);
					}

				}

				// Remove and create children. The updating has already been
				// done.
				deletePropertyIndexFeedEntry(deleteList);
				createPropertyIndexFeedEntry(createList);

			}
		}
		catch (PropertyIndexFeedGroupException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new PropertyIndexFeedGroupException("Exception in updatePropertyIndexFeedEntry: " + ex.toString());
		}
	}

	/**
	 * Delete the list of PropertyIndexFeedEntry under the current
	 * PropertyIndexFeedGroup
	 * @param aDeletionList - List
	 * @throws PropertyIndexFeedGroupException on errors
	 */
	private void deletePropertyIndexFeedEntry(List aDeletionList) throws PropertyIndexFeedGroupException {

		DefaultLogger.debug(this, "entering deletePropertyIndexFeedEntry(<<List>>)...");

		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}

		try {
			Collection col = getCmrPropertyIndexFeedEntry();
			Iterator iter = aDeletionList.iterator();

			IPropertyIndexFeedEntry entry = null;
			while (iter.hasNext()) {
				EBPropertyIndexFeedEntryLocal local = (EBPropertyIndexFeedEntryLocal) iter.next();
				// col.remove(local);
				// local.remove();
				local.setDeletedInd(true);
				// entry = local.getValue();
				// entry.setDeletedInd(true);
				// local.setValue(entry);
			}
		}
		catch (Exception ex) {
			throw new PropertyIndexFeedGroupException("Exception in deletePropertyIndexFeedEntry: " + ex.toString());
		}
	}

	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getPropertyIndexFeedGroupID" };

    public abstract void setType(String param);

    public abstract void setSubType(String param);

    public abstract void setVersionTime(long versionTime);
}
