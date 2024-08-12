/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.checklist.document;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.DocumentHeldSearchCriteria;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: vishal $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/12/27 09:12:46 $ Tag: $Name: $
 */
public class ListSecurityDocsCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ListSecurityDocsCommand() {
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
				{ "checklist_id", "java.lang.String", REQUEST_SCOPE },
				{ "collateral_id", "java.lang.String", REQUEST_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "docsHeldMap", "java.util.HashMap", REQUEST_SCOPE },
		// {"collateralOB", "com.integrosys.cms.app.collateral.bus.ICollateral",
		// REQUEST_SCOPE},
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
			ICollateralProxy collateralProxy = CollateralProxyFactory.getProxy();
			ICMSCustomer customer = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			String checkListIDStr = (String) map.get("checklist_id");
			long checkListID = Long.parseLong(checkListIDStr);
			String collateralIDStr = (String) map.get("collateral_id");
			long collateralID = Long.parseLong(collateralIDStr);
			// if (!customer.getNonBorrowerInd()) //if borrower
			// {
			DefaultLogger.debug(this, "Customer is BORROWER.");
			ILimitProfile limit = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);
			long limitProfileID = limit.getLimitProfileID();
			DefaultLogger.debug(this, "limitProfileID=" + limitProfileID);

			DocumentHeldSearchCriteria criteria = new DocumentHeldSearchCriteria();
			criteria.setLimitProfileID(limitProfileID);
			criteria.setCheckListID(checkListID);
			criteria.setCategory(ICMSConstant.CHECKLIST_SECURITY);

			HashMap docsHeldMap = proxy.getDocumentsHeld(criteria);
			// HashMap docsHeldMap = proxy.getSecDocumentsHeld(limitProfileID,
			// checkListID);
			// ICollateral collateral =
			// (ICollateral)collateralProxy.getCollateral(collateralID,true);

			// resultMap.put("collateralOB", collateral);
			resultMap.put("docsHeldMap", docsHeldMap);
			// }
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
