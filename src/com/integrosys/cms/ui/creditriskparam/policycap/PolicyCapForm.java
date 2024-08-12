/*
Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.creditriskparam.policycap;

import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * 
 * ActionForm for PolicyCapAction.
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */

public class PolicyCapForm extends TrxContextForm implements Serializable {

	private String stockExchange = "";

	private String board[];

	private String maxTradeCapNonFI[];

	private String maxCollateralCapNonFI[];

	private String quotaCollateralCapNonFI[];

	private String maxCollateralCapFI[];

	private String quotaCollateralCapFI[];

	private String liquidMOA[];

	private String illiquidMOA[];

	private String maxPriceCap[];

	private String currency[];

	private String bankEntity = "";

	private String isBankGroup = "";

	private String search = "";

	/**
	 * @return the search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search the search to set
	 */
	public void setSearch(String search) {
		this.search = search;
	}

	public String[] getBoard() {
		return board;
	}

	public void setBoard(String[] board) {
		this.board = board;
	}

	public String[] getMaxTradeCapNonFI() {
		return maxTradeCapNonFI;
	}

	public void setMaxTradeCapNonFI(String[] maxTradeCapNonFI) {
		this.maxTradeCapNonFI = maxTradeCapNonFI;
	}

	public String[] getMaxCollateralCapNonFI() {
		return maxCollateralCapNonFI;
	}

	public void setMaxCollateralCapNonFI(String[] maxCollateralCapNonFI) {
		this.maxCollateralCapNonFI = maxCollateralCapNonFI;
	}

	public String[] getQuotaCollateralCapNonFI() {
		return quotaCollateralCapNonFI;
	}

	/**
	 * @return the bankEntity
	 */
	public String getBankEntity() {
		return bankEntity;
	}

	/**
	 * @param bankEntity the bankEntity to set
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	public void setQuotaCollateralCapNonFI(String[] quotaCollateralCapNonFI) {
		this.quotaCollateralCapNonFI = quotaCollateralCapNonFI;
	}

	public String[] getMaxCollateralCapFI() {
		return maxCollateralCapFI;
	}

	public void setMaxCollateralCapFI(String[] maxCollateralCapFI) {
		this.maxCollateralCapFI = maxCollateralCapFI;
	}

	public String[] getQuotaCollateralCapFI() {
		return quotaCollateralCapFI;
	}

	public void setQuotaCollateralCapFI(String[] quotaCollateralCapFI) {
		this.quotaCollateralCapFI = quotaCollateralCapFI;
	}

	public String[] getLiquidMOA() {
		return liquidMOA;
	}

	public void setLiquidMOA(String[] liquidMOA) {
		this.liquidMOA = liquidMOA;
	}

	public String[] getIlliquidMOA() {
		return illiquidMOA;
	}

	public void setIlliquidMOA(String[] illiquidMOA) {
		this.illiquidMOA = illiquidMOA;
	}

	public String[] getMaxPriceCap() {
		return maxPriceCap;
	}

	public void setMaxPriceCap(String[] maxPriceCap) {
		this.maxPriceCap = maxPriceCap;
	}

	public String[] getCurrency() {
		return currency;
	}

	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	public String getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(String stockExchange) {
		this.stockExchange = stockExchange;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "policyCapGroupMap", "com.integrosys.cms.ui.creditriskparam.policycap.PolicyCapGroupMapper" },
				{ "policyCapTrxGroupValue", "com.integrosys.cms.ui.creditriskparam.policycap.PolicyCapGroupMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" }, };
		return input;
	}

	/**
	 * @return the isBankGroup
	 */
	public String getIsBankGroup() {
		return isBankGroup;
	}

	/**
	 * @param isBankGroup the isBankGroup to set
	 */
	public void setIsBankGroup(String isBankGroup) {
		this.isBankGroup = isBankGroup;
	}
}
