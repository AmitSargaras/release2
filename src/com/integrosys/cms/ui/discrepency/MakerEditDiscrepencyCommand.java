package com.integrosys.cms.ui.discrepency;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.discrepency.bus.IDiscrepency;
import com.integrosys.cms.app.discrepency.bus.IDiscrepencyDAO;
import com.integrosys.cms.app.discrepency.bus.OBDiscrepency;
import com.integrosys.cms.app.discrepency.proxy.IDiscrepencyProxyManager;
import com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue;
import com.integrosys.cms.app.discrepency.trx.OBDiscrepencyTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/04/2011 02:12:00 $ Tag: $Name: $
 */

public class MakerEditDiscrepencyCommand extends AbstractCommand {

	private IDiscrepencyProxyManager discrepencyProxy;
	
	public IDiscrepencyProxyManager getDiscrepencyProxy() {
		return discrepencyProxy;
	}

	public void setDiscrepencyProxy(IDiscrepencyProxyManager discrepencyProxy) {
		this.discrepencyProxy = discrepencyProxy;
	}

	public MakerEditDiscrepencyCommand() {
	}

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "discrepencyObj", "com.integrosys.cms.app.discrepency.bus.IDiscrepency",FORM_SCOPE },
				{"IDiscrepencyTrxValue","com.integrosys.cms.app.discrepency.trx.IDiscrepencyTrxValue",SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
	}

	public String[][] getResultDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"searchstatus","java.lang.String",REQUEST_SCOPE},
				{"discType","java.lang.String",REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "request.ITrxValue","com.integrosys.cms.app.transaction.ICMSTrxValue",REQUEST_SCOPE },
				{com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
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

		try {
			IDiscrepency discrepency = (IDiscrepency) map.get("discrepencyObj");
			
			String event = (String) map.get("event");
			String searchstatus=(String)map.get("searchstatus");
			String discType=(String)map.get("discType");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IDiscrepencyTrxValue trxValueIn = (OBDiscrepencyTrxValue) map
					.get("IDiscrepencyTrxValue");
			IDiscrepencyTrxValue trxValueOut = new OBDiscrepencyTrxValue();

			if (event.equals("maker_edit_discrepency")||event.equals("maker_edit_discrepency_waive")||event.equals("maker_edit_discrepency_defer")||"maker_edit_discrepency_close".equals(event)) {
				if(event.equals("maker_edit_discrepency_defer")||event.equals("maker_edit_discrepency")){
					if(event.equals("maker_edit_discrepency_defer")){
					long count=trxValueIn.getActualDiscrepency().getCounter();
					count++;
					long deferCount=trxValueIn.getActualDiscrepency().getDeferedCounter();
					deferCount++;				
					discrepency.setDeferedCounter(deferCount);
					discrepency.setCounter(count);
					}
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
				}
				trxValueOut = getDiscrepencyProxy().makerUpdateDiscrepency(ctx, trxValueIn,discrepency);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				trxValueOut = getDiscrepencyProxy().makerEditRejectedDiscrepency(ctx,
						trxValueIn, discrepency);
			}
			OBDiscrepency updateStage=replaceStage(discrepency,trxValueOut);
			OBDiscrepency updateActual=replaceActual(trxValueIn.getActualDiscrepency(),updateStage);
			IDiscrepencyDAO discrepencyDAO=(IDiscrepencyDAO)BeanHouse.get("discrepencyDAO");
			discrepencyDAO.updateStageDiscrepency(updateStage);
			discrepencyDAO.updateDiscrepency(updateActual);
			resultMap.put("request.ITrxValue", trxValueOut);
			resultMap.put("searchstatus", searchstatus);
			resultMap.put("discType",discType);
			resultMap.put("startIndex", map.get("startIndex"));
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
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
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
		replace.setTransactionStatus(discrepency.getStatus());
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
		
	}
	
	public OBDiscrepency replaceActual(IDiscrepency discrepency,IDiscrepency stagediscrepency)
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
		replace.setId(discrepency.getId());
		replace.setNextDueDate(discrepency.getNextDueDate());
		replace.setOriginalTargetDate(discrepency.getOriginalTargetDate());
		replace.setRecDate(discrepency.getRecDate());
		replace.setStatus(discrepency.getStatus());
		replace.setTransactionStatus(stagediscrepency.getStatus()); //replace status by trxstatus for trxstatus in db
		replace.setCritical(discrepency.getCritical());
		//replace.setVersionTime(0);
		replace.setWaiveDate(discrepency.getWaiveDate());
		
		return replace;
		
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
