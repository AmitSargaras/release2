/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing.tnc;

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

public class TNCForm extends TrxContextForm implements Serializable {

	private String contractId;

	private String terms;

	private String termsOthers;

	private String tncDate;

	private String conditions;

	private String remarks;

	private String[] deletedBox;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getTerms() {
		return terms;
	}

	public void setTerms(String terms) {
		this.terms = terms;
	}

	public String getTermsOthers() {
		return termsOthers;
	}

	public void setTermsOthers(String termsOthers) {
		this.termsOthers = termsOthers;
	}

	public String getTncDate() {
		return tncDate;
	}

	public void setTncDate(String tncDate) {
		this.tncDate = tncDate;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
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
		return new String[][] { { "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.tnc.TNCMapper" },
				{ "obTncForm", "com.integrosys.cms.ui.contractfinancing.tnc.TNCMapper" }, };
	}
}
