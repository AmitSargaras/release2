/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/OBCommodityDeal.java,v 1.62 2006/09/13 11:35:44 hshii Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.CommodityConstant;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.DifferentialSign;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.doc.IWarehouseReceipt;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This class represents a Commodity Deal entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.62 $
 * @since $Date: 2006/09/13 11:35:44 $ Tag: $Name: $
 */
public class OBCommodityDeal implements ICommodityDeal {
	private long commodityDealID;

	private String dealNo;

	private String dealReferenceNo;

	private String dealTypeCode;

	private long collateralID;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	// Added for CR035.
	private long coBorrowerlimitID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory;

	// end cr 035

	private long subLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private Date dealMaturityDate;

	private Date extendedDealMaturityDate;

	private double financingPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private String isPreSold;

	private String isEnforceAttained;

	private String isPledgeDocumentRequiredStr;

	private Date enforceAttainedDate;

	private Amount origFaceValue;

	private Amount cmv;

	private Amount fsv;

	private double cashMarginPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private double cashReqPct = ICMSConstant.DOUBLE_INVALID_VALUE;

	private Date latestShipDate;

	private String containerNo;

	private String shippingMarks;

	private IFinancingDoc[] financingDocs;

	private ICommodityTitleDocument[] titleDocsAll;

	private ICommodityTitleDocument[] titleDocsLatest;

	private ICommodityTitleDocument[] titleDocsHistory;

	private boolean isAnyWRTitleDoc;

	// Added by Pratheepa for CR129
	private boolean isAnyWRTitleDoc_N;

	private boolean isAnyWRTitleDoc_NN;

	private ISettlement[] settlements;

	private IReceiptRelease[] receiptReleases;

	private IDealCashDeposit[] cashDeposits;

	private IPurchaseAndSalesDetails purchaseAndSalesDetails;

	// //////// HEDGE PRICE ///////////
	private long hedgeContractID = ICMSConstant.LONG_INVALID_VALUE;

	private Amount hedgedPrice;

	private Quantity hedgedQty;

	private IHedgePriceExtension[] hedgePriceExtensions;

	// //////// CONTRACTED DEAL ///////////
	private long contractID = ICMSConstant.LONG_INVALID_VALUE;

	private String contractRIC;

	private Quantity contractQuantity;

	private QuantityDifferential contractQtyDifferential;

	private long contractProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private PriceType contractPriceType;

	private Amount contractPrice;

	private PriceDifferential contractPriceDifferential;

	private QuantityConversionRate contractMarketUOMConversionRate;

	private QuantityConversionRate contractMetricUOMConversionRate;

	// //////// ACTUAL DEAL ///////////
	private Quantity actualQty;

	private Amount actualPrice;

	private PriceDifferential actualCommonDifferential;

	private PriceDifferential actualEODCustomerDifferential;

	private Date actualMarketPriceDate;

	private String remarks;

