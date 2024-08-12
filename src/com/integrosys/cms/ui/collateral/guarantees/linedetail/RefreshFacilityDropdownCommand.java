package com.integrosys.cms.ui.collateral.guarantees.linedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;

public class RefreshFacilityDropdownCommand extends AbstractCommand implements ILineDetailConstants{

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String dropdownName = (String) map.get(REQUEST_DROPDOWN_NAME);
		String curSel = (String) map.get(REQUEST_SELECTED_VALUE);
		List<ILimit> facDetailList = (List<ILimit>) map.get(SESSION_FAC_DETAIL_LIST);
		List<LabelValueBean> facNameList = (List<LabelValueBean>) map.get(SESSION_FAC_NAME_LIST);
		List<LabelValueBean> facIdList = (List<LabelValueBean>) map.get(SESSION_FAC_ID_LIST);
		List<LabelValueBean> facLineNoList = (List<LabelValueBean>) map.get(SESSION_FAC_LINE_NO_LIST);

		DefaultLogger.info(this ,"Refreshing dropdown list for dropdownName " + dropdownName + " for parent value " + curSel);
		
		if(DROPDOWN_FACILITY_ID.equals(dropdownName)) {
			facIdList = new ArrayList<LabelValueBean>();
		}
		
		if(!AbstractCommonMapper.isEmptyOrNull(curSel)) {
			prepareFacilityDropdownList(facDetailList, dropdownName, curSel, facIdList);
		}
		
		resultMap.put(REQUEST_DROPDOWN_NAME, dropdownName);
		resultMap.put(SESSION_FAC_NAME_LIST, facNameList);
		resultMap.put(SESSION_FAC_ID_LIST, facIdList);
		resultMap.put(SESSION_FAC_LINE_NO_LIST, facLineNoList);
		resultMap.put(SESSION_FAC_DETAIL_LIST, facDetailList);
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	public static void prepareFacilityDropdownList(List<ILimit> facDetailList, String dropdownName, String selectedValue, List<LabelValueBean> returnList){
		for(ILimit limit: facDetailList) {
			String value = null;
			
			if(DROPDOWN_FACILITY_NAME.equals(dropdownName)) {
				value = limit.getFacilityName();
			}
			else if(DROPDOWN_FACILITY_ID.equals(dropdownName)) {
				if(limit.getFacilityName().equals(selectedValue)) {
					value = String.valueOf(limit.getLimitID());
				}
				
			}else if(DROPDOWN_LINE_NO.equals(dropdownName)) {
				value = limit.getLineNo();
			}
			
			if(value != null) {
				LabelValueBean lvBean = new LabelValueBean(value, value);
				if(!returnList.contains(lvBean)) {
					returnList.add(lvBean);
				}
			}
			
		}
	}
	
	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ REQUEST_DROPDOWN_NAME, String.class.getName(), REQUEST_SCOPE },
			{ REQUEST_SELECTED_VALUE, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, List.class.getName(), SERVICE_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][] {
			{ REQUEST_DROPDOWN_NAME, String.class.getName(), REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_LINE_NO_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, List.class.getName(), SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, List.class.getName(), SERVICE_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
}
