/*
 * Created on May 10, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.app.collateral.bus.valuation.model;

import java.util.ArrayList;
import java.util.List;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CashValuationModel extends GenericValuationModel {
	private List depositList;

	public CashValuationModel() {
		super();
		depositList = new ArrayList();
	}

	/**
	 * @return Returns the depositList.
	 */
	public List getDepositList() {
		return depositList;
	}

	/**
	 * @param depositList The depositList to set.
	 */
	public void setDepositList(List depositList) {
		this.depositList = depositList;
	}

	public void addDeposit(ICashDeposit cashDeposit) {
		depositList.add(cashDeposit);
	}

	public void setDetailFromCollateral(ICollateral col) {
		super.setDetailFromCollateral(col);
		if (col instanceof ICashCollateral) {
			ICashCollateral cashCol = (ICashCollateral) col;
			ICashDeposit[] deposits = cashCol.getDepositInfo();
			if (deposits != null) {
				for (int i = 0; i < deposits.length; i++) {
					this.addDeposit(deposits[i]);
				}
			}
		}
	}
}
