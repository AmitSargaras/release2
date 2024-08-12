package com.integrosys.cms.ui.image;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerSearchCriteria;
import com.integrosys.cms.app.customer.proxy.CustomerProxyFactory;
import com.integrosys.cms.app.customer.proxy.ICustomerProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.cms.app.image.proxy.ImageUploadCommand;
import com.integrosys.cms.app.image.proxy.IImageUploadProxyManager;


/**
 * This command creates a diary item
 * 
 * @author $Name $<br>
 * @version $Revision: 0 $
 * @since $Date:$ Tag: $Name: $
 */

public class ImageUploadResultCommand extends ImageUploadCommand {

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM,"com.integrosys.component.bizstructure.app.bus.ITeam",GLOBAL_SCOPE },
				{ "customerSearchCriteria","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",FORM_SCOPE },
				{ "customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "indicator", "java.lang.String", REQUEST_SCOPE }, });
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
				{ IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,"com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",GLOBAL_SCOPE },
				{ "customerList","com.integrosys.base.businfra.search.SearchResult",FORM_SCOPE },
				{ "customerSearchCriteria1","com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",SERVICE_SCOPE },
				{ "imageUploadProxyManager","com.integrosys.cms.app.customer.bus.IImageUploadProxyManager",REQUEST_SCOPE },
				{ "imageUploadProxyManager","com.integrosys.cms.app.customer.bus.IImageUploadProxyManager",SERVICE_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
     * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,CommandValidationException {
		DefaultLogger.debug(this, "Enter in doExecute()");
		String indicator = (String) map.get("indicator");
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		CustomerSearchCriteria formCriteria = (CustomerSearchCriteria) map.get("customerSearchCriteria");
		CustomerSearchCriteria searchCriteria = null;
		if (searchCriteria == null) {
			DefaultLogger.debug(this, "- Search Criteria from Form !");
			searchCriteria = formCriteria;
		}

		ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
		long teamTypeID = team.getTeamType().getTeamTypeID();

		if (teamTypeID == ICMSConstant.TEAM_TYPE_MR) {
			searchCriteria.setLmtProfileType(ICMSConstant.AA_TYPE_TRADE);
		}

		searchCriteria.setCtx(theOBTrxContext);

		try {
			ICustomerProxy custproxy = CustomerProxyFactory.getProxy();
			SearchResult sr = custproxy.searchCustomer(searchCriteria);
			IImageUploadProxyManager imageUploadProxyManager = (IImageUploadProxyManager) getImageUploadProxyManager();
			result.put("imageUploadProxyManager", imageUploadProxyManager);
			result.put("customerList", sr);
			result.put(IGlobalConstant.GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ,searchCriteria);
			result.put("customerSearchCriteria1", searchCriteria);
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			CommandProcessingException cpe = new CommandProcessingException("failed to search customer using search criteria '"+ searchCriteria + "'");
			cpe.initCause(e);
			throw cpe;
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		DefaultLogger.debug(this, "Exit from doExecute()");
		return temp;
	}
}
