/*
 * Created on 2007-2-17
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class RefreshLmtBookingLocCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "origBookingCtry", "java.lang.String", REQUEST_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "orgList", "java.util.List", REQUEST_SCOPE }, });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
			AccessDeniedException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();
		try {
			ITeam team = (ITeam) (map.get(IGlobalConstant.USER_TEAM));
			String bookingCountry = (String) (map.get("origBookingCtry"));
			result.put("orgList", getOrgList(bookingCountry, team));
		}
		catch (Exception ex) {
			throw (new CommandProcessingException(ex.getMessage()));
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private List getOrgList(String country, ITeam team) {
//		System.out.println("RETRIEVING ORGANISATION************** COUNTRY CODE" + country);
		List lbValList = new ArrayList();
		ArrayList idList = new ArrayList();
		ArrayList valList = new ArrayList();
		if ((country != null) && !country.trim().equals("")) {
			HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_CODE_BKGLOC, null,
					country);
			Object[] keyArr = map.keySet().toArray();
			for (int i = 0; i < keyArr.length; i++) {
				Object nextKey = keyArr[i];
				boolean allowAdd = false;
				if (team.getOrganisationCodes() != null) {
					String[] orgCodes = team.getOrganisationCodes();
					for (int j = 0; j < orgCodes.length; j++) {
						if (orgCodes[j].equals(nextKey.toString())) {
							allowAdd = true;
							break;
						}
					}
				}
				if (allowAdd) {
					LabelValueBean lvBean = new LabelValueBean(UIUtil.replaceSpecialCharForXml(map.get(nextKey)
							.toString()
							+ " (" + nextKey.toString() + ")"), UIUtil.replaceSpecialCharForXml(nextKey.toString()));
					lbValList.add(lvBean);
				}
			}
		}
		return CommonUtil.sortDropdown(lbValList);
	}
}
