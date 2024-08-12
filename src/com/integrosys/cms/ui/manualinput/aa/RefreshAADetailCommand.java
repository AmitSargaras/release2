/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.aa.proxy.SBMIAAProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Describe this class. Purpose: for Maker to refresh the current page to get
 * organisation code Description: command that get the organisation code from
 * database by pass in the country value that user select
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class RefreshAADetailCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "bookingCty", "java.lang.String", REQUEST_SCOPE },
				{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE },
				{"camDate", "java.lang.String", REQUEST_SCOPE}});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
				{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
				{ "indexChange", "java.lang.String", REQUEST_SCOPE },
				{ "preEvent", "java.lang.String", REQUEST_SCOPE }, { "countryList", "java.util.List", REQUEST_SCOPE },
				{ "orgList", "java.util.List", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "sourceSystemList", "java.util.List", REQUEST_SCOPE },
				{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE } ,
				{ "annualReviewDate", "java.lang.String",REQUEST_SCOPE },
				{ "creditAprrovalList", "java.util.List", REQUEST_SCOPE },
				{ "riskGradeList", "java.util.List", REQUEST_SCOPE }});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		ILimitProfileTrxValue limitProfileTrxVal = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		String countryCode = null;

		String event = (String) map.get("event");
		
		if( event.equals("refresh_cam")){	        		
    		String camDate = (String) map.get("camDate");
    		Calendar calendar = new GregorianCalendar();
    		DateFormat formatter ; 
    		formatter = new SimpleDateFormat("dd/MMM/yyyy");
    		Date camDateEntry=null;
			try {
				camDateEntry = (Date)formatter.parse(camDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    		calendar.setTime(camDateEntry);
    		calendar.add(Calendar.YEAR, 1);
    	    Date nextYear = calendar.getTime();
    	    
    		resultMap.put("annualReviewDate", nextYear.toString());
    	}else{
    		
		String preEvent = (String) map.get("preEvent");
		AAUIHelper helper = new AAUIHelper();

		if (!(event.equals("maker_add_aadetail_confirm_error") || event.equals("maker_edit_aadetail_confirm_error") || event
				.equals("maker_edit_aadetail_reject_confirm_error"))) {

			limitProfileTrxVal.setStagingLimitProfile(obLimitProfile);

			resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
		}
		resultMap.put("limitProfileTrxVal", limitProfileTrxVal);
		resultMap.put("preEvent", preEvent);

		countryCode = (String) (map.get("bookingCty"));
		helper.refreshListing(map, resultMap, obLimitProfile, countryCode);
		SBMIAAProxy proxy = helper.getSBMIAAProxy();
		try {
			//BigDecimal sanctLmt = new BigDecimal(limitProfileTrxVal.getLimitProfile().getTotalSactionedAmount());
			resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverList())); //shiv
			//resultMap.put("creditAprrovalList", getCreditApproverList(proxy.getCreditApproverListWithLimit(sanctLmt))); 
		} catch (SearchDAOException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			throw (new CommandProcessingException(e.getMessage()));
		} //shiv
		resultMap.put("event",event);
		resultMap.put("riskGradeList",getGradeList());//Shiv
		resultMap.put("timefrequency.labels", CommonDataSingleton.getCodeCategoryLabels(ICMSUIConstant.TIME_FREQ));
		resultMap.put("timefrequency.values", CommonDataSingleton.getCodeCategoryValues(ICMSUIConstant.TIME_FREQ));
    	}//end else if Shiv
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	//Shiv
	private List getCreditApproverList(List lst) {
		List lbValList = new ArrayList();
	try {
			
		for (int i = 0; i < lst.size(); i++) {
			String [] creditApprover = (String[])lst.get(i);
				String val = creditApprover[0];
				String name = creditApprover[1];
				if(creditApprover[2].equals("Y")){
					name = name+ " (Senior)";
				}
				LabelValueBean lvBean = new LabelValueBean(name,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return CommonUtil.sortDropdown(lbValList);
}
	
	//Shiv
	private List getGradeList() {
		List lbValList = new ArrayList();
		List values = new ArrayList();
		TreeSet ts = new TreeSet();
		try {
		values.addAll(CommonDataSingleton.getCodeCategoryLabels(CategoryCodeConstant.CommonCode_RISK_GRADE));
		
		for (int i = 0; i < values.size(); i++) {
			ts.add(new Integer(values.get(i).toString()));
		}
		Iterator itr = ts.iterator();
		
		while (itr.hasNext()) {
				String val = itr.next().toString();
				LabelValueBean lvBean = new LabelValueBean(val,val );
				lbValList.add(lvBean);
		}
	} catch (Exception ex) {
	}
	return lbValList;
}
}
