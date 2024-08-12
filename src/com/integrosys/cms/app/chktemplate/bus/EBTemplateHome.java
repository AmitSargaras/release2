/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateHome.java,v 1.4 2003/08/15 09:51:32 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

//java
import java.rmi.RemoteException;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;

/**
 * Home interface for the template entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/08/15 09:51:32 $ Tag: $Name: $
 */

public interface EBTemplateHome extends EJBHome {
	/**
	 * Create a template
	 * @param anITemplate - ITemplate
	 * @return EBTemplate - the remote handler for the created template
	 * @throws CreateException if creation fails
	 * @throws RemoteException
	 */
	public EBTemplate create(ITemplate anITemplate) throws CreateException, RemoteException;

	/**
	 * Find by primary Key, the template ID
	 * @param aPK - Long
	 * @return EBTemplate - the remote handler for the template that has the PK
	 *         as specified
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public EBTemplate findByPrimaryKey(Long aPK) throws FinderException, RemoteException;

	/**
	 * Find by law and legal consitution and country is null
	 * @param aType - String
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @return Collection - the collection of EBTemplate
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findByLawConstitution(String aType, String aLaw, String aLegalConstitution)
			throws FinderException, RemoteException;

	/**
	 * Find by law, legal consitution and the country
	 * @param aType - String
	 * @param aLaw - String
	 * @param aLegalConstitution - String
	 * @param aCountry - String
	 * @return Collection - the collection of EBTemplate
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findByLawConstitutionCountry(String aType, String aLaw, String aLegalConstitution, String aCountry)
			throws FinderException, RemoteException;

	/**
	 * Find by Collateral type, sub type and country is null
	 * @param aType - String
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @return Collection - the collection of EBTemplate
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findByCollateralTypeSubType(String aType, String aCollateralType, String aCollateralSubType)
			throws FinderException, RemoteException;

	/**
	 * Find by Collateral type, sub type and country is null
	 * @param aType - String
	 * @param aCollateralType - String
	 * @param aCollateralSubType - String
	 * @param aCountry - String
	 * @return Collection - the collection of EBTemplate
	 * @throws FinderException
	 * @throws RemoteException
	 */
	public Collection findByCollateralTypeSubTypeCountry(String aType, String aCollateralType,
			String aCollateralSubType, String aCountry) throws FinderException, RemoteException;

	/**
	 * To get the list of law and the customer types
	 * @param lawList of String type
	 * @return LawSearchResultItem[] - the list of law and customer types
	 * @throws SearchDAOException if errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public LawSearchResultItem[] getLawCustomerTypes(String[] lawList) throws SearchDAOException, RemoteException;

	/**
	 * To get the list of templates based on the criteria specified
	 * @param aCriteria - TemplateSearchCriteria
	 * @return SearchResult - contain the list fo templates that satisfy the
	 *         criteria
	 * @throws SearchDAOException is errors in DAO
	 * @throws RemoteException on remote errors
	 */
	public SearchResult searchTemplateList(TemplateSearchCriteria aCriteria) throws SearchDAOException, RemoteException;

}