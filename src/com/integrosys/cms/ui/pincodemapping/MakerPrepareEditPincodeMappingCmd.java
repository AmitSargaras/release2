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

public class MakerPrepareEditPincodeMappingCmd extends AbstractCommand implements
ICommonEventConstant {

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
	public MakerPrepareEditPincodeMappingCmd() {
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
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "pincode", "java.lang.String", REQUEST_SCOPE },
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
//				{ "pincodeMappingObj","com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",SERVICE_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ "pincodeMappingObj","com.integrosys.cms.app.pincodemapping.bus.OBPincodeMapping",FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{"IPincodeMappingTrxValue","com.integrosys.cms.app.pincodemapping.trx.IPincodeMappingTrxValue",SERVICE_SCOPE },
				{ "trxId", "java.lang.String", REQUEST_SCOPE },
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
			CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IPincodeMapping pincodeMapping;
			IPincodeMappingTrxValue trxValue = null;
			String trxId = (String) (map.get("trxId"));
			/*String pincode = (String) (map.get("pincode"));*/
			String event = (String) map.get("event");
			String startIdx = (String) map.get("startIndex");
			if(("maker_edit_state_pincode_mapping_error").equals(event) || ("maker_edit_draft_state_pincode_mapping_error").equals(event)){
				trxValue = (OBPincodeMappingTrxValue) getPincodeMappingProxy()
						.getPincodeMappingTrxValue(Long.parseLong(trxId));
				pincodeMapping = (OBPincodeMapping) map.get("pincodeMappingObj");
			}else{
				trxValue = (OBPincodeMappingTrxValue) getPincodeMappingProxy()
						.getPincodeMappingTrxValue(Long.parseLong(trxId));
				pincodeMapping = (OBPincodeMapping) trxValue.getPincodeMapping();
			}
			

			if ((trxValue.getStatus().equals("PENDING_CREATE"))
					|| (trxValue.getStatus().equals("PENDING_UPDATE"))
					|| (trxValue.getStatus().equals("DRAFT"))
					|| (trxValue.getStatus().equals("PENDING_DELETE"))
					|| (trxValue.getStatus().equals("REJECTED"))) {
				resultMap.put("wip", "wip");
			}
			resultMap.put("stateList", getStateList());
			resultMap.put("event", event);
			resultMap.put("IPincodeMappingTrxValue", trxValue);
			resultMap.put("pincodeMappingObj", pincodeMapping);
			resultMap.put("startIndex", startIdx);
			resultMap.put("trxId", trxId);
			
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
