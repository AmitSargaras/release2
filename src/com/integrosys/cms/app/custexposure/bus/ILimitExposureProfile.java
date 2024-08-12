package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;

/**
* This interface represents an Limit Profile.
*
* @author $Author: czhou $
* @version $Revision: 1.25 $
* @since $Date: 2006/10/25 08:05:48 $
* Tag: $Name:  $
*/
public interface ILimitExposureProfile extends java.io.Serializable {

    public ILimitProfile getLimitProfile();
    public void setLimitProfile(ILimitProfile limitProfile);

    public ICMSCustomer getCMSCustomer() ;
    public void setCMSCustomer(ICMSCustomer cMSCustomer) ;

    public long getEntityID() ;
    public void setEntityID(long entityID) ;


}
