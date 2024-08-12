package com.integrosys.cms.ui.pincodemapping;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

public class MakerCreatePincodeCmd extends AbstractCommand implements ICommonEventConstant {

	private IPincodeMappingProxyManager pincodeMappingProxy;
	
	public IPincodeMappingProxyManager getPincodeMappingProxy() {
		return pincodeMappingProxy;
	}

	public void setPincodeMappingProxy(IPincodeMappingProxyManager pincodeMappingProxy) {
		this.pincodeMappingProxy = pincodeMappingProxy;
	}
	
	public MakerCreatePincodeCmd() {
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
						{"pincode", "java.lang.String", REQUEST_SCOPE },
						{"stateId", "java.lang.String", REQUEST_SCOPE },
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
			
			String pincode = (String)map.get("pincode");
			String stateId=(String) map.get("stateId");
			
			if(stateId!=null){
				isStateIdUnique = getPincodeMappingProxy().isStateIdUnique(stateId.trim(),pincode.trim());
				if(isStateIdUnique!=false){
				exceptionMap.put("dupStateIdError", new ActionMessage("error.string.stateid.exist"));
				IPincodeMappingTrxValue pincodeMappingTrxValue = null;
				resultMap.put("request.ITrxValue", pincodeMappingTrxValue);
				returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
				return returnMap;
				}
				
			}
			OBPincodeMapping pincodeMapping = (OBPincodeMapping) map.get("pincodeMappingObj");
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
		//	IPincodeMappingTrxValue trxValueIn = (OBPincodeMappingTrxValue)
		//	map.get("IPincodeMappingTrxValue");

			IPincodeMappingTrxValue trxValueOut = new OBPincodeMappingTrxValue();
			trxValueOut = getPincodeMappingProxy().makerCreatePincodeMapping(ctx,
					pincodeMapping);

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
	
		//}
}

	
	}
