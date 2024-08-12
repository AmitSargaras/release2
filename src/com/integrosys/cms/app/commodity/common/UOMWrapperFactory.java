/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/common/UOMWrapperFactory.java,v 1.4 2004/07/02 11:45:18 wltan Exp $
 */
package com.integrosys.cms.app.commodity.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.uom.IUnitofMeasure;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.BaseList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/07/02 11:45:18 $ Tag: $Name: $
 */
public class UOMWrapperFactory extends BaseList { // -------

	private static UOMWrapperFactory theInstance;

	private static Date createdDate;

	private static String[] COMMON_CODE_CATEGORY_NAMES = new String[] {
			ICMSConstant.CATEGORY_COMMODITY_METRIC_MARKET_UOM, ICMSConstant.CATEGORY_COMMODITY_OTHER_MARKET_UOM };

	static {
		theInstance = new UOMWrapperFactory();
	}

	/**
	 * Constructs an instance of UOMWrapperFactory.
	 */
	private UOMWrapperFactory() {
		// ------
		super(); // call super class constructor to register a time stamp
		createdDate = super.getLastDate();
		// ------

	}

	/**
	 * Gets an instance of UOMWrapperFactory.
	 * 
	 * @return
	 */
	public static UOMWrapperFactory getInstance() {

		if (theInstance == null) {
			theInstance = new UOMWrapperFactory();
		}
		// ------
		else {
			Date current = new Date();
			if (toRefresh(current, createdDate)) {
				theInstance = null;
				theInstance = new UOMWrapperFactory();
				// setLastDate(current);
			}
		}
		// ------

		return theInstance;
	}

	/**
	 * Gets a unit of measure wrapper given an ID. It can be the ID of a common
	 * unit of measure or the ID of a commodity unit of measure.
	 * 
	 * @param anID - String representing the ID of the unit of measure to get
	 * @return unit of measure wrapper
	 * @return null if no matching uom ID found
	 */
	public UOMWrapper valueOf(String anID) {
		// check if the ID can be found in the list of common uom
		for (int i = 0; i < COMMON_CODE_CATEGORY_NAMES.length; i++) {
			HashMap commonUOMMap = CommonDataSingleton.getCodeCategoryValueLabelMap(COMMON_CODE_CATEGORY_NAMES[i]);
			String uomLabel = (String) commonUOMMap.get(anID);
			if ((uomLabel != null) && (uomLabel.length() > 0)) {
				// uom is a common uom
				return getUOM(anID, uomLabel);
			}
		}

		try {
			// get the commodity uom
			long uomID = Long.parseLong(anID);
			return CommodityMainInfoManagerFactory.getManager().getUnitofMeasureByID(uomID);
		}
		catch (Exception nfe) {
			return null;
		}
	}

	/**
	 * Gets the comon unit of measure wrapper given the id and label
	 * 
	 * @param anID - String representing the id of the unit of measure
	 * @param aLabel = String representing the lable of the unit of measure
	 * @return unit of measure wrapper
	 */
	public UOMWrapper getUOM(String anID, String aLabel) {
		// return new CommonUOMWrapper(anID, aLabel);
		return new UOMWrapper(anID, aLabel);
	}

	/**
	 * Gets the commodity unit of measure wrapper given the IUnitofMeasure.
	 * 
	 * @param anUOM - IUnitofMeasure
	 * @return unit of measure wrapper
	 */
	public UOMWrapper getUOM(IUnitofMeasure anUOM) {
		// return new CommodityUOMWrapper(anUOM);
		return new UOMWrapper(anUOM);
	}

	/**
	 * Gets collection of common uom to be used for commodities.
	 * 
	 * @return Collection of UOMWrapper for common unit of measure
	 */
	public Collection getCommonUOM() {
		ArrayList uoms = new ArrayList();
		for (int idx = 0; idx < COMMON_CODE_CATEGORY_NAMES.length; idx++) {
			String commonCodeCategoryName = COMMON_CODE_CATEGORY_NAMES[idx];
			HashMap commonUOMMap = CommonDataSingleton.getCodeCategoryValueLabelMap(commonCodeCategoryName);
			Iterator i = commonUOMMap.keySet().iterator();
			while (i.hasNext()) {
				String code = (String) i.next();
				String label = (String) commonUOMMap.get(code);
				UOMWrapper uom = UOMWrapperFactory.getInstance().getUOM(code, label);
				uoms.add(uom);
			}
		}
		return uoms;
	}
}
