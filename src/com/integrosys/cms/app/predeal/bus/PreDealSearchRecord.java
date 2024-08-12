/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealSearchRecord
 *
 * Created on 6:00:57 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 23, 2007 Time: 6:00:57 PM
 */
public class PreDealSearchRecord implements Serializable {
	private long feedId;

	private boolean isInSuspended;

	private boolean isSuspended;

	private String shareStatus;

	private String stockCode;

	private String name;

	private String ric;

	private Amount updatePrice;

	private Date lastUpdateDate;

	private long totalUnits;

	private long cmsActualHolding;

	private long earmarkHolding;

	private long earmarkCurrent;

	private String isin_code;

	private long listedShareQuantity;

	private String groupTypeCode;

	private double maxCollCapFi;

	private double maxCollCapNonFi;

	private double quotaCollCapFi;

	private double quotaCollCapNonFi;

	private boolean isFi;

	private String stockExchangeName;

	private String stockExchangeCode;

	private String boardType;

	private String stockExchangeCountry;

	private String groupStockType;

	private Date expiryDate;

	private long groupCmsActualHolding;

	private long groupEarmarkHolding;

	private long groupEarmarkCurrent;

	private long groupTotalUnits;

	private double groupMaxCollCapFi;

	private double groupMaxCollCapNonFi;

	private double groupQuotaCollCapFi;

	private double groupQuotaCollCapNonFi;

	private Amount parValue;

	/**
	 * @return the parValue
	 */
	public Amount getParValue() {
		return parValue;
	}

	/**
	 * @param parValue the parValue to set
	 */
	public void setParValue(Amount parValue) {
		this.parValue = parValue;
	}

	/**
	 * @return the groupTotalUnits
	 */
	public long getGroupTotalUnits() {
		return groupTotalUnits;
	}

	/**
	 * @param groupTotalUnits the groupTotalUnits to set
	 */
	public void setGroupTotalUnits(long groupTotalUnits) {
		this.groupTotalUnits = groupTotalUnits;
	}

	public long getCmsActualHolding() {
		return cmsActualHolding;
	}

	public void setCmsActualHolding(long cmsActualHolding) {
		this.cmsActualHolding = cmsActualHolding;
	}

	public long getEarmarkCurrent() {
		return earmarkCurrent;
	}

	public void setEarmarkCurrent(long earmarkCurrent) {
		this.earmarkCurrent = earmarkCurrent;
	}

	public long getEarmarkHolding() {
		return earmarkHolding;
	}

	public void setEarmarkHolding(long earmarkHolding) {
		this.earmarkHolding = earmarkHolding;
	}

	public long getFeedId() {
		return feedId;
	}

	public void setFeedId(long feedId) {
		this.feedId = feedId;
	}

	public boolean isInSuspended() {
		return isIsInSuspended();
	}

	public void setInSuspended(boolean inSuspended) {
		setIsInSuspended(inSuspended);
	}

	public boolean isSuspended() {
		return isIsSuspended();
	}

