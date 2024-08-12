/*
 * Created on May 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class GuaranteeValuationModel extends GenericValuationModel {
	private Amount guaranteeAmount;

	/**
	 * @return Returns the guaranteeAmount.
	 */
	public Amount getGuaranteeAmount() {
		return guaranteeAmount;
	}

	/**
	 * @param guaranteeAmount The guaranteeAmount to set.
	 */
	public void setGuaranteeAmount(Amount guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public void setDetailFromCollateral(ICollateral col) {
		super.setDetailFromCollateral(col);
		if (col instanceof IGuaranteeCollateral) {
			IGuaranteeCollateral guaranteeCol = (IGuaranteeCollateral) col;
			this.setGuaranteeAmount(guaranteeCol.getGuaranteeAmount());
		}
	}
}
