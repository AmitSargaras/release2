package com.integrosys.cms.ui.collateral.marketablesec.linedetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;

public class RefreshFacilityDropdownCommand extends AbstractCommand implements IMarketableEquityLineDetailConstants {

	public String[][] getParameterDescriptor() {
		return new String[][] {
			{ REQUEST_DROPDOWN_NAME, "java.lang.String", REQUEST_SCOPE },
			{ REQUEST_SELECTED_VALUE, "java.lang.String", REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, "java.util.List", SERVICE_SCOPE },
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE}
		};
	}

	public String[][] getResultDescriptor() {
		return new String[][] {
			{ REQUEST_DROPDOWN_NAME, "java.lang.String", REQUEST_SCOPE },
			{ SESSION_FAC_DETAIL_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_ID_LIST, "java.util.List", SERVICE_SCOPE },
			{ SESSION_FAC_NAME_LIST, "java.util.List", SERVICE_SCOPE },
			{ "bondCode", "java.lang.String", REQUEST_SCOPE },
			{ "schemeCode", "java.lang.String", REQUEST_SCOPE },
			{ "stockExchange", "java.lang.String", REQUEST_SCOPE },
			{ "scriptCode", "java.lang.String", REQUEST_SCOPE },
			{ "fundsFeedEntry", "com.integrosys.cms.app.feed.bus.mutualfunds.OBMutualFundsFeedEntry", REQUEST_SCOPE },
			{ "stockFeedEntry", "com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry", REQUEST_SCOPE },
			{ "from_event", String.class.getName(), REQUEST_SCOPE},
			{ "subtype", String.class.getName(), REQUEST_SCOPE},
			{ "indexID", "java.lang.String", REQUEST_SCOPE },
			{ "itemType", String.class.getName(), REQUEST_SCOPE}
		};
	}
	
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		String from_event = (String) map.get("from_event");
		String subtype = (String) map.get("subtype");
		String dropdownName = (String) map.get(REQUEST_DROPDOWN_NAME);
		String curSel = (String) map.get(REQUEST_SELECTED_VALUE);
		List<ILimit> facDetailList = (List<ILimit>) map.get(SESSION_FAC_DETAIL_LIST);
		List facNameList = (List) map.get(SESSION_FAC_NAME_LIST);
		List facIdList = (List) map.get(SESSION_FAC_ID_LIST);
		
		if(DROPDOWN_FACILITY_ID.equals(dropdownName)) {
			facIdList = new ArrayList<LabelValueBean>();
		}
		
		if(StringUtils.isNotBlank(curSel)) {
			prepareFacilityDropdownList(facDetailList, dropdownName, curSel, facIdList);
		}
		resultMap.put(REQUEST_DROPDOWN_NAME, dropdownName);
		resultMap.put(SESSION_FAC_NAME_LIST, facNameList);
		resultMap.put(SESSION_FAC_ID_LIST, facIdList);
		resultMap.put(SESSION_FAC_DETAIL_LIST, facDetailList);
		resultMap.put("from_event", from_event);
		resultMap.put("subtype", subtype);
		resultMap.put("indexID", map.get("indexID"));
		resultMap.put("itemType", map.get("itemType"));
		
		resultMap.put("bondCode", map.get("bondCode"));
		resultMap.put("schemeCode", map.get("schemeCode"));
		resultMap.put("stockExchange", map.get("stockExchange"));
		resultMap.put("scriptCode", map.get("scriptCode"));
		resultMap.put("stockFeedEntry", map.get("stockFeedEntry"));
		resultMap.put("fundsFeedEntry", map.get("fundsFeedEntry"));
		System.out.println("fundsFeedEntry in RefreshFacilityDropDown is "+map.get("fundsFeedEntry"));

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	public static void prepareFacilityDropdownList(List<ILimit> facDetailList, String dropdownName,
			String selectedValue, List<LabelValueBean> returnList) {

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

}
