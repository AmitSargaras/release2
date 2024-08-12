package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

public interface IFacilityOfficer extends Serializable {

	/**
	 * @return the facilityMasterId
	 */
	public long getFacilityMasterId();

	/**
	 * @return the hostSequenceNumber
	 */
	public Long getHostSequenceNumber();

	/**
	 * @return the id
	 */
	public long getId();

	public Long getCmsRefId();

	/**
	 * @return the officerCategoryCode
	 */
	public String getOfficerCategoryCode();

	/**
	 * @return the officerEntryCode
	 */
	public String getOfficerEntryCode();

	/**
	 * @return the officerTypeCategoryCode
	 */
	public String getOfficerTypeCategoryCode();

	/**
	 * @return the officerTypeEntryCode
	 */
	public String getOfficerTypeEntryCode();

	/**
	 * @return the relationshipCodeCategoryCode
	 */
	public String getRelationshipCodeCategoryCode();

	/**
	 * @return the relationshipCodeEntryCode
	 */
	public String getRelationshipCodeEntryCode();

	/**
	 * @return the sequenceNumber
	 */
	public Long getSequenceNumber();

	/**
	 * @param facilityMasterId the facilityMasterId to set
	 */
	public void setFacilityMasterId(long facilityMasterId);

	/**
	 * @param hostSequenceNumber the hostSequenceNumber to set
	 */
	public void setHostSequenceNumber(Long hostSequenceNumber);

	/**
	 * @param id the id to set
	 */
	public void setId(long id);

	public void setCmsRefId(Long cmsRefId);

	/**
	 * @param officerCategoryCode the officerCategoryCode to set
	 */
	public void setOfficerCategoryCode(String officerCategoryCode);

	/**
	 * @param officerEntryCode the officerEntryCode to set
	 */
	public void setOfficerEntryCode(String officerEntryCode);

	/**
	 * @param officerTypeCategoryCode the officerTypeCategoryCode to set
	 */
	public void setOfficerTypeCategoryCode(String officerTypeCategoryCode);

	/**
	 * @param officerTypeEntryCode the officerTypeEntryCode to set
	 */
	public void setOfficerTypeEntryCode(String officerTypeEntryCode);

	/**
	 * @param relationshipCodeCategoryCode the relationshipCodeCategoryCode to
	 *        set
	 */
	public void setRelationshipCodeCategoryCode(String relationshipCodeCategoryCode);

	/**
	 * @param relationshipCodeEntryCode the relationshipCodeEntryCode to set
	 */
	public void setRelationshipCodeEntryCode(String relationshipCodeEntryCode);

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Long sequenceNumber);

	/**
	 * @return the status
	 */
	public String getStatus();

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status);
}
