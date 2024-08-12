package com.integrosys.cms.ui.otherbankbranch;

import java.io.Serializable;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Purpose : Defines attributes for other Branch 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1 $
 * @since $Date: 2011-02-18 17:12:33 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */
	public class OtherBranchForm extends TrxContextForm implements Serializable
	{
		

		static final long serialVersionUID = 0L;
		
		private String selectBranchCode;
		private String otherBranchCode ;
		private String otherBranchName;
		private String otherBankId;
		private String otherBankCode;
		private String otherBankName;
		private String branchAddress;
	    private String branchCity;
	    private String branchState;
	    private String branchRegion;
	    private String branchCountry;
	    private String branchContactNo;
	    private String branchContactMailId;
	    
	    private String branchCityId;
	    private String branchStateId;
	    private String branchRegionId;
	    private String branchCountryId;
	    
	    private String id ;
	    private String versionTime ;
		private String status;
	    private String deprecated;
	    private String createdBy;
	    private String creationDate;
	    private String lastUpdateBy;
	    private String lastUpdateDate;
	    private String branchFaxNo;
	    private String branchRbiCode;
	    
	    private List countryList;
		private List regionList;
		private List stateList;
		private List cityList;
		
		
		private FormFile fileUpload;
		
		private String syncRemark = null;
		private String syncStatus = null;
		private String syncAction = null; 
		
		
		public FormFile getFileUpload() {
			return fileUpload;
		}

		public void setFileUpload(FormFile fileUpload) {
			this.fileUpload = fileUpload;
		}
		
		/**
		 * @return the branchCityId
		 */
		public String getBranchCityId() {
			return branchCityId;
		}

		/**
		 * @param branchCityId the branchCityId to set
		 */
		public void setBranchCityId(String branchCityId) {
			this.branchCityId = branchCityId;
		}

		/**
		 * @return the branchStateId
		 */
		public String getBranchStateId() {
			return branchStateId;
		}

		/**
		 * @param branchStateId the branchStateId to set
		 */
		public void setBranchStateId(String branchStateId) {
			this.branchStateId = branchStateId;
		}

		/**
		 * @return the branchRegionId
		 */
		public String getBranchRegionId() {
			return branchRegionId;
		}

		/**
		 * @param branchRegionId the branchRegionId to set
		 */
		public void setBranchRegionId(String branchRegionId) {
			this.branchRegionId = branchRegionId;
		}

		/**
		 * @return the branchCountryId
		 */
		public String getBranchCountryId() {
			return branchCountryId;
		}

		/**
		 * @param branchCountryId the branchCountryId to set
		 */
		public void setBranchCountryId(String branchCountryId) {
			this.branchCountryId = branchCountryId;
		}

		/**
		 * @return the countryList
		 */
		public List getCountryList() {
			return countryList;
		}

		/**
		 * @param countryList the countryList to set
		 */
		public void setCountryList(List countryList) {
			this.countryList = countryList;
		}

		/**
		 * @return the regionList
		 */
		public List getRegionList() {
			return regionList;
		}

		/**
		 * @param regionList the regionList to set
		 */
		public void setRegionList(List regionList) {
			this.regionList = regionList;
		}

		/**
		 * @return the stateList
		 */
		public List getStateList() {
			return stateList;
		}

		/**
		 * @param stateList the stateList to set
		 */
		public void setStateList(List stateList) {
			this.stateList = stateList;
		}

		/**
		 * @return the cityList
		 */
		public List getCityList() {
			return cityList;
		}

		/**
		 * @param cityList the cityList to set
		 */
		public void setCityList(List cityList) {
			this.cityList = cityList;
		}

		/**
		 * @return the selectBranchCode
		 */
		public String getSelectBranchCode() {
			return selectBranchCode;
		}

		/**
		 * @param selectBranchCode the selectBranchCode to set
		 */
		public void setSelectBranchCode(String selectBranchCode) {
			this.selectBranchCode = selectBranchCode;
		}

		/**
		 * @return the otherBranchCode
		 */
		public String getOtherBranchCode() {
			return otherBranchCode;
		}

		/**
		 * @param otherBranchCode the otherBranchCode to set
		 */
		public void setOtherBranchCode(String otherBranchCode) {
			this.otherBranchCode = otherBranchCode;
		}

		/**
		 * @return the otherBranchName
		 */
		public String getOtherBranchName() {
			return otherBranchName;
		}

		/**
		 * @param otherBranchName the otherBranchName to set
		 */
		public void setOtherBranchName(String otherBranchName) {
			this.otherBranchName = otherBranchName;
		}

		/**
		 * @return the otherBankId
		 */
		public String getOtherBankId() {
			return otherBankId;
		}

		/**
		 * @param otherBankId the otherBankId to set
		 */
		public void setOtherBankId(String otherBankId) {
			this.otherBankId = otherBankId;
		}

		/**
		 * @return the otherBankCode
		 */
		public String getOtherBankCode() {
			return otherBankCode;
		}

		/**
		 * @param otherBankCode the otherBankCode to set
		 */
		public void setOtherBankCode(String otherBankCode) {
			this.otherBankCode = otherBankCode;
		}

		/**
		 * @return the otherBankName
		 */
		public String getOtherBankName() {
			return otherBankName;
		}

		/**
		 * @param otherBankName the otherBankName to set
		 */
		public void setOtherBankName(String otherBankName) {
			this.otherBankName = otherBankName;
		}

		/**
		 * @return the branchAddress
		 */
		public String getBranchAddress() {
			return branchAddress;
		}

		/**
		 * @param branchAddress the branchAddress to set
		 */
		public void setBranchAddress(String branchAddress) {
			this.branchAddress = branchAddress;
		}

		/**
		 * @return the branchCity
		 */
		public String getBranchCity() {
			return branchCity;
		}

		/**
		 * @param branchCity the branchCity to set
		 */
		public void setBranchCity(String branchCity) {
			this.branchCity = branchCity;
		}

		/**
		 * @return the branchState
		 */
		public String getBranchState() {
			return branchState;
		}

		/**
		 * @param branchState the branchState to set
		 */
		public void setBranchState(String branchState) {
			this.branchState = branchState;
		}

		/**
		 * @return the branchRegion
		 */
		public String getBranchRegion() {
			return branchRegion;
		}

		/**
		 * @param branchRegion the branchRegion to set
		 */
		public void setBranchRegion(String branchRegion) {
			this.branchRegion = branchRegion;
		}

		/**
		 * @return the branchCountry
		 */
		public String getBranchCountry() {
			return branchCountry;
		}

		/**
		 * @param branchCountry the branchCountry to set
		 */
		public void setBranchCountry(String branchCountry) {
			this.branchCountry = branchCountry;
		}

		/**
		 * @return the branchContactNo
		 */
		public String getBranchContactNo() {
			return branchContactNo;
		}

		/**
		 * @param branchContactNo the branchContactNo to set
		 */
		public void setBranchContactNo(String branchContactNo) {
			this.branchContactNo = branchContactNo;
		}

		/**
		 * @return the branchContactMailId
		 */
		public String getBranchContactMailId() {
			return branchContactMailId;
		}

		/**
		 * @param branchContactMailId the branchContactMailId to set
		 */
		public void setBranchContactMailId(String branchContactMailId) {
			this.branchContactMailId = branchContactMailId;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the versionTime
		 */
		public String getVersionTime() {
			return versionTime;
		}

		/**
		 * @param versionTime the versionTime to set
		 */
		public void setVersionTime(String versionTime) {
			this.versionTime = versionTime;
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the deprecated
		 */
		public String getDeprecated() {
			return deprecated;
		}

		/**
		 * @param deprecated the deprecated to set
		 */
		public void setDeprecated(String deprecated) {
			this.deprecated = deprecated;
		}

		/**
		 * @return the createdBy
		 */
		public String getCreatedBy() {
			return createdBy;
		}

		/**
		 * @param createdBy the createdBy to set
		 */
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		/**
		 * @return the creationDate
		 */
		public String getCreationDate() {
			return creationDate;
		}

		/**
		 * @param creationDate the creationDate to set
		 */
		public void setCreationDate(String creationDate) {
			this.creationDate = creationDate;
		}

		/**
		 * @return the lastUpdateBy
		 */
		public String getLastUpdateBy() {
			return lastUpdateBy;
		}

		/**
		 * @param lastUpdateBy the lastUpdateBy to set
		 */
		public void setLastUpdateBy(String lastUpdateBy) {
			this.lastUpdateBy = lastUpdateBy;
		}

		/**
		 * @return the lastUpdateDate
		 */
		public String getLastUpdateDate() {
			return lastUpdateDate;
		}

		/**
		 * @param lastUpdateDate the lastUpdateDate to set
		 */
		public void setLastUpdateDate(String lastUpdateDate) {
			this.lastUpdateDate = lastUpdateDate;
		}

		/**
		 * @return the branchFaxNo
		 */
		public String getBranchFaxNo() {
			return branchFaxNo;
		}

		/**
		 * @param branchFaxNo the branchFaxNo to set
		 */
		public void setBranchFaxNo(String branchFaxNo) {
			this.branchFaxNo = branchFaxNo;
		}

		/**
		 * @return the branchRbiCode
		 */
		public String getBranchRbiCode() {
			return branchRbiCode;
		}

		/**
		 * @param branchRbiCode the branchRbiCode to set
		 */
		public void setBranchRbiCode(String branchRbiCode) {
			this.branchRbiCode = branchRbiCode;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		

		/**
		 * @return the syncRemark
		 */
		public String getSyncRemark() {
			return syncRemark;
		}

		/**
		 * @param syncRemark the syncRemark to set
		 */
		public void setSyncRemark(String syncRemark) {
			this.syncRemark = syncRemark;
		}

		/**
		 * @return the syncStatus
		 */
		public String getSyncStatus() {
			return syncStatus;
		}

		/**
		 * @param syncStatus the syncStatus to set
		 */
		public void setSyncStatus(String syncStatus) {
			this.syncStatus = syncStatus;
		}

		/**
		 * @return the syncAction
		 */
		public String getSyncAction() {
			return syncAction;
		}

		/**
		 * @param syncAction the syncAction to set
		 */
		public void setSyncAction(String syncAction) {
			this.syncAction = syncAction;
		}

		public String[][] getMapper()
		{	
			DefaultLogger.debug(this, " Inside getMapper Other branch");
	        String[][] input =
	            {
	            	{"OtherBranchObj",OtherBranchMapper.class.getName()},
	            	{ "theOBTrxContext", TRX_MAPPER }
	            };
	         
	        return input;
	    }
		
		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	}
