/**
 * Copyright AurionPro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2011-05-03 15:13:16 +0800 jtan Exp $
 */

package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;
import com.integrosys.cms.app.geography.country.trx.ICountryTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Purpose : This command edit the  checker rejected Director Master
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class CheckerRejectEditDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IDirectorMasterProxyManager directorMasterProxy;

	
	
	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(
			IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}

	/**
	 * Default Constructor
	 */
	public CheckerRejectEditDirectorMasterCmd() {
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
				{ "IDirectorMasterTrxValue", "com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue", SERVICE_SCOPE },
				{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
				 {"remarks", "java.lang.String", REQUEST_SCOPE},
				 {"event", "java.lang.String", REQUEST_SCOPE},
		
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
				{ "request.ITrxValue", "com.integrosys.component.common.transaction.ICompTrxResult",
				REQUEST_SCOPE }
				
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		 HashMap returnMap = new HashMap();
	        HashMap resultMap = new HashMap();
	        HashMap exceptionMap = new HashMap();
	        try {
	            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
	            IDirectorMasterTrxValue trxValueIn = (OBDirectorMasterTrxValue) map.get("IDirectorMasterTrxValue");
	            String remarks = (String) map.get("remarks");
	            
	            if(remarks.equals("")){
					exceptionMap.put("remarkError", new ActionMessage("error.reject.remark"));
					IDirectorMasterTrxValue directorTrxValue = null;
					resultMap.put("request.ITrxValue", directorTrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;					
				}	 				            
	            ctx.setRemarks(remarks);
	            IDirectorMasterTrxValue trxValueOut = getDirectorMasterProxy().checkerRejectDirectorMaster(ctx, trxValueIn);
	            resultMap.put("request.ITrxValue", trxValueOut);
	            
	        }catch (DirectorMasterException ex) {
	        	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
		            ex.printStackTrace();
		            throw (new CommandProcessingException(ex.getMessage()));
			}
	        catch (Exception e) {
	            DefaultLogger.debug(this, "got exception in doExecute" + e);
	            e.printStackTrace();
	            throw (new CommandProcessingException(e.getMessage()));
	        }

	        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
	        return returnMap;
	}

}
