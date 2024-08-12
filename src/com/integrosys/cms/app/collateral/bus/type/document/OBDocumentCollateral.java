/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/OBDocumentCollateral.java,v 1.5 2005/09/27 07:22:48 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Document entity.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/09/27 07:22:48 $ Tag: $Name: $
 */
public class OBDocumentCollateral extends OBCollateral implements IDocumentCollateral {
	private Date documentDate;

	private String documentDesc;

	private Amount minAmount;

	private Amount maxAmount;

	private String referenceNo;

	private Amount documentAmount;
	
	private String issuer;
	
	private String contractNumber;
	
	private Amount contractAmt;
	
	private String contractName;
	
	private Date contractDate;

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Amount getContractAmt() {
		return contractAmt;
	}

	public void setContractAmt(Amount contractAmt) {
		this.contractAmt = contractAmt;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	/**
	 * Default Constructor.
	 */
	public OBDocumentCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDocumentCollateral
	 */
	public OBDocumentCollateral(IDocumentCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get date of document.
	 * 
	 * @return Date
	 */
	public Date getDocumentDate() {
		return documentDate;
	}

	/**
	 * Set date of document.
	 * 
	 * @param documentDate of type Date
	 */
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	/**
	 * Get document description.
	 * 
	 * @return String
	 */
	public String getDocumentDesc() {
		return documentDesc;
	}

	/**
	 * Set document description.
	 * 
	 * @param documentDesc of type String
	 */
	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	/**
	 * Get document minimun amount.
	 * 
	 * @return Amount
	 */
	public Amount getMinAmount() {
		return minAmount;
	}

	/**
	 * Set document minimum amount.
	 * 
	 * @param minAmount of type Amount
	 */
	public void setMinAmount(Amount minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * Get document maximum amount.
	 * 
	 * @return Amount
	 */
	public Amount getMaxAmount() {
		return maxAmount;
	}

	/**
	 * Set document maximum amount.
	 * 
	 * @param maxAmount of type Amount
	 */
	public void setMaxAmount(Amount maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * Get document reference number.
	 * 
	 * @return String
	 */
	public String getReferenceNo() {
		return referenceNo;
	}

	/**
	 * Set document reference number.
	 * 
	 * @param referenceNo of type String
	 */
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	/**
	 * Get document amount.
	 * 
	 * @return Amount
	 */
	public Amount getDocumentAmount() {
		return documentAmount;
	}

	/**
	 * Set document amount.
	 * 
	 * @param documentAmount of type Amount
	 */
	public void setDocumentAmount(Amount documentAmount) {
		this.documentAmount = documentAmount;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBDocumentCollateral)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

}