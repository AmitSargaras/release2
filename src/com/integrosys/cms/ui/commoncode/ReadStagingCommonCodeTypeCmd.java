package com.integrosys.cms.ui.commoncode;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.commoncode.bus.CommonCodeTypeException;
import com.integrosys.cms.app.commoncode.proxy.CommonCodeTypeManagerFactory;
import com.integrosys.cms.app.commoncode.proxy.ICommonCodeTypeProxy;
import com.integrosys.cms.app.commoncode.trx.ICommonCodeTypeTrxValue;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class ReadStagingCommonCodeTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public ReadStagingCommonCodeTypeCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "transactionId", "java.lang.String", REQUEST_SCOPE } });
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
				{ "commonCodeType", "com.integrosys.cms.app.commoncode.bus.OBCommonCodeType", FORM_SCOPE },
				{ "OBCommonCodeTypeTrxValue", "com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue",
						SERVICE_SCOPE }, { "closeFlag", "java.lang.String", REQUEST_SCOPE } });
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

		String transactionId = (String) map.get("transactionId");

		DefaultLogger.debug(this, "transactionId" + transactionId);

		ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();

		try {
			ICommonCodeTypeTrxValue commonCodeTypeTrxValue = proxy.getCategoryTrxId(transactionId);

			resultMap.put("commonCodeType", commonCodeTypeTrxValue.getStagingCommonCodeType());
			resultMap.put("OBCommonCodeTypeTrxValue", commonCodeTypeTrxValue);
		}
		catch (CommonCodeTypeException ex) {
			throw new CommandProcessingException("failed to retrieve common code type trx value using transaction id ["
					+ transactionId + "]", ex);
		}

		resultMap.put("closeFlag", "true");

		DefaultLogger.debug(this, "Going out of doExecute()");

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
