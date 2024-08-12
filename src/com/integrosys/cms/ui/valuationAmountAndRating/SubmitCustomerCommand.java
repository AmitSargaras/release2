/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/customer/ListCustomerCommand.java,v 1.17 2005/05/12 12:58:51 jtan Exp $
 */

package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
/**
 * This class is used to list the customer details based on some search
 * contsraints
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.17 $
 * @since $Date: 2005/05/12 12:58:51 $ Tag: $Name: $
 */
public class SubmitCustomerCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public SubmitCustomerCommand() {

	}
	
//	private IDiaryItemProxyManager diaryItemProxyManager;
//
//	public IDiaryItemProxyManager getDiaryItemProxyManager() {
//		return diaryItemProxyManager;
//	}
	
	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", SERVICE_SCOPE}, 
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
					GLOBAL_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
				
				{"customersIdList", "java.lang.String", REQUEST_SCOPE},
				
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
//				{ "customerList", "com.integrosys.base.businfra.search.SearchResult", FORM_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria", GLOBAL_SCOPE },
				 { "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE},
					{ "customerObject", "java.util.List", SERVICE_SCOPE },
					{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
					{ "activityList", "java.util.List", SERVICE_SCOPE },
					
					{"customersIdList", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
	HashMap returnMap = new HashMap();
	HashMap resultMap = new HashMap();
	HashMap exceptionMap = new HashMap();

	DefaultLogger.debug(this, "Inside doExecute()");
	
	String customersIdList1 = (String) map.get("customersIdList");
	System.out.println("customersIdList List == "+customersIdList1);

	OBValuationAmountAndRating filter = (OBValuationAmountAndRating) map.get("valuationAmountAndRatingObj");
	String partyIds = "";
	if(customersIdList1 != null && !"".equals(customersIdList1)) {
	partyIds = customersIdList1.substring(0, customersIdList1.length()-1);
	}
	String addedPartyIds = filter.getExcludePartyId();
	if(addedPartyIds != null && !"".equals(addedPartyIds)) {
		partyIds = addedPartyIds +","+ partyIds;
	}
	filter.setExcludePartyId(partyIds);
	resultMap.put("valuationAmountAndRatingObj", filter);
	resultMap.put("customersIdList", customersIdList1);
//	resultMap.put("diaryNumber", filter.getDiaryNumber());
	
	DefaultLogger.debug(this, "Going out of doExecute()");
	returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	return returnMap;
	
	}

}
