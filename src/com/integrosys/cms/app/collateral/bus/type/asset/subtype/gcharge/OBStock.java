/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBStock.java,v 1.22 2005/06/07 03:36:50 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Stocks of the Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2005/06/07 03:36:50 $ Tag: $Name: $
 */
public class OBStock extends OBGeneralChargeSubType implements IStock {

	private static final String ID_PREFIX = "STK";

	private static final char ID_FILLER = '0';

	private static int ID_MAX_LENGTH = 10;

	private static int DIVIDE_SCALE = 2;

	private long assetGCStockID = ICMSConstant.LONG_MIN_VALUE;

	private String stockID;

	private Amount creditorAmt;

	private Amount rawMaterialAmt;

	private double rawMaterialMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount finishGoodsAmt;

	private double finishGoodsMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount workProgressAmt;

	private double workProgressMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount goodsTransitAmt;

	private double goodsTransitMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount storesSparesAmt;

	private double storesSparesMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Amount otherMerchandiseAmt;

	private double otherMerchandiseMargin = ICMSConstant.DOUBLE_INVALID_VALUE;

	private boolean physicalInspectionDone;

	private int physicalInspectionFreq = ICMSConstant.INT_INVALID_VALUE;

	private String physicalInspectionFreqUnit;

	private Date lastPhysicalInspectDate;

	private Date nextPhysicalInspectDate;

	private Amount recoverableAmt;

	/**
	 * Default Constructor.
	 */
	public OBStock() {
	}

	/**
	 * Construct the object from its interface.
	 * @param obj is of type IStock
	 */
	public OBStock(IStock obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Generate new stock id.
	 * 
	 * @param index
	 * @return
	 */
	public static String generateNewID(int index) {
		return GeneralChargeUtil.generateNewID(ID_PREFIX, ID_FILLER, ID_MAX_LENGTH, index);
	}

	/**
	 * Get ref ID for the stock.
	 * 
	 * @return String
	 */
	public String getID() {
		return getStockID();
	}

	public long getAssetGCStockID() {
		return assetGCStockID;
	}

	public void setAssetGCStockID(long assetGCStockID) {
		this.assetGCStockID = assetGCStockID;
	}

	/**
	 * Get stockID.
	 * 
	 * @return String
	 */
	public String getStockID() {
		return this.stockID;
	}

	/**
	 * Set stockID.
	 * 
	 * @param stockID is of type String
	 */
	public void setStockID(String stockID) {
		this.stockID = stockID;
	}

	/**
	 * Get creditor amount.
	 * 
	 * @return Amount
	 */
	public Amount getCreditorAmt() {
		return creditorAmt;
	}

	public void setCreditorAmt(Amount creditorAmt) {
		this.creditorAmt = creditorAmt;
	}

	/**
	 * Get Is Physical Inspection Done
	 * 
	 * @return boolean
	 */
	public boolean getPhysicalInspectionDone() {
		return physicalInspectionDone;
	}

	public void setPhysicalInspectionDone(boolean physicalInspectionDone) {
		this.physicalInspectionDone = physicalInspectionDone;
	}

	/**
	 * Get Last Physical Inspection Date
	 * 
	 * @return Date
	 */
	public Date getLastPhysicalInspectDate() {
		return lastPhysicalInspectDate;
	}

	public void setLastPhysicalInspectDate(Date lastPhysicalInspectDate) {
		this.lastPhysicalInspectDate = lastPhysicalInspectDate;
	}

	/**
	 * Get Next Physical Inspection Date
	 * 
	 * @return Date
	 */
	public Date getNextPhysicalInspectDate() {
		return nextPhysicalInspectDate;
	}

	public void setNextPhysicalInspectDate(Date nextPhysicalInspectDate) {
		this.nextPhysicalInspectDate = nextPhysicalInspectDate;
	}

	/**
	 * Get Physical Inspection Frequency
	 * 
	 * @return int
	 */
	public int getPhysicalInspectionFreq() {
		return physicalInspectionFreq;
	}

	public void setPhysicalInspectionFreq(int physicalInspectionFreq) {
		this.physicalInspectionFreq = physicalInspectionFreq;
	}

	/**
	 * Get Physical Inspection Frequency Unit
	 * 
	 * @return String
	 */
	public String getPhysicalInspectionFreqUnit() {
		return physicalInspectionFreqUnit;
	}

	public void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit) {
		this.physicalInspectionFreqUnit = physicalInspectionFreqUnit;
	}

	/**
	 * Get Stock Recoverable Amount
	 * 
	 * @return Amount
	 */
	public Amount getRecoverableAmount() {
		return recoverableAmt;
	}

