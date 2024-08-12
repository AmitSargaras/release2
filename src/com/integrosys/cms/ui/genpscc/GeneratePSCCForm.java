/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.genpscc;

import com.integrosys.cms.ui.common.TrxContextForm;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author $Author: czhou $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/06/03 10:13:26 $ Tag: $Name: $
 */

public class GeneratePSCCForm extends TrxContextForm implements Serializable {

    //private String creditOfficerName = "";

    //private String seniorCreditOfficerName = "";

    //private String creditOfficerSgnNo = "";

    //private String seniorCreditOfficerSgnNo = "";

    //private String creditOfficerLocationCountry;

    //private String seniorCreditOfficerLocationCountry;

    //private String creditOfficerLocationOrgCode;

    //private String seniorCreditOfficerLocationOrgCode;

    /**
     * For (Senior) Credit Documentation Officer / Manager has been renamed to Authorised Personnel 1 & 2
     */
    private String authorPerOneName = "";

    private String authorPerOneSignNo = "";

    private String authorPerTwoName = "";

    private String authorPerTwoSignNo = "";
    /**
     * End renamed
     */

    //private String remarksPSCC = "";
    private String remarks = "";

    /**
     * Refer to SCC Limit Amount to be Activated - OBSOLETED
     */
    //private String[] actLimit;

    //private String[] actAmtCurrCode;

    private String[] appLimit;

    /**
     * Refer to Limit Maturiy Date - OBSOLETED
     */
    //private String[] maturityDate;

    private String parSccIssuedIndex = "";

    private String[] expiryAvailabilityDate;

    private String[] distbursementAmt;

    private String[] distbursementAmtCurrCode;

    private String[] amtEnforceTodate;

    private String[] amtEnforceTodateCurrCode;

    private String[] paymentInstruc;

    private String hasCheck1;

    private String hasCheck2;

    private String hasCheck3;

    private String hasCheck4;

    private String insuredWith;

    private String insuredWithAmt;

    private String ambm;

    private String expiry;

    private String sinkFundAmt;

    private String pmForPeriodOf;

    private String commencingFrom;

    private String fundReach;

    private String feesAmt;

    private String others;

    /**
     * OBSOLETED
     */
    /*
    // --- CR146 starts --------------
    private String[] cleanActLimit;

    private String[] cleanActAmtCurrCode;

    private String[] notCleanActLimit;

    private String[] notCleanActAmtCurrCode;

    private String[] cleanAppLimit;

    private String[] notCleanAppLimit;

    private String[] cleanMaturityDate;

    private String[] notCleanMaturityDate;
    // --- CR146 ends --------------
    */
    public String getParSccIssuedIndex() {
        return parSccIssuedIndex;
    }

    public void setParSccIssuedIndex(String parSccIssuedIndex) {
        this.parSccIssuedIndex = parSccIssuedIndex;
    }
    /*
    public String[] getActAmtCurrCode() {
        return actAmtCurrCode;
    }

    public void setActAmtCurrCode(String[] actAmtCurrCode) {
        this.actAmtCurrCode = actAmtCurrCode;
    }

    public String[] getActLimit() {
        return actLimit;
    }

    public void setActLimit(String[] actLimit) {
        this.actLimit = actLimit;
    }

    public String[] getCleanActLimit() {
        return cleanActLimit;
    }

    public void setCleanActLimit(String[] cleanActLimit) {
        this.cleanActLimit = cleanActLimit;
    }

    public String[] getCleanActAmtCurrCode() {
        return cleanActAmtCurrCode;
    }

    public void setCleanActAmtCurrCode(String[] cleanActAmtCurrCode) {
        this.cleanActAmtCurrCode = cleanActAmtCurrCode;
    }

    public String[] getNotCleanActLimit() {
        return notCleanActLimit;
    }

    public void setNotCleanActLimit(String[] notCleanActLimit) {
        this.notCleanActLimit = notCleanActLimit;
    }

    public String[] getNotCleanActAmtCurrCode() {
        return notCleanActAmtCurrCode;
    }

    public void setNotCleanActAmtCurrCode(String[] notCleanActAmtCurrCode) {
        this.notCleanActAmtCurrCode = notCleanActAmtCurrCode;
    }

    public String[] getCleanAppLimit() {
        return cleanAppLimit;
    }

    public void setCleanAppLimit(String[] cleanAppLimit) {
        this.cleanAppLimit = cleanAppLimit;
    }

    public String[] getNotCleanAppLimit() {
        return notCleanAppLimit;
    }

    public void setNotCleanAppLimit(String[] notCleanAppLimit) {
        this.notCleanAppLimit = notCleanAppLimit;
    }

    public String[] getCleanMaturityDate() {
        return cleanMaturityDate;
    }

    public void setCleanMaturityDate(String[] cleanMaturityDate) {
        this.cleanMaturityDate = cleanMaturityDate;
    }

    public String[] getNotCleanMaturityDate() {
        return notCleanMaturityDate;
    }

    public void setNotCleanMaturityDate(String[] notCleanMaturityDate) {
        this.notCleanMaturityDate = notCleanMaturityDate;
    }

    public String getCreditOfficerName() {
        return creditOfficerName;
    }

    public void setCreditOfficerName(String creditOfficerName) {
        this.creditOfficerName = creditOfficerName;
    }

    public String getCreditOfficerSgnNo() {
        return creditOfficerSgnNo;
    }

    public void setCreditOfficerSgnNo(String creditOfficerSgnNo) {
        this.creditOfficerSgnNo = creditOfficerSgnNo;
    }

    public String getCreditOfficerLocationCountry() {
        return creditOfficerLocationCountry;
    }

    public void setCreditOfficerLocationCountry(String creditOfficerLocationCountry) {
        this.creditOfficerLocationCountry = creditOfficerLocationCountry;
    }

    public String getSeniorCreditOfficerLocationCountry() {
        return seniorCreditOfficerLocationCountry;
    }

    public void setSeniorCreditOfficerLocationCountry(String seniorCreditOfficerLocationCountry) {
        this.seniorCreditOfficerLocationCountry = seniorCreditOfficerLocationCountry;
    }

    public String getCreditOfficerLocationOrgCode() {
        return creditOfficerLocationOrgCode;
    }

    public void setCreditOfficerLocationOrgCode(String creditOfficerLocationOrgCode) {
        this.creditOfficerLocationOrgCode = creditOfficerLocationOrgCode;
    }

    public String getSeniorCreditOfficerLocationOrgCode() {
        return seniorCreditOfficerLocationOrgCode;
    }

    public void setSeniorCreditOfficerLocationOrgCode(String seniorCreditOfficerLocationOrgCode) {
        this.seniorCreditOfficerLocationOrgCode = seniorCreditOfficerLocationOrgCode;
    }
    */

