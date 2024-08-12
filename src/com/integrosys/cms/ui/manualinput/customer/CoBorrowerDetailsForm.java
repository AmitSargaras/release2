package com.integrosys.cms.ui.manualinput.customer;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.manualinput.IManualInputConstants;

public class CoBorrowerDetailsForm extends CommonForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String coBorrowerLiabId;
	private String coBorrowerName;
	private String isInterfaced;

	private String coBorrowerLiabIdList;

	public String[][] getMapper() {
		String[][] input = { { IManualInputConstants.CO_BORROWER_DETAILS_KEY, CoBorrowerDetailsMapper.class.getName() } };
		return input;
	}

	public String getCoBorrowerLiabId() {
		return coBorrowerLiabId;
	}

	public void setCoBorrowerLiabId(String coBorrowerLiabId) {
		this.coBorrowerLiabId = coBorrowerLiabId;
	}

	public String getCoBorrowerName() {
		return coBorrowerName;
	}

	public void setCoBorrowerName(String coBorrowerName) {
		this.coBorrowerName = coBorrowerName;
	}

	public String getCoBorrowerLiabIdList() {
		return coBorrowerLiabIdList;
	}

	public void setCoBorrowerLiabIdList(String coBorrowerLiabIdList) {
		this.coBorrowerLiabIdList = coBorrowerLiabIdList;
	}

	public String getIsInterfaced() {
		return isInterfaced;
	}

	public void setIsInterfaced(String isInterfaced) {
		this.isInterfaced = isInterfaced;
	}

	
}
