/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.bus.type.property.marketfactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for MF Checklist entity.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name: $
 */
public abstract class EBMFChecklistBean implements IMFChecklist, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during update to the MF Checklist. */
	protected static final String[] EXCLUDE_METHOD = new String[] { "getMFChecklistID" };

	public static final String EXCLUDE_STATUS = ICMSConstant.STATE_DELETED;

	public static final String EMPTY_STR = "";

	public abstract Long getMFChecklistIDPK();

	public abstract void setMFChecklistIDPK(Long mFChecklistIDPK);

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Long getEBMFTemplateID();

	public abstract void setEBMFTemplateID(Long mFTemplateID);

	public abstract String getMFTemplateName();

	public abstract void setMFTemplateName(String mFTemplateName);

	public abstract Date getLastUpdateDate();

	public abstract void setLastUpdateDate(Date value);

	public abstract String getStatus();

	public abstract void setStatus(String status);

	public abstract long getVersionTime();

	public abstract void setVersionTime(long versionTime);

	public abstract Collection getMFChecklistItemCMR();

	public abstract void setMFChecklistItemCMR(Collection mFChecklistItemCMR);

	// ************ Non-persistence method *************

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getMFChecklistID
	 */
	public long getMFChecklistID() {
		if (null != getMFChecklistIDPK()) {
			return getMFChecklistIDPK().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setMFChecklistID
	 */
	public void setMFChecklistID(long value) {
		setMFChecklistIDPK(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getCollateralID
	 */
	public long getCollateralID() {
		if (null != getEBCollateralID()) {
			return getEBCollateralID().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setCollateralID
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getMFTemplateID
	 */
	public long getMFTemplateID() {
		if (null != getEBMFTemplateID()) {
			return getEBMFTemplateID().longValue();
		}
		else {
			return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setMFTemplateID
	 */
	public void setMFTemplateID(long value) {
		setEBMFTemplateID(new Long(value));
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getCollateralSubType
	 */
	public ICollateralSubType getCollateralSubType() {
		try {
			ICollateralDAO dao = CollateralDAOFactory.getDAO();

			return dao.getCollateralSubTypeByCollateralID(getEBCollateralID());

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "", e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setCollateralSubType
	 */
	public void setCollateralSubType(ICollateralSubType value) {
		// do nothing
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#getMFChecklistItemList
	 */
	public IMFChecklistItem[] getMFChecklistItemList() {
		return null;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist#setMFChecklistItemList
	 */
	public void setMFChecklistItemList(IMFChecklistItem[] mFChecklistItemList) {
		// do nothing
	}

	/**
	 * Get the sequence of primary key for this MF Checklist.
	 * 
	 * @return String
	 */
	protected String getPKSequenceName() {
		return ICMSConstant.SEQUENCE_MF_CHECKLIST;
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param mFChecklist of type IMFChecklist
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(IMFChecklist mFChecklist) throws CreateException {
		if (null == mFChecklist) {
			throw new CreateException("IMFChecklist is null!");
		}

		try {

			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getPKSequenceName(), true));
			DefaultLogger.debug(this, "Creating MF checklist with ID: " + pk);
			setMFChecklistIDPK(new Long(pk));

			AccessorUtil.copyValue(mFChecklist, this, EXCLUDE_METHOD);

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
	 * @param mFChecklist of type IMFChecklist
	 */
	public void ejbPostCreate(IMFChecklist mFChecklist) {
		// do nothing
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklist#createDependants
	 */
	public void createDependants(IMFChecklist value, long verTime, long checklistPK) throws CollateralException,
			ConcurrentUpdateException {
		if (verTime != getVersionTime()) {
			throw new ConcurrentUpdateException("Version mismatched!");
		}
		else {

			IMFChecklistItem[] item = value.getMFChecklistItemList();
			List itemList = new ArrayList();
			if (item != null) {
				for (int i = 0; i < item.length; i++) {
					IMFChecklistItem next = item[i];
					next.setMFParentID(checklistPK);
					itemList.add(next);
				}
			}

			createMFChecklistItem(itemList);
			itemList.clear();
		}
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklist#getValue
	 */
	public IMFChecklist getValue() {
		OBMFChecklist mFChecklist = new OBMFChecklist();
		AccessorUtil.copyValue(this, mFChecklist);

		mFChecklist.setMFChecklistItemList(retrieveMFChecklistItem());
		return mFChecklist;
	}

	/**
	 * @see com.integrosys.cms.app.collateral.bus.type.property.marketfactor.EBMFChecklist#setValue
	 */
	public void setValue(IMFChecklist mFChecklist) throws CollateralException, VersionMismatchException {
		try {
			checkVersionMismatch(mFChecklist);
			AccessorUtil.copyValue(mFChecklist, this, EXCLUDE_METHOD);

			updateMFChecklistItem(mFChecklist.getMFChecklistItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (Exception e) {
			throw new CollateralException("Caught Exception!", e);
		}
	}

	/**
	 * Check the version of this MF Checklist.
	 * 
	 * @param mFChecklist of type IMFChecklist
	 * @throws VersionMismatchException if the entity version is invalid
	 */
	private void checkVersionMismatch(IMFChecklist mFChecklist) throws VersionMismatchException {
		if (getVersionTime() != mFChecklist.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + mFChecklist.getVersionTime());
		}
	}

	// ******************** Private methods for MF Checklist Item
	// ****************
	private IMFChecklistItem[] retrieveMFChecklistItem() {

		Collection c = getMFChecklistItemCMR();
		if ((null == c) || (c.size() == 0)) {
			return null;
		}
		else {

			Iterator i = c.iterator();
			ArrayList list = new ArrayList();

			while (i.hasNext()) {
				EBMFChecklistItemLocal theEjb = (EBMFChecklistItemLocal) i.next();
				IMFChecklistItem item = theEjb.getValue();

				DefaultLogger.debug(this, "retrieveMFChecklistItem, MFChecklistID=" + item.getMFParentID());
				DefaultLogger.debug(this, "retrieveMFChecklistItem, MFChecklistItemID=" + item.getMFItemID());
				list.add(item);
			}
			return (OBMFChecklistItem[]) list.toArray(new OBMFChecklistItem[0]);
		}
	}

	private void createMFChecklistItem(List createList) throws CollateralException {

		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getMFChecklistItemCMR();

		Iterator i = createList.iterator();
		try {
			EBMFChecklistItemLocalHome home = getEBMFChecklistItemLocalHome();
			while (i.hasNext()) {
				IMFChecklistItem ob = (IMFChecklistItem) i.next();
				EBMFChecklistItemLocal local = home.create(ob);
				c.add(local);
			}
		}
		catch (CollateralException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CollateralException("Caught Exception!", e);
		}
	}

	private void updateMFChecklistItem(IMFChecklistItem[] value) throws CollateralException {
		try {
			Collection c = getMFChecklistItemCMR();

			if (null == value) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all MF Checklist Item
					deleteMFChecklistItem(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				// create new records
				createMFChecklistItem(Arrays.asList(value));
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBMFChecklistItemLocal local = (EBMFChecklistItemLocal) i.next();

					long sid = local.getMFItemRef();
					boolean update = false;

					for (int j = 0; j < value.length; j++) {
						IMFChecklistItem newOB = value[j];

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
					IMFChecklistItem newOB = value[j];
					boolean found = false;

					while (i.hasNext()) {
						EBMFChecklistItemLocal local = (EBMFChecklistItemLocal) i.next();
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
				deleteMFChecklistItem(deleteList);
				createMFChecklistItem(createList);
			}
		}
		catch (CollateralException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CollateralException("Caught Exception: " + e.toString());
		}
	}

	private void deleteMFChecklistItem(List deleteList) throws CollateralException {

		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getMFChecklistItemCMR();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBMFChecklistItemLocal local = (EBMFChecklistItemLocal) i.next();
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CollateralException("Caught Exception!", e);
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
	 * Method to get EB Local Home for EBMFChecklistItem
	 * 
	 * @return EBMFChecklistItemLocalHome
	 * @throws CollateralException on errors
	 */
	protected EBMFChecklistItemLocalHome getEBMFChecklistItemLocalHome() throws CollateralException {
		EBMFChecklistItemLocalHome home = (EBMFChecklistItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_MF_CHECKLIST_ITEM_LOCAL_JNDI, EBMFChecklistItemLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new CollateralException("EBMFChecklistItemLocalHome is null!");
		}
	}

}