package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

public class RefreshSystemListCommand extends AbstractCommand{
	
	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
	            { "system", "java.util.List", SERVICE_SCOPE },
	          //  {"systemId", "java.lang.String", REQUEST_SCOPE},
	            { "systemIdList", "java.util.List", SERVICE_SCOPE },
			});
	    }
	

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
	            { "stateList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            { "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
	            { "systemIdList", "java.util.List", SERVICE_SCOPE },
	            { "systemIdList", "java.util.List", REQUEST_SCOPE },
	            {"systemName", "java.lang.String", REQUEST_SCOPE},
	         //   {"systemId", "java.lang.String", REQUEST_SCOPE},
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
	        	String event = (String) map.get("event");
	        	String id =(String) map.get("systemName");
	        	List system =(List) map.get("system");
	        	ICollateralTrxValue trxValue = (OBCollateralTrxValue) map.get("serviceColObj");
	        	resultMap.put("serviceColObj", trxValue);   
	        	if(trxValue != null)
	    		{
	    			
	    			long partyid =   trxValue.getCollateral().getCollateralID() ;
	    			  List  system1 = getSystemDetail(partyid);
	    			//  result.put("system", getValuationAgencyList(system));
	    			  resultMap.put("systemIdList", getValuationAgencyList(system1,id));
	    		}
	        	
	    		
	        //	resultMap.put("stateList", getValuationAgencyList(id));
	        //	resultMap.put("systemId", (String) map.get("systemId"));
	        	resultMap.put("systemName", id);
	        	resultMap.put("event", event);
	        } catch (NoSuchGeographyException nsge) {
	        	CommandProcessingException cpe = new CommandProcessingException(nsge.getMessage());
				cpe.initCause(nsge);
				throw cpe;
			} catch (Exception e) {
				CommandProcessingException cpe = new CommandProcessingException("Internal Error While Processing ");
				cpe.initCause(e);
				throw cpe;
			}
	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	    }
	    private List getSystemDetail(long partyCode) {
			List system = new ArrayList();
			try {
					MISecurityUIHelper helper = new MISecurityUIHelper();
					system = CollateralDAOFactory.getDAO().getSystemList(partyCode);
			}
			catch (Exception ex) {
			}
			return system;
		}
		
		
		private List getValuationAgencyList(List valuationProxy,String idName) {
			List lbValList = new ArrayList();
			try {
				
				//ArrayList valuationAgencyList = new ArrayList();
				//valuationAgencyList = (ArrayList) valuationProxy.();
				String[] stringArray = new String[2];
				for (int i = 0; i < valuationProxy.size(); i++) {
					
					
					stringArray = (String[])valuationProxy.get(i);
					
					if((idName != null) && !(idName.equals("") ))
					{
						if(idName.equals(stringArray[0]))
						{
							String id = stringArray[0] ;
							String val =  stringArray[1] ;
							LabelValueBean lvBean = new LabelValueBean(val, val);
							lbValList.add(lvBean);
						}
					}
				}
			} catch (Exception ex) {
			}
				for(int l = 0; l<lbValList.size();l++)
			{
				System.out.println("lbValList l ====>"+lbValList.get(l));
			for(int i = l; i<lbValList.size()-1 ; i++)
			{
				int k = i+1;
				System.out.println("lbValList k ====>"+lbValList.get(k));
				if(lbValList.get(l).equals(lbValList.get(k)))
				{
					lbValList.remove(k);
					i=i-1;
				}	
			}
			}
			
			return (List) CommonUtil.sortDropdown(lbValList);
		}
}
