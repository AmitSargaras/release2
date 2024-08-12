package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.Arrays;
import java.util.List;

public interface ILineDetailConstants {

	String SESSION_FAC_NAME_LIST = "facNameList";
	String SESSION_FAC_DETAIL_LIST = "facDetailList";
	String SESSION_FAC_LINE_NO_LIST = "facLineNoList";
	String SESSION_FAC_ID_LIST = "facIdList";
	String SESSION_LINE_DETAIL_LIST= "lineDetailList";
	
	String REQUEST_DROPDOWN_NAME = "dropdownName";
	String REQUEST_SELECTED_VALUE = "curSel";
	
	String DROPDOWN_FACILITY_NAME = "facilityName";
	String DROPDOWN_FACILITY_ID = "facilityID";
	String DROPDOWN_LINE_NO = "lineNo";
	
	String LINE_DETAIL_FORM = "form.lineDetailObj";
	String LINE_DETAIL_MAPPER = LineDetailMapper.class.getName();
	
    String MAXIMUM_ALLOWED_AMOUNT_24_4_STR = "99999999999999999999.9999";
    double MAXIMUM_ALLOWED_AMOUNT_24_4 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_24_4_STR);
    
    String MAXIMUM_ALLOWED_AMOUNT_22_2_STR = "99999999999999999999.99";
    double MAXIMUM_ALLOWED_AMOUNT_22_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_22_2_STR);
 
    List<String> ALLOWED_SYSTEM = Arrays.asList("BAHRAIN", "HONGKONG", "GIFTCITY");
}
