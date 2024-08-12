package com.integrosys.cms.app.limit.bus;

/**
 * @author Chong Jun Yong
 * 
 */
public class OBFacilityOfficer implements IFacilityOfficer {
	private long id;

	private long facilityMasterId;

	private Long cmsRefId;

	private String relationshipCodeCategoryCode;

	private String relationshipCodeEntryCode;

	private Long sequenceNumber;

	private Long hostSequenceNumber;

	private String officerTypeCategoryCode;

	private String officerTypeEntryCode;

	private String officerCategoryCode;

	private String officerEntryCode;

	private String status;

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId() {
		return facilityMasterId;
	}

	/**
	 * @return the hostSequenceNumber
	 */
	public Long getHostSequenceNumber() {
		return hostSequenceNumber;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the officerCategoryCode
	 */
	public String getOfficerCategoryCode() {
		return officerCategoryCode;
	}

	/**
	 * @return the officerEntryCode
	 */
	public String getOfficerEntryCode() {
		return officerEntryCode;
	}

	/**
	 * @return the officerTypeCategoryCode
	 */
	public String getOfficerTypeCategoryCode() {
		return officerTypeCategoryCode;
	}

	/**
	 * @return the officerTypeEntryCode
	 */
	public String getOfficerTypeEntryCode() {
		return officerTypeEntryCode;
	}

	/**
	 * @return the relationshipCodeCategoryCode
	 */
	public String getRelationshipCodeCategoryCode() {
		return relationshipCodeCategoryCode;
	}

	/**
	 * @return the relationshipCodeEntryCode
	 */
	public String getRelationshipCodeEntryCode() {
		return relationshipCodeEntryCode;
	}

	/**
	 * @return the sequenceNumber
	 */
	public Long getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId) {
		this.facilityMasterId = facilityMasterId;
	}

	/**
	 * @param hostSequenceNumber the hostSequenceNumber to set
	 */
	public void setHostSequenceNumber(Long hostSequenceNumber) {
		this.hostSequenceNumber = hostSequenceNumber;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param officerCategoryCode the officerCategoryCode to set
	 */
	public void setOfficerCategoryCode(String officerCategoryCode) {
		this.officerCategoryCode = officerCategoryCode;
	}

	/**
	 * @param officerEntryCode the officerEntryCode to set
	 */
	public void setOfficerEntryCode(String officerEntryCode) {
		this.officerEntryCode = officerEntryCode;
	}

	/**
	 * @param officerTypeCategoryCode the officerTypeCategoryCode to set
	 */
	public void setOfficerTypeCategoryCode(String officerTypeCategoryCode) {
		this.officerTypeCategoryCode = officerTypeCategoryCode;
	}

	/**
	 * @param officerTypeEntryCode the officerTypeEntryCode to set
	 */
	public void setOfficerTypeEntryCode(String officerTypeEntryCode) {
		this.officerTypeEntryCode = officerTypeEntryCode;
	}

	/**
	 * @param relationshipCodeCategoryCode the relationshipCodeCategoryCode to
	 *        set
	 */
	public void setRelationshipCodeCategoryCode(String relationshipCodeCategoryCode) {
		this.relationshipCodeCategoryCode = relationshipCodeCategoryCode;
	}

	/**
	 * @param relationshipCodeEntryCode the relationshipCodeEntryCode to set
	 */
	public void setRelationshipCodeEntryCode(String relationshipCodeEntryCode) {
		this.relationshipCodeEntryCode = relationshipCodeEntryCode;
	}

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Long getCmsRefId() {
		return cmsRefId;
	}

	public void setCmsRefId(Long cmsRefId) {
		this.cmsRefId = cmsRefId;
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((officerEntryCode == null) ? 0 : officerEntryCode.hashCode());
		result = prime * result + ((officerTypeEntryCode == null) ? 0 : officerTypeEntryCode.hashCode());
		result = prime * result + ((relationshipCodeEntryCode == null) ? 0 : relationshipCodeEntryCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		OBFacilityOfficer other = (OBFacilityOfficer) obj;
		if (officerEntryCode == null) {
			if (other.officerEntryCode != null) {
				return false;
			}
		}
		else if (!officerEntryCode.equals(other.officerEntryCode)) {
			return false;
		}

		if (officerTypeEntryCode == null) {
			if (other.officerTypeEntryCode != null) {
				return false;
			}
		}
		else if (!officerTypeEntryCode.equals(other.officerTypeEntryCode)) {
			return false;
		}

		if (relationshipCodeEntryCode == null) {
			if (other.relationshipCodeEntryCode != null) {
				return false;
			}
		}
		else if (!relationshipCodeEntryCode.equals(other.relationshipCodeEntryCode)) {
			return false;
		}

		if (status == null) {
			if (other.getStatus() != null) {
				return false;
			}
		}
		else if (!status.equals(other.getStatus())) {
			return false;
		}

		return true;
	}
}
