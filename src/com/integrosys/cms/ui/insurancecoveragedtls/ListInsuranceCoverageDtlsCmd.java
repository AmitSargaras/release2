package com.integrosys.cms.ui.insurancecoveragedtls;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoveragedtls.bus.InsuranceCoverageDtlsException;
import com.integrosys.cms.app.insurancecoveragedtls.proxy.IInsuranceCoverageDtlsProxyManager;

/**
 * This command lists the Insurance Coverage
 * $Author:Dattatray Thorat 
 * @version $Revision: 1.0 $
 */
public class ListInsuranceCoverageDtlsCmd extends AbstractCommand implements ICommonEventConstant {

	
	private  IInsuranceCoverageDtlsProxyManager insuranceCoverageDtlsProxyManager;

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
	public ListInsuranceCoverageDtlsCmd() {
		
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"insuranceCoverageList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
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
	            SearchResult insuranceCoverageList= getInsuranceCoverageDtlsProxyManager().getInsuranceCoverageDtlsList();
	            resultMap.put("insuranceCoverageList", insuranceCoverageList);
	        } catch (InsuranceCoverageDtlsException rme) {
	            DefaultLogger.error(this, "got exception in doExecute" ,rme);
	        	CommandProcessingException cpe = new CommandProcessingException(rme.getMessage());
				cpe.initCause(rme);
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



