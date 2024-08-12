package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

import com.integrosys.base.businfra.search.SearchDAOException;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 22, 2007 Tag: $Name$
 */
public interface EBBridgingLoanHome extends EJBHome {

	/**
	 * Create a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return EBBridgingLoan - the bridging loan being created
	 * @throws CreateException on errors
	 */
	public EBBridgingLoan create(IBridgingLoan bridgingLoanObj) throws CreateException, RemoteException;

	/**
	 * Get the bridging loan object by the primary key
	 * @param pk - primary key used to retrieve the trxValue
	 * @return EBBridgingLoan
	 * @throws FinderException on errors
	 */
	public EBBridgingLoan findByPrimaryKey(Long pk) throws FinderException, RemoteException;

	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws SearchDAOException on errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws SearchDAOException,
			RemoteException;

}
