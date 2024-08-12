/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/message/castor/sci/security/DocumentSecurity.java,v 1.3 2003/12/08 07:13:37 lyng Exp $
 */
package com.integrosys.cms.host.eai.security.bus.document;

import java.util.Date;

import com.integrosys.cms.host.eai.StandardCode;
import com.integrosys.cms.host.eai.security.bus.ApprovedSecurity;
import com.integrosys.cms.host.eai.support.MessageDate;

/**
 * This class represents approved security of type Document.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/12/08 07:13:37 $ Tag: $Name: $
 */
public class DocumentSecurity extends ApprovedSecurity {

	private static final long serialVersionUID = 8808507063140638752L;

	private Date iSDADate;

	private StandardCode iSDAProductDescription;

	private Date iFEMADate;

	private StandardCode iFEMAProductDescription;

	private Date iCOMDate;

	private StandardCode iCOMProductDescription;

	private Date documentDate;

	private String documentReferenceNo;

	private Double documentAmount;

	private Double minimumAmt;

	private Double maximumAmt;

	private String documentDescription;

	private String projectName;

	private Date awardedDate;

	private String letterOfInstructionFlag;

	private String letterOfUndertakingFlag;

	private String blanketAssignment;

	private StandardCode deedAssignmentType;

	private String minimumAmtCcy;

	private String maximumAmtCcy;

	private String documentAmtCcy;

	private Date dateOfLeaseAgreement;

	private Date dateOfExchangeControl;

	private String collateralName;

	private Double guranteeAmount;

	private Date exchangeCtrlObtained;

	private String issuer;

	private StandardCode leaseType;

	private String leaseRentalAgreement;

	private String leaseLimitation;

	private StandardCode propertyType;

	private String lotsLocation;

	private StandardCode titleType;

	private String titleNumber;

	private Double buybackValue;

	private Double guaranteeAmount;

	private String contractNumber;

	private String contractName;

	private Double contractAmount;

	private String contractDate;

	private String currency;

	/**
	 * Default constructor.
	 */
	public DocumentSecurity() {
		super();
	}

	public String getAwardedDate() {
		return MessageDate.getInstance().getString(awardedDate);
	}

	public String getBlanketAssignment() {
		return blanketAssignment;
	}

	public Double getBuybackValue() {
		return buybackValue;
	}

	public String getCollateralName() {
		return collateralName;
	}

	public Double getContractAmount() {
		return contractAmount;
	}

	public String getContractDate() {
		return contractDate;
	}

