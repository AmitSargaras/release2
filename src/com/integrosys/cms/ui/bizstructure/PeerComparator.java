package com.integrosys.cms.ui.bizstructure;

import java.util.Comparator;

import com.integrosys.component.bizstructure.app.bus.ITeamType;

/**
 * @author $Author: ravi $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/07 05:04:05 $ Tag: $Name: $
 */

public class PeerComparator implements Comparator {
	public int compare(Object o1, Object o2) {
		ITeamType f1 = (ITeamType) o1;
		ITeamType f2 = (ITeamType) o2;
		if (f1.getPeerOrder() - f2.getPeerOrder() == 0) {
			return 0;
		}
		else if (f1.getPeerOrder() > f2.getPeerOrder()) {
			return 1;
		}
		else {
			return -1;
		}
	}

}