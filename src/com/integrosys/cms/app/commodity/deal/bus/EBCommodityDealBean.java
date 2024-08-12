/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/EBCommodityDealBean.java,v 1.56 2006/11/19 14:55:32 jzhan Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.common.ConversionKey;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.deal.bus.cash.EBDealCashDepositLocal;
import com.integrosys.cms.app.commodity.deal.bus.cash.EBDealCashDepositLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.cash.OBDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBCommodityTitleDocumentLocal;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBCommodityTitleDocumentLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBFinancingDocLocal;
import com.integrosys.cms.app.commodity.deal.bus.doc.EBFinancingDocLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBHedgePriceExtensionLocal;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBHedgePriceExtensionLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBReceiptReleaseLocal;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBReceiptReleaseLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBSettlementLocal;
import com.integrosys.cms.app.commodity.deal.bus.finance.EBSettlementLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBSettlement;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.EBPurchaseAndSalesDetailsLocal;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.EBPurchaseAndSalesDetailsLocalHome;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Entity bean implementation for Commodity Deal entity.
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.56 $
 * @since $Date: 2006/11/19 14:55:32 $ Tag: $Name: $
 */
public abstract class EBCommodityDealBean implements ICommodityDeal, EntityBean {
	/** The container assigned reference to the entity. */
	private EntityContext context;

	/** A list of methods to be excluded during create/update deal. */
	private static final String[] EXCLUDE_METHOD = new String[] { "getCommodityDealID" };

	/**
	 * Get the commodity deal id.
	 * 
	 * @return long
	 */
	public long getCommodityDealID() {
		return getEBCommodityDealID().longValue();
	}

	/**
	 * Set the commodity deal id.
	 * 
	 * @param commodityDealID of type long
	 */
	public void setCommodityDealID(long commodityDealID) {
		setEBCommodityDealID(new Long(commodityDealID));
	}

	/**
	 * Get deal number.
	 * 
	 * @return String
	 */
	public String getDealNo() {
		return getEBCommodityDealNo();
	}

	/**
	 * Set deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo) {
	}

	/**
	 * Get original face value.
	 * 
	 * @return Amount
	 */
	public Amount getOrigFaceValue() {
		if (getEBOrigFaceValue() == null) {
			return null;
		}
		return new Amount(getEBOrigFaceValue(), new CurrencyCode(getOrigFaceValueCcyCode()));
	}

	/**
	 * Set original face value.
	 * 
	 * @param origFaceValue of type Amount
	 */
	public void setOrigFaceValue(Amount origFaceValue) {
		setEBOrigFaceValue(origFaceValue == null ? null : origFaceValue.getAmountAsBigDecimal());
		setOrigFaceValueCcyCode(origFaceValue == null ? null : origFaceValue.getCurrencyCode());
	}

	/**
	 * Get commodity deal cmv.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		if (getEBCMV() == null) {
			return null;
		}
		return new Amount(getEBCMV(), new CurrencyCode(getCMVCcyCode()));
	}

	/**
	 * Set commodity deal cmv.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		setEBCMV(cmv == null ? null : cmv.getAmountAsBigDecimal());
		setCMVCcyCode(cmv == null ? null : cmv.getCurrencyCode());
	}

	/**
	 * Get commodity deal fsv.
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		if (getEBFSV() == null) {
			return null;
		}
		return new Amount(getEBFSV(), new CurrencyCode(getFSVCcyCode()));
	}

	/**
	 * Set commodity deal fsv.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv) {
		setEBFSV(fsv == null ? null : fsv.getAmountAsBigDecimal());
		setFSVCcyCode(fsv == null ? null : fsv.getCurrencyCode());
	}

	/**
	 * Get cash margin percentage.
	 * 
	 * @return double
	 */
	public double getCashMarginPct() {
		if (getEBCashMarginPct() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		return getEBCashMarginPct().doubleValue();
	}

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashMarginPct of type double
	 */
	public void setCashMarginPct(double cashMarginPct) {
		if (cashMarginPct == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBCashMarginPct(null);
		}
		else {
			setEBCashMarginPct(new Double(cashMarginPct));
		}
	}

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct() {
		if (getEBCashReqPct() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBCashReqPct().doubleValue();
		}
	}

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct) {
		if (cashReqPct == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBCashReqPct(null);
		}
		else {
			setEBCashReqPct(new Double(cashReqPct));
		}
	}

	/**
	 * Get financing percentage.
	 * 
	 * @return double
	 */
	public double getFinancingPct() {
		if (getEBFinancingPct() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBFinancingPct().doubleValue();
		}
	}

	/**
	 * Set financing percentage.
	 * 
	 * @param financingPct of type double
	 */
	public void setFinancingPct(double financingPct) {
		if (financingPct == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBFinancingPct(null);
		}
		else {
			setEBFinancingPct(new Double(financingPct));
		}
	}

	/**
	 * Gets contracted quantity of deal.
	 * 
	 * @return contracted quantity
	 */
	public Quantity getContractQuantity() {
		if ((getEBContractQty() == null) || (getEBContractQtyUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractQtyUOM());
		return new Quantity(getEBContractQty(), uom);
	}

	/**
	 * Sets contracted quantity to deal.
	 * 
	 * @param aQty - contracted quantity
	 */
	public void setContractQuantity(Quantity aQty) {
		setEBContractQty(aQty == null ? null : aQty.getQuantity());
		setEBContractQtyUOM(aQty == null ? null : aQty.getUnitofMeasure().getID());
	}

	/**
	 * Gets contracted quantity differential of deal.
	 * 
	 * @return contracted quantity differential
	 */
	public QuantityDifferential getContractQuantityDifferential() {
		if ((getEBContractQtyDifferential() == null) || (getEBContractQtyDifferentialUOM() == null)
				|| (getEBContractQtyDifferentialSign() == null)) {
			return null;
		}
		String sign = getEBContractQtyDifferentialSign();
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractQtyDifferentialUOM());
		return new QuantityDifferential(new Quantity(getEBContractQtyDifferential(), uom), sign);
	}

	/**
	 * Sets contracted quantity differential to deal.
	 * 
	 * @param aQtyDiff - contracted quantity differential
	 */
	public void setContractQuantityDifferential(QuantityDifferential aQtyDiff) {
		if ((aQtyDiff == null) || (aQtyDiff.getQuantity() == null) || (aQtyDiff.getQuantity().getQuantity() == null)
				|| (aQtyDiff.getSign() == null) || (aQtyDiff.getQuantity().getUnitofMeasure() == null)) {
			setEBContractQtyDifferential(null);
			setEBContractQtyDifferentialSign(null);
			setEBContractQtyDifferentialUOM(null);
		}
		else {
			setEBContractQtyDifferential(aQtyDiff.getQuantity().getQuantity());
			setEBContractQtyDifferentialSign(aQtyDiff.getSign().getName());
			setEBContractQtyDifferentialUOM(aQtyDiff.getQuantity().getUnitofMeasure().getID());
		}
	}

