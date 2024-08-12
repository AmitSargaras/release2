package com.integrosys.cms.app.contractfinancing.proxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.contractfinancing.bus.ContractFinancingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancingSummary;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManager;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManagerHome;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class SBContractFinancingProxyManagerBean extends AbstractContractFinancingProxyManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBContractFinancingProxyManagerBean() {
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

	// public IContractFinancingTrxValue
	// makerSaveNDContractFinancing(ITrxContext ctx, IContractFinancingTrxValue
	// trxValue, IContractFinancing contractFinancing) throws
	// ContractFinancingException {
	// return null; //To change body of implemented methods use File | Settings
	// | File Templates.
	// }

	// ==========================================
	// Implementation Methods
	// ==========================================
	public IContractFinancingSummary[] getContractFinancingSummaryList(long aLimitProfileID)
			throws ContractFinancingException {
		try {
			return getContractFinancingBusManager().getContractFinancingSummaryList(aLimitProfileID);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new ContractFinancingException(e);
		}
	}

	// **************** Getting SB Methods ************
	protected SBContractFinancingBusManager getContractFinancingBusManager() throws Exception {
		SBContractFinancingBusManager home = (SBContractFinancingBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CONTRACT_FINANCING_BUS_JNDI, SBContractFinancingBusManagerHome.class.getName());

		if (home != null) {
			return home;
		}
		else {
			throw new Exception("SBContractFinancingBusManager for Actual is null!");
		}
	}

}
