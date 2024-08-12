/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccountry;

import java.util.Arrays;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/21 03:57:22 $ Tag: $Name: $
 */
public class AddSecurityCountryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public AddSecurityCountryCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "templateItem", "com.integrosys.cms.app.chktemplate.bus.OBItem", FORM_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE }, });
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
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");

		OBItem item = (OBItem) map.get("templateItem");
		DefaultLogger.debug(this, "Item before create" + item);

		ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("itemTrxVal");
		ITemplate temp;
		if (itemTrxVal != null) {
			if (ICMSConstant.STATE_REJECTED.equals(itemTrxVal.getStatus())) {
				DefaultLogger.debug(this, "getting staging");
				temp = itemTrxVal.getStagingTemplate();
			}
			else {
				DefaultLogger.debug(this, "getting actual");
				temp = itemTrxVal.getTemplate();
			}
		}
		else {
			DefaultLogger.debug(this, "item trx null");
			temp = (ITemplate) map.get("template");
		}
		String index = (String) map.get("index");
		if ((index != null) && (index.length() != 0)) {
			int index1 = Integer.parseInt(index);
			temp.updateItem(index1, item);
			DefaultLogger.debug(this, "updating item ");
		}
		else {
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			try {
				boolean isDocumentCodeUnique = proxy.isDocumentCodeUnique(item.getItemCode().trim(),
						ICMSConstant.DOC_TYPE_CC);
				if (!isDocumentCodeUnique) {
					exceptionMap.put("docCode", new ActionMessage("error.string.document.exist"));
					resultMap.put("template", temp);
					resultMap.put("itemTrxVal", itemTrxVal);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
			}
			catch (CheckListTemplateException ex) {
				throw new CommandProcessingException(
						"failed to retrieve document whether the code is unique for code [" + item.getItemCode() + "]",
						ex);
			}

			if (temp.getTemplateItemList() != null) {
				for (int i = 0; i < temp.getTemplateItemList().length; i++) {
					if (item.getItemCode().trim().equals(temp.getTemplateItemList()[i].getItem().getItemCode())) {
						exceptionMap.put("docCode", new ActionMessage("error.string.document.exist"));
						resultMap.put("template", temp);
						resultMap.put("itemTrxVal", itemTrxVal);
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
				}
			}
			temp.addItem(item);
			DefaultLogger.debug(this, "adding item ");
		}

		Arrays.sort(temp.getTemplateItemList());
		resultMap.put("template", temp);
		resultMap.put("itemTrxVal", itemTrxVal);

		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
