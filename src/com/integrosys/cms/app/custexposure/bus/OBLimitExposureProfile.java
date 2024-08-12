package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
* This interface represents an Limit Profile.
*
* @author $Author: czhou $
* @version $Revision: 1.25 $
* @since $Date: 2006/10/25 08:05:48 $
* Tag: $Name:  $
*/
public class OBLimitExposureProfile implements ILimitExposureProfile {

	private static final long serialVersionUID = 1L;
	private ILimitProfile    limitProfile  = null;
    private ICMSCustomer cMSCustomer ;

    private long entityID = ICMSConstant.LONG_INVALID_VALUE;


    public long getEntityID() {
        return entityID;
    }

    public void setEntityID(long entityID) {
        this.entityID = entityID;
    }


    public ILimitProfile getLimitProfile() {
        return limitProfile;
    }

    public void setLimitProfile(ILimitProfile limitProfile) {
        this.limitProfile = limitProfile;
    }

    public ICMSCustomer getCMSCustomer() {
        return cMSCustomer;
    }

    public void setCMSCustomer(ICMSCustomer cMSCustomer) {
        this.cMSCustomer = cMSCustomer;
    }

    /**
     * Return a String representation of the object
     *
     * @return String
     */
     public String toString() {
         return AccessorUtil.printMethodValue(this);
     }

}
