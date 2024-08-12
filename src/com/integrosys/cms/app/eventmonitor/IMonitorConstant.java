/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/IMonitorConstant.java,v 1.3 2003/09/02 02:00:11 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor;

/**
 * @author $author$
 * @version $revision$ Defines constants used in the event monitoring package
 */
public interface IMonitorConstant {
	// EVENT_NONE is special, it will be interpreted by the AbstractMonCommon
	// class
	// if EVENT_NONE is returned, then no listener will be fired.
	// All other events, including EVENT_FALSE below, will trigger events firing
	// and it will be up to
	// the listeners to interpret the result codes and handle them accordingly.
	final String EVENT_NONE = "NO_EVENT_TO_BE_FIRED";

	final String EVENT_TRUE = "EVENT_TRUE";

	final String EVENT_FALSE = "EVENT_FALSE";

	// keys to properties file to get event subject message
	// to be suffixed with the event name
	final String SUBJECT_EVENT_KEY_PREFIX = "cms.eventmonitor.eventnotification.subject";

}
