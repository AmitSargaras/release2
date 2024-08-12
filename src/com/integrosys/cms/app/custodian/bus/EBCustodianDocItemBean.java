/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/EBCustodianDocItemBean.java,v 1.7 2005/08/29 10:25:16 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.util.Date;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.EBCheckListItemHome;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * custodian doc item entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/29 10:25:16 $ Tag: $Name: $
 */
public abstract class EBCustodianDocItemBean implements EntityBean, ICustodianDocItem {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String SEQUENCE_NAME = ICMSConstant.SEQUENCE_CUSTODIAN_DOC_ITEM;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCustodianDocID", "getCustodianDocItemID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCustodianDocItemID", "getCustodianDocID" };

	/**
	 * Default Constructor
	 */
	public EBCustodianDocItemBean() {
	}

	// getter cmp fields
	public abstract Long getCMPCustodianDocItemID();

	public abstract Long getCMPCustodianDocID();

	public abstract Long getCMPCheckListItemRefID();

	public abstract String getCMPStatus();

	public abstract String getCMPReason();

	public abstract Date getCMPLastUpdateDate();

	public abstract Long getCMPMakerID();

	public abstract Long getCMPCheckerID();

    public abstract String getCMPSecEnvelopeBarcode();

    public abstract String getCMPCustodianDocItemBarcode();

	// setter cmp fields
	public abstract void setCMPCustodianDocItemID(Long aCustodianDocItemID);

	public abstract void setCMPCustodianDocID(Long aCustodianDocID);

	public abstract void setCMPCheckListItemRefID(Long aCheckListID);

	public abstract void setCMPStatus(String aStatus);

	public abstract void setCMPReason(String aReason);

	public abstract void setCMPLastUpdateDate(Date aDate);

	public abstract void setCMPMakerID(Long makerID);

	public abstract void setCMPCheckerID(Long checkerID);

    public abstract void setCMPSecEnvelopeBarcode(String secenvelopeBarcode);

    public abstract void setCMPCustodianDocItemBarcode(String custodianDocItemBarcode);

	// CR34
	public abstract void setCMPReversalDate(Date reversalDate);

	public abstract Date getCMPReversalDate();

	public Date getReversalDate() {
		return getCMPReversalDate();
	}

	public void setReversalDate(Date reversalDate) {
		setCMPReversalDate(reversalDate);
	}

	public long getCustodianDocItemID() {
		return this.getCMPCustodianDocItemID().longValue();
	}

