package com.integrosys.cms.ui.geography;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class GeographyForm extends TrxContextForm implements Serializable{
	
static final long serialVersionUID = 0L;
	
	private String id;
	private String codeName;
    private String codeDesc;
    private String codeType;
    private String parentId;
    private String parentCountry;
    private String geographyId;
    private String regionId;
    
    private String status;
    private String versionTime;
    private String creationDate;
    private String createBy;
    private String updateDate;
    private String updateBy;
    private String deprecated;
    private String masterId;
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentCountry() {
		return parentCountry;
	}

	public void setParentCountry(String parentCountry) {
		this.parentCountry = parentCountry;
	}

	public String getGeographyId() {
		return geographyId;
	}

	public void setGeographyId(String geographyId) {
		this.geographyId = geographyId;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(String versionTime) {
		this.versionTime = versionTime;
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

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(String deprecated) {
		this.deprecated = deprecated;
	}

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}

	public String[][] getMapper() {

		String[][] input = {
					{ "OBTrxContext", TRX_MAPPER },
					{ "geographyObj", GEOGRPAHY_MAPPER }		
				};
		return input;
	}

	public static final String GEOGRPAHY_MAPPER = "com.integrosys.cms.ui.geography.GeographyMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";


}
