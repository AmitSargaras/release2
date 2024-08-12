/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.Date;
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
import com.integrosys.cms.app.ddn.bus.IDDN;
import com.integrosys.cms.app.ddn.bus.IDDNItem;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/10/05 07:30:34 $ Tag: $Name: $
 */

public class LADMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public LADMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"cert", "com.integrosys.cms.app.ddn.bus.IDDN", SERVICE_SCOPE},
                {IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ, "com.integrosys.cms.app.limit.bus.ILimitProfile", GLOBAL_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}});
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values
     * and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        IDDN temp = (IDDN) map.get("cert");
        //System.out.println("Inside DDNMapper, value of IDDN : " + temp);
        //System.out.println("Inside DDNMapper, value of GenerateDDNForm : " + cForm.toString());
        
//        IDDNItem securedItems[] = null;
//        IDDNItem unsecuredItems[] = null;
        IDDNItem ddnItems[] = null;
        ArrayList items = new ArrayList();
        if (temp == null) {
            return null;
        }
        // throw new MapperException("DDN Session value is null");
        GenerateLADForm aForm = (GenerateLADForm) cForm;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        if (temp != null) {
//            securedItems = temp.getCleanDDNItemList();
//            unsecuredItems = temp.getNotCleanDDNItemList();
            ddnItems = temp.getDDNItemList();
            // add the list of unsecured items into the items arraylist
//            for (int i = 0; (securedItems != null) && (i < securedItems.length); i++) {
//                items.add(securedItems[i]);
//            }
            // add the list of unsecured items into the items arraylist
//            for (int i = 0; (unsecuredItems != null) && (i < unsecuredItems.length); i++) {
//                items.add(unsecuredItems[i]);
//            }
            for (int i = 0; (ddnItems != null) && (i < ddnItems.length); i++) {
                items.add(ddnItems[i]);
            }
        }

        try {
//            String partIndex = aForm.getDdnIssuedIndex();
//            StringTokenizer st = new StringTokenizer(partIndex, ",");
//            HashMap tempMap = new HashMap();
//            while (st.hasMoreTokens()) {
//                String token = st.nextToken();
//                // DefaultLogger.debug(this,"token "+token);
//                // if(token.startsWith(ICMSConstant.LONG_INVALID_VALUE))
//                tempMap.put(token, "ooo");
//            }

//            if ((aForm.getDdnLimit() != null) && (aForm.getDdnLimit().length > 0)) {
//                // IDDNItem items[] = temp.getDDNItemList();
//                String actLimit[] = aForm.getDdnLimit();
//                String maturityDate[] = aForm.getMaturityDate();
//                if ((actLimit != null) && (actLimit.length > 0)) {
//                    for (int i = 0; i < actLimit.length; i++) {
//                        IDDNItem item = (IDDNItem) items.get(i);
//                        Amount tempAmt = item.getApprovedLimitAmount();
//
//                        if (tempMap.containsKey(String.valueOf(item.getDDNItemRef())
//                                + String.valueOf(item.getLimitID()))) {
//                            item.setIsDDNIssuedInd(true);
//                            // set the ddn amount into the selected items only
//                            if (tempAmt != null) {
//                                Amount newAmt = CurrencyManager.convertToAmount(locale, tempAmt.getCurrencyCode(),
//                                        actLimit[i]);
//                                item.setDDNAmount((newAmt));
//                            }
//                            item.setMaturityDate(DateUtil.convertDate(locale, maturityDate[i]));
//                        } else {
//                            item.setIsDDNIssuedInd(false);
//                            item.setDDNAmount(null);
//                        }
//                    }
//                }
//            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new MapperException(e.getMessage());
        }
        if (aForm != null) {
//            temp.setApprovalBy(aForm.getApprovedBy());
//            if (aForm.getAppDate() != null) {
//                temp.setApprovalDate(DateUtil.convertDate(locale, aForm.getAppDate()));
//            }
//            if (aForm.getDdnExtDate() != null) {
//                temp.setExtendedToDate(DateUtil.convertDate(locale, aForm.getDdnExtDate()));
//            }

            String eventStr = (String) aForm.getEvent();

            if ((eventStr != null) && "submit_remarks".equals(eventStr)) {
                DefaultLogger.debug(this, "do nothing here because update remarks only");
            } else {
                temp.setCreditOfficerName(aForm.getCreditOfficerName());
                temp.setCreditOfficerSignNo(aForm.getCreditOfficerSgnNo());
                temp.setSeniorOfficerName(aForm.getSeniorCreditOfficerName());
                temp.setSeniorOfficerSignNo(aForm.getSeniorCreditOfficerSgnNo());
//                temp.setCreditOfficerLocation(new OBBookingLocation(aForm.getCreditOfficerLocationCountry(), aForm
//                        .getCreditOfficerLocationOrgCode()));
//                temp.setSeniorOfficerLocation(new OBBookingLocation(aForm.getSeniorCreditOfficerLocationCountry(),
//                        aForm.getSeniorCreditOfficerLocationOrgCode()));
                temp.setRemarks(aForm.getRemarksSCC());
                temp.setReleaseTo(aForm.getReleaseTo());
            }

//            if (aForm.getDdnDate() != null) {
//                temp.setDeferredToDate(DateUtil.convertDate(locale, aForm.getDdnDate()));
//            }
//            if ((aForm.getDdnDays() != null) && (aForm.getDdnDays().length() > 0)) {
//                temp.setDaysValid(Integer.parseInt(aForm.getDdnDays()));
//            }
        }
        //if (true) throw new MapperException("TESTING DDN");
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
        GenerateLADForm aForm = (GenerateLADForm) cForm;
        IDDN cert = (IDDN) obj;
        Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
        ILimitProfile limitProfile = (ILimitProfile) map.get(IGlobalConstant.GLOBAL_LIMITPROFILE_OBJ);

        if (cert == null) {
            throw new MapperException("DDN is null in service scope!");
        }
        try {
//            if (limitProfile.getApprovalDate() != null) {
//                aForm.setBcaAppDate(DateUtil.formatDate(locale, limitProfile.getApprovalDate()));
//            }
            aForm.setCreditOfficerName(cert.getCreditOfficerName());
            aForm.setCreditOfficerSgnNo(cert.getCreditOfficerSignNo());
            aForm.setSeniorCreditOfficerName(cert.getSeniorOfficerName());
            aForm.setSeniorCreditOfficerSgnNo(cert.getSeniorOfficerSignNo());
            aForm.setRemarksSCC(cert.getRemarks());
            aForm.setReleaseTo(cert.getReleaseTo());
//            if (cert.getCreditOfficerLocation() != null) {
//                aForm.setCreditOfficerLocationCountry(cert.getCreditOfficerLocation().getCountryCode());
//                aForm.setCreditOfficerLocationOrgCode(cert.getCreditOfficerLocation().getOrganisationCode());
//            }
//            if (cert.getSeniorOfficerLocation() != null) {
//                aForm.setSeniorCreditOfficerLocationCountry(cert.getSeniorOfficerLocation().getCountryCode());
//                aForm.setSeniorCreditOfficerLocationOrgCode(cert.getSeniorOfficerLocation().getOrganisationCode());
//            }
//            aForm.setDdnDate(DateUtil.formatDate(locale, cert.getDeferredToDate()));

//            if (cert.getDaysValid() >= 0) {
//                aForm.setDdnDays(String.valueOf(cert.getDaysValid()));
//            } else {
//                aForm.setDdnDays("");
//            }
//            aForm.setAppDate(DateUtil.formatDate(locale, cert.getApprovalDate()));
//            aForm.setApprovedBy(cert.getApprovalBy());
//            aForm.setDdnExtDate(DateUtil.formatDate(locale, cert.getExtendedToDate()));

            IDDNItem items[] = cert.getDDNItemList();
//            String ddnAmt[] = null, maturityDate[] = null;
//            String[] appAmtCurr = null;
            String[] docNumber = null;
            String[] docCode = null;
            String[] docDesc = null;
            String[] dateDefer = null;
            String[] dateOfReturn = null;
            String[] docStatus = null;
            String[] actionParty = null;
            String[] theApprovalDate = null;
            String[] approvedBy = null;

            int itemCount = items == null ? 0 : items.length;
            if (itemCount > 0) {
//                ddnAmt = new String[itemCount];
//                appAmtCurr = new String[itemCount];
//                maturityDate = new String[itemCount];
                docNumber = new String[itemCount];
                docCode = new String[itemCount];
                docDesc = new String[itemCount];
                dateDefer = new String[itemCount];
                dateOfReturn = new String[itemCount];
                docStatus = new String[itemCount];
                actionParty = new String[itemCount];
                theApprovalDate = new String[itemCount];
                approvedBy = new String[itemCount];

                for (int i = 0; i < itemCount; i++) {
                    docNumber[i] = String.valueOf(items[i].getDocNumber());
                    docCode[i] = items[i].getDocCode();
                    docDesc[i] = items[i].getDocDesc();
                    dateDefer[i] = DateUtil.formatDate(locale, items[i].getDateDefer());
                    dateOfReturn[i] = DateUtil.formatDate(locale, items[i].getDateOfReturn());
                    docStatus[i] = items[i].getDocStatus();
                    actionParty[i] = items[i].getActionParty();
                    theApprovalDate[i] = DateUtil.formatDate(locale, items[i].getTheApprovalDate());
                    approvedBy[i] = items[i].getApprovedBy();
                }
                aForm.setDocNumber(docNumber);
                aForm.setDocCode(docCode);
                aForm.setDocDesc(docDesc);
                aForm.setDateDefer(dateDefer);
                aForm.setDateOfReturn(dateOfReturn);
                aForm.setDocStatus(docStatus);
                aForm.setActionParty(actionParty);
                aForm.setTheApprovalDate(theApprovalDate);
                aForm.setApprovedBy(approvedBy);

//
//                int checkedIndex = 0;
//                String strCheckedIndexes = "";
//                int ddnAmtIndex = 0; // counter for ddnAmt
//                // get the clean/unsecured items
//                IDDNItem unsecuredItems[] = cert.getCleanDDNItemList();
//                int unsecuredCount = unsecuredItems == null ? 0 : unsecuredItems.length;
//                for (int i = 0; i < unsecuredCount; i++) {
//                    if (unsecuredItems[i].getDDNAmount() != null) {
//                        ddnAmt[ddnAmtIndex] = CurrencyManager.convertToDisplayString(locale, (unsecuredItems[i]
//                                .getDDNAmount()));
//                    } else {
//                        ddnAmt[ddnAmtIndex] = "";
//                    }
//                    Amount approveAmt = unsecuredItems[i].getApprovedLimitAmount();
//                    String currencyCode = approveAmt == null ? "" : approveAmt.getCurrencyCode();
//                    appAmtCurr[ddnAmtIndex] = currencyCode;
//                    maturityDate[ddnAmtIndex] = DateUtil.formatDate(locale, unsecuredItems[i].getMaturityDate());
//                    ddnAmtIndex++;
//                    strCheckedIndexes = (unsecuredItems[i].getIsDDNIssuedInd()) ? strCheckedIndexes + ","
//                            + checkedIndex : strCheckedIndexes;
//                    checkedIndex++;
//                }
//                // get the notClean/secured items
//                IDDNItem securedItems[] = cert.getNotCleanDDNItemList();
//                int securedCount = securedItems == null ? 0 : securedItems.length;
//                for (int i = 0; i < securedCount; i++) {
//                    if (securedItems[i].getDDNAmount() != null) {
//                        ddnAmt[ddnAmtIndex] = CurrencyManager.convertToDisplayString(locale, (securedItems[i]
//                                .getDDNAmount()));
//                    } else {
//                        ddnAmt[ddnAmtIndex] = "";
//                    }
//                    Amount approveAmt = securedItems[i].getApprovedLimitAmount();
//                    String currencyCode = approveAmt == null ? "" : approveAmt.getCurrencyCode();
//                    appAmtCurr[ddnAmtIndex] = currencyCode;
//                    maturityDate[ddnAmtIndex] = DateUtil.formatDate(locale, securedItems[i].getMaturityDate());
//                    ddnAmtIndex++;
//                    strCheckedIndexes = (securedItems[i].getIsDDNIssuedInd()) ? strCheckedIndexes + "," + checkedIndex
//                            : strCheckedIndexes;
//                    checkedIndex++;
//                }
//                aForm.setDdnLimit(ddnAmt);
//                aForm.setAppAmtCurrCode(appAmtCurr);
//                aForm.setMaturityDate(maturityDate);
//
//                aForm.setCheckedIndexes(strCheckedIndexes); // remove the ", "
                // in front
            }
        }
        catch (Exception e) {
            DefaultLogger.error(this, "", e);
            throw new MapperException(e.getMessage());
        }
        return aForm;
    }

    // Calculation for DDN Valid for duration.
    // DDN Approved date - (DDN Extended Date or DDN Defered date)

    private String getDDNValidFor(IDDN cert) {
        String validFor = "";

        Date startDate = cert.getApprovalDate();
        Date endDate = new Date();
        if (cert.getExtendedToDate() == null) {
            endDate = cert.getDeferredToDate();
        } else {
            endDate = cert.getExtendedToDate();
        }
        // DefaultLogger.debug(this, "startDate " + startDate);
        // DefaultLogger.debug(this, "endDate " + endDate);
        if ((endDate == null) || (startDate == null)) {
            validFor = "";
        } else {
            if (endDate.before(startDate)) {
                validFor = "0";
            } else {

                // The number of middnseconds in one day
                long ONE_DAY = 1000 * 60 * 60 * 24;

                // Convert both dates to middnseconds
                long date1_ms = endDate.getTime();
                long date2_ms = startDate.getTime();

                // Calculate the difference in middnseconds
                long difference_ms = Math.abs(date1_ms - date2_ms);

                // Convert back to days and return
                long daysDiff = Math.round(difference_ms / ONE_DAY);

                validFor = String.valueOf(daysDiff);

            }
        }
        DefaultLogger.debug(this, "validFor " + validFor);
        return validFor;

    }
}
