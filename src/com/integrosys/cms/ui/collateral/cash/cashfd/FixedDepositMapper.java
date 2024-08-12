package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.OBCashDeposit;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.UIUtil;

public class FixedDepositMapper extends AbstractCommonMapper {

    public Object mapFormToOB(CommonForm cForm, HashMap inputs) throws MapperException {

        FixedDepositForm aForm = (FixedDepositForm) cForm;
        /**
        IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
        IGeneralParamEntry generalParamEntries= generalParamDao.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
		Date  applicationDate= new Date(generalParamEntries.getParamValue());
		aForm.setMaker_date(applicationDate.toString());
        */
        Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ICashCollateral iCash = (ICashCollateral) (((ICollateralTrxValue) inputs.get("serviceColObj"))
                .getStagingCollateral());
        ICashDeposit[] obDeposit = iCash.getDepositInfo();

        DefaultLogger.debug(this, "indexID  is:" + inputs.get("indexID") + ":");

        int index = Integer.parseInt((String) inputs.get("indexID"));
        ICashDeposit obToChange = null;
        int k = index;
        if(iCash.getDepositInfo().length-1 < index)
        {
          k = index-1;
          if(k== -1)
          {
          index = k;
          }
        }
        if (index == -1) {
            obToChange = new OBCashDeposit();
        } else {
            try {
                obToChange = (ICashDeposit) AccessorUtil.deepClone(obDeposit[k]);
            } catch (Exception e) {
                DefaultLogger.debug(this, e.getMessage());
            }
        }

        boolean isChanged = false;
        try {
        	if(iCash.getDepositInfo().length-1< index)
        	{
        		 obToChange.setRefID(ICMSConstant.LONG_MIN_VALUE);
        	}
            obToChange.setThirdPartyBank(aForm.getThirdPartyBank());

            // obToChange.setAccountType(aForm.getAccountType());
            obToChange.setAccountTypeNum(CategoryCodeConstant.FD_ACC_TYPE);

            obToChange.setAccountTypeValue(aForm.getAccountTypeValue());

            obToChange.setDepositReceiptNo(aForm.getDepositReceiptNo());

            obToChange.setDepositRefNo(aForm.getDepositRefNo());

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getFdrRate())) {
                obToChange.setFdrRate(Double.parseDouble(aForm.getFdrRate()));
            }

            obToChange.setDepositMaturityDate(UIUtil.mapFormString_OBDate(locale, obToChange.getDepositMaturityDate(),
                    aForm.getDepMatDate()));

            obToChange.setIssueDate(UIUtil
                    .mapFormString_OBDate(locale, obToChange.getIssueDate(), aForm.getIssueDate()));

            if(StringUtils.isEmpty(aForm.getDepCurr()))
                obToChange.setDepositCcyCode("MYR");
            else
                obToChange.setDepositCcyCode(aForm.getDepCurr());

            if (!AbstractCommonMapper.isEmptyOrNull(aForm.getTenure())) {
                obToChange.setTenure(Integer.parseInt(aForm.getTenure()));
            } else {
                obToChange.setTenure(ICMSConstant.INT_INVALID_VALUE);
            }

            obToChange.setTenureUOM(aForm.getTenureUOM());
            if (aForm.getOwnBank().equals(ICMSConstant.TRUE_VALUE)) {
                obToChange.setOwnBank(true);
            } else {
                obToChange.setOwnBank(false);
            }

            obToChange.setGroupAccountNumber(aForm.getGroupAccountNumber());

            DefaultLogger.debug(this, "deposit currency code: " + obToChange.getDepositCcyCode());
            if (isEmptyOrNull(aForm.getDepAmt())) {
                if (obToChange.getDepositAmount() != null) {
                    isChanged = true;
                }
                obToChange.setDepositAmount(null);
            } else {
                Amount amt = CurrencyManager.convertToAmount(locale, obToChange.getDepositCcyCode(), aForm.getDepAmt());
                if (obToChange.getDepositAmount() == null) {
                    isChanged = true;
                } else if (obToChange.getDepositAmount().getAmount() != amt.getAmount()) {
                    isChanged = true;
                }
                obToChange.setDepositAmount(amt);
            }
            if(aForm.getVerificationDate() != null)
            {
            obToChange.setVerificationDate(UIUtil.mapFormString_OBDate(locale, obToChange.getVerificationDate(),
                    aForm.getVerificationDate()));
            }
            //obToChange.setFdLienPercentage(Double.parseDouble(aForm.getFdLinePercentage()));
            obToChange.setDepositeInterestRate(Double.parseDouble(aForm.getDepositeInterestRate()));
            obToChange.setDepositorName(aForm.getDepositorName());
           // iCash.setCurrencyCode("IN");
            
