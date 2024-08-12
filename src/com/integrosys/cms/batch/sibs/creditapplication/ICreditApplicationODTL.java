/**
 * 
 */
package com.integrosys.cms.batch.sibs.creditapplication;

/**
 * @author User
 *
 */
public interface ICreditApplicationODTL extends ICreditApplication {

	/********************************************************************************
	 * setter , getter methods pertinent to OD , TL Oustanding Balance only.
	 * 
	 * *****************************************************************************/
	public void setDrawingLimit(double dLimit);
	public double getDrawingLimit();

	public void setOutstdgBalSign(String ostdgBalSign);
	public String getOutstdgBalSign();

	public void setOutstandingBalance(double outstdBalance);
	public double getOutstandingBalance();

}
