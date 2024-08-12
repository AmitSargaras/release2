/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerSearchCriteria.java,v 1.7 2005/01/27 08:49:25 lyng Exp $
 */
package com.integrosys.cms.ui.imageTag;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the Customer.
 * 
 * @author abhijit.rudrakshawar
 */
public class ImageTagSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 8232282562706453450L;

	// private String _legalName = null;
	private String legalID;

	private String customerName;

	// private String _subID = null;
	private ILimit[] limits;

	private ITrxContext ctx;

	private boolean checkDAP = false;

	private String leIDType;

	private String IDType;

	private String idNO;

	private String all;

	// limit profile type - default to Banking
	private String lmtProfileType = ICMSConstant.AA_TYPE_BANK;

	private boolean byLimit = false;

	private String frompage;

	private String aaNumber;


	private String issuedCountry;

	private String docBarCode;

	/**
	 * Default Constructor
	 */
	public ImageTagSearchCriteria() {
	}

	// Getters

	public String getAaNumber() {
		return aaNumber;
	}

	public String getAll() {
		return all;
	}

	public boolean getByLimit() {
		return byLimit;
	}

	/**
	 * Check if the data access must be protected.
	 * 
	 * @return boolean
	 */
	public boolean getCheckDAP() {
		return checkDAP;
	}

	/**
	 * Get business transaction context.
	 * 
	 * @return ITrxContext
	 */
	public ITrxContext getCtx() {
		return ctx;
	}

	// Setters

	/**
	 * Get the customer name
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return customerName;
	}

	public String getFrompage() {
		return frompage;
	}

	public String getIdNO() {
		return idNO;
	}

	/**
	 * Get the legal ID
	 * 
	 * @return String
	 */
	public String getLegalID() {
		return legalID;
	}

	public String getLeIDType() {
		return leIDType;
	}

	
	/**
	 * Get a list of limits.
	 * 
	 * @return ILimit[]
	 */
	public ILimit[] getLimits() {
		return limits;
	}

	public String getLmtProfileType() {
		return lmtProfileType;
	}

	public void setAaNumber(String aaNumber) {
		this.aaNumber = aaNumber;
	}

	public void setAll(String all) {
		this.all = all;
	}


	public void setByLimit(boolean byLimit) {
		this.byLimit = byLimit;
	}

	/**
	 * Set indicator to check if data access must be protected.
	 * 
	 * @param checkDAP
	 *            of type boolean
	 */
	public void setCheckDAP(boolean checkDAP) {
		this.checkDAP = checkDAP;
	}

	/**
	 * Set business transaction context.
	 * 
	 * @param ctx
	 *            of type ITrxContext
	 */
	public void setCtx(ITrxContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * Set the customer name
	 * 
	 * @param value
	 *            is of type String
	 */
	public void setCustomerName(String value) {
		customerName = value;
	}

	public void setFrompage(String frompage) {
		this.frompage = frompage;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	/**
	 * Set the legal ID
	 * 
	 * @param value
	 *            is of type String
	 */
	public void setLegalID(String value) {
		legalID = value;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	/**
	 * Set a list of limits.
	 * 
	 * @param limits
	 *            of type ILimit[]
	 */
	public void setLimits(ILimit[] limits) {
		this.limits = limits;
	}

	public void setLmtProfileType(String lmtProfileType) {
		this.lmtProfileType = lmtProfileType;
	}

	/**
	 * @return the iDType
	 */
	public String getIDType() {
		return IDType;
	}

	/**
	 * @param type
	 *            the iDType to set
	 */
	public void setIDType(String type) {
		IDType = type;
	}

	/**
	 * @return the issuedCountry
	 */
	public String getIssuedCountry() {
		return issuedCountry;
	}

	/**
	 * @param issuedCountry
	 *            the issuedCountry to set
	 */
	public void setIssuedCountry(String issuedCountry) {
		this.issuedCountry = issuedCountry;
	}

	public String getDocBarCode() {
		return docBarCode;
	}

	public void setDocBarCode(String docBarCode) {
		this.docBarCode = docBarCode;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@");
		buf.append(System.identityHashCode(this));
		buf.append("LE ID: ").append("[").append(legalID).append("]; ");
		buf.append("Customer Name: ").append("[").append(customerName).append(
				"]; ");
		buf.append("AA Number: ").append("[").append(aaNumber).append("]; ");
		buf.append("ID Number: ").append("[").append(idNO).append("]; ");
		buf.append("ID Number Type: ").append("[").append(IDType).append("]; ");
		buf.append("ID Issued Country: ").append("[").append(issuedCountry)
				.append("]; ");
		return buf.toString();

	}

}
