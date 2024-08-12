package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This object represents marketable item detail for all marketable collaterals.
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public class OBMarketableEquityDetail implements IMarketableEquityDetail {

	private static final long serialVersionUID = 1610202227663947025L;

	private long equityDetailID;

	private long noOfUnits;

	private String shareType;

	private String status;

	private Date transactionDate;

	private String unitSign;

	// private IMarketableEquity equity;

	/**
	 * @return the equityDetailID
	 */
	public long getEquityDetailID() {
		return equityDetailID;
	}

	/**
	 * @param equityDetailID the equityDetailID to set
	 */
	public void setEquityDetailID(long equityDetailID) {
		this.equityDetailID = equityDetailID;
	}

	/**
	 * @return the noOfUnits
	 */
	public long getNoOfUnits() {
		return noOfUnits;
	}

	/**
	 * @param noOfUnits the noOfUnits to set
	 */
	public void setNoOfUnits(long noOfUnits) {
		this.noOfUnits = noOfUnits;
	}

	/**
	 * @return the shareType
	 */
	public String getShareType() {
		return shareType;
	}

	/**
	 * @param shareType the shareType to set
	 */
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the transactionDate
	 */
	public Date getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the unitSign
	 */
	public String getUnitSign() {
		return unitSign;
	}

	/**
	 * @param unitSign the unitSign to set
	 */
	public void setUnitSign(String unitSign) {
		this.unitSign = unitSign;
	}

	// /**
	// * Get the equity object that owns this equity detail
	// */
	// public IMarketableEquity getEquity() {
	// return this.equity;
	// }
	//
	// /**
	// * Set equity
	// * @param equity
	// */
	// public void setEquity(IMarketableEquity equity) {
	// this.equity = equity;
	// }

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
