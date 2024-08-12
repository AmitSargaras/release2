/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genpscc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate;
import com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificateItem;
import com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.16 $
 * @since $Date: 2006/10/05 07:30:35 $ Tag: $Name: $
 */

public class CertMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public CertMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"cert", "com.integrosys.cms.app.sccertificate.bus.IPartialSCCertificate", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {"certTrxVal", "com.integrosys.cms.app.sccertificate.trx.IPartialSCCertificateTrxValue", SERVICE_SCOPE},});
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values
     * and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        IPartialSCCertificate temp = (IPartialSCCertificate) map.get("cert");
        if (temp == null) {
            return null; // throw new
            // MapperException("SCCCertificate Session value is null"
            // );
        }
        GeneratePSCCForm aForm = (GeneratePSCCForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        try {
            String partIndex = aForm.getParSccIssuedIndex();
            DefaultLogger.debug(this, "partrial  Items to remove" + partIndex);
            StringTokenizer st = new StringTokenizer(partIndex, ",");
            HashMap tempMap = new HashMap();
            while (st.hasMoreTokens()) {
                tempMap.put(st.nextToken(), null);
            }
            // DefaultLogger.debug(this,"Hash Map"+tempMap);

            DefaultLogger.debug(this, "Locale " + locale);

            //et reopen this for ABCLIMS

            //Some knowledge before continue from this point beyond..
            //SCC Limit Amount to be Activated == Disbursement Amount
            //Limit Maturiry Date == Expiry of Availability Period

//            if (aForm.getActLimit() != null && aForm.getActLimit().length > 0) {
//                IPartialSCCertificateItem items[] = temp.getPartialSCCItemList();
//
//                String actLimit[] = aForm.getActLimit();
//                String actAmtCurrCode[] = aForm.getActAmtCurrCode();
//                String maturityDate[] = aForm.getMaturityDate();
//
//                for (int i = 0; i < actLimit.length; i++) {
//                    DefaultLogger.debug(this, "SCC certificate Object" + items[i]);
//                    /****
//                     * Amount tempAmt = items[i].getActivatedAmount(); //
//                     * DefaultLogger.debug(this,"Activated Amount"+tempAmt); String
//                     * ccCode = items[i].getApprovedLimitAmount().getCurrencyCode();
//                     * if(tempAmt!=null && tempAmt.getCurrencyCode() != null) ccCode =
//                     * tempAmt.getCurrencyCode();
//                     ****/
//                    Amount newAmt = CurrencyManager.convertToAmount(locale, actAmtCurrCode[i], actLimit[i]);
//                    temp.getPartialSCCItemList()[i].setActivatedAmount(newAmt);
//                    temp.getPartialSCCItemList()[i].setMaturityDate(DateUtil.convertDate(locale, maturityDate[i]));
//                    if (tempMap.containsKey(String.valueOf(i))) {
//                        DefaultLogger.debug(this, "setting the partial falsg" + true);
//                        temp.getPartialSCCItemList()[i].setIsPartialSCCIssued(true);
//                    } else {
//                        temp.getPartialSCCItemList()[i].setIsPartialSCCIssued(false);
//                    }
//                }
//            }

            if ((aForm.getDistbursementAmt() != null && aForm.getDistbursementAmt().length > 0) &&
                (aForm.getAmtEnforceTodate() != null && aForm.getAmtEnforceTodate().length > 0)) {
                IPartialSCCertificateItem items[] = temp.getPartialSCCItemList();

                String distAmt[] = aForm.getDistbursementAmt();
                String distAmtCurrCode[] = aForm.getDistbursementAmtCurrCode();
                String expAvalDT[] = aForm.getExpiryAvailabilityDate();
                String amtEnforce[] = aForm.getAmtEnforceTodate();
                String amtEnforceCurrCode[] = aForm.getAmtEnforceTodateCurrCode();
                String paymntInstruc[] = aForm.getPaymentInstruc();
                
                //String actLimit[] = aForm.getActLimit();
                //String actAmtCurrCode[] = aForm.getActAmtCurrCode();
                //String maturityDate[] = aForm.getMaturityDate();

                for (int i = 0; i < items.length; i++) {
                    DefaultLogger.debug(this, "SCC certificate Object" + items[i]);
                    /****
                     * Amount tempAmt = items[i].getActivatedAmount(); //
                     * DefaultLogger.debug(this,"Activated Amount"+tempAmt); String
                     * ccCode = items[i].getApprovedLimitAmount().getCurrencyCode();
                     * if(tempAmt!=null && tempAmt.getCurrencyCode() != null) ccCode =
                     * tempAmt.getCurrencyCode();
                     ****/
                    Amount newAmt = (distAmt[i] != null && distAmt[i].trim().length() > 0) ? CurrencyManager.convertToAmount(locale, distAmtCurrCode[i], distAmt[i]) : null;
                    items[i].setDisbursementAmount(newAmt);
                    //temp.getPartialSCCItemList()[i].setActivatedAmount(newAmt);
                    //temp.getPartialSCCItemList()[i].setMaturityDate(DateUtil.convertDate(locale, maturityDate[i]));
                    items[i].setExpiryAvailabilityDate((expAvalDT[i] != null & expAvalDT[i].trim().length() > 0) ? DateUtil.convertDate(locale, expAvalDT[i]) : null);
                    newAmt = (amtEnforce[i] != null && amtEnforce[i].trim().length() > 0) ? CurrencyManager.convertToAmount(locale, amtEnforceCurrCode[i], amtEnforce[i]) : null;
                    items[i].setEnforceAmount(newAmt);
                    items[i].setPaymentInstruction(paymntInstruc[i]);
                    if (tempMap.containsKey(String.valueOf(i))) {
                        DefaultLogger.debug(this, "setting the partial falsg" + true);
                        items[i].setIsPartialSCCIssued(true);
                    } else {
                        items[i].setIsPartialSCCIssued(false);
                    }
                }
            }

            /*  Let forget about the clean and not clean things..
            if (((aForm.getCleanActLimit() != null) && (aForm.getCleanActLimit().length > 0))) {
                String cleanActLimit[] = aForm.getCleanActLimit();
                String cleanActAmtCurrCode[] = aForm.getCleanActAmtCurrCode();
                String cleanMaturityDate[] = aForm.getCleanMaturityDate();

                for (int i = 0; i < cleanActLimit.length; i++) {
                    Amount newAmt = CurrencyManager.convertToAmount(locale, cleanActAmtCurrCode[i], cleanActLimit[i]);
                    temp.getCleanPSCCItemList()[i].setMaturityDate(DateUtil.convertDate(locale, cleanMaturityDate[i]));
                    temp.getCleanPSCCItemList()[i].setActivatedAmount(newAmt);
                    temp.getCleanPSCCItemList()[i].setIsPartialSCCIssued(tempMap.containsKey(String.valueOf("clean" + i)));
                }
            }

            if (((aForm.getNotCleanActLimit() != null) && (aForm.getNotCleanActLimit().length > 0))) {
                String notCleanActLimit[] = aForm.getNotCleanActLimit();
                String notCleanActAmtCurrCode[] = aForm.getNotCleanActAmtCurrCode();
                String notCleanMaturityDate[] = aForm.getNotCleanMaturityDate();

                for (int i = 0; i < notCleanActLimit.length; i++) {
                    Amount newAmt = CurrencyManager.convertToAmount(locale, notCleanActAmtCurrCode[i], notCleanActLimit[i]);
                    temp.getNotCleanPSCCItemList()[i].setMaturityDate(DateUtil.convertDate(locale, notCleanMaturityDate[i]));
                    temp.getNotCleanPSCCItemList()[i].setActivatedAmount(newAmt);
                    temp.getNotCleanPSCCItemList()[i].setIsPartialSCCIssued(tempMap.containsKey(String.valueOf("notClean" + i)));
                }
            }
            */
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new MapperException(e.getMessage());
        }
        //temp.setCreditOfficerName(aForm.getCreditOfficerName());
        //temp.setCreditOfficerSignNo(aForm.getCreditOfficerSgnNo());
        //temp.setSeniorOfficerName(aForm.getSeniorCreditOfficerName());
        //temp.setSeniorOfficerSignNo(aForm.getSeniorCreditOfficerSgnNo());
        //temp.setCreditOfficerLocation(new OBBookingLocation(aForm.getCreditOfficerLocationCountry(), aForm.getCreditOfficerLocationOrgCode()));
        //temp.setSeniorOfficerLocation(new OBBookingLocation(aForm.getSeniorCreditOfficerLocationCountry(), aForm.getSeniorCreditOfficerLocationOrgCode()));
        //temp.setRemarks(aForm.getRemarksPSCC());
        temp.setRemarks(aForm.getRemarks());             

        //Some knowledge before continue from this point beyond..
        //Credit Officer == Authorized Personnel 1
        //Senior Credit Officer == Authorized Personnel 2
        temp.setCreditOfficerName(aForm.getAuthorPerOneName());
        temp.setCreditOfficerSignNo(aForm.getAuthorPerOneSignNo());
        temp.setSeniorOfficerName(aForm.getAuthorPerTwoName());
        temp.setSeniorOfficerSignNo(aForm.getAuthorPerTwoSignNo());
        temp.setHasCheck1((aForm.getHasCheck1() != null && aForm.getHasCheck1().trim().length() > 0) ? aForm.getHasCheck1().charAt(0) : 'N');
        temp.setHasCheck2((aForm.getHasCheck2() != null && aForm.getHasCheck2().trim().length() > 0) ? aForm.getHasCheck2().charAt(0) : 'N');
        temp.setHasCheck3((aForm.getHasCheck3() != null && aForm.getHasCheck3().trim().length() > 0) ? aForm.getHasCheck3().charAt(0) : 'N');
        temp.setHasCheck4((aForm.getHasCheck4() != null && aForm.getHasCheck4().trim().length() > 0) ? aForm.getHasCheck4().charAt(0) : 'N');
        temp.setInsuredWith(aForm.getInsuredWith());
        temp.setInsuredWithAmt((aForm.getInsuredWithAmt() != null && aForm.getInsuredWithAmt().trim().length() > 0) ? new BigDecimal(aForm.getInsuredWithAmt()) : null);
        temp.setAmbm(aForm.getAmbm());
        temp.setExpiry((aForm.getExpiry() != null && aForm.getExpiry().trim().length() > 0) ? DateUtil.convertDate(locale, aForm.getExpiry()) : null);
        temp.setSinkFundAmt((aForm.getSinkFundAmt() != null && aForm.getSinkFundAmt().trim().length() > 0) ? new BigDecimal(aForm.getSinkFundAmt()) : null);
        temp.setPmForPeriodOf(aForm.getPmForPeriodOf());
        temp.setCommencingFrom(aForm.getCommencingFrom());
        temp.setFundReach((aForm.getFundReach() != null && aForm.getFundReach().trim().length() > 0) ? new BigDecimal(aForm.getFundReach()) : null);
        temp.setFeesAmt((aForm.getFeesAmt() != null && aForm.getFeesAmt().trim().length() > 0) ? new BigDecimal(aForm.getFeesAmt()) : null);
        temp.setOthers(aForm.getOthers());
        return temp;
    }

    /**
     * This method is used to map data from OB to the form and to return the
     * form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOb to form ");
        GeneratePSCCForm aForm = (GeneratePSCCForm) cForm;
        // DefaultLogger.debug(this,"Form "+aForm);
        IPartialSCCertificate cert = (IPartialSCCertificate) obj;
        IPartialSCCertificateTrxValue certTrxVal = (IPartialSCCertificateTrxValue) map.get("certTrxVal");
        // DefaultLogger.debug(this,"Certificate  "+cert);
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        DefaultLogger.debug(this, "Locale" + locale);
        try {
            if (cert != null) {
                //aForm.setCreditOfficerName(cert.getCreditOfficerName());
                //aForm.setCreditOfficerSgnNo(cert.getCreditOfficerSignNo());
                //aForm.setSeniorCreditOfficerName(cert.getSeniorOfficerName());
                //aForm.setSeniorCreditOfficerSgnNo(cert.getSeniorOfficerSignNo());
                aForm.setAuthorPerOneName(cert.getCreditOfficerName() == null ? "" : cert.getCreditOfficerName());
                aForm.setAuthorPerOneSignNo(cert.getCreditOfficerSignNo() == null ? "" : cert.getCreditOfficerSignNo());
                aForm.setAuthorPerTwoName(cert.getSeniorOfficerName() == null ? "" : cert.getSeniorOfficerName());
                aForm.setAuthorPerTwoSignNo(cert.getSeniorOfficerSignNo() == null ? "" : cert.getSeniorOfficerSignNo());
                aForm.setHasCheck1(String.valueOf(cert.getHasCheck1()).trim());
                aForm.setHasCheck2(String.valueOf(cert.getHasCheck2()).trim());
                aForm.setHasCheck3(String.valueOf(cert.getHasCheck3()).trim());
                aForm.setHasCheck4(String.valueOf(cert.getHasCheck4()).trim());
                aForm.setInsuredWith(cert.getInsuredWith() == null ? "" : cert.getInsuredWith());
                aForm.setInsuredWithAmt(cert.getInsuredWithAmt() == null ? "" : cert.getInsuredWithAmt() + "");
                aForm.setAmbm(cert.getAmbm() == null ? "" : cert.getAmbm());
                aForm.setExpiry(cert.getExpiry() == null ? "" : DateUtil.formatDate(locale, cert.getExpiry()));
                aForm.setSinkFundAmt(cert.getSinkFundAmt() == null ? "" : cert.getSinkFundAmt() + "");
                aForm.setPmForPeriodOf(cert.getPmForPeriodOf() == null ? "" : cert.getPmForPeriodOf());
                aForm.setCommencingFrom(cert.getCommencingFrom() == null ? "" : cert.getCommencingFrom());
                aForm.setFundReach(cert.getFundReach() == null ? "" : cert.getFundReach() + "");
                aForm.setFeesAmt(cert.getFeesAmt() == null ? "" : cert.getFeesAmt() + "");
                aForm.setOthers(cert.getOthers() == null ? "" : cert.getOthers());

                /*
                if (cert.getCreditOfficerLocation() != null) {
                    aForm.setCreditOfficerLocationCountry(cert.getCreditOfficerLocation().getCountryCode());
                    aForm.setCreditOfficerLocationOrgCode(cert.getCreditOfficerLocation().getOrganisationCode());
                }
                if (cert.getSeniorOfficerLocation() != null) {
                    aForm.setSeniorCreditOfficerLocationCountry(cert.getSeniorOfficerLocation().getCountryCode());
                    aForm.setSeniorCreditOfficerLocationOrgCode(cert.getSeniorOfficerLocation().getOrganisationCode());
                }
                */
                
                //Commented out due to Changes by R1.5-CR146
                //aForm.setRemarksPSCC(cert.getRemarks());
                IPartialSCCertificateItem items[] = cert.getPartialSCCItemList();
                //DefaultLogger.debug(this, "Items &&&&&&&&&&&&&&&&&&&&&&&&&&" + items);

                String distAmt[] = null;
                String distAmtCurrCode[] = null;
                String expAvalDT[] = null;
                String amtEnforce[] = null;
                String amtEnforceCurrCode[] = null;
                String paymntInstruc[] = null;
                String isIssued = ""; 
                //String actAmt[] = null;
                //String actAmtCurrCode[] = null;
                //String maturityDate[] = null;

                if (items != null && items.length > 0) {

                    distAmt = new String[items.length];
                    distAmtCurrCode = new String[items.length];
                    expAvalDT = new String[items.length];
                    amtEnforce = new String[items.length];
                    amtEnforceCurrCode = new String[items.length];
                    paymntInstruc = new String[items.length];
                    //actAmt = new String[items.length];
                    //actAmtCurrCode = new String[items.length];
                    //maturityDate = new String[items.length];

                    for (int i = 0; i < items.length; i++) {
                        if (items[i].getDisbursementAmount() != null && items[i].getDisbursementAmount().getCurrencyCode() != null) {
                            distAmt[i] = CurrencyManager.convertToDisplayString(locale, items[i].getDisbursementAmount());
                            distAmtCurrCode[i] = items[i].getDisbursementAmount().getCurrencyCode();
                        } else {
                            distAmt[i] = "";
                            distAmtCurrCode[i] = "";
                        }
                        if (items[i].getExpiryAvailabilityDate() != null)
                            expAvalDT[i] = DateUtil.formatDate(locale, items[i].getExpiryAvailabilityDate());
                        else
                            expAvalDT[i] = "";
                        if (items[i].getEnforceAmount() != null && items[i].getEnforceAmount().getCurrencyCode() != null) {
                            amtEnforce[i] = CurrencyManager.convertToDisplayString(locale, items[i].getEnforceAmount());
                            amtEnforceCurrCode[i] = items[i].getEnforceAmount().getCurrencyCode();
                        } else {
                            amtEnforce[i] = "";
                            amtEnforceCurrCode[i] = "";
                        }
                        if (items[i].getPaymentInstruction() != null) {
                            paymntInstruc[i] = items[i].getPaymentInstruction();
                        } else {
                            paymntInstruc[i] = "";
                        }
                        if (items[i].getIsPartialSCCIssued()) {
                            isIssued += i + ",";
                        } else {
                            isIssued += "";
                        }
                        //maturityDate[i] = DateUtil.formatDate(locale, items[i].getMaturityDate());
                        //if (items[i].getActivatedAmount() != null && items[i].getActivatedAmount().getCurrencyCode() != null) {
                        //    actAmt[i] = CurrencyManager.convertToDisplayString(locale, items[i].getActivatedAmount());
                        //    actAmtCurrCode[i] = items[i].getApprovedLimitAmount().getCurrencyCode();
                        //}
                    }

                    //if (distAmt != null && distAmt.length > 0) {
                    //    for (int j = 0; j < distAmt.length; j++) {
                    //        if (distAmt[j] == null) distAmt[j] = "";
                    //    }
                    //}
                    //if (amtEnforce != null && amtEnforce.length > 0) {
                    //    for (int j = 0; j < amtEnforce.length; j++) {
                    //        if (amtEnforce[j] == null) amtEnforce[j] = "";
                    //    }
                    //}
                    //if (actAmt != null && actAmt.length > 0) {
                    //    for (int j = 0; j < actAmt.length; j++) {
                    //        if (actAmt[j] == null) actAmt[j] = "";
                    //    }
                    //}

                    aForm.setDistbursementAmt(distAmt);
                    aForm.setDistbursementAmtCurrCode(distAmtCurrCode);
                    aForm.setExpiryAvailabilityDate(expAvalDT);
                    aForm.setAmtEnforceTodate(amtEnforce);
                    aForm.setAmtEnforceTodateCurrCode(amtEnforceCurrCode);
                    aForm.setPaymentInstruc(paymntInstruc);
                    //aForm.setActLimit(actAmt);
                    //aForm.setActAmtCurrCode(actAmtCurrCode);
                    //aForm.setMaturityDate(maturityDate);
                }
                aForm.setParSccIssuedIndex(isIssued);                                                    

                /*
                IPartialSCCertificateItem cleanItems[] = cert.getCleanPSCCItemList();
                IPartialSCCertificateItem notCleanItems[] = cert.getNotCleanPSCCItemList();
                String cleanActAmt[] = null;
                String cleanActAmtCurrCode[] = null;
                String notCleanActAmt[] = null;
                String notCleanActAmtCurrCode[] = null;
                String cleanMaturityDate[] = null;
                String notCleanMaturityDate[] = null;
                String strCheckedIndexes = "";

                // Clean Items
                if ((cleanItems != null) && (cleanItems.length > 0)) {
                    cleanActAmt = new String[cleanItems.length];
                    cleanActAmtCurrCode = new String[cleanItems.length];
                    cleanMaturityDate = new String[cleanItems.length];
                    for (int i = 0; i < cleanItems.length; i++) {
                        cleanMaturityDate[i] = DateUtil.formatDate(locale, cleanItems[i].getMaturityDate());
                        if ((cleanItems[i].getActivatedAmount() != null)
                                && (cleanItems[i].getActivatedAmount().getCurrencyCode() != null)) {
                            cleanActAmt[i] = CurrencyManager.convertToDisplayString(locale, cleanItems[i]
                                    .getActivatedAmount());
                            cleanActAmtCurrCode[i] = cleanItems[i].getApprovedLimitAmount().getCurrencyCode();
                        }
                        strCheckedIndexes = (cleanItems[i].getIsPartialSCCIssued()) ? strCheckedIndexes + ",clean" + i
                                : strCheckedIndexes;

                    }
                    if ((cleanActAmt != null) && (cleanActAmt.length > 0)) {
                        for (int j = 0; j < cleanActAmt.length; j++) {
                            if (cleanActAmt[j] == null) {
                                cleanActAmt[j] = "";
                            }
                        }
                    }

                    aForm.setCleanActLimit(cleanActAmt);
                    aForm.setCleanActAmtCurrCode(cleanActAmtCurrCode);
                    aForm.setCleanMaturityDate(cleanMaturityDate);
                }

                // Not Clean (Secured) Items
                if ((notCleanItems != null) && (notCleanItems.length > 0)) {
                    notCleanActAmt = new String[notCleanItems.length];
                    notCleanActAmtCurrCode = new String[notCleanItems.length];
                    notCleanMaturityDate = new String[notCleanItems.length];
                    for (int i = 0; i < notCleanItems.length; i++) {
                        notCleanMaturityDate[i] = DateUtil.formatDate(locale, notCleanItems[i].getMaturityDate());
                        if ((notCleanItems[i].getActivatedAmount() != null)
                                && (notCleanItems[i].getActivatedAmount().getCurrencyCode() != null)) {
                            notCleanActAmt[i] = CurrencyManager.convertToDisplayString(locale, notCleanItems[i]
                                    .getActivatedAmount());
                            notCleanActAmtCurrCode[i] = notCleanItems[i].getApprovedLimitAmount().getCurrencyCode();
                        }
                        strCheckedIndexes = (notCleanItems[i].getIsPartialSCCIssued()) ? strCheckedIndexes
                                + ",notClean" + i : strCheckedIndexes;
                    }
                    if ((notCleanActAmt != null) && (notCleanActAmt.length > 0)) {
                        for (int j = 0; j < notCleanActAmt.length; j++) {
                            if (notCleanActAmt[j] == null) {
                                notCleanActAmt[j] = "";
                            }
                        }
                    }

                    aForm.setNotCleanActLimit(notCleanActAmt);
                    aForm.setNotCleanActAmtCurrCode(notCleanActAmtCurrCode);
                    aForm.setNotCleanMaturityDate(notCleanMaturityDate);
                }
                */
                //aForm.setParSccIssuedIndex(strCheckedIndexes);
            }

            aForm.setRemarks(certTrxVal.getRemarks());

        }
        catch (Exception e) {
            e.printStackTrace();
            DefaultLogger.error(this, e);
            throw new MapperException(e.getMessage());
        }
        DefaultLogger.debug(this, "Going out of mapOb to form ");
        return aForm;
    }

}
