/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/CollateralAction.java,v 1.23 2006/05/03 08:50:36 hmbao Exp $
 */

package com.integrosys.cms.ui.collateral;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.valuationfromlos.ReadValuationFromLOSCommand;
import com.integrosys.cms.ui.user.RefreshBranchDetailsCmd;
import com.integrosys.cms.ui.collateral.CloseInsuranceCommand;
import com.integrosys.cms.ui.collateral.guarantees.gtebanksame.SaveBankGuaranteeToSessionCommand;
import com.integrosys.cms.ui.collateral.guarantees.gteslcsame.SaveGuaranteeToSessionCommand;

/**
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2006/05/03 08:50:36 $ Tag: $Name: $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 2:49:22 PM
 * To change this template use Options | File Templates.
 */

public abstract class CollateralAction extends CommonAction {
	


	

	private Map nameCommandMap;

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}
	
	
	
	public static final String EVENT_PREPARE_CREATE = "prepare_create";

	public static final String EVENT_PREPARE_UPDATE = "prepare_update";


	

	public static final String EVENT_PREPARE_FORM = "prepare_form";

	public static final String EVENT_SUBMIT = "submit";

	public static final String EVENT_PROCESS = "process";
	
	public static final String EVENT_PREPARE ="prepare";

	public static final String EVENT_PREPARE_STOCK_DETAILS_ERROR ="prepare_stock_details_errors";
	
	public static final String EVENT_UPDATE_RETURN = "update_return";

	public static final String EVENT_READ_RETURN = "read_return";

	public static final String EVENT_PROCESS_RETURN = "process_return";

	public static final String EVENT_PROCESS_UPDATE = "process_update";

	public static final String EVENT_PREPARE_CLOSE = "prepare_close";

	public static final String EVENT_CLOSE_RETURN = "close_return";

	public static final String EVENT_CLOSE = "close";

	public static final String EVENT_TRACK = "track";

	public static final String EVENT_TRACK_RETURN = "track_return";

	public static final String EVENT_DELETE_ITEM = "itemDelete";

	public static final String EVENT_PRINT_REMINDER = "print_reminder";

	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_PREPARE_ADD_PLEDGE = "add_pledge_prepare";

	public static final String EVENT_EDIT_PLEDGE_PREPARE = "edit_pledge_prepare";

	public static final String EVENT_DELETE_PLEDGE = "remove_pledge";

	public static final String EVENT_DELETE_PLEDGOR = "remove_pledgor";

	public static final String EVENT_SEARCH_ACCOUNT = "search_account";

	public static final String COLLATERALLOADING = "collateral_loading";

	public static final String READ_SECURITIES = "read_securities";

	public static final String READ_SECURITIES_2 = "read_securities_2";

	public static final String NO_COLLATERAL = "no_collateral";

	public static final String EVENT_CREATE_PLEDGE = "create_pledge";

	public static final String EVENT_UPDATE_PLEDGE = "update_pledge";

	public static final String EVENT_PREPARE_DELETE = "prepare_delete";

	public static final String EVENT_SEARCH_PLEDGOR = "search_pledgor";

	public static final String EVENT_PREPARE_SEARCH = "search_pledgor_prepare";

	public static final String EVENT_EDIT_PLEDGOR_PREPARE = "edit_pledgor_prepare";

	public static final String EVENT_EDIT_PLEDGOR = "edit_pledgor";

	public static final String EVENT_SAVE_PLEDGOR = "save_pledgor";
	
	
	
	public static final String VIEW_CHEQUE_DTL_PAGE = "viewChequeDetails";
	public static final String BANK_CODE_POPULATION = "bank_code_population";
	
	//****************************edit************************************************
	//public static final String VIEW_CHEQUE_EDIT = "view_chequeDetail";
	
    public static final String EDIT_CHEQUE_DTL_PAGE = "edit_chequeDetail";
	
	//public static final String DELETE_CHEQUE_DTL_PAGE = "delete_chequeDetail";
	
	public static final String EVENT_PREPARE_UPDATE_SUB = "prepare_update_sub";
	
	public static final String VIEW_CHEQUE_DETAIL = "view_chequeDetail";
	
	public static final String DELECT_CHEQUEDETAIL = "delete_chequeDetail";
	
	public static final String CREATE_BANKDETAIL = "create_bankDetail";
	
	public static final String VIEW_LIST = "view_list";
	
	public static final String MAKER_VIEW_LIST = "maker_view_list";
	
	public static final String RETURN_VIEW_LIST = "return_view_list";
	
	public static final String REFRESH_REGION_ID="refresh_region_id";
	
	public static final String REFRESH_CURRENCY="refresh_currency";
	
	public static final String REFRESH_STATE_ID="refresh_state_id";
	
	public static final String REFRESH_CITY_ID="refresh_city_id";
	
	public static final String EVENT_PREPARE_UPDATE_CODE = "prepare_update_code";
	
	public static final String EVENT_PREPARE_UPDATE_SCRIPTCODE = "prepare_update_scriptcode";
	
	public static final String REFRESH_BRANCH_DETAIL="refresh_branch_detail";
	
	public static final String EVENT_VIEW_INSURANCE_HISTORY="view_insurance_history";
	
	public static final String EVENT_SEARCH_INSURANCE_HISTORY="search_insurance_history";
	
	public static final String EVENT_DOWNLOAD_INSURANCE_HISTORY_REPORT="download_insurance_history_report";
	
	public static final String EVENT_SEARCH_INSURANCE_HISTORY_ERROR="search_insurance_history_error";
	
	public static final String MAKER_PREPARE_CREATE_INSURANCE="maker_prepare_create_insurance";
	
	public static final String MAKER_CREATE_INSURANCE="maker_create_insurance";
	
	public static final String MAKER_CANCLE_CREATE_INSURANCE="maker_cancle_create_insurance";
	
	public static final String MAKER_RETURN_FROM_SUBMIT_INSURANCE = "maker_return_from_submit_insurance";
	
	public static final String MAKER_VIEW_INSURANCE="maker_view_insurance";
	
	public static final String CHECKER_VIEW_INSURANCE="checker_view_insurance";
	
	public static final String CHECKER_VIEW_INSURANCE_PROCESS="checker_view_insurance_process";
	
	public static final String MAKER_PREPARE_EDIT_INSURANCE="maker_edit_insurance_read";
	
	public static final String MAKER_PREPARE_DELETE_INSURANCE="maker_delete_insurance_read";
	
	public static final String MAKER_DELETE_LIST_INSURANCE="maker_delete_list_insurance";
	
	public static final String MAKER_EDIT_LIST_INSURANCE="maker_edit_list_insurance";
	
	public static final String MAKER_VIEW_INS_EDIT="maker_view_ins_edit";
	
	public static final String CHECKER_VIEW_INS_READ="checker_view_ins_read";
	
	public static final String MAKER_VIEW_INS_CLOSE="maker_view_ins_close";
	
	public static final String MAKER_VIEW_INS_TRACK="maker_view_ins_track";
	
	public static final String MAKER_EDIT_CANCLE_INSURANCE="maker_edit_cancle_insurance";
	
	public static final String MAKER_RETURN_VIEW_INSURANCE="maker_return_view_insurance";

	 //Uma Khot::Insurance Deferral maintainance
	public static final String MAKER_ADD_INSURANCE="maker_add_insurance";
	public static final String MAKER_CREATE_INSURANCE_PENDING="maker_create_insurance_pending";
	public static final String MAKER_CREATE_INSURANCE_DEFERRED="maker_create_insurance_deferred";
	public static final String MAKER_CREATE_INSURANCE_RECEIVED="maker_create_insurance_received";
	public static final String MAKER_CREATE_INSURANCE_WAIVED="maker_create_insurance_waived";
	public static final String MAKER_CANCLE_SUBMIT_INSURANCE="maker_cancle_submit_insurance";
	
	public static final String MAKER_SUBMIT_INSURANCE_PENDING="maker_submit_insurance_pending";
	public static final String MAKER_SUBMIT_INSURANCE_DEFERRED="maker_submit_insurance_deferred";
	public static final String MAKER_SUBMIT_INSURANCE_RECEIVED="maker_submit_insurance_received";
	public static final String MAKER_SUBMIT_INSURANCE_WAIVED="maker_submit_insurance_waived";
	
	public static final String MAKER_EDIT_INSURANCE_RECEIVED="maker_edit_insurance_received";
