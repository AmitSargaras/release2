package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue;
import com.integrosys.cms.app.limit.bus.LimitDAO;
/**
 * This command Prepares the Insurance Coverage before editing
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 */
public class PrepareEditInsuranceCoverageCommand extends AbstractCommand implements ICommonEventConstant {
	
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

	public PrepareEditInsuranceCoverageCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"ICId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{ "InsuranceCoverageObj", "com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverage", FORM_SCOPE },
				{"IInsuranceCoverageTrxValue", "com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "migratedFlag", "java.lang.String", SERVICE_SCOPE },
	            {"ICId", "java.lang.String", REQUEST_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
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
	        	String id = (String) map.get("ICId");
	        	String event = (String) map.get("event");
	        	
	        	IInsuranceCoverageTrxValue insuranceCoverageTrxValue = getInsuranceCoverageProxyManager().getInsuranceCoverageTrxValue(Long.parseLong(id));
	        	IInsuranceCoverage insuranceCoverage = insuranceCoverageTrxValue.getInsuranceCoverage();
	        	
	        	if(insuranceCoverageTrxValue.getStatus().equals("PENDING_UPDATE") || insuranceCoverageTrxValue.getStatus().equals("PENDING_DELETE") 
	        			|| insuranceCoverageTrxValue.getStatus().equals("PENDING_CREATE")
	        			|| insuranceCoverageTrxValue.getStatus().equals("REJECTED")){
					resultMap.put("wip", "wip");
				}
	        	
	        	LimitDAO limitDao = new LimitDAO();
	    		try {
	    		String migratedFlag = "N";	
	    		boolean status = false;	
	    		 status = limitDao.getCAMMigreted("CMS_INSURANCE_COVERAGE",insuranceCoverage.getId(),"ID");
	    		
	    		if(status)
	    		{
	    			migratedFlag= "Y";
	    		}
	    		resultMap.put("migratedFlag", migratedFlag);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	        	
	            resultMap.put("ICId",id);
	            resultMap.put("event", event);
	            resultMap.put("InsuranceCoverageObj", insuranceCoverage);
	            resultMap.put("IInsuranceCoverageTrxValue", insuranceCoverageTrxValue);
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



