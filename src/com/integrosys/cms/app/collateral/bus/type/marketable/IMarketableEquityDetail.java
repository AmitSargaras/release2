package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface represents marketable item detail for all marketable
 * collaterals.
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IMarketableEquityDetail extends Serializable {

	/**
	 * Get marketable equity id.
	 * 
	 * @return
	 */
	public long getEquityDetailID();

	/**
	 * Set the equity id
	 * @param equityDetailId
	 */
	public void setEquityDetailID(long equityDetailId);

	/**
	 * Get the unit number
	 * @return
	 */
	public long getNoOfUnits();

	/**
	 * Set the number of units
	 */
	public void setNoOfUnits(long noOfUnits);

	/**
	 * Get Share type
	 * @return
	 */
	public String getShareType();

	/**
	 * Set Share Type
	 * @param shareType
	 */
	public void setShareType(String shareType);

	/**
	 * Get Status of the transaction
	 * @return
	 */
	public String getStatus();

	/**
	 * Set Status
	 * @param status
	 */
	public void setStatus(String status);

	/**
	 * Get the transaction date
	 * @return
	 */
	public Date getTransactionDate();

	/**
	 * Set transaction date
	 * @param date
	 */
	public void setTransactionDate(Date date);

	/**
	 * Get Unit sign
	 * @return
	 */
	public String getUnitSign();

	/**
	 * Set Unit Sign
	 * @param unitSign
	 */
	public void setUnitSign(String unitSign);

	// /**
	// * Get the equity that owns it
	// * @return
	// */
	// public IMarketableEquity getEquity();
	//
	// /**
	// * Set the Equity
	// * @param equity
	// */
	// public void setEquity(IMarketableEquity equity);
}
