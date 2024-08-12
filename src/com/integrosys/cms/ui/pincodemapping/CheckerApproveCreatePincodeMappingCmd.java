package com.integrosys.cms.ui.pincodemapping;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class CheckerApproveCreatePincodeMappingCmd extends AbstractCommand
implements ICommonEventConstant  {

	private IPincodeMappingProxyManager pincodeMappingProxy;

	public IPincodeMappingProxyManager getPincodeMappingProxy() {
		return pincodeMappingProxy;
	}

	public void setPincodeMappingProxy(IPincodeMappingProxyManager pincodeMappingProxy) {
		this.pincodeMappingProxy = pincodeMappingProxy;
	}
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreatePincodeMappingCmd() {
	}
	
	//private  static boolean isStateIdUnique = true;
	
	/**
	 * @return the isStateCodeUnique
	 */
//	public static boolean isStateIdUnique() {
//		return isStateIdUnique;
//	}
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{
						"IPincodeMappingTrxValue",
						"com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",
						SERVICE_SCOPE },
				{"pincode", "java.lang.String", REQUEST_SCOPE },
				{"stateId", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE } });
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
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE },
			{"stateName", "java.lang.String", REQUEST_SCOPE } });
	}
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
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
			// PincodeMapping Trx value
			IPincodeMappingTrxValue trxValueIn = (OBPincodeMappingTrxValue) map
					.get("IPincodeMappingTrxValue");
			
			String pincode=(String) map.get("pincode");
			String stateId=(String) map.get("stateId");
			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function to approve updated PincodeMapping Trx
			if ( trxValueIn.getStatus().equals("PENDING_CREATE") ) {
			if(stateId!=null)
				{
					Boolean isStateExists = getPincodeMappingProxy().isStateIdUnique(stateId.trim(),pincode.trim());
					if(isStateExists){
						exceptionMap.put("dupStateIdError", new ActionMessage("error.string.stateid.exist"));
//						IPincodeMappingTrxValue pincodeMappingTrxValue = null;
						if(trxValueIn!=null && trxValueIn.getStagingPincodeMapping()!=null && trxValueIn.getStagingPincodeMapping().getStateId()!=null){
							resultMap.put("stateName", trxValueIn.getStagingPincodeMapping().getStateId().getStateName());
						}
						resultMap.put("request.ITrxValue", trxValueIn);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}	
			}
			IPincodeMappingTrxValue trxValueOut = getPincodeMappingProxy().checkerApprovePincodeMapping(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		} catch (PincodeMappingException ex) {
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
