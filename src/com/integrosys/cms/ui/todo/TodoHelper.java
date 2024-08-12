package com.integrosys.cms.ui.todo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;

public class TodoHelper {

	public static List getFilterByValueList(ITeam team, String filterByType) {
		DefaultLogger.debug("ToDoHelper", "<<<<<<<<<< in getFilterByValueList: " + filterByType);
		if ((filterByType == null) || (filterByType.trim().length() == 0)) {
			return new ArrayList();
		}

		if (ICMSConstant.FILTER_BY_ORG.equals(filterByType)) {
			return getFilterByOrg(team);
		}

		if (ICMSConstant.FILTER_BY_CMS_SEGMENT.equals(filterByType)) {
			return getFilterByCMSSegment(team);
		}

		return new ArrayList();

	}

	private static List getFilterByOrg(ITeam team) {
		DefaultLogger.debug("ToDoHelper", "<<<<<<<<<< in getFilterByOrg method");
		List lbValList = new ArrayList();

		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.CATEGORY_CODE_BKGLOC, null, null);
		if (team.getOrganisationCodes() != null) {
			String[] orgCodes = team.getOrganisationCodes();
			for (int j = 0; j < orgCodes.length; j++) {
				String value = StringEscapeUtils.escapeHtml((String) map.get(orgCodes[j]));
				LabelValueBean lvBean = new LabelValueBean(value + " (" + orgCodes[j] + ")", orgCodes[j]);
				lbValList.add(lvBean);
			}
		}

		return CommonUtil.sortDropdown(lbValList);
	}

	private static List getFilterByCMSSegment(ITeam team) {
		List lbValList = new ArrayList();

		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.BIZ_SGMT, null, null);
		if (team.getCMSSegmentCodes() != null) {
			String[] segmentCodes = team.getCMSSegmentCodes();
			for (int j = 0; j < segmentCodes.length; j++) {
				String value = StringEscapeUtils.escapeHtml((String) map.get(segmentCodes[j]));
				LabelValueBean lvBean = new LabelValueBean(value, segmentCodes[j]);
				lbValList.add(lvBean);
			}
		}

		return CommonUtil.sortDropdown(lbValList);
	}

	public static List getCMSSegmentList() {
		List lbValList = new ArrayList();

		HashMap map = CommonDataSingleton.getCodeCategoryValueLabelMap(ICMSConstant.BIZ_SGMT, null, null);
		if (map != null) {
			Collection keySet = map.keySet();
			Iterator itr = keySet.iterator();
			while (itr.hasNext()) {
				String key = (String) itr.next();
				LabelValueBean lvBean = new LabelValueBean(StringEscapeUtils.escapeHtml((String) map.get(key)), key);
				lbValList.add(lvBean);
			}
		}

		return CommonUtil.sortDropdown(lbValList);
	}
}