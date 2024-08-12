package com.integrosys.cms.app.cci.trx;

import com.integrosys.cms.app.cci.bus.ICCICounterpartyDetails;
import com.integrosys.cms.app.cci.bus.OBCCICounterpartyDetails;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 11:14:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CCICounterpartyDetailsHelper {


    public static ICCICounterpartyDetails initialCounterpartyDetails(long groupCCINo) {

        ICCICounterpartyDetails obj = new OBCCICounterpartyDetails();
        obj.setGroupCCINo(groupCCINo);
        obj.setICCICounterparty(null);
        return obj;
    }
}
