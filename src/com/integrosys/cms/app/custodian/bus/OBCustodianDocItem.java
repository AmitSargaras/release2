/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBCustodianDocItem.java,v 1.11 2005/11/16 02:39:59 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CheckListCustodianHelper;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * custodian doc item object
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2005/11/16 02:39:59 $ Tag: $Name: $
 */
public class OBCustodianDocItem implements ICustodianDocItem {

	private long custodianDocItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long custodianDocID = ICMSConstant.LONG_INVALID_VALUE;

	private long checkListItemRefID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = null;

	private String reason = null;

	private ICheckListItem item = null;

	private Date lastUpdateDate = null;

	private Date reversalDate = null;

	// CR34
	// fields to keep track of the maker and checker id and trx date for changes
	// made to item status
	// this is to ease report generation
	private long makerID = ICMSConstant.LONG_INVALID_VALUE;;

	private long checkerID = ICMSConstant.LONG_INVALID_VALUE;;

	private Date makerTrxDate = null;

	private Date checkerTrxDate = null;

    private String secEnvelopeBarcode = null;

    private String custodianDocItemBarcode = null;

	private static final String PENDING_RELODGE_STATE_SUFFIX = "RELODGE";

	private static final String LODGED_STATE = "LODGED";

	private static HashMap CUSTODIAN_DOC_ITEM_STATUS_MAP = null;

	static {
		CUSTODIAN_DOC_ITEM_STATUS_MAP = new HashMap();
		CUSTODIAN_DOC_ITEM_STATUS_MAP.put("PENDING_TEMP_UPLIFT", "TEMP_UPLIFTED");
		CUSTODIAN_DOC_ITEM_STATUS_MAP.put("PENDING_PERM_UPLIFT", "PERM_UPLIFTED");
		//CUSTODIAN_DOC_ITEM_STATUS_MAP.put("PENDING_LODGE", "LODGED");  -- for alliance customization
		CUSTODIAN_DOC_ITEM_STATUS_MAP.put("PENDING_RELODGE", "LODGED");
        //-- Added for alliance customization --
        CUSTODIAN_DOC_ITEM_STATUS_MAP.put("ALLOW_LODGE", "LODGED");
        CUSTODIAN_DOC_ITEM_STATUS_MAP.put("ALLOW_TEMP_UPLIFT", "TEMP_UPLIFTED");
        CUSTODIAN_DOC_ITEM_STATUS_MAP.put("ALLOW_PERM_UPLIFT", "PERM_UPLIFTED");
        CUSTODIAN_DOC_ITEM_STATUS_MAP.put("ALLOW_RELODGE", "LODGED");
	}

	/**
	 * Default Constructor
	 */
	public OBCustodianDocItem() {
	}

	/**
	 * Getter methods
	 */

	/**
	 * Get display date for custodian trx date
	 * @return Date
	 */
	public Date getDisplayTrxDate() {
		return CheckListCustodianHelper.getCustodianTrxDate(this);
	}

	public long getCustodianDocItemID() {
		return this.custodianDocItemID;
	}

	public long getCustodianDocID() {
		return this.custodianDocID;
	}

	public long getCheckListItemRefID() {
		return (getCheckListItem() != null) ? getCheckListItem().getCheckListItemRef() : this.checkListItemRefID;
	}

	public ICheckListItem getCheckListItem() {
		return this.item;
	}

	public String getStatus() {
		return this.status;
	}

