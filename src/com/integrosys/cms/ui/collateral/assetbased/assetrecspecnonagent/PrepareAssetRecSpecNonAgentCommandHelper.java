//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased.assetrecspecnonagent;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.ui.collateral.BookingLocationList;
import com.integrosys.cms.ui.collateral.assetbased.NatureOfAssignmentList;
import com.integrosys.cms.ui.collateral.assetbased.TypeOfInvoiceList;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 22, 2003 Time: 4:45:05 PM
 * To change this template use Options | File Templates.
 */
public class PrepareAssetRecSpecNonAgentCommandHelper {

	public static String[][] getResultDescriptor() {
		return (new String[][] { { "natureOfChargeID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "natureOfChargeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "typeOfInvoiceListID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "typeOfInvoiceListValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationSCBID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationSCBValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryID", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "countryValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	public static void fillPrepare(HashMap map, HashMap result, HashMap exception) throws CommandProcessingException {

		NatureOfAssignmentList natureList = NatureOfAssignmentList.getInstance();
		result.put("natureOfChargeID", natureList.getNatureOfAssignmentListID());
		result.put("natureOfChargeValue", natureList.getNatureOfAssignmentListValue());

		TypeOfInvoiceList typeOfInvoice = TypeOfInvoiceList.getInstance();
		result.put("typeOfInvoiceListID", typeOfInvoice.getTypeOfInvoiceListID());
		result.put("typeOfInvoiceListValue", typeOfInvoice.getTypeOfInvoiceListValue());

		BookingLocationList bookingLoc = BookingLocationList.getInstance();
		result.put("locationSCBID", bookingLoc.getBookingLocationID());
		result.put("locationSCBValue", bookingLoc.getBookingLocationValue());

		CountryList countryList = CountryList.getInstance();
		result.put("countryValue", countryList.getCountryLabels());
		result.put("countryID", countryList.getCountryValues());

		return;
	}
}
