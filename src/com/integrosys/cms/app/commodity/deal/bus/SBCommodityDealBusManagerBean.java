/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/SBCommodityDealBusManagerBean.java,v 1.7 2005/10/06 05:54:55 hmbao Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.businfra.common.exception.VersionMismatchException;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This session bean provides the implementation of the
 * AbstractCommodityDealBusManager, wrapped in an EJB mechanism.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.7 $
 * @since $Date: 2005/10/06 05:54:55 $ Tag: $Name: $
 */
public class SBCommodityDealBusManagerBean extends AbstractCommodityDealBusManager implements SessionBean {
	/** SessionContext object */
	private SessionContext ctx;

	/**
	 * Default Constructor
	 */
	public SBCommodityDealBusManagerBean() {
	}

	/**
	 * Get the complete commodity deal given its deal id.
	 * 
	 * @param dealID commodity deal id
	 * @return commodity deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal getCommodityDeal(long dealID) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			EBCommodityDealLocal theEjb = ejbHome.findByPrimaryKey(new Long(dealID));
			ICommodityDeal deal = theEjb.getValue();
			return deal;
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("FinderExcepion caught! " + e.toString());
		}
	}

	/**
	 * Create a deal.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly created deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal createDeal(ICommodityDeal deal) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			EBCommodityDealLocal theEjb = ejbHome.create(deal);
			return theEjb.getValue();
		}
		catch (CreateException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new CommodityDealException("CreateException caught! " + e.toString());
		}
	}

	/**
	 * Update a deal.
	 * 
	 * @param deal is of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDeal(ICommodityDeal deal) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			EBCommodityDealLocal theEjb = ejbHome.findByPrimaryKey(new Long(deal.getCommodityDealID()));
			theEjb.setValue(deal);
			return theEjb.getValue();
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new CommodityDealException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new CommodityDealException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
	}

	/**
	 * Update commodity deal status.
	 * 
	 * @param deal of type ICommodityDeal
	 * @return newly updated deal
	 * @throws CommodityDealException on errors encountered
	 */
	public ICommodityDeal updateDealStatus(ICommodityDeal deal) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			EBCommodityDealLocal theEjb = ejbHome.findByPrimaryKey(new Long(deal.getCommodityDealID()));
			theEjb.setDealStatus(deal);
			return theEjb.getValue();
		}
		catch (FinderException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new CommodityDealException("FinderException caught! " + e.toString());
		}
		catch (VersionMismatchException e) {
			DefaultLogger.error(this, "", e);
			rollback();
			throw new CommodityDealException("VersionMismatchException caught! " + e.toString(),
					new ConcurrentUpdateException(e.getMessage()));
		}
	}

	/**
	 * Search commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			return ejbHome.searchDeal(criteria);
		}
		catch (SearchDAOException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("SearchDAOException caught! " + e.toString());
		}
	}

	/**
	 * Search closed commodity deal based on the criteria specified.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result
	 * @throws CommodityDealException on errors encountered
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws CommodityDealException {
		try {
			EBCommodityDealLocalHome ejbHome = getEBCommodityDealLocalHome();
			return ejbHome.searchClosedDeal(criteria);
		}
		catch (SearchDAOException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("SearchDAOException caught! " + e.toString());
		}
	}

	/**
	 * Process notification for changes in deal state.
	 * 
	 * @param notifyInfo of type ICommodityDealNotifyInfo
	 * @throws CommodityDealException on errors encountered
	 */
	public void processNotification(ICommodityDealNotifyInfo notifyInfo) throws CommodityDealException {
		try {
			CommodityDealNotifier notifier = new CommodityDealNotifier();
			notifier.process(notifyInfo);
		}
		catch (EventHandlingException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
		catch (CommodityException e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CommodityDealException("RemoteException caught!" + e.toString());
		}
	}

	/**
	 * helper method to get local home interface of EBCommodityDealBean.
	 * 
	 * @return commodity deal local home interface
	 * @throws CommodityDealException on errors encountered
	 */
	protected EBCommodityDealLocalHome getEBCommodityDealLocalHome() throws CommodityDealException {
		EBCommodityDealLocalHome ejbHome = (EBCommodityDealLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_COMMODITY_DEAL_LOCAL_JNDI, EBCommodityDealLocalHome.class.getName());

		if (ejbHome == null) {
			throw new CommodityDealException("EBCommodityDealHome is null!");
		}

		return ejbHome;
	}

	/**
	 * Method to rollback a transaction
	 * 
	 * @throws CommodityDealException on errors encountered
	 */
	protected void rollback() throws CommodityDealException {
		ctx.setRollbackOnly();
	}

	/**
	 * Called by the container to create a session bean instance. Its parameters
	 * typically contain the information the client uses to customize the bean
	 * instance for its use. It requires a matching pair in the bean class and
	 * its home interface. No implementation is required for this bean.
	 */
	public void ejbCreate() {
	}

	/**
	 * Set the associated session context. The container calls this method after
	 * the instance creation. The enterprise bean instance should store the
	 * reference to the context object in an instance variable. This method is
	 * called with no transaction context.
	 */
	public void setSessionContext(SessionContext ctx) {
		this.ctx = ctx;
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
	 * A container invokes this method before it ends the life of the session
	 * object. This happens as a result of a client's invoking a remove
	 * operation, or when a container decides to terminate the session object
	 * after a timeout. This method is called with no transaction context.
	 */
	public void ejbRemove() {
	}

	public boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException {
		try {
			DefaultLogger.debug(this, " - SubLimitId : " + subLimitId);
			Collection deals = getEBCommodityDealLocalHome().findBySubLimitId(subLimitId);
			if ((deals != null) && !deals.isEmpty()) {
				return true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new CommodityDealException("Exception in hasSLRelatedDeal.");
		}
		return false;
	}
}