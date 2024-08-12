/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/EventMonitorException.java,v 1.1 2003/08/18 06:27:32 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.exception.OFAException;

public class EventMonitorException extends OFAException {
	public EventMonitorException() {
		super();
	}

	public EventMonitorException(String msg) {
		super(msg);
	}

	public EventMonitorException(Throwable t) {
		super(t);
	}

	public EventMonitorException(String msg, Throwable t) {
		super(msg, t);
	}

}
