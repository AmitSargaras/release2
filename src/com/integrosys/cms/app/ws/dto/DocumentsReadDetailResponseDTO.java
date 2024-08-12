/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.app.ws.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Describe this class. Purpose: To set get and set method for the value needed
 * by AA Detail Description: Have set and get method to store the screen value
 * and get the value from other command class
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date: $ Tag: $Name$
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "DocumentReadDetailResponseInfo")
public class DocumentsReadDetailResponseDTO{
	
	@XmlElement(name = "documentStatus")
	private String documentStatus;
	
	@XmlElement(name = "documentCode")
	private String documentCode;
	
	@XmlElement(name = "documentDescription")
	private String documentDescription;
	
	@XmlElement(name = "documentAmount")
	private String documentAmount;
	
	@XmlElement(name = "hdfcAmount")
	private String hdfcAmount;
	
	@XmlElement(name = "documentCurrency")
	private String documentCurrency;
	
	@XmlElement(name = "documentDate")
	private String documentDate;
	
	@XmlElement(name = "documentOriginaltargeDate")
	private String documentOriginaltargeDate;
	
	@XmlElement(name = "documentExpiryDate")
	private String documentExpiryDate;
	
	@XmlElement(name = "documentRemarks")
	private String documentRemarks;
	
	@XmlElement(name = "documentItemId")
	private String documentItemId;
	
	@XmlElement(name = "documentDeferredDate")
	private String documentDeferredDate;
	
	@XmlElement(name = "documentWaiveDate")
	private String documentWaiveDate;
	
	@XmlElement(name = "documentReceiveDate")
	private String documentReceiveDate;

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getDocumentCode() {
		return documentCode;
	}

	public void setDocumentCode(String documentCode) {
		this.documentCode = documentCode;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public String getDocumentAmount() {
		return documentAmount;
	}

	public void setDocumentAmount(String documentAmount) {
		this.documentAmount = documentAmount;
	}

	public String getHdfcAmount() {
		return hdfcAmount;
	}

	public void setHdfcAmount(String hdfcAmount) {
		this.hdfcAmount = hdfcAmount;
	}

	public String getDocumentCurrency() {
		return documentCurrency;
	}

	public void setDocumentCurrency(String documentCurrency) {
		this.documentCurrency = documentCurrency;
	}

	public String getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = documentDate;
	}

	public String getDocumentOriginaltargeDate() {
		return documentOriginaltargeDate;
	}

	public void setDocumentOriginaltargeDate(String documentOriginaltargeDate) {
		this.documentOriginaltargeDate = documentOriginaltargeDate;
	}

	public String getDocumentExpiryDate() {
		return documentExpiryDate;
	}

	public void setDocumentExpiryDate(String documentExpiryDate) {
		this.documentExpiryDate = documentExpiryDate;
	}

	public String getDocumentRemarks() {
		return documentRemarks;
	}

	public void setDocumentRemarks(String documentRemarks) {
		this.documentRemarks = documentRemarks;
	}

	public String getDocumentItemId() {
		return documentItemId;
	}

	public void setDocumentItemId(String documentItemId) {
		this.documentItemId = documentItemId;
	}

	public String getDocumentDeferredDate() {
		return documentDeferredDate;
	}

	public void setDocumentDeferredDate(String documentDeferredDate) {
		this.documentDeferredDate = documentDeferredDate;
	}

	public String getDocumentWaiveDate() {
		return documentWaiveDate;
	}

	public void setDocumentWaiveDate(String documentWaiveDate) {
		this.documentWaiveDate = documentWaiveDate;
	}

	public String getDocumentReceiveDate() {
		return documentReceiveDate;
	}

	public void setDocumentReceiveDate(String documentReceiveDate) {
		this.documentReceiveDate = documentReceiveDate;
	}

}