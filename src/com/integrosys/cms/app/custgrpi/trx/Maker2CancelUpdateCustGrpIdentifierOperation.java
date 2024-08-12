package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;

/**
 * This operation class is invoked by a checker to approve Counterparty Details updated by a maker.
 *
 * @author $Author: Jitu<br>
 * @version $Revision: $
 * @since $Date: $
 *        Tag:      $Name: $
 */
public class Maker2CancelUpdateCustGrpIdentifierOperation extends MakerCancelUpdateCustGrpIdentifierOperation {
    /**
     * Default constructor.
     */
    public Maker2CancelUpdateCustGrpIdentifierOperation() {
    }

    /**
     * Returns the Operation Name
     *
     * @return String
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER2_CANCEL;
    }


}
