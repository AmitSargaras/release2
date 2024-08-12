package com.integrosys.cms.app.limitbooking.trx;

import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.limitbooking.bus.ILimitBooking;

/**
 * This interface represents a Limit Booking trx value.
 *
 * @author $Author$
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/06 05:42:09 $
 * Tag: $Name:  $
 */
public interface ILimitBookingTrxValue extends ICMSTrxValue {

    public ILimitBooking getLimitBooking();

    public ILimitBooking getStagingLimitBooking();

    public void setLimitBooking(ILimitBooking value);

    public void setStagingLimitBooking(ILimitBooking value);
}