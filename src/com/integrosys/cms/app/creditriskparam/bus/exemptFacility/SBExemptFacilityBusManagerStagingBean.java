/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/feed/bus/forex/SBExemptFacilityBusManagerStagingBean.java,v 1.2 2003/08/11 06:36:51 btchng Exp $
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;

import javax.ejb.FinderException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 *
 */
public class SBExemptFacilityBusManagerStagingBean
        extends SBExemptFacilityBusManagerBean {



    // added by Jitendra to take all record from the staging including deleted   to show in checker side
    //  on 13th Apr 2008

     public IExemptFacilityGroup getExemptFacilityGroupByGroupID(long groupID) throws ExemptFacilityException {
     try {
            EBExemptFacilityHome ejbHome = getEBExemptFacilityHome();
            Iterator i = ejbHome.findByGroupID ( groupID ).iterator();
            IExemptFacilityGroup grp = null;//new OBExemptFacilityGroup();

            List arrList = new ArrayList();
            if (i.hasNext()){
                arrList = new ArrayList();
                grp = new OBExemptFacilityGroup();
            }
            while (i.hasNext())
            {
                EBExemptFacility theEjb = (EBExemptFacility) i.next();
                IExemptFacility ExemptFacility = theEjb.getValue ();
                arrList.add ( ExemptFacility );
            }
            if (grp != null)
                grp.setExemptFacility((IExemptFacility[])arrList.toArray (new OBExemptFacility[0]));
            return grp;
        }
        catch (FinderException e) {
            throw new ExemptFacilityException ("FinderException caught at getExemptFacilityByGroupID " + e.toString());
        }
        catch (Exception e) {
            throw new ExemptFacilityException ("Exception caught at getExemptFacilityByGroupID " + e.toString());
        }

    }


    protected EBExemptFacilityHome getEBExemptFacilityHome() {
        DefaultLogger.debug(this,"SBExemptFacilityBusManagerStagingBean  getEbExemptFacilityHome EB_EXEMPT_FACILITY_JNDI_STAGING " + ICMSJNDIConstant.EB_EXEMPT_FACILITY_JNDI_STAGING);

        return (EBExemptFacilityHome)BeanController.getEJBHome(
                ICMSJNDIConstant.EB_EXEMPT_FACILITY_JNDI_STAGING,
                EBExemptFacilityHome.class.getName());
    }
}
