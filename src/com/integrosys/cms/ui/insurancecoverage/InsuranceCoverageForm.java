package com.integrosys.cms.ui.insurancecoverage;

import java.io.Serializable;
import java.util.Date;

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
	public class InsuranceCoverageForm extends TrxContextForm implements Serializable {
		
		static final long serialVersionUID = 0L;
		
		private String insuranceCoverageCode ;
		private String companyName;
	    private String address;
	    private String contactNumber;
	    
	    private String id ;
	    private long versionTime;
		private String status;
	    private String deprecated;
	    private String createdBy;
	    private Date creationDate;
	    private String lastUpdateBy;
	    private String lastUpdateDate;
	    
	    private String txtICCode;
	    private String txtCompanyName;
	    
	    private FormFile fileUpload;
	    
		/**
		 * @return the insuranceCoverageCode
		 */
		public String getInsuranceCoverageCode() {
			return insuranceCoverageCode;
		}

		/**
		 * @return the fileUpload
		 */
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
		 * @param insuranceCoverageCode the insuranceCoverageCode to set
		 */
		public void setInsuranceCoverageCode(String insuranceCoverageCode) {
			this.insuranceCoverageCode = insuranceCoverageCode;
		}

		/**
		 * @return the companyName
		 */
		public String getCompanyName() {
			return companyName;
		}

		/**
		 * @param companyName the companyName to set
		 */
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
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
		 * @return the contactNumber
		 */
		public String getContactNumber() {
			return contactNumber;
		}

		/**
		 * @param contactNumber the contactNumber to set
		 */
		public void setContactNumber(String contactNumber) {
			this.contactNumber = contactNumber;
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

		public String getTxtICCode() {
			return txtICCode;
		}

		public void setTxtICCode(String txtICCode) {
			this.txtICCode = txtICCode;
		}

		public String getTxtCompanyName() {
			return txtCompanyName;
		}

		public void setTxtCompanyName(String txtCompanyName) {
			this.txtCompanyName = txtCompanyName;
		}

		public static long getSerialversionuid() {
			return serialVersionUID;
		}


		/**
		 * @return the RelationshipMgrMapper
		 */
		public static String getRelationshipMgrMapper() {
			return INSURANCE_COVERAGE_MAPPER;
		}

		/**
		 * @return the trxMapper
		 */
		public static String getTrxMapper() {
			return TRX_MAPPER;
		}

		public String[][] getMapper() {
			String[][] input = { 
					{ "InsuranceCoverageObj", INSURANCE_COVERAGE_MAPPER },
					{ "theOBTrxContext", TRX_MAPPER }
			};
			return input;
		}

		public static final String INSURANCE_COVERAGE_MAPPER = "com.integrosys.cms.ui.insurancecoverage.InsuranceCoverageMapper";
		
		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
		
	}
