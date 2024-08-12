package com.integrosys.cms.ui.limit.facility.multitierfin;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class MultiTierFinForm extends FacilityMainForm {

	
	private static final long serialVersionUID = -244208532057261889L;

	private String tierSeqNo;
	private String tierTerm;
	private String tierTermCode;
	private String rate;
	
	private String gracePeriod;
	private String rateNumber;
	private String rateVariance;
	private String varianceCode;
	private String paymentAmount;

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.multitierfin.MultiTierFinMapper";

	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}
	

	/**
	 * @return the tierSeqNo
	 */
	public String getTierSeqNo() {
		return tierSeqNo;
	}



	/**
	 * @param tierSeqNo the tierSeqNo to set
	 */
	public void setTierSeqNo(String tierSeqNo) {
		this.tierSeqNo = tierSeqNo;
	}



	/**
	 * @return the tierTerm
	 */
	public String getTierTerm() {
		return tierTerm;
	}



	/**
	 * @param tierTerm the tierTerm to set
	 */
	public void setTierTerm(String tierTerm) {
		this.tierTerm = tierTerm;
	}



	/**
	 * @return the tierTermCode
	 */
	public String getTierTermCode() {
		return tierTermCode;
	}



	/**
	 * @param tierTermCode the tierTermCode to set
	 */
	public void setTierTermCode(String tierTermCode) {
		this.tierTermCode = tierTermCode;
	}



	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}



	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}



	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}


	public String getGracePeriod() {
		return gracePeriod;
	}


	public void setGracePeriod(String gracePeriod) {
		this.gracePeriod = gracePeriod;
	}

	public String getRateNumber() {
		return rateNumber;
	}


	public void setRateNumber(String rateNumber) {
		this.rateNumber = rateNumber;
	}


	public String getRateVariance() {
		return rateVariance;
	}


	public void setRateVariance(String rateVariance) {
		this.rateVariance = rateVariance;
	}


	public String getVarianceCode() {
		return varianceCode;
	}


	public void setVarianceCode(String varianceCode) {
		this.varianceCode = varianceCode;
	}


	public String getPaymentAmount() {
		return paymentAmount;
	}


	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

}
