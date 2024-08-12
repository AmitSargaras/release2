/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * ActionForm for ContractFinancingAction.
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */

public class FacilityTypeForm extends TrxContextForm implements Serializable {

	private String contractId;

	private String facilityType;

	private String facilityOthers;

	private String facilityDate;

	private String moa;

	private String maxCapCurrency;

	private String maxCapAmount;

	private String remarks;

	private String[] deletedBox;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getFacilityOthers() {
		return facilityOthers;
	}

	public void setFacilityOthers(String facilityOthers) {
		this.facilityOthers = facilityOthers;
	}

	public String getFacilityDate() {
		return facilityDate;
	}

	public void setFacilityDate(String facilityDate) {
		this.facilityDate = facilityDate;
	}

	public String getMoa() {
		return moa;
	}

	public void setMoa(String moa) {
		this.moa = moa;
	}

	public String getMaxCapCurrency() {
		return maxCapCurrency;
	}

	public void setMaxCapCurrency(String maxCapCurrency) {
		this.maxCapCurrency = maxCapCurrency;
	}

	public String getMaxCapAmount() {
		return maxCapAmount;
	}

	public void setMaxCapAmount(String maxCapAmount) {
		this.maxCapAmount = maxCapAmount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[] getDeletedBox() {
		return deletedBox;
	}

	public void setDeletedBox(String[] deletedBox) {
		this.deletedBox = deletedBox;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String[][] getMapper() {
		return new String[][] {
				{ "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.FacilityTypeMapper" },
				{ "obFacilityTypeForm", "com.integrosys.cms.ui.contractfinancing.FacilityTypeMapper" }, };
	}
}
