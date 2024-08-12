/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.geography.city;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.fileInsertMapper.bus.IFileMapperId;
import com.integrosys.cms.app.fileInsertMapper.bus.OBFileMapperID;
import com.integrosys.cms.app.geography.bus.NoSuchGeographyException;
import com.integrosys.cms.app.geography.city.proxy.ICityProxyManager;
import com.integrosys.cms.app.geography.city.trx.ICityTrxValue;
import com.integrosys.cms.app.geography.city.trx.OBCityTrxValue;
import com.integrosys.cms.app.holiday.bus.HolidayException;
import com.integrosys.cms.app.limit.bus.LimitDAO;

/**
 *$Author: Abhijit R $
 *Command for checker to read System bank Trx value
 */
public class CheckerReadFileInsertListCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private ICityProxyManager cityProxy;
	
	/**
	 * @return the cityProxy
	 */
	public ICityProxyManager getCityProxy() {
		return cityProxy;
	}

	/**
	 * @param cityProxy the cityProxy to set
	 */
	public void setCityProxy(ICityProxyManager cityProxy) {
		this.cityProxy = cityProxy;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "loginId", "java.lang.String", REQUEST_SCOPE},
				{ "startIndex", "java.lang.String", REQUEST_SCOPE }
				
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{ "TrxId", "java.lang.String", REQUEST_SCOPE },
				{ "cityObj", "com.integrosys.cms.app.geography.city.bus.OBCity", FORM_SCOPE },
				{"ICityTrxValue", "com.integrosys.cms.app.geography.city.trx.ICityTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"cityList", "com.integrosys.base.businfra.search.SearchResult", REQUEST_SCOPE},
				{"startIndex", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,HolidayException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IFileMapperId city;
			ICityTrxValue trxValue=null;
			String transId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");

			String startIndex =(String) map.get("startIndex");
			SearchResult cityList=null;
			
			String login = (String)map.get("loginId");
			
			boolean allowToDelete = false;
			
			if(startIndex == null){
				startIndex ="0"; 
				allowToDelete = true;
			}
			
			if(login==null){
				login="";
			}
			List result = new ArrayList();
			
			// function to get system bank Trx value
			trxValue = (OBCityTrxValue) getCityProxy().getInsertFileByTrxID(transId);
			// systemBank = (OBSystemBank) trxValue.getSystemBank();
			System.out.println("**inside CheckerReadFileInsertListCmd.java*****113*****transId : "+transId+", login : "+login+", startIndex : "+startIndex);
			System.out.println("**inside CheckerReadFileInsertListCmd.java*****124*****trxValue : "+trxValue);
			System.out.println("**inside CheckerReadFileInsertListCmd.java*****125*****allowToDelete : "+allowToDelete);
			try{
				if("20240410002535351".equalsIgnoreCase(transId) && allowToDelete) {
				LimitDAO limitDAO=new LimitDAO();
				limitDAO.updateRecordsBasedOnTransId(transId); 
				}
			}catch(Exception e) {
				System.out.println("**Exception inside CheckerReadFileInsertListCmd.java********136***exception :"+e);
			}
			result = (List)  getCityProxy().getAllStage(transId,login);
			System.out.println("**inside CheckerReadFileInsertListCmd.java********138***after result*****");
			// function to get stging value of system bank trx value
			city = (OBFileMapperID) trxValue.getStagingFileMapperID();
			System.out.println("**inside CheckerReadFileInsertListCmd.java********142***before cityist*****");
			cityList = new SearchResult(Integer.parseInt(startIndex), 10, result.size(), result);
			System.out.println("**inside CheckerReadFileInsertListCmd.java********144***after cityist*****");
			resultMap.put("cityList",cityList);
			resultMap.put("ICityTrxValue", trxValue);
			resultMap.put("cityObj", city);
			resultMap.put("event", event);
			resultMap.put("startIndex", startIndex);
			resultMap.put("TrxId", transId);
		} catch (NoSuchGeographyException e) {
			System.out.println("Exception is**inside CheckerReadFileInsertListCmd.java*****152*****e : "+e);
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			System.out.println("Exception is**inside CheckerReadFileInsertListCmd.java*****157*****e : "+e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
			System.out.println("Exception is**inside CheckerReadFileInsertListCmd.java*****161*****e : "+e);
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
}
