/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBDocumentHeldItem.java,v 1.5 2006/07/31 01:57:25 czhou Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.IDocumentHeldItem;

//app

/**
 * This business object defines the list of methods required for document held
 * items.
 * 
 * @author $Author: czhou $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/07/31 01:57:25 $ Tag: $Name: $
 */
public class OBDocumentHeldItem implements IDocumentHeldItem {
	private long docNo = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docCode = null;

	private String docDesc = null;

	private String narration = null;

	private Boolean isInVault = null;

	private Boolean isExtCust = null;

	private String docStatus = null;

	private String custStatus = null;

	private Date docDate = null;

	private Date docExpiry = null;

	private long parentDocItemRefID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String parentDocItemDocCode;

	private boolean isShared = false;

	private String shareDetailsSummary = null; // R1.5 CR17

	public OBDocumentHeldItem() {
	}

	public long getDocNo() {
		return this.docNo;
	}

	public String getDocCode() {
		return this.docCode;
	}

	public String getDocDesc() {
		return this.docDesc;
	}

	public String getNarration() {
		return this.narration;
	}

	public long getParentDocItemRefID() {
		return this.parentDocItemRefID;
	}

	public String getParentDocItemDocCode() {
		return this.parentDocItemDocCode;
	}

	public Boolean isInVault() {
		return this.isInVault;
	}

	public Boolean isExtCust() {
		return this.isExtCust;
	}

	public String getDocStatus() {
		return this.docStatus;
	}

	public String getCustStatus() {
		return this.custStatus;
	}

	public Date getDocDate() {
		return this.docDate;
	}

	public Date getDocExpiry() {
		return this.docExpiry;
	}

	public void setDocNo(long docNo) {
		this.docNo = docNo;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public void setParentDocItemRefID(long parentItemRefID) {
		this.parentDocItemRefID = parentItemRefID;
	}

	public void setParentDocItemDocCode(String parentDocCode) {
		this.parentDocItemDocCode = parentDocCode;
	}

	public void setInVault(Boolean isInVault) {
		this.isInVault = isInVault;
	}

	public void setExtCust(Boolean isExtCust) {
		this.isExtCust = isExtCust;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public void setDocExpiry(Date docExpiry) {
		this.docExpiry = docExpiry;
	}

	public boolean isShared() {
		return isShared;
	}

	public void setIsShared(boolean shared) {
		isShared = shared;
	}

	public String getShareDetailsSummary() {
		return shareDetailsSummary;
	}

	public void setShareDetailsSummary(String shareDetailsSummary) {
		this.shareDetailsSummary = shareDetailsSummary;
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
