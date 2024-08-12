package com.integrosys.cms.batch.collateral.dao;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

public class CollateralValuationUtil {
	private SBForexManager forexMgr;

	private static HashMap collParamCachMap = new HashMap();

	public boolean isAmtChanged(Amount oldAmt, Amount newAmt) {
		if ((oldAmt == null) && (newAmt == null)) {
			return false;
		}
		if ((oldAmt != null) && (newAmt != null) && oldAmt.equals(newAmt)) {
			return false;
		}
		return true;
	}

	public double getDoubleValue(BigDecimal value) {
		if (value == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return value.doubleValue();
		}
	}

	public Date getDateWithoutTime(Date date) {
		GregorianCalendar g = new GregorianCalendar();
		g.setTime(date);
		g.set(Calendar.HOUR_OF_DAY, 0);
		g.set(Calendar.MINUTE, 0);
		g.set(Calendar.SECOND, 0);
		g.set(Calendar.MILLISECOND, 0);
		return g.getTime();
	}

	public Date getNextRevaluationDate(Date latestValDate, String timeFreq, int timeFreqNum) {
		Date nextVal = null;
		if (latestValDate != null) {
			GregorianCalendar cal = new GregorianCalendar();
			cal.setTime(latestValDate);
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

	public long getLongValue(BigDecimal value) {
		if (value == null) {
			return ICMSConstant.LONG_INVALID_VALUE;
		}
		else {
			return value.longValue();
		}
	}

	public void setDBAmount(DBUtil dbUtil, int amtIdx, int ccyIdx, Amount amt) throws Exception {
		if (amt != null) {
			dbUtil.setBigDecimal(amtIdx, amt.getAmountAsBigDecimal());
			dbUtil.setString(ccyIdx, amt.getCurrencyCode());
		}
		else {
			dbUtil.setNull(amtIdx, Types.DOUBLE);
			dbUtil.setString(ccyIdx, null);
		}
	}

	public void setDBAmount(DBUtil dbUtil, int amtIdx, Amount amt) throws Exception {
		if (amt != null) {
			dbUtil.setBigDecimal(amtIdx, amt.getAmountAsBigDecimal());
		}
		else {
			dbUtil.setNull(amtIdx, Types.DOUBLE);
		}
	}

	public boolean isValidAmt(Amount amt) {
		if (amt == null) {
			return false;
		}
		if ((amt.getCurrencyCode() == null) || (amt.getCurrencyCode().length() == 0)) {
			return false;
		}

		return true;
	}

	public Amount sum(Amount amt1, Amount amt2, String newCcyCode) throws CollateralException {
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
			throw new CollateralException(e.toString());
		}
	}

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
			CollateralException ex = new CollateralException("failed to convert amount [" + amt + "] to currency ["
					+ newCcyCode + "]");
			ex.setErrorCode(CollateralException.FX_ERR_CODE);
			throw ex;
		}
	}

	public Amount getAmount(BigDecimal amt, String ccy) {
		if ((amt != null) && (ccy != null)) {
			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	public Amount roundAmount(Amount amt) {
		if (amt == null) {
			return null;
		}

		BigDecimal bd = amt.getAmountAsBigDecimal();
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return new Amount(bd, new CurrencyCode(amt.getCurrencyCode()));
	}

	public DBUtil getDBUtil() throws Exception {
		return new DBUtil();
	}

	public void finalize(DBUtil dbUtil) {
		try {
			if (dbUtil != null) {
				dbUtil.close();
				dbUtil = null;
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, e);
		}
	}

	public Amount calcFSV(ICollateral col, Amount cmv, BigDecimal margin) {
		if (cmv == null) {
			return null;
		}
		Amount fsvAmt = null;
		if (margin == null) {
			margin = getMargin(col);
		}
		if (margin != null) {
			fsvAmt = cmv.multiply(margin);
			if ((col.getFSV() != null) && !fsvAmt.equals(col.getFSV())) {
				int oldScacle = col.getFSV().getAmountAsBigDecimal().scale();
				int newScale = fsvAmt.getAmountAsBigDecimal().scale();
				int scale = Math.max(oldScacle, newScale);
				if ((oldScacle != newScale) && (scale > 2)) {
					scale = 2;
					fsvAmt.setAmountAsBigDecimal(fsvAmt.getAmountAsBigDecimal().setScale(scale,
							BigDecimal.ROUND_HALF_UP));
				}
			}
		}
		return fsvAmt;
	}

	public boolean isValChanged(ICollateral newColl, ICollateral oldColl) {
		if (newColl.getValuation() == null) {
			return false;
		}
		if (!newColl.getValuation().equals(oldColl.getValuation())) {
			DefaultLogger.debug(this, "Coll: " + newColl.getCollateralID());
			// DefaultLogger.debug(this, "Old Val : " + oldColl.getValuation());
			// DefaultLogger.debug(this, "New Val : " + newColl.getValuation());
			return true;
		}
		Amount newAmt = newColl.getCMV();
		Amount oldAmt = oldColl.getCMV();
		if (!(((newAmt == null) && (oldAmt == null)) || ((newAmt != null) && newAmt.equals(oldAmt)))) {
			DefaultLogger.debug(this, "Coll: " + newColl.getCollateralID());
			// DefaultLogger.debug(this, "newAmt: " + newAmt);
			// DefaultLogger.debug(this, "oldAmt: " + oldAmt);
			return true;
		}
		newAmt = newColl.getFSV();
		oldAmt = oldColl.getFSV();
		if (!(((newAmt == null) && (oldAmt == null)) || ((newAmt != null) && newAmt.equals(oldAmt)))) {
			DefaultLogger.debug(this, "Coll: " + newColl.getCollateralID());
			// DefaultLogger.debug(this, "newAmt: " + newAmt);
			// DefaultLogger.debug(this, "oldAmt: " + oldAmt);
			return true;
		}
		String newCcy = newColl.getCMVCcyCode();
		String oldCcy = oldColl.getCMVCcyCode();
		if (!(((newCcy == null) && (oldCcy == null)) || ((newCcy != null) && newCcy.equals(oldCcy)))) {
			DefaultLogger.debug(this, "Coll: " + newColl.getCollateralID());
			// DefaultLogger.debug(this, "newCcy: " + newCcy);
			// DefaultLogger.debug(this, "oldCcy: " + oldCcy);
			return true;
		}
		newCcy = newColl.getFSVCcyCode();
		oldCcy = oldColl.getFSVCcyCode();
		if (!(((newCcy == null) && (oldCcy == null)) || ((newCcy != null) && newCcy.equals(oldCcy)))) {
			DefaultLogger.debug(this, "Coll: " + newColl.getCollateralID());
			// DefaultLogger.debug(this, "newCcy: " + newCcy);
			// DefaultLogger.debug(this, "oldCcy: " + oldCcy);
			return true;
		}
		return false;
	}

	public BigDecimal getMargin(ICollateral col) {
		BigDecimal margin = new BigDecimal(col.getMargin());
		if (margin.doubleValue() < 0) {
			margin = getCRP(col);
		}
		return margin;
	}

	public BigDecimal getCRP(ICollateral col) {
		ICollateralParameter param = getSecurityParameter(col);
		if (param != null) {
			BigDecimal crp = new BigDecimal(param.getThresholdPercent());
			return crp.divide(ICMSConstant.BIGDECIMAL_ONE_HUNDRED, ICMSConstant.PERCENTAGE_SCALE,
					BigDecimal.ROUND_UNNECESSARY);
		}
		return null;
	}

	public String getValuationCurrency(ICollateral col) {
		if (col.getValuation() != null) {
			return col.getValuation().getCurrencyCode();
		}
		else {
			return null;
		}
	}

	public ICollateralParameter getSecurityParameter(ICollateral col) {
		try {
			String ctry = col.getCollateralLocation();
			String subtype = col.getCollateralSubType().getSubTypeCode();
			String key = ctry + subtype;
			ICollateralParameter param = null;
			if (!collParamCachMap.containsKey(key)) {
				ICollateralProxy proxy = CollateralProxyFactory.getProxy();
				param = proxy.getCollateralParameter(ctry, subtype);
				collParamCachMap.put(key, param);
			}
			else {
				param = (ICollateralParameter) collParamCachMap.get(key);
			}
			return param;
		}
		catch (Exception e) {
			DefaultLogger.warn(this, "", e);
			return null;
		}
	}

	private SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}
}
