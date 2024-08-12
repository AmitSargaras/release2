/*
 * Created on 2007-2-21
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.ui.manualinput.limit;

import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.manualinput.line.covenant.BackToManualInputLimitCommand;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class LmtXRefAction extends LmtDetailAction {
	protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		aPage.setPageReference(getReference(event, resultMap, exceptionMap));
		return aPage;
	}

	private String getReference(String event, HashMap resultMap, HashMap exceptionMap) {
		String fromEvent = (String) (resultMap.get("fromEvent"));
		if (EventConstant.EVENT_READ.equals(event)) {
			return "read_xref";
		}
		else if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE.equals(event)
				|| EventConstant.EVENT_ADD_CO_BORROWER_1.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER_1.equals(event) 
				
				) {
			return "update_xref";
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(event)) {
			return "update_xref_rejected";
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event) 
				|| EventConstant.EVENT_CREATE_UBS.equals(event) || EventConstant.EVENT_SUBMIT_UBS.equals(event)
				|| EventConstant.EVENT_CREATE_TS.equals(event) || EventConstant.EVENT_SUBMIT_TS.equals(event)
				|| EventConstant.EVENT_CANCEL.equals(event)) {
			return "update_return";
		}
		else if (EventConstant.EVENT_CANCEL_REJECTED.equals(event)
				|| EventConstant.EVENT_SUBMIT_UBS_REJECTED.equals(event)
				|| EventConstant.EVENT_SUBMIT_REJECTED.equals(event)) {
			return "update_return_rejected";
		}
		else if (EventConstant.EVENT_READ_RETURN.equals(event)) {
			return fromEvent + "_return";
		}
		else if (EventConstant.EVENT_REFRESH_HOSTSYSCOUNTRY.equals(event)) {
			return "refresh_country";
		}
		else if (EventConstant.EVENT_PREPARE_CREATE_UBS.equals(event)
				|| EventConstant.EVENT_ADD_CO_BORROWER.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER.equals(event)
				|| EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(event) || EventConstant.EVENT_ADD_CO_BORROWER_CREATE.equals(event)
				||  EventConstant.EVENT_SUBMIT_UBS_ERROR.equals(event) || EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event)) {
			return "update_xref_ubs";
		}else if (EventConstant.EVENT_PREPARE_CREATE_TS.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_TS.equals(event)
				||  EventConstant.EVENT_SUBMIT_TS_ERROR.equals(event)) {
			return "update_xref_ts";
		}else if (EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED.equals(event)) {
			return "update_xref_ubs_rejected";
		}else if (EventConstant.EVENT_READ_UBS.equals(event)) {
			return "read_xref_ubs";
		}else if (EventConstant.EVENT_READ_TS.equals(event)) {
			return "read_xref_ts";
		}else if (EventConstant.EVENT_READ_UBS_REJECTED.equals(event)) {
			return "read_xref_ubs_rejected";
		}else if (EventConstant.EVENT_CLOSE_UBS.equals(event)) {
			return "update_return";
		}else if (EventConstant.EVENT_REOPEN_UBS.equals(event)) {
			return "update_return";
		}else if (EventConstant.EVENT_CLOSE_UBS_REJECTED.equals(event)) {
			return "update_return_rejected";
		}else if (EventConstant.EVENT_REOPEN_UBS_REJECTED.equals(event)) {
			return "update_return_rejected";
		}else if (EventConstant.EVENT_PREPARE_CLOSE_UBS.equals(event) || (EventConstant.EVENT_CLOSE_UDF.equals(event))
				||(EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS.equals(event))) {
			return "close_xref_ubs";
		}else if (EventConstant.EVENT_PREPARE_REOPEN_UBS.equals(event) || (EventConstant.EVENT_REOPEN_UDF.equals(event))
				||(EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS.equals(event))) {
			return "reopen_xref_ubs";
		}else if (EventConstant.EVENT_PREPARE_CLOSE_UBS_REJECTED.equals(event) || (EventConstant.EVENT_CLOSE_UDF_REJECTED.equals(event))
				||(EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED.equals(event))) {
			return "close_xref_ubs_rejected";
		}else if (EventConstant.EVENT_PREPARE_REOPEN_UBS_REJECTED.equals(event) || (EventConstant.EVENT_REOPEN_UDF_REJECTED.equals(event))
				||(EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED.equals(event))) {
			return "reopen_xref_ubs_rejected";
		}
		//added by santosh UBS LIMIT UPLOAD
		else if ((EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event))
						|| (EventConstant.EVENT_EDIT_UDF.equals(event))) {
				return "update_sub_acnt";
		}
		else if ((EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(event))
				|| (EventConstant.EVENT_EDIT_UDF_REJECTED.equals(event))) {
		return "update_sub_acnt_rejected";
}
		else if ((EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(event))||EventConstant.EVENT_CREATE_UDF.equals(event)) {
			return "update_sub_acnt";
		}
		else if ((EventConstant.EVENT_READ_RELEASED_LINE_DETAILS.equals(event))
				|| (EventConstant.EVENT_READ_UDF.equals(event))) {
		return "read_sub_acnt";
		}else if ((EventConstant.EVENT_READ_RELEASED_LINE_DETAILS_REJECTED.equals(event))
				|| (EventConstant.EVENT_READ_UDF_REJECTED.equals(event))) {
		return "read_sub_acnt_rejected";
		}		
		else if (EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(event) || (EventConstant.EVENT_UPDATE_STATUS_UDF.equals(event))
				||(EventConstant.EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS.equals(event)) || (EventConstant.EVENT_UPDATE_STATUS_UBS_ERROR.equals(event))) {
			return "updateStatus_xref_ubs";
		}else if (EventConstant.EVENT_UPDATE_STATUS_UBS.equals(event)) {
			return "update_return";
		}else if( EventConstant.FETCH_UTILIZEDAMT.equals(event)){
			return "fetch_utilizedAmt";
		}else if( EventConstant.FETCH_LIABBRANCH.equals(event)){
			return "fetch_LiabBranch";
		}else if( EventConstant.FETCH_COBORROWER_NAME_FCUBS.equals(event)){
			return "fetch_CoBorrowerName_FCUBS";
		}else if( EventConstant.FETCH_COBORROWER_NAME_BH.equals(event)){
			return "fetch_CoBorrowerName_BH";
		}else if( EventConstant.FETCH_COBORROWER_NAME_GC.equals(event)){
			return "fetch_CoBorrowerName_GC";
		}else if( EventConstant.FETCH_COBORROWER_NAME_HK.equals(event)){
			return "fetch_CoBorrowerName_HK";
		}else if(EventConstant.EVENT_PREPARE_UPDATE_STATUS_TS.equals(event)) {
			return "updateStatus_xref_ts";
				
		}else if (EventConstant.EVENT_CREATE_COVENANT_LINE.equals(event)) {
			return "create_covenant_detail_line";
		}else if (EventConstant.EVENT_VIEW_COVENANT_LINE.equals(event)) {
		return "view_covenant_detail_line";				
		
		}	
	
		return event;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EventConstant.EVENT_CREATE.equals(event)) {
			return EventConstant.EVENT_PREPARE_CREATE;
		}
		else if (EventConstant.EVENT_SUBMIT.equals(event)) {
			return EventConstant.EVENT_PREPARE_UPDATE;
		}else if (EventConstant.EVENT_SUBMIT_REJECTED.equals(event)) {
			return EventConstant.EVENT_PREPARE_UPDATE_REJECTED;
		}else if (EventConstant.EVENT_CREATE_UBS.equals(event)) {
			return EventConstant.EVENT_PREPARE_CREATE_UBS;
		}else if (EventConstant.EVENT_CREATE_TS.equals(event)) {
			return EventConstant.EVENT_PREPARE_CREATE_TS;
		}else if (EventConstant.EVENT_SUBMIT_TS.equals(event)) {
			return EventConstant.EVENT_SUBMIT_TS_ERROR;
		}else if (EventConstant.EVENT_SUBMIT_UBS.equals(event)) {
			return EventConstant.EVENT_SUBMIT_UBS_ERROR;
		}else if (EventConstant.EVENT_SUBMIT_UBS_REJECTED.equals(event)) {
			return EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED;
		}else if (EventConstant.EVENT_UPDATE_STATUS_UBS.equals(event)) {
			return EventConstant.EVENT_UPDATE_STATUS_UBS_ERROR;
		}
		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)
				|| EventConstant.EVENT_SUBMIT_REJECTED.equals(event)
				|| EventConstant.EVENT_CREATE_UBS.equals(event) || EventConstant.EVENT_SUBMIT_UBS.equals(event)
				|| EventConstant.EVENT_SUBMIT_UBS_REJECTED.equals(event)/*|| 
			EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event) ||EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(event)*/				
				|| EventConstant.EVENT_UPDATE_STATUS_UBS.equals(event)
				|| EventConstant.EVENT_CREATE_TS.equals(event) || EventConstant.EVENT_SUBMIT_TS.equals(event)
				|| EventConstant.EVENT_SUBMIT_TS_REJECTED.equals(event) 
				|| EventConstant.EVENT_UPDATE_STATUS_TS.equals(event)) {
			return true;
		}
		return false;
	}

	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return MILimitValidator.validateXRef(aForm, locale);
	}

	protected ICommand[] getCommandChain(String event) {
		ICommand[] objArray = null;
		// TODO Auto-generated method stub
		if (EventConstant.EVENT_PREPARE_CREATE.equals(event) || EventConstant.EVENT_PREPARE_CREATE_UBS.equals(event)  
			//	|| EventConstant.EVENT_ADD_CO_BORROWER.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER.equals(event)  
				|| EventConstant.EVENT_PREPARE_CREATE_TS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareXRefDetailCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_UBS.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_TS.equals(event)|| 
				EventConstant.EVENT_RETURN_FROM_LINE_COVENANT.equals(event) ) {
			objArray = new ICommand[2];
			objArray[0] = new ReadXRefDetailCmd();
			objArray[1] = new PrepareXRefDetailCmd();
		//	objArray[2] = new SaveCoBorrowerXRefDetailCmd();
		}
		else if (EventConstant.EVENT_PREPARE_UPDATE_REJECTED.equals(event) || EventConstant.EVENT_PREPARE_UPDATE_UBS_REJECTED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadXRefDetailCmd();
			objArray[1] = new PrepareXRefDetailCmd();
		}
		//Santosh ubs limit
		else if (EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS.equals(event) ||EventConstant.EVENT_EDIT_UDF.equals(event) 
				||EventConstant.EVENT_EDIT_RELEASED_LINE_DETAILS_REJECTED.equals(event) 
				||EventConstant.EVENT_EDIT_UDF_REJECTED.equals(event)
				||EventConstant.EVENT_CREATE_RELEASED_LINE_DETAILS.equals(event)||EventConstant.EVENT_CREATE_UDF.equals(event)) {
			objArray = new ICommand[1];
			//objArray[0] = new ReadXRefDetailCmd();
			objArray[0] = new PrepareXRefDetailCmd();
		//	objArray[1] = new SaveCoBorrowerXRefDetailCmd();
		}
		//end santosh
		else if (EventConstant.EVENT_READ.equals(event) || EventConstant.EVENT_READ_UBS.equals(event)|| EventConstant.EVENT_READ_TS.equals(event)|| 
				EventConstant.EVENT_READ_UBS_REJECTED.equals(event)|| 
				EventConstant.EVENT_READ_UDF.equals(event)|| EventConstant.EVENT_READ_RELEASED_LINE_DETAILS.equals(event)
				||EventConstant.EVENT_READ_UDF_REJECTED.equals(event)|| EventConstant.EVENT_READ_RELEASED_LINE_DETAILS_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadXRefDetailCmd();
		//	objArray[1] = new SaveCoBorrowerXRefDetailCmd();
		}
		else if (EventConstant.EVENT_CREATE.equals(event) || EventConstant.EVENT_SUBMIT.equals(event)
				|| EventConstant.EVENT_SUBMIT_REJECTED.equals(event)
				|| EventConstant.EVENT_CREATE_UBS.equals(event) || EventConstant.EVENT_SUBMIT_UBS.equals(event)			
			//	|| EventConstant.EVENT_ADD_CO_BORROWER.equals(event) || EventConstant.EVENT_DELETE_CO_BORROWER.equals(event)

				|| EventConstant.EVENT_SUBMIT_UBS_REJECTED.equals(event)
				|| EventConstant.EVENT_CLOSE_UBS.equals(event) || EventConstant.EVENT_REOPEN_UBS.equals(event)
				|| EventConstant.EVENT_UPDATE_STATUS_UBS.equals(event)
				|| EventConstant.EVENT_CREATE_TS.equals(event) || EventConstant.EVENT_SUBMIT_TS.equals(event)
				|| EventConstant.EVENT_SUBMIT_TS_REJECTED.equals(event)
				|| EventConstant.EVENT_CLOSE_TS.equals(event) || EventConstant.EVENT_REOPEN_TS.equals(event)
				|| EventConstant.EVENT_UPDATE_STATUS_TS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveXRefDetailCmd();
		}else if ( EventConstant.EVENT_CLOSE_UBS_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_UBS_REJECTED.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveXRefDetailCmd();
		}
		else if (EventConstant.EVENT_CANCEL.equals(event) ||EventConstant.EVENT_CANCEL_REJECTED.equals(event)|| EventConstant.EVENT_READ_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BackToManualInputLimitCommand();
		}
		else if (EventConstant.EVENT_REFRESH_HOSTSYSCOUNTRY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshHostSysCountryCmd();
		}
		else if ( EventConstant.EVENT_PREPARE_CLOSE_UBS.equals(event) || EventConstant.EVENT_PREPARE_REOPEN_UBS.equals(event)
				||EventConstant.EVENT_PREPARE_CLOSE_UBS_REJECTED.equals(event) || EventConstant.EVENT_PREPARE_REOPEN_UBS_REJECTED.equals(event)
				|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_TS.equals(event)
				|| EventConstant.EVENT_PREPARE_UPDATE_STATUS_UBS.equals(event) ||  EventConstant.EVENT_UPDATE_STATUS_UBS_ERROR.equals(event)){
			objArray = new ICommand[2];
			objArray[0] = new ReadXRefDetailCmd();
			objArray[1] = new PrepareXRefDetailCmd();
		}else if ( EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS.equals(event)
				||EventConstant.EVENT_CLOSE_RELEASED_LINE_DETAILS_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_RELEASED_LINE_DETAILS_REJECTED.equals(event)
				){
			objArray = new ICommand[2];
			objArray[0] = new ReadXRefDetailCmd();
			objArray[1] = new PrepareXRefDetailCmd();
		}else if (EventConstant.EVENT_CLOSE_UDF.equals(event) || EventConstant.EVENT_REOPEN_UDF.equals(event)
				||EventConstant.EVENT_CLOSE_UDF_REJECTED.equals(event) || EventConstant.EVENT_REOPEN_UDF_REJECTED.equals(event)
				
				|| EventConstant.EVENT_UPDATE_STATUS_UDF.equals(event) ||EventConstant.EVENT_UPDATE_STATUS_RELEASED_LINE_DETAILS.equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new PrepareXRefDetailCmd();
			
		}else if (EventConstant.FETCH_UTILIZEDAMT.equals(event)){
			
				objArray = new ICommand[1];
			//	objArray[0] = new PrepareXRefDetailCmd();
				objArray[0] = new FetchUtilizedAmtCmd();
				
		}else if (EventConstant.FETCH_LIABBRANCH.equals(event)){
			
			objArray = new ICommand[1];
		
			objArray[0] = new FetchLiabBranchCmd();
			
	
		}else if (EventConstant.EVENT_VIEW_COVENANT_LINE.equals(event)||EventConstant.EVENT_CREATE_COVENANT_LINE.equals(event)){
					objArray = new ICommand[1];
					objArray[0] = new SaveXRefDetailCmd();	
			
	}else if (EventConstant.EVENT_ADD_CO_BORROWER.equals(event) || EventConstant.EVENT_ADD_CO_BORROWER_CREATE.equals(event)) {
		objArray = new ICommand[1];
		//objArray[0] = new SaveCoBorrowerXRefDetailCmd();
		objArray[0] = new PrepareXRefDetailCmd();

	}
	else if (EventConstant.EVENT_ADD_CO_BORROWER_1.equals(event) ) {
		objArray = new ICommand[1];
		objArray[0] = new SaveCoBorrowerXRefDetailCmd();
		//objArray[0] = new PrepareXRefDetailCmd();

	}
	else if ( EventConstant.EVENT_DELETE_CO_BORROWER.equals(event) 
				|| EventConstant.EVENT_DELETE_CO_BORROWER_1.equals(event) 
				) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveCoBorrowerXRefDetailCmd();
		}
	else if (EventConstant.FETCH_COBORROWER_NAME_FCUBS.equals(event)){
		
		objArray = new ICommand[1];
	//	objArray[0] = new PrepareXRefDetailCmd();
		objArray[0] = new FetchCoBorrowerName_FCUBSCmd();
		
}
		
		
		
	
return objArray;
	}
}