	public void setRecoverableAmount(Amount recoverableAmt) {
		this.recoverableAmt = recoverableAmt;
	}

	/**
	 * Get Raw Material Amount
	 * 
	 * @return Amount
	 */
	public Amount getRawMaterialAmt() {
		return rawMaterialAmt;
	}

	public void setRawMaterialAmt(Amount rawMaterialAmt) {
		this.rawMaterialAmt = rawMaterialAmt;
	}

	/**
	 * Get Finished Goods amount.
	 * 
	 * @return Amount
	 */
	public Amount getFinishGoodsAmt() {
		return finishGoodsAmt;
	}

	public void setFinishGoodsAmt(Amount finishGoodsAmt) {
		this.finishGoodsAmt = finishGoodsAmt;
	}

	/**
	 * Get Finished Goods margin.
	 * 
	 * @return double
	 */
	public double getFinishGoodsMargin() {
		return finishGoodsMargin;
	}

	public void setFinishGoodsMargin(double finishGoodsMargin) {
		this.finishGoodsMargin = finishGoodsMargin;
	}

	/**
	 * Get Goods in Transit amount.
	 * 
	 * @return Amount
	 */
	public Amount getGoodsTransitAmt() {
		return goodsTransitAmt;
	}

	public void setGoodsTransitAmt(Amount goodsTransitAmt) {
		this.goodsTransitAmt = goodsTransitAmt;
	}

	/**
	 * Get Goods in Transit margin.
	 * 
	 * @return double
	 */
	public double getGoodsTransitMargin() {
		return goodsTransitMargin;
	}

	public void setGoodsTransitMargin(double goodsTransitMargin) {
		this.goodsTransitMargin = goodsTransitMargin;
	}

	/**
	 * Get Raw Material Margin
	 * 
	 * @return double
	 */
	public double getRawMaterialMargin() {
		return rawMaterialMargin;
	}

	public void setRawMaterialMargin(double rawMaterialMargin) {
		this.rawMaterialMargin = rawMaterialMargin;
	}

	/**
	 * Get Stores Spares Amount
	 * 
	 * @return Amount
	 */
	public Amount getStoresSparesAmt() {
		return storesSparesAmt;
	}

	public void setStoresSparesAmt(Amount storesSparesAmt) {
		this.storesSparesAmt = storesSparesAmt;
	}

	/**
	 * Get Stores Spares Margin
	 * 
	 * @return double
	 */
	public double getStoresSparesMargin() {
		return storesSparesMargin;
	}

	public void setStoresSparesMargin(double storesSparesMargin) {
		this.storesSparesMargin = storesSparesMargin;
	}

	/**
	 * Get Work in progress Amount
	 * 
	 * @return Amount
	 */
	public Amount getWorkProgressAmt() {
		return workProgressAmt;
	}

	public void setWorkProgressAmt(Amount workProgressAmt) {
		this.workProgressAmt = workProgressAmt;
	}

	/**
	 * Get Work in progress Margin
	 * 
	 * @return double
	 */
	public double getWorkProgressMargin() {
		return workProgressMargin;
	}

	public void setWorkProgressMargin(double workProgressMargin) {
		this.workProgressMargin = workProgressMargin;
	}

	/**
	 * Get Other Merchandise Amount
	 * 
	 * @return Amount
	 */
	public Amount getOtherMerchandiseAmt() {
		return otherMerchandiseAmt;
	}

	public void setOtherMerchandiseAmt(Amount otherMerchandiseAmt) {
		this.otherMerchandiseAmt = otherMerchandiseAmt;
	}

	/**
	 * Get Other Merchandise Margin
	 * 
	 * @return double
	 */
	public double getOtherMerchandiseMargin() {
		return otherMerchandiseMargin;
	}

	public void setOtherMerchandiseMargin(double otherMerchandiseMargin) {
		this.otherMerchandiseMargin = otherMerchandiseMargin;
	}

	/**
	 * Get list of stock types.
	 * 
	 * @return String[]
	 */
	public String[] getStockType() {
		ArrayList types = new ArrayList(6);
		if (isContainRawMaterials()) {
			types.add(STOCK_TYPE_RAW_MATERIALS);
		}
		if (isContainFinishedGoods()) {
			types.add(STOCK_TYPE_FINISHED_GOODS);
		}
		if (isContainGoodsInTransit()) {
			types.add(STOCK_TYPE_GOODS_IN_TRANSIT);
		}
		if (isContainWorkInProgress()) {
			types.add(STOCK_TYPE_WORK_IN_PROGRESS);
		}
		if (isContainStoresAndSpares()) {
			types.add(STOCK_TYPE_STORES_AND_SPARES);
		}
		if (isContainOtherMerchandise()) {
			types.add(STOCK_TYPE_OTHER_MERCHANDISE);
		}
		return ((types == null) || (types.size() == 0)) ? new String[0] : (String[]) types.toArray(new String[types
				.size()]);
	}

