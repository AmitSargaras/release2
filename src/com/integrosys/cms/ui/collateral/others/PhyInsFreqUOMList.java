/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/others/PhyInsFreqUOMList.java,v 1.1 2004/06/22 09:00:37 visveswari Exp $
 */
package com.integrosys.cms.ui.collateral.others;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author $Author: visveswari $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/06/22 09:00:37 $ Tag: $Name: $
 */
public class PhyInsFreqUOMList {

	private static ArrayList phyInsFreqUOMList;

	private static PhyInsFreqUOMList thisInstance;

	public synchronized static PhyInsFreqUOMList getInstance() {
		if (thisInstance == null) {
			thisInstance = new PhyInsFreqUOMList();
		}
		return thisInstance;
	}

	private PhyInsFreqUOMList() {
		phyInsFreqUOMList = new ArrayList();
		phyInsFreqUOMList.add("Day(s)");
		phyInsFreqUOMList.add("Week(s)");
		phyInsFreqUOMList.add("Month(s)");
		phyInsFreqUOMList.add("Year(s)");
	}

	public Collection getPhyInsFreqUOMList() {
		return phyInsFreqUOMList;
	}
}
