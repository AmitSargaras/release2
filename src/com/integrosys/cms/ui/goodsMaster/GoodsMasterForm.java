package com.integrosys.cms.ui.goodsMaster;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

public class GoodsMasterForm extends TrxContextForm implements Serializable{

	private String goodsCode;
	private String goodsName;
	private String goodsParentCode;

	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	
	private String status;
	private String deprecated;
	private String id;
	private String cpsId;
	private String operationName;
	private String restrictionType;
	
	
	public String getGoodsParentCode() {
		return goodsParentCode;
	}


	public void setGoodsParentCode(String goodsParentCode) {
		this.goodsParentCode = goodsParentCode;
	}

	public String getRestrictionType() {
		return restrictionType;
	}

	public void setRestrictionType(String restrictionType) {
		this.restrictionType = restrictionType;
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


	public String getDisableForSelection() {
		return disableForSelection;
	}


	public void setDisableForSelection(String disableForSelection) {
		this.disableForSelection = disableForSelection;
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


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getCpsId() {
		return cpsId;
	}


	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}


	public String getOperationName() {
		return operationName;
	}


	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public static final String GOODS_MASTER_MAPPER = "com.integrosys.cms.ui.goodsMaster.GoodsMasterMapper";
	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";
	
	
	public String[][] getMapper() {
		String[][] input = {  { "goodsMasterObj", GOODS_MASTER_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER }
		};
		return input;

	}


	public String getGoodsCode() {
		return goodsCode;
	}


	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}


	public String getGoodsName() {
		return goodsName;
	}


	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
}
