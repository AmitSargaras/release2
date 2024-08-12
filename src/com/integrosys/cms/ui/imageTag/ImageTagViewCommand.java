package com.integrosys.cms.ui.imageTag;

/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/CreateSampleTestCommand.java,v 1.3 2004/07/08 12:32:45 jtan Exp $
 */

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * This command creates a diary item
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/08 12:32:45 $ Tag: $Name: $
 */

public class ImageTagViewCommand extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		DefaultLogger.debug(this, "******** getParameterDescriptor Call: ");
		return (new String[][] { { "ImageUploadObj","com.integrosys.cms.ui.image.OBImageUpload", FORM_SCOPE },
								{ "fromMenu", "java.lang.String", REQUEST_SCOPE },
								{ "userType", "java.lang.String", REQUEST_SCOPE },
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
				{ "userType", "java.lang.String", SERVICE_SCOPE },
				});
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @throws CommandProcessingException
	 *             on errors
	 * @throws CommandValidationException
	 *             on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		DefaultLogger.debug(this, "********  doExecute Call: " + map.size());
		DefaultLogger.debug(this, "********  doExecute Call: " + map);

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		String fromMenu=(String) map.get("fromMenu");
		String userType=(String) map.get("userType");

		
		DefaultLogger.debug(this, "==========userType==================>"+userType);
		if("Y".equals(fromMenu)){
			result.put("userType", userType);
		}

		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
