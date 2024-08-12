/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/EBGeneralChargeBean.java,v 1.20 2005/08/12 08:13:38 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.CreateException;
import javax.ejb.EJBException;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.EBCollateralDetailBean;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.IInsurancePolicy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.batch.common.BatchResourceFactory;

/**
 * Entity bean implementation for Asset of type General Charge.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2005/08/12 08:13:38 $ Tag: $Name: $
 */
public abstract class EBGeneralChargeBean extends EBCollateralDetailBean implements IGeneralCharge {
	/**
	 * Get collateral ID.
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return getEBCollateralID().longValue();
	}

	/**
	 * set collateral ID.
	 * 
	 * @param collateralID is of type long
	 */
	public void setCollateralID(long collateralID) {
		setEBCollateralID(new Long(collateralID));
	}

	/**
	 * Get valuation CMV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorCMV() {
		return null;
	}

	/**
	 * Set valuation CMV - debtors.
	 * 
	 * @param debtorCMV of type Amount
	 */
	public void setDebtorCMV(Amount debtorCMV) {
	}

	/**
	 * Get valuation FSV - debtors.
	 * 
	 * @return Amount
	 */
	public Amount getDebtorFSV() {
		return null;
	}

	/**
	 * Set valuation FSV - debtors.
	 * 
	 * @param debtorFSV of type Amount
	 */
	public void setDebtorFSV(Amount debtorFSV) {
	}

	/**
	 * Get margin - debtors.
	 * 
	 * @return double
	 */
	public double getDebtorMargin() {
		if (getEBDebtorMargin() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBDebtorMargin().doubleValue();
		}
	}

	/**
	 * Set margin - debtors.
	 * 
	 * @param debtorMargin of type double
	 */
	public void setDebtorMargin(double debtorMargin) {
		if (debtorMargin == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBDebtorMargin(null);
		}
		else {
			setEBDebtorMargin(new Double(debtorMargin));
		}
	}

	/**
	 * Get a debtor of this asset.
	 * 
	 * @return IDebtor
	 */
	public IDebtor getDebtor() {
		Iterator i = getDebtorCMR().iterator();
		IDebtor debtor = null;
		if (i.hasNext()) {
			EBDebtorLocal theEjb = (EBDebtorLocal) i.next();
			debtor = theEjb.getValue();
		}
		return debtor;
	}

	/**
	 * set a debtor as an asset.
	 * 
	 * @param debtor of type IDebtor
	 */
	public void setDebtor(IDebtor debtor) {
	}

	/**
	 * Get valuation CMV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockCMV() {
		return null;
	}

	/**
	 * set valuation CMV - stocks.
	 * 
	 * @param stockCMV of type Amount
	 */
	public void setStockCMV(Amount stockCMV) {
	}

	/**
	 * Get valuation FSV - stocks.
	 * 
	 * @return Amount
	 */
	public Amount getStockFSV() {
		return null;
	}

	/**
	 * Set valuation FSV - stocks.
	 * 
	 * @param stockFSV of type Amount
	 */
	public void setStockFSV(Amount stockFSV) {
	}

	/**
	 * Get margin - stocks.
	 * 
	 * @return double
	 */
	public double getStockMargin() {
		if (getEBStockMargin() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBStockMargin().doubleValue();
		}
	}

	/**
	 * Set margin - stocks.
	 * 
	 * @param stockMargin of type double
	 */
	public void setStockMargin(double stockMargin) {
		if (stockMargin == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBStockMargin(null);
		}
		else {
			setEBStockMargin(new Double(stockMargin));
		}
	}

	/**
	 * Get a list of stock info of this asset.
	 * 
	 * @return Map (stockID, IStock)
	 */
	public Map getStocks() {
		Iterator i = getStockCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBStockLocal theEjb = (EBStockLocal) i.next();
			IStock stock = theEjb.getValue();
			if ((stock.getStatus() == null)
					|| ((stock.getStatus() != null) && !stock.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				map.put(stock.getStockID(), stock);
			}
		}
		return map;
	}

	/**
	 * set a list of insurance info as an asset.
	 * 
	 * @param stockMap of type Map
	 */
	public void setStocks(Map stockMap) {
	}

	/**
	 * Get valuation CMV - FAO.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersCMV() {
		return null;
	}

	/**
	 * set valuation CMV - FAO.
	 * 
	 * @param faoCMV of type Amount
	 */
	public void setFixedAssetOthersCMV(Amount faoCMV) {
	}

	/**
	 * Get valuation FSV - FAO.
	 * 
	 * @return Amount
	 */
	public Amount getFixedAssetOthersFSV() {
		return null;
	}

	/**
	 * Set valuation FSV - FAO.
	 * 
	 * @param faoFSV of type Amount
	 */
	public void setFixedAssetOthersFSV(Amount faoFSV) {
	}

	/**
	 * Get margin - FAO.
	 * 
	 * @return double
	 */
	public double getFixedAssetOthersMargin() {
		if (getEBFaoMargin() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBFaoMargin().doubleValue();
		}
	}

	/**
	 * Set margin - FAO.
	 * 
	 * @param faoMargin of type double
	 */
	public void setFixedAssetOthersMargin(double faoMargin) {
		if (faoMargin == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBFaoMargin(null);
		}
		else {
			setEBFaoMargin(new Double(faoMargin));
		}
	}

	/**
	 * Get a list of fao info of this asset.
	 * 
	 * @return Map (faoID, IFixedAssetOthers)
	 */
	public Map getFixedAssetOthers() {
		Iterator i = getFaoCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBFixedAssetOthersLocal theEjb = (EBFixedAssetOthersLocal) i.next();
			IFixedAssetOthers fao = theEjb.getValue();
			if ((fao.getStatus() == null)
					|| ((fao.getStatus() != null) && !fao.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				map.put(fao.getFAOID(), fao);
			}
		}
		return map;
	}

