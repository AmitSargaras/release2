/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianDocItem.java,v 1.7 2005/08/29 10:25:16 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.app.checklist.bus.ICheckListItem;

/**
 * custodian doc item interface
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/08/29 10:25:16 $ Tag: $Name: $
 */
public interface ICustodianDocItem extends Serializable {

	// db fields
	public long getCustodianDocItemID();

	public long getCustodianDocID();

	public long getCheckListItemRefID();

	public String getStatus();

	public String getReason();

	public Date getLastUpdateDate();

	public void setCustodianDocItemID(long aCustodianDocItemID);

	public void setCustodianDocID(long aCustodianDocID);

	public void setCheckListItemRefID(long aCheckListID);

	public void setStatus(String aStatus);

	public void setReason(String aReason);

	public void setLastUpdateDate(Date lastUpdateDate);

    public void setSecEnvelopeBarcode(String secEnvelopeBarcode);

    public void setCustodianDocItemBarcode(String custodianDocItemBarcode);

	// helper method from checklist item
	public ICheckListItem getCheckListItem();

	public void setCheckListItem(ICheckListItem anICheckListItem);

	public String getDocNo();

	public String getDocDescription();

	public String getDocRef();

	public String getFormNo();

	public Date getDocDate();

	public Date getDocExpiryDate();

	public String getDocRemarks();

	public String getDisplayStatus(String actualItemStatus, String stageItemStatus);

	// CR34
	public void setReversalDate(Date reverseDate);

	public Date getReversalDate();

	// methods to populate maker and checker id and trx dates
	/**
	 * Get the maker ID that made a change to the item status.
	 * @return long
	 */
	public long getMakerID();

	/**
	 * Set the maker ID that made a change to the item status.
	 * @param makerID - long
	 */
	public void setMakerID(long makerID);

	/**
	 * Get the checker ID that approve the change to the item status.
	 * @return long
	 */
	public long getCheckerID();

	/**
	 * Set the checker ID that approve the change to the item status.
	 * @param checkerID - long
	 */
	public void setCheckerID(long checkerID);

	/**
	 * Get the trx date on which the maker made a change to the item status.
	 * @return Date
	 */
	public Date getMakerTrxDate();

	/**
	 * Set the trx date on which the maker made a change to the item status.
	 * @param trxDate
	 */
	public void setMakerTrxDate(Date trxDate);

	/**
	 * Get the trx date on which the checker approved the change made to the
	 * item status.
	 * @return
	 */
	public Date getCheckerTrxDate();

	/**
	 * Set the trx date on which the checker approved the change made to the
	 * item status.
	 * @param trxDate
	 */
	public void setCheckerTrxDate(Date trxDate);

	/**
	 * Check if the item status has changed in the current transaction.
	 * @return boolean
	 */
	public boolean isStatusChanged();

	/**
	 * Get display date for custodian trx date
	 * @return Date
	 */
	public Date getDisplayTrxDate();

    public String getSecEnvelopeBarcode();

    public String getCustodianDocItemBarcode();
}
