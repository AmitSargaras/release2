package com.integrosys.cms.batch.mtmvalprocess.fd.upload;

import java.util.ArrayList;
import java.util.List;

public enum FixedDepositFileUploadHeader {

	PARTY_ID("PARTY ID"), PARTY_NAME("PARTY NAME"),  SEGMENT_NAME("SEGMENT NAME"), BRANCH_CODE("BRANCH CODE"), 
	SOURCE_SEC_ID("SOURCE SECURITY ID"), FD_AMOUNT("FD AMOUNT"), CUSTOMER_ID("CUSTOMER ID"), FD_RECEIPT_NUMBER("FD RECEIPT NUMBER"),
	MATURITY_DATE("MATURITY DATE"), DEPOSIT_DATE("DEPOSIT DATE"), FD_LIEN_AMT("FD LIEN AMOUNT"), INTEREST_RATE("INTEREST RATE"),
	CURRENCY_CODE("CURRENCY CODE"), RM_NAME("RM NAME"), RM_REGION("RM REGION"), DEPOSITOR_NAME("DEPOSITOR NAME"),
	FACILITY_NAME("FACILITY NAME"), LINE_NO("LINE NUMBER"), SERIAL_NO("SERIAL NUMBER"), REMARKS("REMARKS"),
	LCN("LCN"), RELEASED_AMOUNT("RELEASED AMOUNT"), ASSETS_TYPE("ASSETS TYPE"), WEIGHTED_AVG_INTEREST("WEIGHTED AVERAGE INTEREST"),
	SPREAD("SPREAD"), EFFECTIVE_RATE("EFFECTIVE RATE"), EDITED_BY("EDITED BY"), EDITED_ON("EDITED ON"),
	LAST_APPROVED_BY("LAST APPROVED BY"), LAST_APPROVED_ON("LAST APPROVED ON"), FD_REBOOKING("FD REBOOKING");
	
	private final String headerName;
	
	FixedDepositFileUploadHeader(String headerName){
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
	
	public static List<String> getHeaderNames() {
		FixedDepositFileUploadHeader[] headers = values();
		
		List<String> headerNames = new ArrayList<String>();
		for(FixedDepositFileUploadHeader header : headers) {
			headerNames.add(header.getHeaderName());
		}
		
		return headerNames;
	}

}
