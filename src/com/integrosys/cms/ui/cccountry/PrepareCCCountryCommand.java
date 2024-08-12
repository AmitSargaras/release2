/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.cccountry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * @author $Author: priya $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2006/09/13 11:11:50 $ Tag: $Name: $
 */
public class PrepareCCCountryCommand extends AbstractCommand implements ICommonEventConstant {
	/**
	 * Default Constructor
	 */
	public PrepareCCCountryCommand() {
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "countryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "countryValues", "java.util.Collection", REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			CountryList list = CountryList.getInstance();
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			ITeam team = ctx.getTeam();
			// Priya - Start - CMSSP-768 - Country listing is NOT IN ANY ORDER
			// in CC Doc Template Screen
			ArrayList countryLabels = new ArrayList();
			ArrayList countryValues = new ArrayList();
			List tempCountryValues = null;
			String[] countryList = null;
			if (onlyViewAllow(team)) {
				countryList = team.getCountryCodes();
			}
			else {
				tempCountryValues = new ArrayList(list.getCountryValues());
				countryList = (String[]) tempCountryValues.toArray(new String[0]);
			}
			if ((countryList != null) && (countryList.length > 0)) {
				HashMap hmCountrySort = new HashMap();
				// Passing Labels as Key , Codes as values
				for (int ii = 0; ii < countryList.length; ii++) {
					hmCountrySort.put(list.getCountryName(countryList[ii]), countryList[ii]);
				}
				ArrayList[] sortedList = getSortedCountryList(hmCountrySort);
				countryValues = sortedList[0];
				countryLabels = sortedList[1];
			}
			// END - CMSSP-768
			resultMap.put("countryLabels", countryLabels);
			resultMap.put("countryValues", countryValues);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

	private boolean onlyViewAllow(ITeam anITeam) {
		for (int ii = 0; ii < anITeam.getTeamMemberships().length; ii++) {
			long teamMembershipID = anITeam.getTeamMemberships()[ii].getTeamTypeMembership().getMembershipID();
			if ((ICMSConstant.TEAM_TYPE_SC_MAKER == teamMembershipID)
					|| (ICMSConstant.TEAM_TYPE_SC_MAKER_WFH == teamMembershipID)
					|| (ICMSConstant.TEAM_TYPE_SC_CHECKER == teamMembershipID)
					|| (ICMSConstant.TEAM_TYPE_SC_CHECKER_WFH == teamMembershipID)
					) {
				return false;
			}
		}
		return true;
	}

	public ArrayList[] getSortedCountryList(HashMap hmap) {
		ArrayList[] CountryListing = new ArrayList[2];
		CountryListing[0] = new ArrayList();
		CountryListing[1] = new ArrayList();
		ArrayList keys = new ArrayList();
		keys.addAll(hmap.keySet());
		Collections.sort(keys);
		Iterator countrylabels = keys.iterator();
		while (countrylabels.hasNext()) {
			String label = (String) countrylabels.next();
			String code = (String) hmap.get(label);
			CountryListing[0].add(code);
			CountryListing[1].add(label);
		}
		return CountryListing;
	}
}
