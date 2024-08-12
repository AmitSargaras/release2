/*
 * Created on Jun 19, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.rv;

import java.io.Serializable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class AccountRVDetail implements Serializable {
	private long accountId;

	private String accountNo;

	private boolean isAccountNPL;

	private Amount chargeAmt;

	private Amount outstandingAmt;

	private Amount curRvValue;

	private Amount realizableValue;

	/**
	 * @return Returns the accountId.
	 */
	public long getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId The accountId to set.
	 */
	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return Returns the accountNo.
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * @param accountNo The accountNo to set.
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * @return Returns the isAccountNPL.
	 */
	public boolean isAccountNPL() {
		return isAccountNPL;
	}

	/**
	 * @param isAccountNPL The isAccountNPL to set.
	 */
	public void setAccountNPL(boolean isAccountNPL) {
		this.isAccountNPL = isAccountNPL;
	}

	public Amount getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Amount chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	/**
	 * @return Returns the outstandingAmt.
	 */
	public Amount getOutstandingAmt() {
		return outstandingAmt;
	}

	/**
	 * @param outstandingAmt The outstandingAmt to set.
	 */
	public void setOutstandingAmt(Amount outstandingAmt) {
		this.outstandingAmt = outstandingAmt;
	}

	/**
	 * @return Returns the realizableValue.
	 */
	public Amount getRealizableValue() {
		return realizableValue;
	}

	/**
	 * @param realizableValue The realizableValue to set.
	 */
	public void setRealizableValue(Amount realizableValue) {
		this.realizableValue = realizableValue;
	}

	public Amount getCurRvValue() {
		return curRvValue;
	}

	public void setCurRvValue(Amount curRvValue) {
		this.curRvValue = curRvValue;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
