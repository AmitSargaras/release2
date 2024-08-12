/*
 * Created on May 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.creditinsurance.ICreditInsurance;
import com.integrosys.cms.app.collateral.bus.type.insurance.subtype.keymaninsurance.IKeymanInsurance;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class InsuranceValuationModel extends GenericValuationModel {
	private Amount insuredAmount;

	/**
	 * @return Returns the insuredAmount.
	 */
	public Amount getInsuredAmount() {
		return insuredAmount;
	}

	/**
	 * @param insuredAmount The insuredAmount to set.
	 */
	public void setInsuredAmount(Amount insuredAmount) {
		this.insuredAmount = insuredAmount;
	}

	public void setDetailFromCollateral(ICollateral col) {
		super.setDetailFromCollateral(col);
		if (col instanceof ICreditInsurance) {
			ICreditInsurance credInsCol = (ICreditInsurance) col;
			this.setInsuredAmount(credInsCol.getInsuredAmount());
		}
		else if (col instanceof IKeymanInsurance) {
			IKeymanInsurance keymanInsCol = (IKeymanInsurance) col;
			this.setInsuredAmount(keymanInsCol.getInsuredAmount());
		}
	}
}
