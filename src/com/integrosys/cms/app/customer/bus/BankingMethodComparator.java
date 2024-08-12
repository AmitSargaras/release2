package com.integrosys.cms.app.customer.bus;

import java.util.Comparator;

public class BankingMethodComparator implements Comparator<IBankingMethod>{

	@Override
	public int compare(IBankingMethod ob1, IBankingMethod ob2) {
		if(ob1.getBankingMethodID() < ob2.getBankingMethodID())
			return -1;
		else if(ob1.getBankingMethodID() == ob2.getBankingMethodID())
			return 0;
		else 
			return 1;
	}

}
