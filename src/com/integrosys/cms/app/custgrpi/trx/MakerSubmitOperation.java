package com.integrosys.cms.app.custgrpi.trx;

import com.integrosys.cms.app.common.constant.ICMSConstant;


public class MakerSubmitOperation
        extends MakerUpdateOperation {

    /**
     * Defaulc Constructor
     */
    public MakerSubmitOperation() {
        super();

    }


    /**
     * Get the operation name of the current operation
     *
     * @return String - the operation name of the current operation
     */
    public String getOperationName() {
        return ICMSConstant.ACTION_MAKER_SUBMIT_CUST_GRP_IDENTIFIER;
    }

}