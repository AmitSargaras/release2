/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/stockitem/StockItemForm.java,v 1.4 2005/06/10 06:27:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem;

import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeForm;

/**
 * Mapper for Stock Item
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/06/10 06:27:56 $ Tag: $Name: $
 */

public class StockItemForm extends GeneralChargeSubTypeForm {

	private String stockID = "";

	// stock type
	private String rawMaterialsAmt = "";

	private String rawMaterialsMargin = "";

	private String goodsInTransitAmt = "";

	private String goodsInTransitMargin = "";

	private String finishedGoodsAmt = "";

	private String finishedGoodsMargin = "";

	private String storesSparesAmt = "";

	private String storesSparesMargin = "";

	private String wipAmt = "";

	private String wipMargin = "";

	private String othMerchandiseAmt = "";

	private String othMerchandiseMargin = "";

	private String totalStockTypeAmt = "";

	private String weightedAverageMargin = "";

	// creditor details
	private String creditor = "";

	private String creditorCMS = "";

	// Inspection Details
	private String physicalInspection = "";

	private String phyInsFreqNum = "";

	private String phyInsFreqUnit = "";

	private String lastPhyInsDate = "";

	private String nextPhyInsDate = "";

	public String getStockID() {
		return this.stockID;
	}

	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	// stock type getter and setter methods
	public String getRawMaterialsAmt() {
		return this.rawMaterialsAmt;
	}

	public void setRawMaterialsAmt(String rawMaterialsAmt) {
		this.rawMaterialsAmt = rawMaterialsAmt;
	}

	public String getRawMaterialsMargin() {
		return this.rawMaterialsMargin;
	}

	public void setRawMaterialsMargin(String rawMaterialsMargin) {
		this.rawMaterialsMargin = rawMaterialsMargin;
	}

	public String getGoodsInTransitAmt() {
		return this.goodsInTransitAmt;
	}

	public void setGoodsInTransitAmt(String goodsInTransitAmt) {
		this.goodsInTransitAmt = goodsInTransitAmt;
	}

	public String getGoodsInTransitMargin() {
		return this.goodsInTransitMargin;
	}

	public void setGoodsInTransitMargin(String goodsInTransitMargin) {
		this.goodsInTransitMargin = goodsInTransitMargin;
	}

	public String getFinishedGoodsAmt() {
		return this.finishedGoodsAmt;
	}

	public void setFinishedGoodsAmt(String finishedGoodsAmt) {
		this.finishedGoodsAmt = finishedGoodsAmt;
	}

	public String getFinishedGoodsMargin() {
		return this.finishedGoodsMargin;
	}

	public void setFinishedGoodsMargin(String finishedGoodsMargin) {
		this.finishedGoodsMargin = finishedGoodsMargin;
	}

	public String getStoresSparesAmt() {
		return this.storesSparesAmt;
	}

	public void setStoresSparesAmt(String storesSparesAmt) {
		this.storesSparesAmt = storesSparesAmt;
	}

	public String getStoresSparesMargin() {
		return this.storesSparesMargin;
	}

	public void setStoresSparesMargin(String storesSparesMargin) {
		this.storesSparesMargin = storesSparesMargin;
	}

	public String getWipAmt() {
		return this.wipAmt;
	}

	public void setWipAmt(String wipAmt) {
		this.wipAmt = wipAmt;
	}

	public String getWipMargin() {
		return this.wipMargin;
	}

	public void setWipMargin(String wipMargin) {
		this.wipMargin = wipMargin;
	}

	public String getOthMerchandiseAmt() {
		return this.othMerchandiseAmt;
	}

	public void setOthMerchandiseAmt(String othMerchandiseAmt) {
		this.othMerchandiseAmt = othMerchandiseAmt;
	}

	public String getOthMerchandiseMargin() {
		return this.othMerchandiseMargin;
	}

	public void setOthMerchandiseMargin(String othMerchandiseMargin) {
		this.othMerchandiseMargin = othMerchandiseMargin;
	}

	public String getTotalStockTypeAmt() {
		return this.totalStockTypeAmt;
	}

	public void setTotalStockTypeAmt(String totalStockTypeAmt) {
		this.totalStockTypeAmt = totalStockTypeAmt;
	}

	public String getWeightedAverageMargin() {
		return this.weightedAverageMargin;
	}

	public void setWeightedAverageMargin(String weightedAverageMargin) {
		this.weightedAverageMargin = weightedAverageMargin;
	}

	// Creditor Details
	public String getCreditor() {
		return this.creditor;
	}

	public void setCreditor(String creditor) {
		this.creditor = creditor;
	}

	public String getCreditorCMS() {
		return this.creditorCMS;
	}

	public void setCreditorCMS(String creditorCMS) {
		this.creditorCMS = creditorCMS;
	}

	// Inspection Details
	public String getPhysicalInspection() {
		return this.physicalInspection;
	}

	public void setPhysicalInspection(String physicalInspection) {
		this.physicalInspection = physicalInspection;
	}

	public String getPhyInsFreqNum() {
		return this.phyInsFreqNum;
	}

	public void setPhyInsFreqNum(String phyInsFreqNum) {
		this.phyInsFreqNum = phyInsFreqNum;
	}

	public String getPhyInsFreqUnit() {
		return this.phyInsFreqUnit;
	}

	public void setPhyInsFreqUnit(String phyInsFreqUnit) {
		this.phyInsFreqUnit = phyInsFreqUnit;
	}

	public String getLastPhyInsDate() {
		return this.lastPhyInsDate;
	}

	public void setLastPhyInsDate(String lastPhyInsDate) {
		this.lastPhyInsDate = lastPhyInsDate;
	}

	public String getNextPhyInsDate() {
		return this.nextPhyInsDate;
	}

	public void setNextPhyInsDate(String nextPhyInsDate) {
		this.nextPhyInsDate = nextPhyInsDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "stockItemObj",
				"com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock.stockitem.StockItemMapper" }, };
		return input;
	}
}
