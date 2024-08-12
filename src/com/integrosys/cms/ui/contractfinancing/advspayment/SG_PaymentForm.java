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

public class SG_PaymentForm extends TrxContextForm implements Serializable {

	private String advanceId;

	private String paymentId;

	private String receiveDate;

	private String receiveCurrency;

	private String receiveAmount;

	private String receiveFrom;

	private String distributeDate;

	private String distributeCurrency;

	private String distributeAmount;

	private String fdrInd;

	private String fdrCurrency;

	private String fdrAmount;

	private String repoInd;

	private String repoCurrency;

	private String repoAmount;

	private String apgInd;

	private String apgCurrency;

	private String apgAmount;

	private String tl1Ind;

	private String tl1Currency;

	private String tl1Amount;

	private String tl2Ind;

	private String tl2Currency;

	private String tl2Amount;

	private String tl3Ind;

	private String tl3Currency;

	private String tl3Amount;

	private String tl4Ind;

	private String tl4Currency;

	private String tl4Amount;

	private String bcInd;

	private String bcCurrency;

	private String bcAmount;

	private String collectionAccInd;

	private String collectionAccCurrency;

	private String collectionAccAmount;

	private String othInd;

	private String othCurrency;

	private String othAmount;

	private String remarks;

	private String[] deletedBox;

	public String getAdvanceId() {
		return advanceId;
	}

	public void setAdvanceId(String advanceId) {
		this.advanceId = advanceId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public String getReceiveCurrency() {
		return receiveCurrency;
	}

	public void setReceiveCurrency(String receiveCurrency) {
		this.receiveCurrency = receiveCurrency;
	}

	public String getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(String receiveAmount) {
		this.receiveAmount = receiveAmount;
	}

	public String getReceiveFrom() {
		return receiveFrom;
	}

	public void setReceiveFrom(String receiveFrom) {
		this.receiveFrom = receiveFrom;
	}

	public String getDistributeDate() {
		return distributeDate;
	}

	public void setDistributeDate(String distributeDate) {
		this.distributeDate = distributeDate;
	}

	public String getDistributeCurrency() {
		return distributeCurrency;
	}

	public void setDistributeCurrency(String distributeCurrency) {
		this.distributeCurrency = distributeCurrency;
	}

	public String getDistributeAmount() {
		return distributeAmount;
	}

	public void setDistributeAmount(String distributeAmount) {
		this.distributeAmount = distributeAmount;
	}

	public String getFdrInd() {
		return fdrInd;
	}

	public void setFdrInd(String fdrInd) {
		this.fdrInd = fdrInd;
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

	public String getRepoInd() {
		return repoInd;
	}

	public void setRepoInd(String repoInd) {
		this.repoInd = repoInd;
	}

	public String getRepoCurrency() {
		return repoCurrency;
	}

	public void setRepoCurrency(String repoCurrency) {
		this.repoCurrency = repoCurrency;
	}

	public String getRepoAmount() {
		return repoAmount;
	}

	public void setRepoAmount(String repoAmount) {
		this.repoAmount = repoAmount;
	}

	public String getApgInd() {
		return apgInd;
	}

	public void setApgInd(String apgInd) {
		this.apgInd = apgInd;
	}

	public String getApgCurrency() {
		return apgCurrency;
	}

	public void setApgCurrency(String apgCurrency) {
		this.apgCurrency = apgCurrency;
	}

	public String getApgAmount() {
		return apgAmount;
	}

	public void setApgAmount(String apgAmount) {
		this.apgAmount = apgAmount;
	}

	public String getTl1Ind() {
		return tl1Ind;
	}

	public void setTl1Ind(String tl1Ind) {
		this.tl1Ind = tl1Ind;
	}

	public String getTl1Currency() {
		return tl1Currency;
	}

	public void setTl1Currency(String tl1Currency) {
		this.tl1Currency = tl1Currency;
	}

	public String getTl1Amount() {
		return tl1Amount;
	}

	public void setTl1Amount(String tl1Amount) {
		this.tl1Amount = tl1Amount;
	}

	public String getTl2Ind() {
		return tl2Ind;
	}

	public void setTl2Ind(String tl2Ind) {
		this.tl2Ind = tl2Ind;
	}

	public String getTl2Currency() {
		return tl2Currency;
	}

	public void setTl2Currency(String tl2Currency) {
		this.tl2Currency = tl2Currency;
	}

	public String getTl2Amount() {
		return tl2Amount;
	}

	public void setTl2Amount(String tl2Amount) {
		this.tl2Amount = tl2Amount;
	}

	public String getTl3Ind() {
		return tl3Ind;
	}

	public void setTl3Ind(String tl3Ind) {
		this.tl3Ind = tl3Ind;
	}

	public String getTl3Currency() {
		return tl3Currency;
	}

	public void setTl3Currency(String tl3Currency) {
		this.tl3Currency = tl3Currency;
	}

	public String getTl3Amount() {
		return tl3Amount;
	}

	public void setTl3Amount(String tl3Amount) {
		this.tl3Amount = tl3Amount;
	}

	public String getTl4Ind() {
		return tl4Ind;
	}

	public void setTl4Ind(String tl4Ind) {
		this.tl4Ind = tl4Ind;
	}

	public String getTl4Currency() {
		return tl4Currency;
	}

	public void setTl4Currency(String tl4Currency) {
		this.tl4Currency = tl4Currency;
	}

	public String getTl4Amount() {
		return tl4Amount;
	}

	public void setTl4Amount(String tl4Amount) {
		this.tl4Amount = tl4Amount;
	}

	public String getBcInd() {
		return bcInd;
	}

	public void setBcInd(String bcInd) {
		this.bcInd = bcInd;
	}

	public String getBcCurrency() {
		return bcCurrency;
	}

	public void setBcCurrency(String bcCurrency) {
		this.bcCurrency = bcCurrency;
	}

	public String getBcAmount() {
		return bcAmount;
	}

	public void setBcAmount(String bcAmount) {
		this.bcAmount = bcAmount;
	}

	public String getCollectionAccInd() {
		return collectionAccInd;
	}

	public void setCollectionAccInd(String collectionAccInd) {
		this.collectionAccInd = collectionAccInd;
	}

	public String getCollectionAccCurrency() {
		return collectionAccCurrency;
	}

	public void setCollectionAccCurrency(String collectionAccCurrency) {
		this.collectionAccCurrency = collectionAccCurrency;
	}

	public String getCollectionAccAmount() {
		return collectionAccAmount;
	}

	public void setCollectionAccAmount(String collectionAccAmount) {
		this.collectionAccAmount = collectionAccAmount;
	}

	public String getOthInd() {
		return othInd;
	}

	public void setOthInd(String othInd) {
		this.othInd = othInd;
	}

	public String getOthCurrency() {
		return othCurrency;
	}

	public void setOthCurrency(String othCurrency) {
		this.othCurrency = othCurrency;
	}

	public String getOthAmount() {
		return othAmount;
	}

	public void setOthAmount(String othAmount) {
		this.othAmount = othAmount;
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
				{ "contractFinancingObj", "com.integrosys.cms.ui.contractfinancing.advspayment.SG_PaymentMapper" },
				{ "obPaymentForm", "com.integrosys.cms.ui.contractfinancing.advspayment.SG_PaymentMapper" },
				{ "obAdvanceForm", "com.integrosys.cms.ui.contractfinancing.advspayment.SG_AdvanceMapper" }, };
	}
}
