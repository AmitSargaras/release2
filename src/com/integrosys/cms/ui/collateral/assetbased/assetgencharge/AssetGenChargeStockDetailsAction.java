package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.ActualListCmd;
import com.integrosys.cms.ui.collateral.PrepareCollateralCreateCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;
import com.integrosys.cms.ui.collateral.ReturnCollateralCommand;

/**
 * This is Asset Based - General Charge Subtype Action
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2006/07/27 04:49:42 $ Tag: $Name: $
 */
public class AssetGenChargeStockDetailsAction extends AssetGenChargeAction {

	public static final String EVENT_VIEW_STOCK_DETAILS = "view_stock_details";
	public static final String EVENT_VIEW_STOCK_DETAILS_CLOSE = "view_stock_details_close";
	public static final String EVENT_VIEW_STOCK_DETAILS_CHECK = "view_stock_details_check";
	public static final String EVENT_VIEW_STOCK_DETAILS_TRACK = "view_stock_details_track";
	public static final String EVENT_EDIT_PREPARE_STOCK_DETAILS = "edit_prepare_stock_details";
	public static final String EVENT_EDIT_STOCK_DETAILS_ERROR = "edit_stock_details_error";
	public static final String EVENT_EDIT_STOCK_DETAILS = "edit_stock_details";
	public static final String EVENT_EDIT_STOCK_DETAILS_INSURANCE = "edit_stock_details_ins";
	public static final String EVENT_PREPARE_CREATE_CURRENT_ASSET = "prepare_create_current_asset";
	
	public static final String EVENT_PREPARE_CREATE_VALUE_DEBTORS = "prepare_create_value_debtors";

	public static final String EVENT_PREPARE_CREATE_CURRENT_ASSET_INSURENCE = "prepare_create_current_asset_insurence";
	public static final String EVENT_CREATE_CURRENT_ASSET = "create_current_asset";
	public static final String EVENT_PREPARE_CREATE_CURRENT_LIABILITIES = "prepare_create_current_liabilities";
	public static final String EVENT_PREPARE_CREATE_LESS_VALUE_ADVANCES = "prepare_create_less_value_advances";

	
	public static final String EVENT_CREATE_CURRENT_LIABILITIES = "create_current_liabilities";
	public static final String EVENT_CANCLE_STOCK_DETAIL = "cancle_stock_detail";
	public static final String EVENT_CANCLE_STOCK_DETAIL_CLOSE = "cancle_stock_detail_close";
	public static final String EVENT_CANCLE_STOCK_DETAIL_CHECK = "cancle_stock_detail_check";
	public static final String EVENT_CREATE_STOCK_DETAIL = "create_stock_detail";
	public static final String EVENT_VIEW_CURRENT_ASSET = "view_current_asset";
	public static final String EVENT_VIEW_CURRENT_ASSET_CLOSE = "view_current_asset_close";
	public static final String EVENT_VIEW_CURRENT_ASSET_CHECK = "view_current_asset_check";
	public static final String EVENT_EDIT_PREPARE_CURRENT_ASSET = "edit_prepare_current_asset";
	public static final String EVENT_EDIT_PREPARE_CURRENT_ERROR = "edit_prepare_current_error";
	public static final String EVENT_EDIT_CURRENT_ASSET = "edit_current_asset";
	public static final String EVENT_EDIT_VALUE_DEBTORS= "edit_value_debtors";
	public static final String EVENT_CREATE_VALUE_DEBTORS = "create_value_debtors";
	public static final String EVENT_CREATE_LESS_VALUE_ADVANCES = "create_less_value_advances";

	
	public static final String EVENT_VIEW_VALUE_DEBTORS = "view_value_debtors";
	public static final String EVENT_VIEW_VALUE_DEBTORS_CLOSE = "view_value_debtors_close";
	public static final String EVENT_VIEW_VALUE_DEBTORS_CHECK = "view_value_debtors_check";
	public static final String EVENT_EDIT_PREPARE_VALUE_DEBTORS = "edit_prepare_value_debtors"; 


