/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccountry;

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
 * @version $Revision: 1.4 $
 * @since $Date: 2003/09/03 15:46:03 $ Tag: $Name: $
 */
public class SubmitCCCountryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public SubmitCCCountryCommand() {
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
				{ "mandatoryForBorrowerRows", "java.lang.String", REQUEST_SCOPE },
				{ "mandatoryForPledgorRows", "java.lang.String", REQUEST_SCOPE },
				{ "checkedInVault", "java.lang.String", REQUEST_SCOPE },
				{ "checkedExtCustodian", "java.lang.String", REQUEST_SCOPE },
				{ "checkedAudit", "java.lang.String", REQUEST_SCOPE },
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue",
				REQUEST_SCOPE } });
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
			String mandatoryForBorrowerRows = (String) map.get("mandatoryForBorrowerRows");
			String mandatoryForPledgorRows = (String) map.get("mandatoryForPledgorRows");
			String checkedInVault = (String) map.get("checkedInVault");
			String checkedExtCustodian = (String) map.get("checkedExtCustodian");
			String checkedAudit = (String) map.get("checkedAudit");
			ITemplate template = (ITemplate) map.get("template");
			HashMap hmMandatoryForBorrowerRows = getMapFromString(mandatoryForBorrowerRows);
			HashMap hmMandatoryForPledgorRows = getMapFromString(mandatoryForPledgorRows);
			HashMap hmCheckedInVault = getMapFromString(checkedInVault);
			HashMap hmCheckedExtCustodian = getMapFromString(checkedExtCustodian);
			HashMap hmCheckedAudit = getMapFromString(checkedAudit);

			ITemplateItem temp[] = template.getTemplateItemList();
			if (temp != null) {
				for (int i = 0; i < temp.length; i++) {
					if (!template.getTemplateItemList()[i].isInherited()) {
						if (hmMandatoryForBorrowerRows.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(false);
						}
						if (hmMandatoryForPledgorRows.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(false);
						}
					}
					if (hmCheckedInVault.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsInVaultInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsInVaultInd(false);
					}
					if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsExtCustInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsExtCustInd(false);
					}
					if (hmCheckedAudit.containsKey(String.valueOf(i))) {
						template.getTemplateItemList()[i].setIsAuditInd(true);
					}
					else {
						template.getTemplateItemList()[i].setIsAuditInd(false);
					}
				}
			}

			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory
					.getCheckListTemplateProxyManager();
			if (itemTrxVal == null) {
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>CREATING");
				itemTrxVal = proxy.makerCreateTemplate(ctx, template);
			}
			else {
				ITemplateItem temp1[] = template.getTemplateItemList();
				if (temp1 != null) {
					for (int i = 0; i < temp1.length; i++) {
						if (!template.getTemplateItemList()[i].isInherited()) {
							if (hmMandatoryForBorrowerRows.containsKey(String.valueOf(i))) {
								template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(true);
							}
							else {
								template.getTemplateItemList()[i].setIsMandatoryForBorrowerInd(false);
							}
							if (hmMandatoryForPledgorRows.containsKey(String.valueOf(i))) {
								template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(true);
							}
							else {
								template.getTemplateItemList()[i].setIsMandatoryForPledgorInd(false);
							}
						}
						if (hmCheckedInVault.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsInVaultInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsInVaultInd(false);
						}
						if (hmCheckedExtCustodian.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsExtCustInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsExtCustInd(false);
						}
						if (hmCheckedAudit.containsKey(String.valueOf(i))) {
							template.getTemplateItemList()[i].setIsAuditInd(true);
						}
						else {
							template.getTemplateItemList()[i].setIsAuditInd(false);
						}
					}
				}
				DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>Updating");
				proxy.makerUpdateTemplate(ctx, itemTrxVal, template);
			}
			resultMap.put("request.ITrxValue", itemTrxVal);
		}
		catch (Exception e) {
			throw new CommandProcessingException("failed to submit cc country template transaction", e);
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private HashMap getMapFromString(String commaSepInput) {
		HashMap hm = new HashMap();
		StringTokenizer st = new StringTokenizer(commaSepInput, ",");
		while (st.hasMoreTokens()) {
			String key = st.nextToken();
			hm.put(key, key);
			DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>Mandatory Id in submit keys" + key);
		}
		return hm;
	}

}
