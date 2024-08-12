/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tat/TatCommand.java,v 1.6 2003/11/11 05:02:19 pooja Exp $
 */

package com.integrosys.cms.ui.tatduration;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatduration.bus.ITatParamDAO;
import com.integrosys.cms.app.tatduration.proxy.ITatParamProxy;
import com.integrosys.cms.ui.common.SecuritySubTypeList;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/11 05:02:19 $ Tag: $Name: $
 */
public class ReadAppListCmd extends TatDurationCommand 
{
	/**
	 * Default Constructor
	 */
	public ReadAppListCmd() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
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
		// {"applicationTypeTatList", "java.util.List", REQUEST_SCOPE},
		 {"tatParamList", "java.util.List", REQUEST_SCOPE},
         {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try 
		{
			ITatParamProxy proxy = getTatParamProxy();
			result.put("applicationTypeTatList", proxy.getTatParamList());
			
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            List tatParamList = new ArrayList();
            tatParamList = (ArrayList) proxy.getTatParamList();

            result.put("tatParamList", tatParamList);

		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
