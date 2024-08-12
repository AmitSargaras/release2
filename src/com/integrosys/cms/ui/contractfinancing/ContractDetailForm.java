/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.contractfinancing;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for ContractFinancingAction.
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */

public class ContractDetailForm extends TrxContextForm implements Serializable {

	private String contractId;

	private String contractNumber;

	private String contractDate;

	private String awarderType;

	private String awarderTypeOthers;

	private String awarderName;

	private String contractType;

	private String contractTypeOthers;

	private String startDate;

	private String expiryDate;

	private String extendedDate;

	private String contractCurrency;

	private String contractAmount;

	private String actualFinanceCurrency;

	private String actualFinanceAmount;

	private String financePercent;

	private String financedAmount;

	private String projectedProfitCurrency;

	private String projectedProfitAmount;

	private String collectionAccount;

	private String projectAccount;

	private String facilityExpiryDate;

	private String sinkingFundInd;

	private String sinkingFundParty;

	private String buildUpFdr;

	private String contractDescription;

	private String remark;

	private String[] deletedBox;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractDate() {
		return contractDate;
	}

	public void setContractDate(String contractDate) {
		this.contractDate = contractDate;
	}

	public String getAwarderType() {
		return awarderType;
	}

	public void setAwarderType(String awarderType) {
		this.awarderType = awarderType;
	}

	public String getAwarderTypeOthers() {
		return awarderTypeOthers;
	}

	public void setAwarderTypeOthers(String awarderTypeOthers) {
		this.awarderTypeOthers = awarderTypeOthers;
	}

	public String getAwarderName() {
		return awarderName;
	}

	public void setAwarderName(String awarderName) {
		this.awarderName = awarderName;
	}

	public String getContractType() {
		return contractType;
	}

	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	public String getContractTypeOthers() {
		return contractTypeOthers;
	}

	public void setContractTypeOthers(String contractTypeOthers) {
		this.contractTypeOthers = contractTypeOthers;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(String extendedDate) {
		this.extendedDate = extendedDate;
	}

	public String getContractCurrency() {
		return contractCurrency;
	}

	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	public String getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(String contractAmount) {
		this.contractAmount = contractAmount;
	}

	public String getActualFinanceCurrency() {
		return actualFinanceCurrency;
	}

	public void setActualFinanceCurrency(String actualFinanceCurrency) {
		this.actualFinanceCurrency = actualFinanceCurrency;
	}

	public String getActualFinanceAmount() {
		return actualFinanceAmount;
	}

	public void setActualFinanceAmount(String actualFinanceAmount) {
		this.actualFinanceAmount = actualFinanceAmount;
	}

	public String getFinancePercent() {
		return financePercent;
	}

	public void setFinancePercent(String financePercent) {
		this.financePercent = financePercent;
	}

	public String getFinancedAmount() {
		return financedAmount;
	}

	public void setFinancedAmount(String financedAmount) {
		this.financedAmount = financedAmount;
	}

	public String getProjectedProfitCurrency() {
		return projectedProfitCurrency;
	}

	public void setProjectedProfitCurrency(String projectedProfitCurrency) {
		this.projectedProfitCurrency = projectedProfitCurrency;
	}

	public String getProjectedProfitAmount() {
		return projectedProfitAmount;
	}

	public void setProjectedProfitAmount(String projectedProfitAmount) {
		this.projectedProfitAmount = projectedProfitAmount;
	}

	public String getCollectionAccount() {
		return collectionAccount;
	}

	public void setCollectionAccount(String collectionAccount) {
		this.collectionAccount = collectionAccount;
	}

	public String getProjectAccount() {
		return projectAccount;
	}

	public void setProjectAccount(String projectAccount) {
		this.projectAccount = projectAccount;
	}

	public String getFacilityExpiryDate() {
		return facilityExpiryDate;
	}

	public void setFacilityExpiryDate(String facilityExpiryDate) {
		this.facilityExpiryDate = facilityExpiryDate;
	}

	public String getSinkingFundInd() {
		return sinkingFundInd;
	}

	public void setSinkingFundInd(String sinkingFundInd) {
		this.sinkingFundInd = sinkingFundInd;
	}

	public String getSinkingFundParty() {
		return sinkingFundParty;
	}

	public void setSinkingFundParty(String sinkingFundParty) {
		this.sinkingFundParty = sinkingFundParty;
	}

	public String getBuildUpFdr() {
		return buildUpFdr;
	}

	public void setBuildUpFdr(String buildUpFdr) {
		this.buildUpFdr = buildUpFdr;
	}

	public String getContractDescription() {
		return contractDescription;
	}

	public void setContractDescription(String contractDescription) {
		this.contractDescription = contractDescription;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
		return new String[][] { { "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.ContractDetailMapper" }, };
	}
}
