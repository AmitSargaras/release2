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
 * Local home interface for EBCustRelationshipBean.
 *
 * @author  $Author: pctan $<br>
 * @version $Revision: $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
public interface EBCustRelationshipLocalHome extends EJBLocalHome
{

    /**
	     * Find the local ejb object by primary key, the Customer Relationship ID.
	     *
	     * @param custRelnshipIDPK Customer Relationship ID
	     * @return local Customer Relationship ejb object
	     * @throws FinderException on error while finding the ejb
	     */
    public EBCustRelationshipLocal findByPrimaryKey (Long custRelnshipIDPK)
        throws FinderException;

}