	/**
	 * Gets contracted market UOM conversion rate.
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getContractMarketUOMConversionRate() {
		if (getEBContractQtyUOM() == null) {
			return null;
		}
		if ((getEBContractMarketUOM() == null) || (getEBContractMarketUOMConversionRate() == null)) {

			return new QuantityConversionRate(new ConversionKey(getEBContractQtyUOM(), getEBContractQtyUOM()), 1);
		}
		return new QuantityConversionRate(new ConversionKey(getEBContractQtyUOM(), getEBContractMarketUOM()),
				getEBContractMarketUOMConversionRate().doubleValue());
	}

	/**
	 * Sets contracted market UOM conversion rate.
	 * 
	 * @param mktUOMConversionRate - QuantityConversionRate
	 */
	public void setContractMarketUOMConversionRate(QuantityConversionRate mktUOMConversionRate) {
		if ((mktUOMConversionRate == null) || (mktUOMConversionRate.getRate() == null)
				|| (mktUOMConversionRate.getRate() == new BigDecimal(1)) || (mktUOMConversionRate.getKey() == null)) {

			setEBContractMarketUOM(null);
			setEBContractMarketUOMConversionRate(null);
		}
		else {
			setEBContractMarketUOM(mktUOMConversionRate.getKey().getToUnit());
			setEBContractMarketUOMConversionRate(mktUOMConversionRate.getRate());
		}
	}

	/**
	 * Gets contracted metric UOM conversion rate.
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getContractMetricUOMConversionRate() {
		if (getEBContractQtyUOM() == null) {
			return null;
		}
		if ((getEBContractMetricUOM() == null) || (getEBContractMetricUOMConversionRate() == null)) {

			return new QuantityConversionRate(new ConversionKey(getEBContractQtyUOM(), getEBContractQtyUOM()), 1);
		}
		return new QuantityConversionRate(new ConversionKey(getEBContractQtyUOM(), getEBContractMetricUOM()),
				getEBContractMetricUOMConversionRate().doubleValue());
	}

	/**
	 * Sets contracted metric UOM conversion rate.
	 * 
	 * @param metricUOMConversionRate - QuantityConversionRate
	 */
	public void setContractMetricUOMConversionRate(QuantityConversionRate metricUOMConversionRate) {
		if ((metricUOMConversionRate == null) || (metricUOMConversionRate.getRate() == null)
				|| (metricUOMConversionRate.getRate() == new BigDecimal(1))
				|| (metricUOMConversionRate.getKey() == null)) {

			setEBContractMetricUOM(null);
			setEBContractMetricUOMConversionRate(null);
		}
		else {
			setEBContractMetricUOM(metricUOMConversionRate.getKey().getToUnit());
			setEBContractMetricUOMConversionRate(metricUOMConversionRate.getRate());
		}
	}

	/**
	 * Get the co borrower limit id
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID() {
		if (getEBCoBorrowerLimitID() != null) {
			return getEBCoBorrowerLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Set the coborrower limit id
	 * 
	 * @param coborrowerid of type long
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID) {

		setEBCoBorrowerLimitID(coBorrowerLimitID == ICMSConstant.LONG_INVALID_VALUE ? null
				: new Long(coBorrowerLimitID));
	}

	/**
	 * Gets the limit id associated with this commodity.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		if (getEBLimitID() != null) {
			return getEBLimitID().longValue();
		}
		else {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
	}

	/**
	 * Sets the limit id associated with this commodity.
	 * 
	 * @param limitID
	 */
	public void setLimitID(long limitID) {
		setEBLimitID(limitID == ICMSConstant.LONG_INVALID_VALUE ? null : new Long(limitID));
	}

	public abstract Long getEBCommodityDealID();

	public abstract void setEBCommodityDealID(Long eBCommodityDealID);

	public abstract String getEBCommodityDealNo();

	public abstract void setEBCommodityDealNo(String eBCommodityDealNo);

	public abstract BigDecimal getEBOrigFaceValue();

	public abstract void setEBOrigFaceValue(BigDecimal eBOrigFaceValue);

	public abstract String getOrigFaceValueCcyCode();

	public abstract void setOrigFaceValueCcyCode(String origFaceValue);

	public abstract BigDecimal getEBCMV();

	public abstract void setEBCMV(BigDecimal cmv);

	public abstract String getCMVCcyCode();

	public abstract void setCMVCcyCode(String cMVCcyCode);

	public abstract BigDecimal getEBFSV();

	public abstract void setEBFSV(BigDecimal fsv);

	public abstract String getFSVCcyCode();

	public abstract void setFSVCcyCode(String fSVCcyCode);

	public abstract Double getEBFinancingPct();

	public abstract void setEBFinancingPct(Double eBFinancingPct);

	public abstract Double getEBCashMarginPct();

	public abstract void setEBCashMarginPct(Double eBCashMarginPct);

	public abstract Double getEBCashReqPct();

	public abstract void setEBCashReqPct(Double eBCashReqPct);

	public abstract Collection getFinancingDocsCMR();

	public abstract void setFinancingDocsCMR(Collection financingDocsCMR);

	public abstract Collection getTitleDocsCMR();

	public abstract void setTitleDocsCMR(Collection titleDocsCMR);

	public abstract Collection getSettlementsCMR();

	public abstract void setSettlementsCMR(Collection settlementsCMR);

	public abstract Collection getReceiptReleasesCMR();

	public abstract void setReceiptReleasesCMR(Collection receiptReleasesCMR);

	public abstract Collection getCashDepositCMR();

	public abstract void setCashDepositCMR(Collection cashDeposits);

	public abstract Collection getHedgePriceExtensionCMR();

	public abstract void setHedgePriceExtensionCMR(Collection extensions);

	// Problem with Websphere one-to-one CMR
	// Workaround : Model as one-to-many CMR
	public abstract Collection getPurchaseAndSalesDetailsCMR();

	public abstract void setPurchaseAndSalesDetailsCMR(Collection details);

	public abstract Long getEBHedgeContractID();

	public abstract void setEBHedgeContractID(Long contractID);

	public abstract BigDecimal getEBHedgePrice();

	public abstract void setEBHedgePrice(BigDecimal price);

	public abstract String getEBHedgePriceCcyCode();

	public abstract void setEBHedgePriceCcyCode(String ccyCode);

	public abstract BigDecimal getEBHedgeQuantity();

	public abstract void setEBHedgeQuantity(BigDecimal qty);

	public abstract Long getEBContractID();

	public abstract void setEBContractID(Long contractID);

	public abstract BigDecimal getEBContractQty();

	public abstract void setEBContractQty(BigDecimal qty);

	public abstract String getEBContractQtyUOM();

	public abstract void setEBContractQtyUOM(String uomCode);

	public abstract BigDecimal getEBContractQtyDifferential();

	public abstract void setEBContractQtyDifferential(BigDecimal qty);

	public abstract String getEBContractQtyDifferentialSign();

	public abstract void setEBContractQtyDifferentialSign(String sign);

	public abstract String getEBContractQtyDifferentialUOM();

	public abstract void setEBContractQtyDifferentialUOM(String uomCode);

	public abstract Long getEBCoBorrowerLimitID();

	public abstract void setEBCoBorrowerLimitID(Long eBCoBorrowerLimitID);

	public abstract String getCustomerCategory();

	public abstract void setCustomerCategory(String customerCategory);

	public abstract Long getEBLimitID();

	public abstract void setEBLimitID(Long eBLimitID);

	public abstract Long getEBContractProfileID();

	public abstract void setEBContractProfileID(Long profileID);

	public abstract String getEBContractPriceType();

	public abstract void setEBContractPriceType(String priceType);

	public abstract BigDecimal getEBContractPrice();

	public abstract void setEBContractPrice(BigDecimal price);

	public abstract String getEBContractPriceCcyCode();

	public abstract void setEBContractPriceCcyCode(String ccyCode);

	public abstract String getEBContractPriceDifferentialSign();

	public abstract void setEBContractPriceDifferentialSign(String sign);

	public abstract Double getEBContractPriceDifferential();

	public abstract void setEBContractPriceDifferential(Double price);

	public abstract String getEBContractMarketUOM();

	public abstract void setEBContractMarketUOM(String marketUOMCode);

