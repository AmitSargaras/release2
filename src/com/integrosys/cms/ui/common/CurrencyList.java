package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.base.businfra.contact.SBCommonManager;
import com.integrosys.base.businfra.contact.SBCommonManagerHome;
import com.integrosys.base.businfra.currency.ICurrency;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jul 9, 2003 Time: 5:29:26 PM
 * To change this template use Options | File Templates.
 */
public class CurrencyList {

	private static HashMap currencyList; // currency code,

	private static HashMap countryCurMapping; // for retrieving default currency
												// for country

	private static CurrencyList thisInstance;

	private ArrayList currencyTypeLabel = new ArrayList();

	private ArrayList currencyTypeValue = new ArrayList();

	/**
	 * @return a list of currencies
	 */
	public ICurrency[] getAllCurrencies() {

		DefaultLogger.debug(this, "Entering getAllCurrencies");

		ArrayList currencies = new ArrayList();
		try {
			SBCommonManager mgr = getCommonManager();
			Collection c = mgr.getAllCurrencies();
			Iterator i = c.iterator();
			while (i.hasNext()) {
				ICurrency currency = ((ICurrency) i.next());
				if ("XXX".equalsIgnoreCase(currency.getCurrencyCode())) {
					DefaultLogger.debug(this, " Filter out the XXX currency code !");
				}
				else {
					currencies.add(currency);
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error retrieving currencies from SBCommonManager");
			e.printStackTrace();
			return null;
		}

		ICurrency[] currencyList = new ICurrency[currencies.size()];
		for (int i = 0; i < currencies.size(); i++) {
			currencyList[i] = (ICurrency) currencies.get(i);
		}
		return currencyList;
	}

	public synchronized static CurrencyList getInstance() {
		if (thisInstance == null) {
			thisInstance = new CurrencyList();
		}
		return thisInstance;
	}

	private CurrencyList() {
		getCurrencyList();

	}
	
	public CurrencyList(String[] currList) {
		currencyList = new HashMap();
		if (currList != null) {
			for (int i = 0; i < currList.length; i++) {
				String value = currList[i];
				currencyList.put(value, value);
				currencyTypeValue.add(value);
				currencyTypeLabel.add(value);
			}
		}
	}

	public HashMap getCurrencyList() {
		DefaultLogger.debug(this, "Entering getCountryList");
		if (currencyList == null) {
			currencyList = new HashMap();
			countryCurMapping = new HashMap();
			ICurrency[] list = this.getAllCurrencies();
			Arrays.sort(list, new CurrencyComparator());

			for (int i = 0; i < list.length; i++) {
				// Consider using getCurrencyDescription
				currencyList.put(list[i].getCurrencyCode(), list[i].getCurrencyDescription());
				countryCurMapping.put(list[i].getCountryCode(), list[i].getCurrencyCode());
				currencyTypeValue.add(list[i].getCurrencyCode());
				currencyTypeLabel.add(list[i].getCurrencyCode());
			}

			String defaultCurrency = UIUtil.readPropertyFile(ICMSConstant.DEFAULT_CURRENCY);
			if ((defaultCurrency != null) && (defaultCurrency.length() > 0)) {
				int index = ((ArrayList) currencyTypeValue).indexOf(defaultCurrency);
				String label = "";
				if(currencyTypeLabel.size() != 0) {
					label = (String) ((ArrayList) currencyTypeLabel).get(index);
				((ArrayList) currencyTypeValue).remove(index);
				((ArrayList) currencyTypeLabel).remove(index);
				}

				((ArrayList) currencyTypeValue).add(0, defaultCurrency);
				((ArrayList) currencyTypeLabel).add(0, label);
			}

		}

		DefaultLogger.debug(this, "Exiting getCountryList");
		return currencyList;
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

	public Collection getCurrencyLabels() {
		return currencyTypeLabel;
	}

	/**
	 * The Collection which return codes for countries
	 * 
	 * @return Collection of Country Codes
	 */

	public Collection getCountryValues() {
		return currencyTypeValue;
	}

	/**
	 * Method to return the country name based on supplied key
	 * 
	 * @param key code to get the corresponding country name
	 * @return String is the country Name
	 */

	public String getCountryName(String key) {
		if (!currencyList.isEmpty()) {
			return (String) currencyList.get(key);
		}
		else {
			return "";
		}
	}

	public String getCurrencyCodeByCountry(String country) {
		if (countryCurMapping != null) {
			return (String) (countryCurMapping.get(country));
		}
		else {
			return null;
		}
	}
}
