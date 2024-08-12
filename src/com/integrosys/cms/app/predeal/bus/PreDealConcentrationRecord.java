/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealConcentrationRecord
 *
 * Created on 5:06:57 PM
 *
 * Purpose: PreDeal Concentration DTO
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal.bus;

import java.io.Serializable;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * User: Siew Kheat Date: Sep 14, 2007 Time: 5:06:57 PM
 */
public class PreDealConcentrationRecord implements Serializable {

	private static final long serialVersionUID = 4465550681779010356L;

	private String counterName;

	private Amount marketCapitalization;

	private Amount totalPaidUpCapital;

	private Amount unitPrice;

	private HashMap concentrationMap;

	public PreDealConcentrationRecord() {
		concentrationMap = new HashMap();
	}

	/**
	 * @return the counterName
	 */
	public String getCounterName() {
		return counterName;
	}

	/**
	 * @param counterName the counterName to set
	 */
	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	/**
	 * @return the marketCapitalization
	 */
	public Amount getMarketCapitalization() {
		return marketCapitalization;
	}

	/**
	 * @param marketCapitalization the marketCapitalization to set
	 */
	public void setMarketCapitalization(Amount marketCapitalization) {
		this.marketCapitalization = marketCapitalization;
	}

	/**
	 * @return the totalPaidUpCapital
	 */
	public Amount getTotalPaidUpCapital() {
		return totalPaidUpCapital;
	}

	/**
	 * @param totalPaidUpCapital the totalPaidUpCapital to set
	 */
	public void setTotalPaidUpCapital(Amount totalPaidUpCapital) {
		this.totalPaidUpCapital = totalPaidUpCapital;
	}

	/**
	 * @return the unitPrice
	 */
	public Amount getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(Amount unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * @return the concentrationMap
	 */
	public HashMap getConcentrationMap() {
		return concentrationMap;
	}

	/**
	 * @param concentrationMap the concentrationMap to set
	 */
	public void setConcentrationMap(HashMap concentrationMap) {
		this.concentrationMap = concentrationMap;
	}

}