	private Date dealDate;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBCommodityDeal() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICommodityDeal
	 */
	public OBCommodityDeal(ICommodityDeal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get the commodity deal id.
	 * 
	 * @return long
	 */
	public long getCommodityDealID() {
		return commodityDealID;
	}

	/**
	 * Set the commodity deal id.
	 * 
	 * @param commodityDealID of type long
	 */
	public void setCommodityDealID(long commodityDealID) {
		this.commodityDealID = commodityDealID;
	}

	/**
	 * Get deal number.
	 * 
	 * @return String
	 */
	public String getDealNo() {
		return dealNo;
	}

	/**
	 * Set deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	/**
	 * Get deal reference number.
	 * 
	 * @return String
	 */
	public String getDealReferenceNo() {
		return dealReferenceNo;
	}

	/**
	 * Set deal reference number.
	 * 
	 * @param dealReferenceNo of type String
	 */
	public void setDealReferenceNo(String dealReferenceNo) {
		this.dealReferenceNo = dealReferenceNo;
	}

	/**
	 * Get deal type code: pool or specific.
	 * 
	 * @return String
	 */
	public String getDealTypeCode() {
		return dealTypeCode;
	}

	/**
	 * Set deal type code: pool or specific.
	 * 
	 * @param dealTypeCode of type String
	 */
	public void setDealTypeCode(String dealTypeCode) {
		this.dealTypeCode = dealTypeCode;
	}

	/**
	 * Get cms collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return collateralID;
	}

	/**
	 * Set cms collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID) {
		this.collateralID = collateralID;
	}

	/**
	 * Get cms limit id.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Set cms limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public long getSubLimitID() {
		return subLimitID;
	}

	public void setSubLimitID(long subLimitID) {
		this.subLimitID = subLimitID;
	}

	/**
	 * Get cms co borrowere limit id.
	 * 
	 * @return long
	 */
	public long getCoBorrowerLimitID() {
		return coBorrowerlimitID;
	}

	/**
	 * Set cms co borrower limit id.
	 * 
	 * @param coBorrowerlimitID of type long
	 */
	public void setCoBorrowerLimitID(long coBorrowerlimitID) {
		this.coBorrowerlimitID = coBorrowerlimitID;
	}

	/**
	 * Get cms customer categoryr
	 * 
	 * @return string
	 */
	public String getCustomerCategory() {
		return customerCategory;
	}

	/**
	 * Set cms customer category
	 * 
	 * @param customerCategory of type long
	 */
	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	/**
	 * Get deal maturity date.
	 * 
	 * @return Date
	 */
	public Date getDealMaturityDate() {
		return dealMaturityDate;
	}

	/**
	 * Set deal maturity date.
	 * 
	 * @param dealMaturityDate of type Date
	 */
	public void setDealMaturityDate(Date dealMaturityDate) {
		this.dealMaturityDate = dealMaturityDate;
	}

	/**
	 * Get extended deal maturity date.
	 * 
	 * @return Date
	 */
	public Date getExtendedDealMaturityDate() {
		return extendedDealMaturityDate;
	}

	/**
	 * Set extended deal maturity date.
	 * 
	 * @param extendedDealMaturityDate of type Date
	 */
	public void setExtendedDealMaturityDate(Date extendedDealMaturityDate) {
		this.extendedDealMaturityDate = extendedDealMaturityDate;
	}

	/**
	 * Get financing percentage.
	 * 
	 * @return double
	 */
	public double getFinancingPct() {
		return financingPct;
	}

	/**
	 * Set financing percentage.
	 * 
	 * @param financingPct of type double
	 */
	public void setFinancingPct(double financingPct) {
		this.financingPct = financingPct;
	}

	/**
	 * Get if the deal is pre sold.
	 * 
	 * @return String
	 */
	public String getIsPreSold() {
		return isPreSold;
	}

	/**
	 * Set if the deal is pre sold.
	 * 
	 * @param isPreSold of type String
	 */
	public void setIsPreSold(String isPreSold) {
		this.isPreSold = isPreSold;
	}

	/**
	 * Get if the enforcibility is attained.
	 * 
	 * @return String
	 */
	public String getIsEnforceAttained() {
		return isEnforceAttained;
	}

	/**
	 * Set if the enforcibility is attained.
	 * 
	 * @param isEnforceAttained of type String
	 */
	public void setIsEnforceAttained(String isEnforceAttained) {
		this.isEnforceAttained = isEnforceAttained;
	}

	/**
	 * Get if the plege documents are required.
	 * 
	 * @return String
	 */
	public String getIsPledgeDocumentRequired() {
		return isPledgeDocumentRequiredStr;
	}

	/**
	 * Set if the plege documents are required.
	 * 
	 * @param isPledgeDocumentRequiredStr of type String
	 */
	public void setIsPledgeDocumentRequired(String isPledgeDocumentRequiredStr) {
		this.isPledgeDocumentRequiredStr = isPledgeDocumentRequiredStr;
	}

	/**
	 * Get date of enforcibility attained.
	 * 
	 * @return Date
	 */
	public Date getEnforceAttainedDate() {
		return enforceAttainedDate;
	}

	/**
	 * Set date of enforcibility attained.
	 * 
	 * @param enforceAttainedDate of type Date
	 */
	public void setEnforceAttainedDate(Date enforceAttainedDate) {
		this.enforceAttainedDate = enforceAttainedDate;
	}

	/**
	 * Get original face value.
	 * 
	 * @return Amount
	 */
	public Amount getOrigFaceValue() {
		return origFaceValue;
	}

	/**
	 * Set original face value.
	 * 
	 * @param origFaceValue of type Amount
	 */
	public void setOrigFaceValue(Amount origFaceValue) {
		this.origFaceValue = origFaceValue;
	}

	/**
	 * Get cash margin percentage.
	 * 
	 * @return double
	 */
	public double getCashMarginPct() {
		return cashMarginPct;
	}

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashMarginPct of type double
	 */
	public void setCashMarginPct(double cashMarginPct) {
		this.cashMarginPct = cashMarginPct;
	}

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct() {
		return cashReqPct;
	}

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct) {
		this.cashReqPct = cashReqPct;
	}

	/**
	 * Get latest shipment date.
	 * 
	 * @return Date
	 */
	public Date getLatestShipDate() {
		return latestShipDate;
	}

	/**
	 * Set latest shiping date.
	 * 
	 * @param latestShipDate of type Date
	 */
	public void setLatestShipDate(Date latestShipDate) {
		this.latestShipDate = latestShipDate;
	}

	/**
	 * Get container number.
	 * 
	 * @return String
	 */
	public String getContainerNo() {
		return containerNo;
	}

	/**
	 * Set container number.
	 * 
	 * @param containerNo of type String
	 */
	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	/**
	 * Get shipping marks.
	 * 
	 * @return String
	 */
	public String getShippingMarks() {
		return shippingMarks;
	}

	/**
	 * Set shipping marks.
	 * 
	 * @param shippingMarks of type String
	 */
	public void setShippingMarks(String shippingMarks) {
		this.shippingMarks = shippingMarks;
	}

	/**
	 * Get financing documents.
	 * 
	 * @return IFinancingDoc[]
	 */
	public IFinancingDoc[] getFinancingDocs() {
		return financingDocs;
	}

	/**
	 * Set financing documents.
	 * 
	 * @param financingDocs of type IFinancingDoc[]
	 */
	public void setFinancingDocs(IFinancingDoc[] financingDocs) {
		this.financingDocs = financingDocs;
	}

