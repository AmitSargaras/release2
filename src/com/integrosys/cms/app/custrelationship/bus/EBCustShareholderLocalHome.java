/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.custrelationship.bus;

import com.integrosys.base.businfra.search.SearchDAOException;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.util.Collection;

/**
 * Local home interface for EBCustShareholderBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustShareholderLocalHome extends EJBLocalHome
{

    /**
	     * Find the local ejb object by primary key, the Customer Shareholder ID.
	     *
	     * @param custRelnshipIDPK Customer Shareholder ID
	     * @return local Customer Shareholder ejb object
	     * @throws FinderException on error while finding the ejb
	     */
    public EBCustShareholderLocal findByPrimaryKey (Long custRelnshipIDPK)
        throws FinderException;

}