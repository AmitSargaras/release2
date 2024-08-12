/**
 * MaintainCommonCodeParameterAction.java
 *
 * Created on January 29, 2007, 11:05 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.commoncodeentry.CommonCodeEntryConstant;
import com.integrosys.cms.ui.commoncodeentry.CommonCodeEntryCommonAction;

/**
 * @Author: BaoHongMan
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 13:21:41 $ Tag: $Name%
 */
public class MaintainCCEntryListAction extends CommonCodeEntryCommonAction {

	protected ICommand[] getCommandChain(String event) {
		DefaultLogger.debug(this, "- event : " + event);

		ICommand[] cmdArray = null;

		if (CommonCodeEntryConstant.LIST_PARAMS.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new ListEditableCCCommand();
		}
		else if ("read".equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new ReadCCEntryListCommand();
			cmdArray[1] = new PrepareViewCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.EDIT_SELECTED_PARAM.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new ReadCCEntryListCommand();
			cmdArray[1] = new PrepareEditCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.UPDATE_SELECTED_PARAM.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new SaveCCEntryList2SessionCommand();
			cmdArray[1] = new SubmitCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.ADD_PARAM.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new SaveCCEntryList2SessionCommand();
		}
		else if (CommonCodeEntryConstant.ADD_PARAM_RETURN.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new PrepareEditCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE.equals(event)
				|| CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE.equals(event)) {
			cmdArray = new ICommand[3];
			cmdArray[0] = new SaveCCEntryList2SessionCommand();
			cmdArray[1] = new PaginateCCEntryListCommand();
			cmdArray[2] = new PrepareEditCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.UPDATE_SELECTED_PARAM_ERROR.equals(event)
				|| CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE_ERROR.equals(event)
				|| CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE_ERROR.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new PrepareEditCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.CHECKER_PROCESS_PAGEINATE.equals(event)
				|| CommonCodeEntryConstant.PREPARE_CLOSE_PARAM_PAGEINATE.equals(event)
				|| CommonCodeEntryConstant.CHECKER_TO_TRACK_PAGEINATE.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new PaginateCCEntryListCommand();
			cmdArray[1] = new PrepareViewCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.CHECKER_PROCESS.equals(event)) {
			cmdArray = new ICommand[3];
			cmdArray[0] = new ReadCCEntryListByTrxIdCommand();
			cmdArray[1] = new PrepareProcessCCEntryListCommand();
			cmdArray[2] = new PrepareViewCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.EDIT_REJECTED_PARAM.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new ReadCCEntryListByTrxIdCommand();
			cmdArray[1] = new PrepareEditCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.PREPARE_CLOSE_PARAM.equals(event)
				|| CommonCodeEntryConstant.CHECKER_TO_TRACK.equals(event)) {
			cmdArray = new ICommand[2];
			cmdArray[0] = new ReadCCEntryListByTrxIdCommand();
			cmdArray[1] = new PrepareViewCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.CHECKER_APPROVE.equals(event)) {
			cmdArray = new ICommand[] { new ApproveCCEntryListCommand(),
					(ICommand) BeanHouse.get("CollateralAction.ReloadAssetValuationProfileSingletonCommand") };
		}
		else if (CommonCodeEntryConstant.CHECKER_REJECT.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new RejectCCEntryListCommand();
		}
		else if (CommonCodeEntryConstant.CLOSE_PARAM.equals(event)) {
			cmdArray = new ICommand[1];
			cmdArray[0] = new CloseCCEntryListCommand();
		}
		return cmdArray;
	}

	protected boolean isValidationRequired(String event) {
		boolean res=false;
		if(event.equals(CommonCodeEntryConstant.UPDATE_SELECTED_PARAM)
			||event.equals("maker_edit_common_code_params_edit")
			||event.equals("read")	
				){
			res=true;
		}
		 // CommonCodeEntryConstant.UPDATE_REJECTED_PARAM.equals ( event
		// ) ||
				//||
				// CommonCodeEntryConstant.EDIT_SELECTED_PARAM.equals ( event )
				// ||
				//CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE.equals(event)
				//|| CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE.equals(event);
		return res;
	}

