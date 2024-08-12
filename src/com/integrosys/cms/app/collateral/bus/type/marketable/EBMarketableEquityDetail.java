/**
 * 
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import java.rmi.RemoteException;

import javax.ejb.EJBObject;

/**
 * @author user
 * 
 */
public interface EBMarketableEquityDetail extends EJBObject {

	/**
	 * Get the marketable equity detail business object.
	 * 
	 * @return marketable equity detail
	 */
	public void setValue(IMarketableEquityDetail equityDetail) throws RemoteException;

	/**
	 * Get the marketable equity detail business object.
	 * 
	 * @return marketable equity
	 */
	public IMarketableEquityDetail getValue() throws RemoteException;
}
