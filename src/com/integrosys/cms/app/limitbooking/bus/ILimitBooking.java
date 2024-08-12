package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * This interface represents a limit booking.
 *
 * @author   $Author: Pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface ILimitBooking extends Serializable, IValueObject{

    public long getLimitBookingID() ;
    public void setLimitBookingID(long limitBookingID) ;
   
    public long getCmsRef() ;
    public void setCmsRef(long cmsRef) ;

	public Long getSubProfileID();
    public void setSubProfileID(Long value);
	
	public String getTicketNo();
    public void setTicketNo(String ticketNo);
	
    public String getAaNo() ;
    public void setAaNo(String aaNo) ;

    public String getAaSource() ;
    public void setAaSource(String aaSource) ;

    public String getBkgName() ;
    public void setBkgName(String bkgName) ;

    public String getBkgIDNo() ;
    public void setBkgIDNo(String bkgIdNo) ;
    
    public Amount getBkgAmount() ;
    public void setBkgAmount(Amount bkgAmount) ;

    public String getBkgCountry() ;
    public void setBkgCountry(String bkgCountry) ;

    public String getBkgBusUnit();
    public void setBkgBusUnit(String bkgBusUnit) ;

	public String getBkgBusSector();
    public void setBkgBusSector(String bkgBusSector);
	
	public String getBkgBankEntity();
    public void setBkgBankEntity(String bkgBankEntity);
	
    public String getBkgStatus() ;
    public void setBkgStatus(String bkgStatus) ;
	
	public String getOverallBkgResult();
    public void setOverallBkgResult(String overallBkgResult);
	
    public Date getExpiryDate() ;
    public void setExpiryDate(Date expiryDate) ;
	
	public Date getCreateDate() ;
    public void setCreateDate(Date value) ;	
	
	public String getIsExistingCustomer();
    public void setIsExistingCustomer(String existingCustomer);

    public String getIsFinancialInstitution();
    public void setIsFinancialInstitution(String isFnancialInstitution);
	
    public List getLoanSectorList();
    public void setLoanSectorList(List loanSectorList);

	public List getBankGroupList();
    public void setBankGroupList(List bankGroupList);
	
	public List getAllBkgs();
    public void setAllBkgs(List allBkgs);   
	
	public String getLastModifiedBy();
    public void setLastModifiedBy(String value);
}
