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
public class OBLAD implements ILAD {
	
	


	/**
	 * constructor
	 */
	public OBLAD() {
		
	}
    private long id;
   
    private long lad_id;
    private String lad_name;
    private Date lad_due_date;    
    private Date generation_date;     
    private Date receive_date;     
    private Date document_date;  
    private Date expiry_date;    
    private long lad_counter;
    private long limit_profile_id=0l;
	private String status;
	private String isOperationAllowed;
	private long versionTime;
	private ILADItem[] iladItem;
	private long checklistId;
	
	
	
    public long getChecklistId() {
		return checklistId;
	}
	public void setChecklistId(long checklistId) {
		this.checklistId = checklistId;
	}
	public String getIsOperationAllowed() {
		return isOperationAllowed;
	}
	public void setIsOperationAllowed(String isOperationAllowed) {
		this.isOperationAllowed = isOperationAllowed;
	}
	public long getLimit_profile_id() {
		return limit_profile_id;
	}
	public void setLimit_profile_id(long limitProfileId) {
		limit_profile_id = limitProfileId;
	}
	public ILADItem[] getIladItem() {
		return iladItem;
	}
	public void setIladItem(ILADItem[] iladItem) {
		this.iladItem = iladItem;
	}
	public long getVersionTime() {
		return versionTime;
	}
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}
	public long getLad_id() {
		return lad_id;
	}
	public void setLad_id(long ladId) {
		lad_id = ladId;
	}
	public String getLad_name() {
		return lad_name;
	}
	public void setLad_name(String ladName) {
		lad_name = ladName;
	}
	public Date getLad_due_date() {
		return lad_due_date;
	}
	public void setLad_due_date(Date ladDueDate) {
		lad_due_date = ladDueDate;
	}
	public Date getGeneration_date() {
		return generation_date;
	}
	public void setGeneration_date(Date generationDate) {
		generation_date = generationDate;
	}
	public Date getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(Date receiveDate) {
		receive_date = receiveDate;
	}
	public Date getDocument_date() {
		return document_date;
	}
	public void setDocument_date(Date documentDate) {
		document_date = documentDate;
	}
	public Date getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(Date expiryDate) {
		expiry_date = expiryDate;
	}
	public long getLad_counter() {
		return lad_counter;
	}
	public void setLad_counter(long ladCounter) {
		lad_counter = ladCounter;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	


	
}