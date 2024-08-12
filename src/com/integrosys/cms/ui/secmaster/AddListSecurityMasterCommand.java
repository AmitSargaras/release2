package com.integrosys.cms.ui.secmaster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.DocumentSearchResultItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.OBTemplate;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class AddListSecurityMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddListSecurityMasterCommand() {
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "isEdit", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "sAddTemplateList", "java.util.ArrayList", SERVICE_SCOPE } });
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		try {
			ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("itemTrxVal");
			String isEdit = (String) map.get("isEdit");
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			DocumentSearchResultItem item[];
			ITemplate temp = (ITemplate) map.get("template");
			if (temp == null) {
				temp = new OBTemplate();
				temp.setTemplateType(ICMSConstant.DOC_TYPE_SECURITY);
			}

			if ("true".equals(isEdit)) {
				if (ICMSConstant.STATE_REJECTED.equals(itemTrxVal.getStatus())) {
					item = proxy.getDocumentItemList(itemTrxVal.getStagingTemplate());
				}
				else {
					item = proxy.getDocumentItemList(itemTrxVal.getTemplate());
				}
			}
			else {
				item = proxy.getDocumentItemList(temp);
			}

			List list = new ArrayList();
			if ((item != null) && (item.length > 0)) {
				for (int i = 0; i < item.length; i++) {
					list.add(item[i]);
				}
			}

			Collections.sort(list);
			resultMap.put("sAddTemplateList", list);
		}
		catch (TemplateNotSetupException ex) {
			throw new CommandProcessingException("template is not setup", ex);
		}
		catch (CheckListTemplateException ex) {
			DefaultLogger.warn(this, "failed to retrieve document item list", ex);
		}

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
