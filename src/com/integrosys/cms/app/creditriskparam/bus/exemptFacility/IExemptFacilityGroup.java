/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;

import java.io.Serializable;

/**
 * IExemptFacilityGroup
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public interface IExemptFacilityGroup extends Serializable, IValueObject {

    long getExemptFacilityGroupID();
    void setExemptFacilityGroupID(long exemptFacilityGroupID);

    IExemptFacility[] getExemptFacility();
    void setExemptFacility(IExemptFacility[] exemptFacilityGroup);

    void addItem(IExemptFacility anItem);
    void removeItems(int[] anItemIndexList);

}
