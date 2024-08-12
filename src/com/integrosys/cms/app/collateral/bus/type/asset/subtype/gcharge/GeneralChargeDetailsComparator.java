package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.util.Comparator;

public class GeneralChargeDetailsComparator implements Comparator<IGeneralChargeDetails>{

		@Override
		public int compare(IGeneralChargeDetails ob1, IGeneralChargeDetails ob2) {
			if(ob1.getGeneralChargeDetailsID() < ob2.getGeneralChargeDetailsID())
				return -1;
			else if(ob1.getGeneralChargeDetailsID() == ob2.getGeneralChargeDetailsID())
				return 0;
			else 
				return 1;
		}

	}