//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ActualListCmd;
import com.integrosys.cms.ui.collateral.ActualListCmdNew;
import com.integrosys.cms.ui.collateral.ApproveCollateralCommand;
import com.integrosys.cms.ui.collateral.ApproveInsuranceCommand;
import com.integrosys.cms.ui.collateral.ApproveOtherReceiptCommand;
import com.integrosys.cms.ui.collateral.DownloadInsuranceHistoryReportCommand;
import com.integrosys.cms.ui.collateral.GenerateInsuranceHistoryReportCommand;
import com.integrosys.cms.ui.collateral.InsuranceHistoryForm;
import com.integrosys.cms.ui.collateral.InsuranceHistoryValidator;
import com.integrosys.cms.ui.collateral.ListComponentCmd;
import com.integrosys.cms.ui.collateral.ListInsuranceCompanyCmd;
import com.integrosys.cms.ui.collateral.ListInsuranceCurrencyCmd;
import com.integrosys.cms.ui.collateral.MaintainInsuranceGCForm;
import com.integrosys.cms.ui.collateral.MaintainOtherCheckListCommand;
import com.integrosys.cms.ui.collateral.MakerPrepareCloseInsuranceCmd;
import com.integrosys.cms.ui.collateral.MakerPrepareInsuranceCmd;
import com.integrosys.cms.ui.collateral.MakerPrepareProcessInsuranceCmd;
import com.integrosys.cms.ui.collateral.MakerViewInsuranceCommand;
import com.integrosys.cms.ui.collateral.MakerViewInsuranceErrorCommand;
import com.integrosys.cms.ui.collateral.MakerViewInsuranceStatusCmd;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.PrepareViewInsuranceSessionCommand;
import com.integrosys.cms.ui.collateral.ProcessCollateralCommand;
import com.integrosys.cms.ui.collateral.ProcessInsuranceCommand;
import com.integrosys.cms.ui.collateral.ProcessingCollateralCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;
import com.integrosys.cms.ui.collateral.SaveCersaiDetailsToSessionCommand;
import com.integrosys.cms.ui.collateral.SubmitOtherCheckListCommand;
import com.integrosys.cms.ui.collateral.SubmitOtherReceiptCommand;
import com.integrosys.cms.ui.collateral.ValidateStpCollateralCommand;
import com.integrosys.cms.ui.collateral.ViewInsuranceHistoryCommand;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedAction;
import com.integrosys.cms.ui.collateral.assetbased.DeleteChargeCommand;
import com.integrosys.cms.ui.collateral.pledge.DeletePledgeCommand;
import com.integrosys.cms.ui.collateral.pledgor.DeletePledgorCommand;
import com.integrosys.cms.ui.image.DownloadImageCommand;

/**
 * This is Asset Based - General Charge Subtype Action
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/07/27 04:49:42 $ Tag: $Name: $
 */
public class AssetGenChargeAction extends AssetBasedAction {

	public static final String EVENT_PRINT_SHORTFALL_REMINDER = "print_shortfall_reminder";

	public static final String EVENT_PRINT_DRAWING_POWER = "print_drawing_power";
	
	public static final String EVENT_VIEW_STOCKS = "view_stocks";
	
	public static final String EVENT_VIEW_STOCKS_TRACK = "view_stocks_track";
	
	public static final String EVENT_PREPARE_ERROR= "error_prepare_event";
	
	public static final String EVENT_VIEW_DUE_DATE_AND_STOCK= "view_due_date_and_stock";
	public static final String EVENT_CHECKER_DUE_DATE_AND_STOCK= "checker_due_date_and_stock";
	public static final String EVENT_ADD_DUE_DATE_AND_STOCK= "add_due_date_and_stock";
	public static final String EVENT_EDIT_DUE_DATE_AND_STOCK= "edit_due_date_and_stock";
	public static final String EVENT_SAVE_DUE_DATE_AND_STOCK= "save_due_date_and_stock";
	public static final String EVENT_REMOVE_DUE_DATE_AND_STOCK= "remove_due_date_and_stock";
	public static final String EVENT_RETURN_FROM_DUE_DATE_AND_STOCK= "return_from_due_date_and_stock";
	public static final String EVENT_RETURN_VIEW_DUE_DATE_AND_STOCK="return_from_view_due_date_and_stock";
	public static final String EVENT_RETURN_CHECKER_DUE_DATE_AND_STOCK="return_from_checker_due_date_and_stock";
	public static final String EVENT_RETURN_TO_DUE_DATE_AND_STOCK= "return_to_due_date_and_stock";
	
