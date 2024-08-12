/**
 *
 */
package com.integrosys.cms.batch.feeds.vehicle;
/**
 * @author gp loh
 * @date 05oct08
 *
 */
public interface IVehicle {

	public void setRegion(String region);
	public String getRegion();

	public void setMakeOfVehicle(String makeType);
	public String getMakeOfVehicle();

	public void setModelOfVehicle(String model);
	public String getModelOfVehicle();

	public void setYearOfVehicle(int year);
	public int getYearOfVehicle();

	public void setPurchaseCurrency(String currency);
	public String getPurchaseCurrency();

	public void setValuationFSV(double fsv);
	public double getValuationFSV();
}
