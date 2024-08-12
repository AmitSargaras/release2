package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.OBInsuranceCoverageDtls;
import com.integrosys.cms.app.insurancecoveragedtls.proxy.IInsuranceCoverageDtlsProxyManager;
import com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.insurancecoveragedtls.trx.OBInsuranceCoverageDtlsTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command saves the Insurance Coverage Details into draft 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 * @since $Date: 2011/03/25 11:32:23 $ Tag: $Name: $
 */
public class SaveCreateInsuranceCoverageDtlsCommand extends AbstractCommand implements ICommonEventConstant {
	
	private IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager;

	/**
	 * @return the insuranceCoverageDtlsProxyManager
	 */
	public IInsuranceCoverageDtlsProxyManager getInsuranceCoverageDtlsProxyManager() {
		return insuranceCoverageDtlsProxyManager;
	}

	/**
	 * @param insuranceCoverageDtlsProxyManager the insuranceCoverageDtlsProxyManager to set
	 */
	public void setInsuranceCoverageDtlsProxyManager(
			IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager) {
		this.insuranceCoverageDtlsProxyManager = insuranceCoverageDtlsProxyManager;
	}
	
	/**
	 * Default Constructor
	 */

	public SaveCreateInsuranceCoverageDtlsCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "RMCode", "java.lang.String", REQUEST_SCOPE },
				{ "InsuranceCoverageDtlsObj", "com.integrosys.cms.app.insurancecoveragedtls.bus.OBInsuranceCoverageDtls", FORM_SCOPE },
				{"IInsuranceCoverageDtlsTrxValue", "com.integrosys.cms.app.insurancecoveragedtls.trx.IInsuranceCoverageDtlsTrxValue", SERVICE_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
		});
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
	        HashMap exceptionMap = new HashMap();
	        try {
	        	
	        	OBInsuranceCoverageDtls insuranceCoverageDtls = (OBInsuranceCoverageDtls) map.get("InsuranceCoverageDtlsObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IInsuranceCoverageDtlsTrxValue trxValueIn = (OBInsuranceCoverageDtlsTrxValue) map.get("IInsuranceCoverageDtlsTrxValue");

				IInsuranceCoverageDtlsTrxValue trxValueOut = new OBInsuranceCoverageDtlsTrxValue();

					if (event.equals("maker_save_create_insurance_coverage_dtls")) {
						trxValueOut = getInsuranceCoverageDtlsProxyManager().makerSaveInsuranceCoverageDtls(ctx, trxValueOut, insuranceCoverageDtls);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getInsuranceCoverageDtlsProxyManager().makerEditRejectedInsuranceCoverageDtls(ctx, trxValueIn, insuranceCoverageDtls);
					} 

					resultMap.put("request.ITrxValue", trxValueOut);
	        } catch (InsuranceCoverageDtlsException obe) {
	        	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }

	}