	public long getCustodianDocID() {
		if (this.getCMPCustodianDocID() != null) {
			return this.getCMPCustodianDocID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getCheckListItemRefID() {
		if (this.getCMPCheckListItemRefID() != null) {
			return this.getCMPCheckListItemRefID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getStatus() {
		if (this.getCMPStatus() != null) {
			return this.getCMPStatus();
		}
		return "";
	}

	public String getReason() {
		if (this.getCMPReason() != null) {
			return this.getCMPReason();
		}
		return "";
	}

	public Date getLastUpdateDate() {
		if (this.getCMPLastUpdateDate() != null) {
			return this.getCMPLastUpdateDate();
		}
		return null;
	}

	public String getDisplayStatus(String actualItemStatus, String stagingItemStatus) {
		return null;
	}

	public void setCustodianDocItemID(long aCustodianDocItemID) {
		this.setCMPCustodianDocItemID(new Long(aCustodianDocItemID));
	}

	public void setCustodianDocID(long aCustodianDocID) {
		this.setCMPCustodianDocID(new Long(aCustodianDocID));
	}

	public void setCheckListItemRefID(long aCheckListID) {
		this.setCMPCheckListItemRefID(new Long(aCheckListID));
	}

	public void setStatus(String aStatus) {
		this.setCMPStatus(aStatus);
	}

	public void setReason(String aReason) {
		this.setCMPReason(aReason);
	}

	public void setLastUpdateDate(Date aDate) {
		this.setCMPLastUpdateDate(aDate);
	}

    public void setCustodianDocItemBarcode(String custodianDocItemBarcode) {
		this.setCMPCustodianDocItemBarcode(custodianDocItemBarcode);
	}

    public void setSecEnvelopeBarcode(String secEnvelopeBarcode) {
		this.setCMPSecEnvelopeBarcode(secEnvelopeBarcode);
	}

	// help methods implemented from ICustodianItem
	public ICheckListItem getCheckListItem() {
		try {
			long checkListItemRefID = (getCMPCheckListItemRefID() != null) ? getCMPCheckListItemRefID().longValue()
					: ICMSConstant.LONG_INVALID_VALUE;
			return (checkListItemRefID != ICMSConstant.LONG_INVALID_VALUE) ? getEBCheckListItemHome()
					.findByCheckListItemRef(checkListItemRefID).getValue() : null;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setCheckListItem(ICheckListItem anICheckListItem) {
		// do nothing;
	}

	public Date getDisplayTrxDate() {
		return null;
	}

	public String getDocNo() {
		return null;
	}

	public String getDocDescription() {
		return null;
	}

	public String getDocRef() {
		return null;
	}

	public String getFormNo() {
		return null;
	}

	public Date getDocDate() {
		return null;
	}

	public Date getDocExpiryDate() {
		return null;
	}

	public String getDocRemarks() {
		return null;
	}

    public String getCustodianDocItemBarcode() {
		if (this.getCMPCustodianDocItemBarcode() != null) {
			return this.getCMPCustodianDocItemBarcode();
		}
		return null;
	}

    public String getSecEnvelopeBarcode() {
		if (this.getCMPSecEnvelopeBarcode() != null) {
			return this.getCMPSecEnvelopeBarcode();
		}
        return null;
	}

	/**
	 * Get the maker ID that made a change to the item status.
	 * @return long
	 */
	public long getMakerID() {
		return (this.getCMPMakerID() != null) ? getCMPMakerID().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Set the maker ID that made a change to the item status.
	 * @param makerID - long
	 */
	public void setMakerID(long makerID) {
		if (makerID != ICMSConstant.LONG_INVALID_VALUE) {
			setCMPMakerID(new Long(makerID));
		}
	}

	/**
	 * Get the checker ID that approve the change to the item status.
	 * @return long
	 */
	public long getCheckerID() {
		return (this.getCMPCheckerID() != null) ? getCMPCheckerID().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Set the checker ID that approve the change to the item status.
	 * @param checkerID - long
	 */
	public void setCheckerID(long checkerID) {
		if (checkerID != ICMSConstant.LONG_INVALID_VALUE) {
			setCMPCheckerID(new Long(checkerID));
		}
	}
    

	/**
	 * Check if the item status has changed in the current transaction.
	 * @return boolean
	 */
	public boolean isStatusChanged() {
		// derived value. not retrieved from persistence
		return false;
	}

	/**
	 * Create a custodian doc item record
	 * @param anICustodianDocItem - ICustodianDocItem
	 * @return Long - the custodian doc item ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(ICustodianDocItem anICustodianDocItem) throws CreateException {
		try {
			String custodianDocItemID = (new SequenceManager()).getSeqNum(SEQUENCE_NAME, true);
			AccessorUtil.copyValue(anICustodianDocItem, this, EXCLUDE_METHOD_CREATE);
			this.setCMPCustodianDocItemID(new Long(custodianDocItemID));
			return new Long(custodianDocItemID);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new CreateException("Caught unknown exception: " + ex.toString());
		}
	}

	public void ejbPostCreate(ICustodianDocItem anICustodianDocItem) throws CreateException {
		// do nothing
	}

	/**
	 * Sets the custodian doc item object.
	 * @param anICustodianDocItem - ICustodianDocItem
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(ICustodianDocItem anICustodianDocItem) throws ConcurrentUpdateException {
		AccessorUtil.copyValue(anICustodianDocItem, this, EXCLUDE_METHOD_UPDATE);
	}

	/**
	 * Return a custodian document item object
	 * @return ICustodianDocItem - the object containing the custodian document
	 *         object
	 */
	public ICustodianDocItem getValue() throws CustodianException {
		OBCustodianDocItem value = new OBCustodianDocItem();
		AccessorUtil.copyValue(this, value, null);
		return value;
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
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Method to get EB Home for checkList item
	 */
	protected EBCheckListItemHome getEBCheckListItemHome() throws CheckListException {
		EBCheckListItemHome home = (EBCheckListItemHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_JNDI, EBCheckListItemHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBCheckListItem is null!");
	}

	public abstract Date getCheckerTrxDate();

	public abstract Date getMakerTrxDate();

	public abstract void setCheckerTrxDate(Date trxDate);

	public abstract void setMakerTrxDate(Date trxDate);
}
