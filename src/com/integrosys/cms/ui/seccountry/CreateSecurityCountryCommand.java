/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.seccountry;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup;
import com.integrosys.cms.app.chktemplate.bus.TemplateNotSetupException;
import com.integrosys.cms.app.chktemplate.bus.ITemplate;
import com.integrosys.cms.app.chktemplate.proxy.ICheckListTemplateProxyManager;
import com.integrosys.cms.app.chktemplate.proxy.CheckListTemplateProxyManagerFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: elango $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/28 10:52:34 $ Tag: $Name: $
 */
public class CreateSecurityCountryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public CreateSecurityCountryCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "secType", "java.lang.String", REQUEST_SCOPE },
				{ "subType", "java.lang.String", REQUEST_SCOPE }, { "countryCode", "java.lang.String", REQUEST_SCOPE } });
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
				{ "template", "com.integrosys.cms.app.chktemplate.bus.ITemplate", SERVICE_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "dynamicPropertySetupList", "com.integrosys.cms.app.chktemplate.bus.IDynamicPropertySetup[]", SERVICE_SCOPE },
				{ "entryCodesArrayList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "idList", "java.util.ArrayList", SERVICE_SCOPE },
				{ "categoryList", "java.util.ArrayList", SERVICE_SCOPE },
		});
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
		String secType = (String) map.get("secType");
		String subType = (String) map.get("subType");
		String countryCode = (String) map.get("countryCode");
		try {
			ICheckListTemplateProxyManager proxy = CheckListTemplateProxyManagerFactory.getCheckListTemplateProxyManager();
			boolean wip = proxy.pendingCreateCollateralTemplateAlreadyExist(secType, subType, countryCode);
			DefaultLogger.debug(this, "wip flag>>>>>" + wip);
			
			if(subType!=null){
				if(subType.equalsIgnoreCase(ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH)){
					IDynamicPropertySetup[] dynamicPropertySetupList = proxy.getDynamicPropertySetup(subType);
					ArrayList entryCodesArrayList = new ArrayList();
					ArrayList idList = new ArrayList();
					ArrayList categoryList = new ArrayList();
					for(int a1=0;a1<dynamicPropertySetupList.length;a1++){
						for(int a2=0;a2<dynamicPropertySetupList[a1].getEntryCodes().length;a2++){
							entryCodesArrayList.add(dynamicPropertySetupList[a1].getEntryCodes()[a2]);
							idList.add(String.valueOf(a1+1));
							if(a1==0){
								categoryList.add(ICMSConstant.CATEGORY_OF_PBR_PBT);
							}else{
								categoryList.add(ICMSConstant.CATEGORY_OF_GOODS_STATUS);
							}	
						}	
					}
					if(dynamicPropertySetupList.length!=0){
						resultMap.put("dynamicPropertySetupList", dynamicPropertySetupList);
						resultMap.put("entryCodesArrayList", entryCodesArrayList);
						resultMap.put("idList", idList);
						resultMap.put("categoryList", categoryList);
					}
				}
			}
			
			ITemplate template = proxy.getDefaultCollateralTemplate(secType, subType, countryCode);
			resultMap.put("itemTrxVal", null);
			resultMap.put("template", template);
			if (wip) {
				resultMap.put("wip", "wip");
			}
		}
		catch (TemplateNotSetupException e) {
			resultMap.put("no_template", "true");
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
