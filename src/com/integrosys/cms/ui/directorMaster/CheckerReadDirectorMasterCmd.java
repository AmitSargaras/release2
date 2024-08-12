/**
 /**
 * Copyright AurionPro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/DiaryItemMapper.java,v 1.8 2011-05-03 15:13:16 +0800 jtan Exp $
 */
package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.bus.DirectorMasterException;
import com.integrosys.cms.app.directorMaster.bus.IDirectorMaster;
import com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;

/**
 * Purpose : This command read the  checker Director Master
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class CheckerReadDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
	private IDirectorMasterProxyManager directorMasterProxy;

	public IDirectorMasterProxyManager getDirectorMasterProxy() {
		return directorMasterProxy;
	}

	public void setDirectorMasterProxy(IDirectorMasterProxyManager directorMasterProxy) {
		this.directorMasterProxy = directorMasterProxy;
	}
	
	
	
	/**
	 * Default Constructor
	 */
	public CheckerReadDirectorMasterCmd() {
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
				{ "directorMasterObj", "com.integrosys.cms.app.directorMaster.bus.OBDirectorMaster", FORM_SCOPE },
				{"IDirectorMasterTrxValue", "com.integrosys.cms.app.directorMaster.trx.IDirectorMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
		});
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,DirectorMasterException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IDirectorMaster directorMaster;
			IDirectorMasterTrxValue trxValue=null;
			String TrxId=(String) (map.get("TrxId"));
			String event = (String) map.get("event");
			trxValue = (OBDirectorMasterTrxValue) getDirectorMasterProxy().getDirectorMasterByTrxID(TrxId);
			directorMaster = (OBDirectorMaster) trxValue.getStagingDirectorMaster();
			resultMap.put("IDirectorMasterTrxValue", trxValue);
			resultMap.put("directorMasterObj", directorMaster);
			resultMap.put("event", event);
		} catch (DirectorMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (TransactionException e) {
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
