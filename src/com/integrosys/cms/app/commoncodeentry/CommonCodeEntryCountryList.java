/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeEntryCountryList.java
 *
 * Created on February 8, 2007, 11:42:29 AM
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
import java.util.StringTokenizer;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 8, 2007 Time: 11:42:29 AM To
 * change this template use File | Settings | File Templates.
 */
public class CommonCodeEntryCountryList {
	private static CommonCodeEntryCountryList instance = new CommonCodeEntryCountryList();

	private static Collection countryLabels = new ArrayList();

	private static Collection countryValues = new ArrayList();

	static {
		String labels = PropertyManager.getValue("common.code.entry.country.labels", "All|Malaysia|Singapore");
		String values = PropertyManager.getValue("common.code.entry.country.values", "ALL|MY|SG");

		StringTokenizer labelTokens = new StringTokenizer(labels, "|");
		StringTokenizer valueTokens = new StringTokenizer(values, "|");

		while (labelTokens.hasMoreTokens() && valueTokens.hasMoreTokens()) {

			countryLabels.add(labelTokens.nextToken());
			countryValues.add(valueTokens.nextToken());
		}
	}

	private CommonCodeEntryCountryList() {

	}

	public static CommonCodeEntryCountryList getInstance() {
		return instance;
	}

	public Collection getCountryLabels() {
		return countryLabels;
	}

	public Collection getCountryValues() {
		return countryValues;
	}

}