	public abstract BigDecimal getEBContractMarketUOMConversionRate();

	public abstract void setEBContractMarketUOMConversionRate(BigDecimal mktConversionRate);

	public abstract String getEBContractMetricUOM();

	public abstract void setEBContractMetricUOM(String metricUOMCode);

	public abstract BigDecimal getEBContractMetricUOMConversionRate();

	public abstract void setEBContractMetricUOMConversionRate(BigDecimal metricConversionRate);

	public abstract BigDecimal getEBActualQuantity();

	public abstract void setEBActualQuantity(BigDecimal qty);

	public abstract BigDecimal getEBActualPrice();

	public abstract void setEBActualPrice(BigDecimal price);

	public abstract String getEBActualPriceCcyCode();

	public abstract void setEBActualPriceCcyCode(String ccyCode);

	public abstract String getEBActualCommonDifferentialSign();

	public abstract void setEBActualCommonDifferentialSign(String sign);

	public abstract Double getEBActualCommonDifferential();

	public abstract void setEBActualCommonDifferential(Double priceDiff);

	public abstract String getEBAcutalEODCustomerDifferentialSign();

	public abstract void setEBAcutalEODCustomerDifferentialSign(String sign);

	public abstract Double getEBAcutalEODCustomerDifferential();

	public abstract void setEBAcutalEODCustomerDifferential(Double priceDiff);

	/**
	 * Get commodity settlements.
	 * 
	 * @return ISettlement[]
	 */
	public ISettlement[] getSettlements() {
		Iterator i = getSettlementsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			arrayList.add(((EBSettlementLocal) i.next()).getValue());
		}

