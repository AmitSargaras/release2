/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/country/SRPCountryMakerPrepareCommand.java,v 1.2 2003/09/10 09:33:45 dayanand Exp $
 */

package com.integrosys.cms.ui.srp.country;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.collateral.proxy.ICollateralProxy;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Prepares for editing Security Recovery Parameters via Country Specific
 * Parameters.
 * 
 * @author $Author: dayanand $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/10 09:33:45 $ Tag: $Name: $
 */
public class SRPCountryMakerPrepareCommand extends AbstractCommand {

	private ICollateralProxy collateralProxy;

	public void setCollateralProxy(ICollateralProxy collateralProxy) {
		this.collateralProxy = collateralProxy;
	}

	public ICollateralProxy getCollateralProxy() {
		return collateralProxy;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE } });
	}

	public String[][] getResultDescriptor() {
		return new String[][] { { "countries.values.list", "java.util.List", SERVICE_SCOPE },
				{ "countries.labels.list", "java.util.List", SERVICE_SCOPE },
				{ "collateral.types.list", "java.util.List", SERVICE_SCOPE } };
	}

	public HashMap doExecute(HashMap hashMap) throws CommandProcessingException {

		// Get the list of countries.
		CountryList countryList = CountryList.getInstance();
		List countryValuesList = new ArrayList(countryList.getCountryValues());
		List countryLabelsList = new ArrayList(countryList.getCountryLabels());

		try {
			// Get the list of security types.
			ICollateralType[] collateralTypeArr = getCollateralProxy().getAllCollateralTypes();

			// Produce the outputs to be put into scopes.
			HashMap resultMap = new HashMap(3);
			resultMap.put("countries.values.list", countryValuesList);
			resultMap.put("countries.labels.list", countryLabelsList);
			resultMap.put("collateral.types.list", Arrays.asList(collateralTypeArr));

			// cleanup previous cache.. if any
			resultMap.remove("CollateralParameterTrxValue");

			HashMap returnMap = new HashMap(2);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, new HashMap());
			return returnMap;

		}
		catch (CollateralException e) {
			throw new CommandProcessingException(e.toString());
		}
	}

}
