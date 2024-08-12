/*
* Copyright Integro Technologies Pte Ltd
* $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBGroupSearchResult.java,v 1.2 2003/09/04 09:22:35 lakshman Exp $
*/
package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.Date;
import java.io.Serializable;

/**
* This interface represents a limit booking search result data.
*
* @author $Author $
* @version $Revision: 1.2 $
* @since $Date: 2003/09/04 09:22:35 $
* Tag: $Name:  $
*/
public class LimitBookingSearchResult  implements Serializable {

    private String ticketNo;
    private String customerName;
    private String sourceSystem;
    private String aaNumber;
    private String country;
    private String businessUnit;
    private String groupName;
    private Amount bookedAmt;
    private String overallBkgResult;
    private Date dateLimitBooked;
    private String lastUpdatedBy;
    private String bkgStatus;
    private Date expiryDate;
    private boolean isEditable;

    private long limitBookingID= ICMSConstant.LONG_INVALID_VALUE;


    public long getLimitBookingID() {
        return limitBookingID;
    }

    public void setLimitBookingID(long limitBookingID) {
        this.limitBookingID = limitBookingID;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getAaNumber() {
        return aaNumber;
    }

    public void setAaNumber(String aaNumber) {
        this.aaNumber = aaNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(String businessUnit) {
        this.businessUnit = businessUnit;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Amount getBookedAmt() {
        return bookedAmt;
    }

    public void setBookedAmt(Amount bookedAmt) {
        this.bookedAmt = bookedAmt;
    }

    public String getOverallBkgResult() {
        return overallBkgResult;
    }

    public void setOverallBkgResult(String overallBkgResult) {
        this.overallBkgResult = overallBkgResult;
    }

    public Date getDateLimitBooked() {
        return dateLimitBooked;
    }

    public void setDateLimitBooked(Date dateLimitBooked) {
        this.dateLimitBooked = dateLimitBooked;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
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

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }
}