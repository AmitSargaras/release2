package com.integrosys.cms.ui.limit.facility.rentalrenewal;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainForm;

public class FacilityIslamicRentalRenewalForm extends FacilityMainForm {
	private static final long serialVersionUID = 1L;
	
	private String renewalTerm;
	private String renewalTermCode;
	private String renewalRate;
	private String primeRateNumber;
	private String primeVariance;
	private String primeVarianceCode;
	
	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.rentalrenewal.FacilityIslamicRentalRenewalMapper";
	
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}	
	
	public String getRenewalTerm() {
		return renewalTerm;
	}

	public void setRenewalTerm(String renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public String getRenewalTermCode() {
		return renewalTermCode;
	}

	public void setRenewalTermCode(String renewalTermCode) {
		this.renewalTermCode = renewalTermCode;
	}

	public String getRenewalRate() {
		return renewalRate;
	}

	public void setRenewalRate(String renewalRate) {
		this.renewalRate = renewalRate;
	}

	public String getPrimeRateNumber() {
		return primeRateNumber;
	}

	public void setPrimeRateNumber(String primeRateNumber) {
		this.primeRateNumber = primeRateNumber;
	}

	public String getPrimeVariance() {
		return primeVariance;
	}

	public void setPrimeVariance(String primeVariance) {
		this.primeVariance = primeVariance;
	}

	public String getPrimeVarianceCode() {
		return primeVarianceCode;
	}

	public void setPrimeVarianceCode(String primeVarianceCode) {
		this.primeVarianceCode = primeVarianceCode;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}	
}
