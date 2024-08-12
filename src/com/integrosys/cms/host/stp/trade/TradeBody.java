package com.integrosys.cms.host.stp.trade;

import com.integrosys.cms.host.stp.STPBody;
import com.integrosys.cms.host.stp.core.ClobFieldHandler;


import java.sql.Clob;
import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @author : Chin Kok Cheong
 * @version $Id$
 */
public class TradeBody extends STPBody implements java.io.Serializable {

   // private String correlationId;
    private String id;
    private String cdtFileId;    //pending
    private String accountNo;
    private String limitNo;
    private String firstLineFacilityId; //pending
    private String mainLineFacilityId; //pending
    private String mtFacilityCode;
    private String mtBrCode; //pending
    private String mtCurrencyCode; //pending
    private String approvedLimit;
    private String mtTenureTimeCode;
    private String approvedTenure;  //
    private Date reviewDt;
    private String tempFlag;
    private String createBy;
    private String updateBy;
    private Date createdDt;
    private Date updatedDt;
    private Long version;
    private String intRate;         //
    private String mtIntRateCode;
    private Date expiryDt;
    private Clob specialRemark;      ////
    private String mtMaintStsCode;  //pending
    private String penaltyIntRate;            //
    private String mtPenaltyIntRateCode;
    private String odRate;        //
    private String mtOdRateCode;
    private String autoPurgeFlag;
    private String mtFacilityId;
    private String lsmCode;
    private String rbsPurposeCode;
    private String limitDesc;
    private String revolvingFlag;
    private String cgcFlag;
    private String sectorFlag;
    private String cgcSchema;
    private String cgcRefNo;
    private String commissionRate;   //
    private String delFlag;
    private Date approvedDt;
    private String mtrFlag;
    private String mbgFlag;

