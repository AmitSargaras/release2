package com.integrosys.cms.ui.manualinput.limit.covenant;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;
import com.integrosys.cms.ui.manualinput.limit.LmtDetailAction;
import com.integrosys.cms.ui.manualinput.limit.ReadLmtDetailCmd;
import com.integrosys.cms.ui.manualinput.limit.RefreshSancAmountCmd;

public class LmtCovenantDetailAction extends LmtDetailAction implements ILmtCovenantConstants {
	
	
	
	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = null;
		if(EVENT_PREPARE_CREATE_COVENANT_DETAIL.equals(event) || EVENT_PREPARE_EDIT_COVENANT_DETAIL.equals(event) ||
				EVENT_VIEW_COVENANT_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCovenantDetailCommand();
		}else if(EVENT_CANCEL_COVENANT_DETAIL.equals(event)||EVENT_OK_COVENANT_DETAIL.equals(event)
				||EVENT_OK_COVENANT_DETAIL_PROCESS.equals(event)
				||EVENT_OK_COVENANT_DETAIL_PREPARE_CLOSE.equals(event)
				||EVENT_OK_COVENANT_DETAIL_PREPARE_DELETE.equals(event)
				||EVENT_OK_COVENANT_DETAIL_PROCESS_DELETE.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new BackToManualInputLimitCommand();
		}else if(EVENT_CREATE_COVENANT_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new AddCovenantDetailCommand();
		}else if(EVENT_EDIT_COVENANT_DETAIL.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_COUNTRY_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddCountryListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_CURRENCY_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddCurrencyList();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_BANK_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddBankListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_DRAWER_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddDrawerListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_DRAWEE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddDraweeListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_ADD_BENE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddBeneListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();	
		}else if(EVENT_DEL_COUNTRY_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteCountryListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_DEL_CURRENCY_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteCurrencyListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_DEL_BANK_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteBankListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_DEL_DRAWER_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteDrawerListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_DEL_DRAWEE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteDraweeListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_DEL_BENE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteBeneListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_PREPARE_EDIT_BENE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new LoadEditBeneData();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_EDIT_BENE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditBeneListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_PREPARE_EDIT_DRAWEE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new LoadEditDraweeData();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_EDIT_DRAWEE_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditDraweeListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_PREPARE_EDIT_DRAWER_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new LoadEditDrawerData();
			objArray[1] = new PrepareCovenantDetailCommand();
		}else if(EVENT_EDIT_DRAWER_REST.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new EditDrawerListCommand();
			objArray[1] = new PrepareCovenantDetailCommand();	
		}else if (EVENT_REFRESH_INCO_DESC.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshIncoDescCmd();
		}
		else if (EVENT_REFRESH_GOODS_MASTER.equals(event)) {
			objArray = new ICommand[1];
			objArray[0] = new RefreshGoodsMasterCmd();
		}
		else if(EVENT_ADD_GOODS_RESTRICTION.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new AddGoodsRestrictionCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}
		else if(EVENT_DELETE_GOODS_RESTRICTION.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new DeleteGoodsRestrictionCommand();
			objArray[1] = new PrepareCovenantDetailCommand();
		}
			

		return objArray;
	}
	
	protected boolean isValidationRequired(String event) {
		boolean result = false;
		if(EVENT_CREATE_COVENANT_DETAIL.equals(event)) {
			result = true;
		}
		
		return result;
	}
	
	public ActionErrors validateInput(ActionForm aForm, Locale locale) {
		return LmtCovenantDetailValidator.validateInput( (LmtCovenantDetailForm) aForm, locale);
	}
	
	protected String getErrorEvent(String event) {
		String errorEvent = event;
		if (EVENT_CREATE_COVENANT_DETAIL.equals(event)) {
			errorEvent = EVENT_EDIT_COVENANT_DETAIL;
		}
		return errorEvent;
	}
	
	public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
		Page aPage = new Page();
		DefaultLogger.info(this, "Event is  " +event);
		if( EVENT_EDIT_COVENANT_DETAIL.equals(event) 
				|| EVENT_DEL_COUNTRY_REST.equals(event) || EVENT_ADD_COUNTRY_REST.equals(event)
				|| EVENT_DEL_BANK_REST.equals(event) || EVENT_ADD_BANK_REST.equals(event)
				|| EVENT_DEL_CURRENCY_REST.equals(event) || EVENT_ADD_CURRENCY_REST.equals(event)
				|| EVENT_DEL_DRAWER_REST.equals(event) || EVENT_ADD_DRAWER_REST.equals(event)
				|| EVENT_DEL_DRAWEE_REST.equals(event) || EVENT_ADD_DRAWEE_REST.equals(event)
				|| EVENT_DEL_BENE_REST.equals(event) || EVENT_ADD_BENE_REST.equals(event)
				|| EVENT_EDIT_DRAWEE_REST.equals(event) || EVENT_EDIT_DRAWER_REST.equals(event)
				|| EVENT_EDIT_BENE_REST.equals(event) || EVENT_PREPARE_EDIT_BENE_REST.equals(event)
				|| EVENT_EDIT_DRAWEE_REST.equals(event) || EVENT_PREPARE_EDIT_DRAWEE_REST.equals(event)
				|| EVENT_EDIT_DRAWER_REST.equals(event) || EVENT_PREPARE_EDIT_DRAWER_REST.equals(event) 
				|| EVENT_ADD_GOODS_RESTRICTION.equals(event) || EVENT_DELETE_GOODS_RESTRICTION.equals(event)) {
			aPage.setPageReference("update_covenant_page");
		}else if(EVENT_PREPARE_CREATE_COVENANT_DETAIL.equals(event) || EVENT_PREPARE_EDIT_COVENANT_DETAIL.equals(event) ||
				EVENT_VIEW_COVENANT_DETAIL.equals(event)) {
			aPage.setPageReference("read_covenant_page");
		}else if(EVENT_CANCEL_COVENANT_DETAIL.equals(event)){	
			aPage.setPageReference("cancel_covenant_page");
		}else if(EVENT_OK_COVENANT_DETAIL.equals(event)){	
			aPage.setPageReference("ok_covenant_page");
		}else if(EVENT_OK_COVENANT_DETAIL_PROCESS.equals(event)){	
			aPage.setPageReference("ok_covenant_page_process");	
		}else if(EVENT_OK_COVENANT_DETAIL_PREPARE_CLOSE.equals(event)){	
			aPage.setPageReference("ok_covenant_page_prepare_close");	
		}else if(EVENT_OK_COVENANT_DETAIL_PREPARE_DELETE.equals(event)){	
			aPage.setPageReference("ok_covenant_page_prepare_delete");	
		}else if(EVENT_OK_COVENANT_DETAIL_PROCESS_DELETE.equals(event)){	
			aPage.setPageReference("ok_covenant_page_process_delete");	
		}else if(EVENT_CREATE_COVENANT_DETAIL.equals(event)){
			aPage.setPageReference("add_covenant_page"); 
		}else if (EVENT_REFRESH_INCO_DESC.equals(event)) {
			aPage.setPageReference("refresh_inco_description"); 
		}else if (EVENT_REFRESH_GOODS_MASTER.equals(event)) {
			aPage.setPageReference("refresh_goods_master"); 
		}else {
			aPage.setPageReference(event);
		}
		return aPage;		
	}
	
	
}
