package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface EBMarketableEquityDetailHome extends EJBHome {

	/**
	 * Create a new marketable equity detail.
	 * 
	 * @param equity of type IMarketableEquity
	 * @return local marketable equity ejb object
	 * @throws CreateException on error creating the ejb object
	 */
	public EBMarketableEquityDetail create(IMarketableEquityDetail equity) throws CreateException, RemoteException;

	/**
	 * Find the marketable equity entity detail bean by its primary key.
	 * 
	 * @param pk marketable equity detail id
	 * @return local marketable equity detail ejb object
	 * @throws FinderException on error finding the equity detail
	 */
	public EBMarketableEquityDetail findByPrimaryKey(Long pk) throws FinderException, RemoteException;
}
