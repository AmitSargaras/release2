/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.advspayment;

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

public class AdvsPaymentForm extends TrxContextForm implements Serializable {

	private String contractId;

	private String facilityTypeId;

	private String facilityType;

	private String referenceNo;

	private String drawdownDate;

	private String tenorUom;

	private String tenorPeriod;

	private String claimCurrency;

	private String claimAmount;

	private String advanceCurrency;

	private String advanceAmount;

	private String expiryDate;

	private String operativeLimitCurrency;

	private String operativeLimitAmount;

	private String[] deletedBox;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getFacilityTypeId() {
		return facilityTypeId;
	}

	public void setFacilityTypeId(String facilityTypeId) {
		this.facilityTypeId = facilityTypeId;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getDrawdownDate() {
		return drawdownDate;
	}

	public void setDrawdownDate(String drawdownDate) {
		this.drawdownDate = drawdownDate;
	}

	public String getTenorUom() {
		return tenorUom;
	}

	public void setTenorUom(String tenorUom) {
		this.tenorUom = tenorUom;
	}

	public String getTenorPeriod() {
		return tenorPeriod;
	}

	public void setTenorPeriod(String tenorPeriod) {
		this.tenorPeriod = tenorPeriod;
	}

	public String getClaimCurrency() {
		return claimCurrency;
	}

	public void setClaimCurrency(String claimCurrency) {
		this.claimCurrency = claimCurrency;
	}

	public String getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}

	public String getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(String advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public String getAdvanceCurrency() {
		return advanceCurrency;
	}

	public void setAdvanceCurrency(String advanceCurrency) {
		this.advanceCurrency = advanceCurrency;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getOperativeLimitAmount() {
		return operativeLimitAmount;
	}

	public void setOperativeLimitAmount(String operativeLimitAmount) {
		this.operativeLimitAmount = operativeLimitAmount;
	}

	public String getOperativeLimitCurrency() {
		return operativeLimitCurrency;
	}

	public void setOperativeLimitCurrency(String operativeLimitCurrency) {
		this.operativeLimitCurrency = operativeLimitCurrency;
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
				{ "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.advspayment.AdvanceMapper" },
				{ "obAdvanceForm", "com.integrosys.cms.ui.contractfinancing.advspayment.AdvanceMapper" }, };
	}
}
