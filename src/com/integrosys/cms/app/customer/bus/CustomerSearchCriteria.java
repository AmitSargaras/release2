/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/CustomerSearchCriteria.java,v 1.7 2005/01/27 08:49:25 lyng Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the possible search criteria
 * required by the Customer.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/01/27 08:49:25 $ Tag: $Name: $
 */
public class CustomerSearchCriteria extends SearchCriteria {

	private static final long serialVersionUID = 8232282562706453450L;

	// private String _legalName = null;
	private String legalID = null;

	private String customerName = null;

	// private String _subID = null;
	private ILimit[] limits;

	private ITrxContext ctx;

	private boolean checkDAP = false;

	private String leIDType = null;

	private String IDType = null;

	private String idNO = null;

	private String all = null;

	// limit profile type - default to Banking
	private String lmtProfileType = ICMSConstant.AA_TYPE_BANK;

	private boolean byLimit = false;

	private String frompage;

	private String aaNumber;

	// private String idCountry = null;

	private String issuedCountry;
	
	private String docBarCode;
	
	private String facilitySystem;
	
	private String facilitySystemID;

	private String customerStatus= ICMSConstant.CUSTOMER_STATUS_ACTIVE;
	
	private String documentDateType = "";
	private String fromDateDoc = "";
	private String toDatedoc = "";
	private String fromAmount = "";
	private String toAmount = "";
	private String filterPartyName = "";
	private String facilityDocName = "";
	private String securityDocName = "";
	private String othersDocsName = "";
	private String statementDocName = "";
	private String camDocName = "";
	private String docNamed = "";
	private String categoryImage = "";
	private String typeOfdocImage = "";
	
	
	public String getTypeOfdocImage() {
		return typeOfdocImage;
	}

	public void setTypeOfdocImage(String typeOfdocImage) {
		this.typeOfdocImage = typeOfdocImage;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public String getDocumentDateType() {
		return documentDateType;
	}

	public void setDocumentDateType(String documentDateType) {
		this.documentDateType = documentDateType;
	}

	public String getFromDateDoc() {
		return fromDateDoc;
	}

	public void setFromDateDoc(String fromDateDoc) {
		this.fromDateDoc = fromDateDoc;
	}

	public String getToDatedoc() {
		return toDatedoc;
	}

	public void setToDatedoc(String toDatedoc) {
		this.toDatedoc = toDatedoc;
	}

	public String getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(String fromAmount) {
		this.fromAmount = fromAmount;
	}

	public String getToAmount() {
		return toAmount;
	}

	public void setToAmount(String toAmount) {
		this.toAmount = toAmount;
	}

	public String getFilterPartyName() {
		return filterPartyName;
	}

	public void setFilterPartyName(String filterPartyName) {
		this.filterPartyName = filterPartyName;
	}

	public String getFacilityDocName() {
		return facilityDocName;
	}

	public void setFacilityDocName(String facilityDocName) {
		this.facilityDocName = facilityDocName;
	}

	public String getSecurityDocName() {
		return securityDocName;
	}

	public void setSecurityDocName(String securityDocName) {
		this.securityDocName = securityDocName;
	}

	public String getOthersDocsName() {
		return othersDocsName;
	}

	public void setOthersDocsName(String othersDocsName) {
		this.othersDocsName = othersDocsName;
	}

	public String getStatementDocName() {
		return statementDocName;
	}

	public void setStatementDocName(String statementDocName) {
		this.statementDocName = statementDocName;
	}

	public String getCamDocName() {
		return camDocName;
	}

	public void setCamDocName(String camDocName) {
		this.camDocName = camDocName;
	}

	public String getDocNamed() {
		return docNamed;
	}

	public void setDocNamed(String docNamed) {
		this.docNamed = docNamed;
	}

	public String getCustomerStatus() {
		return customerStatus;
	}

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

	public String getFacilitySystem() {
		return facilitySystem;
	}

	public void setFacilitySystem(String facilitySystem) {
		this.facilitySystem = facilitySystem;
	}

	public String getFacilitySystemID() {
		return facilitySystemID;
	}

	public void setFacilitySystemID(String facilitySystemID) {
		this.facilitySystemID = facilitySystemID;
	}
	

	/**
	 * Default Constructor
	 */
	public CustomerSearchCriteria() {
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
	 * Get the legal name
	 * 
	 * @return String
	 */
	// public String getLegalName() {
	// return _legalName;
	// }
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
	 * Get the sub-profile ID
	 * 
	 * @return String
	 */
	// public String getSubProfileID() {
	// return _subID;
	// }
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

	// public String getIdCountry() {
	// return idCountry;
	// }
	//
	// public void setIdCountry(String idCountry) {
	// this.idCountry = idCountry;
	// }

	public void setByLimit(boolean byLimit) {
		this.byLimit = byLimit;
	}

	/**
	 * Set indicator to check if data access must be protected.
	 * 
	 * @param checkDAP of type boolean
	 */
	public void setCheckDAP(boolean checkDAP) {
		this.checkDAP = checkDAP;
	}

	/**
	 * Set business transaction context.
	 * 
	 * @param ctx of type ITrxContext
	 */
	public void setCtx(ITrxContext ctx) {
		this.ctx = ctx;
	}

	/**
	 * Set the customer name
	 * 
	 * @param value is of type String
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
	 * Set the legal name
	 * 
	 * @param value is of type String
	 */
	// public void setLegalName(String value) {
	// _legalName = value;
	// }
	/**
	 * Set the legal ID
	 * 
	 * @param value is of type String
	 */
	public void setLegalID(String value) {
		this.legalID = value;
	}

	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	/**
	 * Set the sub-profile ID
	 * 
	 * @param value is of type String
	 */
	// public void setSubProfileID(String value) {
	// _subID = value;
	// }
	/**
	 * Set a list of limits.
	 * 
	 * @param limits of type ILimit[]
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
	 * @param type the iDType to set
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
	 * @param issuedCountry the issuedCountry to set
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
		buf.append("Customer Name: ").append("[").append(customerName).append("]; ");
		buf.append("AA Number: ").append("[").append(aaNumber).append("]; ");
		buf.append("ID Number: ").append("[").append(idNO).append("]; ");
		buf.append("ID Number Type: ").append("[").append(IDType).append("]; ");
		buf.append("ID Issued Country: ").append("[").append(issuedCountry).append("]; ");
		buf.append("NItems to be shown: ").append("[").append(getNItems()).append("] ");

		return buf.toString();

	}

}
