package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.exception.ChainedException;

/**
 * This class represents the summary for the general charge asset stock subtype.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/06/07 07:21:38 $ Tag: $Name: $
 */
public class OBStockSummary extends OBGeneralChargeSubTypeSummary {
	Amount creditorAmount;

	String[] stockTypes;

	public Amount getCreditorAmount() {
		return creditorAmount;
	}

	public void setCreditorAmount(Amount creditorAmount) {
		this.creditorAmount = creditorAmount;
	}

	public Amount getGrossValueLessCreditorAmt() {
		try {
			if (getGrossValue() == null) {
				return null;
			}
			if (GeneralChargeUtil.isForexErrorAmount(getGrossValue())) {
				return GeneralChargeUtil.getForexErrorAmount();
			}
			return (getCreditorAmount() == null) ? getGrossValue() : getGrossValue().subtract(getCreditorAmount());
		}
		catch (ChainedException e) {
			throw new RuntimeException("Exception in calculating grossvalue less creditors : " + e.toString());
		}
	}

	public void setGrossValueLessCreditorAmt(Amount amt) {
		// no action required. derived value
	}

	public String[] getStockTypes() {
		return stockTypes;
	}

	public void setStockTypes(String[] stockTypes) {
		this.stockTypes = stockTypes;
	}
}
