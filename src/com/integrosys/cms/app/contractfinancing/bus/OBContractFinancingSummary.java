package com.integrosys.cms.app.contractfinancing.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBContractFinancingSummary implements IContractFinancingSummary {

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceLimit;

	private String productDescription;

	private long contractID = ICMSConstant.LONG_INVALID_VALUE;

	private String contractNumber;

	private Date contractDate;

	private Date expiryDate;

	private Date extendedDate;

	private Amount contractAmount;

	private float financePercent = ICMSConstant.FLOAT_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBContractFinancingSummary() {
	}

	public long getLimitProfileID() {
		return limitProfileID;
	}

	public void setLimitProfileID(long limitProfileID) {
		this.limitProfileID = limitProfileID;
	}

	public long getLimitID() {
		return limitID;
	}

	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	public String getSourceLimit() {
		return sourceLimit;
	}

	public void setSourceLimit(String sourceLimit) {
		this.sourceLimit = sourceLimit;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public long getContractID() {
		return contractID;
	}

	public void setContractID(long contractID) {
		this.contractID = contractID;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Date getExtendedDate() {
		return extendedDate;
	}

	public void setExtendedDate(Date extendedDate) {
		this.extendedDate = extendedDate;
	}

	public Amount getContractAmount() {
		return contractAmount;
	}

	public void setContractAmount(Amount contractAmount) {
		this.contractAmount = contractAmount;
	}

	public float getFinancePercent() {
		return financePercent;
	}

	public void setFinancePercent(float financePercent) {
		this.financePercent = financePercent;
	}

	public Amount getFinancedAmount() {
		if (financePercent == ICMSConstant.FLOAT_INVALID_VALUE) {
			return null;
		}
		else {
			return new Amount(contractAmount.getAmount() * financePercent / 100, contractAmount.getCurrencyCode());
		}
	}

}
