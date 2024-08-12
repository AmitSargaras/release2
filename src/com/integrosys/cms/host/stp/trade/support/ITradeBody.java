package com.integrosys.cms.host.stp.trade.support;

import com.integrosys.cms.host.stp.STPBody;

import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;


/**
 * @author $Author: marvin $<br>
 * @author Chin Kok Cheong
 * @version $Id$
 */
public interface ITradeBody {

    public String getId() ;

    public void setId(String id) ;

    public String getCdtFileId() ;

    public void setCdtFileId(String cdtFileId) ;

    public String getAccountNo() ;

    public void setAccountNo(String accountNo);

    public String getLimitNo() ;

    public void setLimitNo(String limitNo) ;

    public String getFirstLineFacilityId() ;

    public void setFirstLineFacilityId(String firstLineFacilityId);

    public String getMainLineFacilityId();

    public void setMainLineFacilityId(String mainLineFacilityId) ;

    public String getMtFacilityCode() ;

    public void setMtFacilityCode(String mtFacilityCode) ;

    public String getMtBrCode() ;

    public void setMtBrCode(String mtBrCode) ;

    public String getMtCurrencyCode() ;

    public void setMtCurrencyCode(String mtCurrencyCode);

    public BigDecimal getApprovedLimit() ;

    public void setApprovedLimit(BigDecimal approvedLimit);

    public String getMtTenureTimeCode() ;

    public void setMtTenureTimeCode(String mtTenureTimeCode) ;

    public BigDecimal getApprovedTenure() ;

    public void setApprovedTenure(BigDecimal approvedTenure) ;

    public Date getReviewDt();

    public void setReviewDt(Date reviewDt) ;

    public String getTempFlag() ;

    public void setTempFlag(String tempFlag);

    public String getCreateBy() ;

    public void setCreateBy(String createBy) ;

    public String getUpdateBy();

    public void setUpdateBy(String updateBy);

    public Date getCreatedDt() ;

    public void setCreatedDt(Date createdDt);

    public Date getUpdatedDt() ;

    public void setUpdatedDt(Date updatedDt) ;

    public Long getVersion() ;

    public void setVersion(Long version) ;

    public BigDecimal getIntRate() ;

    public void setIntRate(BigDecimal intRate) ;

    public String getMtIntRateCode();

    public void setMtIntRateCode(String mtIntRateCode) ;

    public Date getExpiryDt();

    public void setExpiryDt(Date expiryDt) ;

    public String getSpecialRemark() ;

    public void setSpecialRemark(String specialRemark);

    public String getMtMaintStsCode();

    public void setMtMaintStsCode(String mtMaintStsCode) ;

    public BigDecimal getPenaltyIntRate() ;

    public void setPenaltyIntRate(BigDecimal penaltyIntRate) ;

    public String getMtPenaltyIntRateCode() ;

    public void setMtPenaltyIntRateCode(String mtPenaltyIntRateCode) ;

    public BigDecimal getOdRate() ;

    public void setOdRate(BigDecimal odRate) ;

    public String getMtOdRateCode() ;

    public void setMtOdRateCode(String mtOdRateCode) ;

    public String getAutoPurgeFlag() ;

    public void setAutoPurgeFlag(String autoPurgeFlag) ;

    public String getMtFacilityId() ;

    public void setMtFacilityId(String mtFacilityId);

    public String getLsmCode() ;

    public void setLsmCode(String lsmCode) ;

    public String getRbsPurposeCode() ;

    public void setRbsPurposeCode(String rbsPurposeCode);

    public String getLimitDesc() ;

    public void setLimitDesc(String limitDesc) ;

    public String getRevolvingFlag() ;

    public void setRevolvingFlag(String revolvingFlag) ;

    public String getCgcFlag() ;

    public void setCgcFlag(String cgcFlag) ;

    public String getSectorFlag() ;

    public void setSectorFlag(String sectorFlag) ;

    public String getCgcSchema() ;

    public void setCgcSchema(String cgcSchema) ;

    public String getCgcRefNo() ;

    public void setCgcRefNo(String cgcRefNo) ;

    public BigDecimal getCommissionRate() ;

    public void setCommissionRate(BigDecimal commissionRate);

    public String getDelFlag() ;

    public void setDelFlag(String delFlag);

    public Date getApprovedDt() ;

    public void setApprovedDt(Date approvedDt) ;

    public String getMtrFlag() ;

    public void setMtrFlag(String mtrFlag) ;

    public String getMbgFlag() ;

    public void setMbgFlag(String mbgFlag) ;
}