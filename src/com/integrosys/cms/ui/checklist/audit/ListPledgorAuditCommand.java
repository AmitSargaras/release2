/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.audit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: bxu $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/04/13 03:35:37 $ Tag: $Name: $
 */
public class ListPledgorAuditCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListPledgorAuditCommand() {
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
				{ "asOfDate", "java.lang.String", SERVICE_SCOPE },
				{ "checklist_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_legal_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_name", "java.lang.String", REQUEST_SCOPE } });
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

		{ "pledgorAuditMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "secPledgedMap", "java.util.HashMap", REQUEST_SCOPE },
				{ "checklist_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_legal_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_id", "java.lang.String", REQUEST_SCOPE },
				{ "pledgor_name", "java.lang.String", REQUEST_SCOPE } });
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
		// DefaultLogger.debug(this, "Inside doExecute()");
		try {
			String asOfDate = (String) map.get("asOfDate");
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();

			String pledgorIDStr = (String) map.get("pledgor_id");
			long pledgorID = Long.parseLong(pledgorIDStr);

			resultMap.put("checklist_id", map.get("checklist_id"));
			resultMap.put("pledgor_legal_id", map.get("pledgor_legal_id"));
			resultMap.put("pledgor_id", pledgorIDStr);
			resultMap.put("pledgor_name", map.get("pledgor_name"));

			String aCustCat = ICMSConstant.CHECKLIST_PLEDGER;
			DefaultLogger.debug(this, "Customer is BORROWER.");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			DefaultLogger.debug(this, "limitProfileID=" + limitProfileID);

			HashMap pledgorAuditMap = proxy.getCheckListAudit(limitProfileID, asOfDate, aCustCat);
			HashMap secPledgedMap = proxy.getSecuritiesPledged(limitProfileID, pledgorID);

			resultMap.put("pledgorAuditMap", pledgorAuditMap);
			resultMap.put("secPledgedMap", secPledgedMap);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		// DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
