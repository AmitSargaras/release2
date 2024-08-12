package com.integrosys.cms.ui.relationshipmgr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;


	/**
	 * Defines attributes for Relationship Manager
	 *
	 * @author $Author: Dattatray Thorat $
	 * @version $Revision: 1.0 $
	 * @since $Date: 2011-03-31 $
	 * Tag : $Name$
	 */
	public class RelationshipMgrForm extends TrxContextForm implements Serializable {
		
		static final long serialVersionUID = 0L;
		
		private String relationshipMgrCode ;
		private String relationshipMgrName;
	    private String relationshipMgrMailId;
	    private String relationshipMgrMobileNo;
	    private String region;
	    private String relationshipMgrCity;
	    private String relationshipMgrState;
	    private String wboRegion;
	    private String reportingHeadName;
	    private String reportingHeadEmployeeCode;
	    private String reportingHeadMailId;
	    private String reportingHeadMobileNo;
	    private String reportingHeadRegion;
	    private Set relationshipMgrHrmsSet;
	    
	    private String id ;
	    private long versionTime;
		private String status;
	    private String deprecated;
	    private String createdBy;
	    private Date creationDate;
	    private String lastUpdateBy;
	    private String lastUpdateDate;
	    
	    
	    private List regionList;
	    private String regionId;
	    
	    private String txtRMCode;
	    private String txtRMName;
	    
	    private FormFile fileUpload;
	    private String employeeId;
	    
	    private String cadBranchCode;
	    private String cadEmployeeCode;
		private String localCAD;
		
		private List localCADs;
		private List localCADsList;
/**
		 * @return the fileUpload
		 */
		
		
		public Set getRelationshipMgrHrmsSet() {
			return relationshipMgrHrmsSet;
		}


		public void setRelationshipMgrHrmsSet(Set relationshipMgrHrmsSet) {
			this.relationshipMgrHrmsSet = relationshipMgrHrmsSet;
		}
		
		
		public FormFile getFileUpload() {
			return fileUpload;
		}

		/**
		 * @param fileUpload the fileUpload to set
		 */
		public void setFileUpload(FormFile fileUpload) {
			this.fileUpload = fileUpload;
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

		public String getLastUpdateBy() {
			return lastUpdateBy;
		}

		public void setLastUpdateBy(String lastUpdateBy) {
			this.lastUpdateBy = lastUpdateBy;
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

		public static long getSerialversionuid() {
			return serialVersionUID;
		}


		/**
		 * @return the relationshipMgrCode
		 */
		public String getRelationshipMgrCode() {
			return relationshipMgrCode;
		}

		/**
		 * @param relationshipMgrCode the relationshipMgrCode to set
		 */
		public void setRelationshipMgrCode(String relationshipMgrCode) {
			this.relationshipMgrCode = relationshipMgrCode;
		}

		/**
		 * @return the relationshipMgrName
		 */
		public String getRelationshipMgrName() {
			return relationshipMgrName;
		}

		/**
		 * @param relationshipMgrName the relationshipMgrName to set
		 */
		public void setRelationshipMgrName(String relationshipMgrName) {
			this.relationshipMgrName = relationshipMgrName;
		}

		/**
		 * @return the reportingHeadName
		 */
		public String getReportingHeadName() {
			return reportingHeadName;
		}

		/**
		 * @param reportingHeadName the reportingHeadName to set
		 */
		public void setReportingHeadName(String reportingHeadName) {
			this.reportingHeadName = reportingHeadName;
		}

		/**
		 * @return the relationshipMgrMailId
		 */
		public String getRelationshipMgrMailId() {
			return relationshipMgrMailId;
		}

		/**
		 * @param relationshipMgrMailId the relationshipMgrMailId to set
		 */
		public void setRelationshipMgrMailId(String relationshipMgrMailId) {
			this.relationshipMgrMailId = relationshipMgrMailId;
		}

		/**
		 * @return the reportingHeadMailId
		 */
		public String getReportingHeadMailId() {
			return reportingHeadMailId;
		}

		/**
		 * @param reportingHeadMailId the reportingHeadMailId to set
		 */
		public void setReportingHeadMailId(String reportingHeadMailId) {
			this.reportingHeadMailId = reportingHeadMailId;
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

		public String getTxtRMCode() {
			return txtRMCode;
		}

		public void setTxtRMCode(String txtRMCode) {
			this.txtRMCode = txtRMCode;
		}

		public String getTxtRMName() {
			return txtRMName;
		}

		public void setTxtRMName(String txtRMName) {
			this.txtRMName = txtRMName;
		}
		
		public String getEmployeeId() {
			return employeeId;
		}

		public void setEmployeeId(String employeeId) {
			this.employeeId = employeeId;
		}		
		
		public String getRelationshipMgrMobileNo() {
			return relationshipMgrMobileNo;
		}

		public void setRelationshipMgrMobileNo(String relationshipMgrMobileNo) {
			this.relationshipMgrMobileNo = relationshipMgrMobileNo;
		}

		public String getRelationshipMgrCity() {
			return relationshipMgrCity;
		}

		public void setRelationshipMgrCity(String relationshipMgrCity) {
			this.relationshipMgrCity = relationshipMgrCity;
		}

		public String getRelationshipMgrState() {
			return relationshipMgrState;
		}

		public void setRelationshipMgrState(String relationshipMgrState) {
			this.relationshipMgrState = relationshipMgrState;
		}

		public String getWboRegion() {
			return wboRegion;
		}

		public void setWboRegion(String wboRegion) {
			this.wboRegion = wboRegion;
		}

		public String getReportingHeadEmployeeCode() {
			return reportingHeadEmployeeCode;
		}

		public void setReportingHeadEmployeeCode(String reportingHeadEmployeeCode) {
			this.reportingHeadEmployeeCode = reportingHeadEmployeeCode;
		}

		public String getReportingHeadMobileNo() {
			return reportingHeadMobileNo;
		}

		public void setReportingHeadMobileNo(String reportingHeadMobileNo) {
			this.reportingHeadMobileNo = reportingHeadMobileNo;
		}

		public String getReportingHeadRegion() {
			return reportingHeadRegion;
		}

		public void setReportingHeadRegion(String reportingHeadRegion) {
			this.reportingHeadRegion = reportingHeadRegion;
		}

	
		public String getCadBranchCode() {
			return cadBranchCode;
		}

		public void setCadBranchCode(String cadBranchCode) {
			this.cadBranchCode = cadBranchCode;
		}

		public String getCadEmployeeCode() {
			return cadEmployeeCode;
		}

		public void setCadEmployeeCode(String cadEmployeeCode) {
			this.cadEmployeeCode = cadEmployeeCode;
		}

		/**
		 * @return the RelationshipMgrMapper
		 */
		public static String getRelationshipMgrMapper() {
			return RELATIONSHIP_MGR_MAPPER;
		}
		
		public String getLocalCAD() {
			return localCAD;
		}

		public void setLocalCAD(String localCAD) {
			this.localCAD = localCAD;
		}
		

		public List getLocalCADs() {
			return localCADs;
		}

		public void setLocalCADs(List localCADs) {
			this.localCADs = localCADs;
		}

		public List getLocalCADsList() {
			return localCADsList;
		}

		public void setLocalCADsList(List localCADsList) {
			this.localCADsList = localCADsList;
		}

		/**
		 * @return the trxMapper
		 */
		public static String getTrxMapper() {
			return TRX_MAPPER;
		}

		public String[][] getMapper() {
			String[][] input = { 
					{ "RelationshipMgrObj", RELATIONSHIP_MGR_MAPPER },
					{ "theOBTrxContext", TRX_MAPPER }
			};
			return input;
		}

		public static final String RELATIONSHIP_MGR_MAPPER = "com.integrosys.cms.ui.relationshipmgr.RelationshipMgrMapper";
		
		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
		
	}