	/**
	 * Get all commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsAll() {
		return titleDocsAll;
	}

	/**
	 * Set all commodity title documents.
	 * 
	 * @param titleDocsAll of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsAll(ICommodityTitleDocument[] titleDocsAll) {
		this.titleDocsAll = titleDocsAll;
	}

	/**
	 * Get latest commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsLatest() {
		if ((titleDocsLatest != null) && (titleDocsLatest.length != 0)) {
			if (titleDocsLatest[0] == null) {
				return null;
			}
			return titleDocsLatest;
		}
		return OBCommodityTitleDocument.getTitleDocsLatest(this.getTitleDocsAll());
	}

	/**
	 * Set latest commodity title documents.
	 * 
	 * @param titleDocsLatest of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsLatest(ICommodityTitleDocument[] titleDocsLatest) {
		this.titleDocsLatest = titleDocsLatest;
	}

	/**
	 * Get historical commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsHistory() {
		DefaultLogger.debug(this, "coming inside OBCommodityDeal");
		if ((titleDocsHistory != null) && (titleDocsHistory.length != 0)) {
			DefaultLogger.debug(this, "coming inside if");
			if (titleDocsHistory[0] == null) {
				return null; // staging without actual title docs.
			}
			return titleDocsHistory;
		}
		if (this.getTitleDocsAll() != null) {
			DefaultLogger.debug(this, "All title Docs:" + this.getTitleDocsAll().length);
		}
		return OBCommodityTitleDocument.getTitleDocsHistory(this.getTitleDocsAll());
	}

	/**
	 * Set historical commodity title document.
	 * 
	 * @param titleDocsHistory of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsHistory(ICommodityTitleDocument[] titleDocsHistory) {
		this.titleDocsHistory = titleDocsHistory;
	}

	/**
	 * Get if warehouse receipt title doc exists.
	 * 
	 * @return boolean
	 */
	public boolean getIsAnyWRTitleDoc() {
		return isAnyWRTitleDoc;
	}

	/**
	 * Set if warehouse receipt title doc exists.
	 * 
	 * @param isAnyWRTitleDoc of type boolean
	 */
	public void setIsAnyWRTitleDoc(boolean isAnyWRTitleDoc) {
		this.isAnyWRTitleDoc = isAnyWRTitleDoc;
	}

	// Added by Pratheepa for CR129
	public boolean getIsAnyWRTitleDoc_N() {
		return isAnyWRTitleDoc_N;
	}

	public void setIsAnyWRTitleDoc_N(boolean isAnyWRTitleDoc_N) {
		this.isAnyWRTitleDoc_N = isAnyWRTitleDoc_N;
	}

	public boolean getIsAnyWRTitleDoc_NN() {
		return isAnyWRTitleDoc_NN;
	}

	public void setIsAnyWRTitleDoc_NN(boolean isAnyWRTitleDoc_NN) {
		this.isAnyWRTitleDoc_NN = isAnyWRTitleDoc_NN;
	}

	/**
	 * Get commodity settlements.
	 * 
	 * @return ISettlement[]
	 */
	public ISettlement[] getSettlements() {
		return settlements;
	}

	/**
	 * Set commodity settlements.
	 * 
	 * @param settlements of type ISettlement[]
	 */
	public void setSettlements(ISettlement[] settlements) {
		this.settlements = settlements;
	}

	/**
	 * Get released warehouse receipts.
	 * 
	 * @return IReceiptRelease[]
	 */
	public IReceiptRelease[] getReceiptReleases() {
		return receiptReleases;
	}

	/**
	 * Set released warehouse receipts.
	 * 
	 * @param receiptReleases of type IReceiptRelease[]
	 */
	public void setReceiptReleases(IReceiptRelease[] receiptReleases) {
		this.receiptReleases = receiptReleases;
	}

	/**
	 * Gets contract ID
	 * 
	 * @return long
	 */
	public long getContractID() {
		return contractID;
	}

	/**
	 * Sets contract ID
	 * 
	 * @param contractID of type long
	 */
	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	/**
	 * Get contract reuters identification code.
	 * 
	 * @return String
	 */
	public String getContractRIC() {
		return contractRIC;
	}

	/**
	 * Set contract reuters id code.
	 * 
	 * @param contractRIC of type String
	 */
	public void setContractRIC(String contractRIC) {
		this.contractRIC = contractRIC;
	}

	/**
	 * Gets contracted quantity of deal.
	 * 
	 * @return contracted quantity
	 */
	public Quantity getContractQuantity() {
		return contractQuantity;
	}

	/**
	 * Sets contracted quantity to deal.
	 * 
	 * @param aQty - contracted quantity
	 */
	public void setContractQuantity(Quantity aQty) {
		this.contractQuantity = aQty;
	}

	/**
	 * Gets contracted quantity differential of deal.
	 * 
	 * @return contracted quantity differential
	 */
	public QuantityDifferential getContractQuantityDifferential() {
		return contractQtyDifferential;
	}

	/**
	 * Sets contracted quantity differential to deal.
	 * 
	 * @param aQtyDiff - contracted quantity differential
	 */
	public void setContractQuantityDifferential(QuantityDifferential aQtyDiff) {
		this.contractQtyDifferential = aQtyDiff;
	}

	/**
	 * Gets contracted market UOM conversion rate.
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getContractMarketUOMConversionRate() {
		return contractMarketUOMConversionRate;
	}

	/**
	 * Sets contracted market UOM conversion rate.
	 * 
	 * @param mktUOMConversionRate - IConversionRate
	 */
	public void setContractMarketUOMConversionRate(QuantityConversionRate mktUOMConversionRate) {
		this.contractMarketUOMConversionRate = mktUOMConversionRate;
	}

	/**
	 * Gets contracted metric UOM conversion rate.
	 * 
	 * @return QuantityConversionRate
	 */
	public QuantityConversionRate getContractMetricUOMConversionRate() {
		return contractMetricUOMConversionRate;
	}

	/**
	 * Sets contracted metric UOM conversion rate.
	 * 
	 * @param metricUOMConversionRate - IConversionRate
	 */
	public void setContractMetricUOMConversionRate(QuantityConversionRate metricUOMConversionRate) {
		this.contractMetricUOMConversionRate = metricUOMConversionRate;
	}

	/**
	 * Get remarks of commodity deal.
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * Set remarks of commodity deal.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Get the date on which the deal is first created.
	 * 
	 * @return Date
	 */
	public Date getDealDate() {
		return dealDate;
	}