    //public String getRemarksPSCC() {
    //    return remarksPSCC;
    //}
    public String getRemarks() {
        return remarks;
    }

    //public void setRemarksPSCC(String remarksPSCC) {
    //    this.remarksPSCC = remarksPSCC;
    //}
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String[] getAppLimit() {
        return appLimit;
    }

    public void setAppLimit(String[] appLimit) {
        this.appLimit = appLimit;
    }
    /*
    public String getSeniorCreditOfficerName() {
        return seniorCreditOfficerName;
    }

    public void setSeniorCreditOfficerName(String seniorCreditOfficerName) {
        this.seniorCreditOfficerName = seniorCreditOfficerName;
    }

    public String getSeniorCreditOfficerSgnNo() {
        return seniorCreditOfficerSgnNo;
    }

    public void setSeniorCreditOfficerSgnNo(String seniorCreditOfficerSgnNo) {
        this.seniorCreditOfficerSgnNo = seniorCreditOfficerSgnNo;
    }

    public String[] getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(String[] maturityDate) {
        this.maturityDate = maturityDate;
    }
    */

    public String getAuthorPerOneName() {
        return authorPerOneName;
    }

    public void setAuthorPerOneName(String authorPerOneName) {
        this.authorPerOneName = authorPerOneName;
    }

    public String getAuthorPerOneSignNo() {
        return authorPerOneSignNo;
    }

    public void setAuthorPerOneSignNo(String authorPerOneSignNo) {
        this.authorPerOneSignNo = authorPerOneSignNo;
    }

    public String getAuthorPerTwoName() {
        return authorPerTwoName;
    }

    public void setAuthorPerTwoName(String authorPerTwoName) {
        this.authorPerTwoName = authorPerTwoName;
    }

    public String getAuthorPerTwoSignNo() {
        return authorPerTwoSignNo;
    }

    public void setAuthorPerTwoSignNo(String authorPerTwoSignNo) {
        this.authorPerTwoSignNo = authorPerTwoSignNo;
    }

    public String[] getExpiryAvailabilityDate() {
        return expiryAvailabilityDate;
    }

    public void setExpiryAvailabilityDate(String[] expiryAvailabilityDate) {
        this.expiryAvailabilityDate = expiryAvailabilityDate;
    }

    public String[] getDistbursementAmt() {
        return distbursementAmt;
    }

    public void setDistbursementAmt(String[] distbursementAmt) {
        this.distbursementAmt = distbursementAmt;
    }

    public String[] getDistbursementAmtCurrCode() {
        return distbursementAmtCurrCode;
    }

    public void setDistbursementAmtCurrCode(String[] distbursementAmtCurrCode) {
        this.distbursementAmtCurrCode = distbursementAmtCurrCode;
    }

    public String[] getAmtEnforceTodate() {
        return amtEnforceTodate;
    }

    public void setAmtEnforceTodate(String[] amtEnforceTodate) {
        this.amtEnforceTodate = amtEnforceTodate;
    }

    public String[] getAmtEnforceTodateCurrCode() {
        return amtEnforceTodateCurrCode;
    }

    public void setAmtEnforceTodateCurrCode(String[] amtEnforceTodateCurrCode) {
        this.amtEnforceTodateCurrCode = amtEnforceTodateCurrCode;
    }

