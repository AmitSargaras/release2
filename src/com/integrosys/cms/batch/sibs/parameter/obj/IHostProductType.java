package com.integrosys.cms.batch.sibs.parameter.obj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Oct 2, 2008
 * Time: 4:10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHostProductType extends ISynchronizer {

    public String getLoanType();

    public void setLoanType(String loanType);

    public String getDescription();

    public void setDescription(String description);

    public String getGroupCode();

    public void setGroupCode(String groupCode);

    public String getGroupDescription();

    public void setGroupDescription(String groupDescription);

    public String getCommericalRetailIndicator();

    public void setCommericalRetailIndicator(String commericalRetailIndicator);

    public String getCurrency();

    public void setCurrency(String currency);

    public BigDecimal getMinInterestRate();

    public void setMinInterestRate(BigDecimal minInterestRate);

    public BigDecimal getMaxInterestRate();

    public void setMaxInterestRate(BigDecimal maxInterestRate);

    public BigDecimal getRateFloor();

    public void setRateFloor(BigDecimal rateFloor);

    public BigDecimal getRateCeiling();

    public void setRateCeiling(BigDecimal rateCeiling);

    public String getStaffIndicator();

    public void setStaffIndicator(String staffIndicator);

    public String getAccountIndicator();

    public void setAccountIndicator(String accountIndicator);

    public String getTierLoanIndicator();

    public void setTierLoanIndicator(String tierLoanIndicator);

    public String getSptfLoanType();

    public void setSptfLoanType(String sptfLoanType);

    public String getPrimeRateNumber();

    public void setPrimeRateNumber(String primeRateNumber);

//    public Integer getMaxLoanTenure();
//
//    public void setMaxLoanTenure(Integer maxLoanTenure);
//
//    public Integer getMinLoanTenure();
//
//    public void setMinLoanTenure(Integer minLoanTenure);

    public String getStatus();

    public void setStatus(String status);

    public Date getLastUpdatedDate();

    public void setLastUpdatedDate(Date lastUpdatedDate);

    public String getConceptCode();

    public void setConceptCode(String conceptCode);

    public String getInterestBase();

    public void setInterestBase(String interestBase);

    public String getSource();

    public void setSource(String source);

    public String getProductGroup();

    public void setProductGroup(String productGroup);

    public String getDealerProductIndicator();

    public void setDealerProductIndicator(String dealerProductIndicator);

    public BigDecimal getProductLimit();

    public void setProductLimit(BigDecimal productLimit);

    public Date getProductExpiryDate();

    public void setProductExpiryDate(Date productExpiryDate);

    public String getProductExpiryDateStr();

    public void setProductExpiryDateStr(String productExpiryDateStr);

    public Integer getProductAgeLimit();

    public void setProductAgeLimit(Integer productAgeLimit);

    public Integer getPaymentFrequency();

    public void setPaymentFrequency(Integer paymentFrequency);

    public String getPaymentFrequencyCode();

    public void setPaymentFrequencyCode(String paymentFrequencyCode);

    public Integer getPaymentCode();

    public void setPaymentCode(Integer paymentCode);

//    public String getGratuityFinancing();
//
//    public void setGratuityFinancing(String gratuityFinancing);

    public String getVarRate();

    public void setVarRate(String varRate);

    public String getRebateMethodBank();

    public void setRebateMethodBank(String rebateMethodBank);

//    public String getPromoPackageCode();
//
//    public void setPromoPackageCode(String promoPackageCode);
}
