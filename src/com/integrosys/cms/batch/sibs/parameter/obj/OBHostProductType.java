package com.integrosys.cms.batch.sibs.parameter.obj;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;
import com.integrosys.base.techinfra.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA. User: Cynthia Date: Oct 2, 2008 Time: 3:51:13 PM To
 * change this template use File | Settings | File Templates.
 */
public class OBHostProductType implements IHostProductType, Serializable {

	private static final long serialVersionUID = 351021667483274424L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "loanType", "currency", };

	private static final String[] IGNORED_PROPERTIES = new String[] { "loanType", "currency", "source", "status",
			"lastUpdatedDate" };

	private String loanType;

	private String description;

	private String groupCode;

	private String groupDescription;

	private String commericalRetailIndicator;

	private String currency;

	private BigDecimal minInterestRate;

	private BigDecimal maxInterestRate;

	private BigDecimal rateFloor;

	private BigDecimal rateCeiling;

	private String staffIndicator;

	private String accountIndicator;

	private String tierLoanIndicator;

	private String sptfLoanType;

	private String primeRateNumber;

//	private Integer maxLoanTenure;
//
//	private Integer minLoanTenure;

	private String status;

	private Date lastUpdatedDate;

	private String source;

	private String interestBase;

	private String conceptCode;

	private String productGroup;

	private String dealerProductIndicator;

	private BigDecimal productLimit;

	private Date productExpiryDate;

	private String productExpiryDateStr;

	private Integer productAgeLimit;

	private Integer paymentFrequency;

	private String paymentFrequencyCode;

	private Integer paymentCode;

//	private String gratuityFinancing;

    private String varRate;

    private String rebateMethodBank;

//    private String promoPackageCode;

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getGroupDescription() {
		return groupDescription;
	}

	public void setGroupDescription(String groupDescription) {
		this.groupDescription = groupDescription;
	}

	public String getCommericalRetailIndicator() {
		return commericalRetailIndicator;
	}

	public void setCommericalRetailIndicator(String commericalRetailIndicator) {
		this.commericalRetailIndicator = commericalRetailIndicator;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getMinInterestRate() {
		return minInterestRate;
	}

	public void setMinInterestRate(BigDecimal minInterestRate) {
		this.minInterestRate = minInterestRate;
	}

	public BigDecimal getMaxInterestRate() {
		return maxInterestRate;
	}

	public void setMaxInterestRate(BigDecimal maxInterestRate) {
		this.maxInterestRate = maxInterestRate;
	}

	public BigDecimal getRateFloor() {
		return rateFloor;
	}

	public void setRateFloor(BigDecimal rateFloor) {
		this.rateFloor = rateFloor;
	}

	public BigDecimal getRateCeiling() {
		return rateCeiling;
	}

	public void setRateCeiling(BigDecimal rateCeiling) {
		this.rateCeiling = rateCeiling;
	}

	public String getStaffIndicator() {
		return staffIndicator;
	}

	public void setStaffIndicator(String staffIndicator) {
		this.staffIndicator = staffIndicator;
	}

	public String getAccountIndicator() {
		return accountIndicator;
	}

	public void setAccountIndicator(String accountIndicator) {
		this.accountIndicator = accountIndicator;
	}

	public String getTierLoanIndicator() {
		return tierLoanIndicator;
	}

	public void setTierLoanIndicator(String tierLoanIndicator) {
		this.tierLoanIndicator = tierLoanIndicator;
	}

	public String getSptfLoanType() {
		return sptfLoanType;
	}

	public void setSptfLoanType(String sptfLoanType) {
		this.sptfLoanType = sptfLoanType;
	}

	public String getPrimeRateNumber() {
		return primeRateNumber;
	}

	public void setPrimeRateNumber(String primeRateNumber) {
		this.primeRateNumber = primeRateNumber;
	}

//	public Integer getMaxLoanTenure() {
//		return maxLoanTenure;
//	}
//
//	public void setMaxLoanTenure(Integer maxLoanTenure) {
//		this.maxLoanTenure = maxLoanTenure;
//	}
//
//	public Integer getMinLoanTenure() {
//		return minLoanTenure;
//	}
//
//	public void setMinLoanTenure(Integer minLoanTenure) {
//		this.minLoanTenure = minLoanTenure;
//	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public String getInterestBase() {
		return interestBase;
	}

	public void setInterestBase(String interestBase) {
		this.interestBase = interestBase;
	}

	public String getProductGroup() {
		return productGroup;
	}

	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}

	public String getDealerProductIndicator() {
		return dealerProductIndicator;
	}

	public void setDealerProductIndicator(String dealerProductIndicator) {
		this.dealerProductIndicator = dealerProductIndicator;
	}

	public BigDecimal getProductLimit() {
		return productLimit;
	}

	public void setProductLimit(BigDecimal productLimit) {
		this.productLimit = productLimit;
	}

	public Date getProductExpiryDate() {
		if (productExpiryDate == null) {
			productExpiryDate = parseJulianDate(getProductExpiryDateStr());
		}
		return productExpiryDate;
	}

	public void setProductExpiryDate(Date productExpiryDate) {
		this.productExpiryDate = productExpiryDate;
	}

	public String getProductExpiryDateStr() {
		return productExpiryDateStr;
	}

	public void setProductExpiryDateStr(String productExpiryDateStr) {
		if (productExpiryDateStr != null) {
			productExpiryDateStr = productExpiryDateStr.trim();
		}
		this.productExpiryDateStr = productExpiryDateStr;
	}

	public Integer getProductAgeLimit() {
		return productAgeLimit;
	}

	public void setProductAgeLimit(Integer productAgeLimit) {
		this.productAgeLimit = productAgeLimit;
	}

	public Integer getPaymentFrequency() {
		return paymentFrequency;
	}

	public void setPaymentFrequency(Integer paymentFrequency) {
		this.paymentFrequency = paymentFrequency;
	}

	public String getPaymentFrequencyCode() {
		return paymentFrequencyCode;
	}

	public void setPaymentFrequencyCode(String paymentFrequencyCode) {
		this.paymentFrequencyCode = paymentFrequencyCode;
	}

	public Integer getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(Integer paymentCode) {
		this.paymentCode = paymentCode;
	}

