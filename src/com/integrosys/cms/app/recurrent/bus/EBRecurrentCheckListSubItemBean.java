/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListSubItemBean.java,v 1.4 2005/09/09 07:56:44 wltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//javax

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for checklist sub item
 * Information
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/09 07:56:44 $ Tag: $Name: $
 */
public abstract class EBRecurrentCheckListSubItemBean implements EntityBean, IRecurrentCheckListSubItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getSubItemID", "getSubItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBRecurrentCheckListSubItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPSubItemID();

	public abstract String getIsPrintReminderIndStr();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCMPCheckListItemID();

	public abstract long getSubItemRef();

	public abstract long getDeferredCount();

	public abstract void setCMPSubItemID(Long aCheckListItemID);

	public abstract void setIsPrintReminderIndStr(String aIsPrintReminderIndStr);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMPCheckListItemID(Long aCheckListItemID);

	public abstract void setDeferredCount(long deferredCount);

	public abstract Date getDeferredDate();

	public abstract Date getDocEndDate();

	public abstract Date getDueDate();

	public abstract int getFrequency();

	public abstract String getFrequencyUnit();

	public abstract int getGracePeriod();

	public abstract String getGracePeriodUnit();

	public abstract Date getReceivedDate();

	public abstract String getRemarks();

	public abstract String getStatus();

	public abstract Date getWaivedDate();

	public abstract void setDeferredDate(Date deferredDate);

	public abstract void setDocEndDate(Date docEndDate);

	public abstract void setDueDate(Date dueDate);

	public abstract void setFrequency(int frequency);

	public abstract void setFrequencyUnit(String frequencyUnit);

	public abstract void setGracePeriod(int gracePeriod);

	public abstract void setGracePeriodUnit(String gracePeriodUnit);

	public abstract void setReceivedDate(Date receivedDate);

	public abstract void setRemarks(String remarks);

	public abstract void setStatus(String status);

	public abstract void setSubItemRef(long subItemRef);

	public abstract void setWaivedDate(Date waivedDate);

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the checklist item ID
	 * @return long - the long value of the checklist item
	 */
	public long getSubItemID() {
		if (getCMPSubItemID() != null) {
			return getCMPSubItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public boolean getIsPrintReminderInd() {
		if ((getIsPrintReminderIndStr() != null) && getIsPrintReminderIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/*
	 * public String getStatus() { return null; }
	 */

	public long getDaysOverDue() {
		return Long.MIN_VALUE;
	}

	/**
	 * Helper method to get the delete indicator
	 * @return boolean - true if it is to be deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		if ((getIsDeletedIndStr() != null) && getIsDeletedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set the checklist item ID
	 * @param aCheckListItemID - long
	 */
	public void setSubItemID(long aCheckListItemID) {
		setCMPSubItemID(new Long(aCheckListItemID));
	}

	public void setIsPrintReminderInd(boolean anIsPrintReminderInd) {
		if (anIsPrintReminderInd) {
			setIsPrintReminderIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsPrintReminderIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set delete indicator
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		if (anIsDeletedInd) {
			setIsDeletedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsDeletedIndStr(ICMSConstant.FALSE_VALUE);
	}

	// *****************************************************
	/**
	 * Create a checklist item Information
	 * 
	 * @param aCheckListID - Long
	 * @param anICheckListItem of IRecurrentCheckListItem type
	 * @return Long - the checklist item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aCheckListItemID, IRecurrentCheckListSubItem anICheckListSubItem) throws CreateException {
		if (anICheckListSubItem == null) {
			throw new CreateException("ICheckListSubItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setSubItemID(pk);
			// DefaultLogger.debug(this, "PK: " + pk);
			// DefaultLogger.debug(this, "CheckListRef: " +
			// anICheckListSubItem.getSubItemRef());
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anICheckListSubItem
					.getSubItemRef()) {
				setSubItemRef(pk);
			}
			else {
				setSubItemRef(anICheckListSubItem.getSubItemRef());
			}
			AccessorUtil.copyValue(anICheckListSubItem, this, EXCLUDE_METHOD);
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param aCheckListID - the checkList item ID (parent of this checkList sub
	 *        item)
	 * @param anICheckListSubItem of IRecurrentCheckListSubItem type
	 */
	public void ejbPostCreate(Long aCheckListItemID, IRecurrentCheckListSubItem anICheckListSubItem)
			throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IRecurrentCheckListItem
	 */
	public IRecurrentCheckListSubItem getValue() {
		OBRecurrentCheckListSubItem value = new OBRecurrentCheckListSubItem();
		AccessorUtil.copyValue(this, value);
		return value;
	}

	/**
	 * Persist a checklist item information
	 * 
	 * @param anICheckListItem - IRecurrentCheckListItem
	 */
	public void setValue(IRecurrentCheckListSubItem anICheckListSubItem) {
		AccessorUtil.copyValue(anICheckListSubItem, this, EXCLUDE_METHOD);
	}

	// ************************************************************************
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
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_RECURRENT_CHECKLIST_SUB_ITEM;
	}

	public abstract String getActionParty();

	public abstract void setActionParty(String actionParty);
}