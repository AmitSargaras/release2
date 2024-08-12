package com.integrosys.cms.batch.fcc.col.liquidation.fd.upload;

import java.util.ArrayList;
import java.util.List;

public enum FccColFdFileUploadHeader {

//	Liability_ID("Liability ID"), Line_ID("Line ID"), SERIAL_NO("SERIAL NUMBER"),
//	Collateral_Id("Collateral Id"), Collateral_Description("Collateral Description"), Collateral_Type("Collateral Type"), Collateral_Number("Collateral Number"),
//	Collateral_Amount("Collateral Amount"), FD_Lien_Amount("FD Lien Amount"), CURRENCY_CODE("CURRENCY CODE"),
//	SYSTEM_NAME("SYSTEM NAME"),ACTION("Action");
	
	/*DEPOSIT_RECEIPT_NUMBER("Liability ID"), DEPOSIT_AMOUNT("Line ID"), SERIAL_NO("SERIAL NUMBER"),
	CMS_COLLATERAL_ID("Collateral Id"), Collateral_Description("Collateral Description"), Collateral_Type("Collateral Type"), Collateral_Number("Collateral Number"),
	Collateral_Amount("Collateral Amount"), FD_Lien_Amount("FD Lien Amount"), CURRENCY_CODE("CURRENCY CODE"),
	SYSTEM_NAME("SYSTEM NAME");*/
	
	SYSTEM_ID("Liability ID"), LIEN_NUMBER("Line ID"), SERIAL_NO("Serial No."),
	CMS_COLLATERAL_ID("Collateral Id"), COLLATERAL_CODE("Collateral Description"), SUBTYPE_NAME("Collateral Type"), DEPOSIT_REFERENCE_NUMBER("Collateral Number"),
	DEPOSIT_AMOUNT("Collateral Amount "), LIEN_AMOUNT("FD Lien Amount"), DEPOSIT_AMOUNT_CURRENCY("Currency code"),SCI_SECURITY_CURRENCY("Security Currency"),
	SYSTEM_NAME("System Name");
	
	/*CMS_COLLATERAL_ID("CMS_COLLATERAL_ID"), SYSTEM_NAME("SYSTEM_NAME");*/
	
	
	private final String headerName;
	
	FccColFdFileUploadHeader(String headerName){
		this.headerName = headerName;
	}
	
	public String getHeaderName() {
		return headerName;
	}
	
	public static List<String> getHeaderNames() {
		FccColFdFileUploadHeader[] headers = values();
		
		List<String> headerNames = new ArrayList<String>();
		for(FccColFdFileUploadHeader header : headers) {
			headerNames.add(header.getHeaderName());
		}
		
		return headerNames;
	}

}
