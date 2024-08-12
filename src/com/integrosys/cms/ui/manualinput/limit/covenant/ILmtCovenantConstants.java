package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.List;

import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.ui.manualinput.line.covenant.LineCovenantDetailMapper;

public interface ILmtCovenantConstants {
	
	public static final String EVENT_PREPARE_CREATE_COVENANT_DETAIL = "prepare_create_covenant_detail";
	public static final String EVENT_CREATE_COVENANT_DETAIL = "create_covenant_detail";
	public static final String EVENT_CREATE_COVENANT_DETAIL_ERROR = "create_covenant_detail_error";
	public static final String EVENT_CANCEL_COVENANT_DETAIL = "cancel_covenant_detail";
	public static final String EVENT_PREPARE_EDIT_COVENANT_DETAIL = "prepare_edit_covenant_detail";
	public static final String EVENT_EDIT_COVENANT_DETAIL = "edit_covenant_detail";
	public static final String EVENT_VIEW_COVENANT_DETAIL = "view_covenant_detail";
	public static final String EVENT_OK_COVENANT_DETAIL = "ok_covenant_detail";
	public static final String EVENT_OK_COVENANT_DETAIL_PROCESS = "ok_covenant_detail_process";
	public static final String EVENT_OK_COVENANT_DETAIL_UPDATE_STATUS = "ok_covenant_detail_update_status";
	public static final String EVENT_OK_COVENANT_DETAIL_PREPARE_CLOSE = "ok_covenant_detail_prepare_close";
	public static final String EVENT_OK_COVENANT_DETAIL_PREPARE_DELETE = "ok_covenant_detail_prepare_delete";
	public static final String EVENT_OK_COVENANT_DETAIL_PROCESS_DELETE = "ok_covenant_detail_process_delete";
	public static final String EVENT_OK_COVENANT_DETAIL_CLOSE = "ok_covenant_detail_close";
	public static final String EVENT_OK_COVENANT_DETAIL_REOPEN = "ok_covenant_detail_reopen";
	public static final String EVENT_ADD_DRAWER_REST = "add_drawer_rest";
	public static final String EVENT_ADD_DRAWEE_REST = "add_drawee_rest";
	public static final String EVENT_ADD_BENE_REST = "add_bene_rest";
	public static final String EVENT_ADD_COUNTRY_REST = "add_country_rest";
	public static final String EVENT_ADD_CURRENCY_REST = "add_currency_rest";
	public static final String EVENT_ADD_BANK_REST = "add_bank_rest";
	public static final String EVENT_DEL_COUNTRY_REST = "remove_country_rest";
	public static final String EVENT_DEL_CURRENCY_REST = "remove_currency_rest";
	public static final String EVENT_DEL_BANK_REST = "remove_bank_rest";
	public static final String EVENT_DEL_DRAWER_REST = "remove_drawer_rest";
	public static final String EVENT_DEL_DRAWEE_REST = "remove_drawee_rest";
	public static final String EVENT_DEL_BENE_REST = "remove_bene_rest";
	public static final String EVENT_EDIT_DRAWER_REST = "edit_drawer_rest";
	public static final String EVENT_EDIT_DRAWEE_REST = "edit_drawee_rest";
	public static final String EVENT_EDIT_BENE_REST = "edit_bene_rest";
	public static final String EVENT_PREPARE_EDIT_BENE_REST = "prepare_edit_bene_rest";
	public static final String EVENT_PREPARE_EDIT_DRAWEE_REST = "prepare_edit_drawee_rest";
	public static final String EVENT_PREPARE_EDIT_DRAWER_REST = "prepare_edit_drawer_rest";
	public static final String EVENT_REFRESH_INCO_DESC = "refresh_inco_desc";
	public static final String EVENT_REFRESH_GOODS_MASTER = "refresh_goods_master";
	public static final String EVENT_ADD_GOODS_RESTRICTION = "add_goods_restriction";
	String EVENT_DELETE_GOODS_RESTRICTION = "delete_goods_restriction";
	
	
	String COVENANT_DETAIL_FORM = "form.covenantDetailObj";
	String COVENANT_LINE_DETAIL_FORM = "form.lineCovenantDetailObj";
	String COVENANT_DETAIL_MAPPER = LmtCovenantDetailMapper.class.getName();
	String COVENANT_LINE_DETAIL_MAPPER = LineCovenantDetailMapper.class.getName();

	
    String MAXIMUM_ALLOWED_AMOUNT_24_4_STR = "99999999999999999999.9999";
    double MAXIMUM_ALLOWED_AMOUNT_24_4 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_24_4_STR);
    
    String MAXIMUM_ALLOWED_AMOUNT_22_2_STR = "99999999999999999999.99";
    double MAXIMUM_ALLOWED_AMOUNT_22_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_22_2_STR);
    
    String SESSION_COVENANT_GOODS_RESTRICTION_LIST = "sessionCovenantGoodsRestrictionList";

    String SESSION_DROPDOWN_COUNTRY_LIST = "countryList";
    String SESSION_DROPDOWN_CURRENCY_LIST = "currList";
    String SESSION_DROPDOWN_BANK_RESTRICTION_LIST = "bankList";
    String SESSION_DROPDOWN_GOODS_PARENT_LIST = "goodsParentList";
    
    String COUNTRY_RESTRICTION="CountryRestriction";
    String CURRENCY_RESTRICTION="CurrencyRestriction";
    String BANK_RESTRICTION="BankRestriction";
    String GOODS_RESTRICTION="GoodsRestriction";
    String DRAWEE_RESTRICTION="DraweeRestriction";
    String DRAWER_RESTRICTION="DrawerRestriction";
    String BENE_RESTRICTION="BeneRestriction";
    
    String SINGLE_COV_FOR_LINE="singleCovForLine";
    
    String REST_COUNTRY_ADD_LIST_FOR_LINE="restCountryAddListForLine";
    String REST_CURRENCY_ADD_LIST_FOR_LINE="restCurrencyAddListForLine";
    String REST_BANK_ADD_LIST_FOR_LINE="restBankAddListForLine";
    String REST_DRAWER_ADD_LIST_FOR_LINE="restDrawerAddListForLine";
    String REST_DRAWEE_ADD_LIST_FOR_LINE="restDraweeAddListForLine";
    String REST_BENE_ADD_LIST_FOR_LINE="restBeneAddListForLine";
    String REST_GOODS_ADD_LIST_FOR_LINE="restGoodsAddListForLine";
    
    String REST_COUNTRY_DELETE_LIST_FOR_LINE="restCountryDeleteListForLine";
    String REST_CURRENCY_DELETE_LIST_FOR_LINE="restCurrencyDeleteListForLine";
    String REST_BANK_DELETE_LIST_FOR_LINE="restBankDeleteListForLine";
    String REST_DRAWER_DELETE_LIST_FOR_LINE="restDrawerDeleteListForLine";
    String REST_DRAWEE_DELETE_LIST_FOR_LINE="restDraweeDeleteListForLine";
    String REST_BENE_DELETE_LIST_FOR_LINE="restBeneDeleteListForLine";
    String REST_GOODS_DELETE_LIST_FOR_LINE="restGoodsDeleteListForLine";
    
}
