/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/07/25 17:58:52 $ Tag: $Name: $
 */

public class GenerateLADForm extends TrxContextForm implements Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String creditOfficerName = "";

	private String seniorCreditOfficerName = "";

	private String creditOfficerSgnNo = "";

	private String seniorCreditOfficerSgnNo = "";
	private String searchCustomerName = "";
	
	private String party="";
	




	public String getParty() {
		return party;
	}

	public void setParty(String party) {
		this.party = party;
	}

	public String getSearchCustomerName() {
		return searchCustomerName;
	}

	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}

	private String remarksSCC = "";



    private String releaseTo = "";

    private String[] docNumber;

    private String[] docCode;

    private String[] docDesc;

    private String[] dateDefer;

    private String[] dateOfReturn;

    private String[] docStatus;

    private String[] actionParty;

    private String[] theApprovalDate;

    private String[] approvedBy;
    
    private String segment;
	private String dueMonth;
	private String dueYear;


	private String relationshipMgr;
	private String relationshipMgrId;
	private String reportId;
	private String filterOption;
//	private String reportsFilterType;
// filters for report starts
	private String partyId;

	private String industry;
	private String status;

	private String branchId;
	private String documentType;

	private String relatoionship;
	private String guarantor;

	private String userId;
	private String departmentId;

	private String fromDate;
	private String toDate;
	private String relationManager;
	
	private String rbiAsset;	
	
	
	
	private String customerNameError;

	
	 
	public String getCustomerNameError() {
		return customerNameError;
	}

	public void setCustomerNameError(String customerNameError) {
		this.customerNameError = customerNameError;
	}

	private String versionTime;
	private String lastUpdateDate;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String id;
	
  
	private String filterPartyMode;
	private String filterUserMode;
	private String filterDocument;
	
	private String rmRegion;

	private String moduleEvent;
	private String tatCriteria;
	private String category;
	private String caseId;
	private String profile;
	
	private String uploadSystem;
	private String quarter;


	
	public String getRelationshipMgrId() {
		return relationshipMgrId;
	}

	public void setRelationshipMgrId(String relationshipMgrId) {
		this.relationshipMgrId = relationshipMgrId;
	}
	
	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getFilterOption() {
		return filterOption;
	}

	public void setFilterOption(String filterOption) {
		this.filterOption = filterOption;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	public String getRelatoionship() {
		return relatoionship;
	}

	public void setRelatoionship(String relatoionship) {
		this.relatoionship = relatoionship;
	}

	public String getGuarantor() {
		return guarantor;
	}

	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getRelationManager() {
		return relationManager;
	}

	public void setRelationManager(String relationManager) {
		this.relationManager = relationManager;
	}

	public String getRbiAsset() {
		return rbiAsset;
	}

	public void setRbiAsset(String rbiAsset) {
		this.rbiAsset = rbiAsset;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilterPartyMode() {
		return filterPartyMode;
	}

	public void setFilterPartyMode(String filterPartyMode) {
		this.filterPartyMode = filterPartyMode;
	}

	public String getFilterUserMode() {
		return filterUserMode;
	}

	public void setFilterUserMode(String filterUserMode) {
		this.filterUserMode = filterUserMode;
	}

	public String getFilterDocument() {
		return filterDocument;
	}

	public void setFilterDocument(String filterDocument) {
		this.filterDocument = filterDocument;
	}

	public String getRmRegion() {
		return rmRegion;
	}

	public void setRmRegion(String rmRegion) {
		this.rmRegion = rmRegion;
	}

	public String getModuleEvent() {
		return moduleEvent;
	}

	public void setModuleEvent(String moduleEvent) {
		this.moduleEvent = moduleEvent;
	}

	public String getTatCriteria() {
		return tatCriteria;
	}

	public void setTatCriteria(String tatCriteria) {
		this.tatCriteria = tatCriteria;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getUploadSystem() {
		return uploadSystem;
	}

	public void setUploadSystem(String uploadSystem) {
		this.uploadSystem = uploadSystem;
	}

	public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}

	public static final String REPORT_MAPPER = "com.integrosys.cms.ui.genlad.ReportMapper";


    /*
	public String[] getAppAmtCurrCode() {
		return appAmtCurrCode;
	}

	public void setAppAmtCurrCode(String[] appAmtCurrCode) {
		this.appAmtCurrCode = appAmtCurrCode;
	}

	public String[] getAppLimit() {
		return appLimit;
	}

	public void setAppLimit(String[] appLimit) {
		this.appLimit = appLimit;
	}

	public String getCheckedIndexes() {
		return checkedIndexes;
	}

	public void setCheckedIndexes(String checkedIndexes) {
		this.checkedIndexes = checkedIndexes;
	}

	public String getBcaAppDate() {
		return bcaAppDate;
	}

	public void setBcaAppDate(String aBcaAppDate) {
		bcaAppDate = aBcaAppDate;
	}

	public String getDdnIssuedIndex() {
		return ddnIssuedIndex;
	}

	public void setDdnIssuedIndex(String ddnIssuedIndex) {
		this.ddnIssuedIndex = ddnIssuedIndex;
	}

	public String getAppDate() {
		return appDate;
	}

	public void setAppDate(String appDate) {
		this.appDate = appDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getDdnExtDate() {
		return ddnExtDate;
	}

	public void setDdnExtDate(String ddnExtDate) {
		this.ddnExtDate = ddnExtDate;
	}

	public String getDdnDate() {
		return ddnDate;
	}

	public void setDdnDate(String ddnDate) {
		this.ddnDate = ddnDate;
	}

	public String getDdnDays() {
		return ddnDays;
	}

	public void setDdnDays(String ddnDays) {
		this.ddnDays = ddnDays;
	}

	public String[] getDdnLimit() {
		return ddnLimit;
	}

	public void setDdnLimit(String[] ddnLimit) {
		this.ddnLimit = ddnLimit;
	}
	*/

	/*
	 * public String[] getDdnLimitLeft() { return ddnLimitLeft; }
	 * 
	 * public void setDdnLimitLeft(String[] ddnLimitLeft) { this.ddnLimitLeft =
	 * ddnLimitLeft; }
	 */
	public String getCreditOfficerName() {
		return creditOfficerName;
	}

	public void setCreditOfficerName(String creditOfficerName) {
		this.creditOfficerName = creditOfficerName;
	}

	public String getCreditOfficerSgnNo() {
		return creditOfficerSgnNo;
	}

	public void setCreditOfficerSgnNo(String creditOfficerSgnNo) {
		this.creditOfficerSgnNo = creditOfficerSgnNo;
	}

	public String getRemarksSCC() {
		return remarksSCC;
	}

	public void setRemarksSCC(String remarksSCC) {
		this.remarksSCC = remarksSCC;
	}

	public String getSeniorCreditOfficerName() {
		return seniorCreditOfficerName;
	}

	public void setSeniorCreditOfficerName(String seniorCreditOfficerName) {
		this.seniorCreditOfficerName = seniorCreditOfficerName;
	}

	public String getSeniorCreditOfficerSgnNo() {
		return seniorCreditOfficerSgnNo;
	}

	public void setSeniorCreditOfficerSgnNo(String seniorCreditOfficerSgnNo) {
		this.seniorCreditOfficerSgnNo = seniorCreditOfficerSgnNo;
	}
    /*
	public String getCreditOfficerLocationCountry() {
		return creditOfficerLocationCountry;
	}

	public void setCreditOfficerLocationCountry(String creditOfficerLocationCountry) {
		this.creditOfficerLocationCountry = creditOfficerLocationCountry;
	}

	public String getSeniorCreditOfficerLocationCountry() {
		return seniorCreditOfficerLocationCountry;
	}

	public void setSeniorCreditOfficerLocationCountry(String seniorCreditOfficerLocationCountry) {
		this.seniorCreditOfficerLocationCountry = seniorCreditOfficerLocationCountry;
	}

	public String getCreditOfficerLocationOrgCode() {
		return creditOfficerLocationOrgCode;
	}

	public void setCreditOfficerLocationOrgCode(String creditOfficerLocationOrgCode) {
		this.creditOfficerLocationOrgCode = creditOfficerLocationOrgCode;
	}

	public String getSeniorCreditOfficerLocationOrgCode() {
		return seniorCreditOfficerLocationOrgCode;
	}

	public void setSeniorCreditOfficerLocationOrgCode(String seniorCreditOfficerLocationOrgCode) {
		this.seniorCreditOfficerLocationOrgCode = seniorCreditOfficerLocationOrgCode;
	}

	public String[] getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String[] maturityDate) {
		this.maturityDate = maturityDate;
	}
    */

    public String getReleaseTo() {
        return releaseTo;
    }

    public void setReleaseTo(String releaseTo) {
        this.releaseTo = releaseTo;
    }

    public String[] getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String[] docNumber) {
        this.docNumber = docNumber;
    }

    public String[] getDocCode() {
        return docCode;
    }

    public void setDocCode(String[] docCode) {
        this.docCode = docCode;
    }

    public String[] getDocDesc() {
        return docDesc;
    }

    public void setDocDesc(String[] docDesc) {
        this.docDesc = docDesc;
    }

    public String[] getDateDefer() {
        return dateDefer;
    }

    public void setDateDefer(String[] dateDefer) {
        this.dateDefer = dateDefer;
    }

    public String[] getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(String[] dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public String[] getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String[] docStatus) {
        this.docStatus = docStatus;
    }

    public String[] getActionParty() {
        return actionParty;
    }

    public void setActionParty(String[] actionParty) {
        this.actionParty = actionParty;
    }

    public String[] getTheApprovalDate() {
        return theApprovalDate;
    }

    public void setTheApprovalDate(String[] theApprovalDate) {
        this.theApprovalDate = theApprovalDate;
    }

    public String[] getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String[] approvedBy) {
        this.approvedBy = approvedBy;
    }

    /**
	 * This method defines a String array which tells what object is to be
	 * formed from the form and using what mapper classes to form it. it has a
	 * syntax (keyMapperclassname)
	 * 
	 * @return two-dimesnional String Array
	 */
	public String[][] getMapper() {
		String[][] input = { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				// {"limitProfile",
				// "com.integrosys.cms.ui.genscc.LimitProfileMapper"},
				{ "cert", "com.integrosys.cms.ui.genddn.DDNMapper" },
				{ "certPrev", "com.integrosys.cms.ui.genddn.DDNMapper" },
				{ "generateLadFormObj", REPORT_MAPPER }

		};
		return input;
	}

    public String toString() {
        return "GenerateDDNForm{" +
                "creditOfficerName='" + creditOfficerName + '\'' +
                ", seniorCreditOfficerName='" + seniorCreditOfficerName + '\'' +
                ", creditOfficerSgnNo='" + creditOfficerSgnNo + '\'' +
                ", seniorCreditOfficerSgnNo='" + seniorCreditOfficerSgnNo + '\'' +
                ", remarksSCC='" + remarksSCC + '\'' +
                ", releaseTo='" + releaseTo + '\'' +
                ", docNumber=" + (docNumber == null ? null : Arrays.asList(docNumber)) +
                ", docCode=" + (docCode == null ? null : Arrays.asList(docCode)) +
                ", docDesc=" + (docDesc == null ? null : Arrays.asList(docDesc)) +
                ", dateDefer=" + (dateDefer == null ? null : Arrays.asList(dateDefer)) +
                ", dateOfReturn=" + (dateOfReturn == null ? null : Arrays.asList(dateOfReturn)) +
                ", docStatus=" + (docStatus == null ? null : Arrays.asList(docStatus)) +
                ", actionParty=" + (actionParty == null ? null : Arrays.asList(actionParty)) +
                ", theApprovalDate=" + (theApprovalDate == null ? null : Arrays.asList(theApprovalDate)) +
                ", approvedBy=" + (approvedBy == null ? null : Arrays.asList(approvedBy)) +
                '}';
    }

	public String getSegment() {
		return segment;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	public String getDueMonth() {
		return dueMonth;
	}

	public void setDueMonth(String dueMonth) {
		this.dueMonth = dueMonth;
	}

	public String getDueYear() {
		return dueYear;
	}

	public void setDueYear(String dueYear) {
		this.dueYear = dueYear;
	}

	public String getRelationshipMgr() {
		return relationshipMgr;
	}

	public void setRelationshipMgr(String relationshipMgr) {
		this.relationshipMgr = relationshipMgr;
	}
}
