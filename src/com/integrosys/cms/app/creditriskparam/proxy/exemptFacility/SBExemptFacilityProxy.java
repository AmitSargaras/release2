/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/proxy/forex/SBExemptFacilityProxy.java,v 1.6 2003/09/12 09:24:00 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.proxy.exemptFacility;

import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.ExemptFacilityException;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.transaction.ITrxContext;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * @author $Author: btchng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/09/12 09:24:00 $
 * Tag: $Name:  $
 */
public interface SBExemptFacilityProxy extends EJBObject {

    IExemptFacilityGroupTrxValue getExemptFacilityTrxValue(ITrxContext anITrxContext)
            throws RemoteException,  ExemptFacilityException;

    IExemptFacilityGroupTrxValue getExemptFacilityTrxValueByTrxID(ITrxContext ctx, String trxID)
            throws RemoteException,  ExemptFacilityException;

    IExemptFacilityGroupTrxValue getExemptFacilityGroup(ITrxContext anITrxContext,long groupID)
            throws RemoteException,  ExemptFacilityException;


    IExemptFacilityGroupTrxValue makerUpdateExemptFacility(ITrxContext anITrxContext, IExemptFacilityGroupTrxValue aTrxValue,
            IExemptFacilityGroup aFeedGroup)
            throws RemoteException,  ExemptFacilityException;

    IExemptFacilityGroupTrxValue makerCloseExemptFacility(ITrxContext anITrxContext, IExemptFacilityGroupTrxValue aTrxValue)
            throws RemoteException,  ExemptFacilityException;


    IExemptFacilityGroupTrxValue checkerApproveExemptFacility(ITrxContext anITrxContext,
            IExemptFacilityGroupTrxValue aTrxValue)
            throws RemoteException,  ExemptFacilityException;


    IExemptFacilityGroupTrxValue checkerRejectExemptFacility(ITrxContext anITrxContext,
            IExemptFacilityGroupTrxValue aTrxValue)
            throws RemoteException,  ExemptFacilityException;

}
