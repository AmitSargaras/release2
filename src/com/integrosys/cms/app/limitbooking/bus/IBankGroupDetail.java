package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * This interface represents a Bank Group details.
 *
 * @author   $Author: Pctan $<br>
 * @version  $Revision: $
 * @since    $Date: $
 * Tag:      $Name: $
 */
public interface IBankGroupDetail extends ILimitBookingDetail{

    public boolean getGrpIsRetrieved();
    public void setGrpIsRetrieved(boolean isRetrieved) ;    

    public Amount getLimitConvAmount() ;
    public void setLimitConvAmount(Amount limitConvAmount) ;

	public Amount getLimitIslamAmount() ;
    public void setLimitIslamAmount(Amount limitIslamAmount) ;

	public Amount getLimitInvAmount() ;
    public void setLimitInvAmount(Amount limitInvAmount) ;


}
