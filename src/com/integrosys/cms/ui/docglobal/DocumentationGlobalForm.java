package com.integrosys.cms.ui.docglobal;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * @author $Author: sathish $<br>
 * 
 * @version $Revision: 1.3 $
 * 
 * @since $Date: 2003/08/21 08:37:44 $
 * 
 *        Tag: $Name: $
 */

public class DocumentationGlobalForm extends TrxContextForm implements Serializable {

	private static final long serialVersionUID = 5376222669405803665L;

	private String itemID = "";

	private String itemCode = "";

	private String itemDesc = "";

	private String expDate = "";

	private String remarks = "";

	private String docVersion = "";
	
	private String tenureCount = "";
	
	private String tenureType = "";
	
	private String deprecated = "";
	
	private String status = "";
	

	private String skipImgTag = "";
	
	private String cersai = "";
	
	private String critical = "";
	
	private String mandatory = "";
	
	private String statementType = "";
	
	private String go = "";
	
	private String isRecurrent="";
	
	private String rating="";
	
	private String segment="";
	
	private String totalSancAmt="";
	
	private String classification="";
	
	private String guarantor="";
	
/*	private String allIsRecurrent = "";

	private String appendIsRecurrent = "";*/
	
	private String allRating = "";

	private String appendRating = "";
	
	private String allSegment = "";

	private String appendSegment = "";
	
	/*private String allTotalSancAmt = "";

	private String appendTotalSancAmt = "";*/
	
	private String allClassification = "";

	private String appendClassification = "";
	
	/*private String allStatementType = "";

	private String appendStatementType = "";*/
	
	private String syncRemark = null;
	private String syncStatus = null;
	private String syncAction = null; 
	private String itemType = null;
	
	
	
	public String getIsRecurrent() {
		return isRecurrent;
	}

	public String getAllRating() {
		return allRating;
	}

	public void setAllRating(String allRating) {
		this.allRating = allRating;
	}

	public String getAppendRating() {
		return appendRating;
	}

	public void setAppendRating(String appendRating) {
		this.appendRating = appendRating;
	}

	public String getAllSegment() {
		return allSegment;
	}

	public void setAllSegment(String allSegment) {
		this.allSegment = allSegment;
	}

	public String getAppendSegment() {
		return appendSegment;
	}

	public void setAppendSegment(String appendSegment) {
		this.appendSegment = appendSegment;
	}

	public String getAllClassification() {
		return allClassification;
	}

	public void setAllClassification(String allClassification) {
		this.allClassification = allClassification;
	}

	public String getAppendClassification() {
		return appendClassification;
	}

	public void setAppendClassification(String appendClassification) {
		this.appendClassification = appendClassification;
	}

	public void setIsRecurrent(String isRecurrent) {
		this.isRecurrent = isRecurrent;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getGo() {
		return go;
	}

	public void setGo(String go) {
		this.go = go;
	}

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getTotalSancAmt() {
		return totalSancAmt;
	}

	public void setTotalSancAmt(String totalSancAmt) {
		this.totalSancAmt = totalSancAmt;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	
	
	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	

	public String getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(String tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}
	
	public String getSkipImgTag() {
		return skipImgTag;
	}

	public void setSkipImgTag(String skipImgTag) {
		this.skipImgTag = skipImgTag;
	}

	private String isPreApprove = "N";

	private String loanApplicationType = "";
	
	private String[] loanApplicationList;
	
	private String appendLoanList;
	
	

	public String getDocVersion() {
		return docVersion;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getIsPreApprove() {
		return isPreApprove;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getItemDesc() {
		return itemDesc;
	}

	public String getItemID() {
		return itemID;
	}

	public String getLoanApplicationType() {
		return loanApplicationType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void reset() {
	}

	public void setDocVersion(String docVersion) {
		this.docVersion = docVersion;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public void setIsPreApprove(String isPreApprove) {
		this.isPreApprove = isPreApprove;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public void setLoanApplicationType(String loanApplicationType) {
		this.loanApplicationType = loanApplicationType;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String[] getLoanApplicationList() {
		return loanApplicationList;
	}

	public void setLoanApplicationList(String[] loanApplicationList) {
		this.loanApplicationList = loanApplicationList;
	}
	
	public String getAppendLoanList() {
		return appendLoanList;
	}

	public void setAppendLoanList(String appendLoanList) {
		this.appendLoanList = appendLoanList;
	}
	

	public String[][] getMapper() {
		String[][] input = {

		{ "documentItem", "com.integrosys.cms.ui.docglobal.DocumentationGlobalCheckListMapper" },

		{ "stgDocumentItem", "com.integrosys.cms.ui.docglobal.StagingDocumentationGlobalCheckListMapper" },

		{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },

		};

		return input;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("DocumentationGlobalForm [");
		buf.append("itemCode=");
		buf.append(itemCode);
		buf.append(", itemDesc=");
		buf.append(itemDesc);
		buf.append(", itemID=");
		buf.append(itemID);
		buf.append(", docVersion=");
		buf.append(docVersion);
		buf.append(", expDate=");
		buf.append(expDate);
		buf.append(", loanApplicationType=");
		buf.append(loanApplicationType);
		buf.append(", isPreApprove=");
		buf.append(isPreApprove);
		buf.append(", remarks=");
		buf.append(remarks);
		buf.append("]");
		return buf.toString();
	}

	/**
	 * @return the syncRemark
	 */
	public String getSyncRemark() {
		return syncRemark;
	}

	/**
	 * @param syncRemark the syncRemark to set
	 */
	public void setSyncRemark(String syncRemark) {
		this.syncRemark = syncRemark;
	}

	/**
	 * @return the syncStatus
	 */
	public String getSyncStatus() {
		return syncStatus;
	}

	/**
	 * @param syncStatus the syncStatus to set
	 */
	public void setSyncStatus(String syncStatus) {
		this.syncStatus = syncStatus;
	}

	/**
	 * @return the syncAction
	 */
	public String getSyncAction() {
		return syncAction;
	}

	/**
	 * @param syncAction the syncAction to set
	 */
	public void setSyncAction(String syncAction) {
		this.syncAction = syncAction;
	}

	/**
	 * @return the itemType
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	private String oldItemCode = "";

	public String getOldItemCode() {
		return oldItemCode;
	}

	public void setOldItemCode(String oldItemCode) {
		this.oldItemCode = oldItemCode;
	}

	public String getCersai() {
		return cersai;
	}

	public void setCersai(String cersai) {
		this.cersai = cersai;
	}

	public String getCritical() {
		return critical;
	}

	public void setCritical(String critical) {
		this.critical = critical;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

}
