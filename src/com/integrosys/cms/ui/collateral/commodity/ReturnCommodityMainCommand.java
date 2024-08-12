/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/ReturnCommodityMainCommand.java,v 1.3 2004/08/04 05:48:28 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/04 05:48:28 $ Tag: $Name: $
 */

public class ReturnCommodityMainCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "commodityMainTrxValue", "java.util.HashMap", SERVICE_SCOPE },
				{ "from_page", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile",
						GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "commodityMainObj", "java.util.HashMap", FORM_SCOPE },
				{ "customerOb", "com.integrosys.cms.app.customer.bus.OBCMSCustomer", REQUEST_SCOPE },
				{ "limitprofileOb", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE }, });
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

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		ILimitProfile limitProfileOB = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

		if (null != custOB) {
			result.put("customerOb", custOB);
		}

		if (null != limitProfileOB) {
			result.put("limitprofileOb", limitProfileOB);
		}

		HashMap trxValueMap = (HashMap) map.get("commodityMainTrxValue");
		String from_event = (String) map.get("from_page");

		HashMap objReturn = new HashMap();
		if ((from_event != null) && from_event.equals(CommodityMainAction.EVENT_READ)) {
			objReturn.put("obj", trxValueMap.get("actual"));
			objReturn.put("limit", trxValueMap.get("actualLimit"));
		}
		else {
			objReturn.put("obj", trxValueMap.get("staging"));
			objReturn.put("limit", trxValueMap.get("stageLimit"));
		}
		result.put("commodityMainObj", objReturn);

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

}
