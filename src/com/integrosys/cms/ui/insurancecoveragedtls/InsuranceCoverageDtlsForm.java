package com.integrosys.cms.ui.insurancecoveragedtls;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.cms.ui.common.TrxContextForm;


	/**
	 * Defines attributes for Relationship Manager
	 *
	 * @author $Author: Dattatray Thorat $
	 * @version $Revision: 1.0 $
	 * @since $Date: 2011-03-31 $
	 * Tag : $Name$
	 */
	public class InsuranceCoverageDtlsForm extends TrxContextForm implements Serializable {
		
		static final long serialVersionUID = 0L;
		
		private String insuranceCoverageId ;
		private String insuranceCoverageCode ;
		private String insuranceType;
	    private String insuranceCategoryName;
	    
	    private String id ;
	    private long versionTime;
		private String status;
	    private String deprecated;
	    private String createdBy;
	    private String creationDate;
	    private String lastUpdateBy;
	    private String lastUpdateDate;
	    
		/**
		 * @return the insuranceCoverageCode
		 */
		public String getInsuranceCoverageCode() {
			return insuranceCoverageCode;
		}

		/**
		 * @param insuranceCoverageCode the insuranceCoverageCode to set
		 */
		public void setInsuranceCoverageCode(String insuranceCoverageCode) {
			this.insuranceCoverageCode = insuranceCoverageCode;
		}

		/**
		 * @return the insuranceCoverageId
		 */
		public String getInsuranceCoverageId() {
			return insuranceCoverageId;
		}

		/**
		 * @param insuranceCoverageId the insuranceCoverageId to set
		 */
		public void setInsuranceCoverageId(String insuranceCoverageId) {
			this.insuranceCoverageId = insuranceCoverageId;
		}

		/**
		 * @return the insuranceType
		 */
		public String getInsuranceType() {
			return insuranceType;
		}

		/**
		 * @param insuranceType the insuranceType to set
		 */
		public void setInsuranceType(String insuranceType) {
			this.insuranceType = insuranceType;
		}

		/**
		 * @return the insuranceCategoryName
		 */
		public String getInsuranceCategoryName() {
			return insuranceCategoryName;
		}

		/**
		 * @param insuranceCategoryName the insuranceCategoryName to set
		 */
		public void setInsuranceCategoryName(String insuranceCategoryName) {
			this.insuranceCategoryName = insuranceCategoryName;
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
		 * @return the RelationshipMgrMapper
		 */
		public static String getInsuranceCoverageDtlsMapper() {
			return INSURANCE_COVERAGE_DTLS_MAPPER;
		}

		/**
		 * @return the trxMapper
		 */
		public static String getTrxMapper() {
			return TRX_MAPPER;
		}

		public String[][] getMapper() {
			String[][] input = { 
					{ "InsuranceCoverageDtlsObj", INSURANCE_COVERAGE_DTLS_MAPPER },
					{ "theOBTrxContext", TRX_MAPPER }
			};
			return input;
		}

		public static final String INSURANCE_COVERAGE_DTLS_MAPPER = "com.integrosys.cms.ui.insurancecoveragedtls.InsuranceCoverageDtlsMapper";
		
		public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
		
	}
