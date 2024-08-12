/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.holiday;

import java.io.Serializable;

import org.apache.struts.upload.FormFile;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *@author $Author: Abhijit R$
 *Form Bean for Holiday Master
 */

public class MaintainHolidayForm extends TrxContextForm implements Serializable {

	private String description;
	private String startDate;
	private String endDate;
	private String isRecurrent;
 
	private String versionTime;

	private String lastUpdateDate;
	private String disableForSelection;
	
	
	private String creationDate;
	
	
	private String createBy;
	//private String lastUpdateDate;
	private String lastUpdateBy;
	
	
	
	private String status;
	private String deprecated;
	private String id;
	
  
   
	private FormFile fileUpload;
	
	public String[][] getMapper() {
		String[][] input = {  { "holidayObj", HOLIDAY_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}
	
	
	public static final String HOLIDAY_MAPPER = "com.integrosys.cms.ui.holiday.HolidayMapper";

	public static final String HOLIDAY_LIST_MAPPER = "com.integrosys.cms.ui.holiday.HolidayListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	
	
	
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
	public String getDisableForSelection() {
		return disableForSelection;
	}
	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDeprecated() {
		return deprecated;
	}
	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIsRecurrent() {
		return isRecurrent;
	}
	public void setIsRecurrent(String isRecurrent) {
		this.isRecurrent = isRecurrent;
	}
		public FormFile getFileUpload() {
		return fileUpload;
	}
	public void setFileUpload(FormFile fileUpload) {
		this.fileUpload = fileUpload;
	}
	
	

}
