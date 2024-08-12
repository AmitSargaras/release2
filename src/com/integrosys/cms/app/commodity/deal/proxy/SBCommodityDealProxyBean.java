/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/proxy/SBCommodityDealProxyBean.java,v 1.4 2006/09/20 12:28:04 hshii Exp $
 */
package com.integrosys.cms.app.commodity.deal.proxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealSearchCriteria;
import com.integrosys.cms.batch.commodity.DealValuationMain;

/**
 * This session bean provides the implementation of the
 * AbstractCommodityDealProxy, wrapped in an EJB mechanism.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/09/20 12:28:04 $ Tag: $Name: $
 */
public class SBCommodityDealProxyBean extends AbstractCommodityDealProxy implements SessionBean {
	/** SessionContext object */
	private SessionContext _context = null;

	/**
	 * Default Constructor
	 */
	public SBCommodityDealProxyBean() {
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CommodityDealException on errors rolling back
	 */
	protected void rollback() throws CommodityDealException {
		try {
			_context.setRollbackOnly();
		}
		catch (Exception e) {
			throw new CommodityDealException(e.toString());
		}
	}

	// ********** EJB Methods ****************
	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface.
	 */
	public void ejbCreate() {
	}

	/**
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}

	/**
	 * The activate method is called when the instance is activated from its
	 * 'passive' state. The instance should acquire any resource that it has
	 * released earlier in the ejbPassivate() method. This method is called with
	 * no transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * The passivate method is called before the instance enters the 'passive'
	 * state. The instance should release any resources that it can re-acquire
	 * later in the ejbActivate() method. After the passivate method completes,
	 * the instance must be in a state that allows the container to use the Java
	 * Serialization protocol to externalize and store away the instance's
	 * state. This method is called with no transaction context.
	 */
	public void ejbPassivate() {

	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise Bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	public void revaluateCustomerDeals(CommodityDealSearchCriteria objSearch) throws CommodityDealException {
		try {
			new DealValuationMain().revaluateCustomerDeals(objSearch, null);
		}
		catch (Exception e) {
			throw new CommodityDealException(e);
		}
	}
}