    public String[] getPaymentInstruc() {
        return paymentInstruc;
    }

    public void setPaymentInstruc(String[] paymentInstruc) {
        this.paymentInstruc = paymentInstruc;
    }

    public String getHasCheck1() {
        return hasCheck1;
    }

    public void setHasCheck1(String hasCheck1) {
        this.hasCheck1 = hasCheck1;
    }

    public String getHasCheck2() {
        return hasCheck2;
    }

    public void setHasCheck2(String hasCheck2) {
        this.hasCheck2 = hasCheck2;
    }

    public String getHasCheck3() {
        return hasCheck3;
    }

    public void setHasCheck3(String hasCheck3) {
        this.hasCheck3 = hasCheck3;
    }

    public String getHasCheck4() {
        return hasCheck4;
    }

    public void setHasCheck4(String hasCheck4) {
        this.hasCheck4 = hasCheck4;
    }

    public String getInsuredWith() {
        return insuredWith;
    }

    public void setInsuredWith(String insuredWith) {
        this.insuredWith = insuredWith;
    }

    public String getInsuredWithAmt() {
        return insuredWithAmt;
    }

    public void setInsuredWithAmt(String insuredWithAmt) {
        this.insuredWithAmt = insuredWithAmt;
    }

    public String getAmbm() {
        return ambm;
    }

    public void setAmbm(String ambm) {
        this.ambm = ambm;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public String getSinkFundAmt() {
        return sinkFundAmt;
    }

    public void setSinkFundAmt(String sinkFundAmt) {
        this.sinkFundAmt = sinkFundAmt;
    }

    public String getPmForPeriodOf() {
        return pmForPeriodOf;
    }

    public void setPmForPeriodOf(String pmForPeriodOf) {
        this.pmForPeriodOf = pmForPeriodOf;
    }

    public String getCommencingFrom() {
        return commencingFrom;
    }

    public void setCommencingFrom(String commencingFrom) {
        this.commencingFrom = commencingFrom;
    }

    public String getFundReach() {
        return fundReach;
    }

    public void setFundReach(String fundReach) {
        this.fundReach = fundReach;
    }

    public String getFeesAmt() {
        return feesAmt;
    }

    public void setFeesAmt(String feesAmt) {
        this.feesAmt = feesAmt;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    /**
     * This method defines a String array which tells what object is to be
     * formed from the form and using what mapper classes to form it. it has a
     * syntax (keyMapperclassname)
     *
     * @return two-dimesnional String Array
     */
    public String[][] getMapper() {
        String[][] input = {{"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"},
                // {"limitProfile","com.integrosys.cms.ui.genscc.LimitProfileMapper"},
                {"cert", "com.integrosys.cms.ui.genpscc.CertMapper"}};
        return input;
    }

    public String toString() {
        return "GeneratePSCCForm{" +
                "authorPerOneName='" + authorPerOneName + '\'' +
                ", authorPerOneSignNo='" + authorPerOneSignNo + '\'' +
                ", authorPerTwoName='" + authorPerTwoName + '\'' +
                ", authorPerTwoSignNo='" + authorPerTwoSignNo + '\'' +
                ", remarks='" + remarks + '\'' +
                ", appLimit=" + (appLimit == null ? null : Arrays.asList(appLimit)) +
                ", parSccIssuedIndex='" + parSccIssuedIndex + '\'' +
                ", expiryAvailabilityDate=" + (expiryAvailabilityDate == null ? null : Arrays.asList(expiryAvailabilityDate)) +
                ", distbursementAmt=" + (distbursementAmt == null ? null : Arrays.asList(distbursementAmt)) +
                ", distbursementAmtCurrCode=" + (distbursementAmtCurrCode == null ? null : Arrays.asList(distbursementAmtCurrCode)) +
                ", amtEnforceTodate=" + (amtEnforceTodate == null ? null : Arrays.asList(amtEnforceTodate)) +
                ", amtEnforceTodateCurrCode=" + (amtEnforceTodateCurrCode == null ? null : Arrays.asList(amtEnforceTodateCurrCode)) +
                ", paymentInstruc=" + (paymentInstruc == null ? null : Arrays.asList(paymentInstruc)) +
                ", hasCheck1='" + hasCheck1 + '\'' +
                ", hasCheck2='" + hasCheck2 + '\'' +
                ", hasCheck3='" + hasCheck3 + '\'' +
                ", hasCheck4='" + hasCheck4 + '\'' +
                ", insuredWith='" + insuredWith + '\'' +
                ", insuredWithAmt='" + insuredWithAmt + '\'' +
                ", ambm='" + ambm + '\'' +
                ", expiry='" + expiry + '\'' +
                ", sinkFundAmt='" + sinkFundAmt + '\'' +
                ", pmForPeriodOf='" + pmForPeriodOf + '\'' +
                ", commencingFrom='" + commencingFrom + '\'' +
                ", fundReach='" + fundReach + '\'' +
                ", feesAmt='" + feesAmt + '\'' +
                ", others='" + others + '\'' +
                '}';
    }
}
