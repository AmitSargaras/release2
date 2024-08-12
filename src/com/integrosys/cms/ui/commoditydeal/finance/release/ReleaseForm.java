/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/release/ReleaseForm.java,v 1.2 2004/09/08 10:02:44 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.release;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/09/08 10:02:44 $ Tag: $Name: $
 */

public class ReleaseForm extends CommonForm implements Serializable {
	private String originalStock = "";

	private String actualDealUOM = "";

	private String[] warehouseRec;

	private String[] selectedWarehouseRec;

	private String partialReleaseDate = "";

	private String quantityRelease = "";

	private String quantityReleaseUOM = "";

	// selected warehouse receipt
	private String[] receiptNo;

	private String[] actualQty;

	private String[] qtyReleasedSoFar;

	private String[] balanceQtyToBeReleased;

	private String[] qtyToReleased;

	private String[] balance;

	private String totalQtyRelForDeal = "";

	private String totalBalanceQtyToBeReleased = "";

	public String getOriginalStock() {
		return originalStock;
	}

	public void setOriginalStock(String originalStock) {
		this.originalStock = originalStock;
	}

	public String getActualDealUOM() {
		return actualDealUOM;
	}

	public void setActualDealUOM(String actualDealUOM) {
		this.actualDealUOM = actualDealUOM;
	}

	public String[] getWarehouseRec() {
		return this.warehouseRec;
	}

	public void setWarehouseRec(String[] warehouseRec) {
		this.warehouseRec = warehouseRec;
	}

	public String[] getSelectedWarehouseRec() {
		return this.selectedWarehouseRec;
	}

	public void setSelectedWarehouseRec(String[] selectedWarehouseRec) {
		this.selectedWarehouseRec = selectedWarehouseRec;
	}

	public String getPartialReleaseDate() {
		return this.partialReleaseDate;
	}

	public void setPartialReleaseDate(String partialReleaseDate) {
		this.partialReleaseDate = partialReleaseDate;
	}

	public String getQuantityRelease() {
		return this.quantityRelease;
	}

	public void setQuantityRelease(String quantityRelease) {
		this.quantityRelease = quantityRelease;
	}

	public String getQuantityReleaseUOM() {
		return quantityReleaseUOM;
	}

	public void setQuantityReleaseUOM(String quantityReleaseUOM) {
		this.quantityReleaseUOM = quantityReleaseUOM;
	}

	public String[] getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String[] receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String[] getActualQty() {
		return actualQty;
	}

	public void setActualQty(String[] actualQty) {
		this.actualQty = actualQty;
	}

	public String[] getQtyReleasedSoFar() {
		return qtyReleasedSoFar;
	}

	public void setQtyReleasedSoFar(String[] qtyReleasedSoFar) {
		this.qtyReleasedSoFar = qtyReleasedSoFar;
	}

	public String[] getBalanceQtyToBeReleased() {
		return balanceQtyToBeReleased;
	}

	public void setBalanceQtyToBeReleased(String[] balanceQtyToBeReleased) {
		this.balanceQtyToBeReleased = balanceQtyToBeReleased;
	}

	public String[] getQtyToReleased() {
		return qtyToReleased;
	}

	public void setQtyToReleased(String[] qtyToReleased) {
		this.qtyToReleased = qtyToReleased;
	}

	public String[] getBalance() {
		return balance;
	}

	public void setBalance(String[] balance) {
		this.balance = balance;
	}

	public String getTotalQtyRelForDeal() {
		return totalQtyRelForDeal;
	}

	public void setTotalQtyRelForDeal(String totalQtyRelForDeal) {
		this.totalQtyRelForDeal = totalQtyRelForDeal;
	}

	public String getTotalBalanceQtyToBeReleased() {
		return totalBalanceQtyToBeReleased;
	}

	public void setTotalBalanceQtyToBeReleased(String totalBalanceQtyToBeReleased) {
		this.totalBalanceQtyToBeReleased = totalBalanceQtyToBeReleased;
	}

	public String[][] getMapper() {
		String[][] input = { { "releaseObj", "com.integrosys.cms.ui.commoditydeal.finance.release.ReleaseMapper" }, };
		return input;
	}
}
