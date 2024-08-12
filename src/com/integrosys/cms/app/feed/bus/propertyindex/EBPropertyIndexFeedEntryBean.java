/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/propertyindex/EBPropertyIndexFeedEntryBean.java,v 1.6 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.feed.bus.propertyindex;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.6 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 * 
 * @ejb.bean name="EBPropertyIndexFeedEntry"
 *           jndi-name="EBPropertyIndexFeedEntryHome"
 *           local-jndi-name="EBPropertyIndexFeedEntryLocalHome"
 *           view-type="both" type="CMP" reentrant="false" cmp-version="2.x"
 *           schema="EBPropertyIndexFeedEntry"
 *           primkey-field="cmpPropertyIndexFeedEntryID"
 * @ejb.persistence table-name="CMS_PROPERTY_INDEX"
 * @ejb.transaction type="Required"
 */
public abstract class EBPropertyIndexFeedEntryBean implements EntityBean, IPropertyIndexFeedEntry {

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="PROPERTY_INDEX_ID"
	 * @ejb.pk-field
	 */
	public abstract Long getCmpPropertyIndexFeedEntryID();

	/**
	 * 
	 * @param anPropertyIndexFeedEntryID
	 */
	public abstract void setCmpPropertyIndexFeedEntryID(Long anPropertyIndexFeedEntryID);

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="FEED_REF"
	 */
	public abstract long getPropertyIndexFeedEntryRef();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="COUNTRY_ISO_CODE"
	 */
	public abstract String getCountryCode();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="LOCALITY"
	 */
	public abstract String getRegion();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="UPDATED_DATE"
	 */
	public abstract Date getLastUpdatedDate();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="TYPE"
	 */
	public abstract String getType();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="INDEX_VALUE"
	 */
	public abstract double getUnitPrice();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="CMS_VERSION_TIME"
	 */
	public abstract long getVersionTime();

	/**
	 * 
	 * @return
	 * @ejb.persistence column-name="DELETED_IND"
	 */
	public abstract String getCmpDeletedInd();

	public abstract void setCmpDeletedInd(String deletedInd);

	/**
	 * Create a document item Information
	 * @param aPropertyIndexFeedEntry
	 * @return Long - the document item ID (primary key)
	 * @throws CreateException on error
	 * 
	 * @ejb.create-method view-type="both"
	 */
	public Long ejbCreate(IPropertyIndexFeedEntry aPropertyIndexFeedEntry) throws CreateException {
		if (aPropertyIndexFeedEntry == null) {
			throw new CreateException("PropertyIndexFeedEntry is null!");
		}

		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			DefaultLogger.debug(this, "Sequence Name: " + getSequenceName());

			// Code commented out below.

			// if (aPropertyIndexFeedEntry.getPropertyIndexFeedEntryID() ==
			// com.integrosys
			// .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			// pk = Long.parseLong((new SequenceManager()).getSeqNum(
			// getSequenceName(), true));
			// } else {
			// pk = aPropertyIndexFeedEntry.getPropertyIndexFeedEntryID();
			// }

			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));

			if (aPropertyIndexFeedEntry.getPropertyIndexFeedEntryRef() == com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				setPropertyIndexFeedEntryRef(pk);
			}
			else {
				setPropertyIndexFeedEntryRef(aPropertyIndexFeedEntry.getPropertyIndexFeedEntryRef());
			}

			DefaultLogger.debug(this, "Item to be inserted: " + aPropertyIndexFeedEntry);
			AccessorUtil.copyValue(aPropertyIndexFeedEntry, this, EXCLUDE_METHOD);
			DefaultLogger.debug(this, "PK: " + pk);
			setPropertyIndexFeedEntryID(pk);
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
	 * @param aPropertyIndexFeedEntry
	 */
	public void ejbPostCreate(IPropertyIndexFeedEntry aPropertyIndexFeedEntry) {
	}

	/**
	 * Return the Interface representation of this object
	 * @return IPropertyIndexFeedEntry
	 * 
	 * @ejb.interface-method view-type="both"
	 */
	public IPropertyIndexFeedEntry getValue() {
		OBPropertyIndexFeedEntry value = new OBPropertyIndexFeedEntry();
		AccessorUtil.copyValue(this, value);

		return value;
	}

	/**
	 * Persist a PropertyIndexFeedEntry
	 * @param aPropertyIndexFeedEntry - IPropertyIndexFeedEntry
	 * @throws ConcurrentUpdateException if enctr concurrent update
	 * @ejb.interface-method view-type="both"
	 */
	public void setValue(IPropertyIndexFeedEntry aPropertyIndexFeedEntry) throws ConcurrentUpdateException {
		// if (getVersionTime() != aPropertyIndexFeedEntry.getVersionTime()) {
		// throw new ConcurrentUpdateException("Mismatch timestamp");
		// }

		DefaultLogger.debug(this, "before AccessorUtil.copyValue(...)");

		AccessorUtil.copyValue(aPropertyIndexFeedEntry, this, EXCLUDE_METHOD);

		DefaultLogger.debug(this, "after AccessorUtil.copyValue(...)");

		setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * @return long - the item ID
	 * 
	 */
	public long getPropertyIndexFeedEntryID() {
		if (getCmpPropertyIndexFeedEntryID() != null) {
			return getCmpPropertyIndexFeedEntryID().longValue();
		}

		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public boolean isDeletedInd() {

		String cmpDeletedInd = getCmpDeletedInd();
		if ((cmpDeletedInd != null) && cmpDeletedInd.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		else {
			return false;
		}
	}

	public void setDeletedInd(boolean deletedInd) {

		if (deletedInd) {
			setCmpDeletedInd(ICMSConstant.TRUE_VALUE);
		}
		else {
			setCmpDeletedInd(ICMSConstant.FALSE_VALUE);
		}
	}

	/**
	 * @param anPropertyIndexFeedEntryID
	 * 
	 */
	public void setPropertyIndexFeedEntryID(long anPropertyIndexFeedEntryID) {
		setCmpPropertyIndexFeedEntryID(new Long(anPropertyIndexFeedEntryID));
	}

	/**
	 * Get the name of the sequence to be used for the item id
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_FEED_PROPERTY_INDEX;
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

	private static final String[] EXCLUDE_METHOD = new String[] { "getPropertyIndexFeedEntryID",
			"getPropertyIndexFeedEntryRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

    public abstract void setType(String type);

    public abstract void setRegion(String region);

    public abstract void setCountryCode(String countryCode);

    public abstract void setUnitPrice(double unitPrice);

    public abstract void setLastUpdatedDate(Date lastUpdatedDate);

    public abstract void setPropertyIndexFeedEntryRef(long param);

    public abstract void setVersionTime(long versionTime);
}
