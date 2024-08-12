package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.integrosys.base.businfra.contact.ICountry;
import com.integrosys.base.businfra.contact.SBCommonManager;
import com.integrosys.base.businfra.contact.SBCommonManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 9, 2003 Time: 5:29:11 PM
 * To change this template use Options | File Templates.
 */
public class CountryList {

	private static HashMap countryList; // country code, and country name

	private static HashMap countryCurrencyList; // country code, and currency

	// code

	private static CountryList thisInstance;

	private static Collection countryTypeLabel = new ArrayList();

	private static Collection countryTypeValue = new ArrayList();

	public synchronized static CountryList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CountryList();
			thisInstance.getCountryList();
		}
		return thisInstance;
	}

	private CountryList() {

	}

	public HashMap getCountryList() {

		if (countryList == null) {
			countryList = new HashMap();
			//Commented for HDFC :India Implementation
			/*
			if (countryCurrencyList == null) {
				countryCurrencyList = new HashMap();
			}

			ICountry[] list = this.getAllCountries();
			HashMap tempCountryList = new HashMap();
			for (int i = 0; i < list.length; i++) {
				countryList.put(list[i].getCountryCode(), list[i].getCountryName());

				countryCurrencyList.put(list[i].getCountryCode(), list[i].getCurrencyCode());
				// System.out.println(i + ": " + list[i].getCountryCode() + " "
				// + list[i].getCurrencyCode());

				countryTypeValue.add(list[i].getCountryCode());
				tempCountryList.put(list[i].getCountryName(), list[i].getCountryCode());
				countryTypeLabel.add(list[i].getCountryName());
			}

			String defaultCountry = UIUtil.readPropertyFile(ICMSConstant.DEFAULT_COUNTRY);
			if ((defaultCountry != null) && (defaultCountry.length() > 0)) {
				int index = ((ArrayList) countryTypeValue).indexOf(defaultCountry);
				String label = (String) ((ArrayList) countryTypeLabel).get(index);

				((ArrayList) countryTypeValue).remove(index);
				((ArrayList) countryTypeLabel).remove(index);

				((ArrayList) countryTypeValue).add(0, defaultCountry);
				((ArrayList) countryTypeLabel).add(0, label);
			}
		*/
			
			ICityProxyManager cityProxy =(ICityProxyManager)BeanHouse.get("cityProxy");
			List idList = (List) cityProxy.getCountryList(0);
			for (int i = 0; i < idList.size(); i++) {
				com.integrosys.cms.app.geography.country.bus.ICountry country = (com.integrosys.cms.app.geography.country.bus.ICountry) idList.get(i);
				if (country.getStatus().equals("ACTIVE")) {
					countryList.put(country.getCountryCode(), country.getCountryName());	
				}
			}
			
		}

		return countryList;
	}

	/**
	 * @return a list of countries
	 */
	private ICountry[] getAllCountries() {

		DefaultLogger.debug(this, "Entering getAllCountries");
		ArrayList countries = new ArrayList();

		try {
			SBCommonManager mgr = getCommonManager();
			Collection c = mgr.getAllCountries();
			Iterator i = c.iterator();
			while (i.hasNext()) {
				ICountry currency = ((ICountry) i.next());
				countries.add(currency);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error retrieving countries from SBCommonManager");
			e.printStackTrace();
			return null;
		}

		ICountry[] countryList = new ICountry[countries.size()];
		for (int i = 0; i < countries.size(); i++) {
			countryList[i] = (ICountry) countries.get(i);
		}
		if ((countryList != null) && (countryList.length != 0)) {
			Arrays.sort(countryList, new Comparator() {

				public int compare(Object thisObject, Object thatObject) {
					String thisCountryName = (thisObject) == null ? null : ((ICountry) thisObject).getCountryName();
					String thatCountryName = (thatObject) == null ? null : ((ICountry) thatObject).getCountryName();

					if (thisCountryName == null) {
						return (thatCountryName == null) ? 0 : -1;
					}

					return (thatCountryName == null) ? 1 : thisCountryName.compareTo(thatCountryName);
				}
			});
		}

		return countryList;
	}

	/**
	 * Helper method to get ejb object of data protection proxy session bean.
	 * 
	 * @return SBDataProtection proxy ejb object
	 */
	private SBCommonManager getCommonManager() throws Exception {
		DefaultLogger.debug(this, "Getting SBCommonManager");
		SBCommonManager mgr = (SBCommonManager) BeanController.getEJB(ICMSJNDIConstant.SB_COMMON_MANAGER_JNDI,
				SBCommonManagerHome.class.getName());

		if (mgr == null) {
			throw new Exception("SBCommonManager is null!");
		}
		return mgr;
	}

	/**
	 * The Collection which return all country names
	 * 
	 * @return Collection of Country Names
	 */

	public Collection getCountryLabels() {
		return countryTypeLabel;
	}

	/**
	 * The Collection which return codes for countries
	 * 
	 * @return Collection of Country Codes
	 */

	public Collection getCountryValues() {
		return countryTypeValue;
	}

	/**
	 * Method to return the country name based on supplied key
	 * 
	 * @param key code to get the corresponding country name
	 * @return String is the country Name
	 */

	public String getCountryName(String key) {
		if (!countryList.isEmpty()) {
			return (String) countryList.get(key);
		}
		else {
			return "";
		}
	}

	public String getCurrencyName(String key) {
		if (!countryCurrencyList.isEmpty()) {
			return (String) countryCurrencyList.get(key);
		}
		else {
			return "";
		}
	}

}
