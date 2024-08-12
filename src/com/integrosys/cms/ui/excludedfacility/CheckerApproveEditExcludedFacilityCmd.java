package com.integrosys.cms.ui.excludedfacility;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.excludedfacility.proxy.IExcludedFacilityProxyManager;
import com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue;
import com.integrosys.cms.app.excludedfacility.trx.OBExcludedFacilityTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveEditExcludedFacilityCmd extends AbstractCommand implements ICommonEventConstant {

	private IExcludedFacilityProxyManager excludedFacilityProxy;

	public IExcludedFacilityProxyManager getExcludedFacilityProxy() {
		return excludedFacilityProxy;
	}

	public void setExcludedFacilityProxy(IExcludedFacilityProxyManager excludedFacilityProxy) {
		this.excludedFacilityProxy = excludedFacilityProxy;
	}
	/**
	 * Default Constructor
	 */
	public CheckerApproveEditExcludedFacilityCmd() {
	}
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IExcludedFacilityTrxValue", "com.integrosys.cms.app.excludedfacility.trx.IExcludedFacilityTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"excludedfacilityDescription", "java.lang.String", REQUEST_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}
	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
	}
	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// ExcludedFacility Trx value
			
			String event = (String) map.get("event");
			
			IExcludedFacilityTrxValue trxValueIn = (OBExcludedFacilityTrxValue) map.get("IExcludedFacilityTrxValue");
			String excludedFacilityDescription = (String) map.get("excludedfacilityDescription");
			boolean isExcludedFacilityDescriptionUnique = false;
			if(trxValueIn.getFromState().equals("ND") || trxValueIn.getStatus().equals("PENDING_CREATE")){
				isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
				if(isExcludedFacilityDescriptionUnique != false){
					exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}else if(trxValueIn.getFromState().equals("ACTIVE") && trxValueIn.getStatus().equals("PENDING_UPDATE")){
				if(null!=excludedFacilityDescription){
				if(!(excludedFacilityDescription.equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
				isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
				if(isExcludedFacilityDescriptionUnique != false){
					exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
						}
					}
				}
			}else if(trxValueIn.getFromState().equals("DRAFT") && trxValueIn.getStatus().equals("PENDING_UPDATE")){
				if(null!=excludedFacilityDescription){
					if(!(excludedFacilityDescription.equals(trxValueIn.getExcludedFacility().getExcludedFacilityDescription()))){
					isExcludedFacilityDescriptionUnique = getExcludedFacilityProxy().isExcludedFacilityDescriptionUnique(excludedFacilityDescription);				
					if(isExcludedFacilityDescriptionUnique != false){
						exceptionMap.put("excludedFacilityDescriptionError", new ActionMessage("error.string.exist","This Facility "));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
							}
						}
					}
				}
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated ExcludedFacility Trx
			IExcludedFacilityTrxValue trxValueOut = getExcludedFacilityProxy().checkerApproveExcludedFacility(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (SystemBankException ex) {
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