	//public static final String EVENT_PREPARE_DUE_DATE_AND_STOCK= "prepare_due_date_and_stock";
	public static final String EVENT_SAVE_DUE_DATE_AND_STOCK_ERROR= "save_due_date_and_stock_error";
	public static final String EVENT_LEAD_BANK_STOCK_ERROR = "lead_bank_stock_error";
	
	public static final String EVENT_UPDATE_SESSION_ADD_DUE_DATE_AND_STOCK= "update_session_add_due_date_and_stock";
	public static final String EVENT_UPDATE_SESSION_EDIT_DUE_DATE_AND_STOCK= "update_session_edit_due_date_and_stock";
	public static final String EVENT_UPDATE_SESSION_REMOVE_DUE_DATE_AND_STOCK= "update_session_remove_due_date_and_stock";
	public static final String EVENT_UPDATE_SESSION_VIEW_INSURANCE_HISTORY= "update_session_view_insurance_history";
	public static final String EVENT_ADD_STOCKS_AND_DATE_IN_EDIT= "add_each_stock_and_date_in_edit";
	public static final String EVENT_EDIT_STOCKS_AND_DATE= "edit_each_stock_and_date";
	public static final String EVENT_ADD_STOCKS_AND_DATE_IN_RESUBMIT= "add_each_stock_and_date_in_resubmit";
	public static final String EVENT_SAVE_STOCKS_AND_DATE_IN_EDIT= "save_each_stock_and_datein_edit";
	public static final String EVENT_SAVE_STOCKS_AND_DATE_IN_RESUBMIT= "save_each_stock_and_date_resubmit";
			
