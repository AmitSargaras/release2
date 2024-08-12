package com.integrosys.cms.host.mq;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public interface IMQConstant {

	// Language Page Code Setting
	public static final int LANGUAGE_SETTING_CODE = PropertyManager.getInt("mq.ccsid");

	// Code for property manager
	public static final String HOST_IP = PropertyManager.getValue("mq.host.ip");

	public static final int HOST_PORT = PropertyManager.getInt("mq.port");

	public static final String GCMS_Q_MANAGER = PropertyManager.getValue("mq.queue.manager");

	// Triggered from Source
	public static final String FROM_HOST_INQUIRY_IN = PropertyManager.getValue("mq.enquiry.source.in.queue");

	public static final String FROM_HOST_INQUIRY_OUT = PropertyManager.getValue("mq.enquiry.source.out.queue");

	public static final String FROM_HOST_UPDATE_IN = PropertyManager.getValue("mq.update.source.in.queue");

	public static final String FROM_HOST_UPDATE_OUT = PropertyManager.getValue("mq.update.source.out.queue");

	// Triggered from CMS
	public static final String SEND_INQUIRY_IN = PropertyManager.getValue("mq.enquiry.gcms.in.queue");

	public static final String SEND_INQUIRY_OUT = PropertyManager.getValue("mq.enquiry.gcms.out.queue");

	public static final String SEND_UPDATE_IN = PropertyManager.getValue("mq.update.gcms.in.queue");

	public static final String SEND_UPDATE_OUT = PropertyManager.getValue("mq.update.gcms.out.queue");

	// To determine if message initiator is Host or GCMS
	public static final int FROM_HOST = 0;

	public static final int FROM_GCMS = 1;

	// To determine is for inquiry or update
	public static final String INQUIRY_TYPE = "I";

	public static final String UPDATE_TYPE = "U";
}