	public static final String EVENT_EDIT_PREPARE_CURRENT_ASSET_INSURANCE ="edit_prepare_current_asset_ins";
	public static final String EVENT_EDIT_CURRENT_ASSET_INSURANCE_ERROR ="edit_current_asset_ins_error";
	public static final String EVENT_EDIT_CURRENT_ASSET_INSURANCE ="edit_current_asset_ins";
	public static final String EVENT_VIEW_CURRENT_LIABILITIES= "view_current_liabilities";
	public static final String EVENT_VIEW_CURRENT_LIABILITIES_CLOSE= "view_current_liabilities_close";
	public static final String EVENT_VIEW_CURRENT_LIABILITIES_CHECK= "view_current_liabilities_check";
	public static final String EVENT_EDIT_PREPARE_CURRENT_LIABILITIES= "edit_prepare_current_liabilities";
	public static final String EVENT_EDIT_PREPARE_LESS_VALUE_ADVANCES = "edit_prepare_less_value_advances";
	
	public static final String EVENT_VIEW_LESS_VALUE_ADVANCES = "view_less_value_advances";
	public static final String EVENT_VIEW_LESS_VALUE_ADVANCES_CLOSE = "view_less_value_advances_close";
	public static final String EVENT_VIEW_LESS_VALUE_ADVANCES_CHECK = "view_less_value_advances_check";
	
	public static final String EVENT_EDIT_CURRENT_LIABILITIES= "edit_current_liabilities";
	public static final String EVENT_EDIT_LESS_VALUE_ADVANCES= "edit_less_value_advances";

	public static final String EVENT_CANCEL_CURRENT_ASSET_LIABILITIES= "cancel_current_asset_liabilities";
	public static final String EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CLOSE= "cancel_current_asset_liabilities_close";
	public static final String EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CHECK= "cancel_current_asset_liabilities_check";
	public static final String EVENT_GET_FILTER_LOCATIONS= "get_filter_locations";
	public static final String EVENT_FILTER_LOCATIONS= "filter_locations";
	public static final String EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL = "update_session_add_stock_detail";
	public static final String EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL = "update_session_edit_stock_detail";
	public static final String EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK = "update_session_add_lead_bank_stock";
	public static final String EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK = "update_session_edit_lead_bank_stock";
	public static final String EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT = "update_session_view_stock_statement";
	public static final String EVENT_VIEW_STOCK_STATEMENT = "view_stock_statement";
	public static final String EVENT_FORWARD_CURRENT_ASSET_LIABILITY = "forward_current_asset_liability";
	
