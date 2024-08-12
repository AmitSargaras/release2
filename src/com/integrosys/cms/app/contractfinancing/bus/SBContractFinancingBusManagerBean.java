package com.integrosys.cms.app.contractfinancing.bus;

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
public class SBContractFinancingBusManagerBean extends AbstractContractFinancingBusManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBContractFinancingBusManagerBean() {
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
	 * Creates a contract financing object
	 * @param contractFinancingObj - IContractFinancing
	 * @return IContractFinancing - the contract financing being created
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancing create(IContractFinancing contractFinancingObj) throws ContractFinancingException {
		try {
			if (contractFinancingObj == null) {
				throw new ContractFinancingException("The IContractFinancing to be created is null !!!");
			}

			EBContractFinancingHome home = getEBContractFinancingHome();
			EBContractFinancing remote = home.create(contractFinancingObj);
			remote.setValue(contractFinancingObj);

			long verTime = remote.getVersionTime();
			IContractFinancing newCF = remote.getValue();
			// create child dependencies with checking on version time
			// remote.createDependants(contractFinancingObj,
			// newCF.getContractID(), verTime);

			return newCF;

		}
		catch (CreateException ex) {
			throw new ContractFinancingException("Exception in create (contractFinancing): " + ex.toString());
		}
		catch (Exception e) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("Caught Exception!", e);
		}
	}

	/**
	 * Updates a contract financing object
	 * @param contractFinancingObj - IContractFinancing
	 * @return IContractFinancing - the contract financing being updated
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancing update(IContractFinancing contractFinancingObj) throws ConcurrentUpdateException,
			ContractFinancingException {
		try {
			if (contractFinancingObj == null) {
				throw new ContractFinancingException("The IContractFinancing to be updated is null !!!");
			}

			EBContractFinancingHome home = getEBContractFinancingHome();
			EBContractFinancing remote = home.findByPrimaryKey(new Long(contractFinancingObj.getContractID()));
			remote.setValue(contractFinancingObj);
			return remote.getValue();

		}
		catch (FinderException ex) {
			throw new ContractFinancingException("Exception in update (contractFinancing): " + ex.toString());
		}
		catch (RemoteException ex) {
			throw new ContractFinancingException("Exception in update (contractFinancing): " + ex.toString());
		}
	}

	// ==========================================
	// Retrieval Methods
	// ==========================================
	/**
	 * Get the contract financing object by the primary key
	 * @param id - ID (Primary Key) used to retrieve the trxValue
	 * @return IContractFinancingTrxValue
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancing getContractFinancingByID(Long id) throws ContractFinancingException {
		try {
			EBContractFinancingHome home = getEBContractFinancingHome();
			EBContractFinancing remote = home.findByPrimaryKey(id);
			return remote.getValue();

		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("FinderException at getContractFinancingByID: " + ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("RemoteException in getContractFinancingByID: " + ex.toString());
		}
	}

	/**
	 * Get the list of contract financing summary info.
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract financing
	 *         summary
	 * @throws ContractFinancingException on errors
	 */
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException {
		try {
			EBContractFinancingHome home = getEBContractFinancingHome();
			return home.getContractFinancingSummaryList(aLimitProfileID);

		}
		catch (SearchDAOException ex) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("SearchDAOException at getContractFinancingSummaryList: "
					+ ex.toString());
		}
		catch (RemoteException ex) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("RemoteException in getContractFinancingSummaryList: " + ex.toString());
		}
	}

	// **************** Getting SB Methods ************
	/**
	 * To get the home handler for the ContractFinancing Entity Bean
	 * @return EBContractFinancingHome - the home handler for the
	 *         ContractFinancing entity bean
	 */
	protected EBContractFinancingHome getEBContractFinancingHome() {
		EBContractFinancingHome ejbHome = (EBContractFinancingHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CONTRACT_FINANCING_JNDI, EBContractFinancingHome.class.getName());
		return ejbHome;
	}

}
