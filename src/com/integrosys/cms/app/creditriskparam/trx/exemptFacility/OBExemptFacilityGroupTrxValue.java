/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/trx/forex/OBExemptFacilityGroupTrxValue.java,v 1.5 2003/08/06 05:42:09 btchng Exp $
 */

package com.integrosys.cms.app.creditriskparam.trx.exemptFacility;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.OBCMSTrxValue;

public class OBExemptFacilityGroupTrxValue
        extends OBCMSTrxValue
        implements IExemptFacilityGroupTrxValue {

    /**
     * Get the IExemptFacilityGroup busines entity
     *
     * @return IExemptFacilityGroup
     */
    public IExemptFacilityGroup getExemptFacilityGroup() {
        return actual;
    }


    /**
     * Construct the object based on its parent info
     * @param anICMSTrxValue - ICMSTrxValue
     */
    public OBExemptFacilityGroupTrxValue(ICMSTrxValue anICMSTrxValue) {
        AccessorUtil.copyValue(anICMSTrxValue, this);
    }


    /**
     * Default constructor.
     */
    public OBExemptFacilityGroupTrxValue() {
        // Follow "limit".
        //super.setTransactionType(ICMSConstant.INSTANCE_EXEMPT_FACILITY_GROUP);
    }


    /**
     * Get the staging IExemptFacilityGroup business entity
     *
     * @return ICheckList
     */
    public IExemptFacilityGroup getStagingExemptFacilityGroup() {
        return staging;
    }


    /**
     * Set the IExemptFacilityGroup busines entity
     *
     * @param value is of type IExemptFacilityGroup
     */
    public void setExemptFacilityGroup(IExemptFacilityGroup value) {
        actual = value;
    }


    /**
     * Set the staging IExemptFacilityGroup business entity
     *
     * @param value is of type IExemptFacilityGroup
     */
    public void setStagingExemptFacilityGroup(IExemptFacilityGroup value) {
        staging = value;
    }


    private IExemptFacilityGroup actual;

    private IExemptFacilityGroup staging;
}
