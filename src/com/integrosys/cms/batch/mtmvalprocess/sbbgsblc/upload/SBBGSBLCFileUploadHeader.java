package com.integrosys.cms.batch.mtmvalprocess.sbbgsblc.upload;

import java.util.ArrayList;
import java.util.List;

public enum SBBGSBLCFileUploadHeader {

	REGION("REGION"), PARTY_ID("PARTY ID"), CUSTOMER_ID("CUSTOMER ID"), PARTY_NAME("PARTY NAME"),
	RM_NAME("RM NAME"), MARGIN("MARGIN"), SEGMENT("SEGMENT"), SOURCE_SEC_ID("SOURCE SECURITY ID"),
	SECURITY_TYPE("SECURITY TYPE"), SECURITY_SUB_TYPE("SECURITY SUB TYPE"), CURRENCY_CODE("CURRENCY CODE"),
	SECURITY_OMV("SECURITY OMV"), LINE_LEVEL_SEC_OMV("LINE LEVEL SECURITY OMV"), ADDRESS("ADDRESS"),
	RAM_ID("RAM ID"), START_DATE("START DATE"), LINE_NO("LINE NO"), LCN_NO("LCN"),
	SERIAL_NO("SERIAL NUMBER"), SYSTEM_NAME("SYSTEM NAME"), BANK_NAME("BANK NAME"),
	BRANCH_NAME("BRANCH NAME"), REFERENCE_NO("REFERENCE NO"), DESCRIPTION_OF_ASSETS("DESCRIPTION OF ASSETS"),
	MATURITY_DATE("MATURITY DATE"), FOLLOW_UP_DATE("FOLLOWUP DATE");
	
	private final String headerName;
	
	SBBGSBLCFileUploadHeader(String headerName){
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
	
	public static List<String> getHeaderNames() {
		SBBGSBLCFileUploadHeader[] headers = values();
		
		List<String> headerNames = new ArrayList<String>();
		for(SBBGSBLCFileUploadHeader header : headers) {
			headerNames.add(header.getHeaderName());
		}
		
		return headerNames;
	}

}