	public void setSuspended(boolean suspended) {
		setIsSuspended(suspended);
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRic() {
		return ric;
	}

	public void setRic(String ric) {
		this.ric = ric;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public String getStockCode() {
		return stockCode;
	}

	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	public long getTotalUnits() {
		return totalUnits;
	}

	public void setTotalUnits(long totalUnits) {
		this.totalUnits = totalUnits;
	}

	public Amount getUpdatePrice() {
		return updatePrice;
	}

	public void setUpdatePrice(Amount updatePrice) {
		this.updatePrice = updatePrice;
	}

	public String getIsin_code() {
		return isin_code;
	}

	public void setIsin_code(String isin_code) {
		this.isin_code = isin_code;
	}

	public long getListedShareQuantity() {
		return listedShareQuantity;
	}

	public void setListedShareQuantity(long listedShareQuantity) {
		this.listedShareQuantity = listedShareQuantity;
	}

	public String getGroupTypeCode() {
		return groupTypeCode;
	}

	public void setGroupTypeCode(String groupTypeCode) {
		this.groupTypeCode = groupTypeCode;
	}

	public double getMaxCollCapFi() {
		return maxCollCapFi;
	}

	public void setMaxCollCapFi(double maxCollCapFi) {
		this.maxCollCapFi = maxCollCapFi;
	}

	public double getMaxCollCapNonFi() {
		return maxCollCapNonFi;
	}

	public void setMaxCollCapNonFi(double maxCollCapNonFi) {
		this.maxCollCapNonFi = maxCollCapNonFi;
	}

	public double getQuotaCollCapFi() {
		return quotaCollCapFi;
	}

	public void setQuotaCollCapFi(double quotaCollCapFi) {
		this.quotaCollCapFi = quotaCollCapFi;
	}

	public double getQuotaCollCapNonFi() {
		return quotaCollCapNonFi;
	}

	public void setQuotaCollCapNonFi(double quotaCollCapNonFi) {
		this.quotaCollCapNonFi = quotaCollCapNonFi;
	}

	public boolean getIsFi() {
		return isIsFi();
	}

	public void setIsFi(boolean isFi) {
		this.isFi = isFi;
	}

	public boolean isIsInSuspended() {
		return isInSuspended;
	}

	public void setIsInSuspended(boolean isInSuspended) {
		this.isInSuspended = isInSuspended;
	}

	public boolean isIsSuspended() {
		return isSuspended;
	}

	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public boolean isIsFi() {
		return isFi;
	}

	public String getStockExchangeName() {
		return stockExchangeName;
	}

	public void setStockExchangeName(String stockExchangeName) {
		this.stockExchangeName = stockExchangeName;
	}

	public String getStockExchangeCode() {
		return stockExchangeCode;
	}

	public void setStockExchangeCode(String stockExchangeCode) {
		this.stockExchangeCode = stockExchangeCode;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getStockExchangeCountry() {
		return stockExchangeCountry;
	}

	public void setStockExchangeCountry(String stockExchangeCountry) {
		this.stockExchangeCountry = stockExchangeCountry;
	}

	public String getGroupStockType() {
		return groupStockType;
	}

	public void setGroupStockType(String groupStockType) {
		this.groupStockType = groupStockType;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the groupCmsActualHolding
	 */
	public long getGroupCmsActualHolding() {
		return groupCmsActualHolding;
	}

	/**
	 * @param groupCmsActualHolding the groupCmsActualHolding to set
	 */
	public void setGroupCmsActualHolding(long groupCmsActualHolding) {
		this.groupCmsActualHolding = groupCmsActualHolding;
	}

	/**
	 * @return the groupEarmarkHolding
	 */
	public long getGroupEarmarkHolding() {
		return groupEarmarkHolding;
	}

	/**
	 * @param groupEarmarkHolding the groupEarmarkHolding to set
	 */
	public void setGroupEarmarkHolding(long groupEarmarkHolding) {
		this.groupEarmarkHolding = groupEarmarkHolding;
	}

	/**
	 * @return the groupEarmarkCurrent
	 */
	public long getGroupEarmarkCurrent() {
		return groupEarmarkCurrent;
	}

	/**
	 * @param groupEarmarkCurrent the groupEarmarkCurrent to set
	 */
	public void setGroupEarmarkCurrent(long groupEarmarkCurrent) {
		this.groupEarmarkCurrent = groupEarmarkCurrent;
	}

	/**
	 * @return the groupMaxCollCapFi
	 */
	public double getGroupMaxCollCapFi() {
		return groupMaxCollCapFi;
	}

	/**
	 * @param groupMaxCollCapFi the groupMaxCollCapFi to set
	 */
	public void setGroupMaxCollCapFi(double groupMaxCollCapFi) {
		this.groupMaxCollCapFi = groupMaxCollCapFi;
	}

	/**
	 * @return the groupMaxCollCapNonFi
	 */
	public double getGroupMaxCollCapNonFi() {
		return groupMaxCollCapNonFi;
	}

	/**
	 * @param groupMaxCollCapNonFi the groupMaxCollCapNonFi to set
	 */
	public void setGroupMaxCollCapNonFi(double groupMaxCollCapNonFi) {
		this.groupMaxCollCapNonFi = groupMaxCollCapNonFi;
	}

	/**
	 * @return the groupQuotaCollCapFi
	 */
	public double getGroupQuotaCollCapFi() {
		return groupQuotaCollCapFi;
	}

	/**
	 * @param groupQuotaCollCapFi the groupQuotaCollCapFi to set
	 */
	public void setGroupQuotaCollCapFi(double groupQuotaCollCapFi) {
		this.groupQuotaCollCapFi = groupQuotaCollCapFi;
	}

	/**
	 * @return the groupQuotaCollCapNonFi
	 */
	public double getGroupQuotaCollCapNonFi() {
		return groupQuotaCollCapNonFi;
	}

	/**
	 * @param groupQuotaCollCapNonFi the groupQuotaCollCapNonFi to set
	 */
	public void setGroupQuotaCollCapNonFi(double groupQuotaCollCapNonFi) {
		this.groupQuotaCollCapNonFi = groupQuotaCollCapNonFi;
	}

}
