package com.integrosys.cms.app.creditriskparam.bus;

import java.util.Date;

/**
 * OBCreditRiskParam Purpose: Description:
 * 
 * @author $Author$
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */

public class OBCreditRiskParam implements ICreditRiskParam, IPriceFeedEntry {

	// ICreditRiskParam

	long parameterId;

	String parameterType;

	double marginOfAdvance;

	double maximumCap;

	String isIntSuspend;

	String status;

	String isLiquid;

	long versionTime;

	long feedId;

	long parameterRef;

	String paramBoardType;

	String paramShareStatus;

	// IPriceFeedEntry

	String ticker;

	String ric;

	String type;

	String countryCode;

	String exchange;

	double unitPrice;

	String currencyCode;

	String isinCode;

	String name;

	String fundManagerName;

	Date lastUpdatedDate;

	double offer;

	long creditRiskParamEntryID;

	long creditRiskParamEntryRef;

	long priceFeedversionTime;

	String blackListed;

	String suspended;

	String lastUpdatedBy;

	long totalOutstandingUnit;

	long feedGroupId;

	String fundManagerCode;

	String productCode;

	Date maturityDate;

	String rating;

	String stockType;

	String isAcceptable;

	String stockCode;

	String isFi;

	String boardType;

	String shareStatus;

	public long getParameterId() {
		return parameterId;
	}

	public void setParameterId(long parameterId) {
		this.parameterId = parameterId;
	}

	public String getParameterType() {
		return parameterType;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public double getMarginOfAdvance() {
		return marginOfAdvance;
	}

	public void setMarginOfAdvance(double marginOfAdvance) {
		this.marginOfAdvance = marginOfAdvance;
	}

	public double getMaximumCap() {
		return maximumCap;
	}

	public void setMaximumCap(double maximumCap) {
		this.maximumCap = maximumCap;
	}

	public String getIsIntSuspend() {
		return isIntSuspend;
	}

	public void setIsIntSuspend(String intSuspend) {
		isIntSuspend = intSuspend;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsLiquid() {
		return isLiquid;
	}

	public void setIsLiquid(String liquid) {
		isLiquid = liquid;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public long getParameterRef() {
		return parameterRef;
	}

	public void setParameterRef(long parameterRef) {
		this.parameterRef = parameterRef;
	}

	public String getParamBoardType() {
		return paramBoardType;
	}

	public void setParamBoardType(String paramBoardType) {
		this.paramBoardType = paramBoardType;
	}

	public String getParamShareStatus() {
		return paramShareStatus;
	}

	public void setParamShareStatus(String paramShareStatus) {
		this.paramShareStatus = paramShareStatus;
	}

	// IPriceFeedEntry

	public String getIntSuspend() {
		return isIntSuspend;
	}

	public void setIntSuspend(String intSuspend) {
		isIntSuspend = intSuspend;
	}

	public String getLiquid() {
		return isLiquid;
	}

	public void setLiquid(String liquid) {
		isLiquid = liquid;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFundManagerName() {
		return fundManagerName;
	}

	public void setFundManagerName(String fundManagerName) {
		this.fundManagerName = fundManagerName;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public double getOffer() {
		return offer;
	}

	public void setOffer(double offer) {
		this.offer = offer;
	}

	public long getCreditRiskParamEntryID() {
		return creditRiskParamEntryID;
	}

	public void setCreditRiskParamEntryID(long creditRiskParamEntryID) {
		this.creditRiskParamEntryID = creditRiskParamEntryID;
	}

	public long getCreditRiskParamEntryRef() {
		return creditRiskParamEntryRef;
	}

	public void setCreditRiskParamEntryRef(long creditRiskParamEntryRef) {
		this.creditRiskParamEntryRef = creditRiskParamEntryRef;
	}

	public String getBlackListed() {
		return blackListed;
	}

	public void setBlackListed(String blackListed) {
		this.blackListed = blackListed;
	}

	public String getSuspended() {
		return suspended;
	}

	public void setSuspended(String suspended) {
		this.suspended = suspended;
	}

	public long getPriceFeedversionTime() {
		return priceFeedversionTime;
	}

	public void setPriceFeedversionTime(long priceFeedversionTime) {
		this.priceFeedversionTime = priceFeedversionTime;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public long getTotalOutstandingUnit() {
		return totalOutstandingUnit;
	}

	public void setTotalOutstandingUnit(long totalOutstandingUnit) {
		this.totalOutstandingUnit = totalOutstandingUnit;
	}

	public long getFeedGroupId() {
		return feedGroupId;
	}

	public void setFeedGroupId(long feedGroupId) {
		this.feedGroupId = feedGroupId;
	}

	public String getFundManagerCode() {
		return fundManagerCode;
	}

	public void setFundManagerCode(String fundManagerCode) {
		this.fundManagerCode = fundManagerCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getIsAcceptable() {
		return isAcceptable;
	}

	public void setIsAcceptable(String acceptable) {
		isAcceptable = acceptable;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public String getIsFi() {
		return isFi;
	}

	public void setIsFi(String fi) {
		isFi = fi;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}
}
