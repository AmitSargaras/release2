/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.paripassuupdate;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.time.DateUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.OBCMSLegalEntity;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.app.recurrent.proxy.IRecurrentProxyManager;

/**
 * @author $Author: cwtan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/02/04 03:19:21 $ Tag: $Name: $
 */
public class ListUpdatePPCheckListCmd extends AbstractCommand implements ICommonEventConstant {


	IRecurrentProxyManager recurrentProxyManager = (IRecurrentProxyManager) BeanHouse.get("recurrentProxy");

	/**
	 * Default Constructor
	 */
	public ListUpdatePPCheckListCmd() {
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
                { "isViewFlag", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "camCheckList", "com.integrosys.cms.app.checklist.bus.CheckListSearchResult", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE } });
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
				{ "paripassuCheckList", "com.integrosys.cms.app.checklist.bus.CheckListSearchResult", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", FORM_SCOPE },
                { "isViewFlag", "java.lang.Boolean", REQUEST_SCOPE },
                {"docDueDate","java.lang.Date",REQUEST_SCOPE},
                {"bankingMethod","java.lang.Date",REQUEST_SCOPE}
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
		ICheckListProxyManager checklistProxyManager=(ICheckListProxyManager) BeanHouse.get("checklistProxy");
        DefaultLogger.debug(this, "Inside doExecute()");
		ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		long limitProfileID = limit.getLimitProfileID();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		OBCMSLegalEntity theOBCMSLegalEntity=(OBCMSLegalEntity) theOBTrxContext.getCustomer().getCMSLegalEntity();
		String bankingMethod=theOBCMSLegalEntity.getBankingMethod();
		
		long subProfileId = theOBTrxContext.getCustomer().getCustomerID();
		if(null==bankingMethod) {
		System.out.println("***************inside ListUpdatePPCheckListCmd****97*****inside 1st if**");
		bankingMethod=theOBCMSLegalEntity.getFinalBankMethodList();
		if(null==bankingMethod) {
		System.out.println("***************inside ListUpdatePPCheckListCmd***100****inside 2nd if**");
			String bankingType = null;
			try{
				bankingType = recurrentProxyManager.getBankingType(limitProfileID,subProfileId);
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
						+ limitProfileID + "], sub profile id [" + subProfileId + "]", re);
			}	
		}}
		if(bankingMethod==null) {
			bankingMethod="";
		}
		System.out.println("***************inside ListUpdatePPCheckListCmd********123********** bankingMethod : "+bankingMethod);

		
		/*Date docDueDate=new Date();
		Date camDate=limit.getCamLoginDate();
		if(camDate!=null){
		docDueDate=CommonUtil.rollUpDateByMonths(camDate, 2);
		}*/
		String isViewFlag = (String) map.get("isViewFlag");

          try {
        	  CheckListSearchResult paripassuCheckList= checklistProxyManager.getPariPassuCheckListByCategoryAndProfileID("PARIPASSU",limitProfileID);
        	  
        	  resultMap.put("paripassuCheckList", paripassuCheckList);
			
		} catch (CheckListException e) {
			
			e.printStackTrace();
			throw new CommandProcessingException("failed to retrieve  checklist ", e);
		}
      
		

		resultMap.put("bankingMethod", bankingMethod);
//		resultMap.put("docDueDate",docDueDate);
		resultMap.put("frame", "true");
		resultMap.put("limitProfileID", String.valueOf(limitProfileID));
        resultMap.put("isViewFlag", new Boolean(isViewFlag));

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


}