	public static final String EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK = "update_session_add_lead_bank_stock";
	public static final String EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK = "update_session_edit_lead_bank_stock";
	public static final String EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL = "update_session_add_stock_detail";
	public static final String EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL = "update_session_edit_stock_detail";
	public static final String EVENT_PREPARE_ADD_LEAD_BANK_STOCK = "prepare_add_lead_bank_stock";
	public static final String EVENT_ADD_LEAD_BANK_STOCK = "add_lead_bank_stock";
	public static final String EVENT_VIEW_LEAD_BANK_STOCK = "view_lead_bank_stock";
	public static final String EVENT_PREPARE_EDIT_LEAD_BANK_STOCK = "prepare_edit_lead_bank_stock";
	public static final String EVENT_EDIT_LEAD_BANK_STOCK = "edit_lead_bank_stock";
	public static final String EVENT_REMOVE_LEAD_BANK_STOCK = "remove_lead_bank_stock";
	public static final String EVENT_SAVE_LEAD_BANK_STOCK = "save_lead_bank_stock";
	public static final String EVENT_BACK_TO_STOCK_AND_DUE_DATE = "back_to_stock_and_due_date";
	public static final String EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT = "update_session_view_stock_statement";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		System.out.println("@@@@@@@@@@@@event in AssetGenChargeAction class:::::"+event);
		ICommand objArray[];
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCollateralCreateCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
		}
		else if (EVENT_PREPARE_UPDATE.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
			objArray[2] = new ReadAssetGenChargeCommand();
			objArray[3] = new ActualListCmd();
			
		}else if (EVENT_PROCESS_UPDATE.equals(event)) {
			objArray = new ICommand[7];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
			objArray[2] = new ReadAssetGenChargeCommand();
			objArray[3] = new ActualListCmd();
			objArray[4] = new ListComponentCmd();
			objArray[5] = new ListInsuranceCompanyCmd();
			objArray[6] = new MakerPrepareProcessInsuranceCmd();
			
		}else if (MAKER_CREATE_INSURANCE.equals(event)) {
			objArray = new ICommand[4];
			//objArray[0] = new ReadCollateralCommand();
			//objArray[1] = new PrepareAssetGenChargeCommand();
			//objArray[2] = new ReadAssetGenChargeCommand();
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new CreateInsuranceGCCommand();
		}else if (MAKER_CANCLE_CREATE_INSURANCE.equals(event) || MAKER_RETURN_FROM_SUBMIT_INSURANCE.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
		}else if (MAKER_DELETE_LIST_INSURANCE.equals(event)) {
			objArray = new ICommand[5];
			//objArray[0] = new ReadCollateralCommand();
			//objArray[1] = new PrepareAssetGenChargeCommand();
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ReadAssetGenChargeCommand();
			objArray[4] = new DeleteListInsuranceCommand();
		}else if (MAKER_EDIT_LIST_INSURANCE.equals(event)) {
			objArray = new ICommand[5];
			//objArray[0] = new ReadCollateralCommand();
			//objArray[1] = new PrepareAssetGenChargeCommand();
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ListComponentCmd();
			objArray[3] = new ActualListCmd();
			objArray[4] = new EditListInsuranceCommand();
		}
		else if (EVENT_VIEW_STOCKS.equals(event) || EVENT_VIEW_STOCKS_TRACK.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new PrepareAssetGenChargeCommand();
			objArray[1] = new ViewFilteredStockDetailsCommand();
			objArray[2] = new RefreshFilterLocationsCommand();
			objArray[3] = new ActualListCmd();
		}else if (EVENT_READ.equals(event)) {
			objArray = new ICommand[7];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ListComponentCmd();
			objArray[4] = new ListInsuranceCompanyCmd();
			objArray[5] = new ListInsuranceCurrencyCmd();
			objArray[6] = new MakerPrepareInsuranceCmd();
		}
		else if ( EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)) {
			objArray = new ICommand[7];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ListComponentCmd();
			objArray[4] = new ListInsuranceCompanyCmd();
			objArray[5] = new ListInsuranceCurrencyCmd();
			objArray[6] = new MakerPrepareCloseInsuranceCmd();
		}
		else if (EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[7];
			objArray[0] = new ProcessCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ListComponentCmd();
			objArray[4] = new ListInsuranceCompanyCmd();
			objArray[5] = new ListInsuranceCurrencyCmd();
			objArray[6] = new ProcessInsuranceCommand();
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareAssetGenChargeCommand();
			objArray[1] = new ActualListCmd();
		}
		else if (EVENT_UPDATE_RETURN.equals(event)) {
			//objArray = new ICommand[3];
			objArray = new ICommand[3];
			objArray[0] = new PrepareAssetGenChargeCommand();
			objArray[1] = new ReturnCollateralCommand();
			objArray[2] = new ActualListCmd();
			//objArray[2] = new ReadAssetGenChargeCommand();
		}
		else if (EVENT_PROCESS_RETURN.equals(event) || EVENT_READ_RETURN.equals(event)
				|| EVENT_CLOSE_RETURN.equals(event) || EVENT_TRACK_RETURN.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ReturnInsuranceCommand();
		}
		else if (EVENT_DELETE_ITEM.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteChargeCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
		}
		else if (EVENT_DELETE_PLEDGOR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgorCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
		}
		else if (EVENT_DELETE_PLEDGE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeletePledgeCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
		}
		else if (event.equals(EVENT_FORWARD)) {
			objArray = new ICommand[1];
			objArray[0] = new NavigationGenChargeCommand();
		}
		else if (EVENT_EDIT.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ProcessingCollateralCommand();
			objArray[1] = new NavigationGenChargeCommand();
		}
		else if (EVENT_PRINT_DRAWING_POWER.equals(event) || EVENT_PRINT_SHORTFALL_REMINDER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrintReminderCommand();
		}
		else if (EVENT_PREPARE_ERROR.equals(event)) {
			objArray = new ICommand[1];
			
			objArray[0] = new ReturnCollateralCommand();
		}else if (EVENT_VIEW_INSURANCE_HISTORY.equals(event) || EVENT_SEARCH_INSURANCE_HISTORY.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ViewInsuranceHistoryCommand();
			objArray[1] = new GenerateInsuranceHistoryReportCommand();
		}else if (EVENT_SEARCH_INSURANCE_HISTORY_ERROR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareViewInsuranceSessionCommand();
		}else if (EVENT_DOWNLOAD_INSURANCE_HISTORY_REPORT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadInsuranceHistoryReportCommand();
		} else if (MAKER_VIEW_INSURANCE.equals(event)||CHECKER_VIEW_INSURANCE.equals(event)||CHECKER_VIEW_INSURANCE_PROCESS.equals(event)
				||MAKER_PREPARE_EDIT_INSURANCE.equals(event)||MAKER_PREPARE_DELETE_INSURANCE.equals(event)
				||MAKER_VIEW_INS_EDIT.equals(event)||CHECKER_VIEW_INS_READ.equals(event)
				||MAKER_VIEW_INS_CLOSE.equals(event) ||MAKER_VIEW_INS_TRACK.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ActualListCmdNew();
			objArray[1] = new MakerViewInsuranceCommand();
		}
		else if ("maker_edit_list_insurance_error".equals(event)|| "maker_create_insurance_error".equals(event) || "maker_edit_insreceived_list_error".equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ActualListCmdNew();
			objArray[1] = new MakerViewInsuranceErrorCommand();
		}
		else if (MAKER_EDIT_CANCLE_INSURANCE.equals(event)||MAKER_RETURN_VIEW_INSURANCE.equals(event) || MAKER_EDIT_CANCEL_INSSTATUS.equals(event)) {
			objArray = new ICommand[4];
			//objArray[0] = new ReadCollateralCommand();
		    //	objArray[1] = new PrepareAssetGenChargeCommand();
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ReturnInsuranceCommand();
		}
		//Uma Khot::Insurance Deferral maintainance
		else if ("save_cersai_to_session".equals(event)){
			objArray = new ICommand[1];
			objArray[0] = new SaveCersaiDetailsToSessionCommand();
		}
		else if (MAKER_ADD_INSURANCE.equals(event)){
			objArray = new ICommand[3];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
		
		}else if (MAKER_CANCLE_SUBMIT_INSURANCE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadAssetGenChargeCommand();
	
		}else if (MAKER_SUBMIT_INSURANCE_RECEIVED.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new CreateInsuranceGCCommand();
	
		}else if (MAKER_SUBMIT_INSURANCE_PENDING.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new CreateInsuranceGCCommand();
	
		}else if (MAKER_SUBMIT_INSURANCE_DEFERRED.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new CreateInsuranceGCCommand();
	
		}else if (MAKER_SUBMIT_INSURANCE_WAIVED.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new CreateInsuranceGCCommand();
	
		}
		else if (MAKER_EDIT_INSURANCE_RECEIVED.equals(event) || MAKER_UPDATE_INSURANCE_RECEIVED.equals(event)
				|| MAKER_UPDATE_INSURANCE_PENDING.equals(event) || MAKER_UPDATE_INSURANCE_DEFERRED.equals(event)
				|| MAKER_UPDATE_INSURANCE_WAIVED.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ActualListCmdNew();
			objArray[1] = new MakerViewInsuranceStatusCmd();
		}else if (MAKER_EDIT_INSRECEIVED_LIST.equals(event) || MAKER_UPDATE_INSRECEIVED_LIST.equals(event)  || event.equals(MAKER_UPDATE_INSPENDING_LIST)  || event.equals(MAKER_UPDATE_INSWAIVED_LIST)  || event.equals(MAKER_UPDATE_INSDEFERRED_LIST)) {
			objArray = new ICommand[5];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ListComponentCmd();
			objArray[3] = new ActualListCmd();
			objArray[4] = new EditListInsuranceCommand();
		}else if (MAKER_VIEW_INSURANCE_RECEIVED.equals(event) || CHECKER_VIEW_INSURANCE_RECEIVED.equals(event)
				|| MAKER_VIEW_INSURANCE_PENDING.equals(event) || MAKER_VIEW_INSURANCE_DEFERRED.equals(event) || MAKER_VIEW_INSURANCE_WAIVED.equals(event)
			 || CHECKER_VIEW_INSURANCE_PENDING.equals(event) ||  CHECKER_VIEW_INSURANCE_DEFERRED.equals(event)|| CHECKER_VIEW_INSURANCE_WAIVED.equals(event)){
			objArray = new ICommand[2];
			objArray[0] = new ActualListCmdNew();
			objArray[1] = new MakerViewInsuranceCommand();
		}else if (EVENT_APPROVE.equals(event)) {
			objArray = new ICommand[8];
			objArray[0] = new ValidateStpCollateralCommand("HP", "AB102");
			objArray[1] = new ApproveCollateralCommand();
			objArray[2] = new ActualListCmd();
			objArray[3] = new ApproveInsuranceCommand();
			objArray[4] = new MaintainOtherCheckListCommand();
			objArray[5] = new SubmitOtherCheckListCommand();
			objArray[6] = new SubmitOtherReceiptCommand();
			objArray[7] = new ApproveOtherReceiptCommand();
		}else if (event.equals(EVENT_ADD_STOCKS_AND_DATE_IN_EDIT)
				|| event.equals(EVENT_ADD_STOCKS_AND_DATE_IN_RESUBMIT)) {
			objArray = new ICommand[1];
			objArray[0] = new ReadAssetGenChargeCommand(); 
		}else if (event.equals(EVENT_UPDATE_SESSION_ADD_DUE_DATE_AND_STOCK)
				|| event.equals(EVENT_UPDATE_SESSION_EDIT_DUE_DATE_AND_STOCK)
				|| event.equals(EVENT_UPDATE_SESSION_REMOVE_DUE_DATE_AND_STOCK)
				|| event.equals(EVENT_UPDATE_SESSION_VIEW_INSURANCE_HISTORY)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateSessionAssetGenChargeCommand();
		} else if (EVENT_VIEW_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewDueDateAndStockCommand();
		}else if (EVENT_CHECKER_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ActualListCmdNew();
			objArray[1] = new ViewDueDateAndStockCommand();
		}else if (EVENT_ADD_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddDueDateAndStockCommand();
		}else if (EVENT_EDIT_DUE_DATE_AND_STOCK.equals(event) || EVENT_SAVE_DUE_DATE_AND_STOCK_ERROR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditDueDateAndStockCommand();
		}else if(EVENT_SAVE_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new SaveDueDateAndStockCommand();
		} else if(EVENT_REMOVE_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveDueDateAndStockCommand();
		} else if(EVENT_RETURN_FROM_DUE_DATE_AND_STOCK.equals(event)
				|| EVENT_RETURN_VIEW_DUE_DATE_AND_STOCK.equals(event)
				|| EVENT_RETURN_CHECKER_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new ListComponentCmd();
			objArray[3] = new ActualListCmd();
		}else if (EVENT_RETURN_TO_DUE_DATE_AND_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ReturnToDueDateAndStockCommand();
		}else if (EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK.equals(event)
				||EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK.equals(event)
				||EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL.equals(event)
				||EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL.equals(event)
				||EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateSessionDueDateAndStockCommand();
		}else if (EVENT_PREPARE_ADD_LEAD_BANK_STOCK.equals(event) )  {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAddLeadBankStockCommand();
		}else if (EVENT_PREPARE_EDIT_LEAD_BANK_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareEditLeadBankStockCommand();
		}else if (EVENT_VIEW_LEAD_BANK_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewLeadBankStockCommand();
		}else if (EVENT_REMOVE_LEAD_BANK_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RemoveLeadBankStockCommand();
		}else if (EVENT_ADD_LEAD_BANK_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddLeadBankStockCommand();
		}else if (EVENT_EDIT_LEAD_BANK_STOCK.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new EditLeadBankStockCommand();
		}else if(EVENT_LEAD_BANK_STOCK_ERROR.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareLeadBankStockCommand();
		}else if("downloadImage".equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new DownloadImageCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}
		
		DefaultLogger.info(this, "Command Chain: "+Arrays.toString(objArray));
		return objArray;
	}
	
	

	/**
	 * This method is called only for create and Update command to validate the
	 * form and return the ActionErrors object.
	 * 
	 * @param aForm is of type ActionForm
	 * @param locale of type Locale
	 * @return ActionErrors
	 */
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		
		if(aForm instanceof MaintainInsuranceGCForm){
		return AssetGenChargeValidator.validateInput((MaintainInsuranceGCForm) aForm, locale);
		}else if (aForm instanceof AssetGenChargeForm){
			return AssetGenChargeValidator.validateInput((AssetGenChargeForm) aForm, locale);
	    }else if(aForm instanceof DueDateAndStockForm) {
	    	return DueDateAndStockValidator.validateInput((DueDateAndStockForm)aForm, locale);
	    }else if(aForm instanceof LeadBankStockForm) {
	    	return LeadBankStockValidator.validateInput((LeadBankStockForm)aForm, locale);
	    }else if(aForm instanceof InsuranceHistoryForm) {
	    	return InsuranceHistoryValidator.validateInput((InsuranceHistoryForm)aForm, locale);
	    }
		return null;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (event.equals("maker_edit_list_insurance"))
		{
			result = true;
		}
		else if (event.equals("maker_create_insurance"))
		{
			result = true;
		}else if (event.equals(EVENT_SUBMIT))
		{
			result = true;
		}
		
		 //Uma Khot::Insurance Deferral maintainance
		
		else if (event.equals(MAKER_SUBMIT_INSURANCE_PENDING))
		{
			result = true;
		}else if (event.equals(MAKER_SUBMIT_INSURANCE_DEFERRED))
		{
			result = true;
		}else if (event.equals(MAKER_SUBMIT_INSURANCE_RECEIVED))
		{
			result = true;
		}else if (event.equals(MAKER_SUBMIT_INSURANCE_WAIVED))
		{
			result = true;
		}if (event.equals(MAKER_EDIT_INSRECEIVED_LIST) || EVENT_APPROVE.equals(event) || event.equals(MAKER_UPDATE_INSRECEIVED_LIST) 
			|| event.equals(MAKER_UPDATE_INSPENDING_LIST)  || event.equals(MAKER_UPDATE_INSWAIVED_LIST)  
			|| event.equals(MAKER_UPDATE_INSDEFERRED_LIST)
			|| EVENT_SAVE_LEAD_BANK_STOCK.equals(event)
			|| EVENT_UPDATE_SESSION_VIEW_INSURANCE_HISTORY.equals(event)
			|| EVENT_SEARCH_INSURANCE_HISTORY.equals(event)
			|| EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK.equals(event)
			|| EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK.equals(event)
			|| EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL.equals(event) || EVENT_SAVE_DUE_DATE_AND_STOCK.equals(event) || EVENT_ADD_LEAD_BANK_STOCK.equals(event) || EVENT_EDIT_LEAD_BANK_STOCK.equals(event) 
			|| EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL.equals(event) || EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT.equals(event))
		{
			result = true;
		}
		else if(EVENT_PREPARE.equals(event)) {
			return true;
		}
		return result;
	}
	
	
	protected String getErrorEvent(String event) {
		
		if(event.equals(EVENT_VIEW_STOCKS) || event.equals(EVENT_VIEW_STOCKS_TRACK)){
			return EVENT_PREPARE_ERROR;		
		}else if ("maker_edit_list_insurance".equals(event)) {
			return "maker_edit_list_insurance_error";
		}else if ("maker_create_insurance".equals(event)) {
			return "maker_create_insurance_error";
		}
		
		 //Uma Khot::Insurance Deferral maintainance
		
		else if (MAKER_SUBMIT_INSURANCE_PENDING.equals(event)) {
			return "maker_submit_ins_pending_error";
		}else if (MAKER_SUBMIT_INSURANCE_DEFERRED.equals(event)) {
			return "maker_submit_ins_deferred_error";
		}else if (MAKER_SUBMIT_INSURANCE_RECEIVED.equals(event)) {
			return "maker_submit_ins_received_error";
		}else if (MAKER_SUBMIT_INSURANCE_WAIVED.equals(event)) {
			return "maker_submit_ins_waived_error";
		}else if (MAKER_EDIT_INSRECEIVED_LIST.equals(event)) {
			return "maker_edit_insreceived_list_error";
		}else if (MAKER_UPDATE_INSRECEIVED_LIST.equals(event)) {
			return "maker_update_insreceived_list_error";
		}else if (MAKER_UPDATE_INSPENDING_LIST.equals(event)) {
			return "maker_update_inspending_list_error";
		}else if (MAKER_UPDATE_INSWAIVED_LIST.equals(event)) {
			return "maker_update_inswaived_list_error";
		}else if (MAKER_UPDATE_INSDEFERRED_LIST.equals(event)) {
			return "maker_update_insdeferred_list_error";
		}else if (EVENT_APPROVE.equals(event) ) {
			return  EVENT_PROCESS_RETURN;
		}else if (EVENT_SEARCH_INSURANCE_HISTORY.equals(event) ) {
			return EVENT_SEARCH_INSURANCE_HISTORY_ERROR;
		}else if(EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK.equals(event)
				||EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK.equals(event)
				||EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL.equals(event)
				||EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL.equals(event)
				||EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT.equals(event)) {
			return EVENT_EDIT_DUE_DATE_AND_STOCK;
		}else if( EVENT_SAVE_DUE_DATE_AND_STOCK.equals(event))
			return EVENT_SAVE_DUE_DATE_AND_STOCK_ERROR;
		else if( EVENT_ADD_LEAD_BANK_STOCK.equals(event) || EVENT_EDIT_LEAD_BANK_STOCK.equals(event))
			return EVENT_LEAD_BANK_STOCK_ERROR; 
		else if(EVENT_PREPARE.equals(event))	
			return EVENT_PREPARE_STOCK_DETAILS_ERROR;
		else{
			return super.getErrorEvent(event);
		}
	}
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
			return aPage;
		}

		if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
			DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
			aPage.setPageReference((String) resultMap.get("forwardPage"));
			return aPage;
		}
		else if (EVENT_PREPARE_FORM.equals(event)) {
			DefaultLogger.debug(this, "forward page: " + (String) resultMap.get("forwardPage"));
			String from_event = (String) resultMap.get("event");
			if (from_event!=null && from_event.equals("create")) aPage.setPageReference(EVENT_PREPARE_CREATE);
			else { 
				if (resultMap.get("forwardPage") != null) {
					aPage.setPageReference((String) resultMap.get("forwardPage"));
				}
				else {
					aPage.setPageReference(EVENT_PREPARE_UPDATE);
				}
			}
		}
		else if (event.equals(EVENT_PREPARE_UPDATE) || event.equals(EVENT_PROCESS_UPDATE)) {
			if (resultMap.get("forwardPage")!=null)
				aPage.setPageReference((String) resultMap.get("forwardPage"));
			else aPage.setPageReference(event);
		}
		//Added by Anil
		else if (event.equals(EVENT_VIEW_STOCKS) || event.equals(EVENT_VIEW_STOCKS_TRACK)) {
//			aPage.setPageReference("prepare_update");
			String parentPageFrom=(String) resultMap.get("parentPageFrom");
			String subtype = (String) resultMap.get("subtype");
			if(parentPageFrom!=null){
				if("ASSET_READ".equals(parentPageFrom)){
					aPage.setPageReference("read");
				}else if("ASSET_CLOSE".equals(parentPageFrom)){
					aPage.setPageReference("close_return");
				}else if("ASSET_CLOSE_TRACK".equals(parentPageFrom)){
					aPage.setPageReference("track_return");
				}else if("ASSET_PROCESS".equals(parentPageFrom)){
					aPage.setPageReference("process_return");
				}else if("ASSET_UPDATE".equals(parentPageFrom)){
					aPage.setPageReference("prepare_update");
				}else{
					aPage.setPageReference("read");
				}
			}
		}
		else if(event.equals(MAKER_CREATE_INSURANCE)||event.equals(MAKER_EDIT_LIST_INSURANCE)){
			aPage.setPageReference("maker_create_insurance_page");
		}
		else if(event.equals(MAKER_CANCLE_CREATE_INSURANCE)||event.equals(MAKER_EDIT_CANCLE_INSURANCE)
				||event.equals(MAKER_RETURN_VIEW_INSURANCE) || event.equals(MAKER_DELETE_LIST_INSURANCE)
				||event.equals( MAKER_EDIT_CANCEL_INSSTATUS)){
			aPage.setPageReference("maker_cancle_create_insurance");
		}
		else if(event.equals(MAKER_VIEW_INSURANCE)||event.equals(CHECKER_VIEW_INSURANCE)||event.equals(CHECKER_VIEW_INSURANCE_PROCESS)
				||event.equals(MAKER_VIEW_INS_EDIT)||event.equals(CHECKER_VIEW_INS_READ)
				||event.equals(MAKER_VIEW_INS_CLOSE) ||event.equals(MAKER_VIEW_INS_TRACK)){
			aPage.setPageReference("maker_view_insurance");
		}
		else if(event.equals(MAKER_PREPARE_EDIT_INSURANCE)){
			aPage.setPageReference("maker_edit_insurance_read");
		}
		else if(event.equals(MAKER_PREPARE_DELETE_INSURANCE)){
			aPage.setPageReference("maker_delete_insurance_read");
		}
		else if ((event != null) && event.equals("maker_edit_list_insurance_error")) {
			aPage.setPageReference("maker_edit_insurance_read");
		}
		 //Uma Khot::Insurance Deferral maintainance
		else if ((event != null) && event.equals("MAKER_ADD_INSURANCE")) {
			aPage.setPageReference(MAKER_ADD_INSURANCE);
		}else if(event.equals(MAKER_CANCLE_SUBMIT_INSURANCE)){
			aPage.setPageReference("maker_cancle_submit_insurance");
		}else if((null!=event && (event.equals(MAKER_SUBMIT_INSURANCE_PENDING) || event.equals(MAKER_SUBMIT_INSURANCE_DEFERRED)  || event.equals(MAKER_SUBMIT_INSURANCE_RECEIVED) || event.equals(MAKER_SUBMIT_INSURANCE_WAIVED)
				|| event.equals(MAKER_EDIT_INSRECEIVED_LIST)) || event.equals(MAKER_UPDATE_INSRECEIVED_LIST)  || event.equals(MAKER_UPDATE_INSPENDING_LIST)  || event.equals(MAKER_UPDATE_INSWAIVED_LIST)  || event.equals(MAKER_UPDATE_INSDEFERRED_LIST))){
			aPage.setPageReference(MAKER_RETURN_FROM_SUBMIT_INSURANCE);
		}else if(event.equals(MAKER_EDIT_INSURANCE_RECEIVED)){
			aPage.setPageReference("maker_edit_insurance_received");
		}else if(event.equals(MAKER_UPDATE_INSURANCE_PENDING)){
			aPage.setPageReference("maker_update_insurance_pending");
		}else if(event.equals(MAKER_UPDATE_INSURANCE_DEFERRED)){
			aPage.setPageReference("maker_update_insurance_deferred");
		}else if(event.equals(MAKER_UPDATE_INSURANCE_WAIVED)){
			aPage.setPageReference("maker_update_insurance_waived");
		}
		else if(event.equals(MAKER_UPDATE_INSURANCE_RECEIVED)){
			aPage.setPageReference("maker_update_insurance_received");
		}else if(event.equals(MAKER_VIEW_INSURANCE_RECEIVED) || event.equals(CHECKER_VIEW_INSURANCE_RECEIVED)){
			aPage.setPageReference("maker_view_insurance_received");
		}else if(event.equals(MAKER_VIEW_INSURANCE_PENDING) || event.equals(CHECKER_VIEW_INSURANCE_PENDING)){
			aPage.setPageReference("maker_view_insurance_pending");
		}else if(event.equals(MAKER_VIEW_INSURANCE_DEFERRED) || event.equals(CHECKER_VIEW_INSURANCE_DEFERRED)){
			aPage.setPageReference("maker_view_insurance_deferred");
		}else if(event.equals(MAKER_VIEW_INSURANCE_WAIVED) || event.equals(CHECKER_VIEW_INSURANCE_WAIVED)){
			aPage.setPageReference("maker_view_insurance_waived");
		}else if ((event != null) && event.equals("save_cersai_to_session")) {
			aPage.setPageReference("save_cersai_to_session");
		}else if (EVENT_SAVE_DUE_DATE_AND_STOCK.equals(event)) {
			aPage.setPageReference(EVENT_RETURN_FROM_DUE_DATE_AND_STOCK);
		}else if (EVENT_ADD_LEAD_BANK_STOCK.equals(event)
					||EVENT_EDIT_LEAD_BANK_STOCK.equals(event) || EVENT_REMOVE_LEAD_BANK_STOCK.equals(event)) {
			String pageRef = (String) resultMap.get("referrerEvent");
			aPage.setPageReference(pageRef);
		}else if (EVENT_REMOVE_DUE_DATE_AND_STOCK.equals(event)) {
			aPage.setPageReference(EVENT_RETURN_FROM_DUE_DATE_AND_STOCK);
		} 
		else if(EVENT_SEARCH_INSURANCE_HISTORY_ERROR.equals(event)) {
			aPage.setPageReference(EVENT_SEARCH_INSURANCE_HISTORY);
		}
		else if(event.equals("downloadImage")){
			aPage.setPageReference("downloadImage");
		}
		else {
			aPage = (Page) super.getNextPage(event, resultMap, exceptionMap);
		}
		return aPage;
	}
	
}