	/**
	 * Set the date on which the deal is first created.
	 * 
	 * @param dealDate of type Date
	 */
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	/*********** OTHERS ************/
	/**
	 * Gets the cash deposits for the deal.
	 * 
	 * @return IDealCashDeposit[]
	 */
	public IDealCashDeposit[] getCashDeposit() {
		return cashDeposits;
	}

	/**
	 * Sets the cash deposits to the deal.
	 * 
	 * @param deposits - IDealCashDeposit[]
	 */
	public void setCashDeposit(IDealCashDeposit[] deposits) {
		this.cashDeposits = deposits;
	}

	/**
	 * Gets the purchase and sales details of the deal.
	 * 
	 * @return IPurchaseAndSalesDetails
	 */
	public IPurchaseAndSalesDetails getPurchaseAndSalesDetails() {
		return purchaseAndSalesDetails;
	}

	/**
	 * Sets the purchase and sales details to the deal.
	 * 
	 * @param details - IPurchaseAndSalesDetails
	 */
	public void setPurchaseAndSalesDetails(IPurchaseAndSalesDetails details) {
		this.purchaseAndSalesDetails = details;
	}

	/*********** PRICE HEDGING ************/
	/**
	 * Gets the hedging contract ID the hedge price is based on.
	 * 
	 * @return long - the hedge contract ID
	 */
	public long getHedgeContractID() {
		return hedgeContractID;
	}

	/**
	 * Sets the hedging contract ID the hedge price is based on.
	 * 
	 * @param aContractID - long representing the ID of the hedging contract.
	 */
	public void setHedgeContractID(long aContractID) {
		this.hedgeContractID = aContractID;
	}

	/**
	 * Gets the hedged price for the deal.
	 * 
	 * @return Amount representing the hedged Price
	 */
	public Amount getHedgePrice() {
		return hedgedPrice;
	}

	/**
	 * Sets the hedged price for the deal.
	 * 
	 * @param aPrice - Amount representing the hedged price
	 */
	public void setHedgePrice(Amount aPrice) {
		this.hedgedPrice = aPrice;
	}

	/**
	 * Gets the quantity of goods in deal to be hedged.
	 * 
	 * @return Quantity representing the hedged quantity
	 */
	public Quantity getHedgeQuantity() {
		return hedgedQty;
	}

	/**
	 * Sets the quantity of goods in deal to be hedged.
	 * 
	 * @param aQty - Quantity representing the hedged quantity
	 */
	public void setHedgeQuantity(Quantity aQty) {
		this.hedgedQty = aQty;
	}

	/**
	 * Gets the hedged price extension of the deal.
	 * 
	 * @return IHedgePriceExtension[]
	 */
	public IHedgePriceExtension[] getHedgePriceExtension() {
		return hedgePriceExtensions;
	}

	/**
	 * SEts the hedge price extension to the deal.
	 * 
	 * @param extensions - IHedgePriceExtension
	 */
	public void setHedgePriceExtension(IHedgePriceExtension[] extensions) {
		this.hedgePriceExtensions = extensions;
	}

	/********** CONTRACTED DEAL ************/
	/**
	 * Gets the profile ID of the product sub-type specified in the contracted
	 * deal.
	 * 
	 * @return long representing the profile ID of the product sub-type
	 */
	public long getContractProfileID() {
		return contractProfileID;
	}

	/**
	 * Sets the profile ID of the product sub-type for the contracted deal.
	 * 
	 * @param aProfileID
	 */
	public void setContractProfileID(long aProfileID) {
		this.contractProfileID = aProfileID;
	}

	/**
	 * Gets the price type from the contracted deal. This will indicate which
	 * price should be used to value the deal.
	 * 
	 * @return PriceType representing the contracted deal price type.
	 */
	public PriceType getContractPriceType() {
		return contractPriceType;
	}

	/**
	 * Sets the price type for the contracted deal. This will indicate which
	 * price will be used to value the deal.
	 * 
	 * @param aPriceType - PriceType representing the price type
	 */
	public void setContractPriceType(PriceType aPriceType) {
		this.contractPriceType = aPriceType;
	}

	/**
	 * Gets the price from the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal was purchased.
	 * 
	 * @return Amount representing the purchase price
	 */
	public Amount getContractPrice() {
		return contractPrice;
	}

	/**
	 * Sets the price to the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal is purchased.
	 * 
	 * @param aPrice - Amount representing the price
	 */
	public void setContractPrice(Amount aPrice) {
		this.contractPrice = aPrice;
	}

	/**
	 * Gets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @return PriceDifferential
	 */
	public PriceDifferential getContractPriceDifferential() {
		return contractPriceDifferential;
	}

	/**
	 * Sets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @param aPriceDifferential - PriceDifferential
	 */
	public void setContractPriceDifferential(PriceDifferential aPriceDifferential) {
		this.contractPriceDifferential = aPriceDifferential;
	}

	/*********** ACTUAL DEAL ************/
	/**
	 * Gets the quantity of product sub-type specified in the contracted deal
	 * traded in the actual deal.
	 * 
	 * @return Quantity representing the actual quantity traded
	 */
	public Quantity getActualQuantity() {
		return actualQty;
	}

	/**
	 * Sets the quantity of product sub-type specified in the contracted deal
	 * traded in the actual deal.
	 * 
	 * @param aQty - Quantity representing the actual quantity traded.
	 */
	public void setActualQuantity(Quantity aQty) {
		this.actualQty = aQty;
	}

	/**
	 * Gets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @return Amount representing the actual price
	 */
	public Amount getActualPrice() {
		return actualPrice;
	}

