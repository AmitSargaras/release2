package com.integrosys.cms.app.limitbooking.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.currency.Amount;

/**
 * Data model holds a Loan Sector details. This is subclass of limit booking details.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public class OBLoanSectorDetail extends OBLimitBookingDetail implements ILoanSectorDetail {
    

	/**
     * Default Constructor.
     */
    public OBLoanSectorDetail() {
        super();
    }


    /**
     * Construct the object from its interface.
     *
     * @param obj is of type ILimitBookingDetail
     */
    public OBLoanSectorDetail (ILimitBookingDetail obj) {
        this();
        AccessorUtil.copyValue (obj, this);

    }
	
	/**
     * Construct the object from its interface.
     *
     * @param obj is of type ILoanSectorDetail
     */
    public OBLoanSectorDetail (ILoanSectorDetail obj) {
        this();
        AccessorUtil.copyValue (obj, this);

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
        else if (!(obj instanceof OBLoanSectorDetail))
            return false;
        else {
            if (obj.hashCode() == this.hashCode())
                return true;
            else
                return false;
        }
    }
   

    
}
