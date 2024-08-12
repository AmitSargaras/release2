package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.Arrays;
import java.util.List;

public interface IMarketableEquityLineDetailConstants {

	String SESSION_FAC_NAME_LIST = "msFacNameList";
	String SESSION_FAC_DETAIL_LIST = "msFacDetailList";
	String SESSION_FAC_LINE_NO_LIST = "msFacLineNoList";
	String SESSION_FAC_ID_LIST = "msFacIdList";
	String SESSION_LINE_DETAIL_LIST= "msLineDetailList";
	String SESSION_EQUITY= "msEquity";
	
	String SESSION_EQUITY_EVENT= "msEquityEvent";
	
	String REQUEST_DROPDOWN_NAME = "dropdownName";
	String REQUEST_SELECTED_VALUE = "curSel";
	
	String DROPDOWN_FACILITY_ID = "facilityId";
	String DROPDOWN_LINE_NO = "lineNumber";
	String DROPDOWN_FACILITY_NAME = "facilityName";
	
	String MARKETABLE_EQUITY_LINE_DETAIL_FORM = "form.msLineDetailObj";
	String MAREKETABLE_EQUITY_LINE_DETAIL_MAPPER = MarketableEquityLineDetailMapper.class.getName();
	
    List<String> ALLOWED_SYSTEM = Arrays.asList("BAHRAIN", "HONGKONG", "GIFTCITY");
    
    String REQUEST_MARKETABLE_EQUITY_LINE_DETAIL_LIST= "msReqLineDetail";
    String REQUEST_STAGING_MARKETABLE_EQUITY_LINE_DETAIL_LIST= "msStgReqLineDetail";
    
    String BRANCH_CODE = "branch.code.";
}
