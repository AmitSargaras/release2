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

public class MakerCreateBulkDiscrepencyCommand extends AbstractCommand {

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
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",
						FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },		
				{
						"IDiscrepencyTrxValue",
						"com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",
						SERVICE_SCOPE },
						{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
							GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{ "remark", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "discrepancyCreateList", "java.util.List", SERVICE_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE } });
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
		
		DefaultLogger.debug(this, "============ MakerCreateDiscrepencyCommand ()");
		String event = (String) map.get("event");
//		IDiscrepency discrepency = (IDiscrepency) map.get("discrepencyObj");
		IDiscrepencyTrxValue trxValueIn = (OBDiscrepencyTrxValue) map.get("IDiscrepencyTrxValue");
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		ArrayList discrepancyCreateList= (ArrayList)map.get("discrepancyCreateList");
		IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
		for(int loop=0;loop<discrepancyCreateList.size();loop++){
		IDiscrepency discrepency =(IDiscrepency)discrepancyCreateList.get(loop);
		IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();
		 // ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
	      //  long limitProfileID = limit.getLimitProfileID();
		HashMap selectedArrayMap = (HashMap) map.get("selectedArrayMap");
		if(selectedArrayMap!=null || !selectedArrayMap.isEmpty()){
			selectedArrayMap.clear();
		}
	        discrepency.setCustomerId(customer.getCustomerID());
		try {
			 
				trxValueOut = getDiscrepencyProxy().makerCreateDiscrepency(ctx, trxValueOut,
						discrepency); // Discrepency need not to be sent
				if(trxValueOut.getStagingDiscrepency().getFacilityList()!=null){
					IDiscrepencyFacilityList[] discrepencyFacilityList=(IDiscrepencyFacilityList[])trxValueOut.getStagingDiscrepency().getFacilityList();
					for(int i=0;i<discrepencyFacilityList.length;i++){
						discrepencyFacilityList[i].setDiscrepencyId(trxValueOut.getStagingDiscrepency().getId());
						
						discrepencyDAO.createDiscrepencyFacilityList("stagingDiscrepencyFacilityList", discrepencyFacilityList[i]);
					}
				}
				OBDiscrepency updateStage=replaceStage(discrepency,trxValueOut);
				
				discrepencyDAO.updateStageDiscrepency(updateStage);
				//resultMap.put("discrepencyObj", discrepency);
				resultMap.put("selectedArrayMap", selectedArrayMap);
				resultMap.put("searchstatus",map.get("searchstatus"));
				resultMap.put("discType",map.get("discType"));
				resultMap.put("startIndex",map.get("startIndex"));
			
		} catch (NoSuchGeographyException nsge) {
			CommandProcessingException cpe = new CommandProcessingException(
					nsge.getMessage());
			cpe.initCause(nsge);
			throw cpe;
		} catch (Exception e) {
			CommandProcessingException cpe = new CommandProcessingException(
					"Internal Error While Processing ");
			cpe.initCause(e);
			throw cpe;
		}
		}
//		resultMap.put("request.ITrxValue", trxValueOut);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	public OBDiscrepency replaceStage(IDiscrepency discrepency,IDiscrepencyTrxValue trxValueOut)
	{
		OBDiscrepency replace=new OBDiscrepency();
		replace.setAcceptedDate(discrepency.getAcceptedDate());
		replace.setApprovedBy(discrepency.getApprovedBy());
		replace.setCounter(discrepency.getCounter());
		replace.setCreationDate(discrepency.getCreationDate());
		replace.setCreditApprover(discrepency.getCreditApprover());
		replace.setCustomerId(discrepency.getCustomerId());
		replace.setDeferDate(discrepency.getDeferDate());
		replace.setTotalDeferedDays(discrepency.getTotalDeferedDays());
		replace.setOriginalDeferedDays(discrepency.getOriginalDeferedDays());
		replace.setDeferedCounter(discrepency.getDeferedCounter());
		replace.setDiscrepency(discrepency.getDiscrepency());
		replace.setDiscrepencyRemark(discrepency.getDiscrepencyRemark());
		replace.setDiscrepencyType(discrepency.getDiscrepencyType());
		replace.setDocRemarks(discrepency.getDocRemarks());
		replace.setFacilityList(discrepency.getFacilityList());
		replace.setId(Long.parseLong(trxValueOut.getStagingReferenceID()));
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(trxValueOut.getStatus());
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
		
	}
	

}