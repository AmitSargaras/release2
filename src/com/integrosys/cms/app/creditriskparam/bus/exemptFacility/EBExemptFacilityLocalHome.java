/*
* Copyright Integro Technologies Pte Ltd
* $Header$
*/
package com.integrosys.cms.app.creditriskparam.bus.exemptFacility;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * EBExemptFacilityLocalHome
 * Purpose:
 * Description:
 *
 * @author $Author$
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
public interface EBExemptFacilityLocalHome extends EJBLocalHome{

    EBExemptFacilityLocal create (IExemptFacility ExemptFacility) throws CreateException;

    EBExemptFacilityLocal findByPrimaryKey (Long pk) throws FinderException;

    Collection findByGroupID (long groupID) throws FinderException;

}
