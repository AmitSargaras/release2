/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/IExemptFacilityProxy.java,v 1.9 2003/09/12 09:24:00 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.transaction.ITrxContext;


/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2003/09/12 09:24:00 $
 * Tag: $Name:  $
 */
public interface IExemptFacilityProxy extends java.io.Serializable {


    public IExemptFacilityGroupTrxValue getExemptFacilityTrxValue (ITrxContext ctx)
        throws ExemptFacilityException;

    IExemptFacilityGroupTrxValue getExemptFacilityGroup(ITrxContext ctx, long groupID)
            throws ExemptFacilityException;

    public IExemptFacilityGroupTrxValue getExemptFacilityTrxValueByTrxID (ITrxContext ctx, String trxID)
        throws ExemptFacilityException;

    public IExemptFacilityGroupTrxValue makerUpdateExemptFacility (ITrxContext ctx,
           IExemptFacilityGroupTrxValue trxVal, IExemptFacilityGroup exemptInsts)
    throws ExemptFacilityException;

    public IExemptFacilityGroupTrxValue makerCloseExemptFacility (ITrxContext ctx,
           IExemptFacilityGroupTrxValue trxVal) throws ExemptFacilityException;

    public IExemptFacilityGroupTrxValue checkerApproveExemptFacility (
        ITrxContext ctx, IExemptFacilityGroupTrxValue trxVal)
    throws ExemptFacilityException;

    public IExemptFacilityGroupTrxValue checkerRejectExemptFacility (
        ITrxContext ctx, IExemptFacilityGroupTrxValue trxVal)
    throws ExemptFacilityException;
}
