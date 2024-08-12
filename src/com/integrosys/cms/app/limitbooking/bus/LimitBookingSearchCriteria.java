package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.Date;

public class LimitBookingSearchCriteria extends SearchCriteria {

    public LimitBookingSearchCriteria()    {}

    private String gobuttonBooking;
    private String[] bookingCheckBoxID;

    private Date searchFromDate;
    private Date searchToDate;
    private String ticketNo;
    private String customerName;
    private String groupName = "";
    private String idNo = "";
    private String searchType = "";

    private ITrxContext ctx;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getGobuttonBooking() {
        return gobuttonBooking;
    }

    public void setGobuttonBooking(String gobuttonBooking) {
        this.gobuttonBooking = gobuttonBooking;
    }

    public String[] getBookingCheckBoxID() {
        return bookingCheckBoxID;
    }

    public void setBookingCheckBoxID(String[] bookingCheckBoxID) {
        this.bookingCheckBoxID = bookingCheckBoxID;
    }

    public Date getSearchFromDate() {
        return searchFromDate;
    }

    public void setSearchFromDate(Date searchFromDate) {
        this.searchFromDate = searchFromDate;
    }

    public Date getSearchToDate() {
        return searchToDate;
    }

    public void setSearchToDate(Date searchToDate) {
        this.searchToDate = searchToDate;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public ITrxContext getCtx() {
        return ctx;
    }

    public void setCtx(ITrxContext ctx) {
        this.ctx = ctx;
    }

}
