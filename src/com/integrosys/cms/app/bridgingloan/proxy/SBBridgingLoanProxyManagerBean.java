package com.integrosys.cms.app.bridgingloan.proxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoanSummary;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManager;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 19, 2007 Tag: $Name$
 */
public class SBBridgingLoanProxyManagerBean extends AbstractBridgingLoanProxyManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBBridgingLoanProxyManagerBean() {
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
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	public IBridgingLoanSummary[] getBridgingLoanSummaryList(long aLimitProfileID) throws BridgingLoanException {
		try {
			return getBridgingLoanBusManager().getBridgingLoanSummaryList(aLimitProfileID);

		}
		catch (Exception e) {
			e.printStackTrace();
			DefaultLogger.error(this, "", e);
			throw new BridgingLoanException(e);
		}

	}

	// **************** Getting SB Methods ************
	protected SBBridgingLoanBusManager getBridgingLoanBusManager() throws Exception {
		SBBridgingLoanBusManager home = (SBBridgingLoanBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_BRIDGING_LOAN_BUS_JNDI, SBBridgingLoanBusManagerHome.class.getName());

		if (home != null) {
			return home;
		}
		else {
			throw new Exception("SBBridgingLoanBusManager for Actual is null!");
		}
	}
}