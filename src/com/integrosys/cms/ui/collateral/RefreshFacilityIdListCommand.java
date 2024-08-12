package com.integrosys.cms.ui.collateral;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.collateral.trx.OBCollateralTrxValue;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.security.MISecurityUIHelper;

public class RefreshFacilityIdListCommand extends AbstractCommand{

	/**
	 * Defines 2-D array to be passed to doExecute Method by HashMap
	 */

	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
	            { "facilityName", "java.lang.String", REQUEST_SCOPE },
	            { "partyId", "java.lang.String", REQUEST_SCOPE }
	            
			});
	    }
	

	/**
	 * Defines 2-D array with ResultList expected doExecute Method using HashMap
	 */

	public String[][] getResultDescriptor() {
	
		return (new String[][]{
//	            {"facilityIdList", "java.util.List", SERVICE_SCOPE},
                {"facilityIdList", "java.util.List", REQUEST_SCOPE },
                {"event", "java.lang.String", REQUEST_SCOPE },
			});
	    }

	    /**
	     * This method does the Business operations  with the HashMap and put the results back into
	     * the HashMap.
	     *
	     * @param map is of type HashMap
	     * @return HashMap with the Result
	     */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,
	AccessDeniedException {
			HashMap result = new HashMap();
			HashMap exceptionMap = new HashMap();
			HashMap temp = new HashMap();
			
				String facilityName = (String) (map.get("facilityName"));
				String partyId = (String) (map.get("partyId"));
			
				
				if ((facilityName != null) && !facilityName.trim().equals("")) {
					result.put("facilityIdList", getValuationFacilityNameList(CollateralDAOFactory.getDAO().getFacilityIdList(partyId, facilityName)));
				}else {
					result.put("facilityIdList","");
				}
				result.put("event","refresh_facility_id");
			temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
			temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return temp;
			}
	
	private List getValuationFacilityNameList(List valuationProxy) {
		List lbValList = new ArrayList();
		try {
			
			//ArrayList valuationAgencyList = new ArrayList();
			//valuationAgencyList = (ArrayList) valuationProxy.();
			String[] stringArray = new String[2];
			for (int i = 0; i < valuationProxy.size(); i++) {
				
				
				stringArray = (String[])valuationProxy.get(i);
				String id = stringArray[0] ;
				String val =  stringArray[0] ;
				LabelValueBean lvBean = new LabelValueBean(val, id);
				lbValList.add(lvBean);
			}
		} catch (Exception ex) {
		}
		return (List) CommonUtil.sortDropdown(lbValList);
	}
	}
