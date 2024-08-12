/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/feed/bond/list/RejectBondListCommand.java,v 1.7 2003/09/22 04:51:48 btchng Exp $
 */
package com.integrosys.cms.ui.digitalLibrary;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.digitalLibrary.proxy.IDigitalLibraryProxy;
import com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue;
import com.integrosys.cms.app.generalparam.trx.IGeneralParamGroupTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * @author $Author: btchng $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/09/22 04:51:48 $ Tag: $Name: $
 */
public class RejectDigitalLibraryCommand extends AbstractCommand {
	
	private IDigitalLibraryProxy digitalLibraryProxy;
	
	/**
	 * @return the digitalLibraryProxy
	 */
	public IDigitalLibraryProxy getDigitalLibraryProxy() {
		return digitalLibraryProxy;
	}

	/**
	 * @param digitalLibraryProxy the digitalLibraryProxy to set
	 */
	public void setDigitalLibraryProxy(IDigitalLibraryProxy digitalLibraryProxy) {
		this.digitalLibraryProxy = digitalLibraryProxy;
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
				// Consume the current feed entries to be saved as a whole.
				{ "digitalLibraryTrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } ,
				{ "remarks", "java.lang.String", REQUEST_SCOPE} };
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.digitalLibrary.trx.IDigitalLibraryTrxValue",
				REQUEST_SCOPE } };
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Map is " + map);

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			IDigitalLibraryTrxValue value = (IDigitalLibraryTrxValue) map.get("digitalLibraryTrxValue");
			
			String remarks = (String) map.get("remarks");

			ITrxContext trxContext = (ITrxContext) map.get("theOBTrxContext");

			// Added because when going from "view limits" to "manual feeds
			// update", some values are set in the trx context object which is
			// "global". Hence has to explicitly set the below to null.
			trxContext.setCustomer(null);
			trxContext.setLimitProfile(null);

			if(remarks == null || remarks.equals("")){
            	exceptionMap.put("remarks", new ActionMessage("error.reject.remark"));
            	IDigitalLibraryTrxValue digitalLibraryTrxValue = null;
            	resultMap.put("digitalLibraryTrxValue", digitalLibraryTrxValue);
            	resultMap.put("request.ITrxValue", digitalLibraryTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
            }else{
            	value = getDigitalLibraryProxy().checkerRejectDigitalLibraryGroup(trxContext, value);

            	resultMap.put("request.ITrxValue", value);
            }	

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}
}
