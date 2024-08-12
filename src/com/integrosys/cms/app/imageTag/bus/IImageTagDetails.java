package com.integrosys.cms.app.imageTag.bus;

import java.io.Serializable;
import java.util.List;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

/**
 * Interface for Image Tag
 * @author abhijit.rudrakshawar
 
 */

public interface IImageTagDetails extends Serializable, IValueObject {

	/**
	 * gets the country allowed for viewing
	 * @return String - allowed country
	 */
	

	public long getId();
	
	public void setId(long id);
	
	public long getSecurityId();
	
	public void setSecurityId(long securityId);

	
	
	public long getVersionTime();
	
	public void setVersionTime(long versionTime);

	public String getCustId();

	public void setCustId(String custId);
	
	
	public String getDocType();
	public void setDocType(String docType);
	
	public String getSecType();
	public void setSecType(String secType);
	
	public String getSecSubtype();
	public void setSecSubtype(String secSubtype);

	public long getFacilityId();
	public void setFacilityId(long facilityId);
	
	public String getDocDesc();
	public void setDocDesc(String docDesc);
	
	// For Handling UI Display By Anil -------Start------
	public String getCategory();
	public void setCategory(String category);
	
	public String getFacilityIdLabel();
	public void setFacilityIdLabel(String facilityIdLabel);
	
	public String getSecTypeLabel();
	public void setSecTypeLabel(String secTypeLabel);
	
	public String getSecSubtypeLabel();
	public void setSecSubtypeLabel(String secSubtypeLabel);
	
	public String getSecurityIdLabel();
	public void setSecurityIdLabel(String securityIdLabel);

	public String getDocDescLabel();
	public void setDocDescLabel(String docDescLabel);

	public String getSubfolderName() ;
	public void setSubfolderName(String subfolderName) ;

	public String getDocumentName();
	public void setDocumentName(String documentName) ;
	
	public String getCustomerNameLabel() ;
	public void setCustomerNameLabel(String customerNameLabel);
	public String getSelectedArray() ;

	public void setSelectedArray(String selectedArray) ;
	
	
	public String getUnCheckArray() ;
	public void setUnCheckArray(String unCheckArray);
	// For Handling UI Display By Anil -------End------
	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getBank() ;
	public void setBank(String bank) ;

	//Added by Uma Khot: Start: Phase 3 CR:tag scanned images of Annexure II
	public String getFacilityName();
	public void setFacilityName(String facilityName);
	
	public String getFacilityDocName();
	public void setFacilityDocName(String facilityDocName);
	
	public String getOtherDocName();
	public void setOtherDocName(String otherDocName);
	
	public String getSecurityNameId();
	public void setSecurityNameId(String securityNameId);
	
	public String getSecurityDocName();
	public void setSecurityDocName(String securityDocName);
	
	public String getOtherSecDocName();
	public void setOtherSecDocName(String otherSecDocName);
	
	public String getTypeOfDocument();
	public void setTypeOfDocument(String typeOfDocument);
	
	public String getStatementTyped();
	public void setStatementTyped(String statementTyped);
	
	public String getStatementDocName();
	public void setStatementDocName(String statementDocName);

//	public String getCamName();
//	public void setCamName(String camName);
	
	public String getCamDocName();
	public void setCamDocName(String camDocName);

	public String getOthersDocsName();
	public void setOthersDocsName(String othersDocsName);
	
	public String getSecurityIdi();
	public void setSecurityIdi(String securityIdi);

	public String getSubTypeSecurity();
	public void setSubTypeSecurity(String subTypeSecurity);
	//Added by Prachit
	
}
