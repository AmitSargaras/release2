/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.limitbooking;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;
import com.integrosys.cms.app.limitbooking.bus.LimitBookingSearchCriteria;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBooking;
import com.integrosys.cms.app.limitbooking.bus.OBLimitBookingDetail;
import com.integrosys.cms.app.limitbooking.bus.OBLoanSectorDetail;
import com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue;
import com.integrosys.cms.app.custgrpi.bus.GroupMemberSearchCriteria;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.CommonCodeList;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * @author priya
 *
 */

public class LimitBookingMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public LimitBookingMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {ICommonEventConstant.EVENT, "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY,"java.util.Locale", GLOBAL_SCOPE},
                {LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING,"com.integrosys.cms.app.limitbooking.trx.ILimitBookingTrxValue",SERVICE_SCOPE}
            }
         );

    }
    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "******************** Inside Map Form to OB ");
        String event = (String)map.get(ICommonEventConstant.EVENT);
        Locale locale = (Locale)map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        LimitBookingForm aForm = (LimitBookingForm) cForm;

        if(LimitBookingAction.EVENT_SEARCH_GROUP.equals(event)){
            GroupMemberSearchCriteria cSearch = new GroupMemberSearchCriteria();
            if (aForm != null) {
                cSearch.setSearchType(aForm.getSearchType());
                cSearch.setGrpNo(aForm.getSearchGroupNo());
                cSearch.setGrpID(aForm.getSearchGroupID());
                cSearch.setGroupName(aForm.getSearchGroupName());
            }
            String nItemsStr = PropertyManager.getValue("customer.pagination.nitems");
            int nItems = 20;
            if (null != nItemsStr) {
                try {
                    nItems = Integer.parseInt(nItemsStr);
                } catch (NumberFormatException e) {
                    nItems = 20;
                }
            }
            if (aForm.getNumItems() > 10) {
                cSearch.setNItems(aForm.getNumItems());
            } else {
                cSearch.setNItems(nItems);
            }
            return cSearch;
        }
        else if(LimitBookingAction.EVENT_SEARCH_BOOKING.equals(event)){
            LimitBookingSearchCriteria cSearch = new LimitBookingSearchCriteria();
            if (aForm != null) {
                cSearch.setSearchType(aForm.getSearchBookingType());
                cSearch.setCustomerName(aForm.getSearchCustomerName());
                cSearch.setGobuttonBooking(aForm.getGobuttonBooking());
                cSearch.setGroupName(aForm.getSearchBookingGroupName());
                cSearch.setTicketNo(aForm.getSearchTicketNo());
                cSearch.setIdNo(aForm.getSearchIDNo());
                if (aForm.getSearchFromDate() != null)
                    cSearch.setSearchFromDate(UIUtil.mapFormString_OBDate(locale,cSearch.getSearchFromDate(),aForm.getSearchFromDate()));
                if (aForm.getSearchToDate() != null)
                    cSearch.setSearchToDate(UIUtil.mapFormString_OBDate(locale,cSearch.getSearchToDate(),aForm.getSearchToDate()));

            }
            
            String nItemsStr = PropertyManager.getValue("customer.pagination.nitems");
            int nItems = 20;
            if (null != nItemsStr) {
                try {
                    nItems = Integer.parseInt(nItemsStr);
                } catch (NumberFormatException e) {
                    nItems = 20;
                }
            }
            if (aForm.getNumItems() > 10) {
                cSearch.setNItems(aForm.getNumItems());
            } else {
                cSearch.setNItems(nItems);
            }
            
            return cSearch;
        }
        else if(LimitBookingAction.EVENT_ADD_POL.equals(event) || LimitBookingAction.EVENT_EDIT_POL.equals(event)){
            OBLoanSectorDetail lmtBkDtl = new OBLoanSectorDetail();
            lmtBkDtl.setBkgTypeCode(aForm.getPol());
            lmtBkDtl.setBkgTypeDesc(aForm.getPolBkgDesc());
            lmtBkDtl.setBkgProdTypeCode(aForm.getProdType());
            lmtBkDtl.setBkgProdTypeDesc(aForm.getProdTypeDesc());
            ArrayList busUnitLabelList = new ArrayList();
            CommonCodeList busUnit = CommonCodeList.getInstance(null, null, ICMSConstant.CATEGORY_CODE_ECONOMIC_SECTOR, null, true);
            busUnitLabelList.addAll((ArrayList)busUnit.getCommonCodeLabels());
            try {
                if (aForm.getPolBkgAmount() != null)
                    lmtBkDtl.setBkgAmount(CurrencyManager.convertToAmount(locale,aForm.getPolBkgCurrency(),aForm.getPolBkgAmount()));
            } catch (Exception e) {
                throw new MapperException("Error in converting to Amount");
            }
            return lmtBkDtl;
        }
        else if(LimitBookingAction.EVENT_REMOVE_POL_ADD.equals(event) || LimitBookingAction.EVENT_REMOVE_POL_EDIT.equals(event)){
            return aForm.getPolDeletedList();
        }
        else if(LimitBookingAction.EVENT_REMOVE_GROUP_ADD.equals(event) || LimitBookingAction.EVENT_REMOVE_GROUP_EDIT.equals(event)){
            return aForm.getBankGroupDeletedList();
        }
        else {
        	DefaultLogger.debug(this,"event = " + event);
            
            ILimitBooking limitBooking = null;
            
            if (event.equals(LimitBookingAction.EVENT_VIEW_RESULT_ADD)) {
            	limitBooking = new OBLimitBooking();
            }
            else {
            	ILimitBookingTrxValue trxValue = (ILimitBookingTrxValue)(map.get(LimitBookingAction.LIMIT_BOOKING_TRX_LIMIT_BOOKING));
            	if (trxValue != null) {
                	limitBooking = trxValue.getLimitBooking();
                }
                if (limitBooking == null) {
                	limitBooking = new OBLimitBooking();
                }   	
            }
            
            limitBooking.setAaNo(aForm.getAaNo());
            limitBooking.setAaSource(aForm.getAaSource());
            try {
                if (aForm.getBkgAmount() != null && !aForm.getBkgCurrency().equals(""))
                    limitBooking.setBkgAmount(CurrencyManager.convertToAmount(locale,aForm.getBkgCurrency(),aForm.getBkgAmount()));
            } catch (Exception e) {
                throw new MapperException("Error in converting to Amount");
            }
            limitBooking.setBkgCountry(aForm.getBkgCountry());
            limitBooking.setBkgIDNo(aForm.getBkgIDNo());
            limitBooking.setBkgName(aForm.getBkgName());
            limitBooking.setBkgBusUnit(aForm.getBkgBusUnit());
            limitBooking.setIsExistingCustomer(aForm.getIsExistingCustomer());
            limitBooking.setIsFinancialInstitution(aForm.getIsFinancialInstitution());
            limitBooking.setBkgBusSector(aForm.getBkgBusSector());
            limitBooking.setBkgBankEntity(aForm.getBkgBankEntity());
            return limitBooking;

        }
        
    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        try {
            DefaultLogger.debug(this, "******************** inside mapOb to form");
            LimitBookingForm aForm = (LimitBookingForm) cForm;
            String event = (String)map.get(ICommonEventConstant.EVENT);
            
            NumberFormat formatter = new DecimalFormat("#######.##");
            
            if (LimitBookingAction.EVENT_SEARCH_GROUP.equals(event)) {
            {
                GroupMemberSearchCriteria cSearch = (GroupMemberSearchCriteria) obj;
                if (aForm == null || cSearch == null) {
                    return aForm;
                }
                 aForm.setSearchType(cSearch.getSearchType());
                 aForm.setSearchGroupNo(cSearch.getGrpNo());
                 aForm.setSearchGroupID(cSearch.getGrpID());
                 aForm.setSearchGroupName(cSearch.getGroupName());
                 aForm.setNumItems(cSearch.getNItems());
                }
           }
            else if (LimitBookingAction.EVENT_PREPARE_ADD_POL_ADD.equals(event)) {
                   aForm.setPolBkgCurrency("MYR");
            }
            else if (LimitBookingAction.EVENT_PREPARE_EDIT_POL_ADD.equals(event) || LimitBookingAction.EVENT_PREPARE_EDIT_POL_EDIT.equals(event)) {
                OBLimitBookingDetail limitBookDtl = (OBLimitBookingDetail)obj;
                if (limitBookDtl == null) {
                    return aForm;
                }
                if (limitBookDtl.getBkgAmount() != null && limitBookDtl.getBkgAmount().getCurrencyCode() != null
                        && !"".equals(limitBookDtl.getBkgAmount().getCurrencyCode().trim())){
                    aForm.setPolBkgAmount(formatter.format(limitBookDtl.getBkgAmount().getAmount()));
                    aForm.setPolBkgCurrency(limitBookDtl.getBkgAmount().getCurrencyCode());
                }
                aForm.setPol(limitBookDtl.getBkgTypeCode());
                aForm.setProdType(limitBookDtl.getBkgProdTypeCode());
            }
            else if (LimitBookingAction.EVENT_SEARCH_BOOKING.equals(event)) {
                {
                	SearchResult cSearch = (SearchResult) obj;
                    if (aForm == null || cSearch == null) {
                        return aForm;
                    }

                    aForm.setCurrentIndex(cSearch.getCurrentIndex());
                    aForm.setNumItems(cSearch.getNItems());
                    aForm.setSearchResult(cSearch.getResultList());
                    }
               }
            else {
                OBLimitBooking limitBook = (OBLimitBooking)obj;
                try{
                } catch (Exception e) {
                    throw new MapperException(e.toString());
                }
                aForm.setAaNo(limitBook.getAaNo());
                aForm.setAaSource(limitBook.getAaSource());
                if (limitBook.getBkgAmount() != null && limitBook.getBkgAmount().getCurrencyCode() != null
                        && !"".equals(limitBook.getBkgAmount().getCurrencyCode().trim())) {
                	aForm.setBkgAmount(formatter.format(limitBook.getBkgAmount().getAmount()));
                	aForm.setBkgCurrency(limitBook.getBkgAmount().getCurrencyCode());
                } 
                else {
                	aForm.setBkgCurrency("MYR");
                }
                aForm.setBkgCountry(limitBook.getBkgCountry() == null ? "MY" : limitBook.getBkgCountry());
                aForm.setBkgIDNo(limitBook.getBkgIDNo());
                aForm.setBkgName(limitBook.getBkgName());
                aForm.setBkgBusUnit(limitBook.getBkgBusUnit());
                aForm.setIsExistingCustomer(limitBook.getIsExistingCustomer() == null ? "N" : limitBook.getIsExistingCustomer());
                aForm.setIsFinancialInstitution(limitBook.getIsFinancialInstitution() == null ? "N" : limitBook.getIsFinancialInstitution());
                aForm.setBkgBusSector(limitBook.getBkgBusSector());
                aForm.setBkgBankEntity(limitBook.getBkgBankEntity());
                
            }
            DefaultLogger.debug(this, "Going out of mapOb to form ");
            return aForm;
        } catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.error(this, "error in Limit Booking Mapper is" + e);
        }
        return null;
    }
}