/*	public static final String MAKER_EDIT_INSURANCE_PENDING="maker_edit_insurance_pending";
	public static final String MAKER_EDIT_INSURANCE_WAIVED="maker_edit_insurance_waived";
	public static final String MAKER_EDIT_INSURANCE_DEFERRED="maker_edit_insurance_deferred"; */
	
	public static final String MAKER_EDIT_INSRECEIVED_LIST="maker_edit_insreceived_list";
	public static final String MAKER_EDIT_CANCEL_INSSTATUS="maker_edit_cancle_insstatus";
	public static final String MAKER_VIEW_INSURANCE_RECEIVED="maker_view_insurance_received";
	public static final String MAKER_VIEW_INSURANCE_DEFERRED="maker_view_insurance_deferred";
	public static final String MAKER_VIEW_INSURANCE_WAIVED="maker_view_insurance_waived";
	public static final String MAKER_VIEW_INSURANCE_PENDING="maker_view_insurance_pending";
	
	public static final String CHECKER_VIEW_INSURANCE_RECEIVED="checker_view_insurance_received";
	public static final String CHECKER_VIEW_INSURANCE_DEFERRED="checker_view_insurance_deferred";
	public static final String CHECKER_VIEW_INSURANCE_WAIVED="checker_view_insurance_waived";
	public static final String CHECKER_VIEW_INSURANCE_PENDING="checker_view_insurance_pending";
	
	public static final String MAKER_UPDATE_INSURANCE_RECEIVED="maker_update_insurance_received";
	public static final String MAKER_UPDATE_INSURANCE_PENDING="maker_update_insurance_pending";
	public static final String MAKER_UPDATE_INSURANCE_DEFERRED="maker_update_insurance_deferred";
	public static final String MAKER_UPDATE_INSURANCE_WAIVED="maker_update_insurance_waived";
	
	public static final String MAKER_UPDATE_INSRECEIVED_LIST="maker_update_insreceived_list";
	public static final String MAKER_UPDATE_INSPENDING_LIST="maker_update_inspending_list";
	public static final String MAKER_UPDATE_INSWAIVED_LIST="maker_update_inswaived_list";
	public static final String MAKER_UPDATE_INSDEFERRED_LIST="maker_update_insdeferred_list";
	
	public static final String MAKER_SUBMIT_INS_DEFERRED_ERROR="maker_submit_ins_deferred_error";
	public static final String MAKER_SUBMIT_INS_WAIVED_ERROR="maker_submit_ins_waived_error";
	public static final String MAKER_SUBMIT_INS_RECEIVED_ERROR="maker_submit_ins_received_error";
	public static final String MAKER_SUBMIT_INS_PENDING_ERROR="maker_submit_ins_pending_error";
	public static final String MAKER_EDIT_INSRECEIVED_LIST_ERROR="maker_edit_insreceived_list_error";
	
	public static final String MAKER_UPDATE_INSDEFERRED_LIST_ERROR="maker_update_insdeferred_list_error";
	public static final String MAKER_UPDATE_INSPENDING_LIST_ERROR="maker_update_inspending_list_error";
	public static final String MAKER_UPDATE_INSWAIVED_LIST_ERROR="maker_update_inswaived_list_error";
	public static final String MAKER_UPDATE_INSRECEIVED_LIST_ERROR="maker_update_insreceived_list_error";
	
	
	public static final String MAKER_ADD_ADD_DOC_FAC_DET="maker_add_addfacdocdet";
	public static final String MAKER_ADD_ADD_DOC_FAC_DET_SUBMIT="maker_create_add_fac_doc_det_submit";
	public static final String MAKER_EDIT_ADD_DOC_FAC_DET="maker_edit_add_fac_doc_det";
	public static final String MAKER_UPDATE_ADD_DOC_FAC_DET="maker_update_add_fac_doc_det";
	public static final String MAKER_EDIT_ADD_DOC_FAC_DET_LIST="maker_edit_add_fac_doc_det_list";
	public static final String MAKER_UPDATE_ADD_DOC_FAC_DET_LIST="maker_update_add_fac_doc_det_list";
	
	public static final String REFRESH_REGION_ID_V2="refresh_region_id_v2";
	public static final String REFRESH_STATE_ID_V2="refresh_state_id_v2";
	public static final String REFRESH_CITY_ID_V2="refresh_city_id_v2";
	
	public static final String REFRESH_REGION_ID_V3="refresh_region_id_v3";
	public static final String REFRESH_STATE_ID_V3="refresh_state_id_v3";
	public static final String REFRESH_CITY_ID_V3="refresh_city_id_v3";
	
		/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {

		ICommand objArray[] = null;
		if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ValidateStpCollateralCommand("HP", "AB102");
			objArray[1] = new ApproveCollateralCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ApproveInsuranceCommand();
		}
		else if (EVENT_REJECT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new RejectCollateralCommand();
			objArray[1] = new RejectInsuranceCommand();
		}
		else if (EVENT_UPDATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new UpdateCollateralCommand();
			objArray[1] = new SubmitInsuranceGCCommand();
		}
		else if (EVENT_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DeleteCollateralCommand();
		}
		else if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)
				|| EVENT_PREPARE_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadCollateralCommand();
		}
		else if (EVENT_SUBMIT.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new SubmitCollateralCommand();
			objArray[1] = new ActualListCmd();
			objArray[2] = new SubmitInsuranceGCCommand();
		}
		else if (EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ProcessCollateralCommand();
		}
		else if (EVENT_CLOSE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new CloseCollateralCommand();
			objArray[1] = new CloseInsuranceCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnCollateralCommand();
		}
		else if ("update_read_valuation_from_los".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ProcessingCollateralCommand();
			objArray[1] = new ReadValuationFromLOSCommand();
		}
		else if ("view_valuation_from_los".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadValuationFromLOSCommand();
		}
		else if ("fd_list".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashFdListCommand();
		}
		else if ("fill_FD".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new CashFillFdCommand();
		}
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB) 
				|| event.endsWith(EVENT_PREPARE)||VIEW_CHEQUE_DETAIL.equals(event)||VIEW_LIST.equals(event)||MAKER_VIEW_LIST.equals(event)
				||DELECT_CHEQUEDETAIL.equals(event)||CREATE_BANKDETAIL.equals(event) ||event.endsWith(EVENT_PREPARE_UPDATE_CODE)) {
			objArray = new ICommand[2];			
			objArray[0] = new ProcessingCollateralCommand();
			objArray[1] = new ActualListCmd();
		}
		
		else if (event.endsWith(VIEW_CHEQUE_DTL_PAGE)) {
			objArray = new ICommand[1];
		
			objArray[0] = (ICommand) getNameCommandMap().get("ProcessingViewChequeDetailCmd");
		}
		
		else if (event.endsWith(BANK_CODE_POPULATION) ) {
			objArray = new ICommand[1];
		
			objArray[0] = (ICommand) getNameCommandMap().get("BankCodePopulationCmd");
		}
		
		/*else if (event.endsWith(VIEW_CHEQUE_EDIT)||event.endsWith(DELETE_CHEQUE_DTL_PAGE) ) {
			objArray = new ICommand[1];
		
			objArray[0] = (ICommand) getNameCommandMap().get("ChequeViewDeleteCmd");
		}*/
		else if (event.endsWith(EDIT_CHEQUE_DTL_PAGE) ) {
			objArray = new ICommand[1];
		
			objArray[0] = (ICommand) getNameCommandMap().get("ChequeEditCmd");
		}
		else if (event.endsWith(CREATE_BANKDETAIL) ) {
			objArray = new ICommand[1];
		
			objArray[0] = (ICommand) getNameCommandMap().get("CreateBankDetails");
		}
		
		
		else if (RETURN_VIEW_LIST.equals(event)) {
			
			    objArray = new ICommand[1];
				objArray[0] = (ICommand) getNameCommandMap().get("ViewchequeCmd");
		}
			else if (event.equals(REFRESH_REGION_ID) || event.equals(REFRESH_REGION_ID_V2)
					|| event.equals(REFRESH_REGION_ID_V3)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshRegionCmd");

		}else if (event.equals(REFRESH_STATE_ID) || event.equals(REFRESH_STATE_ID_V2)
				|| event.equals(REFRESH_STATE_ID_V3)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshStateCmd");

		}
		else if (event.equals(REFRESH_CURRENCY)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshCurrency();

		}
		else if (event.equals(REFRESH_BRANCH_DETAIL)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshBranchDetailsCmd();

		}else if (event.equals(REFRESH_CITY_ID) || event.equals(REFRESH_CITY_ID_V2) 
				|| event.equals(REFRESH_CITY_ID_V3)) {
			objArray = new ICommand[1];
			objArray[0] = (ICommand) getNameCommandMap().get("RefreshCityCmd");

		}else if (event.equals(MAKER_PREPARE_CREATE_INSURANCE) || event.equals("maker_create_insurance_error")) {
			objArray = new ICommand[4];
			objArray[0] = new ListComponentCmd();
			objArray[1] = new ListInsuranceCompanyCmd();
			objArray[2] = new ListInsuranceCurrencyCmd();
			objArray[3] = new MakerPreparCreateInsuranceCmd();

		}else if (event.equals(MAKER_CREATE_INSURANCE_PENDING) || event.equals("maker_submit_ins_pending_error")){
			objArray = new ICommand[1];
			objArray[0] = new MakerPreparCreateInsuranceCmd();

		}else if (event.equals(MAKER_CREATE_INSURANCE_DEFERRED) || event.equals("maker_submit_ins_deferred_error")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerPreparCreateInsuranceCmd();

		}else if (event.equals(MAKER_CREATE_INSURANCE_RECEIVED) || event.equals("maker_submit_ins_received_error")) {
			objArray = new ICommand[3];
			objArray[0] = new ListInsuranceCompanyCmd();
			objArray[1] = new ListInsuranceCurrencyCmd();
			objArray[2] = new MakerPreparCreateInsuranceCmd();

		}else if (event.equals(MAKER_CREATE_INSURANCE_WAIVED) || event.equals("maker_submit_ins_waived_error")) {
			objArray = new ICommand[1];
			objArray[0] = new MakerPreparCreateInsuranceCmd();

		}
		
		 //Uma Khot::Insurance Deferral maintainance
		else if(event.equals(MAKER_ADD_INSURANCE) || event.equals(MAKER_EDIT_INSURANCE_RECEIVED) ||
				MAKER_UPDATE_INSURANCE_RECEIVED.equals(event) || MAKER_UPDATE_INSURANCE_PENDING.equals(event) ||
				MAKER_UPDATE_INSURANCE_DEFERRED.equals(event) || MAKER_UPDATE_INSURANCE_WAIVED.equals(event) ||
				MAKER_VIEW_INSURANCE_RECEIVED.equals(event) || MAKER_VIEW_INSURANCE_DEFERRED.equals(event) ||
				MAKER_VIEW_INSURANCE_WAIVED.equals(event) || MAKER_VIEW_INSURANCE_PENDING.equals(event)
				){
		objArray = new ICommand[2];			
		objArray[0] = new ProcessingCollateralCommand();
		objArray[1] = new ActualListCmd();
		}
		
		else if(event.equals(MAKER_ADD_ADD_DOC_FAC_DET)){
			objArray = new ICommand[2];			
			objArray[0] = new ProcessingCollateralCommand();
			objArray[1] = new ActualListCmd();
			}

		else if(event.equals("saveGTtoSessionForLC")){
			objArray = new ICommand[1];	
			objArray[0] = new SaveGuaranteeToSessionCommand();
			}
		else if(event.equals("saveGTtoSessionForBank")){
			objArray = new ICommand[1];	
			objArray[0] = new SaveBankGuaranteeToSessionCommand();
			}

		DefaultLogger.debug(this, "*******" + event + "================" + objArray);
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
		DefaultLogger.debug(this + " method getNextPage", "~~~~~~~~~~~~~~~~~~ event is: " + event);
		Page aPage = new Page();

		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}
		if (EVENT_DELETE_ITEM.equals(event) || EVENT_DELETE_PLEDGOR.equals(event) || EVENT_DELETE_PLEDGE.equals(event)) {
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}
			else {
				aPage.setPageReference("update_return");
			}
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_UPDATE);

		}
		else if (VIEW_CHEQUE_DTL_PAGE.equals(event)) {
			aPage.setPageReference(VIEW_CHEQUE_DTL_PAGE);

		}
		
		else if (BANK_CODE_POPULATION.equals(event)) {
			aPage.setPageReference(BANK_CODE_POPULATION);

		}
		
		else if (RETURN_VIEW_LIST.equals(event)) {
			aPage.setPageReference("return_view_list");
			}
		else if ((event != null) && event.equals("reject_error")) {
			aPage.setPageReference("reject_editError");
		}

		
		else if (event.endsWith(EVENT_PREPARE_UPDATE_SUB) || event.endsWith(EVENT_PREPARE) ||event.endsWith(EVENT_PREPARE_UPDATE_CODE) || MAKER_ADD_INSURANCE.equals(event)
				|| MAKER_ADD_ADD_DOC_FAC_DET.equals(event)) {
			String itemType = (String) resultMap.get("itemType");
			if (StringUtils.isNotBlank(itemType)) {
				aPage.setPageReference(event + "_" + itemType);
			}
			else {
				aPage.setPageReference(event);
			}
		}/*else if (event.endsWith(EVENT_PREPARE_UPDATE_CODE)) {
			
				aPage.setPageReference(event);
			
		}*/
		else if (event.endsWith(VIEW_CHEQUE_DETAIL)
				||event.endsWith(DELECT_CHEQUEDETAIL)
				||event.endsWith(VIEW_LIST)
				||event.endsWith(MAKER_VIEW_LIST)
				||event.endsWith(CREATE_BANKDETAIL)) {
			String itemType = (String) resultMap.get("itemType");
			if (StringUtils.isNotBlank(itemType)) {
				aPage.setPageReference(event + "_" + itemType);
			}
			else {
				aPage.setPageReference(event);
			}
		}
		
		
		else if (EVENT_PREPARE_DELETE.equals(event)) {
			aPage.setPageReference(EVENT_PREPARE_CLOSE);
		}
		else if (EVENT_PROCESS_UPDATE.equals(event)) {
			ICollateralTrxValue trxValue = (ICollateralTrxValue) resultMap.get("serviceColObj");
			if (ICMSConstant.STATE_PENDING_DELETE.equals(trxValue.getFromState())
					&& ICMSConstant.STATE_REJECTED.equals(trxValue.getStatus())) {
				aPage.setPageReference(EVENT_PREPARE_CLOSE);
			}
			else {
				aPage.setPageReference(event);
			}
		}
		else {
			aPage.setPageReference(event);
		}
		
		if (event.equals("refresh_state_id")) {
			aPage.setPageReference("refresh_state_id");
		} 
		else if (event.equals("refresh_region_id")) {
				aPage.setPageReference("refresh_region_id");
			}
		else if (event.equals("fd_list")) {
			aPage.setPageReference("fd_list");
		}
		else if (event.equals("fill_FD")) {
			aPage.setPageReference("fill_FD");
		}
		else if (event.equals("refresh_city_id")) {
			aPage.setPageReference("refresh_city_id");
		}else if ( "refresh_currency".equals(event)) {
			aPage.setPageReference("refresh_currency");
		}
		else if (event.equals(REFRESH_BRANCH_DETAIL)) {
			aPage.setPageReference("refresh_branch_detail");
		}else if (event.equals(MAKER_PREPARE_CREATE_INSURANCE) || event.equals("maker_create_insurance_error")) {
			aPage.setPageReference("maker_prepare_create_insurance");
		}
		
		 //Uma Khot::Insurance Deferral maintainance
		else if (event.equals("maker_submit_ins_pending_error")) {
			aPage.setPageReference("maker_create_insurance_pending");
		}else if (event.equals("maker_submit_ins_deferred_error")) {
			aPage.setPageReference("maker_create_insurance_deferred");
		}else if (event.equals("maker_submit_ins_received_error")) {
			aPage.setPageReference("maker_create_insurance_received");
		}else if (event.equals("maker_submit_ins_waived_error")) {
			aPage.setPageReference("maker_create_insurance_waived");
		}else if (event.equals("maker_edit_insreceived_list_error")) {
			aPage.setPageReference("maker_edit_insurance_received");
		}else if (event.equals("maker_update_insreceived_list_error")) {
			aPage.setPageReference("maker_update_insurance_received");
		} else if (event.equals("maker_update_inspending_list_error")) {
			aPage.setPageReference("maker_update_insurance_pending");
		} else if (event.equals("maker_update_inswaived_list_error")) {
			aPage.setPageReference("maker_update_insurance_waived");
		} else if (event.equals("maker_update_insdeferred_list_error")) {
			aPage.setPageReference("maker_update_insurance_deferred");
		} else if (event.equals("saveGTtoSessionForLC")) {
			aPage.setPageReference("saveGTtoSessionForLC");
		}else if (event.equals("saveGTtoSessionForBank")) {
			aPage.setPageReference("saveGTtoSessionForBank");
		}

		else if (event.equals("refresh_state_id_v2")) {
			aPage.setPageReference("refresh_state_id_v2");
		} 
		else if (event.equals("refresh_region_id_v2")) {
				aPage.setPageReference("refresh_region_id_v2");
		} else if (event.equals("refresh_city_id_v2")) {
				aPage.setPageReference("refresh_city_id_v2");
		}
		else if (event.equals("refresh_state_id_v3")) {
			aPage.setPageReference("refresh_state_id_v3");
		} 
		else if (event.equals("refresh_region_id_v3")) {
				aPage.setPageReference("refresh_region_id_v3");
		} else if (event.equals("refresh_city_id_v3")) {
				aPage.setPageReference("refresh_city_id_v3");
		}
		else if(MAKER_EDIT_INSURANCE_RECEIVED.equals(event) || MAKER_UPDATE_INSURANCE_RECEIVED.equals(event) || 
				MAKER_UPDATE_INSURANCE_PENDING.equals(event) || MAKER_UPDATE_INSURANCE_DEFERRED.equals(event) ||
				MAKER_UPDATE_INSURANCE_WAIVED.equals(event) || MAKER_VIEW_INSURANCE_RECEIVED.equals(event) || 
				MAKER_VIEW_INSURANCE_DEFERRED.equals(event) || MAKER_VIEW_INSURANCE_WAIVED.equals(event) || 
				MAKER_VIEW_INSURANCE_PENDING.equals(event) ) {
			aPage.setPageReference(event);
		}
		return aPage;
	}

	public String getDefaultEvent() {
		return EVENT_LIST;
	}

	protected String getErrorEvent(String event) {
		String errorEvent = event;
		DefaultLogger.debug(this + "method getErrorEvent", "event is: " + event);
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| EVENT_DELETE_ITEM.equals(event) || event.endsWith(EVENT_PREPARE)|| event.endsWith(EVENT_PREPARE_UPDATE_CODE) || MAKER_ADD_INSURANCE.equals(event) 
				|| MAKER_EDIT_INSURANCE_RECEIVED.equals(event) || MAKER_UPDATE_INSURANCE_PENDING.equals(event) || MAKER_UPDATE_INSURANCE_WAIVED.equals(event)
				|| MAKER_UPDATE_INSURANCE_DEFERRED.equals(event) || MAKER_UPDATE_INSURANCE_RECEIVED.equals(event) || MAKER_VIEW_INSURANCE_RECEIVED.equals(event) 
				|| MAKER_VIEW_INSURANCE_DEFERRED.equals(event) || MAKER_VIEW_INSURANCE_WAIVED.equals(event) || MAKER_VIEW_INSURANCE_PENDING.equals(event)) {
			errorEvent = EVENT_PREPARE_FORM;
		}
		else if (EVENT_APPROVE.equals(event) || EVENT_REJECT.equals(event) || EVENT_DELETE.equals(event)) {
			errorEvent = EVENT_PROCESS_RETURN;
		}
		/* else if (event.equals("reject")) {
				errorEvent = "reject_error";
			}*/

		return errorEvent;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_UPDATE.equals(event) || EVENT_SUBMIT.equals(event) || event.endsWith(EVENT_PREPARE_UPDATE_SUB)
				|| EVENT_DELETE_ITEM.equals(event) || event.endsWith(EVENT_PREPARE) || EVENT_APPROVE.equals(event)
				|| EVENT_REJECT.equals(event)|| EVENT_PREPARE_UPDATE_CODE.equals(event) || MAKER_ADD_INSURANCE.equals(event) 
				|| MAKER_EDIT_INSURANCE_RECEIVED.equals(event) || MAKER_UPDATE_INSURANCE_PENDING.equals(event) || MAKER_UPDATE_INSURANCE_WAIVED.equals(event)
				|| MAKER_UPDATE_INSURANCE_DEFERRED.equals(event) || MAKER_UPDATE_INSURANCE_RECEIVED.equals(event)
				|| MAKER_VIEW_INSURANCE_RECEIVED.equals(event) || MAKER_VIEW_INSURANCE_DEFERRED.equals(event)
				|| MAKER_VIEW_INSURANCE_WAIVED.equals(event) || MAKER_VIEW_INSURANCE_PENDING.equals(event)
				){
			result = true;
		}
		return result;
	}
	
	
}
