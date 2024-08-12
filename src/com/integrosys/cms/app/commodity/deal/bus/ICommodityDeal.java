/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/ICommodityDeal.java,v 1.29 2006/08/14 04:11:57 nkumar Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.QuantityDifferential;
import com.integrosys.cms.app.commodity.deal.bus.cash.IDealCashDeposit;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.IFinancingDoc;
import com.integrosys.cms.app.commodity.deal.bus.finance.IHedgePriceExtension;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.ISettlement;
import com.integrosys.cms.app.commodity.deal.bus.purchasesales.IPurchaseAndSalesDetails;

/**
 * This interface represents a Commodity Deal.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.29 $
 * @since $Date: 2006/08/14 04:11:57 $ Tag: $Name: $
 */
public interface ICommodityDeal extends Serializable {
	/**
	 * Get the commodity deal id.
	 * 
	 * @return long
	 */
	public long getCommodityDealID();

	/**
	 * Set the commodity deal id.
	 * 
	 * @param commodityDealID of type long
	 */
	public void setCommodityDealID(long commodityDealID);

	/**
	 * Get deal number.
	 * 
	 * @return String
	 */
	public String getDealNo();

	/**
	 * Set deal number.
	 * 
	 * @param dealNo of type String
	 */
	public void setDealNo(String dealNo);

	/**
	 * Get deal reference number.
	 * 
	 * @return String
	 */
	public String getDealReferenceNo();

	/**
	 * Set deal reference number.
	 * 
	 * @param dealReferenceNo of type String
	 */
	public void setDealReferenceNo(String dealReferenceNo);

	/**
	 * Get deal type code: pool or specific.
	 * 
	 * @return String
	 */
	public String getDealTypeCode();

	/**
	 * Set deal type code: pool or specific.
	 * 
	 * @param dealTypeCode of type String
	 */
	public void setDealTypeCode(String dealTypeCode);

	/**
	 * Get cms collateral id.
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Set cms collateral id.
	 * 
	 * @param collateralID of type long
	 */
	public void setCollateralID(long collateralID);

	/**
	 * Get cms limit id.
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Set cms limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID);

	public long getSubLimitID();

	public void setSubLimitID(long limitID);

	// start of CR035
	public long getCoBorrowerLimitID();

	public void setCoBorrowerLimitID(long coBorrowerLimitID);

	public String getCustomerCategory();

	public void setCustomerCategory(String customerCategory);

	// end of CR035

	/**
	 * Get deal maturity date.
	 * 
	 * @return Date
	 */
	public Date getDealMaturityDate();

	/**
	 * Set deal maturity date.
	 * 
	 * @param dealMaturityDate of type Date
	 */
	public void setDealMaturityDate(Date dealMaturityDate);

	/**
	 * Get extended deal maturity date.
	 * 
	 * @return Date
	 */
	public Date getExtendedDealMaturityDate();

	/**
	 * Set extended deal maturity date.
	 * 
	 * @param extendedDealMaturityDate of type Date
	 */
	public void setExtendedDealMaturityDate(Date extendedDealMaturityDate);

	/**
	 * Get financing percentage.
	 * 
	 * @return double
	 */
	public double getFinancingPct();

	/**
	 * Set financing percentage.
	 * 
	 * @param financingPct of type double
	 */
	public void setFinancingPct(double financingPct);

	/**
	 * Get if the deal is pre sold.
	 * 
	 * @return String
	 */
	public String getIsPreSold();

	/**
	 * Set if the deal is pre sold.
	 * 
	 * @param isPreSold of type String
	 */
	public void setIsPreSold(String isPreSold);

	/**
	 * Get if the enforcibility is attained.
	 * 
	 * @return String
	 */
	public String getIsEnforceAttained();

	/**
	 * Set if the enforcibility is attained.
	 * 
	 * @param isEnforceAttained of type String
	 */
	public void setIsEnforceAttained(String isEnforceAttained);

	/**
	 * Get if the plege documents are required.
	 * 
	 * @return String
	 */
	public String getIsPledgeDocumentRequired();

	/**
	 * Set if the plege documents are required.
	 * 
	 * @param isPledgeDocumentRequiredStr of type String
	 */
	public void setIsPledgeDocumentRequired(String isPledgeDocumentRequiredStr);

