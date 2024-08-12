package com.integrosys.cms.app.creditriskparam.proxy.policycap;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCap;
import com.integrosys.cms.app.creditriskparam.bus.policycap.IPolicyCapGroup;
import com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManager;
import com.integrosys.cms.app.creditriskparam.bus.policycap.SBPolicyCapBusManagerHome;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $ Tag: $Name: $
 */
public class SBPolicyCapProxyManagerBean extends AbstractPolicyCapProxyManager implements SessionBean {

	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBPolicyCapProxyManagerBean() {
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
	 * Get the Policy Cap by the Exchange Code
	 * @param exchangeCode of String type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCap[] getPolicyCapByExchange(String exchangeCode) throws PolicyCapException {
		try {
			return getPolicyCapBusManager().getPolicyCapByExchange(exchangeCode);
		}
		catch (PolicyCapException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PolicyCapException(e);
		}
	}

	/**
	 * Get the Policy Cap Group by the Exchange Code and Bank Entity
	 * @param exchangeCode of String type
	 * @return IPolicyCap[] - the Policy Cap List for the given exchange
	 * @throws com.integrosys.cms.app.creditriskparam.bus.policycap.PolicyCapException
	 *         on errors
	 */
	public IPolicyCapGroup getPolicyCapGroupByExchangeBank(String exchangeCode, String bankEntity)
			throws PolicyCapException {
		try {
			return getPolicyCapBusManager().getPolicyCapGroupByExchangeBank(exchangeCode, bankEntity);
		}
		catch (PolicyCapException e) {
			DefaultLogger.error(this, "", e);
			throw e;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new PolicyCapException(e);
		}
	}

	// **************** Getting SB Methods ************
	protected SBPolicyCapBusManager getPolicyCapBusManager() throws Exception {
		SBPolicyCapBusManager home = (SBPolicyCapBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_POLICY_CAP_BUS_JNDI, SBPolicyCapBusManagerHome.class.getName());

		if (home != null) {
			return home;
		}
		else {
			throw new Exception("SBPolicyCapBusManager for Actual is null!");
		}
	}

}
