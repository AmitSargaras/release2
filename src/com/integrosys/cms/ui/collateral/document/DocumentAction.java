package com.integrosys.cms.ui.collateral.document;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.IPin;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.collateral.ProcessCollateralCommand;
import com.integrosys.cms.ui.collateral.ReadCollateralCommand;

/**
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2006/07/27 04:52:01 $ Tag: $Name: $
 */

public class DocumentAction extends CollateralAction implements IPin {

	public ICommand[] getCommandChain(String event) {

		ICommand objArray[] = null;
		if (EVENT_READ.equals(event) || EVENT_PREPARE_CLOSE.equals(event) || EVENT_TRACK.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ReadCollateralCommand();
			objArray[1] = new ReadDocumentCommand();
		}
		else if (EVENT_PROCESS.equals(event)) {
			objArray = new ICommand[2];
			objArray[0] = new ProcessCollateralCommand();
			objArray[1] = new ReadDocumentCommand();
		}
		else {
			objArray = super.getCommandChain(event);
		}

		return (objArray);
	}
}
