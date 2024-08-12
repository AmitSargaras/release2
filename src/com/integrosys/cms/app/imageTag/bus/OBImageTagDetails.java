package com.integrosys.cms.app.imageTag.bus;

import java.util.List;

/**
*@author abhijit.rudrakshawar
*
*OB for Image Tag Details
*/

public class OBImageTagDetails implements IImageTagDetails {
	
//	private long imgId;

	private long id;
	
	private long versionTime;
	
	private String custId ;
	
	private long securityId;
	
	private String docType;
	
	private long facilityId;
	
	private String docDesc;

	private String category;

	private String secType;

	private String secSubtype;

	//Added by Anil below labels will be used for viewing purpose added for mapObToForm Mapping
	private String secTypeLabel;
	private String secSubtypeLabel;
	private String securityIdLabel;
	private String facilityIdLabel;
	private String docDescLabel;
	private String customerNameLabel;
	private String selectedArray;
	private String unCheckArray;
	//New filter
	private String subfolderName;
	private String documentName;
	private String camType;
	private String camDate;
	private String expiryDate;

	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	private String bank;
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	
	private String facilityName;
	
	private String facilityDocName="";
	
	private String otherDocName = "";
	
	private String securityNameId = "";
	
	private String securityDocName = "";
	
	private String otherSecDocName = "";
	
	private String typeOfDocument = "";
	
	private String statementTyped="";	
	private String statementDocName = "";	
//	private String camName = "";	
	private String camDocName = "";	
	private String othersDocsName = "";
	private String securityIdi = "";
	private String subTypeSecurity = "";
	
	public String getSecurityIdi() {
		return securityIdi;
	}

	public void setSecurityIdi(String securityIdi) {
		this.securityIdi = securityIdi;
	}

	public String getSubTypeSecurity() {
		return subTypeSecurity;
	}

	public void setSubTypeSecurity(String subTypeSecurity) {
		this.subTypeSecurity = subTypeSecurity;
	}

	public String getStatementTyped() {
			return statementTyped;
		}

		public void setStatementTyped(String statementTyped) {
			this.statementTyped = statementTyped;
		}

		public String getStatementDocName() {
			return statementDocName;
		}

		public void setStatementDocName(String statementDocName) {
			this.statementDocName = statementDocName;
		}

		/*public String getCamName() {
			return camName;
		}

		public void setCamName(String camName) {
			this.camName = camName;
		}*/

		public String getCamDocName() {
			return camDocName;
		}

		public void setCamDocName(String camDocName) {
			this.camDocName = camDocName;
		}

		public String getOthersDocsName() {
			return othersDocsName;
		}

		public void setOthersDocsName(String othersDocsName) {
			this.othersDocsName = othersDocsName;
		}

	public String getTypeOfDocument() {
		return typeOfDocument;
	}

	public void setTypeOfDocument(String typeOfDocument) {
		this.typeOfDocument = typeOfDocument;
	}
	
	public String getOtherSecDocName() {
		return otherSecDocName;
	}

	public void setOtherSecDocName(String otherSecDocName) {
		this.otherSecDocName = otherSecDocName;
	}

	
	public String getSecurityDocName() {
		return securityDocName;
	}

	public void setSecurityDocName(String securityDocName) {
		this.securityDocName = securityDocName;
	}
	
	public String getSecurityNameId() {
		return securityNameId;
	}

	public void setSecurityNameId(String securityNameId) {
		this.securityNameId = securityNameId;
	}

	public String getOtherDocName() {
		return otherDocName;
	}

	public void setOtherDocName(String otherDocName) {
		this.otherDocName = otherDocName;
	}

	public String getFacilityDocName() {
		return facilityDocName;
	}

	public void setFacilityDocName(String facilityDocName) {
		this.facilityDocName = facilityDocName;
	}
	
	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

	public String getCamDate() {
		return camDate;
	}

	public void setCamDate(String camDate) {
		this.camDate = camDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getUnCheckArray() {
		return unCheckArray;
	}

	public void setUnCheckArray(String unCheckArray) {
		this.unCheckArray = unCheckArray;
	}

	public String getSelectedArray() {
		return selectedArray;
	}

	public void setSelectedArray(String selectedArray) {
		this.selectedArray = selectedArray;
	}

	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return the facilityId
	 */
	public long getFacilityId() {
		return facilityId;
	}

	/**
	 * @param facilityId the facilityId to set
	 */
	public void setFacilityId(long facilityId) {
		this.facilityId = facilityId;
	}

	/**
	 * @return the docDesc
	 */
	public String getDocDesc() {
		return docDesc;
	}

	/**
	 * @param docDesc the docDesc to set
	 */
	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public long getSecurityId() {
		return securityId;
	}

	public void setSecurityId(long securityId) {
		this.securityId = securityId;
	}

//	public long getImgId() {
//		return imgId;
//	}
//
//	public void setImgId(long imgId) {
//		this.imgId = imgId;
//	}



	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String getSecSubtype() {
		return secSubtype;
	}

	public void setSecSubtype(String secSubtype) {
		this.secSubtype = secSubtype;
	}

	public String getSecTypeLabel() {
		return secTypeLabel;
	}

	public void setSecTypeLabel(String secTypeLabel) {
		this.secTypeLabel = secTypeLabel;
	}

	public String getSecSubtypeLabel() {
		return secSubtypeLabel;
	}

	public void setSecSubtypeLabel(String secSubtypeLabel) {
		this.secSubtypeLabel = secSubtypeLabel;
	}

	public String getSecurityIdLabel() {
		return securityIdLabel;
	}

	public void setSecurityIdLabel(String securityIdLabel) {
		this.securityIdLabel = securityIdLabel;
	}

	public String getFacilityIdLabel() {
		return facilityIdLabel;
	}

	public void setFacilityIdLabel(String facilityIdLabel) {
		this.facilityIdLabel = facilityIdLabel;
	}

	public String getDocDescLabel() {
		return docDescLabel;
	}

	public void setDocDescLabel(String docDescLabel) {
		this.docDescLabel = docDescLabel;
	}

	public String getSubfolderName() {
		return subfolderName;
	}

	public void setSubfolderName(String subfolderName) {
		this.subfolderName = subfolderName;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	/**
	 * @return the customerNameLabel
	 */
	public String getCustomerNameLabel() {
		return customerNameLabel;
	}

	/**
	 * @param customerNameLabel the customerNameLabel to set
	 */
	public void setCustomerNameLabel(String customerNameLabel) {
		this.customerNameLabel = customerNameLabel;
	}
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getBank() {
		return bank;
	}
	//Added by Uma Khot: End: Phase 3 CR:tag scanned images of Annexure II

}
