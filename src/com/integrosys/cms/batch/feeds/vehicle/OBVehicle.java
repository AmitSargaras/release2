/**
 *
 */
package com.integrosys.cms.batch.feeds.vehicle;

import com.integrosys.base.techinfra.util.AccessorUtil;


/**
 * @author gploh
 * @date 05oct 08
 *
 */
public class OBVehicle implements IVehicle {

	private String make;
	private String compositeID;
	private String model;
	private String region;
	private String currency = "MYR";
	private double forceSaleValue;
	private int year;


	/**
	 * Default Constructor
	 */
	public OBVehicle() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type IVehicle
	 */
	public OBVehicle(IVehicle value) {
		this();
		AccessorUtil.copyValue(value, this);
	}


	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setMakeOfVehicle(java.lang.String)
	 */
	public void setMakeOfVehicle(String makeType) {
		// TODO Auto-generated method stub
		this.make = makeType;
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getMakeOfVehicle()
	 */
	public String getMakeOfVehicle() {
		// TODO Auto-generated method stub
		return make;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setModelOfVehicle(java.lang.String)
	 */
	public void setModelOfVehicle(String version) {
		// TODO Auto-generated method stub
		this.model = version;
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getModelOfVehicle()
	 */
	public String getModelOfVehicle() {
		// TODO Auto-generated method stub
		return model;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setPurchaseCurrency(java.lang.String)
	 */
	public void setPurchaseCurrency(String currency) {
		// TODO Auto-generated method stub
		this.currency = currency;
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getPurchaseCurrency()
	 */
	public String getPurchaseCurrency() {
		// TODO Auto-generated method stub
		return currency;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setRegion(java.lang.String)
	 */
	public void setRegion(String region) {
		// TODO Auto-generated method stub
		this.region = region;
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getRegion()
	 */
	public String getRegion() {
		// TODO Auto-generated method stub
		return region;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setYearOfVehicle(int)
	 */
	public void setYearOfVehicle(int year) {
		// TODO Auto-generated method stub
		this.year = year;
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getYearOfVehicle()
	 */
	public int getYearOfVehicle() {
		// TODO Auto-generated method stub
		return year;
	}

	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#setValuationFSV(java.lang.String)
	 */
	public void setValuationFSV(double fsv) {
		if ( String.valueOf(fsv).equalsIgnoreCase("") ) {
			this.forceSaleValue = -9999999999.9999;
		} else {
			this.forceSaleValue = fsv;
		}
	}
	/* (non-Javadoc)
	 * @see com.integrosys.cms.batch.feeds.hirepurchase.IVehicle#getValuationFSV()
	 */
	public double getValuationFSV() {
		/*String cleansedString = removeLeadingZeros(forceSaleValue,2,3);
		if (cleansedString !=null) {
			return new Double(forceSaleValue);
		} else {
			return new Double("-9999.99");
		}*/
		//return new Double(forceSaleValue);
		return forceSaleValue;
	}

	public void setCompositeID(String concatID) {
		this.compositeID = concatID;
	}
	public String getCompositeID() {
		return compositeID;
	}

}
