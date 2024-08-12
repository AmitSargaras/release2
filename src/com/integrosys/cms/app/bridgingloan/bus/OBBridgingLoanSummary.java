package com.integrosys.cms.app.bridgingloan.bus;

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
public class OBBridgingLoanSummary implements IBridgingLoanSummary {

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private long limitID = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceLimit;

	private String productDescription;

	private long projectID = ICMSConstant.LONG_INVALID_VALUE;

	private String projectNumber;

	private Date contractDate;

	private Date actualStartDate;

	private Date actualCompletionDate;

	private Amount contractAmount;

	private float financePercent = ICMSConstant.FLOAT_INVALID_VALUE;

	/**
	 * Default Constructor
	 */
	public OBBridgingLoanSummary() {
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

	public long getProjectID() {
		return projectID;
	}

	public void setProjectID(long projectID) {
		this.projectID = projectID;
	}

	public String getProjectNumber() {
		return projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public Date getActualStartDate() {
		return actualStartDate;
	}

	public void setActualStartDate(Date actualStartDate) {
		this.actualStartDate = actualStartDate;
	}

	public Date getActualCompletionDate() {
		return actualCompletionDate;
	}

	public void setActualCompletionDate(Date actualCompletionDate) {
		this.actualCompletionDate = actualCompletionDate;
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
		return new Amount(contractAmount.getAmount() * financePercent / 100, contractAmount.getCurrencyCode());
	}
}