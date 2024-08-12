package com.integrosys.cms.ui.pincodemapping;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;

public class MakerReadPincodeMappingCmd extends AbstractCommand implements
ICommonEventConstant{

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
	public MakerReadPincodeMappingCmd() {
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
				{ "pincode", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE } });
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
				{ "pincodeMappingObj","com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "pincodeMappingObj","com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
				{"IPincodeMappingTrxValue","com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",SERVICE_SCOPE } });
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
		try {
			IPincodeMapping pincodeMapping;
			IPincodeMappingTrxValue trxValue = null;
			String event = (String) map.get("event");
			String trxId = "";
				trxId = (String) (map.get("trxId"));
			
			String startIdx = (String) map.get("startIndex");
			
			trxValue = (OBPincodeMappingTrxValue) getPincodeMappingProxy()
					.getPincodeMappingTrxValue(Long.parseLong(trxId));
			pincodeMapping = (OBPincodeMapping) trxValue.getPincodeMapping();

			resultMap.put("event", event);
			resultMap.put("trxId", trxId);
			resultMap.put("IPincodeMappingTrxValue", trxValue);
			resultMap.put("pincodeMappingObj", pincodeMapping);
			resultMap.put("startIndex", startIdx);
		} catch (PincodeMappingException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
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