	/**
	 * Get date of enforcibility attained.
	 * 
	 * @return Date
	 */
	public Date getEnforceAttainedDate();

	/**
	 * Set date of enforcibility attained.
	 * 
	 * @param enforceAttainedDate of type Date
	 */
	public void setEnforceAttainedDate(Date enforceAttainedDate);

	/**
	 * Get original face value.
	 * 
	 * @return Amount
	 */
	public Amount getOrigFaceValue();

	/**
	 * Set original face value.
	 * 
	 * @param origFaceValue of type Amount
	 */
	public void setOrigFaceValue(Amount origFaceValue);

	/**
	 * Get cash margin percentage.
	 * 
	 * @return double
	 */
	public double getCashMarginPct();

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashMarginPct of type double
	 */
	public void setCashMarginPct(double cashMarginPct);

	/**
	 * Get cash requirement percentage.
	 * 
	 * @return double
	 */
	public double getCashReqPct();

	/**
	 * Set cash margin percentage.
	 * 
	 * @param cashReqPct of type double
	 */
	public void setCashReqPct(double cashReqPct);

	/**
	 * Get latest shipment date.
	 * 
	 * @return Date
	 */
	public Date getLatestShipDate();

	/**
	 * Set latest shiping date.
	 * 
	 * @param latestShipDate of type Date
	 */
	public void setLatestShipDate(Date latestShipDate);

	/**
	 * Get container number.
	 * 
	 * @return String
	 */
	public String getContainerNo();

	/**
	 * Set container number.
	 * 
	 * @param containerNo of type String
	 */
	public void setContainerNo(String containerNo);

	/**
	 * Get shipping marks.
	 * 
	 * @return String
	 */
	public String getShippingMarks();

	/**
	 * Set shipping marks.
	 * 
	 * @param shippingMarks of type String
	 */
	public void setShippingMarks(String shippingMarks);

	/**
	 * Get financing documents.
	 * 
	 * @return IFinancingDoc[]
	 */
	public IFinancingDoc[] getFinancingDocs();

	/**
	 * Set financing documents.
	 * 
	 * @param financingDocs of type IFinancingDoc[]
	 */
	public void setFinancingDocs(IFinancingDoc[] financingDocs);

	/**
	 * Get all commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsAll();

	/**
	 * Set all commodity title documents.
	 * 
	 * @param titleDocsAll of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsAll(ICommodityTitleDocument[] titleDocsAll);

	/**
	 * Get latest commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsLatest();

	/**
	 * Set latest commodity title documents.
	 * 
	 * @param titleDocsLatest of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsLatest(ICommodityTitleDocument[] titleDocsLatest);

	/**
	 * Get historical commodity title documents.
	 * 
	 * @return ICommodityTitleDocument[]
	 */
	public ICommodityTitleDocument[] getTitleDocsHistory();

	/**
	 * Set historical commodity title document.
	 * 
	 * @param titleDocsHistory of type ICommodityTitleDocument[]
	 */
	public void setTitleDocsHistory(ICommodityTitleDocument[] titleDocsHistory);

	/**
	 * Get if warehouse receipt title doc exists.
	 * 
	 * @return boolean
	 */
	public boolean getIsAnyWRTitleDoc();

	/**
	 * Set if warehouse receipt title doc exists.
	 * 
	 * @param isAnyWRTitleDoc of type boolean
	 */
	public void setIsAnyWRTitleDoc(boolean isAnyWRTitleDoc);

	// Added by pratheepa for CR129
	public boolean getIsAnyWRTitleDoc_N();

	public void setIsAnyWRTitleDoc_N(boolean isAnyWRTitleDoc);

	public boolean getIsAnyWRTitleDoc_NN();

	public void setIsAnyWRTitleDoc_NN(boolean isAnyWRTitleDoc);

	/**
	 * Get commodity settlements.
	 * 
	 * @return ISettlement[]
	 */
	public ISettlement[] getSettlements();

	/**
	 * Set commodity settlements.
	 * 
	 * @param settlements of type ISettlement[]
	 */
	public void setSettlements(ISettlement[] settlements);

	/**
	 * Get released warehouse receipts.
	 * 
	 * @return IReceiptRelease[]
	 */
	public IReceiptRelease[] getReceiptReleases();

