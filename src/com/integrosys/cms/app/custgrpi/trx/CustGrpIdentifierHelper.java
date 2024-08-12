package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.cms.app.custgrpi.bus.ICustGrpIdentifier;
import com.integrosys.cms.app.custgrpi.bus.OBCustGrpIdentifier;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Dec 18, 2007
 * Time: 11:14:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustGrpIdentifierHelper {


    public static ICustGrpIdentifier initialCustGrpIdentifier(long grpId) {
        ICustGrpIdentifier obj = new OBCustGrpIdentifier();
        return obj;
    }
}
