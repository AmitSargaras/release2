/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/CollateralValuator.java,v 1.106 2006/11/14 13:48:54 jzhai Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.type.asset.IAssetBasedCollateral;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IDebtor;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IFixedAssetOthers;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IGeneralCharge;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge.IStock;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IAssetPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.IPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.asset.subtype.pdcheque.OBPostDatedCheque;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.samecurrency.ISameCurrency;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.document.IDocumentCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.banksameccy.IBankSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.sblcsameccy.ISBLCSameCurrency;
import com.integrosys.cms.app.collateral.bus.type.insurance.ICDSItem;
import com.integrosys.cms.app.collateral.bus.type.insurance.IInsuranceCollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditderivative.ICreditDerivative;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditswaps.ICreditDefaultSwaps;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableCollateral;
import com.integrosys.cms.app.collateral.bus.type.marketable.IMarketableEquity;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IBondsCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.IEquityCommon;
import com.integrosys.cms.app.collateral.bus.type.marketable.subtype.nonlistedlocal.INonListedLocal;
import com.integrosys.cms.app.collateral.bus.type.others.IOthersCollateral;
import com.integrosys.cms.app.collateral.bus.type.property.IPropertyCollateral;
import com.integrosys.cms.app.collateral.bus.valuation.IValuationModel;
import com.integrosys.cms.app.collateral.bus.valuation.support.ValuationUtil;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.collateral.proxy.valuation.ICollateralValuationProxy;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.valuation.IValuationTrxValue;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedEntry;
import com.integrosys.cms.app.feed.bus.bond.IBondFeedGroup;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedEntry;
import com.integrosys.cms.app.feed.bus.stock.IStockFeedGroup;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedEntry;
import com.integrosys.cms.app.feed.bus.unittrust.IUnitTrustFeedGroup;
import com.integrosys.cms.app.feed.proxy.bond.IBondFeedProxy;
import com.integrosys.cms.app.feed.proxy.stock.IStockFeedProxy;
import com.integrosys.cms.app.feed.proxy.unittrust.IUnitTrustFeedProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * A utility class to valuate a collateral.
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.106 $
 * @since $Date: 2006/11/14 13:48:54 $ Tag: $Name: $
 */
public class CollateralValuator implements Serializable {

	private static final long serialVersionUID = -7145383492097429894L;

	private ICollateralValuationProxy collateralValuationProxy;

	/** A list of bond feed price entries. */
	private IBondFeedEntry[] bondFeed = null;

	/** handler to forex manager session bean. */
	private SBForexManager forexMgr = null;

	/** key: security location, value: ArrayList of IUnitTrustFeedEntry objects */
	private HashMap unitTrustMap = new HashMap();

	/** key: stock exchange, value: ArrayList of IStockFeedEntry objects */
	private HashMap stockMap = new HashMap();

	/**
	 * key: country code + subtype code, value: ICollateralParameter (crp value)
	 */
	private HashMap crpMap = new HashMap();

	private static final CollateralValuator LOGOBJ = new CollateralValuator();

	private static int UNIT_PRICE_SCALE = 6;

	public void setCollateralValuationProxy(ICollateralValuationProxy collateralValuationProxy) {
		this.collateralValuationProxy = collateralValuationProxy;
	}

	public ICollateralValuationProxy getCollateralValuationProxy() {
		return collateralValuationProxy;
	}

	/**
	 * Default Constructor
	 */
	public CollateralValuator() {
	}

	public static CollateralValuator getInstance() {
		return LOGOBJ;
	}

	/**
	 * @deprecated Method is no longer in use. Please use
	 *             setCollateralCMVFSV(ICollateral col) instead.
	 * @throws CollateralException
	 */
	public void setCollateralCMVFSV(ICollateral col, ICommodityDeal[] deals) throws CollateralException {

		if (col instanceof ICommodityCollateral) {
			setCommodityCMVFSV((ICommodityCollateral) col, deals, null);
		}
		else if (col instanceof ICashCollateral) {
			setCashCMVFSV((ICashCollateral) col);
		}
		else if (col instanceof IGuaranteeCollateral) {
			setGuaranteeCMVFSV((IGuaranteeCollateral) col);
		}
		else if (col instanceof IDocumentCollateral) {
			setDocumentCMVFSV((IDocumentCollateral) col);
		}
		else if (col instanceof IPropertyCollateral) {
			setOtherCollateralCMV(col);
			setOtherCollateralFSV(col);
		}
		else if (col instanceof IInsuranceCollateral) {

			if ((col instanceof ICreditInsurance) || (col instanceof IKeymanInsurance)) {
				setInsuranceCMVFSV((IInsuranceCollateral) col);
			}
			else if (col instanceof ICreditDefaultSwaps) {
				setInsuranceCDSCMVFSV((IInsuranceCollateral) col);
			}
			else if (col instanceof ICreditDerivative) {
				setInsuranceNew((ICreditDerivative) col);
			}

		}
		else if (col instanceof IAssetBasedCollateral) {
			if (col instanceof IAssetPostDatedCheque) {
				// set FSV also because each pdc has its own margin
				setAssetPtdChequeCMVFSV((IAssetPostDatedCheque) col);
			}
			else if (col instanceof IGeneralCharge) {
				setGeneralChargeCMVFSV((IGeneralCharge) col);
			}
			else {
				setOtherCollateralCMV(col);
				setOtherCollateralFSV(col);
			}
		}
		else if (col instanceof IMarketableCollateral) {
			setMarketableCMVFSV((IMarketableCollateral) col);
		}
		else if (col instanceof IOthersCollateral) {
			setOtherCollateralCMV(col);
			setOtherCollateralFSV(col);
		}
	}

