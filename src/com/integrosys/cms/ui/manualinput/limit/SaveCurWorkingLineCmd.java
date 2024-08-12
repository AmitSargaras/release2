package com.integrosys.cms.ui.manualinput.limit;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.proxy.IGeneralParamProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class SaveCurWorkingLineCmd  extends AbstractCommand {


public String[][] getParameterDescriptor() {
	return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
		{ "xrefDetailForm", "java.lang.Object", FORM_SCOPE }, 
		{ "inrValue", "java.lang.String", REQUEST_SCOPE },
		{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
		{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
		{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
		{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
		{ "customerID", "java.lang.String", REQUEST_SCOPE },
		{"event", "java.lang.String", REQUEST_SCOPE},
		{ "isCreate", "java.lang.String", REQUEST_SCOPE },
		{"fromEvent", "java.lang.String", REQUEST_SCOPE},
		{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		{ "facCat", "java.lang.String", REQUEST_SCOPE },
});

}

public String[][] getResultDescriptor() {
return (new String[][] {{ "inrValue", "java.lang.String", SERVICE_SCOPE },
		{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
		{ "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
		{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
		{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
		{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
		{ "systemIdList", "java.util.List", SERVICE_SCOPE },
		{ "liabilityIDList", "java.util.List", SERVICE_SCOPE },
		{ "BASE_INT_RATE", "java.lang.String", SERVICE_SCOPE },
		{ "BPLR_INT_RATE", "java.lang.String", SERVICE_SCOPE },
		{"limitDashboardList", "java.util.List", REQUEST_SCOPE},
		{"totalLienAmount", "java.util.String", SERVICE_SCOPE} ,
		{"totalLimitInOs", "java.util.String", SERVICE_SCOPE},
		{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
		{"fromEvent", "java.lang.String", REQUEST_SCOPE},
		{ "facCat", "java.lang.String", REQUEST_SCOPE },
		{ "isCreate", "java.lang.String", REQUEST_SCOPE },
		
});
}

public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
	AccessDeniedException {
HashMap result = new HashMap();
HashMap exceptionMap = new HashMap();
HashMap temp = new HashMap();



try {
	// just map from form to staging limit and save in trxValue object
	OBCustomerSysXRef account = (OBCustomerSysXRef) (map.get("xrefDetailForm"));

	String custID = (String) (map.get("customerID"));
	
	
	ILimitTrxValue lmtTrxObj = (ILimitTrxValue) (map.get("lmtTrxObj"));
	
	
	String event=(String)map.get("event");
	DefaultLogger.debug(this, "event:"+event);
	
	String fromEvent=(String)map.get("fromEvent");
	DefaultLogger.debug(this, "fromEvent:"+fromEvent);
	
	String isCreate = (String) (map.get("isCreate"));
	result.put("isCreate", isCreate);

	result.put("lmtTrxObj", lmtTrxObj);
	
	
}
catch (Exception ex) {
	throw (new CommandProcessingException(ex.getMessage()));
}

result.put("sessionCriteria",map.get("sessionCriteria"));
result.put("fundedAmount", map.get("fundedAmount"));
result.put("nonFundedAmount", map.get("nonFundedAmount"));
result.put("memoExposer", map.get("memoExposer"));
result.put("sanctionedLimit", map.get("sanctionedLimit"));
result.put("facCat", map.get("facCat"));
result.put("inrValue", map.get("inrValue"));
temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
return temp;
}

private List getSourceSystemCountryList() {
	List lbValList = new ArrayList();
	List idList = (List) (CountryList.getInstance().getCountryValues());
	List valList = (List) (CountryList.getInstance().getCountryLabels());
	for (int i = 0; i < idList.size(); i++) {
		String id = idList.get(i).toString();
		String val = valList.get(i).toString();
		LabelValueBean lvBean = new LabelValueBean(val, id);
		lbValList.add(lvBean);
	}
	return CommonUtil.sortDropdown(lbValList);
}

private List getSourceSystemNameList(String country) {
	List lbValList = new ArrayList();
	if ((country != null) && !country.trim().equals("")) {
		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap("ACCT_SOURCE", null, country);
		Object[] keyArr = map.keySet().toArray();
		for (int i = 0; i < keyArr.length; i++) {
			Object nextKey = keyArr[i];
			LabelValueBean lvBean = new LabelValueBean(map.get(nextKey).toString(), nextKey.toString());
			lbValList.add(lvBean);
		}
	}
	return CommonUtil.sortDropdown(lbValList);
}

private List getSubSecurityList(List lst) {
	List lbValList = new ArrayList();
	try {

		for (int i = 0; i < lst.size(); i++) {
			String[] mgnrLst = (String[]) lst.get(i);
			LabelValueBean lvBean = new LabelValueBean(mgnrLst[1], mgnrLst[0]);
			lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}

private Map getSubSecurityListMap(List lst) {
	Map lbValMap = new HashMap();
try {
		
	for (int i = 0; i < lst.size(); i++) {
		String [] mgnrLst = (String[])lst.get(i);
		lbValMap.put(mgnrLst[0], mgnrLst[1]);
	}
} catch (Exception ex) {
}
return lbValMap;
}

private String dateFormater(Date d) {
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	try {
		Date date = formatter.parse(d.toString());
		System.out.println("Date is: " + date);
		SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MMM/yyyy");
		String strDate = formatter1.format(date);
		System.out.println("Date Format with MM/dd/yyyy : " + strDate);
		return strDate;
	} catch (ParseException e) {
		e.printStackTrace();
		return null;
	}
}
}