	public String getContractName() {
		return contractName;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public Date getDateOfExchangeControl() {
		return dateOfExchangeControl;
	}

	public Date getDateOfLeaseAgreement() {
		return dateOfLeaseAgreement;
	}

	public StandardCode getDeedAssignmentType() {
		return deedAssignmentType;
	}

	public Double getDocumentAmount() {
		return documentAmount;
	}

	public String getDocumentAmtCcy() {
		return documentAmtCcy;
	}

	public String getDocumentDate() {
		return MessageDate.getInstance().getString(documentDate);
	}

	public String getDocumentDescription() {
		return documentDescription;
	}

	public String getDocumentReferenceNo() {
		return documentReferenceNo;
	}

	public Date getExchangeCtrlObtained() {
		return exchangeCtrlObtained;
	}

	public Double getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public Double getGuranteeAmount() {
		return guranteeAmount;
	}

	public String getICOMDate() {
		return MessageDate.getInstance().getString(iCOMDate);
	}

	public StandardCode getICOMProductDescription() {
		return iCOMProductDescription;
	}

	public String getIFEMADate() {
		return MessageDate.getInstance().getString(iFEMADate);
	}

	public StandardCode getIFEMAProductDescription() {
		return iFEMAProductDescription;
	}

	public String getISDADate() {
		return MessageDate.getInstance().getString(iSDADate);
	}

	public StandardCode getISDAProductDescription() {
		return iSDAProductDescription;
	}

	public String getIssuer() {
		return issuer;
	}

	public Date getJDOAwardedDate() {
		return awardedDate;
	}

	public Date getJDOContractDate() {
		return MessageDate.getInstance().getDate(contractDate);
	}

	public Date getJDODocumentDate() {
		return documentDate;
	}

	public Date getJDOICOMDate() {
		return iCOMDate;
	}

	public Date getJDOIFEMADate() {
		return iFEMADate;
	}

	public Date getJDOISDADate() {
		return iSDADate;
	}

	public String getLeaseLimitation() {
		return leaseLimitation;
	}

	public String getLeaseRentalAgreement() {
		return leaseRentalAgreement;
	}

	public StandardCode getLeaseType() {
		return leaseType;
	}

	public String getLetterOfInstructionFlag() {
		return letterOfInstructionFlag;
	}

	public String getLetterOfUndertakingFlag() {
		return letterOfUndertakingFlag;
	}

	public String getLotsLocation() {
		return lotsLocation;
	}

	public Double getMaximumAmt() {
		return maximumAmt;
	}

	public String getMaximumAmtCcy() {
		return maximumAmtCcy;
	}

	public Double getMinimumAmt() {
		return minimumAmt;
	}

	public String getMinimumAmtCcy() {
		return minimumAmtCcy;
	}

	public String getProjectName() {
		return projectName;
	}

	public StandardCode getPropertyType() {
		return propertyType;
	}

	public String getTitleNumber() {
		return titleNumber;
	}

	public StandardCode getTitleType() {
		return titleType;
	}

	public void setAwardedDate(String awardedDate) {
		this.awardedDate = MessageDate.getInstance().getDate(awardedDate);
	}

	public void setBlanketAssignment(String blanketAssignment) {
		this.blanketAssignment = blanketAssignment;
	}

	public void setBuybackValue(Double buybackValue) {
		this.buybackValue = buybackValue;
	}

	public void setCollateralName(String collateralName) {
		this.collateralName = collateralName;
	}

	public void setContractAmount(Double contractAmount) {
		this.contractAmount = contractAmount;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDateOfExchangeControl(Date dateOfExchangeControl) {
		this.dateOfExchangeControl = dateOfExchangeControl;
	}

	public void setDateOfLeaseAgreement(Date dateOfLeaseAgreement) {
		this.dateOfLeaseAgreement = dateOfLeaseAgreement;
	}

	public void setDeedAssignmentType(StandardCode deedAssignmentType) {
		this.deedAssignmentType = deedAssignmentType;
	}

	public void setDocumentAmount(Double documentAmount) {
		this.documentAmount = documentAmount;
	}

	public void setDocumentAmtCcy(String documentAmtCcy) {
		this.documentAmtCcy = documentAmtCcy;
	}

	public void setDocumentDate(String documentDate) {
		this.documentDate = MessageDate.getInstance().getDate(documentDate);
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public void setDocumentReferenceNo(String documentReferenceNo) {
		this.documentReferenceNo = documentReferenceNo;
	}

	public void setExchangeCtrlObtained(Date exchangeCtrlObtained) {
		this.exchangeCtrlObtained = exchangeCtrlObtained;
	}

	public void setGuaranteeAmount(Double guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public void setGuranteeAmount(Double guranteeAmount) {
		this.guranteeAmount = guranteeAmount;
	}

	public void setICOMDate(String iCOMDate) {
		this.iCOMDate = MessageDate.getInstance().getDate(iCOMDate);
	}

	public void setICOMProductDescription(StandardCode iCOMProductDescription) {
		this.iCOMProductDescription = iCOMProductDescription;
	}

	public void setIFEMADate(String iFEMADate) {
		this.iFEMADate = MessageDate.getInstance().getDate(iFEMADate);
	}

	public void setIFEMAProductDescription(StandardCode iFEMAProductDescription) {
		this.iFEMAProductDescription = iFEMAProductDescription;
	}

	public void setISDADate(String iSDADate) {
		this.iSDADate = MessageDate.getInstance().getDate(iSDADate);
	}

	public void setISDAProductDescription(StandardCode iSDAProductDescription) {
		this.iSDAProductDescription = iSDAProductDescription;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public void setJDOAwardedDate(Date awardedDate) {
		this.awardedDate = awardedDate;
	}

	public void setJDOContractDate(Date contractDate) {
		this.contractDate = MessageDate.getInstance().getString(contractDate);
	}

	public void setJDODocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public void setJDOICOMDate(Date iCOMDate) {
		this.iCOMDate = iCOMDate;
	}

	public void setJDOIFEMADate(Date iFEMADate) {
		this.iFEMADate = iFEMADate;
	}

	public void setJDOISDADate(Date iSDADate) {
		this.iSDADate = iSDADate;
	}

	public void setLeaseLimitation(String leaseLimitation) {
		this.leaseLimitation = leaseLimitation;
	}

	public void setLeaseRentalAgreement(String leaseRentalAgreement) {
		this.leaseRentalAgreement = leaseRentalAgreement;
	}

	public void setLeaseType(StandardCode leaseType) {
		this.leaseType = leaseType;
	}

	public void setLetterOfInstructionFlag(String letterOfInstructionFlag) {
		this.letterOfInstructionFlag = letterOfInstructionFlag;
	}

	public void setLetterOfUndertakingFlag(String letterOfUndertakingFlag) {
		this.letterOfUndertakingFlag = letterOfUndertakingFlag;
	}

	public void setLotsLocation(String lotsLocation) {
		this.lotsLocation = lotsLocation;
	}

	public void setMaximumAmt(Double maximumAmt) {
		this.maximumAmt = maximumAmt;
	}

	public void setMaximumAmtCcy(String maximumAmtCcy) {
		this.maximumAmtCcy = maximumAmtCcy;
	}

	public void setMinimumAmt(Double minimumAmt) {
		this.minimumAmt = minimumAmt;
	}

	public void setMinimumAmtCcy(String minimumAmtCcy) {
		this.minimumAmtCcy = minimumAmtCcy;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setPropertyType(StandardCode propertyType) {
		this.propertyType = propertyType;
	}

	public void setTitleNumber(String titleNumber) {
		this.titleNumber = titleNumber;
	}

	public void setTitleType(StandardCode titleType) {
		this.titleType = titleType;
	}

}
