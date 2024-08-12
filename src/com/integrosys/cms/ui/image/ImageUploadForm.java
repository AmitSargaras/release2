package com.integrosys.cms.ui.image;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * @author $Name:  $<br>
 * 
 * @version $Revision: 0 $
 * 
 * @since $Date: 2003/08/21 08:37:44 $
 * 
 * Tag: $Name: $
 */

public class ImageUploadForm extends TrxContextForm implements Serializable {

	private String userID = "";

	private String subProfileID = "";

	private String legalName = "";

	private String customerName = "";

	private String legalID = "";

	private String leIDType = "";

	private String idNO = "";

	private String gobutton = "";

	private String all = "";
	/** Added by Anil	 */
	private String category = "";
	private String hasSubfolder = "";
	private String subfolderName = "";
	
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
	private String typeOfdocImage = "";
	
	
	public String getTypeOfdocImage() {
		return typeOfdocImage;
	}

	public void setTypeOfdocImage(String typeOfdocImage) {
		this.typeOfdocImage = typeOfdocImage;
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

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the hasSubfolder
	 */
	public String getHasSubfolder() {
		return hasSubfolder;
	}

	/**
	 * @param hasSubfolder the hasSubfolder to set
	 */
	public void setHasSubfolder(String hasSubfolder) {
		this.hasSubfolder = hasSubfolder;
	}

	/**
	 * @return the subfolderName
	 */
	public String getSubfolderName() {
		return subfolderName;
	}

	/**
	 * @param subfolderName the subfolderName to set
	 */
	public void setSubfolderName(String subfolderName) {
		this.subfolderName = subfolderName;
	}

	/**
	 * @return the userID
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * @param userID the userID to set
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * @return the subProfileID
	 */
	public String getSubProfileID() {
		return subProfileID;
	}

	/**
	 * @param subProfileID the subProfileID to set
	 */
	public void setSubProfileID(String subProfileID) {
		this.subProfileID = subProfileID;
	}

	/**
	 * @return the legalName
	 */
	public String getLegalName() {
		return legalName;
	}

	/**
	 * @param legalName the legalName to set
	 */
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the legalID
	 */
	public String getLegalID() {
		return legalID;
	}

	/**
	 * @param legalID the legalID to set
	 */
	public void setLegalID(String legalID) {
		this.legalID = legalID;
	}

	/**
	 * @return the leIDType
	 */
	public String getLeIDType() {
		return leIDType;
	}

	/**
	 * @param leIDType the leIDType to set
	 */
	public void setLeIDType(String leIDType) {
		this.leIDType = leIDType;
	}

	/**
	 * @return the idNO
	 */
	public String getIdNO() {
		return idNO;
	}

	/**
	 * @param idNO the idNO to set
	 */
	public void setIdNO(String idNO) {
		this.idNO = idNO;
	}

	/**
	 * @return the gobutton
	 */
	public String getGobutton() {
		return gobutton;
	}

	/**
	 * @param gobutton the gobutton to set
	 */
	public void setGobutton(String gobutton) {
		this.gobutton = gobutton;
	}

	/**
	 * @return the all
	 */
	public String getAll() {
		return all;
	}

	/**
	 * @param all the all to set
	 */
	public void setAll(String all) {
		this.all = all;
	}
	/**
	 * Description : get method for the form to map the value to ob or ob to
	 * form
	 * 
	 * @return input
	 */
	public String[][] getMapper() {

		String[][] input = {
				{ "aCustomerSearchCriteria",
						"com.integrosys.cms.ui.image.ImageUploadForm" },
				{ "customerSearchCriteria",
						"com.integrosys.cms.ui.image.ImageUploadListMapper" },

				{ "customerList",
						"com.integrosys.cms.ui.image.ImageUploadListMapper" },

				{ "theOBTrxContext",
						"com.integrosys.cms.ui.common.TrxContextMapper" },
						{"imageUploadProxyManager", "com.integrosys.cms.app.customer.bus.IImageUploadProxyManager"}

		};

		return input;
	}

	/**
	 * Description : reset the define property
	 * in form
	 * 
	 * @return void
	 */
	public void reset() {
		setCustomerName("");
		setLegalID("");
		setLeIDType("");
		setIdNO("");
	}
}
