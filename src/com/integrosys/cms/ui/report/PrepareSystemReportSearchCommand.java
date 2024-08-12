package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * Description: This command prepares the input form for searching of MIS
 * Reports. It will populate the list of countries for selection The selected
 * country and entered date is used to filter out the reports of interest
 * 
 * @author $Author: rohaidah $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/08 10:53:36 $ Tag: $Name: $
 */
public class PrepareSystemReportSearchCommand extends AbstractCommand {
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {

		// returns a list of countries for selection
		return (new String[][] { { "CountryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "CountryLabels", "java.util.Collection", REQUEST_SCOPE }, });
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE } });
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

		// get country list and labels
		CountryList countries = CountryList.getInstance();
		Collection countryLabels = new ArrayList(countries.getCountryLabels());
		Collection countryValues = new ArrayList(countries.getCountryValues());

		Collection sortedCountryLabels = new ArrayList();
		Collection sortedCountryValues = new ArrayList();

		// get the countries this user is allowed
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
		if (team != null) {
			// get the allowed countries
			HashMap countriesHash = getAllowedCountries(team);

			// to map the country labels and values
			HashMap hshMp = mapCountriesAndLabels(countryLabels, countryValues);

			Set keySet = hshMp.keySet();
			Iterator keyI = keySet.iterator();
			while (keyI.hasNext()) {
				String key = (String) keyI.next();
				if (!countriesHash.containsKey(key)) {
					keyI.remove();
				}
			}
			Collection lableCollection = new Vector();
			Collection valueCollection = new Vector();

			HashMap sortHash = new HashMap();

			Iterator afterRemoval = keySet.iterator();
			while (afterRemoval.hasNext()) {
				String keys = (String) afterRemoval.next();
				lableCollection.add((String) hshMp.get(keys));
				valueCollection.add(keys);
				sortHash.put((String) hshMp.get(keys), keys);
			}
			countryLabels = lableCollection;
			countryValues = valueCollection;

			String[] tempCountryName = (String[]) countryLabels.toArray(new String[0]);
			Arrays.sort(tempCountryName);
			sortedCountryLabels = Arrays.asList(tempCountryName);
			for (int i = 0; i < tempCountryName.length; i++) {
				sortedCountryValues.add((String) sortHash.get(tempCountryName[i]));
			}

			countryLabels = sortedCountryLabels;
			countryValues = sortedCountryValues;
		}

		if ((countryLabels == null) || (countryValues == null)) {
			throw new CommandProcessingException("Failed to retrieve country names and labels");
		}

		result.put("CountryValues", countryValues);
		result.put("CountryLabels", countryLabels);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

	private HashMap getAllowedCountries(ITeam team) {
		String countriesAllowed[] = team.getCountryCodes();
		HashMap countriesHash = new HashMap();
		for (int i = 0; i < countriesAllowed.length; i++) {
			countriesHash.put(countriesAllowed[i], countriesAllowed[i]);
		}
		return countriesHash;
	}

	private HashMap mapCountriesAndLabels(Collection countryLabels, Collection countryValues) {
		HashMap hshMp = new HashMap();
		Iterator labelI = countryLabels.iterator();
		Iterator valueI = countryValues.iterator();
		while (valueI.hasNext()) {
			String value1 = (String) valueI.next();
			String label1 = (String) labelI.next();
			hshMp.put(value1, label1);
		}
		return hshMp;
	}

}
