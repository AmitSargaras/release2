/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBValuationDetails.java,v 1.1 2005/08/12 03:32:36 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents valuation details of the Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/08/12 03:32:36 $ Tag: $Name: $
 */
public abstract class OBValuationDetails implements IValuationDetails {

	private Date valuationDate;

	private int revalFreq = ICMSConstant.INT_INVALID_VALUE;

	private String revalFreqUnit;

	private Date revaluationDate;

	/**
	 * Get the valuation date.
	 * 
	 * @return Date
	 */
	public Date getValuationDate() {
		return this.valuationDate;
	}

	/**
	 * Set valuation date.
	 * 
	 * @param valuationDate is of type Date
	 */
	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	/**
	 * Get Revaluation Date
	 * 
	 * @return Date
	 */
	public Date getRevaluationDate() {
		return revaluationDate;
	}

	public void setRevaluationDate(Date revalDate) {
		this.revaluationDate = revalDate;
	}

	/**
	 * Get Revaluation Frequency
	 * 
	 * @return int
	 */
	public int getRevalFreq() {
		return revalFreq;
	}

	public void setRevalFreq(int revalFreq) {
		this.revalFreq = revalFreq;
	}

	/**
	 * Get Revaluation Frequency Unit
	 * 
	 * @return String
	 */
	public String getRevalFreqUnit() {
		return revalFreqUnit;
	}

	public void setRevalFreqUnit(String revalFreqUnit) {
		this.revalFreqUnit = revalFreqUnit;
	}
}
