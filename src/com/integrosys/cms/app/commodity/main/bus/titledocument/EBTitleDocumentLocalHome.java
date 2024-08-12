/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/bus/titledocument/EBTitleDocumentLocalHome.java,v 1.4 2004/11/17 06:42:27 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.bus.titledocument;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

/**
 * Defines TitleDOcument home methods for clients.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2004/11/17 06:42:27 $ Tag: $Name: $
 */
public interface EBTitleDocumentLocalHome extends EJBLocalHome {
	/**
	 * Create collateral record.
	 * 
	 * @param collateral of type ICollateral
	 * @return TitleDocument - ejb object
	 * @throws CreateException on error creating the ejb
	 */
	public EBTitleDocumentLocal create(ITitleDocument collateral) throws CreateException;

	/**
	 * Find collateral by primary key, the collateral id.
	 * 
	 * @param theID ID of the entity
	 * @return TitleDocument - ejb object
	 * @throws FinderException on error finding the collateral
	 */
	public EBTitleDocumentLocal findByPrimaryKey(Long theID) throws FinderException;

	/**
	 * Find all TitleDocuements
	 * @return
	 * @throws FinderException
	 */
	public Collection findAll() throws FinderException;

	/**
	 * Find all TitleDocuements for a particular document type,
	 * ITitleDocument.NEGOTIABLE or ITitleDocument.NON_NEGOTIABLE
	 * @return
	 * @throws FinderException
	 */
	public Collection findAllByDocType(String type) throws FinderException;

	public Collection findByGroupID(Long groupID) throws FinderException;

	public EBTitleDocumentLocal findByCommonRef(Long commonRef) throws FinderException;

	public EBTitleDocumentLocal findByGroupIDCommonRef(Long groupID, Long commonRef) throws FinderException;

}
