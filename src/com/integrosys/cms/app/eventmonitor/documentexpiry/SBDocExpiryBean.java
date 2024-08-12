/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/documentexpiry/SBDocExpiryBean.java,v 1.1 2005/11/18 10:25:50 lini Exp $
 */
package com.integrosys.cms.app.eventmonitor.documentexpiry;

import java.util.ArrayList;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.eventmonitor.EventMonitorException;
import com.integrosys.cms.app.eventmonitor.IRuleParam;
import com.integrosys.cms.app.eventmonitor.SBEventManager;
import com.integrosys.cms.app.eventmonitor.SBEventManagerHome;

/**
 * Session Bean for Doc Expiry
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/11/18 10:25:50 $ Tag: $Name: $
 */
public class SBDocExpiryBean implements SessionBean {
	/**
	 * Default constructor.
	 */
	public SBDocExpiryBean() {
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
	}

	/**
	 * This method will process the event monitored based on the protocol codes
	 * and Business OB passed in. Moved here from AbstractMonCommon to create
	 * new Trasaction Context for each iteration.
	 */
	public void processEvent(String code, Object o, IRuleParam ruleParam, String eventName)
			throws EventMonitorException {
		try {
			SBEventManager eventManager = getEventManager();
			ArrayList list = new ArrayList();
			list.add(o);
			list.add(code);
			list.add(ruleParam);
			DefaultLogger.debug(this, "Before calling event handler");
			eventManager.fireEvent(eventName, list);
			DefaultLogger.debug(this, "After calling event handler");
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new EventMonitorException(e);
		}
	}

	/**
	 * Convenient method to get EventManager
	 * 
	 */
	protected SBEventManager getEventManager() {
		SBEventManager eventManager = (SBEventManager) BeanController.getEJB(ICMSJNDIConstant.SB_EVENT_MANAGER_JNDI,
				SBEventManagerHome.class.getName());
		return eventManager;
	}
}