	/**
	 * Set current market value for the given collateral.
	 * 
	 * @param col collateral to be set the cmv value
	 * @throws CollateralException on errors encountered
	 */
	public void setCollateralCMVFSV(ICollateral col) throws CollateralException {
		// setCollateralCMVFSV (col, null);
		ICollateralProxy proxy = CollateralProxyFactory.getProxy();
		IValuationModel latestValuation = proxy.getCollateralCMVFSV(col);

		if (latestValuation == null) {
			return;
		}

		Amount cmvAmt = latestValuation.getValOMV();
		Amount fsvAmt = latestValuation.getValFSV();
		Amount secCmvAmt = col.getCMV();
		boolean isChanged = false;

		if (cmvAmt != null) {
			if (secCmvAmt == null || !secCmvAmt.equals(cmvAmt)) {
				isChanged = true;
			}
		}

		if (cmvAmt != null && isChanged) {
			col.setCMV(cmvAmt);
			col.setCMVCcyCode(cmvAmt.getCurrencyCode());
			col.setValuationType(latestValuation.getValuationType());
			col.setValuer(latestValuation.getValuer());
		}

		if (fsvAmt != null && isChanged) {
			col.setFSV(fsvAmt);
			col.setFSVCcyCode(fsvAmt.getCurrencyCode());
		}

		DefaultLogger.debug(this, "Collateral, id [" + col.getCollateralID() + "] security id ["
				+ col.getSCISecurityID() + "], valuation get changed? [" + (isChanged ? "Yes" : "No") + "]");
		if (isChanged) {
			IValuation valuation = new OBValuation();
			valuation.setCurrencyCode(latestValuation.getValOMV().getCurrencyCode());
			valuation.setValuationDate(latestValuation.getValuationDate());
			valuation.setCMV(latestValuation.getValOMV());
			valuation.setFSV(latestValuation.getValFSV());
			valuation.setRevaluationFreq(latestValuation.getValuationFrequency().getFreq());
			valuation.setRevaluationFreqUnit(latestValuation.getValuationFrequency().getFreqUnit());
			valuation.setSourceType(ICMSConstant.VALUATION_SOURCE_TYPE_A);
			valuation.setSourceId(ICMSConstant.SOURCE_SYSTEM_CMS);

			col.setLastRemarginDate(DateUtil.clearTime(new Date()));
			col.setNextRemarginDate(ValuationUtil.getNextValuationDate(col.getLastRemarginDate(), latestValuation
					.getValuationFrequency()));
			col.setSourceValuation(valuation);
		}
	}

	/**
	 * Helper method to set cmv for other collateral types except guarantee,
	 * marketable.
	 * 
	 * @param col of type ICollateral
	 * @return collateral
	 */
	private ICollateral setOtherCollateralCMV(ICollateral col) {
		Amount cmv = null;

		// valuation cmv, date etc is input online
		if ((col.getValuation() != null) && (col.getValuation().getCurrencyCode() != null)) {
			cmv = col.getValuation().getCMV();
			// col.getValuation().setUpdateDate (new Date
			// (System.currentTimeMillis()));
		}
		col.setCMV(cmv);
		col.setCMVCcyCode(cmv != null ? cmv.getCurrencyCode() : null);
		return col;
	}

	/**
	 * Helper method to get collateral margin.
	 * 
	 * @param col of type ICollateral
	 * @return margin
	 */
	private BigDecimal getMargin(ICollateral col) {
		BigDecimal margin = new BigDecimal(col.getMargin());
		if (margin.doubleValue() < 0) {
			margin = getCRP(col);
		}
		return margin;
	}

	/**
	 * Helper method to get fsv for collateral types updated manually.
	 * 
	 * @param col of type ICollateral
	 */
	private void setOtherCollateralFSV(ICollateral col) {
		Amount fsvAmt = null;

		if ((col.getValuation() != null) && (col.getValuation().getCMV() != null)) {
			fsvAmt = calcFSV(col, col.getValuation().getCMV(), null);
			col.getValuation().setFSV(fsvAmt);
		}
		col.setFSV(fsvAmt);
		col.setFSVCcyCode(col.getFSV() == null ? null : col.getFSV().getCurrencyCode());
	}

	/**
	 * Helper method to calculate FSV given the margin.
	 * 
	 * @param col of type ICollateral
	 * @param cmv of type Amount
	 * @param margin margin
	 * @return FSV amount
	 */
	private Amount calcFSV(ICollateral col, Amount cmv, BigDecimal margin) {
		if (cmv == null) {
			return null;
		}
		Amount fsvAmt = null;
		if (margin == null) {
			margin = getMargin(col);
		}
		if (margin != null) {
			fsvAmt = cmv.multiply(margin);
		}
		return fsvAmt;
	}

	/**
	 * Method to calculate and set cmv for commodity collateral.
	 * 
	 * @param col of type ICommodityCollateral
	 * @throws CollateralException
	 */
	public static void setCommodityCMV(ICommodityCollateral col, Amount[] dealCMV) throws CollateralException {
		String ccy = col.getCurrencyCode();
		if ((ccy == null) || (ccy.length() == 0)) {
			return;
		}

		Amount totalCMV = null;
		int size = dealCMV.length;

		try {
			for (int i = 0; i < size; i++) {
				if (dealCMV[i] != null) {
					Amount cmv = new Amount(dealCMV[i].getAmountAsBigDecimal(), dealCMV[i].getCurrencyCodeAsObject());
					cmv = AmountConversion.getConversionAmount(cmv, ccy);
					if (totalCMV != null) {
						totalCMV.addToThis(cmv);
					}
					else {
						totalCMV = cmv;
					}
				}
			}
		}
		catch (Exception e) {
			throw new CollateralException("Exception caught!" + e.toString());
		}

		IValuation val = col.getValuation();

		if ((val == null) && (totalCMV != null)) {
			val = new OBValuation();
		}

		if (val != null) {
			val.setCollateralID(col.getCollateralID());
			val.setCMV(totalCMV);
			val.setCurrencyCode(ccy);
			val.setValuationDate(new Date(System.currentTimeMillis()));
			col.setValuation(val);
		}

		col.setCMV(totalCMV);
		col.setCMVCcyCode(totalCMV == null ? null : ccy);
	}

