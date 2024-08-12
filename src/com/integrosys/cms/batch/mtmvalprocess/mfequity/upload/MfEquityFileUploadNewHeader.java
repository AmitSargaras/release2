package com.integrosys.cms.batch.mtmvalprocess.mfequity.upload;

import java.util.ArrayList;
import java.util.List;

public enum MfEquityFileUploadNewHeader {

	PARTY_ID("PARTY ID"), PARTY_NAME("PARTY NAME"),  SEGMENT_NAME("SEGMENT NAME"), BRANCH_CODE("BRANCH CODE"), 
	SOURCE_SEC_ID("SOURCE SECURITY ID"),  SECURITY_SUB_TYPE("SECURITY SUB TYPE"), COLLATERAL_CODE("COLLATERAL CODE"), CURRENCY_CODE("CURRENCY CODE"),
	REMARK("REMARKS"), CUSTOMER_ID("CUSTOMER ID"), ISIN("ISIN"), SCHEME_NAME ("SCHEME NAME"), UNITS("UNITS"),
	COLLATERAL_CODE_NAME("COLLATERAL CODE NAME"), LINE_NO("LINE NO"), SERIAL_NO("SERIAL NO"),
	LTV("LTV (%)"), FAS_NO("FAS NUMBER"), UNIT_PRICE("UNIT PRICE"), TOTAL_VALUE("TOTAL VALUE"), INVESTMENT_ACCOUNT_NUMBER("INVESTMENT ACCOUNT NUMBER");
	
	private final String headerName;
	
	MfEquityFileUploadNewHeader(String headerName){
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
	
	public static List<String> getHeaderNames() {
		MfEquityFileUploadNewHeader[] headers = values();
		
		List<String> headerNames = new ArrayList<String>();
		for(MfEquityFileUploadNewHeader header : headers) {
			headerNames.add(header.getHeaderName());
		}
		
		return headerNames;
	}

}
