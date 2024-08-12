package com.integrosys.cms.app.collateral.bus.type.cash;

import com.integrosys.cms.app.collateral.bus.AbstractCollateralPerfector;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.subtype.cashcash.ICashCash;

public class CashCollateralPerfector extends AbstractCollateralPerfector {
	public boolean isCollateralPerfected(ICollateral collateral) {

		ICashCollateral col = (ICashCollateral) collateral;

		if (!isCollateralCommonFieldsPerfected(col)) {
			return false;
		}

		if (!isChargeDetailsPerfected(col.getLimitCharges())) {
			return false;
		}

		ICashDeposit[] deposits = col.getDepositInfo();

		if ((deposits == null) || (deposits.length == 0)) {
			return false;
		}

		if (col instanceof ICashCash) {
			return isCashCashDepositsCollateralPerfected(deposits);
		}

		for (int i = 0; i < deposits.length; i++) {
			if (!isValidStr(deposits[i].getDepositRefNo()) || !isValidAmt(deposits[i].getDepositAmount())
					|| (deposits[i].getDepositMaturityDate() == null)) {
				return false;
			}
		}

		return true;
	}

	protected boolean isCashCashDepositsCollateralPerfected(ICashDeposit[] deposits) {
		for (int i = 0; i < deposits.length; i++) {
			if (!isValidAmt(deposits[i].getDepositAmount())) {
				return false;
			}
		}

		return true;
	}
}