	/**
	 * Method to calculate and set cmv/fsv for commodity collateral.
	 * 
	 * @param col of type ICommodityCollateral
	 * @param deals a list of deals attached to the commodity
	 * @param actualDeal the latest updated deal for the commodity
	 * @throws CollateralException
	 */
	public void setCommodityCMVFSV(ICommodityCollateral col, ICommodityDeal[] deals, ICommodityDeal actualDeal)
			throws CollateralException {
		String newCcyCode = getValuationCcy(col);
		if ((newCcyCode == null) || (newCcyCode.length() == 0)) {
			return;
		}

		Amount totalCMV = null, totalFSV = null;
		int size = deals == null ? 0 : deals.length;

		for (int i = 0; i < size; i++) {
			if ((actualDeal != null) && (actualDeal.getCommodityDealID() == deals[i].getCommodityDealID())) {
				totalCMV = sum(actualDeal.getCMV(), totalCMV, newCcyCode);
				totalFSV = sum(actualDeal.getFSV(), totalFSV, newCcyCode);
			}
			else {
				totalCMV = sum(deals[i].getCMV(), totalCMV, newCcyCode);
				totalFSV = sum(deals[i].getFSV(), totalFSV, newCcyCode);
			}
		}
		totalCMV = roundAmount(totalCMV);
		totalFSV = roundAmount(totalFSV);
		boolean updateValDate = false;
		if (isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV)) {
			updateValDate = true;
		}
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
	}

	/**
	 * Method to set FSV for commodity collaterals
	 * 
	 * @param col of type ICommodityCollateral
	 * @param dealFSV a list of FSV amounts
	 * @throws CollateralException on error calculating the commodity FSV
	 */
	public static void setCommodityFSV(ICommodityCollateral col, Amount[] dealFSV) throws CollateralException {
		String ccy = col.getCurrencyCode();
		if ((ccy == null) || (ccy.length() == 0)) {
			return;
		}

		Amount totalFSV = null;
		int size = dealFSV.length;

		try {
			for (int i = 0; i < size; i++) {
				if (dealFSV[i] != null) {
					Amount fsv = new Amount(dealFSV[i].getAmountAsBigDecimal(), dealFSV[i].getCurrencyCodeAsObject());
					fsv = AmountConversion.getConversionAmount(fsv, ccy);
					if (totalFSV != null) {
						totalFSV.addToThis(fsv);
					}
					else {
						totalFSV = fsv;
					}
				}
			}
		}
		catch (Exception e) {
			throw new CollateralException("Exception caught!" + e.toString());
		}

		IValuation val = col.getValuation();

		if ((val == null) && (totalFSV != null)) {
			val = new OBValuation();
		}

		if (val != null) {
			val.setCollateralID(col.getCollateralID());
			val.setFSV(totalFSV);
			val.setCurrencyCode(ccy);
			val.setValuationDate(new Date(System.currentTimeMillis()));
			col.setValuation(val);
		}
		col.setFSV(totalFSV);
		col.setFSVCcyCode(totalFSV == null ? null : ccy);
	}

	/**
	 * Helper method to set cmv for cash collateral.
	 * 
	 * @param col of type ICashCollateral
	 * @throws CollateralException
	 */
	private void setCashCMVFSV(ICashCollateral col) throws CollateralException {
		Amount totalCMV = null, totalFSV = null;
		boolean updateValDate = false;

		String newCcyCode = getValuationCcy(col);
		ICashDeposit[] items = col.getDepositInfo();

		if ((items != null) && (newCcyCode != null)) {
			int count = items.length;
			for (int i = 0; i < count; i++) {
				totalCMV = sum(items[i].getDepositAmount(), totalCMV, newCcyCode);
			}
		}

		DefaultLogger.debug(this, ">>>>>>>>>>>>> totalCMV = " + totalCMV);
		totalCMV = roundAmount(totalCMV);
		totalFSV = roundAmount(calcFSV(col, totalCMV, null));

		if (!(col instanceof ISameCurrency)
				&& (isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV))) {
			updateValDate = true;
		}
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
	}

	/**
	 * Helper method to set common collateral values.
	 * 
	 * @param col of type ICollateral
	 * @param totalCMV of type Amount
	 * @param totalFSV of type Amount
	 */
	private void setCommonColValues(ICollateral col, Amount totalCMV, Amount totalFSV, String newCcyCode,
			boolean updateValDate, Date defaultValDate) {
		IValuation val = col.getValuation();
		if ((val == null) && (totalCMV != null)) {
			val = new OBValuation();
		}
		if (val != null) {
			if (val.getValuationDate() == null) {
				if ((val.getCMV() == null) && (totalCMV == null)) {
					// do nothing
				}
				else {
					val
							.setValuationDate(defaultValDate == null ? new Date(System.currentTimeMillis())
									: defaultValDate);
				}
			}
			else {
				if (updateValDate) {
					/*
					 * Date latestValDate = getDateWithoutTime
					 * (val.getValuationDate()); Date currDate =
					 * getDateWithoutTime (new Date
					 * (System.currentTimeMillis())); if
					 * (latestValDate.compareTo (currDate) != 0) {
					 * val.setValuationDate (new Date
					 * (System.currentTimeMillis())); }
					 */
					val.setValuationDate(new Date(System.currentTimeMillis()));
				}
			}
			val.setCollateralID(col.getCollateralID());
			val.setCMV(totalCMV);
			val.setFSV(totalFSV);
			val.setBeforeMarginValue(totalCMV);
			val.setCurrencyCode(newCcyCode);
			val.setAfterMarginValue(totalFSV);
			col.setValuation(val);
		}
		col.setCMV(totalCMV);
		col.setCMVCcyCode(col.getCMV() == null ? null : col.getCMV().getCurrencyCode());
		col.setFSV(totalFSV);
		col.setFSVCcyCode(col.getFSV() == null ? null : col.getFSV().getCurrencyCode());
	}

	private static boolean isValidAmt(Amount amt) {
		if (amt == null) {
			return false;
		}

		if ((amt.getCurrencyCode() == null) || (amt.getCurrencyCode().length() == 0)) {
			return false;
		}

		return true;
	}

	/**
	 * Helper method to round the amount value.
	 * 
	 * @param amt of type Amount
	 * @return rounded amount
	 */
	public static Amount roundAmount(Amount amt) {
		if (amt == null) {
			return null;
		}

		BigDecimal bd = amt.getAmountAsBigDecimal();
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return new Amount(bd, new CurrencyCode(amt.getCurrencyCode()));
	}

	/**
	 * Helper method to round the amount value.
	 * 
	 * @param amt of type Amount
	 * @return rounded amount
	 */
	public static Amount roundAmount(Amount amt, int scale) {
		if (amt == null) {
			return null;
		}

		BigDecimal bd = amt.getAmountAsBigDecimal();
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		return new Amount(bd, new CurrencyCode(amt.getCurrencyCode()));
	}

	/**
	 * Helper method to set cmv for insurance collateral.
	 * 
	 * @param col of type IInsuranceCollateral
	 * @throws CollateralException
	 */
	private void setInsuranceCMVFSV(IInsuranceCollateral col) throws CollateralException {
		Amount totalCMV = null, totalFSV = null;
		String newCcyCode = getValuationCurrency(col);

		ILimitCharge[] charges = col.getLimitCharges();
		int count = charges == null ? 0 : charges.length;
		for (int i = 0; i < count; i++) {
			totalCMV = sum(charges[i].getChargeAmount(), totalCMV, newCcyCode);
		}
		totalCMV = roundAmount(totalCMV);
		totalFSV = roundAmount(calcFSV(col, totalCMV, null));
		boolean updateValDate = isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV);
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
	}

	/**
	 * Get new currency code for cash conversion.
	 * 
	 * @param cash of type ICashCollateral
	 * @return new currency code
	 */
	private static String getValuationCcy(ICashCollateral cash) {
		if (cash instanceof ISameCurrency) {
			return cash.getCurrencyCode();
		}
		else {
			if (cash.getValuation() != null) {
				return cash.getValuation().getCurrencyCode();
			}
			else {
				return cash.getCurrencyCode();
			}
		}
	}

	/**
	 * Get new currency code for cash conversion.
	 * 
	 * @param col of type ICollateral
	 * @return new currency code
	 */
	private static String getValuationCurrency(ICollateral col) {
		if (col.getValuation() != null) {
			return col.getValuation().getCurrencyCode();
		}
		else {
			return col.getCurrencyCode();
		}
	}

	/**
	 * Get new currency code for commodity.
	 * 
	 * @param cmdt of type ICommodityCollateral
	 * @return new currency code
	 */
	private static String getValuationCcy(ICommodityCollateral cmdt) {
		if (cmdt.getValuation() != null) {
			return cmdt.getValuation().getCurrencyCode();
		}
		return cmdt.getCurrencyCode();
	}

	/**
	 * Helper method to set cmv/fsv for general charge
	 * 
	 * @param col of type IGeneralCharge
	 * @throws CollateralException on error calculating cmv
	 */
	private void setGeneralChargeCMVFSV(IGeneralCharge col) throws CollateralException {
		try {
			String newCcyCode = getValuationCurrency(col);
			Map stocks = col.getStocks();
			Amount totalCMV = null, totalFSV = null;
			if ((stocks != null) && (stocks.size() > 0)) {
				Iterator i = stocks.values().iterator();
				while (i.hasNext()) {
					IStock stock = (IStock) i.next();
					try {
						totalCMV = sum(stock.getGrossValue(), totalCMV, newCcyCode);
						totalFSV = sum(stock.getNetValue(), totalFSV, newCcyCode);
					}
					catch (Exception e) {
						DefaultLogger.warn("CollateralValuator, stock gross:", stock.getGrossValue() + " new Ccy:"
								+ newCcyCode, e);
						DefaultLogger.warn("CollateralValuator, stock net:", stock.getNetValue() + " new Ccy:"
								+ newCcyCode, e);
					}
				}
			}
			Map faos = col.getFixedAssetOthers();
			if ((faos != null) && (faos.size() > 0)) {
				Iterator i = faos.values().iterator();
				while (i.hasNext()) {
					IFixedAssetOthers fao = (IFixedAssetOthers) i.next();
					try {
						totalCMV = sum(fao.getGrossValue(), totalCMV, newCcyCode);
						totalFSV = sum(fao.getNetValue(), totalFSV, newCcyCode);
					}
					catch (Exception e) {
						DefaultLogger.warn("CollateralValuator, fao gross:", fao.getGrossValue() + " new Ccy:"
								+ newCcyCode, e);
						DefaultLogger.warn("CollateralValuator, fao net:",
								fao.getNetValue() + " new Ccy:" + newCcyCode, e);
					}
				}
			}

			IDebtor debtor = col.getDebtor();
			if (debtor != null) {
				try {
					totalCMV = sum(debtor.getGrossValue(), totalCMV, newCcyCode);
					totalFSV = sum(debtor.getNetValue(), totalFSV, newCcyCode);
				}
				catch (Exception e) {
					DefaultLogger.warn("CollateralValuator, debtor gross:", debtor.getGrossValue() + " new Ccy:"
							+ newCcyCode, e);
					DefaultLogger.warn("CollateralValuator, debtor net:", debtor.getNetValue() + " new Ccy:"
							+ newCcyCode, e);
				}
			}

			if ((totalCMV != null) && (col.getBankShare() != ICMSConstant.DOUBLE_INVALID_VALUE)) {
				totalCMV = totalCMV.multiply(new BigDecimal(col.getBankShare())).divide(
						ICMSConstant.BIGDECIMAL_ONE_HUNDRED);
			}

			if (totalFSV != null) {
				if (col.getBankShare() != ICMSConstant.DOUBLE_INVALID_VALUE) {
					totalFSV = totalFSV.multiply(new BigDecimal(col.getBankShare())).divide(
							ICMSConstant.BIGDECIMAL_ONE_HUNDRED);
				}
				BigDecimal margin = getMargin(col);
				if (margin != null) {
					totalFSV = totalFSV.multiply(margin);
				}
			}

			totalCMV = roundAmount(totalCMV);
			totalFSV = roundAmount(totalFSV);
			setCommonColValues(col, totalCMV, totalFSV, newCcyCode, false, null);
			// if (col.getValuation() != null) {
			// col.getValuation().setValuationDate (new Date
			// (System.currentTimeMillis()));
			// }
		}
		catch (Exception e) {
			DefaultLogger.error("setGeneralChargeCMVFSV", "", e);
			throw new CollateralException("Exception caught! " + e.toString());
		}
	}

	/**
	 * Helper method to set cmv for guarantee.
	 * 
	 * @param col of type IGuaranteeCollateral
	 * @throws CollateralException on errors
	 */
	private void setGuaranteeCMVFSV(IGuaranteeCollateral col) throws CollateralException {
		String newCcyCode = getValuationCcy(col);
		Amount totalCMV = null, totalFSV = null;

		if (newCcyCode != null) {
			totalCMV = getConversionAmount(col.getGuaranteeAmount(), newCcyCode);
		}
		totalCMV = roundAmount(totalCMV);
		totalFSV = roundAmount(calcFSV(col, totalCMV, null));

		boolean updateValDate = false;
		if (!(col instanceof IBankSameCurrency) && !(col instanceof ISBLCSameCurrency)) {
			if (isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV)) {
				updateValDate = true;
			}
		}
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, col.getGuaranteeDate());
	}

	/**
	 * Helper method to set cmv for document.
	 * 
	 * @param col of type IDocumentCollateral
	 * @throws CollateralException on errors
	 */
	private void setDocumentCMVFSV(IDocumentCollateral col) throws CollateralException {
		String newCcyCode = getValuationCcy(col);
		Amount totalCMV = null, totalFSV = null;

		if (newCcyCode != null) {
			totalCMV = getConversionAmount(col.getDocumentAmount(), newCcyCode);
		}
		totalCMV = roundAmount(totalCMV);
		totalFSV = roundAmount(calcFSV(col, totalCMV, null));

		boolean updateValDate = false;
		if (isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV)) {
			updateValDate = true;
		}
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
	}

	/**
	 * Get new currency code for guarantee conversion.
	 * 
	 * @param guarantee of type IGuaranteeCollateral
	 * @return new currency code
	 */
	private static String getValuationCcy(IGuaranteeCollateral guarantee) {
		if ((guarantee instanceof IBankSameCurrency) || (guarantee instanceof ISBLCSameCurrency)) {
			return guarantee.getGuaranteeCcyCode();
		}
		else {
			if (guarantee.getValuation() != null) {
				return guarantee.getValuation().getCurrencyCode();
			}
			else {
				return guarantee.getCurrencyCode();
			}
		}
	}

	/**
	 * Get new currency code for document conversion.
	 * 
	 * @param doc of type IDocumentCollateral
	 * @return new currency code
	 */
	private static String getValuationCcy(IDocumentCollateral doc) {
		if (doc.getValuation() != null) {
			return doc.getValuation().getCurrencyCode();
		}
		else {
			return doc.getCurrencyCode();
		}
	}

	/**
	 * Helper method to set cmv for asset post dated cheque.
	 * 
	 * @param col of type IAssetPostDatedCheque
	 * @throws CollateralException
	 */
	private void setAssetPtdChequeCMVFSV(IAssetPostDatedCheque col) throws CollateralException {
		try {
			Amount totalCMV = null, totalFSV = null;
			String newCcyCode = getValuationCurrency(col);

			IPostDatedCheque[] items = col.getPostDatedCheques();
			int count = items == null ? 0 : items.length;

			for (int i = 0; i < count; i++) {
				BigDecimal margin = new BigDecimal(items[i].getMargin());
				if (margin.doubleValue() < 0) {
					margin = getCRP(col);
				}
				Amount cmv = items[i].getChequeAmount();
				cmv.setCurrencyCode(items[i].getChequeCcyCode());
				Amount fsv = calcFSV(col, cmv, margin);
				totalCMV = sum(cmv, totalCMV, newCcyCode);
				totalFSV = sum(fsv, totalFSV, newCcyCode);
			}

			totalCMV = roundAmount(totalCMV);
			totalFSV = roundAmount(totalFSV);
			boolean updateValDate = isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV);
			setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
		}
		catch (Exception e) {
			throw new CollateralException(e.getMessage() + e.toString());
		}
	}

	/**
	 * Helper method to set cmv for Insurance credit default swaps
	 * 
	 * @param col of type IInsuranceCollateral
	 * @throws CollateralException
	 */
	private void setInsuranceCDSCMVFSV(IInsuranceCollateral col) throws CollateralException {
		try {
			Amount totalCMV = null, totalFSV = null;
			String newCcyCode = getValuationCurrency(col);

			ICDSItem[] items = col.getCdsItems();
			int count = items == null ? 0 : items.length;

			for (int i = 0; i < count; i++) {
				BigDecimal margin = new BigDecimal(items[i].getMargin());
				if (margin.doubleValue() < 0) {
					margin = getCRP(col);
				}

				if (items[i].getValuation() != null) {
					Amount cmv = items[i].getValuation().getCMV();
					Amount fsv = null;
					if (cmv != null) {
						fsv = calcFSV(col, cmv, margin);
						items[i].getValuation().setFSV(fsv);
					}
					fsv = items[i].getValuation().getFSV();
					totalCMV = sum(cmv, totalCMV, newCcyCode);
					totalFSV = sum(fsv, totalFSV, newCcyCode);
				}
			}

			totalCMV = roundAmount(totalCMV);
			totalFSV = roundAmount(totalFSV);
			boolean updateValDate = isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV);
			setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
		}
		catch (Exception e) {
			throw new CollateralException(e.getMessage() + e.toString());
		}
	}

	private void setInsuranceNew(IInsuranceCollateral col) throws CollateralException {
		try {
			Amount totalCMV = null, totalFSV = null;
			String newCcyCode = col.getCurrencyCode();
			if (col.getLimitCharges() != null) {
				totalCMV = col.getLimitCharges()[0].getChargeAmount();
				double margin = getCRP(col).doubleValue();
				double fsv = totalCMV.getAmount() * margin;
				totalFSV = new Amount(fsv, totalCMV.getCurrencyCode());
				col.setCMV(totalCMV);
				col.setFSV(totalFSV);
				totalCMV = roundAmount(totalCMV);
				totalFSV = roundAmount(totalFSV);
			}

			boolean updateValDate = isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV);
			setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);

		}
		catch (Exception e) {
			throw new CollateralException(e.getMessage() + e.toString());
		}
	}

	/**
	 * Helper method to refresh bond feed price.
	 */
	private void refreshBondFeedPrice() {
		try {
			IBondFeedProxy proxy = (IBondFeedProxy) BeanHouse.get("bondFeedProxy");
			IBondFeedGroup feed = proxy.getActualBondFeedGroup();
			bondFeed = feed.getFeedEntries();
		}
		catch (Exception e) {
			DefaultLogger.warn("CollateralValuator failed getting feed for bond!", "", e);
		}
	}

	/**
	 * Helper method to refresh stock feed price.
	 * 
	 * @param key stock exchange
	 */
	public IStockFeedEntry[] refreshStockFeedPrice(String key) {
		try {
			IStockFeedProxy proxy = (IStockFeedProxy) BeanHouse.get("stockFeedProxy");
			IStockFeedGroup feed = proxy.getActualStockFeedGroup(key);
			stockMap.put(key, feed.getFeedEntries());
			return feed.getFeedEntries();
		}
		catch (Exception e) {
			DefaultLogger.warn("CollateralValuator failed getting feed for stock!", "", e);
			return null;
		}
	}

	/**
	 * Helper method to refresh stock feed price.
	 * 
	 * @param key country
	 */
	private IUnitTrustFeedEntry[] refreshUnitTrustFeedPrice(String key) {
		try {
			IUnitTrustFeedProxy proxy = (IUnitTrustFeedProxy) BeanHouse.get("unitTrustFeedProxy");
			IUnitTrustFeedGroup feed = proxy.getActualUnitTrustFeedGroup(key);
			unitTrustMap.put(key, feed.getFeedEntries());
			return feed.getFeedEntries();
		}
		catch (Exception e) {
			DefaultLogger.warn("CollateralValuator failed getting feed for unit trust!", "", e);
			return null;
		}
	}

	/**
	 * Helper method to set portfolio item and total cmv.
	 * 
	 * @param item of type IMarketableEquity
	 * @param unitPrice unit price
	 * @param rating bond rating
	 * @param margin margin to calculate fsv
	 * @param totalCMV total cmv
	 * @param newCcyCode currency to be converted to
	 * @throws CollateralException on any errors
	 */
	private Amount setPortfolioItemAndTotalCMV(IMarketableEquity item, Amount unitPrice, String rating,
			BigDecimal margin, Amount totalCMV, String newCcyCode, boolean feedBlackListed, boolean feedSuspended)
			throws CollateralException {
		Amount itemCMV = null, itemFSV = null, totalItemCMV = totalCMV, feedPrice = null;
		String bondRate = null;

		if (!feedBlackListed && !feedSuspended && !item.getIsCollateralBlacklisted()) {
			if (unitPrice != null) {
				itemCMV = getConversionAmount(unitPrice.multiply(new BigDecimal(item.getNoOfUnits())), newCcyCode);
				if ((margin != null) && (itemCMV != null)) {
					itemFSV = getConversionAmount(itemCMV.multiply(margin), newCcyCode);
				}
			}
			totalItemCMV = sum(itemCMV, totalCMV, newCcyCode);
			bondRate = rating;
			// feedPrice = unitPrice;
		}

		// if (!item.getIsCollateralBlacklisted())
		feedPrice = unitPrice;

		item.setBondRating(bondRate);
		item.setUnitPrice(roundAmount(feedPrice, UNIT_PRICE_SCALE));
		item.setUnitPriceCcyCode(feedPrice == null ? null : feedPrice.getCurrencyCode());
		item.setCMV(roundAmount(itemCMV));
		item.setCMVCcyCode(item.getCMV() == null ? null : item.getCMV().getCurrencyCode());
		item.setFSV(roundAmount(itemFSV));
		item.setFSVCcyCode(item.getFSV() == null ? null : item.getFSV().getCurrencyCode());
		return totalItemCMV;
	}

	/**
	 * Helper method to set cmv for marketable collateral.
	 * 
	 * @param col of type IMarketableCollateral
	 * @return collateral
	 * @throws CollateralException on errors encountered
	 */
	public IMarketableCollateral setMarketableCMVFSV(IMarketableCollateral col) throws CollateralException {
		IMarketableEquity[] items = col.getEquityList();

		Amount totalCMV = null, totalFSV = null;
		boolean updateValDate = false, bond = false, equity = false, nonListed = false;

		String newCcyCode = getValuationCurrency(col);

		if (col instanceof INonListedLocal) {
			nonListed = true;
		}
		else if (col instanceof IBondsCommon) {
			bond = true;
		}
		else if (col instanceof IEquityCommon) {
			equity = true;
		}

		int count = items == null ? 0 : items.length;

		if ((newCcyCode != null) && (count != 0)) {
			BigDecimal margin = getMargin(col);

			for (int i = 0; i < count; i++) {
				Amount unitPrice = null;
				boolean blacklisted = false, suspended = false;
				String rating = null;

				if (bond) {
					IBondFeedEntry entry = getBondFeedEntry(items[i].getRIC());
//					if (entry != null) {
//						unitPrice = new Amount(entry.getUnitPrice(), entry.getCurrencyCode());
//						rating = entry.getRating();
//					}
				}
				else if (nonListed) {
					unitPrice = items[i].getUnitPrice();
				}
				else if (equity && (items[i].getEquityType() != null)
						&& items[i].getEquityType().equals(ICMSConstant.EQUITY_TYPE_UNIT_TRUST)) {
					IUnitTrustFeedEntry entry = getUnitTrustFeedEntry(items[i].getRIC(), items[i]
							.getStockExchangeCountry());
					if (entry != null) {
						unitPrice = new Amount(entry.getUnitPrice(), entry.getCurrencyCode());
					}
				}
				else {
					String exchange = items[i].getStockExchange();
					IStockFeedEntry entry = getStockFeedEntry(items[i].getRIC(), exchange);
					;
					if (entry != null) {
						blacklisted = (entry.getBlackListed() != null)
								&& entry.getBlackListed().equals(ICMSConstant.TRUE_VALUE);
						suspended = (entry.getSuspended() != null)
								&& entry.getSuspended().equals(ICMSConstant.TRUE_VALUE);
						unitPrice = new Amount(entry.getUnitPrice(), entry.getCurrencyCode());
					}
				}

				// check if the feed price changed
				Amount oldPrice = cloneAmt(items[i].getUnitPrice());
				String oldRate = items[i].getBondRating();

				totalCMV = setPortfolioItemAndTotalCMV(items[i], unitPrice, rating, margin, totalCMV, newCcyCode,
						blacklisted, suspended);
				if (!nonListed && !updateValDate) {
					if (isAmtChanged(oldPrice, items[i].getUnitPrice())
							|| isStrChanged(oldRate, items[i].getBondRating())) {
						updateValDate = true;
					}
				}
			}
			if ((margin != null) && (totalCMV != null)) {
				totalFSV = calcFSV(col, totalCMV, margin);
			}
		}
		totalFSV = roundAmount(totalFSV);
		totalCMV = roundAmount(totalCMV);

		if (!nonListed && (isAmtChanged(col.getCMV(), totalCMV) || isAmtChanged(col.getFSV(), totalFSV))) {
			updateValDate = true;
		}
		setCommonColValues(col, totalCMV, totalFSV, newCcyCode, updateValDate, null);
		return col;
	}

	/**
	 * Clone amount object.
	 * 
	 * @param amt of type Amount
	 * @return Amount
	 */
	public Amount cloneAmt(Amount amt) {
		return amt == null ? null : new Amount(amt.getAmountAsBigDecimal(), new CurrencyCode(amt.getCurrencyCode()));
	}

	/**
	 * Helper method to check if amount has been changed.
	 * 
	 * @param oldAmt old amount value
	 * @param newAmt new amount value
	 * @return boolean
	 */
	public boolean isAmtChanged(Amount oldAmt, Amount newAmt) {
		if ((oldAmt == null) && (newAmt == null)) {
			return false;
		}

		if ((oldAmt != null) && (newAmt != null) && oldAmt.equals(newAmt)) {
			return false;
		}

		return true;
	}

	/**
	 * Helper method to check if a String has been changed.
	 * 
	 * @param oldStr of type String
	 * @param newStr of type String
	 * @return boolean
	 */
	public boolean isStrChanged(String oldStr, String newStr) {
		if ((oldStr == null) && (newStr == null)) {
			return false;
		}

		if ((oldStr != null) && (newStr != null) && oldStr.equals(newStr)) {
			return false;
		}

		return true;
	}

	/**
	 * Convert the amt using the new currency code.
	 * 
	 * @param amt of type Amount
	 * @param newCcyCode new currency code for exchange
	 * @return new amount object
	 * @throws CollateralException on conversion error
	 */
	public Amount getConversionAmount(Amount amt, String newCcyCode) throws CollateralException {
		try {
			if (!isValidAmt(amt)) {
				return null;
			}
			if (newCcyCode == null) {
				return new Amount(amt.getAmountAsBigDecimal(), new CurrencyCode(amt.getCurrencyCode()));
			}
			if (!amt.getCurrencyCode().equals(newCcyCode)) {
				if (forexMgr == null) {
					forexMgr = getSBForexManager();
				}
				CurrencyCode newCurrency = new CurrencyCode(newCcyCode);
				Amount newAmt = forexMgr.convert(amt, newCurrency);
				return newAmt;
			}
			else {
				return new Amount(amt.getAmountAsBigDecimal(), new CurrencyCode(amt.getCurrencyCode()));
			}
		}
		catch (Exception e) {
			DefaultLogger.error(LOGOBJ, "", e);
			CollateralException ex = new CollateralException("failed to convert amount [" + amt + "] to currency ["
					+ newCcyCode + "]", e);
			ex.setErrorCode(CollateralException.FX_ERR_CODE);
			throw ex;
		}
	}

	/**
	 * Method to check if valuation needs to be valuated.
	 * 
	 * @param oldVal of type IValuation
	 * @param newVal of type IValuation
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isToBeRevaluated(IValuation oldVal, IValuation newVal) throws Exception {
		if ((oldVal == null) && (newVal == null)) {
			return false;
		}
		if ((oldVal == null) && (newVal != null)) {
			return true;
		}
		if (oldVal.equals(newVal)) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method to sum up amt1 and amt2 using new currency code.
	 * 
	 * @param amt1 of type Amount
	 * @param amt2 of type Amount
	 * @param newCcyCode new currency code
	 * @return total amount of amt1 and amt2 after conversion to newCcyCode
	 * @throws CollateralException on error summing up the amount
	 */
	private Amount sum(Amount amt1, Amount amt2, String newCcyCode) throws CollateralException {
		Amount num1 = getConversionAmount(amt1, newCcyCode);
		Amount num2 = getConversionAmount(amt2, newCcyCode);

		if ((num1 == null) && (num2 == null)) {
			return null;
		}
		if (num1 == null) {
			return num2;
		}
		if (num2 == null) {
			return num1;
		}
		try {
			return num1.add(num2);
		}
		catch (Exception e) {
			DefaultLogger.error(LOGOBJ, "", e);
			throw new CollateralException(e.toString());
		}
	}

	/**
	 * Helper method to get feed price for bond given the RIC code.
	 * 
	 * @param ric reuters identification code
	 * @return bond feed entry
	 */
	private IBondFeedEntry getBondFeedEntry(String ric) {
		if (ric == null) {
			return null;
		}

		if (bondFeed == null) {
			refreshBondFeedPrice();
		}
		int count = bondFeed == null ? 0 : bondFeed.length;
//		for (int i = 0; i < count; i++) {
//			if (ric.equals(bondFeed[i].getRic())) {
//				return bondFeed[i];
//			}
//		}
		return null;
	}

	/**
	 * Helper method to get feed price for unit trust given the RIC code.
	 * 
	 * @param ric reuters identification code
	 * @param ctry stock exchange country
	 * @return unit trust feed entry
	 */
	private IUnitTrustFeedEntry getUnitTrustFeedEntry(String ric, String ctry) {
		if ((ric == null) || (ctry == null)) {
			return null;
		}

		IUnitTrustFeedEntry[] entries = null;
		if (!unitTrustMap.containsKey(ctry)) {
			entries = refreshUnitTrustFeedPrice(ctry);
		}
		else {
			entries = (IUnitTrustFeedEntry[]) unitTrustMap.get(ctry);
		}
		int count = entries == null ? 0 : entries.length;
		for (int i = 0; i < count; i++) {
			if (ric.equals(entries[i].getRic())) {
				return entries[i];
			}
		}
		return null;
	}

	/**
	 * Helper method to get the feed price of stock given the RIC code.
	 * 
	 * @param ric reuters identification code
	 * @param exchange stock exchange
	 * @return stock feed entry
	 */
	public IStockFeedEntry getStockFeedEntry(String ric, String exchange) {
		if ((ric == null) || (exchange == null) || (ric.equals("")) || (exchange.equals(""))) {
			return null;
		}

		IStockFeedEntry[] entries = null;
		if (!stockMap.containsKey(exchange)) {
			entries = refreshStockFeedPrice(exchange);
		}
		else {
			entries = (IStockFeedEntry[]) stockMap.get(exchange);
		}
		int count = entries == null ? 0 : entries.length;
		for (int i = 0; i < count; i++) {
			if (ric.equals(entries[i].getRic())) {
				return entries[i];
			}
		}
		return null;
	}

	/**
	 * Helper method to get collateral parameter by subtype code and country.
	 * 
	 * @param col of type ICollateral
	 * @return ICollateralParameter
	 */
	private ICollateralParameter getSecurityParameter(ICollateral col) {
		try {
			String ctry = col.getCollateralLocation();
			String subtype = col.getCollateralSubType().getSubTypeCode();
			String key = ctry + subtype;

			ICollateralParameter param = null;
			if (!crpMap.containsKey(key)) {
				ICollateralProxy proxy = CollateralProxyFactory.getProxy();
				param = proxy.getCollateralParameter(ctry, subtype);
				crpMap.put(key, param);
			}
			else {
				param = (ICollateralParameter) crpMap.get(key);
			}
			return param;
		}
		catch (Exception e) {
			DefaultLogger.warn(LOGOBJ, "", e);
			return null;
		}
	}

	/**
	 * Helper method to get CRP value given the collateral. It is based on
	 * security location and security subtype code.
	 * 
	 * @param col of type ICollateral
	 * @return CRP value
	 */
	private BigDecimal getCRP(ICollateral col) {
		ICollateralParameter param = getSecurityParameter(col);
		if (param != null) {
			BigDecimal crp = new BigDecimal(param.getThresholdPercent());
			return crp.divide(ICMSConstant.BIGDECIMAL_ONE_HUNDRED, ICMSConstant.PERCENTAGE_SCALE,
					BigDecimal.ROUND_UNNECESSARY);
		}
		return null;
	}

	/**
	 * Method to get next revaluation date given a collateral, latest valuation
	 * date, and security parameter.
	 * 
	 * @param col of type ICollateral
	 * @param latestValDate latest valuation date
	 * @param srp security recovery parameter
	 * @return Date
	 */
	private Date getNextRevaluationDate(ICollateral col, Date latestValDate, ICollateralParameter srp) {
		if (srp == null) {
			srp = getSecurityParameter(col);
		}

		if (srp == null) {
			return null;
		}

		Date nextVal = null;

		if (latestValDate != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(latestValDate);

			String timeFreq = null;
			int timeFreqNum = 0;
			if (col instanceof IDocumentCollateral) {
				IValuation val = col.getValuation();
				if (val != null) {
					timeFreq = val.getNonRevaluationFreqUnit();
					timeFreqNum = val.getNonRevaluationFreq();
				}
				if ((timeFreqNum == ICMSConstant.INT_INVALID_VALUE) || (timeFreq == null)
						|| (timeFreq.trim().length() == 0)) {
					timeFreq = srp.getValuationFrequencyUnit();
					timeFreqNum = srp.getValuationFrequency();
				}
			}
			else {
				timeFreq = srp.getValuationFrequencyUnit();
				timeFreqNum = srp.getValuationFrequency();
			}

			if (timeFreq != null) {
				if (timeFreq.equals(ICMSConstant.TIME_FREQ_YEAR)) {
					cal.add(Calendar.YEAR, timeFreqNum);
					nextVal = cal.getTime();
				}
				else if (timeFreq.equals(ICMSConstant.TIME_FREQ_MONTH)) {
					cal.add(Calendar.MONTH, timeFreqNum);
					nextVal = cal.getTime();
				}
				else if (timeFreq.equals(ICMSConstant.TIME_FREQ_WEEK)) {
					cal.add(Calendar.WEEK_OF_MONTH, timeFreqNum);
					nextVal = cal.getTime();
				}
				else if (timeFreq.equals(ICMSConstant.TIME_FREQ_DAY)) {
					cal.add(Calendar.DAY_OF_MONTH, timeFreqNum);
					nextVal = cal.getTime();
				}
			}
		}
		if (nextVal == null) {
			nextVal = new Date(System.currentTimeMillis());
		}
		return nextVal;
	}

	/**
	 * Helper method to check if the collateral needs to be revaluated. This
	 * method is used by collateral of type IAssetPostDatedCheque.
	 * 
	 * @param collateral of type ICollateral
	 * @return true if it needs revaluation, otherwise false
	 */
	public ICollateral getAssetPDCNeedsRevaluation(ICollateral collateral) {
		IAssetPostDatedCheque asset = (IAssetPostDatedCheque) collateral;
		IPostDatedCheque[] cheques = asset.getPostDatedCheques();
		if ((cheques == null) || (cheques.length == 0)) {
			return null;
		}

		ICollateralParameter srp = getSecurityParameter(collateral);

		if (srp == null) {
			return null;
		}

		ArrayList chequesList = new ArrayList();

		for (int i = 0; i < cheques.length; i++) {
			Date nextVal = getNextRevaluationDate(asset, cheques[i].getValuationDate(), srp);

			if (nextVal == null) {
				continue;
			}

			nextVal = getDateWithoutTime(nextVal);

			Date currDate = getDateWithoutTime(new Date(System.currentTimeMillis()));

			if (nextVal.equals(currDate)) {
				chequesList.add(cheques[i]);
			}
		}
		IPostDatedCheque[] pdc = (IPostDatedCheque[]) chequesList.toArray(new OBPostDatedCheque[0]);
		asset.setPostDatedCheques(pdc);
		return asset;
	}

	/**
	 * Helper method to check if the collateral needs to be revaluated. This
	 * method must not be used by collateral of type IAssetPostDatedCheque
	 * 
	 * @param collateral of type ICollateral
	 * @return true if it needs revaluation, otherwise false
	 */
	public boolean isRevaluationRequired(ICollateral collateral) {
		if ((collateral instanceof IInsuranceCollateral) || (collateral instanceof ICommodityCollateral)) {
			return true;
		}

		IValuation val = collateral.getValuation();

		if (val == null) {
			return false;
		}

		Date nextVal = getNextRevaluationDate(collateral, val.getValuationDate(), null);

		collateral.getValuation().setNextRevaluationDate(nextVal);
		if (nextVal == null) {
			return false;
		}

		nextVal = getDateWithoutTime(nextVal);

		Date currDate = getDateWithoutTime(new Date(System.currentTimeMillis()));

		if (nextVal.compareTo(currDate) <= 0) { // minus is to cater for last
			// failed valuation.
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get date without timestamp.
	 * 
	 * @param date a date with timestamp
	 * @return date without timestamp
	 */
	private Date getDateWithoutTime(Date date) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(date);
		g.set(Calendar.HOUR_OF_DAY, 0);
		g.set(Calendar.MINUTE, 0);
		g.set(Calendar.SECOND, 0);
		g.set(Calendar.MILLISECOND, 0);
		return g.getTime();
	}

	/**
	 * Helper method to call valuation transaction to do collateral revaluation.
	 * 
	 * @param colTrx of type ICollateralTrxValue
	 * @deprecated consider to use
	 *             com.integrosys.cms.app.collateral.bus.valuation for various
	 *             kind of valuation mechanism.
	 */
	public void revaluateCollateral(ICollateralTrxValue colTrx) throws Exception {
		DefaultLogger.info(this, "********************** Revaluating for collateral ID:" + colTrx.getReferenceID());

		IValuationTrxValue valTrx = getCollateralValuationProxy().getValuationTrxValueByTrxRefID(new OBTrxContext(),
				colTrx.getReferenceID());

		if (valTrx.getValuation() == null) {
			valTrx.setValuation(colTrx.getCollateral().getValuation());
			getCollateralValuationProxy().systemCreateRevaluation(new OBTrxContext(), valTrx);
		}
		else {
			getCollateralValuationProxy().systemUpdateRevaluation(new OBTrxContext(), valTrx,
					colTrx.getCollateral().getValuation());
		}
	}

	/**
	 * Helper method to return the forex session bean
	 * 
	 * @return SBForexManager the remote handler for the forex manager session
	 *         bean
	 * @throws Exception for any errors encountered
	 */
	private static SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());

		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}
}
