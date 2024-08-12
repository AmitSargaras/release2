/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import java.util.List;

import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import com.integrosys.base.techinfra.event.EventHandlingException;
import com.integrosys.base.techinfra.event.EventManager;

/**
 * Session bean implementation of the services provided by the Event Montitor
 * Controller.
 * 
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/25 05:54:25 $ Tag: $Name: $
 */
public class SBEventManagerBean implements SessionBean {
	/**
	 * Default constructor.
	 */
	public SBEventManagerBean() {
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

	public void fireEvent(String eventID, List params) throws EventHandlingException {
		EventManager eventMgr = EventManager.getInstance();
		eventMgr.fireEvent(eventID, params);
	}

}
