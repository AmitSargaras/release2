/*
Copyright Integro Technologies Pte Ltd
*/

package com.integrosys.cms.ui.limitbooking;

import com.integrosys.cms.ui.custgrpi.CustGroupUIHelper;
import com.integrosys.cms.ui.custgrpi.groupmember.GroupMemberForm;
import com.integrosys.base.techinfra.util.AccessorUtil;
import java.io.Serializable;

/**
 * @author priya
 *
 */

public class LimitBookingForm extends GroupMemberForm implements Serializable {
		
	private static final long serialVersionUID = 1L;
	
	private long limitBookingID;
    private String aaNo;
    private String aaSource;
    private String bkgName;
    private String bkgIDNo;
    private String bkgCurrency;
    private String bkgAmount;
    private String bkgCountry;
    private String bkgBusUnit;
    private String isExistingCustomer;
    private String isFinancialInstitution;
    private String bkgBusSector;
    private String bkgBankEntity;

    //  For Search Group
    private String searchGroupName;
    private String searchGroupID;
    private String searchGroupNo;
    private String searchType;
    private String gobutton;
    private String[] entityCheckBoxID;

    // For Search Booking
    private String searchFromDate;
    private String searchToDate;
    private String searchTicketNo;
    private String searchBookingGroupName;
    private String searchCustomerName;
    private String searchIDNo;
    private String searchBookingType;
    private String gobuttonBooking;
    private String[] bookingCheckBoxID;

    
    // For Economic Sector
    private String pol;
    private String polBkgDesc;
    private String polBkgCurrency;
    private String polBkgAmount;
    private String[] polDeletedList;
    
    private String prodType;
    private String prodTypeDesc;


	// For Bank Group
    private String[] bankGroupDeletedList;

    public String getPolBkgAmount() {
        return polBkgAmount;
    }

    public void setPolBkgAmount(String polBkgAmount) {
        this.polBkgAmount = polBkgAmount;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }
    
    public String getPolBkgDesc() {
		return polBkgDesc;
	}

	public void setPolBkgDesc(String polBkgDesc) {
		this.polBkgDesc = polBkgDesc;
	}

    public String getPolBkgCurrency() {
        return polBkgCurrency;
    }

    public void setPolBkgCurrency(String polBkgCurrency) {
        this.polBkgCurrency = polBkgCurrency;
    }
    
    public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	
	public String getProdTypeDesc() {
		return prodTypeDesc;
	}

	public void setProdTypeDesc(String prodTypeDesc) {
		this.prodTypeDesc = prodTypeDesc;
	}
    
    public String getGobutton() {
        return gobutton;
    }

    public void setGobutton(String gobutton) {
        this.gobutton = gobutton;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchGroupName() {
        return searchGroupName;
    }

    public void setSearchGroupName(String searchGroupName) {
        this.searchGroupName = searchGroupName;
    }

    public String getSearchGroupID() {
        return searchGroupID;
    }

    public void setSearchGroupID(String searchGroupID) {
        this.searchGroupID = searchGroupID;
    }

    public String getSearchGroupNo() {
        return searchGroupNo;
    }

    public void setSearchGroupNo(String searchGroupNo) {
        this.searchGroupNo = searchGroupNo;
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

    public void setIsFinancialInstitution(String financialInstitution) {
        isFinancialInstitution = financialInstitution;
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

    public String getBkgCurrency() {
        return bkgCurrency;
    }

    public void setBkgCurrency(String bkgCurrency) {
        this.bkgCurrency = bkgCurrency;
    }

    public String getBkgAmount() {
        return bkgAmount;
    }

    public void setBkgAmount(String bkgAmount) {
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

    public long getLimitBookingID() {
        return limitBookingID;
    }

    public void setLimitBookingID(long limitBookingID) {
        this.limitBookingID = limitBookingID;
    }
    
    public String getBkgBankEntity() {
		return bkgBankEntity;
	}

	public void setBkgBankEntity(String bkgBankEntity) {
		this.bkgBankEntity = bkgBankEntity;
	}

	public String getBkgBusSector() {
		return bkgBusSector;
	}

	public void setBkgBusSector(String bkgBusSector) {
		this.bkgBusSector = bkgBusSector;
	}
	
	public String[] getBankGroupDeletedList() {
		return bankGroupDeletedList;
	}

	public void setBankGroupDeletedList(String[] bankEntityDeletedList) {
		this.bankGroupDeletedList = bankEntityDeletedList;
	}

	public String[] getBookingCheckBoxID() {
		return bookingCheckBoxID;
	}

	public void setBookingCheckBoxID(String[] bookingCheckBoxID) {
		this.bookingCheckBoxID = bookingCheckBoxID;
	}

	public String getGobuttonBooking() {
		return gobuttonBooking;
	}

	public void setGobuttonBooking(String gobuttonBooking) {
		this.gobuttonBooking = gobuttonBooking;
	}

	public String getSearchBookingGroupName() {
		return searchBookingGroupName;
	}

	public void setSearchBookingGroupName(String searchBookingGroupName) {
		this.searchBookingGroupName = searchBookingGroupName;
	}

	public String getSearchBookingType() {
		return searchBookingType;
	}

	public void setSearchBookingType(String searchBookingType) {
		this.searchBookingType = searchBookingType;
	}

	public String getSearchCustomerName() {
		return searchCustomerName;
	}

	public void setSearchCustomerName(String searchCustomerName) {
		this.searchCustomerName = searchCustomerName;
	}

	public String getSearchFromDate() {
		return searchFromDate;
	}

	public void setSearchFromDate(String searchFromDate) {
		this.searchFromDate = searchFromDate;
	}

	public String getSearchIDNo() {
		return searchIDNo;
	}

	public void setSearchIDNo(String searchIDNo) {
		this.searchIDNo = searchIDNo;
	}

	public String getSearchTicketNo() {
		return searchTicketNo;
	}

	public void setSearchTicketNo(String searchTicketNo) {
		this.searchTicketNo = searchTicketNo;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	
	public String[] getPolDeletedList() {
		return polDeletedList;
	}

	public void setPolDeletedList(String[] polDeletedList) {
		this.polDeletedList = polDeletedList;
	}
	
	public String[] getEntityCheckBoxID() {
		return entityCheckBoxID;
	}

	public void setEntityCheckBoxID(String[] entityCheckBoxID) {
		this.entityCheckBoxID = entityCheckBoxID;
	}

    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }

    public String[][] getMapper() {
        String[][] input = {
                {CustGroupUIHelper.form_EntitySelectedIDMapper, "com.integrosys.cms.ui.custgrpi.groupmember.EntitySelectedIDMapper"},
                {LimitBookingAction.SEARCH_CRITERIA,"com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {LimitBookingAction.BOOKING_SEARCH_CRITERIA,"com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {LimitBookingAction.LIMIT_BOOKING,"com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {LimitBookingAction.LIMIT_BOOKING_DETAIL,"com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {LimitBookingAction.BOOKING_SEARCH_RESULT,"com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {"sess.trxValue","com.integrosys.cms.ui.limitbooking.LimitBookingMapper"},
                {"theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper"}
            };
        return input;
    }

}
