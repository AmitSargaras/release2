/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.finwareupload;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.holiday.trx.IHolidayTrxValue;
import com.integrosys.cms.app.holiday.trx.OBHolidayTrxValue;
import com.integrosys.cms.ui.finwareupload.proxy.IFinwareUploadProxyManager;

/**
@author $Author: Abhijit R$
* Command for Create Holiday
 */
public class FinwareUploadCmd extends AbstractCommand implements ICommonEventConstant {
	

	private IFinwareUploadProxyManager finwareuploadProxy;


	/**
	 * Default Constructor
	 */
	
	
	public FinwareUploadCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	        	
	        }
	        );
	    }

	 public String[][] getResultDescriptor() {
			return (new String[][] {
			
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
			String strError = "";
			String fileType = "";
			String fileUploadPending ="";
			String fileCheckSum ="";			

//			System.out.println("now abc");
			String remarks = (String) map.get("remarks");
			
			IHolidayTrxValue trxValueOut = new OBHolidayTrxValue();
			try {

					resultMap.put("fileUploadPending",fileUploadPending);
					resultMap.put("fileCheckSum",fileCheckSum);	
					resultMap.put("fileType", fileType);
			    	resultMap.put("errorEveList", strError);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					return returnMap;
			}
			catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }
			
			
	    }

		public IFinwareUploadProxyManager getFinwareuploadProxy() {
			return finwareuploadProxy;
		}

		public void setFinwareuploadProxy(IFinwareUploadProxyManager finwareuploadProxy) {
			this.finwareuploadProxy = finwareuploadProxy;
		}




}
