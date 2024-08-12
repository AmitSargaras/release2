package com.integrosys.cms.ui.insurancecoverage;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.insurancecoverage.bus.InsuranceCoverageException;
import com.integrosys.cms.app.insurancecoverage.proxy.IInsuranceCoverageProxyManager;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class PaginateInsuranceCoverageListCommand extends AbstractCommand {

	private  IInsuranceCoverageProxyManager insuranceCoverageProxyManager;

	public IInsuranceCoverageProxyManager getInsuranceCoverageProxyManager() {
		return insuranceCoverageProxyManager;
	}

	public void setInsuranceCoverageProxyManager(
			IInsuranceCoverageProxyManager insuranceCoverageProxyManager) {
		this.insuranceCoverageProxyManager = insuranceCoverageProxyManager;
	}

	/**
	 * Default Constructor
	 */
	public PaginateInsuranceCoverageListCommand() {
		
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{"ICCode", "java.lang.String", REQUEST_SCOPE},
	            {"CompanyName", "java.lang.String", REQUEST_SCOPE},	           
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE }
			});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"insuranceCoverageList", "com.integrosys.base.businfra.search.SearchResult", SERVICE_SCOPE},
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"startIndex", "java.lang.String", REQUEST_SCOPE},
	            {"ICCode", "java.lang.String", REQUEST_SCOPE},
	            {"CompanyName", "java.lang.String", REQUEST_SCOPE},	           
	            {"loginUser", "java.lang.String", SERVICE_SCOPE}
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
				String startInd = (String) map.get("startIndex");
				String icCode = (String) map.get("ICCode");
				String companyName = (String) map.get("CompanyName");				
				ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);
				String loginUser = user.getLoginID();
				
				if( icCode == null || icCode.equals("null") )
					icCode = "";
				
				if( companyName == null || companyName.equals("null") )
					companyName = "";
				
	            SearchResult insuranceCoverageList= getInsuranceCoverageProxyManager().getInsuranceCoverageList(icCode,companyName);
	            resultMap.put("loginUser",loginUser);
	            resultMap.put("insuranceCoverageList", insuranceCoverageList);
	            resultMap.put("startIndex", startInd);
	            resultMap.put("ICCode", icCode);
	            resultMap.put("CompanyName", companyName);
	        } catch (InsuranceCoverageException rme) {
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
	        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	        return returnMap;
	    }
}
