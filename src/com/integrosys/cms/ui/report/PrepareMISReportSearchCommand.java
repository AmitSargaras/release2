package com.integrosys.cms.ui.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.bus.OBCodeCategoryEntry;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/report/PrepareMISReportSearchCommand.java,v 1.6 2006/11/17 07:53:50 jychong Exp $
 */

/**
 * Description: This command prepares the input form for searching of MIS
 * Reports. It will populate the list of countries for selection The selected
 * country and entered date is used to filter out the reports of interest
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/11/17 07:53:50 $ Tag: $Name: $
 */
public class PrepareMISReportSearchCommand extends ReportCommandAccessor implements ICommand, ICommonEventConstant {
	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {

		// returns a list of countries for selection
		return (new String[][] {
                { "CountryValues", "java.util.Collection", REQUEST_SCOPE },
                { "CountryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "CountryLabels", "java.util.Collection", REQUEST_SCOPE },
				{ "CentreValues", "java.util.Collection", REQUEST_SCOPE },
				{ "CentreLabels", "java.util.Collection", REQUEST_SCOPE },
                { "countryCode", "java.lang.String", REQUEST_SCOPE },
         });
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "legalName", "java.lang.String", REQUEST_SCOPE },
				{ "startIndex", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "locale", "java.util.Locale", REQUEST_SCOPE },
                { "countryCode", "java.lang.String", REQUEST_SCOPE },
        });
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

		Collection sortedCountryLabels = new ArrayList();
		Collection sortedCountryValues = new ArrayList();
		Collection sortedCentreValues = new ArrayList();
		Collection sortedCentreLabels = new ArrayList();

        String countryCode = (String) map.get("countryCode") ;

        // get the countries this user is allowed
		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

		if (team != null) {
			HashMap countryLabelValMap = new HashMap();
			String countriesCodeAccessed[] = team.getCountryCodes();
			for (int i = 0; i < countriesCodeAccessed.length; i++) {
				countryLabelValMap.put(countries.getCountryName(countriesCodeAccessed[i]), countriesCodeAccessed[i]);
			}


            String[] countriesLabelAccessed = (String[]) countryLabelValMap.keySet().toArray(new String[0]);


            
            Arrays.sort(countriesLabelAccessed);

			sortedCountryLabels = Arrays.asList(countriesLabelAccessed);
			for (int i = 0; i < countriesLabelAccessed.length; i++) {
				sortedCountryValues.add(countryLabelValMap.get(countriesLabelAccessed[i]));
			}

            OBCodeCategoryEntry[] centreCodes =  getReportRequestManager().getCentreCodesByTeamID(team.getTeamID(),countryCode);
            if (centreCodes != null && centreCodes.length > 0){
                DefaultLogger.debug(this, "**********centreCodes= " + centreCodes.length + "**********");
                for (int i = 0; i < centreCodes.length; i++) {
                    sortedCentreValues.add(centreCodes[i].getEntryCode())  ;
                    sortedCentreLabels.add(centreCodes[i].getEntryName()) ;
               }

                
            }else{
               DefaultLogger.debug(this, "**********centreCodes = null ");
             }
            
        }

		if ((sortedCountryLabels.size() == 0) || (sortedCountryValues.size() == 0)) {
			throw new CommandProcessingException("Failed to retrieve country names and labels");
		}

        if (sortedCentreValues == null) {
            sortedCentreValues = new ArrayList();
        }

         if (sortedCentreLabels == null) {
            sortedCentreLabels = new ArrayList();
        }
        result.put("countryCode", countryCode);
        result.put("CentreValues", sortedCentreValues);
		result.put("CentreLabels", sortedCentreLabels);

        result.put("CountryValues", sortedCountryValues);
		result.put("CountryLabels", sortedCountryLabels);
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}
}