/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/common/OBDocumentExpiryInfo.java,v 1.6 2006/03/06 12:28:34 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.common;

import java.util.Date;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/06 12:28:34 $
 * Tag: $Name:  $
 */

/**
 * Bean to hold the information that are used for sending notifications Same as
 * the ones used in
 */
public class OBDocumentExpiryInfo extends OBEventInfo {

	private String documentType;

	private Date documentDate;

	private Date expiryDate;

	private int daysDue;

	private String expiryCode;

	private long checkListItemID;

	private long checklistItemRef;

	private String docType;

	private String documentCode;

	private String docRef;

	private Date docDefferalExpiryDate;

	private String sciSecurityID;

	private String securitySubType;

	private Date securityMaturityDate;

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocRef() {
		return docRef;
	}

	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}

	public Date getDocDefferalExpiryDate() {
		return docDefferalExpiryDate;
	}

	public void setDocDefferalExpiryDate(Date docDefferalExpiryDate) {
		this.docDefferalExpiryDate = docDefferalExpiryDate;
	}

	public long getCheckListItemID() {
		return checkListItemID;
	}

	public void setCheckListItemID(long checkListItemID) {
		this.checkListItemID = checkListItemID;
	}

	public long getCheckListItemRef() {
		return checklistItemRef;
	}

	public void setChecklistItemRef(long checklistItemRef) {
		this.checklistItemRef = checklistItemRef;
	}

	public String getExpiryCode() {
		return expiryCode;

	}

	public void setExpiryCode(String expiryCode) {
		this.expiryCode = expiryCode;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public int getDaysDue() {
		return daysDue;
	}

	public void setDaysDue(int daysDue) {
		this.daysDue = daysDue;
	}

	public String getSciSecurityID() {
		return sciSecurityID;
	}

	public void setSciSecurityID(String sciSecurityID) {
		this.sciSecurityID = sciSecurityID;
		setSecurityID(sciSecurityID);
	}

	public String getSecuritySubType() {
		return this.securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public Date getSecurityMaturityDate() {
		return this.securityMaturityDate;
	}

	public void setSecurityMaturityDate(Date securityMaturityDate) {
		this.securityMaturityDate = securityMaturityDate;
	}
}
