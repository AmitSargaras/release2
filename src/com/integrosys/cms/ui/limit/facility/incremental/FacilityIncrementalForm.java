package com.integrosys.cms.ui.limit.facility.incremental;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.limit.facility.common.FacilityIncrementalReductionCommonForm;

public class FacilityIncrementalForm extends FacilityIncrementalReductionCommonForm {

	public static final String MAPPER = "com.integrosys.cms.ui.limit.facility.incremental.FacilityIncrementalMapper";
	
	public String[][] getMapper() {
		return new String[][] { { MAPPER, MAPPER },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" } };
	}		
	
	private String purpose;
	private String offerDate;
	private String primeReviewDate;
	private String primeReviewTerm;
	private String primeReviewTermCode;
	private String dealerNumber;
	private String dateSendToDecisionCenter;
	private String dateReceiveFromDecisionCenter;
	private String dateApproveByCGCBNM;
	
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	public String getOfferDate() {
		return offerDate;
	}
	public void setOfferDate(String offerDate) {
		this.offerDate = offerDate;
	}
	public String getPrimeReviewDate() {
		return primeReviewDate;
	}
	public void setPrimeReviewDate(String primeReviewDate) {
		this.primeReviewDate = primeReviewDate;
	}
	public String getPrimeReviewTerm() {
		return primeReviewTerm;
	}
	public void setPrimeReviewTerm(String primeReviewTerm) {
		this.primeReviewTerm = primeReviewTerm;
	}
	public String getPrimeReviewTermCode() {
		return primeReviewTermCode;
	}
	public void setPrimeReviewTermCode(String primeReviewTermCode) {
		this.primeReviewTermCode = primeReviewTermCode;
	}
	public String getDealerNumber() {
		return dealerNumber;
	}
	public void setDealerNumber(String dealerNumber) {
		this.dealerNumber = dealerNumber;
	}
	public String getDateSendToDecisionCenter() {
		return dateSendToDecisionCenter;
	}
	public void setDateSendToDecisionCenter(String dateSendToDecisionCenter) {
		this.dateSendToDecisionCenter = dateSendToDecisionCenter;
	}
	public String getDateReceiveFromDecisionCenter() {
		return dateReceiveFromDecisionCenter;
	}
	public void setDateReceiveFromDecisionCenter(
			String dateReceiveFromDecisionCenter) {
		this.dateReceiveFromDecisionCenter = dateReceiveFromDecisionCenter;
	}
	public String getDateApproveByCGCBNM() {
		return dateApproveByCGCBNM;
	}
	public void setDateApproveByCGCBNM(String dateApproveByCGCBNM) {
		this.dateApproveByCGCBNM = dateApproveByCGCBNM;
	}	
	
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}		
}
