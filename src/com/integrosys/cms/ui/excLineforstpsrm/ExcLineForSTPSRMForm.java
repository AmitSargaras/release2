package com.integrosys.cms.ui.excLineforstpsrm;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class ExcLineForSTPSRMForm  extends TrxContextForm implements Serializable, IExcLineForSTPSRMConstant {

	private String id;
	private String lineCode;
	private String excluded;
	private String versionTime;
	private String lastUpdateDate;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String status;
	private String deprecated;
	private String operationName;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLineCode() {
		return lineCode;
	}
	
	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}
	
	public String getExcluded() {
		return excluded;
	}
	
	public void setExcluded(String excluded) {
		this.excluded = excluded;
	}
	
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
	
	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}
	
	public String[][] getMapper() {
		String[][] input = 
		{
			{ POJO_OBJECT, MAIN_MAPPER},
			{ TRX_CONTEXT, TRX_MAPPER }
		};
		return input;

	}
	
}