	/**
	 * Set released warehouse receipts.
	 * 
	 * @param receiptReleases of type IReceiptRelease[]
	 */
	public void setReceiptReleases(IReceiptRelease[] receiptReleases);

	/**
	 * Gets contract ID
	 * 
	 * @return long
	 */
	public long getContractID();

	/**
	 * Sets contract ID
	 * 
	 * @param contractID of type long
	 */
	public void setContractID(long contractID);

	/**
	 * Get contract reuters identification code.
	 * 
	 * @return String
	 */
	public String getContractRIC();

	/**
	 * Set contract reuters id code.
	 * 
	 * @param contractRIC of type String
	 */
	public void setContractRIC(String contractRIC);

	/**
	 * Gets contracted quantity of deal.
	 * 
	 * @return contracted quantity
	 */
	public Quantity getContractQuantity();

	/**
	 * Sets contracted quantity to deal.
	 * 
	 * @param aQty - contracted quantity
	 */
	public void setContractQuantity(Quantity aQty);

	/**
	 * Gets contracted market UOM conversion rate.
	 * 
	 * @return IConversionRate
	 */
	public QuantityConversionRate getContractMarketUOMConversionRate();

	/**
	 * Sets contracted market UOM conversion rate.
	 * 
	 * @param mktUOMConversionRate - IConversionRate
	 */
	public void setContractMarketUOMConversionRate(QuantityConversionRate mktUOMConversionRate);

	/**
	 * Gets contracted metric UOM conversion rate.
	 * 
	 * @return IConversionRate
	 */
	public QuantityConversionRate getContractMetricUOMConversionRate();

	/**
	 * Sets contracted metric UOM conversion rate.
	 * 
	 * @param metricUOMConversionRate - IConversionRate
	 */
	public void setContractMetricUOMConversionRate(QuantityConversionRate metricUOMConversionRate);

	/**
	 * Gets contracted quantity differential of deal.
	 * 
	 * @return contracted quantity differential
	 */
	public QuantityDifferential getContractQuantityDifferential();

	/**
	 * Sets contracted quantity differential to deal.
	 * 
	 * @param aQtyDiff - contracted quantity differential
	 */
	public void setContractQuantityDifferential(QuantityDifferential aQtyDiff);

	/**
	 * Get remarks of commodity deal.
	 * 
	 * @return String
	 */
	public String getRemarks();

	/**
	 * Set remarks of commodity deal.
	 * 
	 * @param remarks of type String
	 */
	public void setRemarks(String remarks);

	/**
	 * Get the date on which the deal is first created.
	 * 
	 * @return Date
	 */
	public Date getDealDate();

	/**
	 * Set the date on which the deal is first created.
	 * 
	 * @param dealDate of type Date
	 */
	public void setDealDate(Date dealDate);

	/*********** OTHERS ************/
	/**
	 * Gets the cash deposits for the deal.
	 * 
	 * @return IDealCashDeposit[]
	 */
	public IDealCashDeposit[] getCashDeposit();

	/**
	 * Sets the cash deposits to the deal.
	 * 
	 * @param deposits - IDealCashDeposit[]
	 */
	public void setCashDeposit(IDealCashDeposit[] deposits);

	/**
	 * Gets the purchase and sales details of the deal.
	 * 
	 * @return IPurchaseAndSalesDetails
	 */
	public IPurchaseAndSalesDetails getPurchaseAndSalesDetails();

	/**
	 * Sets the purchase and sales details to the deal.
	 * 
	 * @param details - IPurchaseAndSalesDetails
	 */
	public void setPurchaseAndSalesDetails(IPurchaseAndSalesDetails details);

	/**
	 * Gets the hedged price extension of the deal.
	 * 
	 * @return IHedgePriceExtension[]
	 */
	public IHedgePriceExtension[] getHedgePriceExtension();

	/**
	 * SEts the hedge price extension to the deal.
	 * 
	 * @param extensions - IHedgePriceExtension
	 */
	public void setHedgePriceExtension(IHedgePriceExtension[] extensions);

	/*********** PRICE HEDGING ************/
	/**
	 * Gets the hedging contract ID the hedge price is based on.
	 * 
	 * @return long - the hedge contract ID
	 */
	public long getHedgeContractID();

