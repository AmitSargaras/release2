package com.integrosys.cms.ui.manualinput.customer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;

public class RefreshSecurityTypeCommand extends AbstractCommand{

	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            {"securityCode", "java.lang.String", REQUEST_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE}
	            /*{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE }*/
			});
	    }
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {"event", "java.lang.String", REQUEST_SCOPE},
//	            { "regionList", "java.util.List",SERVICE_SCOPE },
	            { "securityCodeAndTypeList", "java.util.List",REQUEST_SCOPE },
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            /*{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },*/
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
        	String securityCode = (String) map.get("securityCode");
        	List securityCodeAndTypeList=new ArrayList();
        	/*ICollateralTrxValue trxValue = (OBCollateralTrxValue) map.get("serviceColObj");*/
//        	ICityTrxValue cityTrxValue = (OBCityTrxValue) map.get("ICityTrxValue");
        	ICollateralDAO collateralDao = CollateralDAOFactory.getDAO();
			
			
        	if( event.equals("refresh_security_type")){	        		
        		securityCodeAndTypeList=collateralDao.getSeurityCodeAndType(securityCode);
        		resultMap.put("securityCodeAndTypeList", securityCodeAndTypeList);
        	}
        	/*resultMap.put("serviceColObj", trxValue);*/
	        resultMap.put("event", event);
//	        resultMap.put("ICityTrxValue", cityTrxValue);
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
	
}
