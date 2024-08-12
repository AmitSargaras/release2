package com.integrosys.cms.ui.rbicategory;

import java.io.Serializable;
import java.util.List;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Govind.Sahu$
 *Form Bean for Rbi Category Master
 */

public class RbiCategoryForm extends TrxContextForm implements Serializable {

	private String id;
	private String industryNameId;
	private String rbiCodeCategorys;
	private String appendRbiCodeCategoryList;
	private String versionTime;
	private String lastUpdateDate;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String status;
	private String deprecated;
	private String allListrbiCodeCategory;



	public String getAllListrbiCodeCategory() {
		return allListrbiCodeCategory;
	}

	public void setAllListrbiCodeCategory(String allListrbiCodeCategory) {
		this.allListrbiCodeCategory = allListrbiCodeCategory;
	}

	public String[][] getMapper() {
		String[][] input = {  { "rbiCategoryObj", RBI_CATEGORY_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER },

		};
		return input;

	}
	
	
	public static final String RBI_CATEGORY_MAPPER = "com.integrosys.cms.ui.rbicategory.RbiCategoryMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

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
	 * @return the industryNameId
	 */
	public String getIndustryNameId() {
		return industryNameId;
	}

	/**
	 * @param industryNameId the industryNameId to set
	 */
	public void setIndustryNameId(String industryNameId) {
		this.industryNameId = industryNameId;
	}

	/**
	 * @return the appendRbiCodeCategoryList
	 */
	public String getAppendRbiCodeCategoryList() {
		return appendRbiCodeCategoryList;
	}

	/**
	 * @param appendRbiCodeCategoryList the appendRbiCodeCategoryList to set
	 */
	public void setAppendRbiCodeCategoryList(String appendRbiCodeCategoryList) {
		this.appendRbiCodeCategoryList = appendRbiCodeCategoryList;
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
	 * @return the createBy
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param createBy the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
	 * @return the rbiCodeCategorys
	 */
	public String getRbiCodeCategorys() {
		return rbiCodeCategorys;
	}

	/**
	 * @param rbiCodeCategorys the rbiCodeCategorys to set
	 */
	public void setRbiCodeCategorys(String rbiCodeCategorys) {
		this.rbiCodeCategorys = rbiCodeCategorys;
	}
}
