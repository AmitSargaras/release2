package com.integrosys.cms.app.collateral.bus;

/**
 * Collateral Perfector to check whether collateral information is perfected for
 * in the system
 * 
 * @author Chong Jun Yong
 * 
 */
public interface ICollateralPerfector {
	/**
	 * @param col collateral to check against for the perfection information
	 * @return true if collateral is perfected, else false
	 */
	public boolean isCollateralPerfected(ICollateral col);
}