package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;

/**
 * Data model holds a Bank Group details. This is subclass of limit booking details.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBBankGroupDetail extends OBLimitBookingDetail implements IBankGroupDetail {
       	
    private Amount limitConvAmount;                  
    private Amount limitIslamAmount;              
    private Amount limitInvAmount;            
    private boolean grpIsRetrieved;

	/**
     * Default Constructor.
     */
    public OBBankGroupDetail() {
        super();
    }


    /**
     * Construct the object from its interface.
     *
     * @param obj is of type ILimitBookingDetail
     */
    public OBBankGroupDetail (ILimitBookingDetail obj) {
        this();
        AccessorUtil.copyValue (obj, this);

    }

	 /**
     * Construct the object from its interface.
     *
     * @param obj is of type IBankGroupDetail
     */
    public OBBankGroupDetail (IBankGroupDetail obj) {
        this();
        AccessorUtil.copyValue (obj, this);

    }
	
    public boolean getGrpIsRetrieved() {
        return grpIsRetrieved;
    }

    public void setGrpIsRetrieved(boolean grpIsRetrieved) {
        this.grpIsRetrieved = grpIsRetrieved;
    } 
    
    public Amount getLimitConvAmount() {
        return limitConvAmount;
    }

    public void setLimitConvAmount(Amount limitConvAmount) {
        this.limitConvAmount = limitConvAmount;
    }

	 public Amount getLimitIslamAmount() {
        return limitIslamAmount;
    }

    public void setLimitIslamAmount(Amount limitIslamAmount) {
        this.limitIslamAmount = limitIslamAmount;
    }
	
	 public Amount getLimitInvAmount() {
        return limitInvAmount;
    }

    public void setLimitInvAmount(Amount limitInvAmount) {
        this.limitInvAmount = limitInvAmount;
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
        else if (!(obj instanceof OBBankGroupDetail))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
}