	/**
	 * set a list of fao as an asset.
	 * 
	 * @param faoMap of type Map
	 */
	public void setFixedAssetOthers(Map faoMap) {
	}

	/**
	 * Get a list of insurance stock map of this asset.
	 * 
	 * @return Map (insuranceID, IGenChargeMapEntry)
	 */
	public Map get_Insurance_Stock_Map() {
		Iterator i = getInsuranceStockMapCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) i.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() == null)
					|| ((mapEntry.getStatus() != null) && !mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				String keyID = String.valueOf(mapEntry.getInsuranceID());
				ArrayList stockList = (ArrayList) map.get(keyID);
				if (stockList == null) {
					stockList = new ArrayList();
				}
				stockList.add(mapEntry);
				map.put(keyID, stockList);
			}
		}
		return map;
	}

	/**
	 * set a list of insurance Stock map as an asset.
	 * 
	 * @param insuranceStockMap of type Map
	 */
	public void set_Insurance_Stock_Map(Map insuranceStockMap) {
	}

	/**
	 * Get a list of insurance fao map of this asset.
	 * 
	 * @return Map (insuranceID, IGenChargeMapEntry)
	 */
	public Map get_Insurance_FixedAssetOthers_Map() {
		Iterator i = getInsuranceFaoMapCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) i.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() == null)
					|| ((mapEntry.getStatus() != null) && !mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				String keyID = String.valueOf(mapEntry.getInsuranceID());
				ArrayList faoList = (ArrayList) map.get(keyID);
				if (faoList == null) {
					faoList = new ArrayList();
				}
				// populateSecurityCCY(mapEntry);
				faoList.add(mapEntry);
				map.put(keyID, faoList);
			}
		}
		return map;
	}

	// weiling : insr coverage amt to be phased out. replaced by recoverable amt
	// in stock.
	/**
	 * Helper method to populate security ccy into map entry.
	 * 
	 * @param mapEntry
	 */
	/*
	 * private void populateSecurityCCY(IGenChargeMapEntry mapEntry) { if
	 * (mapEntry != null && mapEntry.getInsrCoverageAmount() != null) {
	 * mapEntry.getInsrCoverageAmount().setCurrencyCode(new
	 * CurrencyCode(currencyCode)); } }
	 */

	/**
	 * set a list of insurance Fao map as an asset.
	 * 
	 * @param insuranceFaoMap of type Map
	 */
	public void set_Insurance_FixedAssetOthers_Map(Map insuranceFaoMap) {
	}

	/**
	 * Get a list of stock insurance map of this asset.
	 * 
	 * @return Map (stockID, IGenChargeMapEntry)
	 */
	public Map get_Stock_Insurance_Map() {
		Iterator i = getInsuranceStockMapCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) i.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() == null)
					|| ((mapEntry.getStatus() != null) && !mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				String keyID = String.valueOf(mapEntry.getEntryValueID());
				ArrayList insuranceList = (ArrayList) map.get(keyID);
				if (insuranceList == null) {
					insuranceList = new ArrayList();
				}
				// weiling : insr coverage amt to be phased out. replaced by
				// recoverable amt in stock.
				// populateSecurityCCY(mapEntry);
				insuranceList.add(mapEntry);
				map.put(keyID, insuranceList);
			}
		}
		return map;
	}

	/**
	 * set a list of stock insurance map as an asset.
	 * 
	 * @param stockInsuranceMap of type Map
	 */
	public void set_Stock_Insurance_Map(Map stockInsuranceMap) {
	}

	/**
	 * Get a list of fao insurance map of this asset.
	 * 
	 * @return Map (faoID, IGenChargeMapEntry)
	 */
	public Map get_FixedAssetOthers_Insurance_Map() {
		Iterator i = getInsuranceFaoMapCMR().iterator();
		HashMap map = new HashMap();

		while (i.hasNext()) {
			EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) i.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() == null)
					|| ((mapEntry.getStatus() != null) && !mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED))) {
				String keyID = String.valueOf(mapEntry.getEntryValueID());
				ArrayList insuranceList = (ArrayList) map.get(keyID);
				if (insuranceList == null) {
					insuranceList = new ArrayList();
				}
				insuranceList.add(mapEntry);
				map.put(keyID, insuranceList);
			}
		}
		return map;
	}

	/**
	 * set a list of fao insurance map as an asset.
	 * 
	 * @param faoInsuranceMap of type Map
	 */
	public void set_FixedAssetOthers_Insurance_Map(Map faoInsuranceMap) {
	}

	/**
	 * Get Bank's participating share
	 * 
	 * @return double
	 */
	public double getBankShare() {
		if (getEBBankShare() == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return getEBBankShare().doubleValue();
		}
	}

	/**
	 * Set Bank's participating share
	 * 
	 * @param bankShare of type double
	 */
	public void setBankShare(double bankShare) {
		if (bankShare == ICMSConstant.DOUBLE_INVALID_VALUE) {
			setEBBankShare(null);
		}
		else {
			setEBBankShare(new Double(bankShare));
		}
	}

	/**
	 * Get last Used Stock Index.
	 * 
	 * @return int
	 */
	public int getLastUsedStockIndex() {
		if (getEBLastUsedStockIndex() != null) {
			return getEBLastUsedStockIndex().intValue();
		}
		return 0;
	}

	/**
	 * set last Used Stock Index.
	 * 
	 * @param lastUsedStockIndex is of type int
	 */
	public void setLastUsedStockIndex(int lastUsedStockIndex) {
		setEBLastUsedStockIndex(new Integer(lastUsedStockIndex));
	}

	/**
	 * Get last Used FAO Index.
	 * 
	 * @return int
	 */
	public int getLastUsedFixedAssetOthersIndex() {
		if (getEBLastUsedFaoIndex() != null) {
			return getEBLastUsedFaoIndex().intValue();
		}
		return 0;
	}

	/**
	 * set last Used Fao Index.
	 * 
	 * @param lastUsedFaoIndex is of type int
	 */
	public void setLastUsedFixedAssetOthersIndex(int lastUsedFaoIndex) {
		setEBLastUsedFaoIndex(new Integer(lastUsedFaoIndex));
	}

	/**
	 * Get last index used to generated id - Insurance.
	 * 
	 * @return int
	 */
	public int getLastUsedInsuranceIndex() {
		if (getEBLastUsedInsuranceIndex() != null) {
			return getEBLastUsedInsuranceIndex().intValue();
		}
		return 0;
	}

	/**
	 * Set last index used to generated id - Insurance.
	 * 
	 * @param lastUsedInsuranceIndex of type int
	 */
	public void setLastUsedInsuranceIndex(int lastUsedInsuranceIndex) {
		setEBLastUsedInsuranceIndex(new Integer(lastUsedInsuranceIndex));
	}

	/**
	 * Get Stock Insurance Grace Period
	 * 
	 * @return int
	 */
	public int getStockInsrGracePeriod() {
		if (getEBStockInsrGracePeriod() != null) {
			return getEBStockInsrGracePeriod().intValue();
		}
		return ICMSConstant.INT_INVALID_VALUE;
	}

	/**
	 * Set Stock Insurance Grace Period
	 * 
	 * @param stockInsrGracePeriod of type int
	 */
	public void setStockInsrGracePeriod(int stockInsrGracePeriod) {
		setEBStockInsrGracePeriod((stockInsrGracePeriod == ICMSConstant.INT_INVALID_VALUE) ? null : new Integer(
				stockInsrGracePeriod));
	}

	/**
	 * Get Stock Insurance Shortfall Amount
	 * 
	 * @return Amount
	 */
	public Amount getStockInsrShortfallAmount() {
		return ((getEBCurrentStockInsrShortfallAmount() == null) || (currencyCode == null)) ? null : new Amount(
				getEBCurrentStockInsrShortfallAmount(), new CurrencyCode(currencyCode));
	}

	/**
	 * Set Stock Insurance Shortfall Amount
	 * 
	 * @param shortfallAmt of type Amount
	 */
	public void setStockInsrShortfallAmount(Amount shortfallAmt) {
		// set existing value to prev before setting current value
		BigDecimal prevShortfallAmt = getEBCurrentStockInsrShortfallAmount();
		setEBPreviousStockInsrShortfallAmount((prevShortfallAmt == null) ? null : prevShortfallAmt);
		setEBCurrentStockInsrShortfallAmount((shortfallAmt == null) ? null : shortfallAmt.getAmountAsBigDecimal());
	}

	/**
	 * Get drawing power gross amount without taking into consideration
	 * insurance coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerGrossAmount() {
		return ((getEBDrawingPowerGrossAmount() == null) || (currencyCode == null)) ? null : new Amount(
				getEBDrawingPowerGrossAmount(), new CurrencyCode(currencyCode));
	}

	/**
	 * Set drawing power gross amount.
	 * 
	 * @param dpGrossAmt - Amount
	 */
	public void setDrawingPowerGrossAmount(Amount dpGrossAmt) {
		if ((dpGrossAmt != null) && (dpGrossAmt.getAmountAsBigDecimal() != null)
				&& (dpGrossAmt.getCurrencyCode() != null)) {
			boolean isForexError = GeneralChargeUtil.isForexErrorAmount(dpGrossAmt);
			setEBDrawingPowerGrossAmount((isForexError) ? null : dpGrossAmt.getAmountAsBigDecimal());
		}
	}

	/**
	 * Get drawing power gross amount taking into consideration insurance
	 * coverage.
	 * 
	 * @ return Amount
	 */
	public Amount getDrawingPowerLessInsrGrossAmount() {
		return ((getEBDrawingPowerLessInsrGrossAmount() == null) || (currencyCode == null)) ? null : new Amount(
				getEBDrawingPowerLessInsrGrossAmount(), new CurrencyCode(currencyCode));
	}

	/**
	 * Set drawing power gross amount taking into consideration insurance
	 * coverage
	 * 
	 * @param dpLessInsrGrossAmt - Amount
	 */
	public void setDrawingPowerLessInsrGrossAmount(Amount dpLessInsrGrossAmt) {
		if ((dpLessInsrGrossAmt != null) && (dpLessInsrGrossAmt.getAmountAsBigDecimal() != null)
				&& (dpLessInsrGrossAmt.getCurrencyCode() != null)) {
			boolean isForexError = GeneralChargeUtil.isForexErrorAmount(dpLessInsrGrossAmt);
			setEBDrawingPowerLessInsrGrossAmount((isForexError) ? null : dpLessInsrGrossAmt.getAmountAsBigDecimal());
		}
	}

	/**
	 * Get FAO Insurance Grace Period
	 * 
	 * @return int
	 */
	public int getFaoInsrGracePeriod() {
		if (getEBFaoInsrGracePeriod() != null) {
			return getEBFaoInsrGracePeriod().intValue();
		}
		return ICMSConstant.INT_INVALID_VALUE;
	}

	/**
	 * Set FAO Insurance Grace Period
	 * 
	 * @param faoInsrGracePeriod of type int
	 */
	public void setFaoInsrGracePeriod(int faoInsrGracePeriod) {
		setEBFaoInsrGracePeriod((faoInsrGracePeriod == ICMSConstant.INT_INVALID_VALUE) ? null : new Integer(
				faoInsrGracePeriod));
	}

	public String generateNewID(int type) {
		return null;
	}

	public List getStockIDList(String insuranceID) {
		return null;
	}

	public List getInsuranceIDList(String id, int type) {
		return null;
	}

	public List getFixedAssetOthersIDList(String insuranceID) {
		return null;
	}

	public Date getInsuranceGracePeriodEndDate(IInsurancePolicy insr) {
		return null;
	}

	public abstract Long getEBCollateralID();

	public abstract void setEBCollateralID(Long eBCollateralID);

	public abstract Integer getEBStockInsrGracePeriod();

	public abstract void setEBStockInsrGracePeriod(Integer ebStockInsrGracePeriod);

	public abstract Integer getEBFaoInsrGracePeriod();

	public abstract void setEBFaoInsrGracePeriod(Integer ebFaoInsrGracePeriod);

	public abstract Double getEBDebtorMargin();

	public abstract void setEBDebtorMargin(Double eBDebtorMargin);

	public abstract Collection getDebtorCMR();

	public abstract void setDebtorCMR(Collection debtors);

	public abstract Double getEBStockMargin();

	public abstract void setEBStockMargin(Double eBStockMargin);

	public abstract Collection getStockCMR();

	public abstract void setStockCMR(Collection stocks);

	public abstract Double getEBFaoMargin();

	public abstract void setEBFaoMargin(Double eBFaokMargin);

	public abstract Double getEBBankShare();

	public abstract void setEBBankShare(Double eBBankShare);

	public abstract Collection getFaoCMR();

	public abstract void setFaoCMR(Collection faos);

	public abstract Collection getInsuranceStockMapCMR();

	public abstract void setInsuranceStockMapCMR(Collection insuranceStockMap);

	public abstract Collection getInsuranceFaoMapCMR();

	public abstract void setInsuranceFaoMapCMR(Collection insuranceFaoMap);

	public abstract Integer getEBLastUsedStockIndex();

	public abstract void setEBLastUsedStockIndex(Integer eBLastUsedStockIndex);

	public abstract Integer getEBLastUsedFaoIndex();

	public abstract void setEBLastUsedFaoIndex(Integer eBLastUsedFaoIndex);

	public abstract Integer getEBLastUsedInsuranceIndex();

	public abstract void setEBLastUsedInsuranceIndex(Integer eBLastUsedInsuranceIndex);

	public abstract BigDecimal getEBPreviousStockInsrShortfallAmount();

	public abstract void setEBPreviousStockInsrShortfallAmount(BigDecimal prevShortfallAmt);

	public abstract BigDecimal getEBCurrentStockInsrShortfallAmount();

	public abstract void setEBCurrentStockInsrShortfallAmount(BigDecimal currShortfallAmt);

	public abstract BigDecimal getEBDrawingPowerGrossAmount();

	public abstract void setEBDrawingPowerGrossAmount(BigDecimal dpGrossAmt);

	public abstract BigDecimal getEBDrawingPowerLessInsrGrossAmount();

	public abstract void setEBDrawingPowerLessInsrGrossAmount(BigDecimal dpLessInsrGrossAmt);

	/**
	 * Set the general charge asset collateral.
	 * 
	 * @param collateral is of type ICollateral
	 */
	public void setValue(ICollateral collateral) {
		AccessorUtil.copyValue(collateral, this, super.EXCLUDE_METHOD);
		setReferences(collateral, false);
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param collateral of type ICollateral
	 * @throws CreateException on error creating references to collateral
	 */
	public void ejbPostCreate(ICollateral collateral) throws CreateException {
		setReferences(collateral, true);
	}

	/**
	 * Get stock local home
	 * 
	 * @return EBStockLocalHome
	 */
	protected EBStockLocalHome getEBStockLocalHome() {
		EBStockLocalHome ejbHome = (EBStockLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_STOCK_LOCAL_JNDI, EBStockLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBStockLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get Debtor local home
	 * 
	 * @return EBDebtorLocalHome
	 */
	protected EBDebtorLocalHome getEBDebtorLocalHome() {
		EBDebtorLocalHome ejbHome = (EBDebtorLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEBTOR_LOCAL_JNDI, EBDebtorLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBDebtorLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get FAO local home
	 * 
	 * @return EBFixedAssetOthersLocalHome
	 */
	protected EBFixedAssetOthersLocalHome getEBFixedAssetOthersLocalHome() {
		EBFixedAssetOthersLocalHome ejbHome = (EBFixedAssetOthersLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_FAO_LOCAL_JNDI, EBFixedAssetOthersLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBFixedAssetOthersLocalHome is Null!");
		}

		return ejbHome;
	}

	
	/**
	 * Get insurance stock map local home
	 * 
	 * @return EBInsuranceStockMapLocalHome
	 */
	protected EBInsuranceStockMapLocalHome getEBInsuranceStockMapLocalHome() {
		EBInsuranceStockMapLocalHome ejbHome = (EBInsuranceStockMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INS_STOCK_MAP_LOCAL_JNDI, EBInsuranceStockMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBInsuranceStockMapLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Get insurance fao map local home
	 * 
	 * @return EBInsuranceFaoMapLocalHome
	 */
	protected EBInsuranceFaoMapLocalHome getEBInsuranceFaoMapLocalHome() {
		EBInsuranceFaoMapLocalHome ejbHome = (EBInsuranceFaoMapLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_INS_FAO_MAP_LOCAL_JNDI, EBInsuranceFaoMapLocalHome.class.getName());

		if (ejbHome == null) {
			throw new EJBException("EBInsuranceFaoMapLocalHome is Null!");
		}

		return ejbHome;
	}

	/**
	 * Set the references to this general charge asset.
	 * 
	 * @param collateral of type ICollateral
	 * @param isAdd true is to create new references, otherwise false
	 */
	private void setReferences(ICollateral collateral, boolean isAdd) {
		IGeneralCharge genCharge = (IGeneralCharge) collateral;
		try {
			setStockRef(genCharge.getStocks(), isAdd);
			setDebtorRef(genCharge.getDebtor(), isAdd);
			setFaoRef(genCharge.getFixedAssetOthers(), isAdd);
			setInsuranceStockMapRef(genCharge.get_Insurance_Stock_Map(), isAdd);
			setInsuranceFaoMapRef(genCharge.get_Insurance_FixedAssetOthers_Map(), isAdd);
			//Added By Anil
			updateGeneralChargeDetails(genCharge.getGeneralChargeDetails(), genCharge.getCollateralID(),isAdd);
			}
		catch (Exception e) {
			throw new EJBException(e);
		}
	}

	// private methods for stockMap

	/**
	 * set a list of stocks as an asset.
	 * 
	 * @param stockMap of type Map
	 * @throws CreateException on error creating reference to stocks
	 */
	private void setStockRef(Map stockMap, boolean isAdd) throws CreateException, ConcurrentUpdateException {
		if ((stockMap == null) || stockMap.isEmpty()) {
			removeAllStocks();
			return;
		}

		EBStockLocalHome ejbHome = getEBStockLocalHome();

		Collection c = getStockCMR();

		if (isAdd || (c.size() == 0)) {
			if (stockMap != null) {
				Collection valueList = stockMap.values();
				Iterator itr = valueList.iterator();
				while (itr.hasNext()) {
					c.add(ejbHome.create((IStock) itr.next()));
				}
			}
			return;
		}

		removeStock(c, stockMap);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		Collection keyList = stockMap.keySet();
		Iterator itr = keyList.iterator();
		while (itr.hasNext()) {
			boolean found = false;
			IStock stock = (IStock) stockMap.get(itr.next());

			while (iterator.hasNext()) {
				EBStockLocal theEjb = (EBStockLocal) iterator.next();
				IStock value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (stock.getStockID().equals(value.getStockID())) {
					theEjb.setValue(stock);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(stock);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IStock) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the stocks.
	 */
	private void removeAllStocks() {
		Collection c = getStockCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBStockLocal theEjb = (EBStockLocal) iterator.next();
			deleteStock(theEjb);
		}
	}

	/**
	 * Helper method to delete stocks in stockCol that are not contained in
	 * stockMap.
	 * 
	 * @param stockCol a list of old stock
	 * @param stockMap a Map of newly updated stock
	 */
	private void removeStock(Collection stockCol, Map stockMap) {
		Iterator iterator = stockCol.iterator();

		while (iterator.hasNext()) {
			EBStockLocal theEjb = (EBStockLocal) iterator.next();
			IStock stock = theEjb.getValue();
			if ((stock.getStatus() != null) && stock.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			Collection keyList = stockMap.keySet();
			Iterator itr = keyList.iterator();
			while (itr.hasNext()) {
				IStock stockObj = (IStock) stockMap.get(itr.next());
				if (stockObj.getStockID().equals(stock.getStockID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteStock(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a stock.
	 * 
	 * @param theEjb of type EBStockLocal
	 */
	private void deleteStock(EBStockLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	// private methods for debtor

	/**
	 * set a debtor as an asset.
	 * 
	 * @param debtor of type IDebtor
	 * @throws CreateException on error creating reference to debtor
	 */
	private void setDebtorRef(IDebtor debtor, boolean isAdd) throws CreateException, ConcurrentUpdateException {
		EBDebtorLocalHome ejbHome = getEBDebtorLocalHome();

		Collection c = getDebtorCMR();

		if (isAdd || (c.size() == 0)) {
			if (debtor != null) {
				c.add(ejbHome.create(debtor));
			}
			return;
		}

		Iterator iterator = c.iterator();
		if (debtor != null) {
			if (iterator.hasNext()) {
				EBDebtorLocal theEjb = (EBDebtorLocal) iterator.next();
				theEjb.setValue(debtor);
			}
		}
	}

	/**
	 * set a list of fao as an asset.
	 * 
	 * @param faoMap of type Map
	 * @throws CreateException
	 * @throws ConcurrentUpdateException on error creating reference to fao
	 */
	private void setFaoRef(Map faoMap, boolean isAdd) throws CreateException, ConcurrentUpdateException {
		if ((faoMap == null) || faoMap.isEmpty()) {
			removeAllFaos();
			return;
		}

		EBFixedAssetOthersLocalHome ejbHome = getEBFixedAssetOthersLocalHome();

		Collection c = getFaoCMR();

		if (isAdd || (c.size() == 0)) {
			if (faoMap != null) {
				Collection valueList = faoMap.values();
				Iterator itr = valueList.iterator();
				while (itr.hasNext()) {
					c.add(ejbHome.create((IFixedAssetOthers) itr.next()));
				}
			}
			return;
		}

		removeFao(c, faoMap);

		Iterator iterator = c.iterator();
		ArrayList newItems = new ArrayList();

		Collection keyList = faoMap.keySet();
		Iterator itr = keyList.iterator();
		while (itr.hasNext()) {
			boolean found = false;
			IFixedAssetOthers fao = (IFixedAssetOthers) faoMap.get(itr.next());

			while (iterator.hasNext()) {
				EBFixedAssetOthersLocal theEjb = (EBFixedAssetOthersLocal) iterator.next();
				IFixedAssetOthers value = theEjb.getValue();
				if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
					continue;
				}

				if (fao.getFAOID().equals(value.getFAOID())) {
					theEjb.setValue(fao);
					found = true;
					break;
				}
			}
			if (!found) {
				newItems.add(fao);
			}
			iterator = c.iterator();
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IFixedAssetOthers) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the fao.
	 * @throws ConcurrentUpdateException on error update to fao
	 */
	private void removeAllFaos() throws ConcurrentUpdateException {
		Collection c = getFaoCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBFixedAssetOthersLocal theEjb = (EBFixedAssetOthersLocal) iterator.next();
			deleteFao(theEjb);
		}
	}

	/**
	 * Helper method to delete fao in faoCol that are not contained in faoMap.
	 * 
	 * @param faoCol a list of old Insurance Info
	 * @param faoMap a Map of newly updated fao
	 * @throws ConcurrentUpdateException on error update to fao
	 */
	private void removeFao(Collection faoCol, Map faoMap) throws ConcurrentUpdateException {
		Iterator iterator = faoCol.iterator();

		while (iterator.hasNext()) {
			EBFixedAssetOthersLocal theEjb = (EBFixedAssetOthersLocal) iterator.next();
			IFixedAssetOthers fao = theEjb.getValue();
			if ((fao.getStatus() != null) && fao.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			Collection keyList = faoMap.keySet();
			Iterator itr = keyList.iterator();
			while (itr.hasNext()) {
				IFixedAssetOthers faoObj = (IFixedAssetOthers) faoMap.get(itr.next());
				if (faoObj.getFAOID().equals(fao.getFAOID())) {
					found = true;
					break;
				}
			}
			if (!found) {
				deleteFao(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a fao.
	 * 
	 * @param theEjb of type EBFixedAssetOthersLocal
	 * @throws ConcurrentUpdateException on error update to fao
	 */
	private void deleteFao(EBFixedAssetOthersLocal theEjb) throws ConcurrentUpdateException {
		theEjb.deleteFixedAssetOthers(theEjb.getValue());
		// theEjb.setStatus (ICMSConstant.STATE_DELETED);
	}

	// private methods for insurance stock map

	/**
	 * set a list of insurance stock map as an asset.
	 * 
	 * @param insuranceStockMap of type Map
	 * @throws CreateException on error creating reference to insuranceStockMap
	 */
	private void setInsuranceStockMapRef(Map insuranceStockMap, boolean isAdd) throws CreateException {
		if ((insuranceStockMap == null) || insuranceStockMap.isEmpty()) {
			removeAllInsuranceStockMap();
			return;
		}

		EBInsuranceStockMapLocalHome ejbHome = getEBInsuranceStockMapLocalHome();

		Collection c = getInsuranceStockMapCMR();

		if (isAdd || (c.size() == 0)) {
			if (insuranceStockMap != null) {
				Collection valueList = insuranceStockMap.values();
				Iterator itr = valueList.iterator();
				while (itr.hasNext()) {
					Collection mapList = (Collection) itr.next();
					if (mapList != null) {
						Iterator mapItr = mapList.iterator();
						while (mapItr.hasNext()) {
							c.add(ejbHome.create((IGenChargeMapEntry) mapItr.next()));
						}
					}
				}
			}
			return;
		}

		removeInsuranceStockMap(c, insuranceStockMap);

		Iterator iterator = c.iterator();
		HashMap insIDStockIDMap = new HashMap();

		while (iterator.hasNext()) {
			EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) iterator.next();
			IGenChargeMapEntry entry = theEjb.getValue();
			String key = String.valueOf(entry.getRefID());
			insIDStockIDMap.put(key, theEjb);
		}

		ArrayList newItems = new ArrayList();

		Collection keyList = insuranceStockMap.keySet();
		Iterator keyItr = keyList.iterator();
		while (keyItr.hasNext()) {
			Collection mapEntryList = (Collection) insuranceStockMap.get(keyItr.next());
			if (mapEntryList != null) {
				Iterator mapItr = mapEntryList.iterator();
				while (mapItr.hasNext()) {
					boolean found = false;
					IGenChargeMapEntry entry = (IGenChargeMapEntry) mapItr.next();
					String key = String.valueOf(entry.getRefID());

					EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) insIDStockIDMap.get(key);
					if (theEjb != null) {
						IGenChargeMapEntry value = theEjb.getValue();
						if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
							continue;
						}

						theEjb.setValue(entry);
						found = true;
					}
					if (!found) {
						newItems.add(entry);
					}
				}
			}
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IGenChargeMapEntry) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the insurance info.
	 */
	private void removeAllInsuranceStockMap() {
		Collection c = getInsuranceStockMapCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) iterator.next();
			deleteInsuranceStockMap(theEjb);
		}
	}

	/**
	 * Helper method to delete insurance info in insuranceCol that are not
	 * contained in insuranceMap.
	 * 
	 * @param insuranceStockMap a list of old Insurance Info
	 * @param insuranceStockMapCol a Map of newly updated insurance info
	 */
	private void removeInsuranceStockMap(Collection insuranceStockMapCol, Map insuranceStockMap) {
		Iterator iterator = insuranceStockMapCol.iterator();

		while (iterator.hasNext()) {
			EBInsuranceStockMapLocal theEjb = (EBInsuranceStockMapLocal) iterator.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() != null) && mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			Collection mapList = (Collection) insuranceStockMap.get(mapEntry.getInsuranceID());
			if (mapList != null) {
				Iterator itr = mapList.iterator();
				while (itr.hasNext()) {
					IGenChargeMapEntry obj = (IGenChargeMapEntry) itr.next();
					if (obj.getRefID() == mapEntry.getRefID()) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				deleteInsuranceStockMap(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a insurance info
	 * 
	 * @param theEjb of type EBInsuranceInfoLocal
	 */
	private void deleteInsuranceStockMap(EBInsuranceStockMapLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	/**
	 * set a list of insurance fao map as an asset.
	 * 
	 * @param insuranceFaoMap of type Map
	 * @throws CreateException on error creating reference to insuranceFaoMap
	 */
	private void setInsuranceFaoMapRef(Map insuranceFaoMap, boolean isAdd) throws CreateException {
		if ((insuranceFaoMap == null) || insuranceFaoMap.isEmpty()) {
			removeAllInsuranceFaoMap();
			return;
		}

		EBInsuranceFaoMapLocalHome ejbHome = getEBInsuranceFaoMapLocalHome();

		Collection c = getInsuranceFaoMapCMR();

		if (isAdd || (c.size() == 0)) {
			if (insuranceFaoMap != null) {
				Collection valueList = insuranceFaoMap.values();
				if (valueList != null) {
					Iterator itr = valueList.iterator();
					while (itr.hasNext()) {
						Collection mapList = (Collection) itr.next();
						if (mapList != null) {
							Iterator mapItr = mapList.iterator();
							while (mapItr.hasNext()) {
								c.add(ejbHome.create((IGenChargeMapEntry) mapItr.next()));
							}
						}
					}
				}
			}
			return;
		}

		removeInsuranceFaoMap(c, insuranceFaoMap);

		Iterator iterator = c.iterator();
		HashMap insIDFaoIDMap = new HashMap();

		while (iterator.hasNext()) {
			EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) iterator.next();
			IGenChargeMapEntry entry = theEjb.getValue();
			String key = String.valueOf(entry.getRefID());
			insIDFaoIDMap.put(key, theEjb);
		}

		ArrayList newItems = new ArrayList();

		Collection keyList = insuranceFaoMap.keySet();
		Iterator keyItr = keyList.iterator();
		while (keyItr.hasNext()) {
			Collection mapEntryList = (Collection) insuranceFaoMap.get(keyItr.next());
			if (mapEntryList != null) {
				Iterator mapItr = mapEntryList.iterator();
				while (mapItr.hasNext()) {
					boolean found = false;
					IGenChargeMapEntry entry = (IGenChargeMapEntry) mapItr.next();
					String key = String.valueOf(entry.getRefID());

					EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) insIDFaoIDMap.get(key);
					if (theEjb != null) {
						IGenChargeMapEntry value = theEjb.getValue();
						if ((value.getStatus() != null) && value.getStatus().equals(ICMSConstant.STATE_DELETED)) {
							continue;
						}

						theEjb.setValue(entry);
						found = true;
					}
					if (!found) {
						newItems.add(entry);
					}
				}
			}
		}

		iterator = newItems.iterator();

		while (iterator.hasNext()) {
			c.add(ejbHome.create((IGenChargeMapEntry) iterator.next()));
		}
	}

	/**
	 * Helper method to delete all the insurance info.
	 */
	private void removeAllInsuranceFaoMap() {
		Collection c = getInsuranceFaoMapCMR();
		Iterator iterator = c.iterator();

		while (iterator.hasNext()) {
			EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) iterator.next();
			deleteInsuranceFaoMap(theEjb);
		}
	}

	/**
	 * Helper method to delete insurance info in insuranceCol that are not
	 * contained in insuranceMap.
	 * 
	 * @param insuranceFaoMap a list of old Insurance Info
	 * @param insuranceFaoMapCol a Map of newly updated insurance info
	 */
	private void removeInsuranceFaoMap(Collection insuranceFaoMapCol, Map insuranceFaoMap) {
		Iterator iterator = insuranceFaoMapCol.iterator();

		while (iterator.hasNext()) {
			EBInsuranceFaoMapLocal theEjb = (EBInsuranceFaoMapLocal) iterator.next();
			IGenChargeMapEntry mapEntry = theEjb.getValue();
			if ((mapEntry.getStatus() != null) && mapEntry.getStatus().equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			boolean found = false;

			Collection mapList = (Collection) insuranceFaoMap.get(mapEntry.getInsuranceID());
			if (mapList != null) {
				Iterator itr = mapList.iterator();
				while (itr.hasNext()) {
					IGenChargeMapEntry obj = (IGenChargeMapEntry) itr.next();
					if (obj.getRefID() == mapEntry.getRefID()) {
						found = true;
						break;
					}
				}
			}
			if (!found) {
				deleteInsuranceFaoMap(theEjb);
			}
		}
	}

	/**
	 * Helper method to delete a insurance info
	 * 
	 * @param theEjb of type EBInsuranceInfoLocal
	 */
	private void deleteInsuranceFaoMap(EBInsuranceFaoMapLocal theEjb) {
		theEjb.setStatus(ICMSConstant.STATE_DELETED);
	}

	// added for gcms MBB-474

	public abstract Double getEBDepreciateRate();

	public abstract void setEBDepreciateRate(Double eBDepreciateRate);

	public abstract String getEBIsPhysicalInspection();

	public abstract void setEBIsPhysicalInspection(String eBIsPhysicalInspection);

	/*
	 * public abstract int getPhysicalInspectionFreq(); public abstract void
	 * setPhysicalInspectionFreq(int physicalInspectionFreq);
	 */

	/*
	 * public abstract Date getNextPhysicalInspectDate(); public abstract void
	 * setNextPhysicalInspectDate(Date nextPhysicalInspectDate);
	 */

	/*
	 * public abstract Date getLastPhysicalInspectDate(); public abstract void
	 * setLastPhysicalInspectDate(Date lastPhysicalInspectDate);
	 */

	// gcms
	public Amount getDepreciateRate() {
		if (getEBDepreciateRate() != null) {
			return new Amount(getEBDepreciateRate().doubleValue(), currencyCode);
		}
		else {
			return null;
		}
	}

	public void setDepreciateRate(Amount depreciateRate) {
		if (depreciateRate != null) {
			setEBDepreciateRate(new Double(depreciateRate.getAmountAsDouble()));
		}
		else {
			setEBDepreciateRate(null);
		}
	}

	public boolean getIsPhysicalInspection() {
		String isInspect = getEBIsPhysicalInspection();
		if ((isInspect != null) && isInspect.equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public void setIsPhysicalInspection(boolean isPhysicalInspection) {
		if (isPhysicalInspection) {
			setEBIsPhysicalInspection(ICMSConstant.TRUE_VALUE);
		}
		else {
			setEBIsPhysicalInspection(ICMSConstant.FALSE_VALUE);
		}
	}

	public abstract String getRemarks();

	public abstract void setRemarks(String remarks);

	public abstract String getOtherRemarks();

	public abstract void setOtherRemarks(String otherRemarks);

	public abstract String getEnvRiskyStatus();

	public abstract void setEnvRiskyStatus(String envRiskyStatus);

	public abstract Date getEnvRiskyDate();

	public abstract void setEnvRiskyDate(Date envRiskyDate);

	public abstract String getEnvRiskyRemarks();

	public abstract void setEnvRiskyRemarks(String envRiskyRemarks);

	public abstract String getStockInsrGracePeriodFreq();

	public abstract void setStockInsrGracePeriodFreq(String stockInsrGracePeriodFreq);

	public abstract String getFaoInsrGracePeriodFreq();

	public abstract void setFaoInsrGracePeriodFreq(String faoInsrGracePeriodFreq);

	public abstract int getPhysicalInspectionFreq();

	public abstract void setPhysicalInspectionFreq(int physicalInspectionFreq);

	public abstract String getPhysicalInspectionFreqUnit();

	public abstract void setPhysicalInspectionFreqUnit(String physicalInspectionFreqUnit);

	public abstract Date getLastPhysicalInspectDate();

	public abstract void setLastPhysicalInspectDate(Date lastPhysicalInspectDate);

	public abstract Date getNextPhysicalInspectDate();

	public abstract void setNextPhysicalInspectDate(Date nextPhysicalInspectDate);

	public abstract Date getChattelSoldDate();

	public abstract String getRlSerialNumber();

	public abstract void setChattelSoldDate(Date chattelSoldDate);

	public abstract void setRlSerialNumber(String rlSerialNumber);

	//Added by Anil will be for one to many relationship of GeneralChargeDetails 
	public IGeneralChargeDetails[] getGeneralChargeDetails() {
		return retrieveGeneralChargeDetails();
	}
	public void setGeneralChargeDetails(IGeneralChargeDetails[] generalChargeDetails) {
		//do noting
	}
	
	public boolean addGeneralChargeDetails(IGeneralChargeDetails chargeDetails) {
		return false;
	}
	
	public boolean replaceGeneralChargeDetails(int index, IGeneralChargeDetails chargeDetails) throws Exception{
		return false;
	}
	
	public abstract Collection getCMRGeneralChargeDetails();
	public abstract void setCMRGeneralChargeDetails(Collection value);

	private IGeneralChargeDetails[] retrieveGeneralChargeDetails() throws GeneralChargeException {
		try {
			Collection c = getCMRGeneralChargeDetails();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList aList = new ArrayList();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBGeneralChargeDetailsLocal local = (EBGeneralChargeDetailsLocal) i.next();
					IGeneralChargeDetails ob = local.getValue();
					aList.add(ob);
				}

				return (IGeneralChargeDetails[]) aList.toArray(new IGeneralChargeDetails[0]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof GeneralChargeException) {
				throw (GeneralChargeException) e;
			}
			else {
				throw new GeneralChargeException("Caught Exception: " + e.toString());
			}
		}
	}
	
	private void updateGeneralChargeDetails(IGeneralChargeDetails[] chargeDetails,long cmsCollateralID, boolean isAdd) throws GeneralChargeException {
		try {
			Collection c = getCMRGeneralChargeDetails();

			if (null == chargeDetails) {
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteGeneralChargeDetails(new ArrayList(c));
				}
			}
			else if (isAdd ||(null == c) || (c.size() == 0)) {
				// create new records
				createGeneralChargeDetails(Arrays.asList(chargeDetails),cmsCollateralID);
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (i.hasNext()) {
					EBGeneralChargeDetailsLocal local = (EBGeneralChargeDetailsLocal) i.next();

					long generalChargeDetailsID = local.getGeneralChargeDetailsID();
					boolean update = false;

					for (int j = 0; j < chargeDetails.length; j++) {
						IGeneralChargeDetails newOB = chargeDetails[j];

						if (newOB.getGeneralChargeDetailsID() == generalChargeDetailsID) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				for (int j = 0; j < chargeDetails.length; j++) {
					i = c.iterator();
					IGeneralChargeDetails newOB = chargeDetails[j];
					boolean found = false;

					while (i.hasNext()) {
						EBGeneralChargeDetailsLocal local = (EBGeneralChargeDetailsLocal) i.next();
						long id = local.getGeneralChargeDetailsID();

						if (newOB.getGeneralChargeDetailsID() == id) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteGeneralChargeDetails(deleteList);
				createGeneralChargeDetails(createList,cmsCollateralID);
			}
		}
		catch (GeneralChargeException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new GeneralChargeException("Caught Exception: " + e.toString());
		}
	}
	private void deleteGeneralChargeDetails(List deleteList) throws GeneralChargeException {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection c = getCMRGeneralChargeDetails();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBGeneralChargeDetailsLocal local = (EBGeneralChargeDetailsLocal) i.next();
				c.remove(local);
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof GeneralChargeException) {
				throw (GeneralChargeException) e;
			}
			else {
				throw new GeneralChargeException("Caught Exception: " + e.toString());
			}
		}
	}
	private void createGeneralChargeDetails(List createList,long cmsCollateralID) throws GeneralChargeException {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRGeneralChargeDetails();
		Iterator i = createList.iterator();
		try {
			EBGeneralChargeDetailsLocalHome home = getEBLocalHomeGeneralChargeDetails();
			while (i.hasNext()) {
				IGeneralChargeDetails ob = (IGeneralChargeDetails) i.next();
				if(ob!=null){
				DefaultLogger.debug(this, "Creating GeneralChargeDetails ID: " + ob.getGeneralChargeDetailsID());
				String serverType = (new BatchResourceFactory()).getAppServerType();
				DefaultLogger.debug(this, "=======Application server Type is(banking method) ======= : " + serverType);
				if(serverType.equals(ICMSConstant.WEBSPHERE))
				{
					ob.setCmsCollatralId(cmsCollateralID);
				}
				EBGeneralChargeDetailsLocal local = home.create(ob);
				c.add(local);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			if (e instanceof GeneralChargeException) {
				throw (GeneralChargeException) e;
			}
			else {
				throw new GeneralChargeException("Caught Exception: " + e.toString());
			}
		}
	}
	
	public Map<String, String> getDueDateAndStockStatements() {
		return null;
	}

	public void setDueDateAndStockStatements(Map<String, String> dueDateAndStockStatements) {
		
	}

	protected EBGeneralChargeDetailsLocalHome getEBLocalHomeGeneralChargeDetails() throws GeneralChargeException {
		EBGeneralChargeDetailsLocalHome home = (EBGeneralChargeDetailsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_GENERALCHARGEDETAILS_LOCAL_JNDI, EBGeneralChargeDetailsLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new GeneralChargeException("EBGeneralChargeDetailsLocalHome is null!");
		}
	}
}