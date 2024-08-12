package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoverage.trx.OBInsuranceCoverageTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * $Author: Dattatray Thorat $
 * Command for checker to approve edit .
 */

public class CheckerApproveEditInsuranceCoverageCmd extends AbstractCommand implements ICommonEventConstant {


	private IInsuranceCoverageProxyManager insuranceCoverageProxyManager;

	/**
	 * @return the insuranceCoverageProxyManager
	 */
	public IInsuranceCoverageProxyManager getInsuranceCoverageProxyManager() {
		return insuranceCoverageProxyManager;
	}

	/**
	 * @param insuranceCoverageProxyManager the insuranceCoverageProxyManager to set
	 */
	public void setInsuranceCoverageProxyManager(
			IInsuranceCoverageProxyManager insuranceCoverageProxyManager) {
		this.insuranceCoverageProxyManager = insuranceCoverageProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public CheckerApproveEditInsuranceCoverageCmd() {
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"IInsuranceCoverageTrxValue", "com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue", SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				{"remarks", "java.lang.String", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * Defines an two dimensional array with the result list to be
	 * expected as a result from the doExecute method using a HashMap
	 * syntax for the array is (HashMapkey,classname,scope)
	 * The scope may be request,form or service
	 *
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		}
		);
	}

	/**
	 * This method does the Business operations  with the HashMap and put the results back into
	 * the HashMap.
	 *
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			// System Bank Trx value
			IInsuranceCoverageTrxValue trxValueIn = (OBInsuranceCoverageTrxValue) map.get("IInsuranceCoverageTrxValue");

			String remarks = (String) map.get("remarks");
			ctx.setRemarks(remarks);
			// Function  to approve updated insurance coverage Trx
			IInsuranceCoverageTrxValue trxValueOut = getInsuranceCoverageProxyManager().checkerApproveInsuranceCoverage(ctx, trxValueIn);
			resultMap.put("request.ITrxValue", trxValueOut);
		}catch (InsuranceCoverageException ex) {
			DefaultLogger.debug(this, "got exception in doExecute" + ex);
			ex.printStackTrace();
			throw (new CommandProcessingException(ex.getMessage()));
		} catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
}



