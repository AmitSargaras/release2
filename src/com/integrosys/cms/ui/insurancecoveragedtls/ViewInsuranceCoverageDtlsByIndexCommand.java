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

/**
 * This command displays the Insurance Coverage Details present for selected id 
 * $Author: Dattatray Thorat
 * @version $Revision: 1.0 $
 */
public class ViewInsuranceCoverageDtlsByIndexCommand extends AbstractCommand implements ICommonEventConstant {
	
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

	public ViewInsuranceCoverageDtlsByIndexCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"ICId", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
				{ "InsuranceCoverageDtlsObj", "com.integrosys.cms.app.insurancecoverage.bus.OBInsuranceCoverageDtls", FORM_SCOPE },
				{"IInsuranceCoverageDtlsTrxValue", "com.integrosys.cms.app.insurancecoverage.trx.IInsuranceCoverageDtlsTrxValue", SERVICE_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
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

	        try {
	        	String id = (String) map.get("ICId");
	        	String event = (String) map.get("event");
	        	
	        	IInsuranceCoverageDtls insuranceCoverageDtls = new OBInsuranceCoverageDtls();
	        	
	        	IInsuranceCoverageDtlsTrxValue insuranceCoverageDtlsTrxValue = getInsuranceCoverageDtlsProxyManager().getInsuranceCoverageDtlsTrxValue(Long.parseLong(id));
	        	insuranceCoverageDtls = insuranceCoverageDtlsTrxValue.getInsuranceCoverageDtls();
	        	
	            resultMap.put("event", event);
	            resultMap.put("InsuranceCoverageDtlsObj", insuranceCoverageDtls);
	            resultMap.put("IInsuranceCoverageDtlsTrxValue", insuranceCoverageDtlsTrxValue);
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



