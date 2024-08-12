package com.integrosys.cms.ui.stockdetailsupload;

import java.util.Arrays;
import java.util.List;

import com.integrosys.cms.ui.common.TrxContextMapper;

public interface IStockDetailsUploadConstants {
	String STOCK_DETAILS_UPLOAD_FORM = "form.stockDetailsUploadObj";
	String STOCK_DETAILS_UPLOAD_MAPPER = StockDetailsUploadMapper.class.getName();
	
	String TRX_MAPPER = TrxContextMapper.class.getName();
	String SESSION_TRX_OBJ = "theOBTrxContext";
	String STOCK_DETAILS_UPLOAD = "StockDetailsUpload";
	
	List<String> ALLOWED_SEC_SUBTYPE = Arrays.asList("Stock", "Share");
	
	String STOCK_EXCHANGE_BSE = "BSE";
	String STOCK_EXCHANGE_NSE = "NSE";
	String STOCK_EXCHANGE_OTHERS = "Others";
	List<String> ALLOWED_STOCK_EXCHANGE = Arrays.asList(STOCK_EXCHANGE_BSE, STOCK_EXCHANGE_NSE, STOCK_EXCHANGE_OTHERS);
	
	String ISSUER_PROMOTERS = "promoters";
	String ISSUER_GROUP_COMPANY = "groupcompany";
	String ISSUER_OTHERS = "others";
	
	List<String> ALLOWED_ISSUERS_PROMOTERS_GROUP_OTHERS = Arrays.asList("1", "2", "3");
	
	String SESSION_TRX_VALUE_OUT = "trxValueOut";
	String SESSION_TOTAL_UPLOADED_LIST = "totalUploadedList";
	String SESSION_TOTAL_FAILED_LIST = "totalFailedList";
	
	String TOTAL = "total";
	String PASS = "pass";
	String FAIL = "fail";
}
