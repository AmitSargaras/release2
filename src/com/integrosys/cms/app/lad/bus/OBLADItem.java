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
public class OBLADItem implements ILADItem {
	
	


	/**
	 * constructor
	 */
	public OBLADItem() {
		
	}
	 	private long id;
	    private long lad_id;
	    private String doc_description;
	    private Date doc_date;    
	    private Date expiry_date;    
	    private long doc_item_id;     
		private String status;
		private String category;
		private long versionTime;
		private ILADSubItem[] iladSubItem;
		
		
		
		
		public ILADSubItem[] getIladSubItem() {
			return iladSubItem;
		}
		public void setIladSubItem(ILADSubItem[] iladSubItem) {
			this.iladSubItem = iladSubItem;
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
		public long getLad_id() {
			return lad_id;
		}
		public void setLad_id(long ladId) {
			lad_id = ladId;
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