	/**
	 * Get gross value for this stock
	 * 
	 * @return Amount denoting gross value of the stock
	 */
	public Amount getGrossValue() {
		return grossValue;
	}

	/**
	 * Set gross value for this stock
	 * 
	 * @param grossValue - Amount
	 */
	public void setGrossValue(Amount grossValue) {
		this.grossValue = grossValue;
	}

	////////////////////////////////////////////////////////////////////////////
	// //////
	// /////// Methods to calculate derived values
	// ////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	// //////
	/**
	 * Get calculated gross value for this stock. Adds up the gross amt for each
	 * stock type.
	 * 
	 * @return Amount denoting gross value of the stock
	 */
	public Amount getCalculatedGrossValue() {
		Amount derivedGrossValue = null;
		try {
			if (isContainRawMaterials()) {
				derivedGrossValue = add(derivedGrossValue, getRawMaterialAmt());
			}
			if (isContainFinishedGoods()) {
				derivedGrossValue = add(derivedGrossValue, getFinishGoodsAmt());
			}
			if (isContainGoodsInTransit()) {
				derivedGrossValue = add(derivedGrossValue, getGoodsTransitAmt());
			}
			if (isContainWorkInProgress()) {
				derivedGrossValue = add(derivedGrossValue, getWorkProgressAmt());
			}
			if (isContainStoresAndSpares()) {
				derivedGrossValue = add(derivedGrossValue, getStoresSparesAmt());
			}
			if (isContainOtherMerchandise()) {
				derivedGrossValue = add(derivedGrossValue, getOtherMerchandiseAmt());
			}
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating stock gross value for [" + getStockID() + "] : "
					+ e.toString());
		}
		return derivedGrossValue;
	}

	/**
	 * Get calculated gross value for this stock. Adds up the gross amt for each
	 * stock type.
	 * 
	 * @return Amount denoting gross value of the stock
	 */
	public Amount getCalculatedGrossValueLessCreditors(Amount grossValue) {
		if (grossValue == null) {
			return null;
		}
		Amount grossValueLessCreditor = null;
		try {
			grossValueLessCreditor = (getCreditorAmt() == null) ? grossValue : grossValue.subtract(getCreditorAmt());
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating stock gross value less creditors for [" + getStockID()
					+ "] : " + e.toString());
		}
		return grossValueLessCreditor;
	}

	/**
	 * Get calculated net value for this stock. Adds up the net amount for each
	 * stock type.
	 * 
	 * @return Amount
	 */
	public Amount getCalculatedNetValue() {
		Amount grossValue = getCalculatedGrossValue();
		if (grossValue == null) {
			return null;
		}
		Amount derivedNetValue = null;
		try {
			// if gross value <= creditor amt
			// net value = gross value - creditor amt (ignore margin)
			// else take into account margin for each subtype
			Amount grossLessCreditor = getCalculatedGrossValueLessCreditors(grossValue);
			if (grossLessCreditor.getAmount() <= 0) {
				derivedNetValue = grossLessCreditor;
			}
			else {
				Amount netAmt = getNetRawMaterialsAmt(grossValue);
				derivedNetValue = add(derivedNetValue, netAmt);
				netAmt = getNetFinishedGoodsAmt(grossValue);
				derivedNetValue = add(derivedNetValue, getNetFinishedGoodsAmt(grossValue));
				netAmt = getNetGoodsInTransitAmt(grossValue);
				derivedNetValue = add(derivedNetValue, getNetGoodsInTransitAmt(grossValue));
				netAmt = getNetWorkInProgressAmt(grossValue);
				derivedNetValue = add(derivedNetValue, getNetWorkInProgressAmt(grossValue));
				netAmt = getNetStoresAndSparesAmt(grossValue);
				derivedNetValue = add(derivedNetValue, getNetStoresAndSparesAmt(grossValue));
				netAmt = getNetOtherMerchandiseAmt(grossValue);
				derivedNetValue = add(derivedNetValue, getNetOtherMerchandiseAmt(grossValue));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Exception in calculating stock net value for [" + getStockID() + "] : "
					+ e.toString());
		}
		return derivedNetValue;
	}

	/**
	 * Get calculated average margin for this stock. Formula is netValue /
	 * grossValue
	 * 
	 * @return double
	 */
	public double getCalculatedMargin() {
		Amount grossValue = getGrossValue();
		Amount netValue = getNetValue();
		if ((grossValue == null) || (netValue == null)) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		double margin = ICMSConstant.DOUBLE_INVALID_VALUE;
		try {
			BigDecimal grossValueBD = grossValue.getAmountAsBigDecimal();
			BigDecimal netValueDB = netValue.getAmountAsBigDecimal();
			margin = (grossValueBD.equals(netValueDB)) ? 1d : netValueDB.divide(grossValueBD, DIVIDE_SCALE,
					BigDecimal.ROUND_HALF_EVEN).doubleValue();
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating stock avg margin for [" + getStockID() + "] : "
					+ e.toString());
		}
		return margin;
	}

	/**
	 * Helper method to check if the stock contains raw materials.
	 * 
	 * @return boolean
	 */
	private boolean isContainRawMaterials() {
		return ((getRawMaterialAmt() != null) && (getRawMaterialAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to check if the stock contains finished goods.
	 * 
	 * @return boolean
	 */
	private boolean isContainFinishedGoods() {
		return ((getFinishGoodsAmt() != null) && (getFinishGoodsAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to check if the stock contains goods-in-transit.
	 * 
	 * @return boolean
	 */
	private boolean isContainGoodsInTransit() {
		return ((getGoodsTransitAmt() != null) && (getGoodsTransitAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to check if the stock contains work-in-progress goods.
	 * 
	 * @return boolean
	 */
	private boolean isContainWorkInProgress() {
		return ((getWorkProgressAmt() != null) && (getWorkProgressAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to check if the stock contains stores and spares.
	 * 
	 * @return boolean
	 */
	private boolean isContainStoresAndSpares() {
		return ((getStoresSparesAmt() != null) && (getStoresSparesAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to check if the stock contains other merchandise.
	 * 
	 * @return boolean
	 */
	private boolean isContainOtherMerchandise() {
		return ((getOtherMerchandiseAmt() != null) && (getOtherMerchandiseAmt().getAmountAsDouble() > 0));
	}

	/**
	 * Helper method to get raw materials net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetRawMaterialsAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getRawMaterialAmt(), getRawMaterialMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get finished goods net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetFinishedGoodsAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getFinishGoodsAmt(), getFinishGoodsMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get goods-in-transit net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetGoodsInTransitAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getGoodsTransitAmt(), getGoodsTransitMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get work-in-progress net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetWorkInProgressAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getWorkProgressAmt(), getWorkProgressMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get stores and spares net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetStoresAndSparesAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getStoresSparesAmt(), getStoresSparesMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get other merchandise net amount.
	 * @param stockGrossValue - Amount denoting gross value for this stock
	 * @return Amount
	 */
	private Amount getNetOtherMerchandiseAmt(Amount stockGrossValue) throws ChainedException {
		return getNetAmt(getOtherMerchandiseAmt(), getOtherMerchandiseMargin(), stockGrossValue);
	}

	/**
	 * Helper method to get net amount given the gross amount and margin for a
	 * stock subtype.
	 * 
	 * @param grossAmt - Amount
	 * @param margin - double
	 * @return Amount
	 * @throws ChainedException
	 */
	private Amount getNetAmt(Amount grossAmt, double margin, Amount stockGrossValue) throws ChainedException {
		if ((stockGrossValue == null) || (grossAmt == null) || (margin < 0)) {
			return null;
		}
		if (margin == 0) {
			return new Amount(new BigDecimal(0), grossAmt.getCurrencyCodeAsObject());
		}
		Amount aportionedCreditorAmt = getCreditorAmt(grossAmt, stockGrossValue);
		Amount grossAmtLessCreditorAmt = (aportionedCreditorAmt == null) ? grossAmt : grossAmt
				.subtract(aportionedCreditorAmt);
		return (margin == 1) ? grossAmtLessCreditorAmt : grossAmtLessCreditorAmt.multiply(new BigDecimal(margin));
	}

	/**
	 * Helper method to get a portion of the credit amount based on the
	 * aportioned gross amt and the total gross amount. Formula = (apotionedAmt
	 * / totalStockGrossValue) * creditorAmt
	 * 
	 * @param aportionedAmt
	 * @return
	 */
	private Amount getCreditorAmt(Amount aportionedAmt, Amount stockGrossValue) {
		if ((aportionedAmt == null) || (stockGrossValue == null) || (getCreditorAmt() == null)) {
			return null;
		}
		// formula is (apotionedAmt / stockGrossValue) * creditorAmt
		BigDecimal stockGrossValueBD = stockGrossValue.getAmountAsBigDecimal();
		BigDecimal aportionedAmtBD = aportionedAmt.getAmountAsBigDecimal();
		if (aportionedAmtBD.equals(stockGrossValueBD)) {
			return getCreditorAmt();
		}
		BigDecimal creditorAmtBD = getCreditorAmt().getAmountAsBigDecimal();
		if (stockGrossValueBD.equals(creditorAmtBD)) {
			return aportionedAmt;
		}
		// further calculation required, so used a bigger scale to prevent loss
		// of accuracy
		BigDecimal ratioBD = aportionedAmtBD.divide(stockGrossValueBD, 8, BigDecimal.ROUND_HALF_EVEN);
		return new Amount(ratioBD.multiply(creditorAmtBD), stockGrossValue.getCurrencyCodeAsObject());
	}

	/**
	 * Helper method to add amt to total.
	 * @param total - Amount
	 * @param amtToAdd - Amount
	 * @return Amount new total
	 */
	private Amount add(Amount total, Amount amtToAdd) throws ChainedException {
		if (total == null) {
			return amtToAdd;
		}
		return (amtToAdd == null) ? total : total.add(amtToAdd);
	}

	public static void main(String[] args) {
		try {
			testDerivedValues();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void testDerivedValues() {
		printStock(getTestStock1());
		printStock(getTestStock2());
		printStock(getTestStock3());
		printStock(getTestStock4());
		printStock(getTestStock5());
	}

	public static OBStock getTestStock1() {
		OBStock stock = new OBStock();
		stock.setStockID("STK0000000001");
		stock.setValuationCurrency("SGD");

		// valid stock type entry. amt and margin must come in a pair
		stock.setRawMaterialAmt(new Amount(5000, "SGD"));
		stock.setRawMaterialMargin(0.1);

		stock.setCreditorAmt(new Amount(500, "SGD"));

		stock.setGrossValue(stock.getCalculatedGrossValue());
		stock.setNetValue(stock.getCalculatedNetValue());

		return stock;
	}

	public static OBStock getTestStock2() {
		OBStock stock = new OBStock();
		stock.setStockID("STK0000000002");
		stock.setValuationCurrency("SGD");

		// valid stock type entry. amt and margin must come in a pair
		stock.setFinishGoodsAmt(new Amount(5000, "SGD"));
		stock.setFinishGoodsMargin(0);

		stock.setCreditorAmt(new Amount(500, "SGD"));

		stock.setGrossValue(stock.getCalculatedGrossValue());
		stock.setNetValue(stock.getCalculatedNetValue());

		return stock;
	}

	public static OBStock getTestStock3() {
		OBStock stock = new OBStock();
		stock.setStockID("STK0000000003");
		stock.setValuationCurrency("SGD");

		// valid stock type entry. amt and margin must come in a pair
		stock.setFinishGoodsAmt(new Amount(5000, "SGD"));
		stock.setFinishGoodsMargin(1);

		stock.setCreditorAmt(new Amount(500, "SGD"));

		stock.setGrossValue(stock.getCalculatedGrossValue());
		stock.setNetValue(stock.getCalculatedNetValue());

		return stock;
	}

	public static OBStock getTestStock4() {
		OBStock stock = new OBStock();
		stock.setStockID("STK0000000004");
		stock.setValuationCurrency("SGD");

		// invalid stock type entry
		stock.setFinishGoodsMargin(0.1);

		stock.setCreditorAmt(new Amount(500, "SGD"));

		stock.setGrossValue(stock.getCalculatedGrossValue());
		stock.setNetValue(stock.getCalculatedNetValue());

		return stock;
	}

	public static OBStock getTestStock5() {
		OBStock stock = new OBStock();
		stock.setStockID("STK0000000005");
		stock.setValuationCurrency("SGD");

		// valid stock type entry. amt and margin must come in a pair
		stock.setRawMaterialAmt(new Amount(5000, "SGD"));
		stock.setRawMaterialMargin(0.1);

		stock.setGrossValue(stock.getCalculatedGrossValue());
		stock.setNetValue(stock.getCalculatedNetValue());

		return stock;
	}

	public static void printStock(OBStock stock) {
		if (stock != null) {
			DefaultLogger.debug("OBStock",">>>> stock.id : " + stock.getStockID());
			DefaultLogger.debug("OBStock",">>>> stock.grossvalue : " + stock.getCalculatedGrossValue());
			DefaultLogger.debug("OBStock",">>>> stock.netvalue : " + stock.getCalculatedNetValue());
			DefaultLogger.debug("OBStock",">>>> stock.margin : " + stock.getCalculatedMargin());
		}
	}
}