	/**
	 * Sets the hedging contract ID the hedge price is based on.
	 * 
	 * @param aContractID - long representing the ID of the hedging contract.
	 */
	public void setHedgeContractID(long aContractID);

	/**
	 * Gets the hedged price for the deal.
	 * 
	 * @return Amount representing the hedged Price
	 */
	public Amount getHedgePrice();

	/**
	 * Sets the hedged price for the deal.
	 * 
	 * @param aPrice - Amount representing the hedged price
	 */
	public void setHedgePrice(Amount aPrice);

	/**
	 * Gets the quantity of goods in deal to be hedged.
	 * 
	 * @return Quantity representing the hedged quantity
	 */
	public Quantity getHedgeQuantity();

	/**
	 * Sets the quantity of goods in deal to be hedged.
	 * 
	 * @param aQty - Quantity representing the hedged quantity
	 */
	public void setHedgeQuantity(Quantity aQty);

	/********** CONTRACTED DEAL ************/

	/**
	 * Gets the profile ID of the product sub-type specified in the contracted
	 * deal.
	 * 
	 * @return long representing the profile ID of the product sub-type
	 */
	public long getContractProfileID();

	/**
	 * Sets the profile ID of the product sub-type for the contracted deal.
	 * 
	 * @param aProfileID
	 */
	public void setContractProfileID(long aProfileID);

	/**
	 * Gets the price type from the contracted deal. This will indicate which
	 * price should be used to value the deal.
	 * 
	 * @return PriceType representing the contracted deal price type.
	 */
	public PriceType getContractPriceType();

	/**
	 * Sets the price type for the contracted deal. This will indicate which
	 * price will be used to value the deal.
	 * 
	 * @param aPriceType - PriceType representing the price type
	 */
	public void setContractPriceType(PriceType aPriceType);

	/**
	 * Gets the price from the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal was purchased.
	 * 
	 * @return Amount representing the purchase price
	 */
	public Amount getContractPrice();

	/**
	 * Sets the price to the contracted deal. This is the price at which the
	 * product sub-type specified in the contracted deal is purchased.
	 * 
	 * @param aPrice - Amount representing the price
	 */
	public void setContractPrice(Amount aPrice);

	/**
	 * Gets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @return PriceDifferential
	 */
	public PriceDifferential getContractPriceDifferential();

	/**
	 * Sets the price differential for the contracted deal. This is the price
	 * differential agreed upon by both the buyer and seller.
	 * 
	 * @param aPriceDifferential - PriceDifferential
	 */
	public void setContractPriceDifferential(PriceDifferential aPriceDifferential);

	/*********** ACTUAL DEAL ************/

	/**
	 * Gets the quantity of product sub-type specified in the contracted deal
	 * traded in the actual deal.
	 * 
	 * @return Quantity representing the actual quantity traded
	 */
	public Quantity getActualQuantity();

	/**
	 * Sets the quantity of product sub-type specified in the contracted deal
	 * traded in the actual deal.
	 * 
	 * @param aQty - Quantity representing the actual quantity traded.
	 */
	public void setActualQuantity(Quantity aQty);

	/**
	 * Gets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @return Amount representing the actual price
	 */
	public Amount getActualPrice();

	/**
	 * Sets the price at which the product sub-type specified in the contracted
	 * deal is valued at in the actual deal.
	 * 
	 * @param aPrice - Amount representing the actual price
	 */
	public void setActualPrice(Amount aPrice);

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
	public PriceDifferential getActualCommonDifferential();

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
	public void setActualCommonDifferential(PriceDifferential commonDiff);

	/**
	 * Gets the customer price differential. This is only utilised when
	 * calculating actual price for a contracted deal using Floating Futures
	 * price type.
	 * 
	 * @return PriceDifferential representing customer price differential
	 */
	public PriceDifferential getActualEODCustomerDifferential();

	/**
	 * Sets the customer price differential. This is utilised when calculating
	 * actual price for a contracted deal using EOD or Floating Futures price
	 * type.
	 * 
	 * @param customerDiff - PriceDifferential
	 */
	public void setActualEODCustomerDifferential(PriceDifferential customerDiff);

	/**
	 * Gets the date on which the market price is retrieved.
	 * 
	 * @return Date
	 */
	public Date getActualMarketPriceDate();

