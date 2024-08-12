/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/doctemplate/DocumentTemplateAction.java,v 1.4 2006/07/28 02:40:34 hmbao Exp $
 */
package com.integrosys.cms.ui.doctemplate;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.chktemplate.trx.ITemplateTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/07/28 02:40:34 $ Tag: $Name: $
 */
public class DocumentTemplateAction extends CommonAction {

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("process".equals(event) || "edit_staging_template_item".equals(event)
				|| "close_template_item".equals(event) || "to_track".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadDocumentTemplateTrxCommand();
		}
		return (objArray);
	}

	/**
	 * This method is used to determine which the page to be displayed next
	 * using the event Result hashmap and exception hashmap.It returns the page
	 * object .
	 * 
	 * @param event is of type String
	 * @param resultMap is of type HashMap
	 * @param exceptionMap is of type HashMap
	 * @return IPage
	 */
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		ITemplateTrxValue itemTrxVal = (ITemplateTrxValue) resultMap.get("itemTrxVal");
		aPage.setPageReference(getRedirectPath(itemTrxVal));
		return aPage;
	}

	private String getRedirectPath(ITemplateTrxValue itemTrxVal) {
		String redirectPath = "";
		
		if (ICMSConstant.DOC_TYPE_CC.equals(itemTrxVal.getStagingTemplate().getTemplateType())) {
			if (itemTrxVal.getStagingTemplate().getCountry() == null) {
				redirectPath = "cc_master";
			}
			else {
				redirectPath = "cc_country";
			}
		}
		if (ICMSConstant.DOC_TYPE_SECURITY.equals(itemTrxVal.getStagingTemplate().getTemplateType())) {
			if (itemTrxVal.getStagingTemplate().getCountry() == null) {
				redirectPath = "sec_master";
			}
			else {
				redirectPath = "sec_master";
			}
		}
		if (ICMSConstant.DOC_TYPE_FACILITY.equals(itemTrxVal.getStagingTemplate().getTemplateType())) {
			
				redirectPath = "fac_master";
			
			
		}
		
		DefaultLogger.debug(this, "Redirect path for Document template >>>>>>>>>>>>>>>>>>>>>>> " + redirectPath);
		return redirectPath;
	}
}