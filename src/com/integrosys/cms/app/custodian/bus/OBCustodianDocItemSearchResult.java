/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBCustodianDocItemSearchResult.java,v 1.5 2005/10/24 09:42:32 wltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.CheckListCustodianHelper;

/**
 * Implementation class for the ICustodianDocItemSearchResult interface
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/10/24 09:42:32 $ Tag: $Name: $
 */
public class OBCustodianDocItemSearchResult implements ICustodianDocItemSearchResult {
	private long custodianDocItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docNo = null;

	private long docItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docDescription = null;

	private String status = null;

	private String itemStatus = null;

	private String stageItemStatus = null;

	private String docType = null;

	private long checkListItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String cPCCustStatus = null;

	private Date cpcCustDate = null;

	private Date stageCPCDate = null;

	private Date trxDate = null;

	// CR130 : disaplay doc item narration in lodgement/withdrawal list
	private String docItemNarration = null;

	private Date docDate = null;

	private Date docExpiryDate = null;

    private String SecEnvAdd = null;

    private String SecEnvCab = null;

    private String SecEnvDrw = null;

    private String SecEnvBarcode = null;

    /**
	 * Default Constructor
	 */
	public OBCustodianDocItemSearchResult() {
	}

	/**
	 * Getter methods
	 */
	public long getCustodianDocItemID() {
		return custodianDocItemID;
	}

	public String getDocNo() {
		return docNo;
	}

	public long getDocItemRef() {
		return docItemRef;
	}

	public String getDocDescription() {
		return docDescription;
	}

	/**
	 * Get current custodian doc item status.
	 * @return String - current item status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Get actual checklist item status.
	 * @return String - actual item status
	 */
	public String getItemStatus() {
		return itemStatus;
	}

	/**
	 * Get stage checklist doc item status.
	 * @return String - stage item status
	 */
	public String getStageItemStatus() {
		return stageItemStatus;
	}

	public String getCPCDisplayStatus() {
		return CheckListCustodianHelper.getCheckListCPCStatus(getItemStatus(), getStageItemStatus());
	}

	public String getDocType() {
		return docType;
	}

	public long getCheckListItemID() {
		return this.checkListItemID;
	}

	public String getCPCCustStatus() {
		return this.cPCCustStatus;
	}

	public Date getCPCCustDate() {
		return this.cpcCustDate;
	}

	public Date getStageCPCDate() {
		return this.stageCPCDate;
	}

	public Date getTrxDate() {
		return this.trxDate;
	}

	/**
	 * Get checklist item narration.
	 * @return String
	 */
	public String getDocItemNarration() {
		return docItemNarration;
	}

	/**
	 * Get display custodian status for checklist item.
	 * @return String - display status
	 */
	public String getDisplayStatus() {
		// the individual item status is no longer determmined by the trx status
		// since items of a checklist has been consolidated into one transaction
		// return
		// CheckListCustodianHelper.getCheckListCustodianStatus(getCPCCustStatus
		// (), getTrxStatus());
		return CheckListCustodianHelper.getCheckListCustodianStatus(getCPCCustStatus(), getStatus());
	}


    /**
	 * Setter methods
	 */
	public void setCustodianDocItemID(long custodianDocItemID) {
		this.custodianDocItemID = custodianDocItemID;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public void setDocItemRef(long aDocItemRef) {
		this.docItemRef = aDocItemRef;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	/**
	 * Get current custodian doc item status.
	 * @param status - String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get actual checklist item status.
	 * @param itemStatus - String
	 */
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	/**
	 * Get stage checklist item status.
	 * @param stageItemStatus - String
	 */
	public void setStageItemStatus(String stageItemStatus) {
		this.stageItemStatus = stageItemStatus;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setCheckListItemID(long aCheckListItemID) {
		this.checkListItemID = aCheckListItemID;
	}

	public void setCPCCustStatus(String aCPCCustStatus) {
		this.cPCCustStatus = aCPCCustStatus;
	}

	public void setCPCCustDate(Date aCPCCustDate) {
		this.cpcCustDate = aCPCCustDate;
	}

	public void setStageCPCDate(Date stageCPCDate) {
		this.stageCPCDate = stageCPCDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public void setDocItemNarration(String narration) {
		this.docItemNarration = narration;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Date getDocExpiryDate() {
		return docExpiryDate;
	}

	public void setDocExpiryDate(Date docExpiryDate) {
		this.docExpiryDate = docExpiryDate;
	}
    public String getSecEnvAdd() {
		return SecEnvAdd;
	}

    public void setSecEnvAdd(String SecEnvAdd) {
		this.SecEnvAdd = SecEnvAdd;
	}
         public String getSecEnvCab() {
		return SecEnvCab;
	}

    public void setSecEnvCab(String SecEnvCab) {
		this.SecEnvCab = SecEnvCab;
	}
    public String getSecEnvDrw() {
		return SecEnvDrw;
	}

    public void setSecEnvDrw(String SecEnvDrw) {
		this.SecEnvDrw = SecEnvDrw;
	}

    public String getSecEnvBarcode() {
		return SecEnvBarcode;
	}

    public void setSecEnvBarcode(String SecEnvBarcode) {
		this.SecEnvBarcode = SecEnvBarcode;
	}


	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
