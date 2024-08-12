package com.integrosys.cms.ui.pincodemapping;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerEditPincodeMappingCmd extends AbstractCommand implements
ICommonEventConstant  {

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

	public MakerEditPincodeMappingCmd() {
	}
	private  static boolean isStateIdUnique = true;
	/**
	 * @return the isStateIdUnique
	 */
	public static boolean isStateIdUnique() {
		return isStateIdUnique;
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
						"IPincodeMappingTrxValue",
						"com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",
						SERVICE_SCOPE },
				{ "stateId", "java.lang.String", REQUEST_SCOPE },
				{ "pincode", "java.lang.String", REQUEST_SCOPE },
				{ "theOBTrxContext",
						"com.integrosys.cms.app.transaction.OBTrxContext",
						FORM_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "pincodeMappingObj",
						"com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",
						FORM_SCOPE }

		});
	}
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue",
				"com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
			OBPincodeMapping pincodeMapping = (OBPincodeMapping) map.get("pincodeMappingObj");
			String event = (String) map.get("event");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			IPincodeMappingTrxValue trxValueIn = (OBPincodeMappingTrxValue) map
					.get("IPincodeMappingTrxValue");

			IPincodeMappingTrxValue trxValueOut = new OBPincodeMappingTrxValue();
			String pincode = (String) map.get("pincode");
			String newstateId = (String) map.get("stateId");
			long stateId=Long.parseLong(newstateId);
			
			if (event.equals("maker_save_create")) {
				trxValueOut = getPincodeMappingProxy()
						.makerUpdateSaveUpdatePincodeMapping(ctx, trxValueIn,
								pincodeMapping);
			} else if (trxValueIn.getFromState().equals(
					ICMSConstant.STATE_PENDING_PERFECTION)) {
				boolean isStateIdUnique = true;
				 long oldStateId=trxValueIn.getStagingPincodeMapping().getStateId().getIdState();
				if( stateId != oldStateId  && newstateId!=null){
					isStateIdUnique = getPincodeMappingProxy().isStateIdUnique(newstateId.trim(),pincode.trim());
					if(isStateIdUnique!=false){
					exceptionMap.put("dupStateIdError", new ActionMessage("error.string.stateid.exist"));
					IPincodeMappingTrxValue pincodeMappingTrxValue = null;
					resultMap.put("request.ITrxValue", pincodeMappingTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
					}
					
				}
					trxValueOut = getPincodeMappingProxy().makerCreatePincodeMapping(ctx,
						trxValueIn, pincodeMapping);
			} else if ((event.equals("maker_edit_state_pincode_mapping") || event.equals("maker_save_update"))) {
				long oldStateId=0;
				boolean isStateIdUnique = false;
				if ( event.equals("maker_save_update") ){
					oldStateId=trxValueIn.getStagingPincodeMapping().getStateId().getIdState();
				}else{
					oldStateId=trxValueIn.getPincodeMapping().getStateId().getIdState();
				}
				if( stateId != oldStateId && newstateId!=null){
					isStateIdUnique = getPincodeMappingProxy().isStateIdUnique(newstateId.trim(),pincode.trim());
					if(isStateIdUnique!=false){
					exceptionMap.put("dupStateIdError", new ActionMessage("error.string.stateid.exist"));
					IPincodeMappingTrxValue pincodeMappingTrxValue = null;
					resultMap.put("request.ITrxValue", pincodeMappingTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
					}
					
				}
				trxValueOut = getPincodeMappingProxy().makerUpdatePincodeMapping(ctx,
						trxValueIn, pincodeMapping);
			} else if (event.equals("maker_delete_state_pincode_mapping")) {
				trxValueOut = getPincodeMappingProxy().makerUpdatePincodeMapping(ctx,
						trxValueIn, pincodeMapping);
			} else {
				// event is maker_confirm_resubmit_edit
				String remarks = (String) map.get("remarks");
				ctx.setRemarks(remarks);
				boolean isStateIdUnique = false;
				long oldStateId=0;
				if(trxValueIn!=null) {
					if(trxValueIn.getPincodeMapping()!=null) {
						if(trxValueIn.getPincodeMapping().getStateId()!=null) {				
						oldStateId=trxValueIn.getPincodeMapping().getStateId().getIdState();
								
				if( stateId != oldStateId && newstateId!=null){
					isStateIdUnique = getPincodeMappingProxy().isStateIdUnique(newstateId.trim(),pincode.trim());
						if(isStateIdUnique!=false){
							exceptionMap.put("dupStateIdError", new ActionMessage("error.string.stateid.exist"));
							IPincodeMappingTrxValue pincodeMappingTrxValue = null;
							resultMap.put("request.ITrxValue", pincodeMappingTrxValue);
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
						}
				}}}}
				trxValueOut = getPincodeMappingProxy().makerEditRejectedPincodeMapping(
						ctx, trxValueIn, pincodeMapping);
			}

			resultMap.put("request.ITrxValue", trxValueOut);

		} catch (PincodeMappingException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}


}
