package com.integrosys.cms.app.creditriskparam.bus.policycap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class OBPolicyCap implements IPolicyCap {

	private long policyCapID = ICMSConstant.LONG_INVALID_VALUE;

	private String stockExchange = null;

	private String board = null;

	private float maxTradeCapNonFI;

	private float maxCollateralCapNonFI;

	private float quotaCollateralCapNonFI;

	private float maxCollateralCapFI;

	private float quotaCollateralCapFI;

	private float liquidMOA;

	private float illiquidMOA;

	private double maxPriceCap;

	private String currency = null;

	private Amount priceCap;

	private long groupID;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private long versionTime = ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBPolicyCap() {
	}

	public long getPolicyCapID() {
		return policyCapID;
	}

	public void setPolicyCapID(long policyCapID) {
		this.policyCapID = policyCapID;
	}

	public String getBoard() {
		return board;
	}

	public void setBoard(String board) {
		this.board = board;
	}

	public float getMaxTradeCapNonFI() {
		return maxTradeCapNonFI;
	}

	public void setMaxTradeCapNonFI(float cap) {
		maxTradeCapNonFI = cap;
	}

	public float getMaxCollateralCapNonFI() {
		return maxCollateralCapNonFI;
	}

	public void setMaxCollateralCapNonFI(float cap) {
		maxCollateralCapNonFI = cap;
	}

	public float getQuotaCollateralCapNonFI() {
		return quotaCollateralCapNonFI;
	}

	public void setQuotaCollateralCapNonFI(float cap) {
		quotaCollateralCapNonFI = cap;
	}

	public float getMaxCollateralCapFI() {
		return maxCollateralCapFI;
	}

	public void setMaxCollateralCapFI(float cap) {
		maxCollateralCapFI = cap;
	}

	public float getQuotaCollateralCapFI() {
		return quotaCollateralCapFI;
	}

	public void setQuotaCollateralCapFI(float cap) {
		quotaCollateralCapFI = cap;
	}

	public float getLiquidMOA() {
		return liquidMOA;
	}

	public void setLiquidMOA(float moa) {
		liquidMOA = moa;
	}

	public float getIlliquidMOA() {
		return illiquidMOA;
	}

	public void setIlliquidMOA(float moa) {
		illiquidMOA = moa;
	}

	public double getMaxPriceCap() {
		return maxPriceCap;
	}

	public void setMaxPriceCap(double cap) {
		maxPriceCap = cap;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Amount getPriceCap() {
		return priceCap;
	}

	public void setPriceCap(Amount priceCap) {
		this.priceCap = priceCap;
	}

	public long getGroupID() {
		return groupID;
	}

	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * Get cms common reference id across actual and staging tables.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set cms common reference id across actual and staging tables.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public long getVersionTime() {
		return versionTime;
	}

	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

}
