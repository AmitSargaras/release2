package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;
import java.util.List;

/**
 * This interface represents a limit booking common details.
 *
 * @author   $Author: Pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface ILimitBookingDetail extends Serializable, IValueObject{    

    
	public long getLimitBookingID() ;
    public void setLimitBookingID(long limitBookingID) ;
	
    public long getLmtBookDtlID() ;
    public void setLmtBookDtlID(long lmtBookDtlID) ;  

	public String getBkgType() ;
    public void setBkgType(String bkgType) ;
	
    public String getBkgTypeCode() ;
    public void setBkgTypeCode(String bkgTypeCode) ;

    public String getBkgTypeDesc() ;
    public void setBkgTypeDesc(String bkgTypeDesc) ;

	public String getBkgSubType() ;
    public void setBkgSubType(String bkgSubType) ;
	
    public String getBkgSubTypeCode() ;
    public void setBkgSubTypeCode(String bkgSubTypeCode) ;

    public String getBkgSubTypeDesc() ;
    public void setBkgSubTypeDesc(String bkgSubTypeDesc) ;
	
	public Amount getBkgBaseAmount();
    public void setBkgBaseAmount(Amount bkgBaseAmount);
    
    public Amount getBkgAmount() ;
    public void setBkgAmount(Amount bkgAmount) ;

    public Amount getPreBookedAmount() ;
    public void setPreBookedAmount(Amount preBookedAmount) ;

    public Amount getLimitAmount() ;
    public void setLimitAmount(Amount limitAmount) ;

    public Amount getCurrentExposure() ;
    public void setCurrentExposure(Amount currentExposure) ;

    public Amount getTotalBookedAmount() ;
    public void setTotalBookedAmount(Amount totalBookedAmount) ;

    public String getBkgResult() ;
    public void setBkgResult(String bkgResult) ;

	public String getStatus() ;
    public void setStatus(String status) ;
	
	public long getVersionTime() ;
    public void setVersionTime(long versionTime) ;

    public long getCmsRef() ;
    public void setCmsRef(long cmsRef) ;
	
	public List getBookedLimitList();
    public void setBookedLimitList(List bookedLimitList);
	
	public boolean getGrpIsRetrieved();
    public void setGrpIsRetrieved(boolean isRetrieved) ;  
    
    public String getBkgProdTypeCode() ;
    public void setBkgProdTypeCode(String bkgProdTypeCode) ;

    public String getBkgProdTypeDesc() ;
    public void setBkgProdTypeDesc(String bkgProdTypeDesc);
}
