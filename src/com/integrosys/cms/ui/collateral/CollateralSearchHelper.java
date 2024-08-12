package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.ui.common.SecurityTypeList;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 12, 2007 Time: 8:01:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class CollateralSearchHelper {

	public CollateralSearchHelper() {
	}

	public static HashMap getSecurityTyes(Locale locale) {
		HashMap output = new HashMap();
		List securityTypeCode = new ArrayList();
		List SecurityTypeLabel = new ArrayList();

		try {
			SecurityTypeList secTypeList = SecurityTypeList.getInstance();
			Collection list = secTypeList.getSecurityTypeProperty();

			Iterator itr = list.iterator();
			while (itr.hasNext()) {
				securityTypeCode.add(itr.next());
			}
			list = secTypeList.getSecurityTypeLabel(locale);
			itr = list.iterator();
			while (itr.hasNext()) {
				SecurityTypeLabel.add(itr.next());
			}
			output.put("securityTypeCode", securityTypeCode);
			output.put("SecurityTypeLabel", SecurityTypeLabel);

		}
		catch (Exception e) {
			return null;
		}
		return output;
	}

	public HashMap getSecuritySubTyes(String secType, Locale locale) {
		HashMap output = new HashMap();
		List subTypeCode = new ArrayList();
		List subTypeLabel = new ArrayList();
		try {
			if (secType != null) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				HashMap resultMap = proxy.getSecuritySubTypes(secType);
				if ((resultMap != null) && !resultMap.isEmpty()) {
					subTypeLabel = (List) resultMap.get("subTypeLabel");
					subTypeCode = (List) resultMap.get("subTypeCode");
				}
			}
			output.put("subTypeCode", subTypeCode);
			output.put("subTypeLabel", subTypeLabel);

		}
		catch (Exception e) {
			return null;
		}
		return output;
	}
}
