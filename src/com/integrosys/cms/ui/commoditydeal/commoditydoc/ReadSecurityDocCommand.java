/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/ReadSecurityDocCommand.java,v 1.3 2004/06/16 10:42:23 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckList;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/16 10:42:23 $ Tag: $Name: $
 */
public class ReadSecurityDocCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE }, { "securityID", "java.lang.String", REQUEST_SCOPE }

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
		return (new String[][] { { "checkList", "com.integrosys.cms.app.checklist.bus.OBCheckList", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Before Doing Search");
		ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
		String securityID = (String) map.get("securityID");
		ICheckList checkList = null;
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
		if (null != limitProfileOB) {
			long limitProfileID = limitProfileOB.getLimitProfileID();
			if (limitProfileID > 0) {
				if (securityID != null) {
					try {
						DefaultLogger
								.debug(this, "limitProfileID: " + limitProfileID + "\tcollatearlID: " + securityID);
						checkList = proxy.getCollateralCheckList(limitProfileID, Long.parseLong(securityID));
					}
					catch (Exception e) {
						DefaultLogger.debug(this, "got exception in doExecute " + e);
					}
				}
			}
		}
		resultMap.put("checkList", checkList);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
}
