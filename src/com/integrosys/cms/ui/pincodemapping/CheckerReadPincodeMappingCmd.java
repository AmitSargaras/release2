package com.integrosys.cms.ui.pincodemapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.pincodemapping.bus.IPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping;
import com.integrosys.cms.app.pincodemapping.bus.PincodeMappingException;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class CheckerReadPincodeMappingCmd extends AbstractCommand implements
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
	public CheckerReadPincodeMappingCmd() {
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
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },

				{ "event", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "pincodeMappingObj",
						"com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",
						FORM_SCOPE },
				{
						"IPincodeMappingTrxValue",
						"com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",
						SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "stateList", "java.util.List", REQUEST_SCOPE }
				
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
			CommandValidationException, PincodeMappingException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IPincodeMapping pincodeMapping;
			IPincodeMappingTrxValue trxValue = null;
			String trxId = (String) (map.get("TrxId"));
			String event = (String) map.get("event");
			
			
			if(("maker_confirm_resubmit_edit_error").equals(event) || ("maker_save_update_error").equals(event)
					|| ("checker_approve_create_error").equals(event)){
				trxValue = (OBPincodeMappingTrxValue) getPincodeMappingProxy()
						.getPincodeMappingByTrxID(trxId);
				pincodeMapping = (OBPincodeMapping) map.get("pincodeMappingObj");
			}else{
				trxValue = (OBPincodeMappingTrxValue) getPincodeMappingProxy()
						.getPincodeMappingByTrxID(trxId);
				pincodeMapping = (OBPincodeMapping) trxValue.getStagingPincodeMapping();
			}
			
			resultMap.put("stateList", getStateList());
			resultMap.put("IPincodeMappingTrxValue", trxValue);
			resultMap.put("pincodeMappingObj", pincodeMapping);
			resultMap.put("event", event);
			resultMap.put("TrxId", trxId);
		} catch (PincodeMappingException e) {

			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	private List getStateList() {
		List lbValList = new ArrayList();
		try {
			List idList = (List) getPincodeMappingProxy().getStateList();

			for (int i = 0; i < idList.size(); i++) {
				IState state = (IState) idList.get(i);
				if (state.getStatus().equals("ACTIVE")) {
					String id = Long.toString(state.getIdState());
					String val = state.getStateName();
					LabelValueBean lvBean = new LabelValueBean(val, id);
					lbValList.add(lvBean);
				}
			}
		} catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
