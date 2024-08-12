/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.annexure;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.recurrent.bus.ConvenantComparator;
import com.integrosys.cms.app.recurrent.bus.IConvenant;
import com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList;
import com.integrosys.cms.app.recurrent.bus.RecurrentException;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;
import com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.login.CMSGlobalSessionConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/09/21 12:27:54 $ Tag: $Name: $
 */
public class ListAnnexureCommand extends AbstractCommand {

	private IRecurrentProxyManager recurrentProxyManager;

	public void setRecurrentProxyManager(IRecurrentProxyManager recurrentProxyManager) {
		this.recurrentProxyManager = recurrentProxyManager;
	}

	public IRecurrentProxyManager getRecurrentProxyManager() {
		return recurrentProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public ListAnnexureCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
						{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",GLOBAL_SCOPE },
						{ CMSGlobalSessionConstant.TEAM_TYPE_MEMBERSHIP_ID, "java.lang.String", GLOBAL_SCOPE },
						{ "event", "java.lang.String", REQUEST_SCOPE } 
					});
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
				{ "recChkLst", "com.integrosys.cms.app.recurrent.bus.IRecurrentCheckList", SERVICE_SCOPE },
				{ "checkListTrxVal", "com.integrosys.cms.app.recurrent.trx.IRecurrentCheckListTrxValue", SERVICE_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
				{ "limitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "bankingMethod", "java.lang.String", SERVICE_SCOPE }
			});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String bankingType = null;
		String bankingMethod = null;
		DefaultLogger.debug(this, "Inside doExecute()");
		String event = (String) map.get("event");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileId = limit.getLimitProfileID();
		long subProfileId = limit.getCustomerID();
		String annexureId = "Annexure";
		boolean wip; 
		try {
			wip = getRecurrentProxyManager().allowRecurrentCheckListTrx(limitProfileId, subProfileId);
		}
		catch (RecurrentException ex) {
			throw new CommandProcessingException(
					"failed to checklist whether recurrent checklist trx is allowed for limit profile id ["
							+ limitProfileId + "], sub profile id [" + subProfileId + "]", ex);
		}
		

		if (!EVENT_READ.equals(event) && !wip) {
			resultMap.put("wip", "wip");
		}
		else if(event.equals(AnnexureAction.EVENT_MAINTAIN_ANNEXURE_LIST)){
			/*long recurrentDocId =0l;
			String bankingType = null;
			String bankingMethod = null;
			String bankingMode = null;*/
			try{
				/*recurrentDocId = getRecurrentProxyManager().getRecurrentDocId(limitProfileId,subProfileId);
				
				bankingType = getRecurrentProxyManager().getBankingType(limitProfileId,subProfileId); 
				
				String[] str = bankingType.split("-");
				
				if(str.length>1){
					bankingMethod = str[0];
					bankingMode = str[1];
				}else{
					bankingMethod = str[0];
				}
				
				if((bankingMethod != null && !bankingMethod.equalsIgnoreCase("SOLE") 
						&& (bankingMode != null && bankingMode.equalsIgnoreCase("Y"))))
				{*/
				if(limit.getCamType()!= null && limit.getNextAnnualReviewDate() != null){
					getRecurrentProxyManager().insertAnnexures(limit);
				}	
				else
				{
					exceptionMap.put("processError", new ActionMessage("error.process.annexure"));
				}
				/*}
				else if(bankingMethod != null && !bankingMethod.equalsIgnoreCase("SOLE"))
				{
					getRecurrentProxyManager().insertAnnexures(limit);
				}*/
					
				IRecurrentCheckListTrxValue checkListTrxVal = getAnnexureList(limitProfileId, subProfileId);
				
				if (checkListTrxVal != null) {
					IRecurrentCheckList recChkLst = checkListTrxVal.getCheckList();
					IConvenant conList[] = recChkLst.getConvenantList();

					if ((conList != null) && (conList.length > 0)) {
						Arrays.sort(conList, new ConvenantComparator());
					}
					recChkLst.setConvenantList(conList);
					checkListTrxVal.setCheckList(recChkLst);

					resultMap.put("recChkLst", recChkLst);
				}
				resultMap.put("limitProfile", limit);
				resultMap.put("checkListTrxVal", checkListTrxVal);
			}
			catch(Exception re)
			{
				throw new CommandProcessingException("failed to fetch recurrent workflow for  limit profile id ["
						+ limitProfileId + "], sub profile id [" + subProfileId + "]", re);
			}
			
		}else {
			try{
				bankingType = getRecurrentProxyManager().getBankingType(limitProfileId,subProfileId);
				if(null!=bankingType) {
				String[] str = bankingType.split("-");
				
				if(str.length>1){
					bankingMethod = str[0];
				}else{
					bankingMethod = str[0];
				}
			  }	
			}
			catch(Exception re)
			{
				throw new CommandProcessingException("failed to retrieve banking method for limit profile id ["
						+ limitProfileId + "], sub profile id [" + subProfileId + "]", re);
			}	
			
			IRecurrentCheckListTrxValue checkListTrxVal = getAnnexureList(limitProfileId, subProfileId);
			 
			if (checkListTrxVal != null) {
				IRecurrentCheckList recChkLst = checkListTrxVal.getCheckList();
				IConvenant conList[] = recChkLst.getConvenantList();

				if ((conList != null) && (conList.length > 0)) {
					Arrays.sort(conList, new ConvenantComparator());
				}
				recChkLst.setConvenantList(conList);
				checkListTrxVal.setCheckList(recChkLst);

				resultMap.put("recChkLst", recChkLst);
			}
			resultMap.put("limitProfile", limit);
			resultMap.put("checkListTrxVal", checkListTrxVal);
		}
		resultMap.put("bankingMethod", bankingMethod);
		resultMap.put("event", event);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(COMMAND_RESULT_MAP, resultMap);
		returnMap.put(COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
	private IRecurrentCheckListTrxValue getAnnexureList(long limitProfileId,long subProfileId){
		String annexureType = "Annexure";
		IRecurrentCheckListTrxValue checkListTrxVal;
		try {
			checkListTrxVal = getRecurrentProxyManager().getRecurrentCheckListTrxByAnnexureId(limitProfileId, subProfileId,annexureType);				
		}
		catch (RecurrentException ex) {
			throw new CommandProcessingException("failed to retrieve recurrent workflow for  limit profile id ["
					+ limitProfileId + "], sub profile id [" + subProfileId + "]", ex);
		}
		return checkListTrxVal;
	}	
}
