/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/property/PhyInsFreqUOMList.java,v 1.1 2003/07/28 11:54:26 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.property;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/28 11:54:26 $ Tag: $Name: $
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
