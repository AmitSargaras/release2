package com.integrosys.cms.ui.geography.region;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.region.proxy.IRegionProxyManager;
import com.integrosys.cms.app.geography.region.trx.IRegionTrxValue;
import com.integrosys.cms.app.geography.region.trx.OBRegionTrxValue;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: Sandeep Shinde
 * @version 2.0
 * @since $Date: 14/02/2011 02:37:00 $ Tag: $Name: $
 */

public class CheckerRejectRegionCommmand extends AbstractCommand implements
		ICommonEventConstant {

	private IRegionProxyManager regionProxy;

	public IRegionProxyManager getRegionProxy() {
		return regionProxy;
	}

	public void setRegionProxy(IRegionProxyManager regionProxy) {
		this.regionProxy = regionProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectRegionCommmand() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"IRegionTrxValue",
						"com.integrosys.cms.app.geography.region.trx.IRegionTrxValue",
						SERVICE_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE }

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
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE }
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IRegionTrxValue trxValueIn = (OBRegionTrxValue) map
					.get("IRegionTrxValue");

			String remarks = (String) map.get("remarks");
			if(remarks.equals("")){
				exceptionMap.put("remarkError", new ActionMessage("error.reject.remark"));
				resultMap.put("request.ITrxValue", trxValueIn);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
				
			}
 			else{
 				ctx.setRemarks(remarks);
			}
			
			IRegionTrxValue trxValueOut = getRegionProxy().checkerRejectRegion(
					ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (RelationshipMgrException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}