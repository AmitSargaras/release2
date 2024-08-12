/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * IExemptFacility
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
    public interface IExemptFacility extends Serializable, IValueObject {

    long getExemptFacilityID();
    void setExemptFacilityID(long exemptFacilityID);

    long getGroupID();
    void setGroupID(long groupID);

    long getCmsRef();
    void setCmsRef(long cmsRef);
    
    // Comment out unnecessary codes
    // String getLosSystem() ;
    // void setLosSystem(String losSystem) ;

    String getFacilityCode() ;
    void setFacilityCode(String facilityCode) ;

    String getFacilityStatusExempted() ;
    void setFacilityStatusExempted(String facilityStatusExempted);

    double getFacilityStatusConditionalPerc() ;
    void setFacilityStatusConditionalPerc(double facilityStatusConditionalPerc) ;

    String getRemarks();
    void setRemarks(String remarks) ;

    String getStatus();
    void setStatus(String status);

}
