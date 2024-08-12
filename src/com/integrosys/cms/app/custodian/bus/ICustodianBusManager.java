/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/ICustodianBusManager.java,v 1.5 2003/06/23 06:40:49 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//ofa
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;

/**
 * This interface defines the list of business related methods pertaining to the
 * custodian that is available for use
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/06/23 06:40:49 $ Tag: $Name: $
 */
public interface ICustodianBusManager {
	/**
	 * Create a custodian document in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the created custodian doc
	 * @throws CustodianException
	 */
	public ICustodianDoc create(ICustodianDoc anICustodianDoc) throws CustodianException;

	/**
	 * Update a custodian that is already in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the updated custodian doc
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public ICustodianDoc update(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, CustodianException;

	/**
	 * Delete a custodian document from the custodian registry.
	 * @param aCustodianDocID - long
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public void delete(long aCustodianDocID) throws ConcurrentUpdateException, CustodianException;

	/**
	 * Retrieve a custodian document from the custodian registry.
	 * @param aCustodianID - long
	 * @return OBCustodianDoc - the object encapsulating the custodian document
	 *         info
	 * @throws CustodianException
	 */
	public ICustodianDoc getCustodianDoc(long aCustodianID) throws CustodianException;

	/**
	 * Retrieve a list of custodian documents from the custodian registry.
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contains the list of custodian document retrieved
	 *         based on the search criteria
	 * @throws CustodianException
	 */
	public SearchResult getCustodianDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException;
}
