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
public class MakerEditPrepareCommonCodeTypeCmd extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public MakerEditPrepareCommonCodeTypeCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "transactionId", "java.lang.String", REQUEST_SCOPE },
				{ "categoryId", "java.lang.String", REQUEST_SCOPE } });
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
				{ "OBCommonCodeTypeTrxValue", "com.integrosys.cms.app.commoncode.trx.OBCommonCodeTypeTrxValue",
						SERVICE_SCOPE },
				{ "commonCodeType", "com.integrosys.cms.app.commoncode.bus.ICommonCodeType", FORM_SCOPE },
				{ "transactionId", "java.lang.String", REQUEST_SCOPE } });
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
		String event = (String) map.get("event");
		String transactionId = (String) map.get("transactionId"); // for
																	// checking
																	// of WIP?
		DefaultLogger.debug(this, "Event : " + event);
		DefaultLogger.debug(this, "Inside doExecute()  transactionId=" + transactionId);
		try {

			ICommonCodeTypeProxy proxy = CommonCodeTypeManagerFactory.getCommonCodeTypeProxy();

			ICommonCodeTypeTrxValue commonCodeTypeTrxVal = proxy.getCategoryTrxId(transactionId);

			resultMap.put("OBCommonCodeTypeTrxValue", commonCodeTypeTrxVal);
			resultMap.put("commonCodeType", commonCodeTypeTrxVal.getCommonCodeType());
			resultMap.put("transactionId", transactionId);

		}
		catch (CommonCodeTypeException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
