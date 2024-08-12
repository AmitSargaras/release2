/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/common/RegionList.java,v 1.2 2003/09/17 03:54:09 btchng Exp $
 */

package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.concentrationreport.bus.IRegion;
import com.integrosys.cms.app.concentrationreport.proxy.ConcReportProxyFactory;
import com.integrosys.cms.app.concentrationreport.proxy.IConcReportProxy;

/**
 * Convenience list for region information.
 * @author $Author: btchng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/17 03:54:09 $ Tag: $Name: $
 */
public class RegionList {

	private RegionList() {

	}

	public static RegionList getInstance() {

		if (regionList == null) {

			regionList = new RegionList();
			IConcReportProxy proxy = ConcReportProxyFactory.getProxy();
			IRegion[] regionsArr = proxy.getAllRegions();

			for (int i = 0; i < regionsArr.length; i++) {
				regionList.addRegion(regionsArr[i]);
			}
		}

		DefaultLogger.debug(RegionList.class.getName(), regionList.regionCodeNameMap);

		return regionList;

	}

	/**
	 * Gets the labels in the form of region names. Note that they are NOT
	 * region descriptions.
	 * @return The collection of region names as labels. If there are no labels,
	 *         this collection is zero in length.
	 */
	public Collection getRegionLabels() {
		return regionNames;
	}

	/**
	 * Gets the values in the form of region codes.
	 * @return The collection of region codes as values. If there are no values,
	 *         this collection is zero in length.
	 */
	public Collection getRegionValues() {
		return regionCodes;
	}

	/**
	 * Gets the region name based on the region code.
	 * @param regionCode The region code.
	 * @return The region name for the region code. If there is no such region
	 *         code, then <code>null</code> is returned. If region code is
	 *         present but region name is not, empty string is returned.
	 */
	public String getRegionName(String regionCode) {
		if (regionCodeNameMap.containsKey(regionCode)) {
			Object obj = regionCodeNameMap.get(regionCode);
			if (obj == null) {
				return "";
			}
			else {
				return (String) obj;
			}
		}
		else {
			return null;
		}
	}

	/**
	 * Gets the region description for the region code.
	 * @param regionCode The region code.
	 * @return The region description for the region code. If there is no such
	 *         region code, <code>null</code> is returned. If region code is
	 *         present but region description is not, empty string is returned.
	 */
	public String getRegionDescription(String regionCode) {
		if (regionCodeDescMap.containsKey(regionCode)) {
			Object obj = regionCodeDescMap.get(regionCode);
			if (obj == null) {
				return "";
			}
			else {
				return (String) obj;
			}
		}
		else {
			return null;
		}
	}

	private void addRegion(IRegion region) {
		String code = region.getCode();
		String name = region.getName();
		String description = region.getDescription();
		regionCodeNameMap.put(code, name);
		regionCodeDescMap.put(code, description);
		regionNames.add(name);
		regionCodes.add(code);
		regionDescriptions.add(description);

	}

	private static RegionList regionList;

	private HashMap regionCodeNameMap = new HashMap();

	private HashMap regionCodeDescMap = new HashMap();

	private Collection regionNames = new ArrayList();

	private Collection regionCodes = new ArrayList();

	private Collection regionDescriptions = new ArrayList();

}