	/**
	 * This method return a Array of Commad Objects responsible for a event
	 * 
	 * @param event is of type String
	 * @return Icommand Array
	 */
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[];
		if (EVENT_PREPARE_CREATE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new PrepareCollateralCreateCommand();
			objArray[1] = new PrepareAssetGenChargeCommand();
		}else if (EVENT_PREPARE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareStockDetailsCommand();
		}else if (EVENT_PREPARE_CREATE_CURRENT_ASSET.equals(event)||EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(event)
          	|| EVENT_PREPARE_CREATE_VALUE_DEBTORS.equals(event) || EVENT_PREPARE_CREATE_LESS_VALUE_ADVANCES.equals(event)
	        ) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAssetsAndLiabilityCommand();
		}else if (EVENT_PREPARE_CREATE_CURRENT_ASSET_INSURENCE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareAssetsAndLiabilityInsurenceCommand();
		}else if (EVENT_CREATE_CURRENT_ASSET.equals(event)||EVENT_CREATE_CURRENT_LIABILITIES.equals(event)
				|| EVENT_CREATE_VALUE_DEBTORS.equals(event)  || EVENT_CREATE_LESS_VALUE_ADVANCES.equals(event) 
				) {
			objArray = new ICommand[3];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new AddAssetsAndLiabilityCommand();
			objArray[2] = new PrepareStockDetailsCommand();
		}else if (EVENT_VIEW_CURRENT_ASSET.equals(event)||EVENT_VIEW_CURRENT_LIABILITIES.equals(event)|| EVENT_VIEW_CURRENT_ASSET_CLOSE.equals(event)||EVENT_VIEW_CURRENT_LIABILITIES_CLOSE.equals(event)
				|| EVENT_VIEW_CURRENT_ASSET_CHECK.equals(event)||EVENT_VIEW_CURRENT_LIABILITIES_CHECK.equals(event)
				|| EVENT_VIEW_VALUE_DEBTORS.equals(event)||EVENT_VIEW_VALUE_DEBTORS_CHECK.equals(event) ||EVENT_VIEW_VALUE_DEBTORS_CLOSE.equals(event)
				|| EVENT_VIEW_LESS_VALUE_ADVANCES.equals(event)||EVENT_VIEW_LESS_VALUE_ADVANCES_CHECK.equals(event) ||EVENT_VIEW_LESS_VALUE_ADVANCES_CLOSE.equals(event)

				) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCurrentAssetAndLiabilityCommand();
		}else if ( EVENT_EDIT_PREPARE_CURRENT_ASSET_INSURANCE.equals(event)
				||EVENT_EDIT_CURRENT_ASSET_INSURANCE_ERROR.equals(event)
				) {
			objArray = new ICommand[1];
			objArray[0] = new ViewCurrentAssetAndLiabilityCommand();
		}else if (EVENT_EDIT_PREPARE_CURRENT_ASSET.equals(event)||EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(event) 
				||EVENT_EDIT_PREPARE_VALUE_DEBTORS.equals(event) ||EVENT_EDIT_PREPARE_LESS_VALUE_ADVANCES.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new UpdateSessionStockDetailsCommand();
			objArray[1] = new ViewCurrentAssetAndLiabilityCommand();
		}else if (EVENT_EDIT_CURRENT_ASSET.equals(event)||EVENT_EDIT_CURRENT_LIABILITIES.equals(event)
				||EVENT_EDIT_VALUE_DEBTORS.equals(event) ||EVENT_EDIT_LESS_VALUE_ADVANCES.equals(event)
				) {
			objArray = new ICommand[3];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new EditAssetsAndLiabilityCommand();
			objArray[2] = new PrepareStockDetailsCommand();
		}else if (EVENT_EDIT_CURRENT_ASSET_INSURANCE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new EditAssetInsuranceCommand();
		}else if (EVENT_CREATE_STOCK_DETAIL.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadAssetGenChargeCommand();
			objArray[1] = new AddStockDetailsCommand();
			objArray[2] = new ActualListCmd();
		}else if (EVENT_CANCEL_CURRENT_ASSET_LIABILITIES.equals(event)|| EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CLOSE.equals(event) || EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CHECK.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new PrepareStockDetailsCommand();
		}else if (EVENT_VIEW_STOCK_DETAILS.equals(event)||EVENT_EDIT_PREPARE_STOCK_DETAILS.equals(event) || EVENT_VIEW_STOCK_DETAILS_CLOSE.equals(event) || EVENT_VIEW_STOCK_DETAILS_CHECK.equals(event) || EVENT_VIEW_STOCK_DETAILS_TRACK.equals(event) || EVENT_EDIT_STOCK_DETAILS_ERROR.equals(event) || EVENT_EDIT_PREPARE_CURRENT_ERROR.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadAssetGenChargeCommand();
			objArray[1] = new ViewStockDetailsCommand();
		}else if (EVENT_EDIT_STOCK_DETAILS.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadAssetGenChargeCommand();
			objArray[1] = new EditStockDetailsCommand();
		}else if (EVENT_EDIT_STOCK_DETAILS_INSURANCE.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadAssetGenChargeCommand();
			objArray[1] = new EditStockDetailsInsuranceCommand();
		}else if (EVENT_CANCLE_STOCK_DETAIL.equals(event) || EVENT_CANCLE_STOCK_DETAIL_CLOSE.equals(event)|| EVENT_CANCLE_STOCK_DETAIL_CHECK.equals(event)) {
			objArray = new ICommand[4];
			objArray[0] = new ReturnCollateralCommand();
			objArray[1] = new ReadAssetGenChargeCommand();
			objArray[2] = new CancleStockDetailsCommand();
			objArray[3] = new ActualListCmd();
		}else if (EVENT_FILTER_LOCATIONS.equals(event)) {
			objArray = new ICommand[3];
			objArray[0] = new ReadAssetGenChargeCommand();
			objArray[1] = new ActualListCmd();
			objArray[2] = new RefreshFilterLocationsCommand();
		}else if (EVENT_GET_FILTER_LOCATIONS.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshFilterLocationsCommand();
		}else if(EVENT_UPDATE_SESSION_ADD_STOCK_DETAIL.equals(event)
				||EVENT_UPDATE_SESSION_EDIT_STOCK_DETAIL.equals(event)
				|| EVENT_UPDATE_SESSION_ADD_LEAD_BANK_STOCK.equals(event) 
				|| EVENT_UPDATE_SESSION_EDIT_LEAD_BANK_STOCK.equals(event)
				|| EVENT_UPDATE_SESSION_VIEW_STOCK_STATEMENT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateSessionDueDateAndStockCommand();
		}else if (EVENT_VIEW_STOCK_STATEMENT.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new ViewStockStatementImageCommand();
		}else if(EVENT_FORWARD_CURRENT_ASSET_LIABILITY.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new UpdateSessionStockDetailsCommand();
		}else {
			objArray = super.getCommandChain(event);
		}
		
		DefaultLogger.info(this, "Command Chain: "+Arrays.toString(objArray));
		return objArray;
	}

	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if (EVENT_CREATE_CURRENT_ASSET.equals(event)||EVENT_CREATE_CURRENT_LIABILITIES.equals(event)
			||EVENT_EDIT_CURRENT_ASSET.equals(event)||EVENT_EDIT_CURRENT_LIABILITIES.equals(event)
			||EVENT_EDIT_CURRENT_ASSET_INSURANCE.equals(event)
			|| EVENT_CREATE_VALUE_DEBTORS.equals(event)  || EVENT_EDIT_VALUE_DEBTORS.equals(event)
			|| EVENT_CREATE_LESS_VALUE_ADVANCES.equals(event)  || EVENT_EDIT_LESS_VALUE_ADVANCES.equals(event)
			
			|| EVENT_CREATE_STOCK_DETAIL.equals(event) 
			|| EVENT_PREPARE_CREATE_CURRENT_ASSET.equals(event) || EVENT_EDIT_PREPARE_CURRENT_ASSET.equals(event)
			|| EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(event) || EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(event)
			|| EVENT_VIEW_CURRENT_ASSET.equals(event) || EVENT_VIEW_CURRENT_LIABILITIES.equals(event) || EVENT_EDIT_STOCK_DETAILS.equals(event) 
			|| EVENT_FORWARD_CURRENT_ASSET_LIABILITY.equals(event)) {
			result = true;
		}
		return result;
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
		return AssetGenChargeStockDetailsValidator.validateInput((AssetGenChargeStockDetailsForm) aForm, locale);
	}
	
	protected String getErrorEvent(String event) {
		DefaultLogger.debug(this," In the getErrorEvent()");
		String errorEvent=event;
		if(EVENT_CREATE_STOCK_DETAIL.equals(event)){
			errorEvent=EVENT_PREPARE;
		}else if(EVENT_EDIT_STOCK_DETAILS.equals(event) || EVENT_FORWARD_CURRENT_ASSET_LIABILITY.equals(event)){
			errorEvent=EVENT_EDIT_STOCK_DETAILS_ERROR;
		}else if(EVENT_PREPARE_CREATE_CURRENT_ASSET.equals(event)||EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(event)||EVENT_PREPARE_CREATE_CURRENT_ASSET_INSURENCE.equals(event)
				||EVENT_PREPARE_CREATE_VALUE_DEBTORS.equals(event) ||EVENT_PREPARE_CREATE_LESS_VALUE_ADVANCES.equals(event)){
			errorEvent=EVENT_PREPARE;
		}else if(EVENT_CREATE_CURRENT_ASSET.equals(event)){
			errorEvent=EVENT_PREPARE_CREATE_CURRENT_ASSET;
		}else if(EVENT_CREATE_CURRENT_LIABILITIES.equals(event)){
			errorEvent=EVENT_PREPARE_CREATE_CURRENT_LIABILITIES;
		}else if(EVENT_CREATE_LESS_VALUE_ADVANCES.equals(event)){
			errorEvent=EVENT_PREPARE_CREATE_LESS_VALUE_ADVANCES;
		}else if(EVENT_EDIT_CURRENT_ASSET.equals(event)){
			errorEvent=EVENT_EDIT_PREPARE_CURRENT_ASSET;
		}
		
		else if(EVENT_CREATE_VALUE_DEBTORS.equals(event)){
			errorEvent=EVENT_PREPARE_CREATE_VALUE_DEBTORS;
		}
		else if(EVENT_EDIT_VALUE_DEBTORS.equals(event)){
			errorEvent=EVENT_EDIT_PREPARE_VALUE_DEBTORS;
		}
		
		else if(EVENT_EDIT_CURRENT_ASSET_INSURANCE.equals(event)){
			errorEvent=EVENT_EDIT_CURRENT_ASSET_INSURANCE_ERROR;
		}else if(EVENT_EDIT_CURRENT_LIABILITIES.equals(event)){
			errorEvent=EVENT_EDIT_PREPARE_CURRENT_LIABILITIES;
		}else if(EVENT_EDIT_LESS_VALUE_ADVANCES.equals(event)){
			errorEvent=EVENT_EDIT_PREPARE_LESS_VALUE_ADVANCES;
		}else if(EVENT_PREPARE.equals(event)){
			errorEvent=EVENT_PREPARE_ERROR;
		}else if(EVENT_EDIT_PREPARE_CURRENT_ASSET.equals(event) || EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(event)) {
			errorEvent=EVENT_EDIT_PREPARE_CURRENT_ERROR;
		}	
		return errorEvent;
	}

	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		String parentPageFrom=(String) resultMap.get("parentPageFrom");
		DefaultLogger.debug(this, "parentPageFrom===================================>" + parentPageFrom);
		String fromPage=(String) resultMap.get("from_page");
		if ((resultMap.get("wip") != null) && ((String) resultMap.get("wip")).equals("wip")) {
			aPage.setPageReference("wip");
		}else if (EVENT_CREATE_STOCK_DETAIL.equals(event)
				||EVENT_EDIT_STOCK_DETAILS.equals(event)
				||EVENT_EDIT_STOCK_DETAILS_INSURANCE.equals(event)
				){
			DefaultLogger.debug(this, "ResultMap is :" + resultMap);
			String subtype = (String) resultMap.get("subtype");
			if (subtype == null) {
				// todo forward to error page after populating the exceptionMap
				throw new RuntimeException("URL passed is wrong");
			}else if("ASSET_UPDATE".equals(parentPageFrom)) {
				aPage.setPageReference(EVENT_EDIT_DUE_DATE_AND_STOCK);
			}else {
				aPage.setPageReference(subtype + "_update");
			}
		}else if(EVENT_CANCLE_STOCK_DETAIL.equals(event) || EVENT_CANCLE_STOCK_DETAIL_CLOSE.equals(event)|| EVENT_CANCLE_STOCK_DETAIL_CHECK.equals(event)){
			String subtype = (String) resultMap.get("subtype");
			if(parentPageFrom!=null){
				if("ASSET_READ".equals(parentPageFrom)){
					aPage.setPageReference(EVENT_VIEW_DUE_DATE_AND_STOCK);
				}else if("ASSET_CLOSE".equals(parentPageFrom)){
					aPage.setPageReference(EVENT_VIEW_DUE_DATE_AND_STOCK);
				}else if("ASSET_CLOSE_TRACK".equals(parentPageFrom)){
					aPage.setPageReference(EVENT_VIEW_DUE_DATE_AND_STOCK);
				}else if("ASSET_PROCESS".equals(parentPageFrom)){
					aPage.setPageReference(EVENT_CHECKER_DUE_DATE_AND_STOCK);
				}else if("ASSET_UPDATE".equals(parentPageFrom)){
					aPage.setPageReference(EVENT_EDIT_DUE_DATE_AND_STOCK);
				}else{
					aPage.setPageReference(EVENT_VIEW_DUE_DATE_AND_STOCK);
				}
			}else{
				aPage.setPageReference(EVENT_VIEW_DUE_DATE_AND_STOCK);
			}
		}else if(EVENT_FILTER_LOCATIONS.equals(event)){
			String subtype = (String) resultMap.get("subtype");
			if(parentPageFrom!=null){
				if("ASSET_READ".equals(parentPageFrom)){
					aPage.setPageReference(subtype + "_read_"+EVENT_FILTER_LOCATIONS);
				}else if("ASSET_CLOSE".equals(parentPageFrom)){
					aPage.setPageReference(subtype + "_close");
				}else if("ASSET_CLOSE_TRACK".equals(parentPageFrom)){
					aPage.setPageReference(subtype + "_track");
				}else if("ASSET_PROCESS".equals(parentPageFrom)){
					aPage.setPageReference(subtype + "_process");
				}else if("ASSET_UPDATE".equals(parentPageFrom)){
					aPage.setPageReference(subtype + "_update");
				}else{
					aPage.setPageReference(subtype + "_read");
				}
			}else{
				aPage.setPageReference(subtype + "_read");
			}
		}else if((EVENT_CREATE_CURRENT_ASSET.equals(event)||EVENT_CREATE_CURRENT_LIABILITIES.equals(event) || EVENT_CREATE_VALUE_DEBTORS.equals(event) ||EVENT_CREATE_LESS_VALUE_ADVANCES.equals(event) ) 
				&& fromPage!=null && "update_stock_detail".equals(fromPage)){
			aPage.setPageReference("edit_prepare_stock_details");
		}else if(EVENT_CREATE_CURRENT_ASSET.equals(event)||EVENT_CREATE_CURRENT_LIABILITIES.equals(event) ||EVENT_CREATE_VALUE_DEBTORS.equals(event) ||EVENT_CREATE_LESS_VALUE_ADVANCES.equals(event)){
			aPage.setPageReference("stockDetail");
		}else if(EVENT_PREPARE_CREATE_CURRENT_ASSET.equals(event)||EVENT_EDIT_PREPARE_CURRENT_ASSET.equals(event)||EVENT_PREPARE_CREATE_CURRENT_ASSET_INSURENCE.equals(event)){
			aPage.setPageReference("prepare_create_current_asset"); 
		}
		else if(EVENT_PREPARE_CREATE_VALUE_DEBTORS.equals(event)||EVENT_EDIT_PREPARE_VALUE_DEBTORS.equals(event) ){
			aPage.setPageReference("prepare_create_value_debtors"); 
		}
		else if(EVENT_EDIT_PREPARE_CURRENT_ASSET_INSURANCE.equals(event)||EVENT_EDIT_CURRENT_ASSET_INSURANCE_ERROR.equals(event)){
			aPage.setPageReference("prepare_create_current_asset_ins");
		}else if(EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(event)||EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(event)){
			aPage.setPageReference("prepare_create_current_liabilities");
//		}else if(EVENT_PREPARE_CREATE_CURRENT_LIABILITIES.equals(event)||EVENT_EDIT_PREPARE_CURRENT_LIABILITIES.equals(event)){
//			aPage.setPageReference("prepare_create_current_liabilities");
		}
		else if(EVENT_PREPARE_CREATE_LESS_VALUE_ADVANCES.equals(event)||EVENT_EDIT_PREPARE_LESS_VALUE_ADVANCES.equals(event)){
			aPage.setPageReference("prepare_create_less_value_advances");
		}
		else if((EVENT_EDIT_CURRENT_ASSET.equals(event)||EVENT_EDIT_CURRENT_LIABILITIES.equals(event) ||EVENT_EDIT_VALUE_DEBTORS.equals(event) ||EVENT_EDIT_LESS_VALUE_ADVANCES.equals(event)) 
					&& fromPage!=null && "update_stock_detail".equals(fromPage)){
			aPage.setPageReference("edit_prepare_stock_details");
		}else if(EVENT_EDIT_CURRENT_ASSET.equals(event)||EVENT_EDIT_CURRENT_LIABILITIES.equals(event)  ||EVENT_EDIT_VALUE_DEBTORS.equals(event) ||EVENT_EDIT_LESS_VALUE_ADVANCES.equals(event)){
			aPage.setPageReference("stockDetail");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES.equals(event) && fromPage!=null && "update_stock_detail".equals(fromPage)){
			aPage.setPageReference("edit_prepare_stock_details");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES.equals(event) && fromPage!=null && "view_stock_detail".equals(fromPage)){
			aPage.setPageReference("view_stock_details");
		}/*else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES.equals(event) && fromPage!=null && "view_stock_detail".equals(fromPage)){
			aPage.setPageReference("view_stock_details");
		}*/else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CLOSE.equals(event) && fromPage!=null && "view_stock_detail_close".equals(fromPage)){
			aPage.setPageReference("view_stock_details");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CHECK.equals(event) && fromPage!=null && "view_stock_detail_check".equals(fromPage)){
			aPage.setPageReference("view_stock_details");
		}else if(EVENT_EDIT_CURRENT_ASSET_INSURANCE.equals(event)){
			aPage.setPageReference("view_stock_details");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES.equals(event)){
			aPage.setPageReference("stockDetail");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CLOSE.equals(event)){
			aPage.setPageReference("stockDetail_view");
		}else if(EVENT_CANCEL_CURRENT_ASSET_LIABILITIES_CHECK.equals(event)){
			aPage.setPageReference("stockDetail_view");
		}else if(EVENT_EDIT_STOCK_DETAILS_ERROR.equals(event) || EVENT_EDIT_PREPARE_CURRENT_ERROR.equals(event)) {
			aPage.setPageReference(EVENT_EDIT_PREPARE_STOCK_DETAILS);
		}else {
			aPage.setPageReference(event);
		}
		return aPage;
		
	}
}