		return (OBSettlement[]) arrayList.toArray(new OBSettlement[0]);
	}

	/**
	 * Set commodity settlements.
	 * 
	 * @param settlements of type ISettlement[]
	 */
	public void setSettlements(ISettlement[] settlements) {
	}

	/**
	 * Get released warehouse receipts.
	 * 
	 * @return IReceiptRelease[]
	 */
	public IReceiptRelease[] getReceiptReleases() {
		Iterator i = getReceiptReleasesCMR().iterator();
		ArrayList aList = new ArrayList();

		while (i.hasNext()) {
			aList.add(((EBReceiptReleaseLocal) i.next()).getValue());
		}

		return (OBReceiptRelease[]) aList.toArray(new OBReceiptRelease[0]);
	}

	/**
	 * Set released warehouse receipts.
	 * 
	 * @param receiptReleases of type IReceiptRelease[]
	 */
	public void setReceiptReleases(IReceiptRelease[] receiptReleases) {
	}

	/**
	 * Get financing documents.
	 * 
	 * @return IFinancingDoc[]
	 */
	public IFinancingDoc[] getFinancingDocs() {
		Iterator i = getFinancingDocsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IFinancingDoc fDoc = ((EBFinancingDocLocal) i.next()).getValue();
			if ((fDoc.getStatus() != null) && fDoc.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			arrayList.add(fDoc);
		}

		return (OBFinancingDoc[]) arrayList.toArray(new OBFinancingDoc[0]);
	}

	/**
	 * Set financing documents.
	 * 
	 * @param financingDocs of type IFinancingDoc[]
	 */
	public void setFinancingDocs(IFinancingDoc[] financingDocs) {
	}

	/**
	 * Get commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsAll() {
		Iterator i = getTitleDocsCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			ICommodityTitleDocument tdoc = ((EBCommodityTitleDocumentLocal) i.next()).getValue();
			if ((tdoc.getStatus() != null) && tdoc.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}
			arrayList.add(tdoc);
		}

		return (OBCommodityTitleDocument[]) arrayList.toArray(new OBCommodityTitleDocument[0]);
	}

	/**
	 * Get if warehouse receipt title doc exists.
	 * 
	 * @return boolean
	 */
	public boolean getIsAnyWRTitleDoc() {
		return false;
	}

	/**
	 * Set if warehouse receipt title doc exists.
	 * 
	 * @param isAnyWRTitleDoc of type boolean
	 */
	public void setIsAnyWRTitleDoc(boolean isAnyWRTitleDoc) {
	}

	// Added by Pratheepa for CR129
	public boolean getIsAnyWRTitleDoc_N() {
		return false;
	}

	public void setIsAnyWRTitleDoc_N(boolean isAnyWRTitleDoc_N) {
	}

	public boolean getIsAnyWRTitleDoc_NN() {
		return false;
	}

	public void setIsAnyWRTitleDoc_NN(boolean isAnyWRTitleDoc_NN) {
	}

	/**
	 * Set commodity title documents.
	 * 
	 * @param titleDocs of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsAll(ICommodityTitleDocument[] titleDocs) {
	}

	/**
	 * Get latest commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsLatest() {
		return null;
	}

	/**
	 * Set latest commodity title documents.
	 * 
	 * @param titleDocsLatest of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsLatest(ICommodityTitleDocument[] titleDocsLatest) {
	}

	/**
	 * Get historical commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsHistory() {
		return null;
	}

	/**
	 * Set historical commodity title document.
	 * 
	 * @param titleDocsHistory of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsHistory(ICommodityTitleDocument[] titleDocsHistory) {
	}

	/*********** OTHERS ************/
	/**
	 * Gets the cash deposits for the deal.
	 * 
	 * @return IDealCashDeposit[]
	 */
	public IDealCashDeposit[] getCashDeposit() {
		Iterator i = getCashDepositCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IDealCashDeposit deposit = ((EBDealCashDepositLocal) i.next()).getValue();
			if ((deposit.getStatus() != null) && deposit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(deposit);
		}

		return (OBDealCashDeposit[]) arrayList.toArray(new OBDealCashDeposit[0]);
	}

	/**
	 * Sets the cash deposits to the deal.
	 * 
	 * @param deposits - IDealCashDeposit[]
	 */
	public void setCashDeposit(IDealCashDeposit[] deposits) {
	}

	/**
	 * Gets the purchase and sales details of the deal.
	 * 
	 * @return IPurchaseAndSalesDetails
	 */
	public IPurchaseAndSalesDetails getPurchaseAndSalesDetails() {
		// Problem with Websphere one-to-one CMR
		// Workaround : Model as one-to-many CMR
		Collection detailsEjbList = getPurchaseAndSalesDetailsCMR();
		return ((detailsEjbList == null) || (detailsEjbList.size() == 0)) ? null
				: ((EBPurchaseAndSalesDetailsLocal) detailsEjbList.iterator().next()).getValue();
	}

	/**
	 * Sets the purchase and sales details to the deal.
	 * 
	 * @param details - IPurchaseAndSalesDetails
	 */
	public void setPurchaseAndSalesDetails(IPurchaseAndSalesDetails details) {
	}

	/*********** PRICE HEDGING ************/
	/**
	 * Gets the hedging contract ID the hedge price is based on.
	 * 
	 * @return long - the hedge contract ID
	 */
	public long getHedgeContractID() {
		return (getEBHedgeContractID() != null) ? getEBHedgeContractID().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Sets the hedging contract ID the hedge price is based on.
	 * 
	 * @param aContractID - long representing the ID of the hedging contract.
	 */
	public void setHedgeContractID(long aContractID) {
		setEBHedgeContractID((aContractID != ICMSConstant.LONG_INVALID_VALUE) ? new Long(aContractID) : null);
	}

	/**
	 * Gets the hedged price for the deal.
	 * 
	 * @return Amount representing the hedged Price
	 */
	public Amount getHedgePrice() {
		if ((getEBHedgePrice() == null) || (getEBHedgePriceCcyCode() == null)) {
			return null;
		}
		return new Amount(getEBHedgePrice(), new CurrencyCode(getEBHedgePriceCcyCode()));
	}

	/**
	 * Sets the hedged price for the deal.
	 * 
	 * @param aPrice - Amount representing the hedged price
	 */
	public void setHedgePrice(Amount aPrice) {
		setEBHedgePrice((aPrice != null) ? aPrice.getAmountAsBigDecimal() : null);
		setEBHedgePriceCcyCode((aPrice != null) ? aPrice.getCurrencyCode() : null);
	}

	/**
	 * Gets the quantity of goods in deal to be hedged.
	 * 
	 * @return Quantity representing the hedged quantity
	 */
	public Quantity getHedgeQuantity() {
		if ((getEBHedgeQuantity() == null) || (getEBContractQtyUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractQtyUOM());
		return new Quantity(getEBHedgeQuantity(), uom);
	}

	/**
	 * Sets the quantity of goods in deal to be hedged. Precondition : Quantity
	 * unit of measure has to be the same unit of measure specified in
	 * contracted deal.
	 * 
	 * @param aQty - Quantity representing the hedged quantity
	 */
	public void setHedgeQuantity(Quantity aQty) {
		setEBHedgeQuantity(aQty != null ? aQty.getQuantity() : null);
	}

	/**
	 * Gets the hedged price extension of the deal.
	 * 
	 * @return IHedgePriceExtension[]
	 */
	public IHedgePriceExtension[] getHedgePriceExtension() {
		Iterator i = getHedgePriceExtensionCMR().iterator();
		ArrayList arrayList = new ArrayList();

		while (i.hasNext()) {
			IHedgePriceExtension extension = ((EBHedgePriceExtensionLocal) i.next()).getValue();
			if ((extension.getStatus() != null) && extension.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			arrayList.add(extension);
		}

		return (IHedgePriceExtension[]) arrayList.toArray(new OBHedgePriceExtension[0]);
	}

	/**
	 * SEts the hedge price extension to the deal.
	 * 
	 * @param extensions - IHedgePriceExtension
	 */
	public void setHedgePriceExtension(IHedgePriceExtension[] extensions) {
	}

	/********** CONTRACTED DEAL ************/

	/**
	 * Gets contract ID
	 * 
	 * @return long
	 */
	public long getContractID() {
		return (getEBContractID() != null) ? getEBContractID().longValue() : ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Sets contract ID
	 * 
	 * @param aContractID of type long
	 */
	public void setContractID(long aContractID) {
		setEBContractID((aContractID != ICMSConstant.LONG_INVALID_VALUE) ? new Long(aContractID) : null);
	}

	/**
	 * Gets the profile ID of the product sub-type specified in the contracted
	 * deal.
	 * 
	 * @return long representing the profile ID of the product sub-type
	 */
	public long getContractProfileID() {
		if (getEBContractProfileID() == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		return getEBContractProfileID().longValue();
	}

	/**
	 * Sets the profile ID of the product sub-type for the contracted deal.
	 * 
	 * @param aProfileID
	 */
	public void setContractProfileID(long aProfileID) {
		Long profileID = (aProfileID != ICMSConstant.LONG_INVALID_VALUE) ? new Long(aProfileID) : null;
		setEBContractProfileID(profileID);
	}

	/**
	 * Gets the price type from the contracted deal. This will indicate which
	 * price should be used to value the deal.
	 * 
	 * @return PriceType representing the contracted deal price type.
	 * @return null if no price type specified.
	 */
	public PriceType getContractPriceType() {
		return (getEBContractPriceType() != null) ? PriceType.valueOf(getEBContractPriceType().trim()) : null;
	}

	/**
	 * Sets the price type for the contracted deal. This will indicate which
	 * price will be used to value the deal.
	 * 
	 * @param aPriceType - PriceType representing the price type
	 */
	public void setContractPriceType(PriceType aPriceType) {
		setEBContractPriceType((aPriceType != null) ? aPriceType.getName() : null);
	}

	/**
	 * Gets the price from the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal was purchased.
	 * 
	 * @return Amount representing the purchase price
	 */
	public Amount getContractPrice() {
		if (getEBContractPrice() == null) {
			return null;
		}
		return new Amount(getEBContractPrice(), new CurrencyCode(getEBContractPriceCcyCode()));
	}

	/**
	 * Sets the price to the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal is purchased.
	 * 
	 * @param aPrice - Amount representing the price
	 */
	public void setContractPrice(Amount aPrice) {
		setEBContractPrice(aPrice == null ? null : aPrice.getAmountAsBigDecimal());
		setEBContractPriceCcyCode(aPrice == null ? null : aPrice.getCurrencyCode());
	}

	/**
	 * Gets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @return PriceDifferential
	 */
	public PriceDifferential getContractPriceDifferential() {
		if ((getEBContractPriceDifferential() == null) || (getEBContractPriceDifferentialSign() == null)) {
			return null;
		}
		String sign = getEBContractPriceDifferentialSign();
		double price = getEBContractPriceDifferential().doubleValue();
		return new PriceDifferential(new Amount(price, (String) null), sign);
	}

	/**
	 * Sets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @param aPriceDiff - PriceDifferential
	 */
	public void setContractPriceDifferential(PriceDifferential aPriceDiff) {
		if ((aPriceDiff == null) || (aPriceDiff.getPrice() == null) || (aPriceDiff.getSign() == null)) {

			setEBContractPriceDifferential(null);
			setEBContractPriceDifferentialSign(null);
		}
		else {
			setEBContractPriceDifferential(new Double(aPriceDiff.getPrice().getAmountAsDouble()));
			setEBContractPriceDifferentialSign(aPriceDiff.getSign().getName());
		}
	}

	/***********
	 * ACTUAL DEAL ************ /** Gets the quantity of product sub-type
	 * specified in the contracted deal traded in the actual deal.
	 * 
	 * @return Quantity representing the actual quantity traded
	 * @return null if no acutal quantity specified or no contract uom specified
	 */
	public Quantity getActualQuantity() {
		if ((getEBActualQuantity() == null) || (getEBContractQtyUOM() == null)) {
			return null;
		}
		UOMWrapper uom = UOMWrapperFactory.getInstance().valueOf(getEBContractQtyUOM());
		return new Quantity(getEBActualQuantity(), uom);
	}

	/**
	 * Sets the quantity of product sub-type specified in the contracted deal
	 * traded in the actual deal. Assumes that the biz layer has check that the
	 * unit of measure for the acutal quantity is the same as that used in
	 * contracted deal.
	 * 
	 * @param aQty - Quantity representing the actual quantity traded.
	 */
	public void setActualQuantity(Quantity aQty) {
		setEBActualQuantity(aQty != null ? aQty.getQuantity() : null);
	}

	/**
	 * Gets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @return Amount representing the actual price
	 */
	public Amount getActualPrice() {
		if ((getEBActualPrice() == null) || (getEBActualPriceCcyCode() == null)) {
			return null;
		}
		return new Amount(getEBActualPrice(), new CurrencyCode(getEBActualPriceCcyCode()));
	}

	/**
	 * Sets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @param aPrice - Amount representing the actual price
	 */
	public void setActualPrice(Amount aPrice) {
		setEBActualPrice(aPrice == null ? null : aPrice.getAmountAsBigDecimal());
		setEBActualPriceCcyCode(aPrice == null ? null : aPrice.getCurrencyCode());
	}

	/**
	 * Gets the price differential depending on the price type. If price type is
	 * specified as EOD, this price differential refers to the commodity
	 * differential. If price type is specfied as Floating Futures, this price
	 * differential refers to the buyer/seller agreed differential. This is
	 * utilised when calculating actual price for a contracted deal using EOD or
	 * Floating Futures price type.
	 * 
	 * @return PriceDifferential representing the appropriate price differential
	 */
	public PriceDifferential getActualCommonDifferential() {
		if ((getEBActualCommonDifferential() == null) || (getEBActualCommonDifferentialSign() == null)) {
			return null;
		}
		String sign = getEBActualCommonDifferentialSign();
		double price = getEBActualCommonDifferential().doubleValue();
		return new PriceDifferential(new Amount(price, (String) null), sign);
	}

	/**
	 * Sets the price differential. If price type is specified as EOD, this
	 * price differential refers to the commodity differential. If price type is
	 * specfied as Floating Futures, this price differential refers to the
	 * buyer/seller agreed differential. This is utilised when calculating
	 * actual price for a contracted deal using EOD or Floating Futures price
	 * type.
	 * 
	 * @param commonDiff - PriceDifferential
	 */
	public void setActualCommonDifferential(PriceDifferential commonDiff) {
		if ((commonDiff == null) || (commonDiff.getPrice() == null) || (commonDiff.getSign() == null)) {

			setEBActualCommonDifferential(null);
			setEBActualCommonDifferentialSign(null);
		}
		else {
			setEBActualCommonDifferential(new Double(commonDiff.getPrice().getAmountAsDouble()));
			setEBActualCommonDifferentialSign(commonDiff.getSign().getName());
		}
	}

	/**
	 * Gets the customer price differential. This is only utilised when
	 * calculating actual price for a contracted deal using Floating Futures
	 * price type.
	 * 
	 * @return PriceDifferential representing customer price differential
	 */
	public PriceDifferential getActualEODCustomerDifferential() {
		if ((getEBAcutalEODCustomerDifferential() == null) || (getEBAcutalEODCustomerDifferentialSign() == null)) {
			return null;
		}
		String sign = getEBAcutalEODCustomerDifferentialSign();
		double price = getEBAcutalEODCustomerDifferential().doubleValue();
		return new PriceDifferential(new Amount(price, (String) null), sign);
	}

	/**
	 * Sets the customer price differential. This is utilised when calculating
	 * actual price for a contracted deal using EOD or Floating Futures price
	 * type.
	 * 
	 * @param aCustomerDiff - PriceDifferential
	 */
	public void setActualEODCustomerDifferential(PriceDifferential aCustomerDiff) {
		if ((aCustomerDiff == null) || (aCustomerDiff.getPrice() == null) || (aCustomerDiff.getSign() == null)) {

			setEBAcutalEODCustomerDifferential(null);
			setEBAcutalEODCustomerDifferentialSign(null);
		}
		else {
			setEBAcutalEODCustomerDifferential(new Double(aCustomerDiff.getPrice().getAmountAsDouble()));
			setEBAcutalEODCustomerDifferentialSign(aCustomerDiff.getSign().getName());
		}
	}

	// ****** implemented in OB!!!! ********
	public Amount getCalculatedFSVAmt(double crp, double hedgeMargin) {
		return null;
	}

	public Amount getCalculatedCMVAmt() {
		return null;
	}

	public Amount getActualCMVAmt() {
		return null;
	}

	public Amount getActualFSVAmt(double crp, double hedgeMargin) {
		return null;
	}

	public Amount getFaceValueAmt() {
		return null;
	}

	public Amount getAdjustedPricingAmt() {
		return null;
	}

	public Amount getHedgeProfitLossAmt() {
		return null;
	}

	public Amount getTotalSetOffCashAmt() {
		return null;
	}

	public Amount getTotalReqCashAmt() {
		return null;
	}

	public Amount getTotalCashDepositAmt() {
		return null;
	}

	public Amount getDealAmt() {
		return null;
	}

	public String getDealAmtCCyCode() {
		return null;
	}

	public Amount getTotalSettlementAmt() {
		return null;
	}

	public Quantity getBalanceDealQty() {
		return null;
	}

	public Quantity getTotalWRQuantity() {
		return null;
	}

	public Quantity getTotalQuantityReleased() {
		return null;
	}

	public Amount getBalanceDealAmt() {
		return null;
	}

	public Amount getFaceValuePostAdvRateAmt() {
		return null;
	}

	/**
	 * Search commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult ejbHomeSearchDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException {
		CommodityDealDAO dao = new CommodityDealDAO();
		return dao.searchDeal(criteria);
	}

	/**
	 * Search closed commodity deal given the criteria.
	 * 
	 * @param criteria deal search criteria
	 * @return search result
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult ejbHomeSearchClosedDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException {
		CommodityDealDAO dao = new CommodityDealDAO();
		return dao.searchClosedDeal(criteria);
	}

	/**
	 * Get the commodity deal business object.
	 * 
	 * @return commodity deal
	 */
	// Method modified by Pratheepa for CR129
	public ICommodityDeal getValue() throws CommodityDealException {
		OBCommodityDeal deal = new OBCommodityDeal();
		AccessorUtil.copyValue(this, deal);
		ICommodityTitleDocument[] docs = deal.getTitleDocsAll();
		DefaultLogger.debug(this, "getValue:" + docs.length);

		if (docs != null) {
			for (int i = 0; i < docs.length; i++) {
				if ((docs[i].getTitleDocType().getName() != null)
						&& docs[i].getTitleDocType().getName().equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N)) {
					DefaultLogger.debug(this, "coming inside neg");
					deal.setIsAnyWRTitleDoc_N(true);
				}
				if ((docs[i].getTitleDocType().getName() != null)
						&& docs[i].getTitleDocType().getName().equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT)) {
					DefaultLogger.debug(this, "coming inside nonneg");
					deal.setIsAnyWRTitleDoc_NN(true);
				}

				if (((docs[i].getTitleDocType().getName() != null) && docs[i].getTitleDocType().getName().equals(
						ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT))
						|| ((docs[i].getTitleDocType().getName() != null) && docs[i].getTitleDocType().getName()
								.equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N)))

				{
					deal.setIsAnyWRTitleDoc(true);

				}
				if (deal.getIsAnyWRTitleDoc_N() && deal.getIsAnyWRTitleDoc_NN()) {
					break;
				}
			}

		}

		return deal;
	}

	/**
	 * Set the commodity deal to this entity.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	public void setValue(ICommodityDeal deal) throws VersionMismatchException {
		checkVersionMismatch(deal);
		AccessorUtil.copyValue(deal, this, EXCLUDE_METHOD);
		setReferences(deal, false);
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Set the commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @throws VersionMismatchException if the deal version is invalid
	 */
	public void setDealStatus(ICommodityDeal deal) throws VersionMismatchException {
		checkVersionMismatch(deal);
		this.setStatus(deal.getStatus());
		this.setVersionTime(VersionGenerator.getVersionNumber());
	}

	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param deal of type ICommodityDeal
	 * @throws CreateException on error creating the entity object
	 */
	public Long ejbCreate(ICommodityDeal deal) throws CreateException {
		try {
			String dealID = (new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_COMMODITY_DEAL, true);
			AccessorUtil.copyValue(deal, this, EXCLUDE_METHOD);
			setEBCommodityDealID(new Long(dealID));
			setEBCommodityDealNo(deal.getDealNo());
			setVersionTime(VersionGenerator.getVersionNumber());
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param deal of type ICommodityDeal
	 * @throws CreateException on error creating the references
	 */
	public void ejbPostCreate(ICommodityDeal deal) throws CreateException {
		setReferences(deal, true);
	}

	/**
	 * Set the references to this commodity deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICommodityDeal deal, boolean isAdd) {
		try {
			setSettlementsRef(deal.getSettlements(), isAdd);
			setReceiptReleasesRef(deal.getReceiptReleases(), isAdd);
			setFinancingDocsRef(deal.getFinancingDocs(), isAdd);
			setTitleDocsRef(deal.getTitleDocsLatest(), deal.getTitleDocsAll(), isAdd);
			setCashDepositRef(deal.getCashDeposit(), isAdd);
			setPurchaseAndSalesDetailsRef(deal.getPurchaseAndSalesDetails());
			setHedgePriceExtensionRef(deal.getHedgePriceExtension(), isAdd);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EJBException(e);
		}
	}

	/**
	 * Get settlement local home.
	 * 
	 * @return EBSettlementLocalHome
	 */
	protected EBSettlementLocalHome getEBSettlementLocalHome() {
		EBSettlementLocalHome ejbHome = (EBSettlementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_SETTLEMENT_LOCAL_JNDI, EBSettlementLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBSettlementLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get settlement local home.
	 * 
	 * @return EBSettlementLocalHome
	 */
	protected EBReceiptReleaseLocalHome getEBReceiptReleaseLocalHome() {
		EBReceiptReleaseLocalHome ejbHome = (EBReceiptReleaseLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECEIPT_RELEASE_LOCAL_JNDI, EBReceiptReleaseLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBReceiptReleaseLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get financing document local home.
	 * 
	 * @return EBFinancingDocLocalHome
	 */
	protected EBFinancingDocLocalHome getEBFinancingDocLocalHome() {
		EBFinancingDocLocalHome ejbHome = (EBFinancingDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FINANCING_DOC_LOCAL_JNDI, EBFinancingDocLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFinancingDocLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get commodity title document local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBCommodityTitleDocumentLocalHome getEBCommodityTitleDocLocalHome() {
		EBCommodityTitleDocumentLocalHome ejbHome = (EBCommodityTitleDocumentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_TITLE_DOC_LOCAL_JNDI, EBCommodityTitleDocumentLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBCommodityTitleDocumentLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal cash deposit local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBDealCashDepositLocalHome getEBDealCashDepositLocalHome() {
		EBDealCashDepositLocalHome ejbHome = (EBDealCashDepositLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEAL_CASH_DEPOSIT_LOCAL_JNDI, EBDealCashDepositLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBDealCashDepositLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal hedged price extension local home.
	 * 
	 * @return EBHedgePriceExtensionLocalHome
	 */
	protected EBHedgePriceExtensionLocalHome getEBHedgePriceExtensionLocalHome() {
		EBHedgePriceExtensionLocalHome ejbHome = (EBHedgePriceExtensionLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_HEDGE_PRICE_EXTENSION_LOCAL_JNDI, EBHedgePriceExtensionLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBHedgePriceExtensionLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get deal purchase and sales details local home.
	 * 
	 * @return EBCommodityTitleDocumentLocalHome
	 */
	protected EBPurchaseAndSalesDetailsLocalHome getEBPurchaseAndSalesDetailsLocalHome() {
		EBPurchaseAndSalesDetailsLocalHome ejbHome = (EBPurchaseAndSalesDetailsLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_PURCHASE_SALES_LOCAL_JNDI,
						EBPurchaseAndSalesDetailsLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBPurchaseAndSalesDetailsLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Check the version of this deal against the backend version.
	 * 
	 * @param deal commodity deal's version to be checked
	 * @throws VersionMismatchException if the entity version is different from
	 *         backend
	 */
	private void checkVersionMismatch(ICommodityDeal deal) throws VersionMismatchException {
		if (getVersionTime() != deal.getVersionTime()) {
			throw new VersionMismatchException("Mismatch timestamp! " + deal.getVersionTime());
		}
	}

	/**
	 * Set commodity settlements.
	 * 
	 * @param settlements of type ISettlement[]
	 */
	public void setSettlementsRef(ISettlement[] settlements, boolean isAdd) throws CreateException {
		// remove all existing settlements
		if ((settlements == null) || (settlements.length == 0)) {
			removeAllSettlements();
			return;
		}

		EBSettlementLocalHome ejbHome = getEBSettlementLocalHome();

		Collection c = getSettlementsCMR();

		// add all newly added settlements
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < settlements.length; i++) {
				c.add(ejbHome.create(settlements[i]));
			}
			return;
		}

		// remove existing settlements that are not in newly updated list.
		removeSettlement(c, settlements);

		// update existing settlements as well as add new settlements
		Iterator iterator = c.iterator();
		ArrayList newSettles = new ArrayList();

		for (int i = 0; i < settlements.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBSettlementLocal theEjb = (EBSettlementLocal) iterator.next();
				ISettlement value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (settlements[i].getRefID() == value.getRefID()) {
					// update existing settlements
					theEjb.setValue(settlements[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newSettles.add(settlements[i]);
			}
			iterator = c.iterator();
		}

		iterator = newSettles.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ISettlement) iterator.next()));
		}
	}

	/**
	 * Set commodity receipt releases.
	 * 
	 * @param releases of type IReceiptRelease[]
	 */
	public void setReceiptReleasesRef(IReceiptRelease[] releases, boolean isAdd) throws CreateException {
		// remove all existing releases
		if ((releases == null) || (releases.length == 0)) {
			removeAllReleases();
			return;
		}

		EBReceiptReleaseLocalHome ejbHome = getEBReceiptReleaseLocalHome();

		Collection c = getReceiptReleasesCMR();

		// add all newly added releases
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < releases.length; i++) {
				c.add(ejbHome.create(releases[i]));
			}
			return;
		}

		// remove existing releases that are not in newly updated list.
		removeRelease(c, releases);

		// update existing releases as well as add new releases.
		Iterator iterator = c.iterator();
		ArrayList newReleases = new ArrayList();

		for (int i = 0; i < releases.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBReceiptReleaseLocal theEjb = (EBReceiptReleaseLocal) iterator.next();
				IReceiptRelease value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (releases[i].getRefID() == value.getRefID()) {
					// update existing releases
					theEjb.setValue(releases[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newReleases.add(releases[i]);
			}
			iterator = c.iterator();
		}

		iterator = newReleases.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IReceiptRelease) iterator.next()));
		}
	}

	/**
	 * Set financing documents.
	 * 
	 * @param financingDocs of type IFinancingDoc[]
	 * @param isAdd true if the caller is ejb post create, otherwise false
	 * @throws CreateException on error creating the document
	 */
	private void setFinancingDocsRef(IFinancingDoc[] financingDocs, boolean isAdd) throws CreateException {
		// remove all existing financing docs
		if ((financingDocs == null) || (financingDocs.length == 0)) {
			removeAllFinancingDocs();
			return;
		}

		EBFinancingDocLocalHome ejbHome = getEBFinancingDocLocalHome();

		Collection c = getFinancingDocsCMR();

		// add all newly added documents
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < financingDocs.length; i++) {
				c.add(ejbHome.create(financingDocs[i]));
			}
			return;
		}

		// remove existing documents that are not in newly updated list.
		removeFinancingDoc(c, financingDocs);

		// update existing documents as well as add new documents
		Iterator iterator = c.iterator();
		ArrayList newDocs = new ArrayList();

		for (int i = 0; i < financingDocs.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBFinancingDocLocal theEjb = (EBFinancingDocLocal) iterator.next();
				IFinancingDoc value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (financingDocs[i].getRefID() == value.getRefID()) {
					// update existing receipt
					theEjb.setValue(financingDocs[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newDocs.add(financingDocs[i]);
			}
			iterator = c.iterator();
		}

		iterator = newDocs.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IFinancingDoc) iterator.next()));
		}
	}

	/**
	 * Set commodity title documents.
	 * 
	 * @param titleDocs of type ICommodityTitleDocument[]
	 * @param isAdd true if the caller is from ejb post create, otherwise false
	 * @throws CreateException on error creating the references
	 */
	private void setTitleDocsRef(ICommodityTitleDocument[] titleDocs, ICommodityTitleDocument[] allTitleDocs,
			boolean isAdd) throws CreateException {
		// remove all existing title documents
		if ((titleDocs == null) || (titleDocs.length == 0)) {
			removeAllTitleDocs();
			return;
		}

		EBCommodityTitleDocumentLocalHome ejbHome = getEBCommodityTitleDocLocalHome();

		Collection c = getTitleDocsCMR();

		// add all newly added title documents
		if (isAdd || (c.size() == 0)) {
			for (int i = 0; i < titleDocs.length; i++) {
				c.add(ejbHome.create(titleDocs[i]));
			}
			return;
		}

		// remove existing documents that are not in newly updated list.
		removeTitleDoc(c, titleDocs, allTitleDocs);

		// update existing documents as well as add new documents
		Iterator iterator = c.iterator();
		ArrayList newDocs = new ArrayList();

		for (int i = 0; i < titleDocs.length; i++) {
			boolean found = false;

			while (iterator.hasNext()) {
				EBCommodityTitleDocumentLocal theEjb = (EBCommodityTitleDocumentLocal) iterator.next();
				ICommodityTitleDocument value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (titleDocs[i].getRefID() == value.getRefID()) {
					// update existing receipt
					theEjb.setValue(titleDocs[i]);
					found = true;
					break;
				}
			}
			if (!found) {
				newDocs.add(titleDocs[i]);
			}
			iterator = c.iterator();
		}

		iterator = newDocs.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((ICommodityTitleDocument) iterator.next()));
		}
	}

	/**
	 * Sets cash deposits for deal.
	 * 
	 * @param deposits - IDealCashDeposit[]
	 * @param isAdd - boolean
	 * @throws CreateException
	 */
	private void setCashDepositRef(IDealCashDeposit[] deposits, boolean isAdd) throws CreateException {
		// get existing cash deposits for the deal
		Collection existingList = getCashDepositCMR();
		if (existingList.isEmpty()) {
			// existing list is empty, add all in deposits
			// if (isAdd && deposits != null && deposits.length != 0) {
			if ((deposits != null) && (deposits.length != 0)) {
				addCashDeposit(Arrays.asList(deposits), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if deposits is empty , remove all cash deposits
			if ((deposits == null) || (deposits.length == 0)) {
				removeCashDeposit(existingList);
				return;
			}

			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBDealCashDepositLocal depositLocal = (EBDealCashDepositLocal) existingListIterator.next();
				IDealCashDeposit deposit = depositLocal.getValue();
				if ((deposit.getStatus() == null) || !deposit.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(deposit.getCommonReferenceID()), depositLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare deposits with existing list
			for (int i = 0; i < deposits.length; i++) {
				IDealCashDeposit deposit = deposits[i];
				Long commonRefID = new Long(deposit.getCommonReferenceID());
				EBDealCashDepositLocal theExistingEjb = (EBDealCashDepositLocal) existingListMap.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(deposit);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(deposit);
				}
			}

			// add new cash deposit
			addCashDeposit(newList, existingList);

			// remove deleted cash deposit
			EBDealCashDepositLocal[] deletedList = (EBDealCashDepositLocal[]) existingListMap.values().toArray(
					new EBDealCashDepositLocal[0]);
			removeCashDeposit(Arrays.asList(deletedList));
		}
	}

	/**
	 * Sets hedged price extension for deal.
	 * 
	 * @param extensions - IHedgePriceExtension[]
	 * @param isAdd - boolean
	 * @throws CreateException
	 */
	private void setHedgePriceExtensionRef(IHedgePriceExtension[] extensions, boolean isAdd) throws CreateException {

		// get existing hedged price extension for the deal
		Collection existingList = getHedgePriceExtensionCMR();
		if (existingList.isEmpty()) {
			// if (isAdd && extensions != null && extensions.length != 0) {
			if ((extensions != null) && (extensions.length != 0)) {
				addHedgePriceExtension(Arrays.asList(extensions), existingList);
				return;
			}
		}
		else {
			// existing list is NOT empty
			// if extensions is empty , remove all hedge price extensions
			if ((extensions == null) || (extensions.length == 0)) {
				removeHedgePriceExtension(existingList);
				return;
			}

			// put existing list into hashmap - excludes deleted values
			HashMap existingListMap = new HashMap(existingList.size());
			Iterator existingListIterator = existingList.iterator();
			while (existingListIterator.hasNext()) {
				EBHedgePriceExtensionLocal extLocal = (EBHedgePriceExtensionLocal) existingListIterator.next();
				IHedgePriceExtension ext = extLocal.getValue();
				if ((ext.getStatus() == null) || !ext.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					existingListMap.put(new Long(ext.getCommonReferenceID()), extLocal);
				}
			}

			ArrayList newList = new ArrayList();

			// compare extensions with existing list
			for (int i = 0; i < extensions.length; i++) {
				IHedgePriceExtension ext = extensions[i];
				Long commonRefID = new Long(ext.getCommonReferenceID());
				EBHedgePriceExtensionLocal theExistingEjb = (EBHedgePriceExtensionLocal) existingListMap
						.get(commonRefID);
				if (theExistingEjb != null) {
					theExistingEjb.setValue(ext);
					existingListMap.remove(commonRefID);
				}
				else {
					newList.add(ext);
				}
			}

			// add new hedge price extension
			addHedgePriceExtension(newList, existingList);

			// remove deleted hedge price extension
			EBHedgePriceExtensionLocal[] deletedList = (EBHedgePriceExtensionLocal[]) existingListMap.values().toArray(
					new EBHedgePriceExtensionLocal[0]);
			removeHedgePriceExtension(Arrays.asList(deletedList));
		}
	}

	/**
	 * Set purchase and sales details for deal.
	 * 
	 * @param details - IPurchaseAndSalesDetails
	 * @throws CreateException
	 */
	private void setPurchaseAndSalesDetailsRef(IPurchaseAndSalesDetails details) throws CreateException {

		// Problem with Websphere one-to-one CMR
		// Workaround : Model as one-to-many CMR
		Collection detailsEjbList = getPurchaseAndSalesDetailsCMR();
		EBPurchaseAndSalesDetailsLocal existingEjb = ((detailsEjbList == null) || (detailsEjbList.size() == 0)) ? null
				: ((EBPurchaseAndSalesDetailsLocal) detailsEjbList.iterator().next());

		if (existingEjb == null) {
			if (details != null) {
				EBPurchaseAndSalesDetailsLocalHome theEjbHome = getEBPurchaseAndSalesDetailsLocalHome();
				existingEjb = theEjbHome.create(details);
				detailsEjbList = new ArrayList(1);
				detailsEjbList.add(existingEjb);
				setPurchaseAndSalesDetailsCMR(detailsEjbList);
			}
		}
		else {
			if (details != null) {
				existingEjb.setValue(details);
			}
			else {
				existingEjb.getValue().setStatus(ICMSConstant.STATE_DELETED);
			}
		}
	}

	/**
	 * Helper method to add all cash deposit specified in the collection.
	 * 
	 * @param newDeposits
	 * @param existingDeposits
	 * @throws CreateException
	 */
	private void addCashDeposit(Collection newDeposits, Collection existingDeposits) throws CreateException {
		EBDealCashDepositLocalHome theEjbHome = getEBDealCashDepositLocalHome();
		Iterator i = newDeposits.iterator();
		while (i.hasNext()) {
			IDealCashDeposit deposit = (IDealCashDeposit) i.next();
			existingDeposits.add(theEjbHome.create(deposit));
		}
	}

	/**
	 * Helper method to add all hedged price extension specified in the
	 * collection.
	 * 
	 * @param newExtensions
	 * @param existingExtensions
	 * @throws CreateException
	 */
	private void addHedgePriceExtension(Collection newExtensions, Collection existingExtensions) throws CreateException {
		EBHedgePriceExtensionLocalHome theEjbHome = getEBHedgePriceExtensionLocalHome();
		Iterator i = newExtensions.iterator();
		while (i.hasNext()) {
			existingExtensions.add(theEjbHome.create((IHedgePriceExtension) i.next()));
		}
	}

	/**
	 * Helper method to delete all settlements.
	 */
	private void removeAllSettlements() {
		Collection c = getSettlementsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBSettlementLocal theEjb = (EBSettlementLocal) iterator.next();
			deleteSettlement(theEjb);
		}
	}

	/**
	 * Helper method to delete all warehouse receipt releases.
	 */
	private void removeAllReleases() {
		Collection c = getReceiptReleasesCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBReceiptReleaseLocal theEjb = (EBReceiptReleaseLocal) iterator.next();
			deleteRelease(theEjb);
		}
	}

	/**
	 * Helper method to delete all financing documents.
	 */
	private void removeAllFinancingDocs() {
		Collection c = getFinancingDocsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBFinancingDocLocal theEjb = (EBFinancingDocLocal) iterator.next();
			deleteFinancingDoc(theEjb);
		}
	}

	/**
	 * Helper method to delete all commodity title documents.
	 */
	private void removeAllTitleDocs() {
		Collection c = getTitleDocsCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBCommodityTitleDocumentLocal theEjb = (EBCommodityTitleDocumentLocal) iterator.next();
			deleteTitleDoc(theEjb);
		}
	}

	/**
	 * Helper method to delete existing settlements that are not in the list of
	 * new settlements.
	 * 
	 * @param settleCol a list of old settlements
	 * @param settleList a list of newly updated settlements
	 */
	private void removeSettlement(Collection settleCol, ISettlement[] settleList) {
		Iterator iterator = settleCol.iterator();

		while (iterator.hasNext()) {
			EBSettlementLocal theEjb = (EBSettlementLocal) iterator.next();
			ISettlement settle = theEjb.getValue();
			if ((settle.getStatus() != null) && settle.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < settleList.length; i++) {
				if (settleList[i].getRefID() == settle.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteSettlement(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete existing receipt releases that are not in the
	 * list of new releases.
	 * 
	 * @param releaseCol a list of old releases
	 * @param releaseList a list of newly updated releases
	 */
	private void removeRelease(Collection releaseCol, IReceiptRelease[] releaseList) {
		Iterator iterator = releaseCol.iterator();

		while (iterator.hasNext()) {
			EBReceiptReleaseLocal theEjb = (EBReceiptReleaseLocal) iterator.next();
			IReceiptRelease release = theEjb.getValue();
			if ((release.getStatus() != null) && release.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < releaseList.length; i++) {
				if (releaseList[i].getRefID() == release.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteRelease(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete existing financing documents that are not in the
	 * list of new financing documents.
	 * 
	 * @param FinDocCol a list of old financing documents
	 * @param finDocList a list of newly updated financing documents
	 */
	private void removeFinancingDoc(Collection FinDocCol, IFinancingDoc[] finDocList) {
		Iterator iterator = FinDocCol.iterator();

		while (iterator.hasNext()) {
			EBFinancingDocLocal theEjb = (EBFinancingDocLocal) iterator.next();
			IFinancingDoc finDoc = theEjb.getValue();
			if ((finDoc.getStatus() != null) && finDoc.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < finDocList.length; i++) {
				if (finDocList[i].getRefID() == finDoc.getRefID()) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteFinancingDoc(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete existing title documents that are not in the list
	 * of new title documents.
	 * 
	 * @param titleDocCol a list of old title documents
	 * @param titleDocList a list of newly updated title documents
	 */
	private void removeTitleDoc(Collection titleDocCol, ICommodityTitleDocument[] titleDocList,
			ICommodityTitleDocument[] allTitleDocs) {
		ICommodityTitleDocument[] latest = titleDocList;

		Iterator iterator = titleDocCol.iterator();

		while (iterator.hasNext()) {
			EBCommodityTitleDocumentLocal theEjb = (EBCommodityTitleDocumentLocal) iterator.next();
			ICommodityTitleDocument doc = theEjb.getValue();
			if ((doc.getStatus() != null) && doc.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			for (int i = 0; i < latest.length; i++) {
				// filter out the history
				if (doc.getRefID() == latest[i].getRefID()) {
					found = true;
					break;
				}
			}

			/*
			 * if (!found) continue;
			 * 
			 * found = false;
			 * 
			 * for (int i=0; i<titleDocList.length; i++) { if
			 * (titleDocList[i].getRefID() == doc.getRefID()) { found = true;
			 * break; } }
			 * 
			 * if (!found) { for (int i=0; i<allTitleDocs.length; i++) { if
			 * (allTitleDocs[i].getRefID() == doc.getRefID()) { found = true;
			 * break; } } }
			 */

			if (!found) {
				deleteTitleDoc(theEjb);
			}
		}
	}

	/**
	 * Helper method to remove all cash deposits specified in the collection.
	 * 
	 * @param deposits - Collection of cash deposits to be removed. Cannot be
	 *        null.
	 */
	private void removeCashDeposit(Collection deposits) {
		Iterator i = deposits.iterator();
		while (i.hasNext()) {
			EBDealCashDepositLocal theEjb = (EBDealCashDepositLocal) i.next();
			removeCashDeposit(theEjb);
		}
	}

	/**
	 * Helper method to remove the said cash deposit.
	 * 
	 * @param anEjb - EBDealCashDepositLocal
	 */
	private void removeCashDeposit(EBDealCashDepositLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Helper method to remove all cash deposits specified in the collection.
	 * 
	 * @param extensions - Collection of cash deposits to be removed. Cannot be
	 *        null.
	 */
	private void removeHedgePriceExtension(Collection extensions) {
		Iterator i = extensions.iterator();
		while (i.hasNext()) {
			EBHedgePriceExtensionLocal theEjb = (EBHedgePriceExtensionLocal) i.next();
			removeHedgePriceExtension(theEjb);
		}
	}

	/**
	 * Helper method to remove the said cash deposit.
	 * 
	 * @param anEjb - EBDealCashDepositLocal
	 */
	private void removeHedgePriceExtension(EBHedgePriceExtensionLocal anEjb) {
		anEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Helper method to delete deal settlement.
	 * 
	 * @param theEjb of type EBSettlementLocal
	 */
	private void deleteSettlement(EBSettlementLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Helper method to delete deal release.
	 * 
	 * @param theEjb of type EBReceiptReleaseLocal
	 */
	private void deleteRelease(EBReceiptReleaseLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Helper method to delete a financing document.
	 * 
	 * @param theEjb of type EBFinancingDocLocal
	 */
	private void deleteFinancingDoc(EBFinancingDocLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * Helper method to delete a title document.
	 * 
	 * @param theEjb of type EBCommodityTitleDocumentLocal
	 */
	private void deleteTitleDoc(EBCommodityTitleDocumentLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		this.context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		this.context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}
}