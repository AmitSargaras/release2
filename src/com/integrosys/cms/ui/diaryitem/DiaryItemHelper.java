/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemHelper.java,v 1.2 2005/11/13 12:06:04 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Helper class for Diary Item - UI related
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/11/13 12:06:04 $ Tag: $Name: $
 */
public class DiaryItemHelper {

	public static Collection getSortedCountryLabels(HashMap map) {

		String[] labels = (String[]) map.keySet().toArray(new String[0]);

		Arrays.sort(labels);

		Collection result = new ArrayList();

		for (int i = 0; i < labels.length; i++) {
			String label = labels[i];
			result.add(label);
		}
		return result;

	}

	public static Collection getSortedCountryValues(Collection labels, HashMap map) {
		ArrayList result = new ArrayList();

		for (Iterator iter = labels.iterator(); iter.hasNext();) {
			result.add(map.get(iter.next()));
		}
		return result;

	}

	/**
	 * prepares the list of country values and labels accessible by team
	 * @param team
	 * @return HashMap - country label and value pair
	 */
	public static HashMap getAllowedCountries(ITeam team) {
		String countriesAllowed[] = team.getCountryCodes();
		HashMap countryMap = new HashMap();
		CountryList countries = CountryList.getInstance();

		if(countriesAllowed!=null)
		{
		for (int i = 0; i < countriesAllowed.length; i++) {
			countryMap.put(countries.getCountryName(countriesAllowed[i]), countriesAllowed[i]);
		}
		}
		return countryMap;
	}

	public static boolean isNull(String s) {
		return ((s == null) || (s.trim().length() == 0)||s.equals("null"));
	}

	public static void time(long start, String method) {
		DefaultLogger.info("", "\n**************************************************");
		DefaultLogger.info("", "* Method - " + method + " took " + (System.currentTimeMillis() - start) + " ms *");
		DefaultLogger.info("", "\n*************************************************");
	}

}
