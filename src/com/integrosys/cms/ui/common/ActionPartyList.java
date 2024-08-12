package com.integrosys.cms.ui.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 10, 2003 Time: 12:03:45 PM
 * To change this template use Options | File Templates.
 */
public class ActionPartyList {
	private ActionPartyList() {

	}

	static ActionPartyList actionPartyList = null;

	private static HashMap hActionPartyList = new HashMap();

	private static Collection hActionPartyLabel = new ArrayList();

	private static Collection hActionPartyValue = new ArrayList();

	public static ActionPartyList getInstance() {

		if (actionPartyList == null) {
			actionPartyList = new ActionPartyList();
			hActionPartyList.put("RM", "RM");
			hActionPartyList.put("CPC CHECKER", "CPC CHECKER");
			hActionPartyList.put("CPC/VAULT", "CPC/VAULT");
			hActionPartyList.put("CUSTOMER", "CUSTOMER");
			hActionPartyList.put("LAWYER", "LAWYER");
			hActionPartyList.put("GOVT OFFICE", "GOVT OFFICE");
			hActionPartyValue.add("RM");
			hActionPartyValue.add("CPC CHECKER");
			hActionPartyValue.add("CPC/VAULT");
			hActionPartyValue.add("CUSTOMER");
			hActionPartyValue.add("LAWYER");
			hActionPartyValue.add("GOVT OFFICE");
			hActionPartyLabel.add("RM");
			hActionPartyLabel.add("CPC CHECKER");
			hActionPartyLabel.add("CPC/VAULT");
			hActionPartyLabel.add("CUSTOMER");
			hActionPartyLabel.add("LAWYER");
			hActionPartyLabel.add("GOVT OFFICE");
			DefaultLogger.debug("Action Party type List ---->", hActionPartyList);
		}
		return actionPartyList;
	}

	public Collection getActionPartyLabel() throws CollateralException {
		return hActionPartyLabel;
	}

	public Collection getActionPartyProperty() throws CollateralException {
		return hActionPartyValue;
	}

	public String getActionPartyValue(String key) {
		if (!hActionPartyList.isEmpty()) {
			return (String) hActionPartyList.get(key);
		}
		else {
			return "";
		}
	}

	public Collection getValueList() {
		return hActionPartyValue;
	}

	public Collection getKeyList() {
		return hActionPartyLabel;
	}

}
