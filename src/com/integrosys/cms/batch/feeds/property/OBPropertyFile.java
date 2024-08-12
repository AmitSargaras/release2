/**
 *
 */
package com.integrosys.cms.batch.feeds.property;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;


/**
 * @author sai heng
 * @date 09 dec 08
 *
 */
public class OBPropertyFile implements IPropertyFile {

    private String compositeID;
    private String cifID;
    private String sourceSecurityId;
	private String valuerName;
//    private Date   valuationDate;
//    private int    valuationFrequency;
//    private Date   reValuationDate;
    private String valuerCode;
    private double omv;
    private double fsv;
    private double reservedPrice;
    private String valuationCurrency = "MYR";
    private long collateralID;


    /**
	 * Default Constructor
	 */
	public OBPropertyFile() {
	}

	/**
	 * Construct OB from interface
	 *
	 * @param value is of type IVehicle
	 */
	public OBPropertyFile(IPropertyFile value) {
		this();
		AccessorUtil.copyValue(value, this);
	}


	public void setCompositeID(String concatID) {
		this.compositeID = concatID;
	}
	public String getCompositeID() {
		return compositeID;
	}

    public String getCifID() {
        return cifID;
    }

    public void setCifID(String cifID) {
        this.cifID = cifID;
    }

    public String getSourceSecurityId() {
        return sourceSecurityId;
    }

    public void setSourceSecurityId(String sourceSecurityId) {
        this.sourceSecurityId = sourceSecurityId;
    }

    public String getValuerName() {
        return valuerName;
    }

    public void setValuerName(String valuerName) {
        this.valuerName = valuerName;
    }

//    public Date getValuationDate() {
//        return valuationDate;
//    }
//
//    public void setValuationDate(Date valuationDate) {
//        this.valuationDate = valuationDate;
//    }

//    public int getValuationFrequency() {
//        return valuationFrequency;
//    }
//
//    public void setValuationFrequency(int valuationFrequency) {
//        this.valuationFrequency = valuationFrequency;
//    }

//    public Date getReValuationDate() {
//        return reValuationDate;
//    }
//
//    public void setReValuationDate(Date reValuationDate) {
//        this.reValuationDate = reValuationDate;
//    }

    public String getValuerCode() {
        return valuerCode;
    }

    public void setValuerCode(String valuerCode) {
        this.valuerCode = valuerCode;
    }

    public double getOmv() {
        return omv;
    }

    public void setOmv(double omv) {
        this.omv = omv;
    }

    public double getFsv() {
        return fsv;
    }

    public void setFsv(double fsv) {
        this.fsv = fsv;
    }

    public double getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(double reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public String getValuationCurrency() {
        return valuationCurrency;
    }

    public void setValuationCurrency(String valuationCurrency) {
        this.valuationCurrency = valuationCurrency;
    }

    public long getCollateralID() {
        return collateralID;
    }

    public void setCollateralID(long collateralID) {
        this.collateralID = collateralID;
    }

}