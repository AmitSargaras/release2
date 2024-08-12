package com.integrosys.cms.ui.commoncode;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractOptionsListAction;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.component.commondata.app.CommonDataSearchContext;

/**
 * <p>
 * Action to generate common codes options tag. This wont generate the
 * <b>'Please Select'</b> option as the first of select tag.
 * <p>
 * Required parameter from request is
 * <ul>
 * <li>categoryCode
 * <li>refEntryCode
 * <li>descWithCode
 * </ul>
 * @author Chong Jun Yong
 * 
 */
public class RefreshCommonCodesAction extends AbstractOptionsListAction {

	protected String extractLabel(Object option) {
		LabelValueBean labelValue = (LabelValueBean) option;
		return labelValue.getLabel();
	}

	protected String extractValue(Object option) {
		LabelValueBean labelValue = (LabelValueBean) option;
		return labelValue.getValue();
	}

	protected List generateOptionsList(ActionForm form, HttpServletRequest request) {
		String categoryCode = request.getParameter("categoryCode");
		String refEntryCode = request.getParameter("refEntryCode");
		String descWithCode = request.getParameter("descWithCode");

		CommonDataSearchContext context = new CommonDataSearchContext(categoryCode, null, null, refEntryCode);

		CommonCodeList commonCodes = CommonCodeList.getInstance(context, StringUtils.isNotBlank(descWithCode) ? Boolean
				.valueOf(descWithCode).booleanValue() : false, null);

		return commonCodes.getOptionList();
	}

}