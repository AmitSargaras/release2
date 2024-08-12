/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/proxy/SBCommodityMaintenanceProxyBean.java,v 1.2 2004/06/04 04:53:42 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.proxy;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * Created by IntelliJ IDEA. User: dayanand Date: Mar 24, 2004 Time: 3:14:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class SBCommodityMaintenanceProxyBean extends AbstractCommodityMaintenanceProxy implements SessionBean {
	/**
	 * SessionContext object
	 */
	private SessionContext _context = null;

	/**
	 * Default constructor.
	 */
	public SBCommodityMaintenanceProxyBean() {
	}

	public void ejbCreate() {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public void setSessionContext(SessionContext sc) {
		_context = sc;
	}

	/**
	 * To rollback a transaction
	 */
	protected void rollback() {
		_context.setRollbackOnly();
	}

}
