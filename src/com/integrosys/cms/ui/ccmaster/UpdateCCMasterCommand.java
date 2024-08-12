/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.ccmaster;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/05 09:28:29 $ Tag: $Name: $
 */
public class UpdateCCMasterCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public UpdateCCMasterCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "itemTrxVal", "com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue", SERVICE_SCOPE },
				{ "mandatoryForBorrowerID", "java.lang.String", REQUEST_SCOPE },
				{ "mandatoryForPledgorID", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
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
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) map.get("itemTrxVal");
			String mandatoryForBorrowerID = (String) map.get("mandatoryForBorrowerID");
			String mandatoryForPledgorID = (String) map.get("mandatoryForPledgorID");
			ITemplate template = (ITemplate) map.get("template");
			DefaultLogger.debug(this, "template in submit --->" + template);
			StringTokenizer st = new StringTokenizer(mandatoryForBorrowerID, ",");
			StringTokenizer ss = new StringTokenizer(mandatoryForPledgorID, ",");
			HashMap hm = new HashMap();
			HashMap hm1 = new HashMap();
			while (st.hasMoreTokens()) {
				String key = st.nextToken();
				hm.put(key, key);
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>Mandatory Id in submit keys" + key);
			}
			while (ss.hasMoreTokens()) {
				String key = ss.nextToken();
				hm1.put(key, key);
			}
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			ITemplateItem temp1[] = template.getTemplateItemList();
			if (temp1 != null) {
				for (int i = 0; i < temp1.length; i++) {
					if (hm.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(false);
					}
					
					if (hm1.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(false);
					}
				}
			}
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating rejected item");
			proxy.makerEditRejectedTemplateTrx(ctx, itemTrxVal, template);

            resultMap.put("request.ITrxValue", itemTrxVal);            
        }
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}
