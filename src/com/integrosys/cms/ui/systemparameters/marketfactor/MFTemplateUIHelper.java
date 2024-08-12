/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.ui.systemparameters.marketfactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFItem;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.proxy.IPrPaProxyManager;
import com.integrosys.cms.app.propertyparameters.proxy.PrPaProxyManagerFactory;
import com.integrosys.cms.app.propertyparameters.trx.marketfactor.IMFTemplateTrxValue;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.component.commondata.app.CommonDataSingleton;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MFTemplateUIHelper {

	public IPrPaProxyManager getIPrPaProxyManager() {
		IPrPaProxyManager proxy = PrPaProxyManagerFactory.getProxyManager();
		return proxy;
	}

	public List deleteItem(List itemOrig, String[] deleteInd) {
		if ((itemOrig != null) && (deleteInd != null)) {
			List tempList = new ArrayList();
			for (int i = 0; i < deleteInd.length; i++) {
				int nextDelInd = Integer.parseInt(deleteInd[i]);
				tempList.add(itemOrig.get(nextDelInd));
			}
			itemOrig.removeAll(tempList);
			tempList.clear();
		}
		return itemOrig;
	}

	public IMFItem getCurWorkingMFItem(String event, String fromEvent, int index, IMFTemplateTrxValue trxValue) {
		IMFTemplate template = null;
		if (EventConstant.EVENT_READ.equals(fromEvent)) {
			template = trxValue.getMFTemplate();
		}
		else {
			template = trxValue.getStagingMFTemplate();
		}
		if (template != null) {
			IMFItem[] item = template.getMFItemList();
			if (item != null) {
				return item[index];
			}
		}
		return null;
	}

	public String getSecurityTypeDesc(String secCode) {
		return CommonDataSingleton.getCodeCategoryLabelByValue("31", secCode);
	}

	public void setSecuritySubTypeToList(ArrayList listValue, ArrayList listLabel, Locale locale)
			throws CollateralException {
		try {
			SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
			Collection fullList = subTypeList.getSecuritySubTypeProperty();

			// filter out sub type for property type only
			Iterator iter = fullList.iterator();
			HashMap subTypeLabelValMap = new HashMap();
			while (iter.hasNext()) {
				String subType = (String) iter.next();
				if ((subType != null)
						&& (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
					subTypeLabelValMap.put(subTypeList.getSecuritySubTypeValue(subType, locale), subType);
				}
			}

			// sort the list by label
			String[] subTypeLabel = (String[]) subTypeLabelValMap.keySet().toArray(new String[0]);
			Arrays.sort(subTypeLabel);

			listLabel.addAll(new ArrayList(Arrays.asList(subTypeLabel)));
			for (int i = 0; i < subTypeLabel.length; i++) {
				listValue.add(subTypeLabelValMap.get(subTypeLabel[i]));
			}

		}
		catch (CollateralException cex) {
			DefaultLogger.error(this, "", cex);
			throw cex;
		}
	}

}
