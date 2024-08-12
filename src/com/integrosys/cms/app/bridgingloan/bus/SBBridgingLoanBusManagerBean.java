package com.integrosys.cms.app.bridgingloan.bus;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class SBBridgingLoanBusManagerBean extends AbstractBridgingLoanBusManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBBridgingLoanBusManagerBean() {
	}

	// ==========================================
	// Start of Standard Session Bean Methods
	// ==========================================

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(javax.ejb.SessionContext sc) {
		_context = sc;
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

	// ==========================================
	// Implementation Methods
	// ==========================================
	/**
	 * Create a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return IBridgingLoan - the bridging loan being created
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoan create(IBridgingLoan bridgingLoanObj) throws BridgingLoanException {
		try {
			if (bridgingLoanObj == null) {
				throw new BridgingLoanException("The IBridgingLoan to be created is null !!!");
			}
			EBBridgingLoanHome home = getEBBridgingLoanHome();
			EBBridgingLoan remote = home.create(bridgingLoanObj);
			remote.setValue(bridgingLoanObj);

			// long verTime = remote.getVersionTime();
			IBridgingLoan newBL = remote.getValue();
			return newBL;
		}
		catch (CreateException ex) {
			throw new BridgingLoanException("Exception in create (bridgingLoan): " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in create (bridgingLoan): " + ex.toString());
		}
		catch (ConcurrentUpdateException ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in create (bridgingLoan): " + ex.toString());
		}
		catch (Exception ex) {
			rollback();
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in create (bridgingLoan): " + ex.toString());
		}
	}

	/**
	 * Update a bridging loan object
	 * @param bridgingLoanObj - IBridgingLoan
	 * @return ICheckList - the bridging loan being updated
	 * @throws BridgingLoanException on errors
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         on concurrent updates
	 */
	public IBridgingLoan update(IBridgingLoan bridgingLoanObj) throws ConcurrentUpdateException, BridgingLoanException {
		try {
			if (bridgingLoanObj == null) {
				throw new BridgingLoanException("The IBridgingLoan to be updated is null !!!");
			}
			EBBridgingLoanHome home = getEBBridgingLoanHome();
			EBBridgingLoan remote = home.findByPrimaryKey(new Long(bridgingLoanObj.getProjectID()));
			remote.setValue(bridgingLoanObj);
			return remote.getValue();
		}
		catch (FinderException ex) {
			throw new BridgingLoanException("Exception in update (bridgingLoan): " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new BridgingLoanException("Exception in update (bridgingLoan): " + ex.toString());
		}
	}

	// ==========================================
	// Retrieval Methods
	// ==========================================
	/**
	 * Get the bridging loan object by the primary key
	 * @param id - ID (Primary Key) used to retrieve the trxValue
	 * @return IBridgingLoan
	 * @throws BridgingLoanException on errors
	 */
	public IBridgingLoan getBridgingLoanByID(Long id) throws BridgingLoanException {
		try {
			EBBridgingLoanHome home = getEBBridgingLoanHome();
			EBBridgingLoan remote = home.findByPrimaryKey(id);
			return remote.getValue();

		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new BridgingLoanException("FinderException at getBridgingLoanByID: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new BridgingLoanException("RemoteException in getBridgingLoanByID: " + ex.toString());
		}
	}

	/**
	 * Get the list of bridging loan summary info. //@param aLimitProfileID of
	 * long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 *         //@throws BridgingLoanException on errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws BridgingLoanException {
		try {
			EBBridgingLoanHome home = getEBBridgingLoanHome();
			return home.getBridgingLoanSummaryList(aLimitProfileID);

		}
		catch (SearchDAOException ex) {
			_context.setRollbackOnly();
			throw new BridgingLoanException("SearchDAOException at getBridgingLoanSummaryList: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new BridgingLoanException("RemoteException in getBridgingLoanSummaryList: " + ex.toString());
		}
	}

	// **************** Getting SB Methods ************
	/**
	 * To get the home handler for the BridgingLoan Entity Bean
	 * @return EBBridgingLoanHome - the home handler for the BridgingLoan entity
	 *         bean
	 */
	protected EBBridgingLoanHome getEBBridgingLoanHome() {
		EBBridgingLoanHome ejbHome = (EBBridgingLoanHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_BRIDGING_LOAN_JNDI, EBBridgingLoanHome.class.getName());
		return ejbHome;
	}
}