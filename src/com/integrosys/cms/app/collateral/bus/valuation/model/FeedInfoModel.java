/*
 * Created on May 23, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class FeedInfoModel implements Serializable {

    public static final String UNDEFINED_AMT_TO_USE = "UNDEFINED_AMT_TO_USE";

    private String isinCode;
	private String ricCode;
    private String stockCode;
    private String stockType;       //COMMON_CODE_CATEGORY_ENTRY.CATEGORY_CODE='SHARE_TYPE'
    private Amount unitPrice;
	private Amount prevQuarterPrice;
	private String boardType;
	private String shareStatus;
	private String prevShareStatus;
	private boolean isSuspended;
	private Date expiredDate;

    private Amount maxPriceCap;
    private Amount dailyRunningAvg;
    private Amount monthEndRunningAvg;
    private Amount perAmount;
    private Date launchDate;

    private String amtToUseStr = UNDEFINED_AMT_TO_USE;
    private Amount amtToUseForValuation;

    /**
	 * @return Returns the isinCode.
	 */
	public String getIsinCode() {
		return isinCode;
	}

	/**
	 * @param isinCode The isinCode to set.
	 */
	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	/**
	 * @return Returns the prevQuarterPrice.
	 */
	public Amount getPrevQuarterPrice() {
		return prevQuarterPrice;
	}

	/**
	 * @param prevQuarterPrice The prevQuarterPrice to set.
	 */
	public void setPrevQuarterPrice(Amount prevQuarterPrice) {
		this.prevQuarterPrice = prevQuarterPrice;
	}

	/**
	 * @return Returns the ricCode.
	 */
	public String getRicCode() {
		return ricCode;
	}

	/**
	 * @param ricCode The ricCode to set.
	 */
	public void setRicCode(String ricCode) {
		this.ricCode = ricCode;
	}

    /**
     * @return return the stock code
     */
    public String getStockCode() {
        return stockCode;
    }

    /**
     * @param stockCode Set the stock code
     */
    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }


    public String getStockType() {
        return stockType;
    }

    public void setStockType(String stockType) {
        this.stockType = stockType;
    }

    /**
	 * @return Returns the unitPrice.
	 */
	public Amount getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice The unitPrice to set.
	 */
	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return Returns the boardType.
	 */
	public String getBoardType() {
		return boardType;
	}

	/**
	 * @param boardType The boardType to set.
	 */
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(String shareStatus) {
		this.shareStatus = shareStatus;
	}

	public String getPrevShareStatus() {
		return prevShareStatus;
	}

	public void setPrevShareStatus(String prevShareStatus) {
		this.prevShareStatus = prevShareStatus;
	}

	public boolean getIsSuspended() {
		return isSuspended;
	}

	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}

	public Date getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
	}


    public Amount getMaxPriceCap() {
        return maxPriceCap;
    }

    public void setMaxPriceCap(Amount maxPriceCap) {
        this.maxPriceCap = maxPriceCap;
    }

    public Amount getDailyRunningAvg() {
        return dailyRunningAvg;
    }

    public void setDailyRunningAvg(Amount dailyRunningAvg) {
        this.dailyRunningAvg = dailyRunningAvg;
    }

    public Amount getMonthEndRunningAvg() {
        return monthEndRunningAvg;
    }

    public void setMonthEndRunningAvg(Amount monthEndRunningAvg) {
        this.monthEndRunningAvg = monthEndRunningAvg;
    }

    public Amount getPerAmount() {
        return perAmount;
    }

    public void setPerAmount(Amount perAmount) {
        this.perAmount = perAmount;
    }

    public Date getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(Date launchDate) {
        this.launchDate = launchDate;
    }

    public String getAmtToUseStr() {
        return amtToUseStr;
    }

    public void setAmtToUseStr(String amtToUseStr) {
        this.amtToUseStr = amtToUseStr;
    }

    public Amount getAmtToUseForValuation() {
        return amtToUseForValuation;
    }

    public void setAmtToUseForValuation(Amount amtToUseForValuation) {
        this.amtToUseForValuation = amtToUseForValuation;
    }

}
