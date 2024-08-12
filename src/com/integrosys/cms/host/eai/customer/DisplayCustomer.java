package com.integrosys.cms.host.eai.customer;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.host.eai.customer.bus.MainProfile;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class DisplayCustomer {
	private static DisplayCustomer instance = null;

	private DisplayCustomer() {
	}

	/**
	 * Return an instance of Host File ID Genenerator
	 * @return instance of Store Bulk Trx File Daemon
	 */

	public static DisplayCustomer getInstance() {
		if (instance == null) {
			synchronized (DisplayCustomer.class) {
				if (instance == null) {
					instance = new DisplayCustomer();
				}
			}
		}
		return instance;
	}

	public void displayMainProfile(MainProfile mp) {
		DefaultLogger.debug(this, "\nCMSID is :" + mp.getCmsId() + ", \nCIFID is :" + mp.getCIFId()
				+ ", \nOperationStatus StandardCode is:" + mp.getChangeIndicator() + ", \n is :");

	}
}
