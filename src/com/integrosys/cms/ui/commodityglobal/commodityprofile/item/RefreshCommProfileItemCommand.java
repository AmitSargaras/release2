/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/RefreshCommProfileItemCommand.java,v 1.2 2004/06/04 05:11:33 hltan Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 05:11:33 $ Tag: $Name: $
 */
public class RefreshCommProfileItemCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
		/*
		 * {"indexID", "java.lang.String", REQUEST_SCOPE},
		 * {"commProfileItemObj",
		 * "com.integrosys.cms.app.commodity.main.bus.profile.IProfile",
		 * FORM_SCOPE}, {"commProfileTrxValue",
		 * "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
		 * SERVICE_SCOPE},
		 */
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
		// {"commProfileItemObj",
		// "com.integrosys.cms.app.commodity.main.bus.profile.IProfile",
		// FORM_SCOPE},
		// {"commProfileTrxValue",
		// "com.integrosys.cms.app.commodity.main.trx.profile.IProfileTrxValue",
		// SERVICE_SCOPE},
		});
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
		/*
		 * int index = Integer.parseInt((String)map.get("indexID"));
		 * IProfileTrxValue trxValue =
		 * (IProfileTrxValue)map.get("commProfileTrxValue"); IProfile obj =
		 * (IProfile)map.get("commProfileItemObj");
		 * 
		 * if (index != -1) { IProfile[] list = trxValue.getStagingProfile();
		 * list[index] = obj; trxValue.setStagingProfile(list); }
		 * result.put("commProfileTrxValue", trxValue);
		 */
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
