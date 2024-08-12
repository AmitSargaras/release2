/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBGeneralChargeSubType.java,v 1.8 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents subtypes of the Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public class OBGeneralChargeSubType extends OBValuationDetails implements IGeneralChargeSubType {
	private String address;

	private String status;

	private String valuerCode;

	private CurrencyCode valuationCCYCode;

	protected Amount grossValue;

	protected Amount netValue;

	private double margin = ICMSConstant.DOUBLE_INVALID_VALUE;

	/**
	 * Get ref ID for the subtype.
	 * 
	 * @return String
	 */
	public String getID() {
		return null;
	}

	/**
	 * Get address.
	 * 
	 * @return String
	 */
	public String getAddress() {
		return this.address;
	}

	/**
	 * Set address.
	 * 
	 * @param address is of type String
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get Status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status.
	 * 
	 * @param status is of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Get valuer Code.
	 * 
	 * @return String
	 */
	public String getValuerName() {
		return valuerCode;
	}

	/**
	 * Set valuer Code.
	 * 
	 * @param valuerCode is of type String
	 */
	public void setValuerName(String valuerCode) {
		this.valuerCode = valuerCode;
	}

	/**
	 * Get subtype gross value.
	 * 
	 * @return Amount
	 */
	public Amount getGrossValue() {
		return grossValue;
	}

	/**
	 * Set subtype gross value.
	 * 
	 * @param grossValue - Amount
	 */
	public void setGrossValue(Amount grossValue) {
		this.grossValue = grossValue;
	}

	/**
	 * Get subtype margin.
	 * 
	 * @return double
	 */
	public double getMargin() {
		return margin;
	}

	/**
	 * Set subtype margin.
	 * 
	 * @param margin - double
	 */
	public void setMargin(double margin) {
		this.margin = margin;
	}

	/**
	 * Get subtype net value.
	 * 
	 * @return Amount
	 */
	public Amount getNetValue() {
		return netValue;
	}

	/**
	 * Set subtype net value
	 * 
	 * @param netValue - Amount
	 */
	public void setNetValue(Amount netValue) {
		this.netValue = netValue;
	}

	public String getValuationCurrency() {
		return (valuationCCYCode == null) ? null : valuationCCYCode.getCode();
	}

	public void setValuationCurrency(String valuationCCYCode) {
		this.valuationCCYCode = (valuationCCYCode == null) ? null : new CurrencyCode(valuationCCYCode);
	}
}
