package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.insurancecoverage.trx.OBInsuranceCoverageTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * This command Edits the Insurance Coverage selected for edition 
 * 
 * $Author: Dattatray Thorat
 * 
 * @version $Revision: 1.2 $
 */
public class SubmitCreateInsuranceCoverageCommand extends AbstractCommand implements ICommonEventConstant {
	
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
	
	private  static boolean isRMCodeUnique = true;
	
	/**
	 * @return the isRMCodeUnique
	 */
	public static boolean isRMCodeUnique() {
		return isRMCodeUnique;
	}

	/**
	 * @param isRMCodeUnique the isRMCodeUnique to set
	 */
	public static void setRMCodeUnique(boolean isRMCodeUnique) {
		SubmitCreateInsuranceCoverageCommand.isRMCodeUnique = isRMCodeUnique;
	}

	/**
	 * Default Constructor
	 */

	public SubmitCreateInsuranceCoverageCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "ICCode", "java.lang.String", REQUEST_SCOPE },
				{ "InsuranceCoverageObj", "com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage", FORM_SCOPE },
				{"IInsuranceCoverageTrxValue", "com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue", SERVICE_SCOPE},
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
	        	
	        	boolean isRMCodeUnique = true;
				String rmCode = (String)map.get("ICCode");
				if(rmCode!=null)
					isRMCodeUnique = getInsuranceCoverageProxyManager().isICCodeUnique(rmCode.trim());

				if(isRMCodeUnique != false){
					exceptionMap.put("insuranceCoverageCodeError", new ActionMessage("error.string.iccode.exist"));
					IInsuranceCoverageTrxValue relationshipMgrTrxValue = null;
					resultMap.put("request.ITrxValue", relationshipMgrTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
	        	
	        	OBInsuranceCoverage insuranceCoverage = (OBInsuranceCoverage) map.get("InsuranceCoverageObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IInsuranceCoverageTrxValue trxValueIn = (OBInsuranceCoverageTrxValue) map.get("IInsuranceCoverageTrxValue");

				boolean isCompanyNameUnique = false;
				String newCompanyName = insuranceCoverage.getCompanyName();
								
				IInsuranceCoverageTrxValue trxValueOut = new OBInsuranceCoverageTrxValue();

				if (event.equals("maker_submit_create_insurance_coverage")) {
					
					// Start
					isCompanyNameUnique = getInsuranceCoverageProxyManager().isCompanyNameUnique(newCompanyName.trim());
					
					if(isCompanyNameUnique != false){
						exceptionMap.put("companyNameError", new ActionMessage("error.string.exist","Company Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					// End
					
					trxValueOut = getInsuranceCoverageProxyManager().makerCreateInsuranceCoverage(ctx, trxValueOut, insuranceCoverage);
				} else {
					// event is  maker_confirm_resubmit_edit
					String remarks = (String) map.get("remarks");
					ctx.setRemarks(remarks);
					trxValueOut = getInsuranceCoverageProxyManager().makerEditRejectedInsuranceCoverage(ctx, trxValueIn, insuranceCoverage);
				} 

				resultMap.put("request.ITrxValue", trxValueOut);
	        } catch (InsuranceCoverageException obe) {
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



