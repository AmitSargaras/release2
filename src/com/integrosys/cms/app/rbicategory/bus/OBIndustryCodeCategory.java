package com.integrosys.cms.app.rbicategory.bus;

/**
 * @author  Govind.Sahu
 */
public class OBIndustryCodeCategory  implements IIndustryNameStage{
    private long id;
    private long rbiCategoryId;
	private String rbiCodeCategoryId;
	private long versionTime;
	private OBRbiCategory oBRbiCategory;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the rbiCodeCategoryId
	 */
	public String getRbiCodeCategoryId() {
		return rbiCodeCategoryId;
	}
	/**
	 * @param rbiCodeCategoryId the rbiCodeCategoryId to set
	 */
	public void setRbiCodeCategoryId(String rbiCodeCategoryId) {
		this.rbiCodeCategoryId = rbiCodeCategoryId;
	}
	/**
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
	 * @return the oBRbiCategory
	 */
	public OBRbiCategory getOBRbiCategory() {
		return oBRbiCategory;
	}
	/**
	 * @param rbiCategory the oBRbiCategory to set
	 */
	public void setOBRbiCategory(OBRbiCategory rbiCategory) {
		oBRbiCategory = rbiCategory;
	}
	/**
	 * @return the rbiCategoryId
	 */
	public long getRbiCategoryId() {
		return rbiCategoryId;
	}
	/**
	 * @param rbiCategoryId the rbiCategoryId to set
	 */
	public void setRbiCategoryId(long rbiCategoryId) {
		this.rbiCategoryId = rbiCategoryId;
	}

	
	
}
