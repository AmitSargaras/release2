/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.creditriskparam.bus.exemptedinst;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Local home interface for EBExemptedInstBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBExemptedInstLocalHome extends EJBLocalHome
{

    /**
	     * Find the local ejb object by primary key, the Exempted Institution ID.
	     *
	     * @param exemptedInstIDPK Exempted Institution ID
	     * @return local Exempted Institution ejb object
	     * @throws FinderException on error while finding the ejb
	     */
    public EBExemptedInstLocal findByPrimaryKey (Long exemptedInstIDPK)
        throws FinderException;

}