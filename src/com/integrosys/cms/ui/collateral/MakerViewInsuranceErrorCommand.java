package com.integrosys.cms.ui.collateral;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC;

public class MakerViewInsuranceErrorCommand extends AbstractCommand{

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				//{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE }, 
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				
				{ "creationDate", "java.lang.String", REQUEST_SCOPE },
		        { "isProcessed", "java.lang.String", REQUEST_SCOPE },
		        { "deprecated", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceRequired", "java.lang.String", REQUEST_SCOPE },
				{ "insurancePolicyNo", "java.lang.String", REQUEST_SCOPE },
				{ "coverNoteNo", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceCompany", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceCurrency", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceCoverge", "java.lang.String", REQUEST_SCOPE },
				{ "insurancePolicyAmt", "java.lang.String", REQUEST_SCOPE },
				{ "insuredAmount", "java.lang.String", REQUEST_SCOPE },
				{ "receivedDate", "java.lang.String", REQUEST_SCOPE },
				{ "effectiveDate", "java.lang.String", REQUEST_SCOPE },
				{ "expiryDate", "java.lang.String", REQUEST_SCOPE },
				{ "insuranceDefaulted", "java.lang.String", REQUEST_SCOPE },
				{ "insurancePremium", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE },
				{ "allComponent", "java.lang.String", REQUEST_SCOPE },
				{ "selectComponent", "java.lang.String", REQUEST_SCOPE },
				{ "appendedComponent", "java.lang.String", REQUEST_SCOPE },
				
				{"insuranceList", "java.util.ArrayList", SERVICE_SCOPE},
				{"insuranceCode","java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				//{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
				
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				//{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{ "componentList", "java.util.List", SERVICE_SCOPE },
				});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
//				{ "fundedShare", "java.lang.String", REQUEST_SCOPE },
				
				{ "dpShare", "java.lang.String", REQUEST_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "dueDate", "java.lang.String", REQUEST_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				//{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "exchangeRate", "java.lang.String", SERVICE_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"insuranceGCObj","com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", FORM_SCOPE },
				//{"insuranceObj","com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", REQUEST_SCOPE },
				});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap temp = new HashMap();
		
		String insuranceCode=(String)map.get("insuranceCode");
		
		String event=(String)map.get("event");
		
		ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
		try{
			
			result.put("event", event);
			result.put("theOBTrxContext", map.get("theOBTrxContext"));
			result.put("trxID", map.get("trxID"));
			result.put("calculatedDP", map.get("calculatedDP"));
//			result.put("fundedShare", map.get("fundedShare"));
			
			result.put("dpShare", map.get("dpShare"));
			result.put("dueDate", map.get("dueDate"));
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		}
		catch(Exception ex){
			
		}
		return temp;
	}
}
