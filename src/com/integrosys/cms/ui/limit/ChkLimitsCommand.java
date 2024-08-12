/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/limit/ChkLimitsCommand.java,v 1.5 2003/09/02 10:22:39 pooja Exp $
 */

package com.integrosys.cms.ui.limit;

//---------------------------------/
//- Imported classes and packages -/
//---------------------------------/

import java.util.ArrayList;
import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.bus.OBBookingLocation;
import com.integrosys.cms.app.limit.bus.OBCollateralAllocation;
import com.integrosys.cms.app.limit.bus.OBLimit;
import com.integrosys.cms.app.limit.bus.OBLimitProfile;

/**
 * This class is used to list the customer details based on some search
 * contsraints
 * @author $Author: pooja $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/09/02 10:22:39 $ Tag: $Name: $
 */
public class ChkLimitsCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public ChkLimitsCommand() {

	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
		// {"customerSearchCriteria",
		// "com.integrosys.cms.app.customer.bus.CustomerSearchCriteria",
		// FORM_SCOPE},
		// {"event", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
		// {"customerList", "com.integrosys.base.businfra.search.SearchResult",
		// FORM_SCOPE},
		{ "limitObList", "com.integrosys.cms.app.limit.bus.OBLimitProfile", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		HashMap result = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap temp = new HashMap();

		try {
			OBLimitProfile ab = new OBLimitProfile();
			OBLimit ab1[] = new OBLimit[2];
			OBLimit ab2 = new OBLimit();
			OBLimit ab3 = new OBLimit();
			OBCollateralAllocation ca1 = new OBCollateralAllocation();
			OBCollateralAllocation ca2 = new OBCollateralAllocation();
			OBCollateralAllocation ca[] = new OBCollateralAllocation[2];
			// ca1.setCollateralID(1000);
			// ca2.setCollateralID(1000);
			ca[0] = ca1;
			ca[1] = ca2;
			ab2.setCollateralAllocations(ca);
			ab2.setBookingLocation(new OBBookingLocation("SG", "SCBLSG"));

			ab2.setLimitID(1111);

			ab2.setProductDesc("securityyy");
			ab3.setCollateralAllocations(ca);
			ab3.setBookingLocation(new OBBookingLocation("SG", "SCBLSG"));

			ab3.setLimitID(2222);

			ab3.setProductDesc("collateralll");
			ab1[0] = ab2;
			ab1[1] = ab3;
			ab.setLimits(ab1);

			java.util.List limitObList = new ArrayList();

			result.put("limitObList", ab);

		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return temp;

	}

}
