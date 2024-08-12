package com.aurionpro.clims.rest.constants;

public interface ResponseConstants {
	
	public static final String SUCCESS_RESPONSE_CODE = "CC0000";
	public static final String COMMONCODE_SUCCESS_MESSAGE = "Common Code Details Shared Successfully";
	
	
	public static final String NO_DATA_FOUND = "CC00001";
	public static final String NO_DATA_FOUND_MESSAGE = "No Data found for given criteria";
	
	public static final String CHANNEL_CODE_REQ = "CHCODE01";
	public static final String CHANNEL_CODE_REQ_MESSAGE = "Channel Code is a mandatory field";
	
	public static final String ACCESS_DENIED = "CHCODE02";
	public static final String ACCESS_DENIED_MESSAGE = "NO ACCESS FOR GIVEN CHANNEL CODE";
	
	public static final String CHANNEL_CODE_LENGTH= "CHCODE03";
	public static final String CHANNEL_CODE_LENGTH_MESSAGE = "Channel Code length exceeded";
	
	public static final String CHANNEL_CODE_INVALID = "CHCODE04";
	public static final String CHANNEL_CODE_INVALID_MESSAGE = "Invalid Channel Code";
	
	public static final String REQUEST_ID_REQ = "REQID001";
	public static final String REQUEST_ID_REQ_MESSAGE = "Request Id is a mandatory field";
	
	/*public static final String REQUEST_ID_INVALID = "REQID002";
	public static final String REQUEST_ID_INVALID_MSG = "Request Id is invalid.";*/
	
	public static final String REQUEST_ID_NUM = "REQID002";
	public static final String REQUEST_ID_NUM_MESSAGE = "Request Id must be a number";
	
	public static final String REQUEST_ID_LEN = "REQID003";
	public static final String REQUEST_ID_LEN_MESSAGE = "Request Id length exceeded";
	
	public static final String REQUEST_ID_DUP = "REQID004";
	public static final String REQUEST_ID_DUP_MSG = "Request Id is duplicate.";	

	public static final String PASSCODE_REQ = "PASSCODE01";
	public static final String PASSCODE_REQ_MESSAGE = "Pass Code is a mandatory field";
		
	public static final String PASSCODE_WRONG = "PASSCODE02";
	public static final String PASSCODE_WRONG_MESSAGE = "Pass Code is invalid.";
	
	
	
		
	public static final String CC_CATEGORY_CODE_INVALID = "CC0002";
	public static final String CC_ENTRY_CODE_INVALID = "CC0003";
	public static final String CC_ENTRY_ID_INVALID = "CC0004";
	public static final String CC_ENTRY_NAME_INVALID = "CC0005";
	public static final String CC_STATUS_INVALID = "CC0006";
	public static final String CC_ENTRY_ID_NUM = "CC0007";
	public static final String CC_ENTRY_ID_LEN = "CC0008";
	public static final String CC_ENTRY_NAME_LEN = "CC0009";
	public static final String CC_ENTRY_CODE_LEN = "CC0010";
	
	
	
	
	public static final String CC_CATEGORY_CODE_INVALID_MESSAGE = "Category Code is invalid";
	
	public static final String CC_ENTRY_CODE_INVALID_MESSAGE = "Entry Code is invalid";
	public static final String CC_ENTRY_CODE_LEN_MESSAGE = "Entry Code must not exceed 60 characters";
	
	public static final String CC_ENTRY_ID_INVALID_MESSAGE = "Entry Id is invalid";
	public static final String CC_ENTRY_ID_NUM_MESSAGE = "Entry Id must be a number";
	public static final String CC_ENTRY_ID_LEN_MESSAGE = "Entry Id must not exceed 19 characters";
	
	public static final String CC_ENTRY_NAME_INVALID_MESSAGE = "Entry Name is invalid";
	public static final String CC_ENTRY_NAME_LEN_MESSAGE = "Entry Name must not exceed 750 characters";
	
	public static final String CC_STATUS_INVALID_MESSAGE = "Status must be either ACTIVE or INACTIVE only";
	
	
/*	public static final String createPartySuccessMessage;
	public static final String updatePartySuccessMessage;
	public static final String createCAMSuccessMessage;
	public static final String updateCAMSuccessMessage;
	
	public static final String createFacilitySuccessMessage;
	public static final String updateFacilitySuccessMessage;*/
	
	public static final String CC_CATEGORY_CODE_REQ = "CC0001";
	public static final String CC_CATEGORY_CODE_REQ_MESSAGE = "Category Code is a mandatory field";
	
	public static final String INVALID_JSON = "EXP0001";
	public static final String INVALID_JSON_MESSAGE = "INVALID JSON SYNTAX";
	
	public static final String EXCEPTION = "EXP0002";
	public static final String EXCEPTION_MESSAGE = "EXCPETION";
	
	public static final String PTY_SUCCESS_RESPONSE_CODE = "PTY0000";
	public static final String PTY_ADD_SUCCESS_MESSAGE = "PARTY_CREATED_SUCCESSFULLY";
	public static final String PTY_UPDT_SUCCESS_MESSAGE = "PARTY_UPDATED_SUCCESSFULLY";
	
	public static final String FAC_SUCCESS_RESPONSE_CODE = "FAC0000";
	public static final String FAC_ADD_SUCCESS_MESSAGE = "FACILITY_CREATED_SUCCESSFULLY";
	public static final String FAC_UPDT_SUCCESS_MESSAGE = "FACILITY_UPDATED_SUCCESSFULLY";

	
	public static final String SEC_STATUS_INVALID = "SEC0001";
	public static final String SEC_STATUS_INVALID_MESSAGE = "SECURITY ID IS INVALID";
	
	public static final String SEC_MISSING = "SEC0002";
	public static final String SEC_MISSING_MESSAGE = "SECURITY ID A MANDATORY FIELD";

	public static final String SEC_ID_NUM = "SEC0003";
	public static final String SEC_ID_NUM_MESSAGE = "SECURITY ID must be a number";
	
	public static final String SEC_ID_LEN = "SEC0004";
	public static final String SEC_ID_LEN_MESSAGE = "SECURITY ID length exceeded";
	
	public static final String SEC_INSURANCE_NOT_ALLOWED = "SEC0005";
	public static final String SEC_INSURANCE_NOT_ALLOWED_MESSAGE = "FOR THIS COLLATERAL, INSURANCE IS DISABLED IN MASTER, HENCE INSURANCE IS NOT ALLOWED FOR THIS COLLATERAL";
	
	public static final String SEC_ID_PATH_INVALID = "SEC0006";
	public static final String SEC_ID_PATH_INVALID_MESSAGE = "SECURITY ID IS INVALID FOR THE SECURITY TYPE";
	
	public static final String SEC_INVALID_SUBTYPE = "SECDEL0001";
	public static final String SEC_INVALID_SUBTYPE_MESSAGE = "SECURITY ID A MANDATORY FIELD";

	public static final String UDF_SUCCESS_RESPONSE_CODE = "UDF0000";	
	public static final String UDF_ENQUIRY_REQ = "UDF0001";	
	public static final String UDF_SUCCESS_MESSAGE = "UDF details fetched successfully.";
	public static final String UDF_NO_DATA_FOUND_MESSAGE = "No data found for given criteria";
	public static final String UDF_SIGNATURE_MESSAGE = "sequence,mandatory and status must present in signature";
	
}
