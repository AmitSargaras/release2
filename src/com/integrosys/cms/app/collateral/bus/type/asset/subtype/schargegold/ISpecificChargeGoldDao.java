package com.integrosys.cms.app.collateral.bus.type.asset.subtype.schargegold;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;

public interface ISpecificChargeGoldDao{
	
	/**
	 * Get the amount of unit price by the gold grade
	 * @param goldGrade of String type
	 * @return Amount - the value of unit price 
	 * @throws SearchDAOException on errors
	 */
	public HashMap getUnitPriceByGoldGrade(String goldGrade)throws SearchDAOException;

}