            obToChange.setSystemName(aForm.getSystemName());
            obToChange.setSystemId(aForm.getSystemId());
            obToChange.setCustomerId(aForm.getCustomerId());
            obToChange.setFinwareId(aForm.getFinwareId());
            obToChange.setActive(aForm.getActive());
            obToChange.setMaker_id(aForm.getMaker_id());
            obToChange.setFlag("E");
            obToChange.setSearchFlag("Y");
                   	 	
            //new -date
            IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
 			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
 			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
 			Date date_app=new Date();
 			for(int i=0;i<generalParamEntries.length;i++){
 				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
 					date_app=new Date(generalParamEntries[i].getParamValue());
 				}
 			}
 			aForm.setMaker_date(date_app.toString().substring(0,10)+" "+new java.util.Date().toString().substring(11));
            // --->> end new-date
            DateFormat formatter  = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date maker_date = (Date)formatter.parse(aForm.getMaker_date());
            obToChange.setMaker_date(maker_date);
        }
        catch (Exception e) {
            DefaultLogger.debug(this, "error is :" + e.toString());
            throw new MapperException(e.getMessage());
        }

        HashMap returnMap = new HashMap();
        returnMap.put("deposit", obToChange);
        returnMap.put("isChanged", String.valueOf(isChanged));
        return returnMap;

    }

    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap inputs) throws MapperException {
        ICashDeposit iDeposit = (ICashDeposit) obj;

        FixedDepositForm aForm = (FixedDepositForm) cForm;
        Locale locale = (Locale) inputs.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

        if ("Y".equals(iDeposit.getThirdPartyBank())) {
            String display = "Yes";
            aForm.setThirdPartyBank(display);
        } else if ("N".equals(iDeposit.getThirdPartyBank())) {
            String display = "No";
            aForm.setThirdPartyBank(display);
        }
        aForm.setCashDepositID(String.valueOf(iDeposit.getCashDepositID())); 
        aForm.setDepositRefNo(iDeposit.getDepositRefNo());
        aForm.setFdrRate(String.valueOf(iDeposit.getFdrRate()));
        aForm.setDepositReceiptNo(iDeposit.getDepositReceiptNo());
        aForm.setDepAmt(UIUtil.mapOBAmount_FormString(locale, iDeposit.getDepositAmount()));
        aForm.setDepMatDate(UIUtil.mapOBDate_FormString(locale, iDeposit.getDepositMaturityDate()));
        aForm.setDepCurr(iDeposit.getDepositCcyCode());
        aForm.setIssueDate(UIUtil.mapOBDate_FormString(locale, iDeposit.getIssueDate()));

        if (iDeposit.getTenure() != ICMSConstant.INT_INVALID_VALUE) {
            aForm.setTenure("" + iDeposit.getTenure());
        }
        aForm.setTenureUOM(iDeposit.getTenureUOM());
        if (iDeposit.getOwnBank()) {
            aForm.setOwnBank(ICMSConstant.TRUE_VALUE);
        } else {
            aForm.setOwnBank(ICMSConstant.FALSE_VALUE);
        }
        //aForm.setGroupAccountNumber(iDeposit.getGroupAccountNumber());

       //  aForm.setFdLinePercentage(Double.toString(iDeposit.getFdLienPercentage()));
        aForm.setDepositeInterestRate(Double.toString(iDeposit.getDepositeInterestRate()));
        if(iDeposit.getVerificationDate() != null)
        {
        aForm.setVerificationDate(UIUtil.mapOBDate_FormString(locale, iDeposit.getVerificationDate()));
        }
        aForm.setDepositorName(iDeposit.getDepositorName());
        
        aForm.setSystemName(iDeposit.getSystemName());
        aForm.setSystemId(iDeposit.getSystemId());
        aForm.setCustomerId(iDeposit.getCustomerId());
        aForm.setFinwareId(iDeposit.getFinwareId());
        aForm.setActive(iDeposit.getActive());
        aForm.setSearchFlag(iDeposit.getSearchFlag());

        return aForm;
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{{"serviceColObj", "java.lang.Object", SERVICE_SCOPE},
                {"indexID", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},});
    }
}
