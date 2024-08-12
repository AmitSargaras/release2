/**

 * Copyright Integro Technologies Pte Ltd

 * $Header:

 */

package com.integrosys.cms.ui.manualinput.partygroup;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 *$Author: Bharat Waghela $ Form Bean for System Bank Master
 */

public class MIPartyGroupForm extends TrxContextForm implements Serializable {

	private String partyCode;
	private String partyName;
	private String groupExpLimit;

	private String versionTime;
	private String lastUpdateDate;
	private String disableForSelection;
	private String creationDate;
	private String createBy;
	private String lastUpdateBy;
	private String status;
	private String deprecated;
	private String masterId;
	private String id;
	private String cpsId;
	private String operationName;

	public String[][] getMapper() {
		String[][] input = { { "partyGroupObj", PARTY_GROUP_MAPPER },
				{ "theOBTrxContext", TRX_MAPPER } };
		return input;

	}

	public static final String PARTY_GROUP_MAPPER = "com.integrosys.cms.ui.manualinput.partygroup.PartyGroupMapper";

	public static final String PARTY_GROUP_LIST_MAPPER = "com.integrosys.cms.ui.systemBank.PartyGroupListMapper";

	public static final String TRX_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getGroupExpLimit() {
		return groupExpLimit;
	}

	public void setGroupExpLimit(String groupExpLimit) {
		this.groupExpLimit = groupExpLimit;
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

	public String getMasterId() {
		return masterId;
	}

	public void setMasterId(String masterId) {
		this.masterId = masterId;
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

	
}
