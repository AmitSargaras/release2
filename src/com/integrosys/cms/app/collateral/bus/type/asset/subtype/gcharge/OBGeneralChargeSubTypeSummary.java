package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents the summary for the general charge asset subtype.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/09/13 12:18:00 $ Tag: $Name: $
 */
public class OBGeneralChargeSubTypeSummary implements IGeneralChargeSubTypeSummary {

	String compareResultKey;

	String id;

	String address;

	String valuerName;

	Date valuationDate;

	Amount grossValue;

	Amount netValue;

	Amount totalInsrCoverageAmt; // sum of insuranceSummary.insuranceCover

	Amount recoverableAmount; // min of (netvalue, totalInsrCoverageAmt)

	OBInsuranceSummary[] insuranceSummary; // sorted by insurance id

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getValuerName() {
		return valuerName;
	}

	public void setValuerName(String valuerName) {
		this.valuerName = valuerName;
	}

	public Date getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public Amount getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(Amount grossValue) {
		this.grossValue = grossValue;
	}

	public Amount getNetValue() {
		return netValue;
	}

	public void setNetValue(Amount netValue) {
		this.netValue = netValue;
	}

	public Amount getTotalInsrCoverageAmt() {
		return totalInsrCoverageAmt;
	}

	public void setTotalInsrCoverageAmt(Amount totalInsrCoverageAmt) {
		this.totalInsrCoverageAmt = totalInsrCoverageAmt;
	}

	public Amount getRecoverableAmount() {
		return recoverableAmount;
	}

	public void setRecoverableAmount(Amount recoverableAmount) {
		this.recoverableAmount = recoverableAmount;
	}

	public OBInsuranceSummary[] getInsuranceSummary() {
		return insuranceSummary;
	}

	public void setInsuranceSummary(OBInsuranceSummary[] insuranceSummary) {
		this.insuranceSummary = insuranceSummary;
	}

	public String getCompareResultKey() {
		return compareResultKey;
	}

	public void setCompareResultKey(String compareResultKey) {
		this.compareResultKey = compareResultKey;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
