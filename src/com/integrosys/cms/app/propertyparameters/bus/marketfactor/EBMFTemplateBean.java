/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.bus.marketfactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;

/**
 * Entity bean implementation for MF Template entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFTemplateBean implements IMFTemplate, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the MF Template. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getMFTemplateID" };

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	public static final String EMPTY_STR = "";

	public abstract Long getMFTemplateIDPK();

	public abstract void setMFTemplateIDPK(Long mFTemplateIDPK);

	public abstract String getMFTemplateName();

	public abstract void setMFTemplateName(String mFTemplateName);

	public abstract String getMFTemplateStatus();

	public abstract void setMFTemplateStatus(String mFTemplateStatus);

	public abstract String getSecurityTypeID();

	public abstract void setSecurityTypeID(String secTypeID);

	public abstract Date getLastUpdateDate();

	public abstract void setLastUpdateDate(Date value);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract Collection getMFItemCMR();

	public abstract void setMFItemCMR(Collection mFItemCMR);

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getMFTemplateID
	 */
	public long getMFTemplateID() {
		if (null != getMFTemplateIDPK()) {
			return getMFTemplateIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setMFTemplateID
	 */
	public void setMFTemplateID(long value) {
		setMFTemplateIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getSecuritySubTypeList
	 */
	public IMFTemplateSecSubType[] getSecuritySubTypeList() {
		try {
			EBMFTemplateSecSubTypeLocalHome ebMFTemplateSecSubTypeLocalHome = getEBMFTemplateSecSubTypeLocalHome();
			Iterator i = ebMFTemplateSecSubTypeLocalHome.findByTemplateID(getMFTemplateID(), getFindExcludeStatus())
					.iterator();

			ArrayList arrayList = new ArrayList();
			while (i.hasNext()) {
				arrayList.add(((EBMFTemplateSecSubTypeLocal) i.next()).getValue());
			}
			return (IMFTemplateSecSubType[]) arrayList.toArray(new IMFTemplateSecSubType[0]);

		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setSecuritySubTypeList
	 */
	public void setSecuritySubTypeList(IMFTemplateSecSubType[] secSubTypeList) {
		// do nothing
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#getMFItemList
	 */
	public IMFItem[] getMFItemList() {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate#setMFItemList
	 */
	public void setMFItemList(IMFItem[] mFItemList) {
		// do nothing
	}

	/**
	 * Get the sequence of primary key for this MF Template.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_TEMPLATE;
	}

	protected String getFindExcludeStatus() {
		return ICMSConstant.STATE_DELETED;
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param mFTemplate of type IMFTemplate
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IMFTemplate mFTemplate) throws CreateException {
		if (null == mFTemplate) {
			throw new CreateException("IMFTemplate is null!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating MF template with ID: " + pk);
			setMFTemplateIDPK(new Long(pk));

			AccessorUtil.copyValue(mFTemplate, this, EXCLUDE_METHOD);

			// setSecurityTypeID( ICMSConstant.SECURITY_TYPE_PROPERTY );
			setStatus(ICMSConstant.STATE_ACTIVE);
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * @param mFTemplate of type IMFTemplate
	 */
	public void ejbPostCreate(IMFTemplate mFTemplate) {
		try {
			persistSecuritySubType(mFTemplate.getSecuritySubTypeList());

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplate#createDependants
	 */
	public void createDependants(IMFTemplate value, long verTime, long templatePK) throws PropertyParametersException,
			ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		}
		else {

			IMFItem[] item = value.getMFItemList();
			List itemList = new ArrayList();
			if (item != null) {
				for (int i = 0; i < item.length; i++) {
					IMFItem next = item[i];
					next.setMFParentID(templatePK);
					itemList.add(next);
				}
			}

			createMFItem(itemList);
			itemList.clear();
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplate#getValue
	 */
	public IMFTemplate getValue() {
		OBMFTemplate mFTemplate = new OBMFTemplate();
		AccessorUtil.copyValue(this, mFTemplate);
		mFTemplate.setMFItemList(retrieveMFItem());
		return mFTemplate;
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplate#setValue
	 */
	public void setValue(IMFTemplate mFTemplate) throws PropertyParametersException, VersionMismatchException {
		try {
			checkVersionMismatch(mFTemplate);
			AccessorUtil.copyValue(mFTemplate, this, EXCLUDE_METHOD);

			persistSecuritySubType(mFTemplate.getSecuritySubTypeList());
			updateMFItem(mFTemplate.getMFItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (Exception e) {
			throw new PropertyParametersException("Caught Exception!", e);
		}
	}

	/**
	 * @see com.integrosys.cms.app.propertyparameters.bus.marketfactor.EBMFTemplate#delete
	 */
	public void delete(IMFTemplate mFTemplate) throws PropertyParametersException, VersionMismatchException {
		checkVersionMismatch(mFTemplate);
		setStatus(ICMSConstant.STATE_DELETED);
		deleteAllSecuritySubType();
		deleteAllMFItem();
		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Check the version of this MF Template.
	 * 
	 * @param mFTemplate of type IMFTemplate
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IMFTemplate mFTemplate) throws VersionMismatchException {
		if (getVersionTime() != mFTemplate.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + mFTemplate.getVersionTime());
		}
	}

	// ******************** Private methods for Security Sub Type
	// ****************
	private void deleteAllSecuritySubType() throws PropertyParametersException {
		persistSecuritySubType(null);
	}

	private void persistSecuritySubType(IMFTemplateSecSubType[] value) throws PropertyParametersException {

		try {
			EBMFTemplateSecSubTypeLocalHome ebMFTemplateSecSubTypeLocalHome = getEBMFTemplateSecSubTypeLocalHome();

			Collection oldMFTemplateSecSubTypes = ebMFTemplateSecSubTypeLocalHome.findByTemplateID(getMFTemplateID(),
					EXCLUDE_STATUS);

			if ((value == null) || (value.length == 0)) {
				removeMFTemplateSecSubTypes(oldMFTemplateSecSubTypes);
				return;
			}

			HashMap oldMFTemplateSecSubTypeMap = new HashMap();
			for (Iterator inter = oldMFTemplateSecSubTypes.iterator(); inter.hasNext();) {
				EBMFTemplateSecSubTypeLocal theEjb = (EBMFTemplateSecSubTypeLocal) inter.next();
				IMFTemplateSecSubType aMFTemplateSecSubType = theEjb.getValue();
				oldMFTemplateSecSubTypeMap.put(new Long(aMFTemplateSecSubType.getTemplateSubTypeID()),
						aMFTemplateSecSubType);
			}

			HashMap existMFTemplateSecSubTypeMap = new HashMap();
			for (int index = 0; index < value.length; index++) {
				if (oldMFTemplateSecSubTypeMap.get(new Long(value[index].getTemplateSubTypeID())) == null) {
					value[index].setMFTemplateID(getMFTemplateID());
					try {
						ebMFTemplateSecSubTypeLocalHome.create(value[index]);
					}
					catch (CreateException e) {
						e.printStackTrace();
					}
				}
				else {

					existMFTemplateSecSubTypeMap.put(new Long(value[index].getTemplateSubTypeID()), value[index]);
				}
			}

			if (oldMFTemplateSecSubTypes.size() != 0) {
				for (Iterator inter = oldMFTemplateSecSubTypes.iterator(); inter.hasNext();) {
					EBMFTemplateSecSubTypeLocal theEjb = (EBMFTemplateSecSubTypeLocal) inter.next();
					IMFTemplateSecSubType aMFTemplateSecSubType = theEjb.getValue();
					if (existMFTemplateSecSubTypeMap.get(new Long(aMFTemplateSecSubType.getTemplateSubTypeID())) == null) {
						try {

							theEjb.delete();
						}
						catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PropertyParametersException("Caught Exception!", e);
		}
	}

	private void removeMFTemplateSecSubTypes(Collection value) throws PropertyParametersException {

		if ((value == null) || (value.size() == 0)) {
			return;
		}
		for (Iterator inter = value.iterator(); inter.hasNext();) {
			try {

				((EBMFTemplateSecSubTypeLocal) inter.next()).delete();

			}
			catch (Exception e) {
				e.printStackTrace();
				throw new PropertyParametersException("Caught Exception!", e);
			}
		}
	}

	// ******************** Private methods for MF Item ****************
	private IMFItem[] retrieveMFItem() {

		Collection c = getMFItemCMR();
		if ((null == c) || (c.size() == 0)) {
			return null;
		}
		else {

			Iterator i = c.iterator();
			ArrayList list = new ArrayList();

			while (i.hasNext()) {
				EBMFItemLocal theEjb = (EBMFItemLocal) i.next();
				IMFItem item = theEjb.getValue();
				if ((item.getStatus() != null) && item.getStatus().equals(getFindExcludeStatus())) {
					continue;
				}
				DefaultLogger.debug(this, "retrieveMFItem, MFTemplateID=" + item.getMFParentID());
				DefaultLogger.debug(this, "retrieveMFItem, MFItemID=" + item.getMFItemID());
				list.add(item);
			}
			return (OBMFItem[]) list.toArray(new OBMFItem[0]);
		}
	}

	private void createMFItem(List createList) throws PropertyParametersException {

		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getMFItemCMR();

		Iterator i = createList.iterator();
		try {
			EBMFItemLocalHome home = getEBMFItemLocalHome();
			while (i.hasNext()) {
				IMFItem ob = (IMFItem) i.next();
				EBMFItemLocal local = home.create(ob);
				c.add(local);
			}
		}
		catch (PropertyParametersException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PropertyParametersException("Caught Exception!", e);
		}
	}

	private void deleteAllMFItem() throws PropertyParametersException {
		updateMFItem(null);
	}

	private void updateMFItem(IMFItem[] value) throws PropertyParametersException {
		try {
			Collection c = getMFItemCMR();

			if (null == value) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all MF Item
					deleteMFItem(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createMFItem(Arrays.asList(value));
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBMFItemLocal local = (EBMFItemLocal) i.next();

					long sid = local.getMFItemRef();
					boolean update = false;

					for (int j = 0; j < value.length; j++) {
						IMFItem newOB = value[j];

						if (newOB.getMFItemRef() == sid) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < value.length; j++) {
					i = c.iterator();
					IMFItem newOB = value[j];
					boolean found = false;

					while (i.hasNext()) {
						EBMFItemLocal local = (EBMFItemLocal) i.next();
						long sid = local.getMFItemRef();

						if (newOB.getMFItemRef() == sid) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteMFItem(deleteList);
				createMFItem(createList);
			}
		}
		catch (PropertyParametersException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PropertyParametersException("Caught Exception: " + e.toString());
		}
	}

	private void deleteMFItem(List deleteList) throws PropertyParametersException {

		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getMFItemCMR();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBMFItemLocal local = (EBMFItemLocal) i.next();

				// do soft delete
				local.delete();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PropertyParametersException("Caught Exception!", e);
		}
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	/**
	 * Method to get EB Local Home for EBMFItem
	 * 
	 * @return EBMFItemLocalHome
	 * @throws PropertyParametersException on errors
	 */
	protected EBMFItemLocalHome getEBMFItemLocalHome() throws PropertyParametersException {
		EBMFItemLocalHome home = (EBMFItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_ITEM_LOCAL_JNDI, EBMFItemLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new PropertyParametersException("EBMFItemLocalHome is null!");
		}
	}

	/**
	 * Method to get EB Local Home for BMFTemplateSecSubType
	 * 
	 * @return EBMFTemplateSecSubTypeLocalHome
	 * @throws PropertyParametersException on errors
	 */
	protected EBMFTemplateSecSubTypeLocalHome getEBMFTemplateSecSubTypeLocalHome() throws PropertyParametersException {
		EBMFTemplateSecSubTypeLocalHome home = (EBMFTemplateSecSubTypeLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_MF_TEMPLATE_SEC_SUBTYPE_LOCAL_JNDI,
						EBMFTemplateSecSubTypeLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new PropertyParametersException("EBMFTemplateSecSubTypeLocalHome is null!");
		}
	}
}