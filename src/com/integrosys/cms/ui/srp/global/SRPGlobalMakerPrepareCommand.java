/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalMakerPrepareCommand.java,v 1.1 2003/08/18 08:39:07 dayanand Exp $
 */

package com.integrosys.cms.ui.srp.global;

import java.util.Arrays;
import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;

/**
 * Prepares for editing Security Recovery Parameters via Country Specific
 * Parameters.
 * 
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/18 08:39:07 $ Tag: $Name: $
 */
public class SRPGlobalMakerPrepareCommand extends AbstractCommand {

	private ICollateralProxy collateralProxy;

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "collateral.types.list", "java.util.List", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		try {
			// Get the list of security types.
			ICollateralType[] collateralTypeArr = getCollateralProxy().getAllCollateralTypes();

			// Produce the outputs to be put into scopes.
			HashMap resultMap = new HashMap(3);
			resultMap.put("collateral.types.list", Arrays.asList(collateralTypeArr));

			HashMap returnMap = new HashMap(2);
			returnMap.put(COMMAND_RESULT_MAP, resultMap);
			returnMap.put(COMMAND_EXCEPTION_MAP, new HashMap());
			return returnMap;

		}
		catch (CollateralException e) {
			throw new CommandProcessingException(e.toString());
		}
	}

}
