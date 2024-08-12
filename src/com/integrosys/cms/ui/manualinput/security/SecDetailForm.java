/*
 * Created on Apr 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.security;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecDetailForm extends TrxContextForm implements Serializable {

	private String isCreate;

	private String securityId;

	private String sciColId;

	private String secBookingCountry;

	private String secBookingCountryDesc;

	private String secBookingOrg;

	private String secBookingOrgDesc;

	private String secType;

	private String secTypeDesc;

	private String secSubtype;

	private String secSubtypeDesc;

	private String secCurrency;

	private String fsv;

	private String secReferenceNote;

	private String secBookingCountryClass = "fieldname";

	private String secBookingOrgClass = "fieldname";

	private String secTypeClass = "fieldname";

	private String secSubtypeClass = "fieldname";

	private String secCurrencyClass = "fieldname";

	private String fsvClass = "fieldname";

	private String secReferenceNoteClass = "fieldname";

	private List pledgorList;

	private String[] deletedPledgor;

	private String[] secPledgorRelnshipList;

	private String limitProfileID;

	private String customerID;
	
	private String collateralCode;
	
	private String secPriority;
	
	private String collateralList;
	
	private String cpsSecurityId;
	
	private String uniqueReqId;
	
	//Added by Pramod Katkar for New Filed CR on 20-08-2013
	
	private String monitorProcess;
	private String monitorFrequency;
	
	
	private String primarySecurityAddress;
	
	private String securityValueAsPerCAM;
	
	private String secondarySecurityAddress;
	
	private String securityMargin;
	
	private String chargePriority;
	
	
	
	
	
	public String getPrimarySecurityAddress() {
		return primarySecurityAddress;
	}
	public void setPrimarySecurityAddress(String primarySecurityAddress) {
		this.primarySecurityAddress = primarySecurityAddress;
	}
	public String getSecurityValueAsPerCAM() {
		return securityValueAsPerCAM;
	}
	public void setSecurityValueAsPerCAM(String securityValueAsPerCAM) {
		this.securityValueAsPerCAM = securityValueAsPerCAM;
	}
	public String getSecondarySecurityAddress() {
		return secondarySecurityAddress;
	}
	public void setSecondarySecurityAddress(String secondarySecurityAddress) {
		this.secondarySecurityAddress = secondarySecurityAddress;
	}
	public String getSecurityMargin() {
		return securityMargin;
	}
	public void setSecurityMargin(String securityMargin) {
		this.securityMargin = securityMargin;
	}
	public String getChargePriority() {
		return chargePriority;
	}
	public void setChargePriority(String chargePriority) {
		this.chargePriority = chargePriority;
	}
	public String getMonitorProcess() {
		return monitorProcess;
	}
	public void setMonitorProcess(String monitorProcess) {
		this.monitorProcess = monitorProcess;
	}
	public String getMonitorFrequency() {
		return monitorFrequency;
	}
	public void setMonitorFrequency(String monitorFrequency) {
		this.monitorFrequency = monitorFrequency;
	}
	
	//End by Pramod Katkar
	public String getLmtSecurityCoverage() {
		return lmtSecurityCoverage;
	}

	public void setLmtSecurityCoverage(String lmtSecurityCoverage) {
		this.lmtSecurityCoverage = lmtSecurityCoverage;
	}

	private String lmtSecurityCoverage;
	

	public String getSecPriority() {
		return secPriority;
	}

	public void setSecPriority(String secPriority) {
		this.secPriority = secPriority;
	}

	public String getCollateralList() {
		return collateralList;
	}

	public void setCollateralList(String collateralList) {
		this.collateralList = collateralList;
	}

	public String getCollateralCode() {
		return collateralCode;
	}

	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}

	public SecDetailForm() {
		super();
		deletedPledgor = new String[0];
	}

	/**
	 * @return Returns the limitProfileId.
	 */
	public String getLimitProfileID() {
		return limitProfileID;
	}

	/**
	 * @param limitProfileId The limitProfileId to set.
	 */
	public void setLimitProfileID(String limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	/**
	 * @return Returns the customerID.
	 */
	public String getCustomerID() {
		return customerID;
	}

	/**
	 * @param customerID The customerID to set.
	 */
	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	/**
	 * @return Returns the fsv.
	 */
	public String getFsv() {
		return fsv;
	}

	/**
	 * @param fsv The fsv to set.
	 */
	public void setFsv(String fsv) {
		this.fsv = fsv;
	}

	/**
	 * @return Returns the sciColId.
	 */
	public String getSciColId() {
		return sciColId;
	}

	/**
	 * @param sciColId The sciColId to set.
	 */
	public void setSciColId(String sciColId) {
		this.sciColId = sciColId;
	}

	/**
	 * @return Returns the secBookingCountry.
	 */
	public String getSecBookingCountry() {
		return secBookingCountry;
	}

	/**
	 * @param secBookingCountry The secBookingCountry to set.
	 */
	public void setSecBookingCountry(String secBookingCountry) {
		this.secBookingCountry = secBookingCountry;
	}

	/**
	 * @return Returns the secBookingOrg.
	 */
	public String getSecBookingOrg() {
		return secBookingOrg;
	}

	/**
	 * @param secBookingOrg The secBookingOrg to set.
	 */
	public void setSecBookingOrg(String secBookingOrg) {
		this.secBookingOrg = secBookingOrg;
	}

	/**
	 * @return Returns the secCurrency.
	 */
	public String getSecCurrency() {
		return secCurrency;
	}

	/**
	 * @param secCurrency The secCurrency to set.
	 */
	public void setSecCurrency(String secCurrency) {
		this.secCurrency = secCurrency;
	}

	/**
	 * @return Returns the secReferenceNote.
	 */
	public String getSecReferenceNote() {
		return secReferenceNote;
	}

	/**
	 * @param secReferenceNote The secReferenceNote to set.
	 */
	public void setSecReferenceNote(String secReferenceNote) {
		this.secReferenceNote = secReferenceNote;
	}

	/**
	 * @return Returns the secSubtype.
	 */
	public String getSecSubtype() {
		return secSubtype;
	}

	/**
	 * @param secSubtype The secSubtype to set.
	 */
	public void setSecSubtype(String secSubtype) {
		this.secSubtype = secSubtype;
	}

	/**
	 * @return Returns the secType.
	 */
	public String getSecType() {
		return secType;
	}

	/**
	 * @param secType The secType to set.
	 */
	public void setSecType(String secType) {
		this.secType = secType;
	}

	/**
	 * @return Returns the securityId.
	 */
	public String getSecurityId() {
		return securityId;
	}

	/**
	 * @param securityId The securityId to set.
	 */
	public void setSecurityId(String securityId) {
		this.securityId = securityId;
	}

	/**
	 * @return Returns the isCreate.
	 */
	public String getIsCreate() {
		return isCreate;
	}

	/**
	 * @param isCreate The isCreate to set.
	 */
	public void setIsCreate(String isCreate) {
		this.isCreate = isCreate;
	}

	/**
	 * @return Returns the secBokkingOrgDesc.
	 */
	public String getSecBookingOrgDesc() {
		return secBookingOrgDesc;
	}

	/**
	 * @param secBokkingOrgDesc The secBokkingOrgDesc to set.
	 */
	public void setSecBookingOrgDesc(String secBookingOrgDesc) {
		this.secBookingOrgDesc = secBookingOrgDesc;
	}

	/**
	 * @return Returns the secBookingCountryDesc.
	 */
	public String getSecBookingCountryDesc() {
		return secBookingCountryDesc;
	}

	/**
	 * @param secBookingCountryDesc The secBookingCountryDesc to set.
	 */
	public void setSecBookingCountryDesc(String secBookingCountryDesc) {
		this.secBookingCountryDesc = secBookingCountryDesc;
	}

	/**
	 * @return Returns the secSubtypeDesc.
	 */
	public String getSecSubtypeDesc() {
		return secSubtypeDesc;
	}

	/**
	 * @param secSubtypeDesc The secSubtypeDesc to set.
	 */
	public void setSecSubtypeDesc(String secSubtypeDesc) {
		this.secSubtypeDesc = secSubtypeDesc;
	}

	/**
	 * @return Returns the secTypeDesc.
	 */
	public String getSecTypeDesc() {
		return secTypeDesc;
	}

	/**
	 * @param secTypeDesc The secTypeDesc to set.
	 */
	public void setSecTypeDesc(String secTypeDesc) {
		this.secTypeDesc = secTypeDesc;
	}

	/**
	 * @return Returns the fsvClass.
	 */
	public String getFsvClass() {
		return fsvClass;
	}

	/**
	 * @param fsvClass The fsvClass to set.
	 */
	public void setFsvClass(String fsvClass) {
		this.fsvClass = fsvClass;
	}

	/**
	 * @return Returns the secBookingCountryClass.
	 */
	public String getSecBookingCountryClass() {
		return secBookingCountryClass;
	}

	/**
	 * @param secBookingCountryClass The secBookingCountryClass to set.
	 */
	public void setSecBookingCountryClass(String secBookingCountryClass) {
		this.secBookingCountryClass = secBookingCountryClass;
	}

	/**
	 * @return Returns the secBookingOrgClass.
	 */
	public String getSecBookingOrgClass() {
		return secBookingOrgClass;
	}

	/**
	 * @param secBookingOrgClass The secBookingOrgClass to set.
	 */
	public void setSecBookingOrgClass(String secBookingOrgClass) {
		this.secBookingOrgClass = secBookingOrgClass;
	}

	/**
	 * @return Returns the secCurrencyClass.
	 */
	public String getSecCurrencyClass() {
		return secCurrencyClass;
	}

	/**
	 * @param secCurrencyClass The secCurrencyClass to set.
	 */
	public void setSecCurrencyClass(String secCurrencyClass) {
		this.secCurrencyClass = secCurrencyClass;
	}

	/**
	 * @return Returns the secReferenceNoteClass.
	 */
	public String getSecReferenceNoteClass() {
		return secReferenceNoteClass;
	}

	/**
	 * @param secReferenceNoteClass The secReferenceNoteClass to set.
	 */
	public void setSecReferenceNoteClass(String secReferenceNoteClass) {
		this.secReferenceNoteClass = secReferenceNoteClass;
	}

	/**
	 * @return Returns the secSubtypeClass.
	 */
	public String getSecSubtypeClass() {
		return secSubtypeClass;
	}

	/**
	 * @param secSubtypeClass The secSubtypeClass to set.
	 */
	public void setSecSubtypeClass(String secSubtypeClass) {
		this.secSubtypeClass = secSubtypeClass;
	}

	/**
	 * @return Returns the secTypeClass.
	 */
	public String getSecTypeClass() {
		return secTypeClass;
	}

	/**
	 * @param secTypeClass The secTypeClass to set.
	 */
	public void setSecTypeClass(String secTypeClass) {
		this.secTypeClass = secTypeClass;
	}

	/**
	 * @return Returns the deletedPledgor.
	 */
	public String[] getDeletedPledgor() {
		return deletedPledgor;
	}

	/**
	 * @param deletedPledgor The deletedPledgor to set.
	 */
	public void setDeletedPledgor(String[] deletedPledgor) {
		this.deletedPledgor = deletedPledgor;
	}

	/**
	 * @return Returns the secPledgorRelnshipList.
	 */
	public String[] getSecPledgorRelnshipList() {
		return secPledgorRelnshipList;
	}

	/**
	 * @param secPledgorRelnshipList The secPledgorRelnshipList to set.
	 */
	public void setSecPledgorRelnshipList(String[] secPledgorRelnshipList) {
		this.secPledgorRelnshipList = secPledgorRelnshipList;
	}

	
	
	
	public String getCpsSecurityId() {
		return cpsSecurityId;
	}
	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}
	/**
	 * @return Returns the pledgorList.
	 */
	public List getPledgorList() {
		return pledgorList;
	}

	/**
	 * @param pledgorList The pledgorList to set.
	 */
	public void setPledgorList(List pledgorList) {
		this.pledgorList = pledgorList;
	}

	public String[][] getMapper() {
		// TODO Auto-generated method stub
		String[][] input = { { "secDetailForm", "com.integrosys.cms.ui.manualinput.security.SecDetailMapper" },
				{ "secList", "com.integrosys.cms.ui.manualinput.security.SecListMapper" },
				{ "dispFieldMapper", "com.integrosys.cms.ui.manualinput.security.DispFieldMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
		return input;
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1) {
		deletedPledgor = new String[0];
	}
	public String getUniqueReqId() {
		return uniqueReqId;
	}
	public void setUniqueReqId(String uniqueReqId) {
		this.uniqueReqId = uniqueReqId;
	}
}
