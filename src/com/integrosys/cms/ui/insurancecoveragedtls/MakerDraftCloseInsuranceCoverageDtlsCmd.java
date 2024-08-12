package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoverage.trx.OBInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.insurancecoveragedtls.proxy.IInsuranceCoverageDtlsProxyManager;
import com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.trx.OBInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 * @author Dattatray Thorat: 
 * Command for maker to close the draft Insurance Coverage Details trx value
 */

public class MakerDraftCloseInsuranceCoverageDtlsCmd extends AbstractCommand implements ICommonEventConstant {
    

	private IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager;

	/**
	 * @return the insuranceCoverageProxyManager
	 */
	public IInsuranceCoverageDtlsProxyManager getInsuranceCoverageDtlsProxyManager() {
		return insuranceCoverageDtlsProxyManager;
	}

	/**
	 * @param insuranceCoverageProxyManager the insuranceCoverageProxyManager to set
	 */
	public void setInsuranceCoverageDtlsProxyManager(
			IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager) {
		this.insuranceCoverageDtlsProxyManager = insuranceCoverageDtlsProxyManager;
	}

	/**
     * Default Constructor
     */
    public MakerDraftCloseInsuranceCoverageDtlsCmd() {
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
        		{"IInsuranceCoverageTrxValue", "com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
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
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
        	IInsuranceCoverageDtlsTrxValue trxValueIn = (OBInsuranceCoverageDtlsTrxValue) map.get("IInsuranceCoverageDtlsTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IInsuranceCoverageDtlsTrxValue trxValueOut = getInsuranceCoverageDtlsProxyManager().makerCloseDraftInsuranceCoverageDtls(ctx, trxValueIn);

            resultMap.put("request.ITrxValue", trxValueOut);

        }catch (InsuranceCoverageDtlsException ex) {
       	 	DefaultLogger.debug(this, "got exception in doExecute" + ex);
       	 	ex.printStackTrace();
       	 	throw (new CommandProcessingException(ex.getMessage()));
        }
        catch (TransactionException e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



