/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/IExemptFacilityBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

import java.io.Serializable;
import java.util.ArrayList;

/**
NOT USED
 */
public interface IExemptFacilityBusManager
        extends Serializable {

//    public IExemptFacilityGroup getExemptFacility(IExemptFacilityGroup exemptFacilityGroup) throws ExemptFacilityException;

//    public IExemptFacilityGroup getStagingExemptFacilityGroup() throws ExemptFacilityException;

    IExemptFacilityGroup getExemptFacilityGroup() throws ExemptFacilityException;

    IExemptFacilityGroup createExemptFacilityGroup (IExemptFacilityGroup liq)
        throws ExemptFacilityException;

    IExemptFacilityGroup updateExemptFacilityGroup(IExemptFacilityGroup value)
        throws ExemptFacilityException;

    IExemptFacilityGroup getExemptedFacilityGroupByGroupID ( long groupID )
        throws ExemptFacilityException;
//    public IExemptFacilityGroup updateExemptFacilityGroup(IExemptFacilityGroup paramGroup) throws ExemptFacilityException, ConcurrentUpdateException;

//    public ArrayList getExemptFacility() throws ExemptFacilityException ;

/*
    public IExemptFacilityGroup getExemptFacility(long id)
            throws ExemptFacilityException;

    public IExemptFacilityGroup getExemptFacility(String groupType)
            throws ExemptFacilityException;

    public IExemptFacilityGroup createExemptFacility(IExemptFacilityGroup group)
            throws ExemptFacilityException;

    public IExemptFacilityGroup updateExemptFacility(IExemptFacilityGroup group)
            throws ExemptFacilityException;

    public IExemptFacilityGroup deleteExemptFacility(IExemptFacilityGroup group)
            throws ExemptFacilityException;

*/
}
