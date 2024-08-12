/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/PrepareDiaryItemCommand.java,v 1.2 2005/07/06 11:36:40 jtan Exp $
 */
package com.integrosys.cms.ui.diaryitem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.IDiaryItem;
import com.integrosys.cms.app.diary.bus.IDiaryItemJdbc;
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.app.diary.proxy.IDiaryItemProxyManager;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * This command populates the country list that the user has access to A diary
 * item created may be viewed/filtered by country and user team
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/07/06 11:36:40 $ Tag: $Name: $
 */

public class PrepareDiaryItemCommand extends DiaryItemsCommand {

	public String[][] getParameterDescriptor() {
		return (new String[][] { { IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
				GLOBAL_SCOPE },
		{ "activityList", "java.util.List", REQUEST_SCOPE },
		{ "makerId", "java.lang.String", REQUEST_SCOPE },
		{ "makerDate", "java.lang.String", REQUEST_SCOPE },
		{IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE},
		{ "customerName", "java.lang.String", REQUEST_SCOPE},
		{ "dropLineOD", "java.lang.String", REQUEST_SCOPE},
		{ "odScheduleUploadFile", "org.apache.struts.upload.FormFile", REQUEST_SCOPE},
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "activityList", "java.util.List", REQUEST_SCOPE },
				{ "CountryValues", "java.util.Collection", REQUEST_SCOPE },
				{ "CountryLabels", "java.util.Collection", REQUEST_SCOPE } ,
				{ "activityList", "java.util.List", SERVICE_SCOPE },
				{ "makerId", "java.lang.String", REQUEST_SCOPE },
				{ "makerId", "java.lang.String", SERVICE_SCOPE },
				{ "makerDate", "java.lang.String", SERVICE_SCOPE },	
				{ "makerDate", "java.lang.String", REQUEST_SCOPE },	
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "dropLineOD", "java.lang.String", REQUEST_SCOPE},
				{ "dropLineOD", "java.lang.String", SERVICE_SCOPE },
				{ "odScheduleUploadFile", "org.apache.struts.upload.FormFile", REQUEST_SCOPE},
				});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap. The country values and labels that a user
	 * has access to will be populated into the drop down list for selection
	 * 
	 * @param map is of type HashMap
	 * @throws CommandProcessingException on errors
	 * @throws CommandValidationException on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {

			ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);

			if (team == null) {
				throw new CommandProcessingException("Team information is null!");
			}

			HashMap reverseCountryMap = getAllowedCountries(team);

			Collection countryLabels = getSortedCountryLabels(reverseCountryMap);

			Collection countryValues = getSortedCountryValues(countryLabels, reverseCountryMap);
			
			
			IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			Date dateApplication=new Date();
			long ladGenIndicator=0l;
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					dateApplication=new Date(generalParamEntries[i].getParamValue());
				}
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			
			
			//IDiaryItem item = (IDiaryItem) map.get("diaryItemObj");
			IDiaryItemJdbc diaryItemJdbc=(IDiaryItemJdbc)BeanHouse.get("diaryItemJdbc");
			String customerName = (String)map.get("customerName");
			String dropLineOD = (String)map.get("dropLineOD");
			FormFile odScheduleUploadFile = (FormFile)map.get("odScheduleUploadFile");
			result.put("makerId",user.getLoginID());
			result.put("activityList", getActivityList(diaryItemJdbc));
			result.put("makerDate", dateFormat.format(DateUtil.getDate()));
			result.put("odScheduleUploadFile", odScheduleUploadFile);
			result.put("CountryValues", countryValues);
			result.put("CountryLabels", countryLabels);
			if(customerName == null) {
			result.put("customerObject", null);
			}
			result.put("dropLineOD", dropLineOD);
		}
		catch (Exception ex) {
			DefaultLogger.debug(this, ex);
			throw new CommandProcessingException(ex.getMessage()); 	
		}
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;
	}

	private Collection getSortedCountryLabels(HashMap map) {

		String[] labels = (String[]) map.keySet().toArray(new String[0]);

		Arrays.sort(labels);

		Collection result = new ArrayList();

		for (int i = 0; i < labels.length; i++) {
			String label = labels[i];
			result.add(label);
		}
		return result;

	}

	private Collection getSortedCountryValues(Collection labels, HashMap map) {
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
	private HashMap getAllowedCountries(ITeam team) {
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
	
	private List getActivityList(IDiaryItemJdbc diaryItemJdbc) {
		List lbValList = new ArrayList();
		try {
				//MISecurityUIHelper helper = new MISecurityUIHelper();
				List activityList = diaryItemJdbc.getActivityList();
				
					for (int i = 0; i < activityList.size(); i++) {
						String [] str = (String[]) activityList.get(i);
						String id = str[0];
						String value = str[1];
						LabelValueBean lvBean = new LabelValueBean(value, id);
						lbValList.add(lvBean);
					}
		}
		catch (Exception ex) {
		}
		return CommonUtil.sortDropdown(lbValList);
	}

}
