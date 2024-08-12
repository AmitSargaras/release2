package com.integrosys.cms.ui.pincodemapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.geography.state.bus.IState;
import com.integrosys.cms.app.pincodemapping.proxy.IPincodeMappingProxyManager;
import com.integrosys.cms.app.pincodemapping.trx.OBPincodeMappingTrxValue;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerPrepareCreatePincodeCmd extends AbstractCommand implements ICommonEventConstant {
	public MakerPrepareCreatePincodeCmd() {

	}

	private IPincodeMappingProxyManager pincodeMappingProxy;

	public IPincodeMappingProxyManager getPincodeMappingProxy() {
		return pincodeMappingProxy;
	}

	public void setPincodeMappingProxy(IPincodeMappingProxyManager pincodeMappingProxy) {
		this.pincodeMappingProxy = pincodeMappingProxy;
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
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }

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
		return (new String[][] {
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
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
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String startIdx = (String) map.get("startIndex");

		OBPincodeMappingTrxValue pincodeMappingTrxValue = new OBPincodeMappingTrxValue();

		resultMap.put("stateList", getStateList());
		resultMap.put("IPincodeMappingTrxValue", pincodeMappingTrxValue);
		// resultMap.put("hubValueList",getHUBList() );
		resultMap.put("startIndex", startIdx);
		
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
