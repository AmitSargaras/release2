package com.integrosys.cms.ui.checklist.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.checklist.bus.CheckListException;
import com.integrosys.cms.app.checklist.bus.CheckListSearchResult;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.checklist.bus.ICheckListSummary;
import com.integrosys.cms.app.checklist.bus.OBCheckList;
import com.integrosys.cms.app.checklist.bus.OBCheckListItem;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ViewCompleteChecklistDoc extends AbstractCommand implements ICommonEventConstant{
	

	/**
	 * Default Constructor
	 */
	public ViewCompleteChecklistDoc() {
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "id", "java.lang.String", REQUEST_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE }, 
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "description", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "documentList", "java.util.List", REQUEST_SCOPE },
				{ "docCatList", "java.util.HashMap", REQUEST_SCOPE },
				{ "checkListIDMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "finalList", "java.util.List", SERVICE_SCOPE },
				{ "category", "java.lang.String", REQUEST_SCOPE },
				{ "description", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				{ "viewObj", "com.integrosys.cms.ui.checklist.document.OBViewCompleteCheckList", REQUEST_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", REQUEST_SCOPE } });
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
		HashMap checkListIDMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		OBTrxContext theOBTrxContext = (OBTrxContext) map.get("theOBTrxContext");
		resultMap.put("theOBTrxContext", theOBTrxContext);

		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
        ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
        String id=(String)map.get("id");
        String status=(String)map.get("status");
		String description=(String)map.get("description");
		String category=(String)map.get("category");
        List finalList=(List)map.get("finalList");
        ICheckListItem viewObj=null;
        if(null!=id && !id.equals("")){
        viewObj=CheckListDAOFactory.getCheckListDAO().viewCheckListItem(Long.parseLong(id));
        }
        
        
        resultMap.put("viewObj", viewObj);
        resultMap.put("status",status);
		resultMap.put("description",description);
		resultMap.put("category",category);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
	}


}