	/**
	 * Sets the date on which the market price is retrieved.
	 * 
	 * @param priceDate - Date
	 */
	public void setActualMarketPriceDate(Date priceDate);

	/**
	 * Get commodity deal cmv.
	 * 
	 * @return Amount
	 */
	public Amount getCMV();

	/**
	 * Set commodity deal cmv.
	 * 
	 * @param cmv of type Amount
	 */
	public void setCMV(Amount cmv);

	/**
	 * Get commodity deal fsv.
	 * 
	 * @return Amount
	 */
	public Amount getFSV();

	/**
	 * Set commodity deal fsv.
	 * 
	 * @param fsv of type Amount
	 */
	public void setFSV(Amount fsv);

	/**
	 * Get the status of this commodity deal.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of this commodity deal.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version of the deal.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version of the deal.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

	// *************** online calculated values ****************

	/**
	 * Get newly calculated fsv of this deal. Formula: commodity face value
	 * where enforcibility is yes*crp + set off cash*cash margin + Hedged
	 * collateral* hedge margin
	 * 
	 * @param crp security crp
	 * @param hedgeMargin margin for hedge collateral
	 * @return Amount
	 */
	public Amount getCalculatedFSVAmt(double crp, double hedgeMargin);

	/**
	 * Get actual fsv of this deal. Formula: actual face value where
	 * enforcibility is yes*crp + set off cash*cash margin + Hedged collateral*
	 * hedge margin
	 * 
	 * @param crp security crp
	 * @param hedgeMargin margin for hedge collateral
	 * @return Amount
	 */
	public Amount getActualFSVAmt(double crp, double hedgeMargin);

	/**
	 * Get newly calculated adjusted cmv of this deal.
	 * 
	 * @return Amount
	 */
	public Amount getCalculatedCMVAmt();

	/**
	 * Get actual cmv of this deal. Formula: actual face value where
	 * enforcibility is yes + set off cash + Hedged collateral
	 * 
	 * @return Amount
	 */
	public Amount getActualCMVAmt();

	/**
	 * Get face value amount. Formaula: Actual quantity x Adjusted Pricing
	 * @return
	 */
	public Amount getFaceValueAmt();

	/**
	 * Get adjusted pricing amount in original face value currency. Formula:
	 * commodity market price + customer differential + commodity differential
	 * 
	 * @return Amount
	 */
	public Amount getAdjustedPricingAmt();

	/**
	 * Get profit/loss of hedged collaterals. Formula: (Hedged price - market
	 * price) * hedged quantity
	 * 
	 * @return Amount
	 */
	public Amount getHedgeProfitLossAmt();

	/**
	 * Get total set off cash amount converted to deal amount currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalSetOffCashAmt();

	/**
	 * Get total requirement cash amount converted to deal amount currency.
	 * 
	 * @return Amount
	 */
	public Amount getTotalReqCashAmt();

	/**
	 * Get total cash deposit amount.
	 * 
	 * @return Amount
	 */
	public Amount getTotalCashDepositAmt();

	/**
	 * Get deal amount. Formula: Original face value * % of financing
	 * 
	 * @return Amount
	 */
	public Amount getDealAmt();

	/**
	 * Get currency code for the deal amount.
	 * 
	 * @return String
	 */
	public String getDealAmtCCyCode();

	/**
	 * Get total settlement amount in deal amount currency code.
	 * 
	 * @return Amount
	 */
	public Amount getTotalSettlementAmt();

	/**
	 * Get balance deal quantity outstanding.
	 * 
	 * @return Quantity
	 */
	public Quantity getBalanceDealQty();

	/**
	 * Get total warehouse receipt quantity.
	 * 
	 * @return Quantity
	 */
	public Quantity getTotalWRQuantity();

	/**
	 * Get total quantity released in settlements.
	 * 
	 * @return Quantity
	 */
	public Quantity getTotalQuantityReleased();

	/**
	 * Get outstanding balance deal amount. Formula: Deal amount - Total
	 * settlement amount
	 * 
	 * @return Amount
	 */
	public Amount getBalanceDealAmt();

	/**
	 * Get face value post advance eligibility rate. Formula: Adjusted Pricing x
	 * Actual Qty x Eligibility of Advance
	 * 
	 * @return Amount
	 */
	public Amount getFaceValuePostAdvRateAmt();
}