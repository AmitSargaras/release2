package com.integrosys.cms.ui.checklist;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Purpose: Description:
 * 
 * @author $Author: Naveen $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/22/30 10:17:49 $ Tag: $Name: $
 */
public class ViewDocumentationStatusCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Default Constructor
	 */
	public ViewDocumentationStatusCommand() {
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
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "docStatus", "java.lang.String", REQUEST_SCOPE },
				{ "docDate", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here dynamic checkList added to document
	 * done.
	 * 
	 * @param inputMap is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap inputMap) throws CommandProcessingException, CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap resultMap = new HashMap();
		String docStatus = "-";
		Date docDate = null;

		try {
			ILimitProfile lmtprofile = (ILimitProfile) inputMap.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

			DefaultLogger.debug(this, ">>>>>>>>>>>> getLimitProfileID:- " + lmtprofile.getLimitProfileID());

			if (lmtprofile.getLimitProfileID() > 0) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				HashMap map = proxy.getDocumentationStatus(lmtprofile.getLimitProfileID());
				if ((map != null) && !map.isEmpty()) {
					docStatus = (String) map.get("docStatus");
					docDate = (Date) map.get("docDate");
				}
			}

			resultMap.put("docStatus", docStatus);
			resultMap.put("docDate", docDate);

		}
		catch (Exception e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap); // use
																					// this
																					// to
																					// replace
																					// the
																					// one
																					// above
		return returnMap;
	}

}
