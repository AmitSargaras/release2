/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestItemBean.java,v 1.3 2003/09/12 17:41:09 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This entity bean represents the persistence for Deferral request item
 * Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/12 17:41:09 $ Tag: $Name: $
 */
public abstract class EBDeferralRequestItemBean implements EntityBean, IDeferralRequestItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getRequestItemID", "getRequestItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBDeferralRequestItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPRequestItemID();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCMPCheckListID();

	public abstract Long getCMPCheckListItemID();

	public abstract Long getCMPRequestID();

	public abstract long getRequestItemRef();

	public abstract void setCMPRequestItemID(Long aRequestItemID);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setCMPCheckListItemID(Long aCheckListItemID);

	public abstract void setCMPRequestID(Long aRequestID);

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the request item ID
	 * @return long - the long value of the request item
	 */
	public long getRequestItemID() {
		if (getCMPRequestItemID() != null) {
			return getCMPRequestItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
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

	public long getCheckListID() {
		if (getCMPCheckListID() != null) {
			return getCMPCheckListID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Not Implemented
	 */
	public ICheckListItem getCheckListItem() {
		return null;
	}

	/**
	 * Helper method to set the request item ID
	 * @param aRequestItemID - long
	 */
	public void setRequestItemID(long aRequestItemID) {
		setCMPRequestItemID(new Long(aRequestItemID));
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

	public void setCheckListID(long aCheckListID) {
		setCMPCheckListID(new Long(aCheckListID));
	}

	public void setCheckListItem(ICheckListItem anICheckListItem) {
		// do nothing
	}

	// *****************************************************
	/**
	 * Create a request item Information
	 * @param anIDeferralRequestItem of IDeferralRequestItem type
	 * @return Long - the Deferral request item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IDeferralRequestItem anIDeferralRequestItem) throws CreateException {
		if (anIDeferralRequestItem == null) {
			throw new CreateException("IDeferralRequestItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setRequestItemID(pk);
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anIDeferralRequestItem
					.getRequestItemRef()) {
				setRequestItemRef(pk);
			}
			else {
				setRequestItemRef(anIDeferralRequestItem.getRequestItemRef());
			}
			AccessorUtil.copyValue(anIDeferralRequestItem, this, EXCLUDE_METHOD);
			long checkListItemID = anIDeferralRequestItem.getCheckListItem().getCheckListItemID();
			setCMPCheckListItemID(new Long(checkListItemID));
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param anIDeferralRequestItem of IDeferralRequestItem type
	 */
	public void ejbPostCreate(IDeferralRequestItem anIDeferralRequestItem) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IDeferralRequestItem
	 */
	public IDeferralRequestItem getValue() {
		IDeferralRequestItem value = new OBDeferralRequestItem();
		AccessorUtil.copyValue(this, value);
		ICheckListItem item = new OBCheckListItem();
		item.setCheckListItemID(getCMPCheckListItemID().longValue());
		value.setCheckListItem(item);
		return value;

	}

	/**
	 * Persist a Deferral request item information
	 * 
	 * @param anIDeferralRequestItem - IDeferralRequestItem
	 */
	public void setValue(IDeferralRequestItem anIDeferralRequestItem) {
		AccessorUtil.copyValue(anIDeferralRequestItem, this, EXCLUDE_METHOD);
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
		return ICMSConstant.SEQUENCE_DEFERRAL_REQUEST_ITEM;
	}
}