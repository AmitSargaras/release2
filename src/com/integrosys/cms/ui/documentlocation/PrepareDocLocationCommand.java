/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/documentlocation/PrepareDocLocationCommand.java,v 1.8 2004/11/19 09:45:41 lyng Exp $
 */
package com.integrosys.cms.ui.documentlocation;

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.documentlocation.bus.ICCDocumentLocation;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * Description
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/11/19 09:45:41 $ Tag: $Name: $
 */

public class PrepareDocLocationCommand extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "docLocationObj",
				"com.integrosys.cms.app.documentlocation.bus.OBCCDocumentLocation", SERVICE_SCOPE }, });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "orgCodeLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "orgCodeValues", "java.util.Collection", REQUEST_SCOPE },
				{ "countryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here reading for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		ICCDocumentLocation docLoc = (ICCDocumentLocation) map.get("docLocationObj");

		if ((map.get("wip") == null) && (map.get("wip_checklist") == null)) {
			try {
				ArrayList list1 = new ArrayList();
				ArrayList list2 = new ArrayList();

				if ((docLoc.getOriginatingLocation() != null)
						&& (docLoc.getOriginatingLocation().getCountryCode() != null)) {
					ILimitProxy proxy = LimitProxyFactory.getProxy();
					IBookingLocation bkg[] = proxy.getBookingLocationByCountry(docLoc.getOriginatingLocation()
							.getCountryCode());
					if (bkg != null) {
						for (int i = 0; i < bkg.length; i++) {
							DefaultLogger.debug(this, "Org code " + bkg[i].getOrganisationCode());
							DefaultLogger.debug(this, "Org name " + bkg[i].getBookingLocationDesc());
							list2.add(bkg[i].getOrganisationCode());
							list1.add(CommonDataSingleton.getCodeCategoryLabelByValue(ICMSConstant.ORG_CODE, bkg[i]
									.getOrganisationCode()));
						}
					}
				}
				result.put("orgCodeLabels", list1);
				result.put("orgCodeValues", list2);

				CountryList list = CountryList.getInstance();
				result.put("countryValues", list.getCountryValues());
				result.put("countryLabels", list.getCountryLabels());

			}
			catch (Exception e) {
				e.printStackTrace();
				throw (new CommandProcessingException(e.getMessage()));
			}
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}
}
