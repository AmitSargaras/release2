package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
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
public class MakerSubmitEditInsuranceCoverageCommand extends AbstractCommand implements ICommonEventConstant {


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

	public MakerSubmitEditInsuranceCoverageCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "InsuranceCoverageObj", "com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage", FORM_SCOPE },
				{"IInsuranceCoverageTrxValue", "com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue", SERVICE_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            {"ICId", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{"ICId", "java.lang.String", REQUEST_SCOPE},
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
	        	OBInsuranceCoverage insuranceCoverage = (OBInsuranceCoverage) map.get("InsuranceCoverageObj");
				String event = (String) map.get("event");
				String id = (String) map.get("ICId");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IInsuranceCoverageTrxValue trxValueIn = (OBInsuranceCoverageTrxValue) map.get("IInsuranceCoverageTrxValue");

				IInsuranceCoverageTrxValue trxValueOut = new OBInsuranceCoverageTrxValue();
				
				boolean isCompanyNameUnique = false;
				String newCompanyName = insuranceCoverage.getCompanyName();
				String oldCompanyName = "";
				
				if(trxValueIn.getFromState().equals(ICMSConstant.STATE_PENDING_PERFECTION)){//add - save - maker process - submit 
					// Start
					oldCompanyName = trxValueIn.getStagingInsuranceCoverage().getCompanyName();
					if( ! oldCompanyName.equals(newCompanyName) )
						isCompanyNameUnique = getInsuranceCoverageProxyManager().isCompanyNameUnique(newCompanyName.trim());
					
					if(isCompanyNameUnique != false){
						exceptionMap.put("companyNameError", new ActionMessage("error.string.exist","Company Name"));
						returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
						returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
						return returnMap;
					}
					// End
					trxValueOut = getInsuranceCoverageProxyManager().makerUpdateCreateInsuranceCoverage(ctx, trxValueIn, insuranceCoverage);
				}else{
					if (event.equals("maker_submit_edit") || event.equals("maker_confirm_resubmit_update") ) { //edit - submit
						// Start
						oldCompanyName = trxValueIn.getInsuranceCoverage().getCompanyName();
						if( ! oldCompanyName.equals(newCompanyName) )
							isCompanyNameUnique = getInsuranceCoverageProxyManager().isCompanyNameUnique(newCompanyName.trim());
						
						if(isCompanyNameUnique != false){
							exceptionMap.put("companyNameError", new ActionMessage("error.string.exist","Company Name"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						// End
						
						trxValueOut = getInsuranceCoverageProxyManager().makerUpdateInsuranceCoverage(ctx, trxValueIn, insuranceCoverage);
					}else if (event.equals("maker_save_update_insurance_coverage")){ //edit - save
						
						// Start
						oldCompanyName = trxValueIn.getInsuranceCoverage().getCompanyName();
						if( ! oldCompanyName.equals(newCompanyName) )
							isCompanyNameUnique = getInsuranceCoverageProxyManager().isCompanyNameUnique(newCompanyName.trim());
						
						if(isCompanyNameUnique != false){
							exceptionMap.put("companyNameError", new ActionMessage("error.string.exist","Company Name"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						// End
						
						trxValueOut = getInsuranceCoverageProxyManager().makerUpdateSaveInsuranceCoverage(ctx, trxValueIn, insuranceCoverage);
					} else {
						// event is  maker_confirm_resubmit_edit
						
						// Start
						oldCompanyName = trxValueIn.getStagingInsuranceCoverage().getCompanyName();
						if( ! oldCompanyName.equals(newCompanyName) )
							isCompanyNameUnique = getInsuranceCoverageProxyManager().isCompanyNameUnique(newCompanyName.trim());
						
						if(isCompanyNameUnique != false){
							exceptionMap.put("companyNameError", new ActionMessage("error.string.exist","Company Name"));
							returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
							returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
							return returnMap;
						}
						// End
						
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getInsuranceCoverageProxyManager().makerEditRejectedInsuranceCoverage(ctx, trxValueIn, insuranceCoverage);
					} 
				}	
					resultMap.put("ICId",id);
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



