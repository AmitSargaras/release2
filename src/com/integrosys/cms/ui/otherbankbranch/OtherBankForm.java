package com.integrosys.cms.ui.otherbankbranch;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;


	/**
	 * Defines attributes for other bank
	 *
	 * @author $Author: Dattatray Thorat $
	 * @version $Revision: 1.0 $
	 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
	 * Tag : $Name$
	 */
	public class OtherBankForm extends TrxContextForm implements Serializable {
		
		static final long serialVersionUID = 0L;
		
		private String selectBranchCode;
		private String otherBranchCode ;
		private String otherBranchName;
		private String otherBankCode;
		private String iFSCCode;
		private String otherBranchId  ;
		private String branchName;
		 
		
		private String otherBankName;
		private String address;
	    private String city;
	    private String state;
	    private String region;
	    private String country;
	    private String contactNo;
	    private String contactMailId;
	    
	    private String cityId;
	    private String stateId;
	    private String regionId;
	    private String countryId;
	    
	    private String id ;
	    private long versionTime;
		private String status;
	    private String deprecated;
	    private String faxNo;
	    private String createdBy;
	    private Date creationDate;
	    private String lastUpdateBy;
	    private String lastUpdateDate;
	    
	    private List countryList;
		private List regionList;
		private List stateList;
		private List cityList;
		
		private String txtBranchCode;		
		private String txtBranchName;		
		private String txtState;		
		private String txtCity;
		
		private String syncRemark = null;
		private String syncStatus = null;
		private String syncAction = null; 
		
		private FormFile fileUpload;
		
		
		public String getiFSCCode() {
			return iFSCCode;
		}

		public void setiFSCCode(String iFSCCode) {
			this.iFSCCode = iFSCCode;
		}

		public String getOtherBranchId() {
			return otherBranchId;
		}

		public void setOtherBranchId(String otherBranchId) {
			this.otherBranchId = otherBranchId;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		public FormFile getFileUpload() {
			return fileUpload;
		}

		public void setFileUpload(FormFile fileUpload) {
			this.fileUpload = fileUpload;
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
		 * @return the cityId
		 */
		public String getCityId() {
			return cityId;
		}

		/**
		 * @param cityId the cityId to set
		 */
		public void setCityId(String cityId) {
			this.cityId = cityId;
		}

		/**
		 * @return the stateId
		 */
		public String getStateId() {
			return stateId;
		}

		/**
		 * @param stateId the stateId to set
		 */
		public void setStateId(String stateId) {
			this.stateId = stateId;
		}

		/**
		 * @return the regionId
		 */
		public String getRegionId() {
			return regionId;
		}

		/**
		 * @param regionId the regionId to set
		 */
		public void setRegionId(String regionId) {
			this.regionId = regionId;
		}

		/**
		 * @return the countryId
		 */
		public String getCountryId() {
			return countryId;
		}

		/**
		 * @param countryId the countryId to set
		 */
		public void setCountryId(String countryId) {
			this.countryId = countryId;
		}

		public String getLastUpdateBy() {
			return lastUpdateBy;
		}

		public void setLastUpdateBy(String lastUpdateBy) {
			this.lastUpdateBy = lastUpdateBy;
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
		 * @return the contactNo
		 */
		public String getContactNo() {
			return contactNo;
		}

		/**
		 * @param contactNo the contactNo to set
		 */
		public void setContactNo(String contactNo) {
			this.contactNo = contactNo;
		}

		/**
		 * @return the contactMailId
		 */
		public String getContactMailId() {
			return contactMailId;
		}

		/**
		 * @param contactMailId the contactMailId to set
		 */
		public void setContactMailId(String contactMailId) {
			this.contactMailId = contactMailId;
		}

	    /**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}

		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}

		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}

		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}

		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}

		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}

		/**
		 * @return the region
		 */
		public String getRegion() {
			return region;
		}

		/**
		 * @param region the region to set
		 */
		public void setRegion(String region) {
			this.region = region;
		}

		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}

		/**
		 * @param country the country to set
		 */
		public void setCountry(String country) {
			this.country = country;
		}

		/**
		 * @return the creationDate
		 */
		public Date getCreationDate() {
			return creationDate;
		}

		/**
		 * @param creationDate the creationDate to set
		 */
		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
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

		/**ed
		 * @return the versionTime
		 */
		public long getVersionTime() {
			return versionTime;
		}

		/**
		 * @param versionTime the versionTime to set
		 */
		public void setVersionTime(long versionTime) {
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
		 * @return the faxNo
		 */
		public String getFaxNo() {
			return faxNo;
		}

		/**
		 * @param faxNo the faxNo to set
		 */
		public void setFaxNo(String faxNo) {
			this.faxNo = faxNo;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
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

		public String getTxtBranchCode() {
			return txtBranchCode;
		}

		public void setTxtBranchCode(String txtBranchCode) {
			this.txtBranchCode = txtBranchCode;
		}

		public String getTxtBranchName() {
			return txtBranchName;
		}

		public void setTxtBranchName(String txtBranchName) {
			this.txtBranchName = txtBranchName;
		}

		public String getTxtState() {
			return txtState;
		}

		public void setTxtState(String txtState) {
			this.txtState = txtState;
		}

		public String getTxtCity() {
			return txtCity;
		}

		public void setTxtCity(String txtCity) {
			this.txtCity = txtCity;
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

		public String[][] getMapper() {
			String[][] input = { 
					{ "OtherBankObj", OTHERBANK_MAPPER },
					{ "theOBTrxContext", TRX_MAPPER }
			};
			return input;
		}

		public static final String OTHERBANK_MAPPER = "com.integrosys.cms.ui.otherbankbranch.OtherBankMapper";
		
		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
		
	}
