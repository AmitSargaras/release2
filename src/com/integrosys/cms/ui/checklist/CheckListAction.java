/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/checklist/CheckListAction.java,v 1.12 2006/07/20 01:44:56 wltan Exp $
 */
package com.integrosys.cms.ui.checklist;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: wltan $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/07/20 01:44:56 $ Tag: $Name: $
 */
public class CheckListAction extends CommonAction {

	private static final String VIEW_SHARE_DOC = "view_share_doc";

	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if ("process".equals(event) || "edit_staging_checklist_item".equals(event)
				|| "close_checklist_item".equals(event) || "edit".equals(event) || "to_track".equals(event)
				|| "cancel_checklist_item".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RedirectCheckListCommand();
		}
		else if (VIEW_SHARE_DOC.equals(event)) { // R1.5 CR17 - View Share
													// Documents Link
			objArray = new ICommand[1];
			objArray[0] = new ViewShareDocumentsCommand();
		}
		else if ("viewDocumentStatus".equals(event)) { // R1.5 CR17 - View Share
														// Documents Link
			objArray = new ICommand[1];
			objArray[0] = new ViewDocumentationStatusCommand();
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

		if (VIEW_SHARE_DOC.equals(event)) { // R1.5 CR17: View Share Documents
			aPage.setPageReference(event);
			return aPage;
		}
		if ("viewDocumentStatus".equals(event)) { // R1.5 CR17: View Share
													// Documents
			aPage.setPageReference(event);
			return aPage;
		}

		String trxSubType = (String) resultMap.get("trxSubType");
		String checkListCategory = (String) resultMap.get("checkListCategory");
		aPage.setPageReference(getRedirectPath(trxSubType, checkListCategory));
		return aPage;
	}

	private String getRedirectPath(String trxSubType, String checkListCategoryCode) {
		String redirectPath = "";
		if (ICMSConstant.DOC_TYPE_CC.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_cc" : "maintain_cc";
		}
		if (ICMSConstant.DOC_TYPE_SECURITY.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_sec" : "maintain_sec";
		}
		if (ICMSConstant.DOC_TYPE_CAM.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_cam" : "maintain_cam";
		}
		if (ICMSConstant.DOC_TYPE_OTHER.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_other" : "maintain_other";
		}
		if (ICMSConstant.DOC_TYPE_RECURRENT_MASTER.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_recurrentDoc" : "maintain_recurrentDoc";
		}
		if (ICMSConstant.DOC_TYPE_FACILITY.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_fac" : "maintain_fac";
		}
		if (ICMSConstant.DOC_TYPE_PARIPASSU.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_pp" : "maintain_pp";
		}
		if (ICMSConstant.DOC_TYPE_LAD.equals(checkListCategoryCode)) {
			redirectPath = (trxSubType.endsWith("REC")) ? "update_lad" : "maintain_lad";
		}
		return redirectPath;
	}
}