/*
 * Created on Apr 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.support;

import java.io.Serializable;

public class ValuationFrequency implements Serializable {

	private static final long serialVersionUID = -1431120699962420892L;

	public static final String FREQ_DAY = "1";

	public static final String FREQ_WEEK = "2";

	public static final String FREQ_MONTH = "3";

	public static final String FREQ_YEAR = "4";

	private int freq = 0;

	private String freqUnit = FREQ_DAY;

	public ValuationFrequency() {
	}

	public ValuationFrequency(String frequencyUnit, int frequency) {
		this.freqUnit = frequencyUnit;
		this.freq = frequency;
	}

	/**
	 * @return Returns the freq.
	 */
	public int getFreq() {
		return freq;
	}

	/**
	 * @param freq The freq to set.
	 */
	public void setFreq(int freq) {
		this.freq = freq;
	}

	/**
	 * @return Returns the freqUnit.
	 */
	public String getFreqUnit() {
		return freqUnit;
	}

	/**
	 * @param freqUnit The freqUnit to set.
	 */
	public void setFreqUnit(String freqUnit) {
		this.freqUnit = freqUnit;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Valuation Frequencey [");
		buf.append(freq).append(" ");
		if (FREQ_DAY.equals(freqUnit)) {
			buf.append(" Day(s)");
		}
		else if (FREQ_WEEK.equals(freqUnit)) {
			buf.append(" Week(s)");
		}
		else if (FREQ_MONTH.equals(freqUnit)) {
			buf.append(" Month(s)");
		}
		else if (FREQ_YEAR.equals(freqUnit)) {
			buf.append(" Year(s)");
		}
		buf.append("]");

		return buf.toString();
	}

}