	protected ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MaintainCCEntryListValidator.validateForm((MaintainCCEntryListForm) aForm);
	}

	protected String getErrorEvent(String event) {

		DefaultLogger.debug(this, "-error event : " + event);
		String retValue = CommonCodeEntryConstant.LIST_PARAMS;

		if (CommonCodeEntryConstant.UPDATE_SELECTED_PARAM.equals(event)
				|| CommonCodeEntryConstant.ADD_PARAM.equals(event)) {
			retValue = CommonCodeEntryConstant.UPDATE_SELECTED_PARAM_ERROR;
		}
		else if (CommonCodeEntryConstant.UPDATE_REJECTED_PARAM.equals(event)) {
			retValue = CommonCodeEntryConstant.EDIT_REJECTED_PARAM;
		}
		else if (CommonCodeEntryConstant.EDIT_SELECTED_PARAM.equals(event)) {
			retValue = CommonCodeEntryConstant.EDIT_SELECTED_PARAM_ERROR;
		}
		else if ("read".equals(event)) {
			retValue = "read_error";
		}
		else if (CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE.equals(event)) {
			retValue = CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE_ERROR;
		}
		else if (CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE.equals(event)) {
			retValue = CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE_ERROR;
		}
		return retValue;
	}

	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page page = new Page();

		if (exceptionMap.get(CommonCodeEntryConstant.WORK_IN_PROGRESS) != null) {
			page.setPageReference(CommonCodeEntryConstant.WORK_IN_PROGRESS);
		}
		else if (CommonCodeEntryConstant.UPDATE_SELECTED_PARAM_ERROR.equals(event)
				||CommonCodeEntryConstant.EDIT_SELECTED_PARAM_ERROR.equals(event)
				|| CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE.equals(event)
				|| CommonCodeEntryConstant.EDIT_SELECTED_PARAM_PAGEINATE_ERROR.equals(event)
				|| CommonCodeEntryConstant.ADD_PARAM_RETURN.equals(event)) {
			page.setPageReference(CommonCodeEntryConstant.EDIT_SELECTED_PARAM);
		}
		else if (CommonCodeEntryConstant.CHECKER_PROCESS_PAGEINATE.equals(event)) {
			page.setPageReference(CommonCodeEntryConstant.CHECKER_PROCESS);
		}
		else if (CommonCodeEntryConstant.PREPARE_CLOSE_PARAM_PAGEINATE.equals(event)) {
			page.setPageReference(CommonCodeEntryConstant.PREPARE_CLOSE_PARAM);
		}
		else if (CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE.equals(event)
				|| CommonCodeEntryConstant.EDIT_REJECTED_PARAM_PAGEINATE_ERROR.equals(event)) {
			page.setPageReference(CommonCodeEntryConstant.EDIT_REJECTED_PARAM);
		}
		else if ("read".equals(event) || CommonCodeEntryConstant.CHECKER_TO_TRACK_PAGEINATE.equals(event)) {
			page.setPageReference(CommonCodeEntryConstant.CHECKER_TO_TRACK);
		}
		else if ("read_error".equals(event)) {
		page.setPageReference("maker_edit_common_code_params_prepare_close");
		}
		else if(CommonCodeEntryConstant.EDIT_SELECTED_PARAM.equals(event)) {
			String isViewOnlyCommonCode = (String) resultMap.get("isViewOnlyCommonCode");
			if(ICMSConstant.YES.equals(isViewOnlyCommonCode))
				page.setPageReference(CommonCodeEntryConstant.CHECKER_TO_TRACK);
			else
				page.setPageReference(event);
		}
		else {
			page.setPageReference(event);
		}

		return page;
	}

}