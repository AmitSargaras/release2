/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.fdr;

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

public class FDRForm extends TrxContextForm implements Serializable {

	private String contractId;

	private String fdrDate;

	private String accountNo;

	private String fdrCurrency;

	private String fdrAmount;

	private String referenceNo;

	private String remarks;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getFdrDate() {
		return fdrDate;
	}

	public void setFdrDate(String fdrDate) {
		this.fdrDate = fdrDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFdrCurrency() {
		return fdrCurrency;
	}

	public void setFdrCurrency(String fdrCurrency) {
		this.fdrCurrency = fdrCurrency;
	}

	public String getFdrAmount() {
		return fdrAmount;
	}

	public void setFdrAmount(String fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String[][] getMapper() {
		return new String[][] { { "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.fdr.FDRMapper" },
				{ "obFdrForm", "com.integrosys.cms.ui.contractfinancing.fdr.FDRMapper" }, };
	}
}