	public String getReason() {
		return this.reason;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	/**
	 * Take into consideration the CPC Trx Date
	 */
	public Date getItemTransactionDate() {
		Date trxDate = null;
		if ((getCheckListItem() != null) && (getCheckListItem().getLastUpdateDate() != null)
				&& (getLastUpdateDate() != null)) {
			Date cpcTrxDate = getCheckListItem().getLastUpdateDate();
			trxDate = (cpcTrxDate.compareTo(getLastUpdateDate()) >= 0) ? cpcTrxDate : getLastUpdateDate();
		}
		return getLastUpdateDate();
	}

	public String getDocNo() {
		return ((getCheckListItem() != null) && (getCheckListItem().getItem() != null)) ? getCheckListItem().getItem()
				.getItemCode() : null;
	}

	public String getDocDescription() {
		return ((getCheckListItem() != null) && (getCheckListItem().getItem() != null)) ? getCheckListItem().getItem()
				.getItemDesc() : null;
	}

	public String getDocRef() {
		return (getCheckListItem() != null) ? getCheckListItem().getDocRef() : null;
	}

	public String getFormNo() {
		return (getCheckListItem() != null) ? getCheckListItem().getFormNo() : null;
	}

	public Date getDocDate() {
		return (getCheckListItem() != null) ? getCheckListItem().getDocDate() : null;
	}

	public Date getDocExpiryDate() {
		return (getCheckListItem() != null) ? getCheckListItem().getExpiryDate() : null;
	}

	public String getDocRemarks() {
		return (getCheckListItem() != null) ? getCheckListItem().getRemarks() : null;
	}

    public String getSecEnvelopeBarcode(){
        return secEnvelopeBarcode;
    }

    public  String getCustodianDocItemBarcode(){
        return custodianDocItemBarcode;
    }

	/**
	 * Setter methods
	 */
	public void setCustodianDocItemID(long aCustodianDocItemID) {
		this.custodianDocItemID = aCustodianDocItemID;
	}

	public void setCustodianDocID(long aCustodianDocID) {
		this.custodianDocID = aCustodianDocID;
	}

	public void setCheckListItemRefID(long aCheckListItemRefID) {
		this.checkListItemRefID = aCheckListItemRefID;
	}

	public void setCheckListItem(ICheckListItem anICheckListItem) {
		this.item = anICheckListItem;
	}

	public void setReason(String aReason) {
		this.reason = aReason;
	}

	public void setLastUpdateDate(Date aDate) {
		this.lastUpdateDate = aDate;
	}

	public void setStatus(String aStatus) {
		this.status = aStatus;
	}

	/**
	 * Helper method to get the correct custodian item display status
	 * 
	 * @param actualItemStatus - String
	 * @param stageItemStatus - String
	 */
	public String getDisplayStatus(String actualItemStatus, String stageItemStatus) {        
		if ((actualItemStatus == null) || (stageItemStatus == null)) {
			return stageItemStatus;
		}
		String stageAction = getStageAction(stageItemStatus);
		if (!actualItemStatus.startsWith(stageAction)) {
			return stageItemStatus;
		}
		if (getCheckListItem() != null) {
			String cPCCustodianStatus = getCheckListItem().getCPCCustodianStatus();
			return CheckListCustodianHelper.getCheckListCustodianStatus(cPCCustodianStatus, actualItemStatus);
		}
		return getStatus();
	}

	// methods to populate maker and checker id and trx dates
	/**
	 * Get the maker ID that made a change to the item status.
	 * @return long
	 */
	public long getMakerID() {
		return makerID;
	}

	/**
	 * Set the maker ID that made a change to the item status.
	 * @param makerID - long
	 */
	public void setMakerID(long makerID) {
		this.makerID = makerID;
	}

	/**
	 * Get the checker ID that approve the change to the item status.
	 * @return long
	 */
	public long getCheckerID() {
		return checkerID;
	}

	/**
	 * Set the checker ID that approve the change to the item status.
	 * @param checkerID - long
	 */
	public void setCheckerID(long checkerID) {
		this.checkerID = checkerID;
	}

	/**
	 * Get the trx date on which the maker made a change to the item status.
	 * @return Date
	 */
	public Date getMakerTrxDate() {
		return makerTrxDate;
	}

	/**
	 * Set the trx date on which the maker made a change to the item status.
	 * @param trxDate
	 */
	public void setMakerTrxDate(Date trxDate) {
		this.makerTrxDate = trxDate;
	}

	/**
	 * Get the trx date on which the checker approved the change made to the
	 * item status.
	 * @return
	 */
	public Date getCheckerTrxDate() {
		return checkerTrxDate;
	}

	/**
	 * Set the trx date on which the checker approved the change made to the
	 * item status.
	 * @param trxDate
	 */
	public void setCheckerTrxDate(Date trxDate) {
		this.checkerTrxDate = trxDate;
	}

	/**
	 * Check if the item status has changed in the current transaction.
	 * @return boolean
	 */
	public boolean isStatusChanged() {
		if (getStatus() == null) {
			return false;
		}
		return (getStatus().startsWith(ICMSConstant.STATE_ITEM_PENDING_PREFIX));
	}

	/**
	 * Helper method to get the stage action.
	 * 
	 * @param stageItemStatus
	 */
	private String getStageAction(String stageItemStatus) {
		int idx = stageItemStatus.indexOf(ICMSConstant.STATE_ITEM_PENDING_PREFIX);
		if (idx != -1) {
			String stageAction = stageItemStatus.substring(ICMSConstant.STATE_ITEM_PENDING_PREFIX.length());
			return (stageAction.equals(PENDING_RELODGE_STATE_SUFFIX)) ? LODGED_STATE : stageAction;
		}
		return stageItemStatus;
	}

	/**
	 * Helper method to convert custodian doc item from pending status to actual
	 * status.
	 * 
	 * @param item - ICustodianDocItem
	 */
	public static void convertPendingStatus(ICustodianDocItem item) {
		String currState = item.getStatus();
		String nextState = (String) CUSTODIAN_DOC_ITEM_STATUS_MAP.get(currState);
		item.setStatus((nextState != null) ? nextState : currState);
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * @return Returns the reversalDate.
	 */
	public Date getReversalDate() {
		return reversalDate;
	}

	/**
	 * @param reversalDate The reversalDate to set.
	 */
	public void setReversalDate(Date reversalDate) {
		this.reversalDate = reversalDate;
	}

    public void setSecEnvelopeBarcode(String secEnvelopeBarcode){
        this.secEnvelopeBarcode = secEnvelopeBarcode;
    }

    public void setCustodianDocItemBarcode(String custodianDocItemBarcode){
        this.custodianDocItemBarcode = custodianDocItemBarcode;
    }
}