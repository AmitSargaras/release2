/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/forex/OBForex.java,v 1.2 2003/08/08 04:20:04 jtan Exp $
 */
package com.integrosys.cms.batch.forex;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Describes this class Purpose: A value object that represents a forex rate
 * Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class OBForex {

	public static final String DATE_FORMAT = "ddMMyyyy";

	private String fromCurrency;

	private String toCurrency;

	private double rate;

	private String effectiveDate;

	private String indicator;

	public String getIndicator() {
		return indicator;
	}

	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}

	private int unit;

	public OBForex(String fromCurrency, String toCurrency, double rate, String effectiveDate, int unit, String indicator) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.rate = rate;
		this.effectiveDate = effectiveDate;
		this.unit = unit;
		this.indicator = indicator;
	}
	
	public OBForex(){
		super();
	}
	
	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public int getUnit() {
		return unit;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	public java.sql.Date getSQLEffectiveDate() {
		java.util.Date d = convertStringToDate(getEffectiveDate(), DATE_FORMAT);
		return new java.sql.Date(d.getTime());
	}

	public static java.util.Date convertStringToDate(String s, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		java.util.Date d = null;
		try {
			if (pattern != null) {
				d = df.parse(s);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(ForexLoader.class.getName(), "Error occured in parsing date");
			e.printStackTrace();
		}

		return d;
	}

	public void show() {
		DefaultLogger.info(this, "fromCurrency : " + this.fromCurrency);
		DefaultLogger.info(this, "toCurrency: " + this.toCurrency);
		DefaultLogger.info(this, "getEffectiveDate: " + this.getEffectiveDate());
		DefaultLogger.info(this, "getRate: " + this.getRate());
		DefaultLogger.info(this, "unit: " + this.getUnit());
	}

}
