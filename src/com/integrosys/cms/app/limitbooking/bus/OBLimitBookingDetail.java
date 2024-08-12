package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;
import java.util.List;

/**
 * Data model holds a limit booking common details.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBLimitBookingDetail implements ILimitBookingDetail {    
   
    private long lmtBookDtlID=ICMSConstant.LONG_INVALID_VALUE;
    private long limitBookingID=ICMSConstant.LONG_INVALID_VALUE;
	
	private String bkgType;                        //MGEL/ENTITY/POL/COUNTRY
	private String bkgTypeCode;                    //grp_id/ sub profile id/ entry_code of POL/ ctry code
    private String bkgTypeDesc;                    //grp_name/ bk_name/ entry_desc of POL/ country name
	
	private String bkgSubType;                        //CONV/ISM/INV
	private String bkgSubTypeCode;                    //GRP_SUBLIMIT_ID
    private String bkgSubTypeDesc;                    //
	
	private Amount bkgBaseAmount;                      //always in MYR
    private Amount bkgAmount;                      //your bookin
    private Amount preBookedAmount;                //your bookin
    private Amount limitAmount;                    //credit risk param set at pre booked time, MGEL amount for Grp
    private Amount currentExposure;                //get exposure from exposure tables
    private Amount totalBookedAmount;              //booked + PB amount in this table (based on BK_STATUS in CMS_lmt_book table) exclude current one
    private String bkgResult;                      // pass/fail
    		
	private String status;	
	private long versionTime;
    private long cmsRef=ICMSConstant.LONG_INVALID_VALUE;
    
    private String bkgProdTypeCode;                       
	private String bkgProdTypeDesc; 

	//contains object of ILimitBooking
	private List bookedLimitList;
    
	private boolean grpIsRetrieved;

	
   /**
     * Default Constructor.
     */
    public OBLimitBookingDetail() {
        super();
    }


    /**
     * Construct the object from its interface.
     *
     * @param obj is of type ILoanSectorDetail
     */
    public OBLimitBookingDetail (ILimitBookingDetail obj) {
        this();
        AccessorUtil.copyValue (obj, this);

    }      
	
	   
    public long getLmtBookDtlID() {
        return lmtBookDtlID;
    }

    public void setLmtBookDtlID(long lmtBookDtlID) {
        this.lmtBookDtlID = lmtBookDtlID;
    }
	
	public long getLimitBookingID() {
        return limitBookingID;
    }

    public void setLimitBookingID(long limitBookingID) {
        this.limitBookingID = limitBookingID;
    }
	
	public String getBkgType() {
        return bkgType;
    }

    public void setBkgType(String bkgType) {
        this.bkgType = bkgType;
    }

	public String getBkgTypeCode() {
        return bkgTypeCode;
    }

    public void setBkgTypeCode(String bkgTypeCode) {
        this.bkgTypeCode = bkgTypeCode;
    }

    public String getBkgTypeDesc() {
        return bkgTypeDesc;
    }

    public void setBkgTypeDesc(String bkgTypeDesc) {
        this.bkgTypeDesc = bkgTypeDesc;
    }
    
    public String getBkgProdTypeCode() {
		return bkgProdTypeCode;
	}


	public void setBkgProdTypeCode(String bkgProdTypeCode) {
		this.bkgProdTypeCode = bkgProdTypeCode;
	}


	public String getBkgProdTypeDesc() {
		return bkgProdTypeDesc;
	}


	public void setBkgProdTypeDesc(String bkgProdTypeDesc) {
		this.bkgProdTypeDesc = bkgProdTypeDesc;
	}
	
	public String getBkgSubType() {
        return bkgSubType;
    }

    public void setBkgSubType(String bkgSubType) {
        this.bkgSubType = bkgSubType;
    }

	public String getBkgSubTypeCode() {
        return bkgSubTypeCode;
    }

    public void setBkgSubTypeCode(String bkgSubTypeCode) {
        this.bkgSubTypeCode = bkgSubTypeCode;
    }

    public String getBkgSubTypeDesc() {
        return bkgSubTypeDesc;
    }

    public void setBkgSubTypeDesc(String bkgSubTypeDesc) {
        this.bkgSubTypeDesc = bkgSubTypeDesc;
    }
	
    public Amount getBkgBaseAmount() {
        return bkgBaseAmount;
    }

    public void setBkgBaseAmount(Amount bkgBaseAmount) {
        this.bkgBaseAmount = bkgBaseAmount;
    }
	
	public Amount getBkgAmount() {
        return bkgAmount;
    }

    public void setBkgAmount(Amount bkgAmount) {
        this.bkgAmount = bkgAmount;
    }
	
    public Amount getPreBookedAmount() {
        return preBookedAmount;
    }

    public void setPreBookedAmount(Amount preBookedAmount) {
        this.preBookedAmount = preBookedAmount;
    }

    public Amount getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Amount limitAmount) {
        this.limitAmount = limitAmount;
    }

    public Amount getCurrentExposure() {
        return currentExposure;
    }

    public void setCurrentExposure(Amount currentExposure) {
        this.currentExposure = currentExposure;
    }

    public Amount getTotalBookedAmount() {
        return totalBookedAmount;
    }

    public void setTotalBookedAmount(Amount totalBookedAmount) {
        this.totalBookedAmount = totalBookedAmount;
    }

    public String getBkgResult() {
        return bkgResult;
    }

    public void setBkgResult(String bkgResult) {
        this.bkgResult = bkgResult;
    }
	
	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
	
	public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }

    public long getCmsRef() {
        return cmsRef;
    }

    public void setCmsRef(long cmsRef) {
        this.cmsRef = cmsRef;
    }

	public List getBookedLimitList() {
        return bookedLimitList;
    }

    public void setBookedLimitList(List bookedLimitList) {
        this.bookedLimitList = bookedLimitList;
    }
	
	public boolean getGrpIsRetrieved() {
        return grpIsRetrieved;
    }

    public void setGrpIsRetrieved(boolean grpIsRetrieved) {
        this.grpIsRetrieved = grpIsRetrieved;
    } 
	/**
     * Return a String representation of this object.
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue (this);
    }

    /**
     * Test for equality.
     *
     * @param obj is of type Object
     * @return boolean
     */
    public boolean equals (Object obj)
    {
        if (obj == null)
            return false;
        else if (!(obj instanceof OBLimitBookingDetail))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
