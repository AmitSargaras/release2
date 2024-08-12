/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/SBExemptFacilityBusManager.java,v 1.1 2003/08/11 04:08:19 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;


import javax.ejb.EJBObject;
import java.rmi.RemoteException;

/**
 * @author $Author$<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/11 04:08:19 $
 * Tag: $Name$
 */
public interface SBExemptFacilityBusManager extends EJBObject {


    /**
           * Gets all the Exempted Facility in actual table.
           *
           * @return IExemptFacilityGroup
           * @throws ExemptFacilityException on errors encountered
           */
      public IExemptFacilityGroup getExemptFacilityGroup ()
          throws ExemptFacilityException, RemoteException;

      /**
           * Gets list of Exempted Facility by group ID.
           *
           * @param groupID group ID
           * @return IExemptFacilityGroup
           * @throws ExemptFacilityException on errors encountered
           */
      public IExemptFacilityGroup getExemptFacilityGroupByGroupID ( long groupID )
          throws ExemptFacilityException, RemoteException;

      /**
           * Creates the input list of Exempted Facility.
           *
           * @param value of type IExemptFacilityGroup
           * @return IExemptFacilityGroup
           * @throws ExemptFacilityException on errors encountered
           */
      public IExemptFacilityGroup createExemptFacilityGroup (IExemptFacilityGroup value)
          throws ExemptFacilityException, RemoteException;

     /**
           * Updates the input list of Exempted Facility.
           *
           * @param value of type IExemptFacilityGroup
           * @return IExemptFacilityGroup
           * @throws ExemptFacilityException on errors encountered
           */
      public IExemptFacilityGroup updateExemptFacilityGroup (IExemptFacilityGroup value)
          throws ExemptFacilityException, RemoteException;


}
