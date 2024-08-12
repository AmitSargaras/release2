/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.app.eventmonitor;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * 
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/03/19 10:26:44 $ Tag: $Name: $
 */
public class EventMonitorControllerImpl implements IEventMonitorController {
	/*
	 * public void monitorDocumentExpiry(){ try{
	 * getEventMonitorController().monitorDocumentExpiry();
	 * }catch(RemoteException e){ //todo log the error } }
	 */

	/**
	 * To get the remote handler for the custodian proxy manager
	 * @return SBEventMonitorController - the remote handler for the custodian
	 *         proxy manager
	 */
	private SBEventMonitorController getEventMonitorController() {
		SBEventMonitorController controller = (SBEventMonitorController) BeanController.getEJB(
				ICMSJNDIConstant.SB_EVENT_MONITOR_CONTROLLER_JNDI, SBEventMonitorControllerHome.class.getName());
		return controller;
	}

}
