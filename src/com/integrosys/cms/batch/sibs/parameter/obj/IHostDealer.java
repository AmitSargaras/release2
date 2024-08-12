package com.integrosys.cms.batch.sibs.parameter.obj;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 4:51:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostDealer extends ISynchronizer {
    public String getDealerNumber();

    public void setDealerNumber(String dealerNumber);

    public String getDealerName();

    public void setDealerName(String dealerName);

    public String getDealerShortName();

    public void setDealerShortName(String dealerShortName);

    public String getFormattedShortName();

    public void setFormattedShortName(String formattedShortName);

    public String getCif();

    public void setCif(String cif);

    public String getAddressLine1();

    public void setAddressLine1(String addressLine1);

    public String getAddressLine2();

    public void setAddressLine2(String addressLine2);

    public String getAddressLine3();

    public void setAddressLine3(String addressLine3);

    public String getPhone1();

    public void setPhone1(String phone1);

    public String getPhone2();

    public void setPhone2(String phone2);

    public BigDecimal getLimitAmount();

    public void setLimitAmount(BigDecimal limitAmount);

    public BigDecimal getReserveAmount();

    public void setReserveAmount(BigDecimal reserveAmount);

    public BigDecimal getAmountUtilised();

    public void setAmountUtilised(BigDecimal amountUtilised);

    public String getDealerPlan();

    public void setDealerPlan(String dealerPlan);

    public String getDealerGroup();

    public void setDealerGroup(String dealerGroup);

    public String getOfficerCode();

    public void setOfficerCode(String officerCode);

    public Date getExpiryDate1();

    public void setExpiryDate1(Date expiryDate1);

    public Date getExpiryDate2();

    public void setExpiryDate2(Date expiryDate2);

    public String getExpiryDateStr();

    public void setExpiryDateStr(String expiryDateStr);

    public String getRevolvingIndicator();

    public void setRevolvingIndicator(String revolvingIndicator);

    public BigDecimal getAccrualPercent();

    public void setAccrualPercent(BigDecimal accrualPercent);

    public String getDealerStatus();

    public void setDealerStatus(String dealerStatus);

    public Date getDateRecruited1();

    public void setDateRecruited1(Date dateRecruited1);

    public Date getDateRecruited2();

    public void setDateRecruited2(Date dateRecruited2);

    public String getDateRecruitedStr();

    public void setDateRecruitedStr(String dateRecruitedStr);

    public int getMinIntDay();

    public void setMinIntDay(int minIntDay);

    public int getIntSubsidyDays();

    public void setIntSubsidyDays(int intSubsidyDays);

    public String getProductGroup();

    public void setProductGroup(String productGroup);

    public String getBranchServed();

    public void setBranchServed(String branchServed);

    public BigDecimal getGrossDevValue();

    public void setGrossDevValue(BigDecimal grossDevValue);

    public BigDecimal getGrossDevCost();

    public void setGrossDevCost(BigDecimal grossDevCost);

    public BigDecimal getSecurityValue();

    public void setSecurityValue(BigDecimal securityValue);

    public BigDecimal getUnderLimitFbr();

    public void setUnderLimitFbr(BigDecimal underLimitFbr);

    public BigDecimal getUnderLimitFbt();

    public void setUnderLimitFbt(BigDecimal underLimitFbt);

    public String getRecondIndicator();

    public void setRecondIndicator(String recondIndicator);

    public String getUsedIndicator();

    public void setUsedIndicator(String usedIndicator);

    public String getNewIndicator();

    public void setNewIndicator(String newIndicator);

    public String getNewNational();

    public void setNewNational(String newNational);

    public String getDealerStatus2();

    public void setDealerStatus2(String dealerStatus2);

    public Date getDealerStatusDate1();

    public void setDealerStatusDate1(Date dealerStatusDate1);

    public Date getDealerStatusDate2();

    public void setDealerStatusDate2(Date dealerStatusDate2);

    public String getDealerStatusDateStr();

    public void setDealerStatusDateStr(String dealerStatusDateStr);

    public String getRescheduleIndicator();

    public void setRescheduleIndicator(String rescheduleIndicator);

    public BigDecimal getDeliquencyRate();

    public void setDeliquencyRate(BigDecimal deliquencyRate);

    public String getFisMemberIndicator();

    public void setFisMemberIndicator(String fisMemberIndicator);

    public String getFisDealerCode();

    public void setFisDealerCode(String fisDealerCode);

    public String getRemarks();

    public void setRemarks(String remarks);

//    public String getType();
//
//    public void setType(String type);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);
         
}
