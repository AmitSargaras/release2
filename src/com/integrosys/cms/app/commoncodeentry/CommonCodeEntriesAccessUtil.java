/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * EntriesAccessUtil.java
 *
 * Created on February 12, 2007, 10:11:41 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManager;
import com.integrosys.cms.app.commoncodeentry.bus.SBCommonCodeEntryManagerHome;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 12, 2007 Time: 10:11:41 AM To
 * change this template use File | Settings | File Templates.
 */
public final class CommonCodeEntriesAccessUtil {

	public static String COUNTRY_TYPE = "COUNTRY";

	public static String STATE_TYPE = "STATE";

	public static String DISTRICT_TYPE = "DISTRICT";

	public static String MUKIM_TYPE = "MUKIM";

	private static CommonCodeEntriesAccessUtil self = new CommonCodeEntriesAccessUtil();

	public static CommonCodeEntriesAccessUtil getInstance() {
		return self;
	}

	public HashMap getCountryValusAndLabels() {
		try {
			SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();

			return manager.getValuesAndLabelsForCountry();
		}
		catch (Exception e) {
			e.printStackTrace();

			DefaultLogger.debug(this, "Error retreiving country labels and values, returning empty has map");
		}

		return new HashMap();
	}

	public HashMap getValuesAndLabelsForCodes(String loaction_type, String[] codes) {
		HashMap map = new HashMap();

		if ((codes == null) || (codes.length <= 0)) {
			DefaultLogger.debug(this, "Size of codes array is zero or null");

			return map;
		}

		SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();

		try {
			if (COUNTRY_TYPE.equals(loaction_type)) {
				return manager.getValuesAndLabelsForCountry();
			}
			else if (STATE_TYPE.equals(loaction_type)) {
				return manager.getValuesAndLabelsForStates(codes);
			}
			else if (DISTRICT_TYPE.equals(loaction_type)) {
				return manager.getValuesAndLabelsForDistrict(codes);
			}
			else if (MUKIM_TYPE.equals(loaction_type)) {
				return manager.getValuesAndLabelsForMukim(codes);
			}

			DefaultLogger.debug(this,
					"Location type provided does not match any preset setting ! , returning an empty hashmap");
		}
		catch (Exception e) {
			e.printStackTrace();

			DefaultLogger.debug(this, "Error finding data for settings LOCATION_TYPE : " + loaction_type);
			DefaultLogger.debug(this, "Size of array codes provided : " + codes.length);

			for (int loop = 0; loop < codes.length; loop++) {
				DefaultLogger.debug(this, "Data in array codes [ " + loop + " ]  : " + codes[loop]);
			}

			DefaultLogger.debug(this, "Returning an empty hashmap instead");
		}

		return map;
	}

	public Collection getOBCollectionForCodeAndReference(String categoryCode, String refCode) {
		Collection coll = new ArrayList();
		SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();

		DefaultLogger.debug(this, "Retrieving OB entries for category code : " + categoryCode);
		DefaultLogger.debug(this, "Retrieving OB entries for reference code : " + refCode);

		try {
			return manager.getOBCollectionForCodeAndReference(categoryCode, refCode);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error finding data , returning an empty array list instead", e);
		}

		return coll;
	}

	public HashMap getValuesAndLabelsForCodeAndReference(String categoryCode, String refCode) {
		HashMap map = new HashMap();
		SBCommonCodeEntryManager manager = getSBCommonCodeEntryManager();

		DefaultLogger.debug(this, "Retrieving entries for category code : " + categoryCode);
		DefaultLogger.debug(this, "Retrieving entries for reference code : " + refCode);

		try {
			return manager.getValuesAndLabelsForCodeAndReference(categoryCode, refCode);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "Error finding data , returning an empty hash map instead", e);
		}

		return map;
	}

	protected final SBCommonCodeEntryManager getSBCommonCodeEntryManager() {
		Object obj = BeanController.getEJB(ICMSJNDIConstant.SB_COMMON_CODE_MANAGER_ENTRY_HOME,
				SBCommonCodeEntryManagerHome.class.getName());

		if (obj == null) {
			DefaultLogger.debug(this,
					"Unable to locate SBCommonCodeEntryManager , object returned by bean controller is null !");

			throw new NullPointerException("Unable to locate SBCommonCodeEntryManager");
		}

		return (SBCommonCodeEntryManager) obj;
	}

}
