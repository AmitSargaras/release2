/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/document/IDocumentCollateral.java,v 1.4 2005/09/27 07:22:48 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.document;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Document.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/27 07:22:48 $ Tag: $Name: $
 */
public interface IDocumentCollateral extends ICollateral {
	/**
	 * Get date of document.
	 * 
	 * @return Date
	 */
	public Date getDocumentDate();

	/**
	 * Set date of document.
	 * 
	 * @param documentDate of type Date
	 */
	public void setDocumentDate(Date documentDate);

	/**
	 * Get document description.
	 * 
	 * @return String
	 */
	public String getDocumentDesc();

	/**
	 * Set document description.
	 * 
	 * @param documentDesc of type String
	 */
	public void setDocumentDesc(String documentDesc);

	/**
	 * Get document minimun amount.
	 * 
	 * @return Amount
	 */
	public Amount getMinAmount();

	/**
	 * Set document minimum amount.
	 * 
	 * @param minAmount of type Amount
	 */
	public void setMinAmount(Amount minAmount);

	/**
	 * Get document maximum amount.
	 * 
	 * @return Amount
	 */
	public Amount getMaxAmount();

	/**
	 * Set document maximum amount.
	 * 
	 * @param maxAmount of type Amount
	 */
	public void setMaxAmount(Amount maxAmount);

	/**
	 * Get document reference number.
	 * 
	 * @return String
	 */
	public String getReferenceNo();

	/**
	 * Set document reference number.
	 * 
	 * @param referenceNo of type String
	 */
	public void setReferenceNo(String referenceNo);

	/**
	 * Get document amount.
	 * 
	 * @return Amount
	 */
	public Amount getDocumentAmount();

	/**
	 * Set document amount.
	 * 
	 * @param documentAmount of type Amount
	 */
	public void setDocumentAmount(Amount documentAmount);
	
	public String getContractNumber();
	
	public void setContractNumber(String contractNumber);
	
	public Amount getContractAmt();
	
	public void setContractAmt(Amount contractAmt);
	
	public String getContractName();
	
	public void setContractName(String contractName);
	
	public Date getContractDate();
	
	public void setContractDate(Date contractDate);
	
	public String getIssuer();
	
	public void setIssuer(String issuer);
	
}
