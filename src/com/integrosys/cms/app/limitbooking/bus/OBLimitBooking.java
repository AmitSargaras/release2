package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

import java.util.Date;
import java.util.List;

/**
 * Data model holds a limit booking. 
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBLimitBooking implements ILimitBooking {

    private long versionTime;
    private long limitBookingID=ICMSConstant.LONG_INVALID_VALUE;
    private long cmsRef=ICMSConstant.LONG_INVALID_VALUE;
	
	private Long subProfileID;
	
	private String ticketNo;
	//AA Number 
    private String aaNo;
	//Source System
    private String aaSource;
	//Customer Name
    private String bkgName;
	//Customer ID
    private String bkgIDNo;
	//Customer Business Sector 
	private String bkgBusSector;
	//Business Unit
	private String bkgBusUnit;
	//Bank Entity
	private String bkgBankEntity;
	
	//New Booking Amount
    private Amount bkgAmount;
	//Country Risk
    private String bkgCountry;
	
    private String bkgStatus;
    private String overallBkgResult;
    private Date expiryDate;
	private Date createDate;
	
	private String isExistingCustomer;
    private String isFinancialInstitution;
	private String lastModifiedBy;
	
	//contains object of ILoanSectorDetail
    private List loanSectorList;
	//contains object of IBankGroupDetail
	private List bankGroupList;
	
	//all limit booking result - contains object of ILimitBookingDetail
	private List allBkgs;
	    

    public long getLimitBookingID() {
        return limitBookingID;
    }

    public void setLimitBookingID(long limitBookingID) {
        this.limitBookingID = limitBookingID;
    }
    

    public long getCmsRef() {
        return cmsRef;
    }

    public void setCmsRef(long cmsRef) {
        this.cmsRef = cmsRef;
    }

	public Long getSubProfileID() {
        return subProfileID;
    }

    public void setSubProfileID(Long value) {
        this.subProfileID = value;
    }
	
    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getAaNo() {
        return aaNo;
    }

    public void setAaNo(String aaNo) {
        this.aaNo = aaNo;
    }

    public String getAaSource() {
        return aaSource;
    }

    public void setAaSource(String aaSource) {
        this.aaSource = aaSource;
    }

    public String getBkgName() {
        return bkgName;
    }

    public void setBkgName(String bkgName) {
        this.bkgName = bkgName;
    }

    public String getBkgIDNo() {
        return bkgIDNo;
    }

    public void setBkgIDNo(String bkgIDNo) {
        this.bkgIDNo = bkgIDNo;
    }
   
    public Amount getBkgAmount() {
        return bkgAmount;
    }

    public void setBkgAmount(Amount bkgAmount) {
        this.bkgAmount = bkgAmount;
    }

    public String getBkgCountry() {
        return bkgCountry;
    }

    public void setBkgCountry(String bkgCountry) {
        this.bkgCountry = bkgCountry;
    }

    public String getBkgBusUnit() {
        return bkgBusUnit;
    }

    public void setBkgBusUnit(String bkgBusUnit) {
        this.bkgBusUnit = bkgBusUnit;
    }
	
	public String getBkgBusSector() {
        return bkgBusSector;
    }

    public void setBkgBusSector(String bkgBusSector) {
        this.bkgBusSector = bkgBusSector;
    }
	
	public String getBkgBankEntity() {
        return bkgBankEntity;
    }

    public void setBkgBankEntity(String bkgBankEntity) {
        this.bkgBankEntity = bkgBankEntity;
    }
	
    public String getBkgStatus() {
        return bkgStatus;
    }

    public void setBkgStatus(String bkgStatus) {
        this.bkgStatus = bkgStatus;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
	
	public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
	
	public String getIsExistingCustomer() {
        return isExistingCustomer;
    }

    public void setIsExistingCustomer(String existingCustomer) {
        isExistingCustomer = existingCustomer;
    }

    public String getIsFinancialInstitution() {
        return isFinancialInstitution;
    }

    public void setIsFinancialInstitution(String isFnancialInstitution) {
        isFinancialInstitution = isFnancialInstitution;
    }
	public List getLoanSectorList() {
        return loanSectorList;
    }

    public void setLoanSectorList(List loanSectorList) {
        this.loanSectorList = loanSectorList;
    }

	public List getBankGroupList() {
        return bankGroupList;
    }

    public void setBankGroupList(List bankGroupList) {
        this.bankGroupList = bankGroupList;
    }

	
    public String getOverallBkgResult() {
        return overallBkgResult;
    }

    public void setOverallBkgResult(String overallBkgResult) {
        this.overallBkgResult = overallBkgResult;
    }

    public long getVersionTime() {
        return versionTime;
    }

    public void setVersionTime(long versionTime) {
        this.versionTime = versionTime;
    }
	
	public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String value) {
        this.lastModifiedBy = value;
    }	
	
	public List getAllBkgs() {
        return allBkgs;
    }

    public void setAllBkgs(List allBkgs) {
        this.allBkgs = allBkgs;
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
        else if (!(obj instanceof OBLimitBooking))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
