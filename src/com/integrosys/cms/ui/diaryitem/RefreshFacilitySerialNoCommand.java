package com.integrosys.cms.ui.diaryitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class RefreshFacilitySerialNoCommand  extends AbstractCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "facilityBoardCategory", "java.lang.String", REQUEST_SCOPE },

			{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
			{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
			{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
				GLOBAL_SCOPE },
			{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
			{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
			{ "facilityLineNo", "java.lang.String", REQUEST_SCOPE },
			{ "activityList", "java.util.List", SERVICE_SCOPE },
			});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "facilityLineNoList", "java.util.List", REQUEST_SCOPE },
			{ "facilitySerialNoList", "java.util.List", REQUEST_SCOPE },
			{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
			{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
			{ "facilityBoardCategorylist", "java.util.List", SERVICE_SCOPE },
			{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
			{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
			{ "activityList", "java.util.List", SERVICE_SCOPE },});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		OBDiaryItem obdiaryItem = (OBDiaryItem)map.get("diaryItemObj");
		String facilityBoardCategory = (String) map.get("facilityBoardCategory");
		String facilityLineNo = (String) map.get("facilityLineNo");
		IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
		result.put("facilitySerialNoList", getListOfFacilitySerialNumber(diaryItemJdbc,obdiaryItem.getCustomerReference(),facilityBoardCategory,facilityLineNo));
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
	
	private List getListOfFacilitySerialNumber(IDiaryItemJdbc diaryItemJdbc,String customerName,String facilityBoardCategory,String facilityLineNo) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List facilityLineNumber = diaryItemJdbc.getListOfFacilitySerialNumber(customerName,facilityBoardCategory,facilityLineNo);
				
					for (int i = 0; i < facilityLineNumber.size(); i++) {
						String [] str = (String[]) facilityLineNumber.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
