package com.integrosys.cms.ui.collateral.valuationfromlos;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.others.OthersAction;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Jul 6, 2007 Time: 6:34:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class OthersValuationFromLOSAction extends OthersAction {

	public ICommand[] getCommandChain(String event) {
		ICommand objArray[] = new ICommand[1];
		if (CollateralAction.EVENT_READ.equals(event)) {
			objArray[0] = new ReadValuationFromLOSCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return objArray;
	}
}
