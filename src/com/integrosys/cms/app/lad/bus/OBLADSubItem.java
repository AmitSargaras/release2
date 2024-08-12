/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/diary/bus/OBDiaryItem.java,v 1.7 2005/09/30 11:32:23 jtan Exp $
 */
package com.integrosys.cms.app.lad.bus;

//app

import java.util.Date;

/**
 * 
@author $Author: Abhijit R $
*/
public class OBLADSubItem implements ILADSubItem {
	
	


	/**
	 * constructor
	 */
	public OBLADSubItem() {
		
	}
	 	private long id;
	    private String doc_description;
	    private Date doc_date;    
	    private Date expiry_date;    
	    private long doc_item_id;   
	    private long doc_sub_item_id;    
		private String status;
		private String category;
		private long versionTime;
		/*Added by Pramod For LAD CR*/
		
		private String docChklistVersion;
		private String docChklistAmt;
		private long chklistDocItemId;
		//private String isDisplay;
		private long chklistDocId;
		private String type;
		private String docStatus;
		
		
		public String getDocStatus() {
			return docStatus;
		}
		public void setDocStatus(String docStatus) {
			this.docStatus = docStatus;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public long getChklistDocId() {
			return chklistDocId;
		}
		public void setChklistDocId(long chklistDocId) {
			this.chklistDocId = chklistDocId;
		}
		/*public String getIsDisplay() {
			return isDisplay;
		}
		public void setIsDisplay(String isDisplay) {
			this.isDisplay = isDisplay;
		}*/
		public long getChklistDocItemId() {
			return chklistDocItemId;
		}
		public void setChklistDocItemId(long chklistDocItemId) {
			this.chklistDocItemId = chklistDocItemId;
		}
		public String getDocChklistVersion() {
			return docChklistVersion;
		}
		public void setDocChklistVersion(String docChklistVersion) {
			this.docChklistVersion = docChklistVersion;
		}
		public String getDocChklistAmt() {
			return docChklistAmt;
		}
		public void setDocChklistAmt(String docChklistAmt) {
			this.docChklistAmt = docChklistAmt;
		}
		//Ended By Pramod
		public long getDoc_sub_item_id() {
			return doc_sub_item_id;
		}
		public void setDoc_sub_item_id(long docSubItemId) {
			doc_sub_item_id = docSubItemId;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public long getId() {
			return id;
		}
		public void setId(long id) {
			this.id = id;
		}
	
		public String getDoc_description() {
			return doc_description;
		}
		public void setDoc_description(String docDescription) {
			doc_description = docDescription;
		}
		public Date getDoc_date() {
			return doc_date;
		}
		public void setDoc_date(Date docDate) {
			doc_date = docDate;
		}
		public Date getExpiry_date() {
			return expiry_date;
		}
		public void setExpiry_date(Date expiryDate) {
			expiry_date = expiryDate;
		}
		public long getDoc_item_id() {
			return doc_item_id;
		}
		public void setDoc_item_id(long docItemId) {
			doc_item_id = docItemId;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public long getVersionTime() {
			return versionTime;
		}
		public void setVersionTime(long versionTime) {
			this.versionTime = versionTime;
		}
		
		

	
}