/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/CustodianBusManagerImpl.java,v 1.6 2003/06/23 06:40:49 hltan Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This interface defines the list of business related methods pertaining to the
 * custodian that is available for use
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/06/23 06:40:49 $ Tag: $Name: $
 */
public class CustodianBusManagerImpl implements ICustodianBusManager {
	/**
	 * Create a custodian document in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the created custodian doc
	 * @throws CustodianException
	 */
	public ICustodianDoc create(ICustodianDoc anICustodianDoc) throws CustodianException {
		try {
			return getCustodianBusManager().create(anICustodianDoc);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Update a custodian that is already in the custodian registry.
	 * @param anICustodianDoc - ICustodianDoc
	 * @return ICustodianDoc - the updated custodian doc
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public ICustodianDoc update(ICustodianDoc anICustodianDoc) throws ConcurrentUpdateException, CustodianException {
		try {
			return getCustodianBusManager().update(anICustodianDoc);
		}
		catch (ConcurrentUpdateException vex) {
			throw vex;
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Delete a custodian document from the custodian registry.
	 * @param aCustodianDocID - long
	 * @throws ConcurrentUpdateException
	 * @throws CustodianException
	 */
	public void delete(long aCustodianDocID) throws ConcurrentUpdateException, CustodianException {
		try {
			getCustodianBusManager().delete(aCustodianDocID);
		}
		catch (ConcurrentUpdateException vex) {
			throw vex;
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Retrieve a custodian document from the custodian registry.
	 * @param aCustodianID - long
	 * @return ICustodianDoc - the object encapsulating the custodian document
	 *         info
	 * @exception CustodianException
	 */
	public ICustodianDoc getCustodianDoc(long aCustodianID) throws CustodianException {
		try {
			return getCustodianBusManager().getCustodianDoc(aCustodianID);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * Retrieve a list of custodian documents from the custodian registry.
	 * @param aCustodianSearchCriteria - CustodianSearchCriteria
	 * @return SearchResult - contain the list of custodian document retrieved
	 *         based on the search criteria
	 * @exception CustodianException
	 */
	public SearchResult getCustodianDocList(CustodianSearchCriteria aCustodianSearchCriteria) throws CustodianException {
		try {
			return getCustodianBusManager().getCustodianDocList(aCustodianSearchCriteria);
		}
		catch (CustodianException cex) {
			throw cex;
		}
		catch (RemoteException rex) {
			throw new CustodianException(rex.toString());
		}
	}

	/**
	 * To get the remote handler for the custodian bus manager
	 * @return SBCustodianBusManager - the remote handler for the custodian bus
	 *         manager
	 */
	private SBCustodianBusManager getCustodianBusManager() {
		SBCustodianBusManager busmgr = (SBCustodianBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CUSTODIAN_BUS_JNDI, SBCustodianBusManagerHome.class.getName());
		return busmgr;
	}
}