	/**
	 * Sets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @param aPrice - Amount representing the actual price
	 */
	public void setActualPrice(Amount aPrice) {
		this.actualPrice = aPrice;
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
		return actualCommonDifferential;
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
		this.actualCommonDifferential = commonDiff;
	}

	/**
	 * Gets the customer price differential. This is only utilised when
	 * calculating actual price for a contracted deal using Floating Futures
	 * price type.
	 * 
	 * @return PriceDifferential representing customer price differential
	 */
	public PriceDifferential getActualEODCustomerDifferential() {
		return actualEODCustomerDifferential;
	}

	/**
	 * Sets the customer price differential. This is utilised when calculating
	 * actual price for a contracted deal using EOD or Floating Futures price
	 * type.
	 * 
	 * @param customerDiff - PriceDifferential
	 */
	public void setActualEODCustomerDifferential(PriceDifferential customerDiff) {
		this.actualEODCustomerDifferential = customerDiff;
	}

	/**
	 * Gets the date on which the market price is retrieved.
	 * 
	 * @return Date
	 */
	public Date getActualMarketPriceDate() {
		return actualMarketPriceDate;
	}

	/**
	 * Sets the date on which the market price is retrieved.
	 * 
	 * @param priceDate - Date
	 */
	public void setActualMarketPriceDate(Date priceDate) {
		this.actualMarketPriceDate = priceDate;
	}

	/**
	 * Get commodity deal cmv.
	 * 
	 * @return Amount
	 */
	public Amount getCMV() {
		return cmv;
	}

	/**
	 * Set commodity deal cmv.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv) {
		this.cmv = cmv;
	}

	/**
	 * Get commodity deal fsv.
	 * 
	 * @return Amount
	 */
	public Amount getFSV() {
		return fsv;
	}

	/**
	 * Set commodity deal fsv.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv) {
		this.fsv = fsv;
	}

	/**
	 * Get the status of this commodity deal.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of this commodity deal.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get the version of the deal.
	 * 
	 * @return long
	 */
	public long getVersionTime() {
		return versionTime;
	}