    /*public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCdtFileId() {
        return cdtFileId;
    }

    public void setCdtFileId(String cdtFileId) {
        this.cdtFileId = cdtFileId;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getLimitNo() {
        return limitNo;
    }

    public void setLimitNo(String limitNo) {
        this.limitNo = limitNo;
    }

    public String getFirstLineFacilityId() {
        return firstLineFacilityId;
    }

    public void setFirstLineFacilityId(String firstLineFacilityId) {
        this.firstLineFacilityId = firstLineFacilityId;
    }

    public String getMainLineFacilityId() {
        return mainLineFacilityId;
    }

    public void setMainLineFacilityId(String mainLineFacilityId) {
        this.mainLineFacilityId = mainLineFacilityId;
    }

    public String getMtFacilityCode() {
        return mtFacilityCode;
    }

    public void setMtFacilityCode(String mtFacilityCode) {
        this.mtFacilityCode = mtFacilityCode;
    }

    public String getMtBrCode() {
        return mtBrCode;
    }

    public void setMtBrCode(String mtBrCode) {
        this.mtBrCode = mtBrCode;
    }

    public String getMtCurrencyCode() {
        return mtCurrencyCode;
    }

    public void setMtCurrencyCode(String mtCurrencyCode) {
        this.mtCurrencyCode = mtCurrencyCode;
    }

    public String getApprovedLimit() {
        return approvedLimit;
    }

    public void setApprovedLimit(String approvedLimit) {
        this.approvedLimit = approvedLimit;
    }

    public String getMtTenureTimeCode() {
        return mtTenureTimeCode;
    }

    public void setMtTenureTimeCode(String mtTenureTimeCode) {
        this.mtTenureTimeCode = mtTenureTimeCode;
    }

    public String getApprovedTenure() {
        return approvedTenure;
    }

    public void setApprovedTenure(String approvedTenure) {
        this.approvedTenure = approvedTenure;
    }

    public Date getReviewDt() {
        return reviewDt;
    }

    public void setReviewDt(Date reviewDt) {
        this.reviewDt = reviewDt;
    }

    public String getTempFlag() {
        return tempFlag;
    }

    public void setTempFlag(String tempFlag) {
        this.tempFlag = tempFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Date createdDt) {
        this.createdDt = createdDt;
    }

    public Date getUpdatedDt() {
        return updatedDt;
    }

    public void setUpdatedDt(Date updatedDt) {
        this.updatedDt = updatedDt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getIntRate() {
        return intRate;
    }

    public void setIntRate(String intRate) {
        this.intRate = intRate;
    }

    public String getMtIntRateCode() {
        return mtIntRateCode;
    }

    public void setMtIntRateCode(String mtIntRateCode) {
        this.mtIntRateCode = mtIntRateCode;
    }

    public Date getExpiryDt() {
        return expiryDt;
    }

    public void setExpiryDt(Date expiryDt) {
        this.expiryDt = expiryDt;
    }

    public Clob getSpecialRemark() {
        return specialRemark;
    }

    public void setSpecialRemark(Clob specialRemark) {
        this.specialRemark = specialRemark;
    }

    public String getSpecialRemarktoString() {
       // return this.specialRemark;
        return (String) new ClobFieldHandler().convertClobToString(specialRemark);
    }

    public void setSpecialRemarkFromString(String specialRemark) {
        //this.specialRemark = specialRemark;
        if(specialRemark!=null)
            this.specialRemark = (Clob) new ClobFieldHandler().convertStringToClob(specialRemark);

    }

    public String getMtMaintStsCode() {
        return mtMaintStsCode;
    }

    public void setMtMaintStsCode(String mtMaintStsCode) {
        this.mtMaintStsCode = mtMaintStsCode;
    }

    public String getPenaltyIntRate() {
        return penaltyIntRate;
    }

    public void setPenaltyIntRate(String penaltyIntRate) {
        this.penaltyIntRate = penaltyIntRate;
    }

    public String getMtPenaltyIntRateCode() {
        return mtPenaltyIntRateCode;
    }

    public void setMtPenaltyIntRateCode(String mtPenaltyIntRateCode) {
        this.mtPenaltyIntRateCode = mtPenaltyIntRateCode;
    }

    public String getOdRate() {
        return odRate;
    }

    public void setOdRate(String odRate) {
        this.odRate = odRate;
    }

    public String getMtOdRateCode() {
        return mtOdRateCode;
    }

    public void setMtOdRateCode(String mtOdRateCode) {
        this.mtOdRateCode = mtOdRateCode;
    }

    public String getAutoPurgeFlag() {
        return autoPurgeFlag;
    }

    public void setAutoPurgeFlag(String autoPurgeFlag) {
        this.autoPurgeFlag = autoPurgeFlag;
    }

    public String getMtFacilityId() {
        return mtFacilityId;
    }

    public void setMtFacilityId(String mtFacilityId) {
        this.mtFacilityId = mtFacilityId;
    }

    public String getLsmCode() {
        return lsmCode;
    }

    public void setLsmCode(String lsmCode) {
        this.lsmCode = lsmCode;
    }

    public String getRbsPurposeCode() {
        return rbsPurposeCode;
    }

    public void setRbsPurposeCode(String rbsPurposeCode) {
        this.rbsPurposeCode = rbsPurposeCode;
    }

    public String getLimitDesc() {
        return limitDesc;
    }

    public void setLimitDesc(String limitDesc) {
        this.limitDesc = limitDesc;
    }

    public String getRevolvingFlag() {
        return revolvingFlag;
    }

    public void setRevolvingFlag(String revolvingFlag) {
        this.revolvingFlag = revolvingFlag;
    }

    public String getCgcFlag() {
        return cgcFlag;
    }

    public void setCgcFlag(String cgcFlag) {
        this.cgcFlag = cgcFlag;
    }

    public String getSectorFlag() {
        return sectorFlag;
    }

    public void setSectorFlag(String sectorFlag) {
        this.sectorFlag = sectorFlag;
    }

    public String getCgcSchema() {
        return cgcSchema;
    }

    public void setCgcSchema(String cgcSchema) {
        this.cgcSchema = cgcSchema;
    }

    public String getCgcRefNo() {
        return cgcRefNo;
    }

    public void setCgcRefNo(String cgcRefNo) {
        this.cgcRefNo = cgcRefNo;
    }

    public String getCommissionRate() {
        return commissionRate;
    }

    public void setCommissionRate(String commissionRate) {
        this.commissionRate = commissionRate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getApprovedDt() {
        return approvedDt;
    }

    public void setApprovedDt(Date approvedDt) {
        this.approvedDt = approvedDt;
    }

    public String getMtrFlag() {
        return mtrFlag;
    }

    public void setMtrFlag(String mtrFlag) {
        this.mtrFlag = mtrFlag;
    }

    public String getMbgFlag() {
        return mbgFlag;
    }

    public void setMbgFlag(String mbgFlag) {
        this.mbgFlag = mbgFlag;
    }

}