//	public String getGratuityFinancing() {
//		return gratuityFinancing;
//	}
//
//	public void setGratuityFinancing(String gratuityFinancing) {
//		this.gratuityFinancing = gratuityFinancing;
//	}

	/****** Methods from ISynchronizer ******/
	public String[] getMatchingProperties() {
		return MATCHING_PROPERTIES;
	}

	public String[] getIgnoreProperties() {
		return IGNORED_PROPERTIES;
	}

	public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
		setStatus(ICMSConstant.STATE_ACTIVE);
		setLastUpdatedDate(new Date());

		trimStringAttributes();
		defaultSource();
	}

	public void updatePropertiesForDelete(IParameterProperty paramProperty) {
		setStatus(ICMSConstant.STATE_DELETED);
		setLastUpdatedDate(new Date());

		trimStringAttributes();
		defaultSource();
	}

	private void trimStringAttributes() {
		if (getLoanType() != null) {
			setLoanType(getLoanType().trim());
		}
		if (getCurrency() != null) {
			setCurrency(getCurrency().trim());
		}
		if (getDescription() != null) {
			setDescription(getDescription().trim());
		}
		if (getGroupCode() != null) {
			setGroupCode(getGroupCode().trim());
		}
		if (getGroupDescription() != null) {
			setGroupDescription(getGroupDescription().trim());
		}
		if (getCommericalRetailIndicator() != null) {
			setCommericalRetailIndicator(getCommericalRetailIndicator().trim());
		}
		if (getStaffIndicator() != null) {
			setStaffIndicator(getStaffIndicator().trim());
		}
		if (getAccountIndicator() != null) {
			setAccountIndicator(getAccountIndicator().trim());
		}
		if (getTierLoanIndicator() != null) {
			setTierLoanIndicator(getTierLoanIndicator().trim());
		}
		if (getSptfLoanType() != null) {
			setSptfLoanType(getSptfLoanType().trim());
		}
		if (getPrimeRateNumber() != null) {
			setPrimeRateNumber(getPrimeRateNumber().trim());
		}

		if (getInterestBase() != null) {
			setInterestBase(getInterestBase().trim());
		}

		if (getConceptCode() != null) {
			setConceptCode(getConceptCode().trim());
		}

		if (getProductGroup() != null) {
			setProductGroup(getProductGroup().trim());
		}

		if (getDealerProductIndicator() != null) {
			setDealerProductIndicator(getDealerProductIndicator().trim());
		}

		if (getPaymentFrequencyCode() != null) {
			setPaymentFrequencyCode(getPaymentFrequencyCode().trim());
		}

//		if (getGratuityFinancing() != null) {
//			setGratuityFinancing(getGratuityFinancing().trim());
//		}

        if (getVarRate() != null) {
            setVarRate(getVarRate().trim());
        }

        if (getRebateMethodBank() != null) {
            setRebateMethodBank(getRebateMethodBank().trim());
        }

//        if (getPromoPackageCode() != null) {
//            setPromoPackageCode(getPromoPackageCode().trim());
//        }
	}

	public void defaultSource() {
		// nothing happen here
	}

	private Date parseJulianDate(String dateStr) {
		Date parsedDate = null;
		if (dateStr != null && dateStr.length() == 7 && !("9999999").equals(dateStr)) {
			parsedDate = DateUtil.parseDate("yyyyDDD", dateStr);
		}

		return parsedDate;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currency == null) ? 0 : currency.hashCode());
		result = prime * result + ((loanType == null) ? 0 : loanType.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		OBHostProductType other = (OBHostProductType) obj;
		if (currency == null) {
			if (other.currency != null) {
				return false;
			}
		}
		else if (!currency.equals(other.currency)) {
			return false;
		}
		if (loanType == null) {
			if (other.loanType != null) {
				return false;
			}
		}
		else if (!loanType.equals(other.loanType)) {
			return false;
		}
		if (source == null) {
			if (other.source != null) {
				return false;
			}
		}
		else if (!source.equals(other.source)) {
			return false;
		}
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		}
		else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

    public String getVarRate() {
        return varRate;
    }

    public void setVarRate(String varRate) {
        this.varRate = varRate;
    }

    public String getRebateMethodBank() {
        return rebateMethodBank;
    }

    public void setRebateMethodBank(String rebateMethodBank) {
        this.rebateMethodBank = rebateMethodBank;
    }

//    public String getPromoPackageCode() {
//        return promoPackageCode;
//    }
//
//    public void setPromoPackageCode(String promoPackageCode) {
//        this.promoPackageCode = promoPackageCode;
//    }
}
