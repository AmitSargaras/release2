package com.integrosys.cms.ui.discrepency;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyFacilityList;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: Sandeep Shinde
 * @version 1.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerCreateTempDiscrepencyCommand extends AbstractCommand {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",	FORM_SCOPE },
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",	FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },		
				{"IDiscrepencyTrxValue","com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",SERVICE_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "checkId", "java.lang.String", REQUEST_SCOPE },
				{ "unCheckId", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				//{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				//{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },	
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndexInn", "java.lang.String", REQUEST_SCOPE },
				{ "legalCustomerId", "java.lang.String", REQUEST_SCOPE }
		
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. Here Listing of Discrepency is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */

	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		OBDiscrepency obDiscrepency= new OBDiscrepency();
		DefaultLogger.debug(this, "============ MakerCreateDiscrepencyCommand ()");
		String event = (String) map.get("event");
		String checkId =(String) map.get("checkId");
		String unCheckId =(String) map.get("unCheckId");
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap==null){ 
			selectedArrayMap = new HashMap();
		}
		
		if(checkId!=null && !checkId.equals("") ){
			
			String[] selected=checkId.split("-");
			if(selected!=null){
					for(int k=0;k<selected.length;k++){
						if(!selected[k].equals("")){
								selectedArrayMap.put(selected[k], selected[k]);
						}
					}
			}
		}
			
		if(unCheckId!=null && !unCheckId.equals("")){
			String[] unchecked=unCheckId.split("-");
			if(unchecked!=null){
					for(int ak=0;ak<unchecked.length;ak++){
						if(!unchecked[ak].equals("")){
								selectedArrayMap.remove(unchecked[ak]);
						}
					}
				}
		}
		
		resultMap.put("selectedArrayMap", selectedArrayMap);
		
		if("maker_next_create_temp_list_discrepency".equals(event)){
			resultMap.put("startIndexInn", map.get("startIndexInn"));
			resultMap.put("startIndex", map.get("startIndex"));
			resultMap.put("searchstatus", map.get("searchstatus"));
			resultMap.put("discType",map.get("discType"));
			resultMap.put("discrepancyCreateList",map.get("discrepancyCreateList"));
			resultMap.put("event", event);
		}
		else{
		
		
		String legalCustomerId = (String) map.get("legalCustomerId");
		ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");
		if(discrepancyCreateList==null){
			discrepancyCreateList= new ArrayList();
		}
		IDiscrepency discrepency = (IDiscrepency) map.get("discrepencyObj");
		
		IDiscrepencyTrxValue trxValueIn = (OBDiscrepencyTrxValue) map.get("IDiscrepencyTrxValue");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
		 // ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	      //  long limitProfileID = limit.getLimitProfileID();
		Date creationDate=discrepency.getCreationDate();
		Date originalDate=discrepency.getOriginalTargetDate();
		Date nextDate=discrepency.getNextDueDate();
		Date deferDate = discrepency.getDeferDate();
		if(nextDate!=null){
		String originalDays=calculateDays(nextDate,originalDate);
		discrepency.setOriginalDeferedDays(originalDays);
		}
		if(nextDate!=null){
			String totalDays=calculateDays(nextDate,creationDate);
			discrepency.setTotalDeferedDays(totalDays);
			}
		
		
	    discrepency.setCustomerId(customer.getCustomerID());
		try {
			discrepancyCreateList.add(discrepency);
			
			
		}  catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		resultMap.put("discrepancyCreateList", discrepancyCreateList);
		resultMap.put("legalCustomerId", legalCustomerId);
		resultMap.put("discrepencyObj", obDiscrepency);
		resultMap.put("startIndex", map.get("startIndex"));
		resultMap.put("startIndexInn", map.get("startIndexInn"));
		resultMap.put("searchstatus", map.get("searchstatus"));
		resultMap.put("discType",map.get("discType"));
		resultMap.put("startIndexInn", map.get("startIndexInn"));
		resultMap.put("event", event);
		
		
	}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
public String calculateDays(Date nextDate,Date calculateDate){
		
		
		Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calculateDate.getYear(),calculateDate.getMonth(),calculateDate.getDate());
		calendar2.set(nextDate.getYear(), nextDate.getMonth(), nextDate.getDate());
		  long milliseconds1 = calendar1.getTimeInMillis();
		  long milliseconds2 = calendar2.getTimeInMillis();
		  long diff =  milliseconds2-milliseconds1;
		  long diffSeconds = diff / 1000;
		  long diffMinutes = diff / (60 * 1000);
		  long diffHours = diff / (60 * 60 * 1000);
		  long diffDays = diff / (24 * 60 * 60 * 1000);
		  String days=String.valueOf(diffDays);
		  return days;
        
	}
}