	/**
	 * Set the version of the deal.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * Get newly calculated adjusted fsv of this deal. Formula: commodity face
	 * value where enforcibility is yes*crp + set off cash*cash margin + Hedged
	 * collateral* hedge margin
	 * 
	 * @param crp security crp
	 * @param hedgeMargin margin for hedge collateral
	 * @return Amount
	 */
	public Amount getCalculatedFSVAmt(double crp, double hedgeMargin) {
		String ccy = getDealAmtCCyCode();
		if (ccy == null) {
			return null;
		}

		Amount totalAmt = null;

		try {
			// calculate the [commodity * crp] part
			Amount amt = getEnforcedFaceValueAmt(true);
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), crp));
				totalAmt = amt;
			}

			// calculate [total cash setoff * cash margin]
			amt = getTotalSetOffCashAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), getCashMarginPct()));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			// calculate [total cash requirement * cash margin]
			amt = getTotalReqCashAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), getCashMarginPct()));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			// calculate [hedged collateral * hedge margin]
			amt = getHedgeProfitLossAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), hedgeMargin));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating adjusted deal fsv! " + e.toString());
		}
	}

	/**
	 * Get actual fsv of this deal. Formula: actual face value where
	 * enforcibility is yes*crp + set off cash*cash margin + Hedged collateral*
	 * hedge margin
	 * 
	 * @param crp security crp
	 * @param hedgeMargin margin for hedge collateral
	 * @return Amount
	 */
	public Amount getActualFSVAmt(double crp, double hedgeMargin) {
		String ccy = getDealAmtCCyCode();
		if (ccy == null) {
			return null;
		}

		Amount totalAmt = null;

		try {
			// calculate the [commodity * crp] part
			Amount amt = getEnforcedFaceValueAmt(false);
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), crp));
				totalAmt = amt;
			}

			// calculate [total cash setoff * cash margin]
			amt = getTotalSetOffCashAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), getCashMarginPct()));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			// calculate [total cash requirement * cash margin]
			amt = getTotalReqCashAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), getCashMarginPct()));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			// calculate [hedged collateral * hedge margin]
			amt = getHedgeProfitLossAmt();
			if (amt != null) {
				amt.setAmountAsBigDecimal(CommonUtil.calcAfterPercent(amt.getAmountAsBigDecimal(), hedgeMargin));
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating actual deal fsv! " + e.toString());
		}
	}

	/**
	 * Get newly calculated adjusted cmv of this deal. Formula: commodity face
	 * value where enforcibility is yes + set off cash + Hedged collateral
	 * 
	 * @return Amount
	 */
	public Amount getCalculatedCMVAmt() {
		String ccy = getDealAmtCCyCode();
		if (ccy == null) {
			return null;
		}

		Amount totalAmt = null;

		try {
			Amount amt = getEnforcedFaceValueAmt(true);
			if (amt != null) {
				totalAmt = amt;
			}

			amt = getTotalSetOffCashAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			amt = getTotalReqCashAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			amt = getHedgeProfitLossAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting adjusted deal cmv! " + e.toString());
		}
	}

	/**
	 * Get actual cmv of this deal. Formula: actual face value where
	 * enforcibility is yes + set off cash + Hedged collateral
	 * 
	 * @return Amount
	 */
	public Amount getActualCMVAmt() {
		String ccy = getDealAmtCCyCode();
		if (ccy == null) {
			return null;
		}

		Amount totalAmt = null;

		try {
			Amount amt = getEnforcedFaceValueAmt(false);
			if (amt != null) {
				totalAmt = amt;
			}

			amt = getTotalSetOffCashAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			amt = getTotalReqCashAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}

			amt = getHedgeProfitLossAmt();
			if (amt != null) {
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					totalAmt.addToThis(amt);
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting deal actual cmv! " + e.toString());
		}
	}

	/**
	 * Get face value amount. Formula: Oustanding quantity x Adjusted Pricing
	 * 
	 * @return Amount
	 */
	public Amount getFaceValueAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			if (ccy == null) {
				return null;
			}

			Amount adjPrice = getAdjustedPricingAmt();
			if ((adjPrice != null) && (getActualQuantity() != null)) {

				Quantity balanceQty = getBalanceDealQty();
				BigDecimal quantity = new BigDecimal("0");
				QuantityConversionRate rate = getContractMarketUOMConversionRate();
				if ((rate != null) && (balanceQty != null)) {
					balanceQty = (Quantity) rate.convert(balanceQty);
					quantity = balanceQty.getQuantity();

				}
				Amount faceVal = new Amount(quantity.multiply(adjPrice.getAmountAsBigDecimal()), adjPrice
						.getCurrencyCodeAsObject());
				return AmountConversion.getConversionAmount(faceVal, ccy);
			}
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting adjusted face value!" + e.toString());
		}
	}

	/**
	 * Get actual face value amount. Formaula: Outstanding Quantity x Actual
	 * Pricing
	 * @return
	 */
	public Amount getActualFaceValueAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			if (ccy == null) {
				return null;
			}

			if ((getActualPrice() != null) && (getActualQuantity() != null)) {
				Amount adjPrice = new Amount(getActualPrice().getAmountAsBigDecimal(), getActualPrice()
						.getCurrencyCodeAsObject());
				Quantity balanceQty = getBalanceDealQty();
				BigDecimal quantity = new BigDecimal("0");
				QuantityConversionRate rate = getContractMarketUOMConversionRate();
				if ((rate != null) && (balanceQty != null)) {
					balanceQty = (Quantity) rate.convert(balanceQty);
					quantity = balanceQty.getQuantity();
				}

				Amount faceVal = new Amount(quantity.multiply(adjPrice.getAmountAsBigDecimal()), adjPrice
						.getCurrencyCodeAsObject());
				return AmountConversion.getConversionAmount(faceVal, ccy);
			}
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting actual face value!" + e.toString());
		}
	}

	/**
	 * Get face value if the enforcibility is attained using original face value
	 * currency. Formula: Outstanding Quantity x Adjusted Pricing (or normal
	 * without adjustment) where enforcibiliy = yes
	 * 
	 * @return Amount
	 */
	public Amount getEnforcedFaceValueAmt(boolean isAdjusted) {
		try {
			String isEnforced = getIsEnforceAttained();
			if ((isEnforced != null) && isEnforced.equals(ICMSConstant.TRUE_VALUE)) {
				if (isAdjusted) {
					return getFaceValueAmt();
				}
				else {
					return getActualFaceValueAmt();
				}
			}
			return null;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting enforced face value!" + e.toString());
		}
	}

	/**
	 * Get adjusted pricing amount. Formula: commodity market price + customer
	 * differential + commodity differential
	 * 
	 * @return Amount
	 */
	public Amount getAdjustedPricingAmt() {
		Amount cmdtDiff = null, custDiff = null;

		try {
			if (getActualPrice() == null) {
				return null;
			}

			Amount adjPrice = new Amount(getActualPrice().getAmountAsBigDecimal(), new CurrencyCode(getActualPrice()
					.getCurrencyCode()));
			if (getActualCommonDifferential() != null) {
				if (getActualCommonDifferential().getPrice() != null) {
					cmdtDiff = new Amount(getActualCommonDifferential().getPrice().getAmountAsBigDecimal(),
							new CurrencyCode(adjPrice.getCurrencyCode()));
				}
				if ((getActualCommonDifferential().getSign() != null) && (cmdtDiff != null)) {
					if (getActualCommonDifferential().getSign().getName().equals(
							DifferentialSign.POSITIVE_DIFFERENTIAL.getName())) {
						adjPrice.addToThis(cmdtDiff);
					}
					else if (getActualCommonDifferential().getSign().getName().equals(
							DifferentialSign.NEGATIVE_DIFFERENTIAL.getName())) {
						adjPrice.subtractFromThis(cmdtDiff);
					}
				}
			}

			if (getActualEODCustomerDifferential() != null) {
				if (getActualEODCustomerDifferential().getPrice() != null) {
					custDiff = new Amount(getActualEODCustomerDifferential().getPrice().getAmountAsBigDecimal(),
							new CurrencyCode(adjPrice.getCurrencyCode()));
				}
				if ((getActualEODCustomerDifferential().getSign() != null) && (custDiff != null)) {
					if (getActualEODCustomerDifferential().getSign().getName().equals(
							DifferentialSign.POSITIVE_DIFFERENTIAL.getName())) {
						adjPrice.addToThis(custDiff);
					}
					else if (getActualEODCustomerDifferential().getSign().getName().equals(
							DifferentialSign.NEGATIVE_DIFFERENTIAL.getName())) {
						adjPrice.subtractFromThis(custDiff);
					}
				}
			}
			return adjPrice;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating the adjusting pricing!" + e.toString());
		}
	}

	/**
	 * Get profit/loss of hedged collateral where the hedging has not expired.
	 * Formula: (Hedged price - market price) * hedged quantity
	 * 
	 * @return Amount
	 */
	public Amount getHedgeProfitLossAmt() {
		try {
			if (getActualPrice() == null) {
				return null; // no market price
			}

			int length = 0;
			if ((getHedgePriceExtension() == null) || ((length = getHedgePriceExtension().length) == 0)) {
				return null;
			}

			Date endDate = getHedgePriceExtension()[length - 1].getEndDate();
			GregorianCalendar g = new GregorianCalendar();
			g.set(Calendar.HOUR_OF_DAY, 0);
			g.set(Calendar.MINUTE, 0);
			g.set(Calendar.SECOND, 0);
			g.set(Calendar.MILLISECOND, 0);
			Date currDate = g.getTime();

			if ((endDate != null) && endDate.before(currDate)) {
				return null; // expired.
			}

			String ccy = getDealAmtCCyCode();
			if (ccy == null) {
				return null;
			}

			BigDecimal hedgePrice = new BigDecimal(0d), actualPrice = new BigDecimal(0d), qty = new BigDecimal(0d);
			if (getHedgePrice() != null) {
				hedgePrice = AmountConversion.getConversionAmount(getHedgePrice(), ccy).getAmountAsBigDecimal();
			}
			if (getActualPrice() != null) {
				actualPrice = AmountConversion.getConversionAmount(getActualPrice(), ccy).getAmountAsBigDecimal();
			}
			if (getHedgeQuantity() != null) {
				qty = getHedgeQuantity().getQuantity();
				QuantityConversionRate rate = getContractMarketUOMConversionRate();
				if (rate != null) {
					qty = ((Quantity) rate.convert(getHedgeQuantity())).getQuantity();
				}
			}
			BigDecimal totalVal = hedgePrice.subtract(actualPrice).multiply(qty);
			return new Amount(totalVal, new CurrencyCode(ccy));
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting hedged profit/loss! " + e.toString());
		}
	}

	/**
	 * Get total set off cash amount, converted to deal amount currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalSetOffCashAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			int size = 0;

			if (ccy == null) {
				return null;
			}

			if ((cashDeposits == null) || ((size = cashDeposits.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				String cashType = cashDeposits[i].getDepositType();
				if ((cashType != null) && cashType.equals(ICMSConstant.CASH_TYPE_SETOFF)) {
					Amount amt = AmountConversion.getConversionAmount(cashDeposits[i].getAmount(), ccy);
					if (totalAmt == null) {
						totalAmt = amt;
					}
					else {
						if (amt != null) {
							totalAmt.addToThis(amt);
						}
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total set off cash amount! " + e.toString());
		}
	}

	/**
	 * Get total requirement cash amount converted to deal amount currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalReqCashAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			int size = 0;

			if (ccy == null) {
				return null;
			}

			if ((cashDeposits == null) || ((size = cashDeposits.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				String cashType = cashDeposits[i].getDepositType();
				if ((cashType != null) && cashType.equals(ICMSConstant.CASH_TYPE_REQUIREMENT)) {
					Amount amt = AmountConversion.getConversionAmount(cashDeposits[i].getAmount(), ccy);
					if (totalAmt == null) {
						totalAmt = amt;
					}
					else {
						if (amt != null) {
							totalAmt.addToThis(amt);
						}
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total requirement cash amount! " + e.toString());
		}
	}

	/**
	 * Get total cash deposit amount.
	 * 
	 * @return Amount
	 */
	public Amount getTotalCashDepositAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			int size = 0;

			if (ccy == null) {
				return null;
			}

			if ((cashDeposits == null) || ((size = cashDeposits.length) == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < size; i++) {
				Amount amt = AmountConversion.getConversionAmount(cashDeposits[i].getAmount(), ccy);
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					if (amt != null) {
						totalAmt.addToThis(amt);
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total cash deposit amount! " + e.toString());
		}
	}

	/**
	 * Get deal amount. Formula: Original face value * % of financing in 2 dp
	 * 
	 * @return Amount
	 */
	public Amount getDealAmt() {
		if ((origFaceValue != null) && (financingPct != ICMSConstant.DOUBLE_INVALID_VALUE)) {
			BigDecimal dealAmtVal = CommonUtil.calcAfterPercent(origFaceValue.getAmountAsBigDecimal(), financingPct);
			// round amt to 2 dp since comparision shd be done with the value
			// displayed in the ui
			dealAmtVal = CommonUtil.roundAmount(dealAmtVal, CommodityConstant.DEAL_AMT_SCALE);
			return new Amount(dealAmtVal, origFaceValue.getCurrencyCodeAsObject());
		}
		return null;
	}

	/**
	 * Get currency code for the deal amount.
	 * 
	 * @return String
	 */
	public String getDealAmtCCyCode() {
		if (getOrigFaceValue() != null) {
			return getOrigFaceValue().getCurrencyCode();
		}
		return null;
	}

	/**
	 * Get total settlement amount in deal amount currency code.
	 * 
	 * @return Amount
	 */
	public Amount getTotalSettlementAmt() {
		try {
			String ccy = getDealAmtCCyCode();
			if (ccy == null) {
				return null;
			}

			if ((settlements == null) || (settlements.length == 0)) {
				return null;
			}

			Amount totalAmt = null;
			for (int i = 0; i < settlements.length; i++) {
				Amount amt = AmountConversion.getConversionAmount(settlements[i].getPaymentAmt(), ccy);
				if (totalAmt == null) {
					totalAmt = amt;
				}
				else {
					if (amt != null) {
						totalAmt.addToThis(amt);
					}
				}
			}
			return totalAmt;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total settlements! " + e.toString());
		}
	}

	/**
	 * Get balance deal quantity outstanding.
	 * 
	 * @return Quantity
	 */
	public Quantity getBalanceDealQty() {
		Quantity qtyReleased = getTotalQuantityReleased();
		BigDecimal releasedVal = new BigDecimal(0d);
		BigDecimal totalQty = new BigDecimal(0d);
		UOMWrapper uom = null;

		if ((qtyReleased == null) && (getActualQuantity() == null)) {
			return null;
		}

		if (qtyReleased != null) {
			releasedVal = qtyReleased.getQuantity();
			uom = qtyReleased.getUnitofMeasure();
		}

		if (getActualQuantity() != null) {
			totalQty = getActualQuantity().getQuantity();
			uom = getActualQuantity().getUnitofMeasure();
		}

		return new Quantity(totalQty.subtract(releasedVal), uom);
	}

	/**
	 * Get total warehouse receipt quantity.
	 * 
	 * @return Quantity
	 */
	// Method modified by Pratheepa on 16.01.2006 while fixing R1.5 CR129.Added
	// check for DOC_TYPE_WAREHOUSE_RECEIPT_N also.
	public Quantity getTotalWRQuantity() {
		ICommodityTitleDocument[] titleDocs = getTitleDocsLatest();

		ICommodityTitleDocument wr = null;
		ICommodityTitleDocument wrN = null;
		if (titleDocs != null) {
			for (int i = 0; i < titleDocs.length; i++) {
				if (titleDocs[i].getTitleDocType().getName().equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT)) {
					wr = titleDocs[i];
				}
				else if (titleDocs[i].getTitleDocType().getName().equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N)) {
					wrN = titleDocs[i];
				}

				if ((wr != null) && (wrN != null)) {
					break;
				}
			}
		}

		if ((wr == null) || (wrN == null)) {
			titleDocs = getTitleDocsAll();
			if (titleDocs != null) {
				for (int i = 0; i < titleDocs.length; i++) {
					if ((wr == null)
							&& titleDocs[i].getTitleDocType().getName().equals(ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT)) {
						wr = titleDocs[i];
					}
					else if ((wrN == null)
							&& titleDocs[i].getTitleDocType().getName().equals(
									ICMSConstant.DOC_TYPE_WAREHOUSE_RECEIPT_N)) {
						wrN = titleDocs[i];
					}
					if ((wr != null) && (wrN != null)) {
						break;
					}
				}
			}
		}

		if ((wr == null) && (wrN == null)) {
			return null; // no warehouse receipt title doc
		}

		Quantity totalQty = null;
		if (wr != null) {
			totalQty = countWRQuantity(wr.getWarehouseReceipts(), totalQty);
		}
		if (wrN != null) {
			totalQty = countWRQuantity(wrN.getWarehouseReceipts(), totalQty);
		}
		return totalQty;
	}

	/**
	 * Helper method to count a list of Warehouse Receipt Quantity
	 */
	private Quantity countWRQuantity(IWarehouseReceipt[] rcpts, Quantity totalQty) {
		if ((rcpts == null) || (rcpts.length == 0)) {
			return totalQty;
		}
		for (int i = 0; i < rcpts.length; i++) {
			Quantity qty = null;
			if (rcpts[i].getQuantity() != null) {
				qty = new Quantity(rcpts[i].getQuantity().getQuantity(), rcpts[i].getQuantity().getUnitofMeasure());
			}
			if (totalQty == null) {
				totalQty = qty;
			}
			else {
				if (qty != null) {
					totalQty = new Quantity(totalQty.getQuantity().add(qty.getQuantity()), totalQty.getUnitofMeasure());
				}
			}
		}
		return totalQty;
	}

	/**
	 * Get total quantity released for this deal.
	 * 
	 * @return Quantity
	 */
	public Quantity getTotalQuantityReleased() {
		try {
			if ((receiptReleases == null) || (receiptReleases.length == 0)) {
				return null;
			}

			Quantity totalQty = null;
			for (int i = 0; i < receiptReleases.length; i++) {
				Quantity tempQty = receiptReleases[i].getTotalReleasedQuantity();
				Quantity qty = null;
				if (tempQty != null) {
					qty = new Quantity(tempQty.getQuantity(), tempQty.getUnitofMeasure());
				}

				if (totalQty == null) {
					totalQty = qty;
				}
				else {
					if (qty != null) {
						totalQty = new Quantity(totalQty.getQuantity().add(qty.getQuantity()), totalQty
								.getUnitofMeasure());
					}
				}
			}
			return totalQty;
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in getting total quantity released! " + e.toString());
		}
	}

	/**
	 * Get outstanding balance deal amount. Formula: Deal amount - Total
	 * settlement amount
	 * 
	 * @return Amount
	 */
	public Amount getBalanceDealAmt() {
		try {
			Amount dealAmt = getDealAmt();
			Amount settleAmt = getTotalSettlementAmt();

			if (settleAmt == null) {
				return dealAmt;
			}

			if (dealAmt == null) {
				dealAmt = new Amount(0d, settleAmt.getCurrencyCode());
			}
			return dealAmt.subtract(settleAmt);
		}
		catch (Exception e) {
			throw new RuntimeException("Exception in calculating outstanding balance deal amt!" + e.toString());
		}
	}

	/**
	 * Get face value post advance eligibility rate. Formula: Adjusted Pricing x
	 * Actual Qty x Eligibility of Advance
	 * 
	 * @return Amount
	 */
	public Amount getFaceValuePostAdvRateAmt() {
		Amount faceValue = getFaceValueAmt();
		if (faceValue == null) {
			return null;
		}

		ICommodityTitleDocument td = OBCommodityTitleDocument.getTitleDocsLatestByTitleDocID(titleDocsAll);
		if (td == null) {
			return null;
		}
		BigDecimal postVal = CommonUtil.calcAfterPercent(faceValue.getAmountAsBigDecimal(), td.getAdvEligibilityPct());
		return new Amount(postVal, faceValue.getCurrencyCodeAsObject());
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(commodityDealID);
		return hash.hashCode();
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof ICommodityDeal)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}