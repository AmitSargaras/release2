/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/BookingLocationList.java,v 1.3 2004/06/04 05:19:56 hltan Exp $
 */
package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.proxy.CollateralProxyFactory;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Description
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/04 05:19:56 $ Tag: $Name: $
 */

public class BookingLocationList {
	private static ArrayList bookingLocationID;

	private static ArrayList bookingLocationValue;

	private static HashMap bookingLocationMap;

	private static BookingLocationList thisInstance;

	public synchronized static BookingLocationList getInstance() throws CommandProcessingException {
		thisInstance = new BookingLocationList();
		return thisInstance;
	}

	private BookingLocationList() throws CommandProcessingException {
		bookingLocationID = new ArrayList();
		bookingLocationValue = new ArrayList();
		bookingLocationMap = new HashMap();
		CountryList list = CountryList.getInstance();

		try {
			IBookingLocation[] bookingLoc = CollateralProxyFactory.getProxy().getAllBookingLocation();
			if (bookingLoc != null) {
				for (int i = 0; i < bookingLoc.length; i++) {
					if (bookingLoc[i] != null) {
						// String id = bookingLoc[i].getCountryCode()
						// +" "+bookingLoc[i].getOrganisationCode();
						String id = list.getCountryName(bookingLoc[i].getCountryCode()) + " "
								+ bookingLoc[i].getOrganisationCode();
						String value = bookingLoc[i].getBookingLocationDesc();
						bookingLocationID.add(id);
						bookingLocationValue.add(value);
						bookingLocationMap.put(id, value);
					}
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
	}

	public Collection getBookingLocationID() {
		return bookingLocationID;
	}

	public Collection getBookingLocationValue() {
		return bookingLocationValue;
	}

	public String getBookingLocationItem(String key) {
		if (!bookingLocationMap.isEmpty()) {
			return (String) bookingLocationMap.get(key);
		}
		return "";
	}

}
