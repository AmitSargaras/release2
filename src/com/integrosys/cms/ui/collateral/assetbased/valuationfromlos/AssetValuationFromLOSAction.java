package com.integrosys.cms.ui.collateral.assetbased.valuationfromlos;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.assetbased.assetspecother.AssetSpecOtherAction;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 3, 2007 Time: 8:56:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssetValuationFromLOSAction extends AssetSpecOtherAction {

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