package com.integrosys.cms.ui.directorMaster;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.directorMaster.proxy.IDirectorMasterProxyManager;
import com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue;


/**
 * Purpose :This command prepare the  selected Director Master for creation.
 *
 * @author $Author: Venkat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-05-03 15:13:16 +0800 (Tue, 03 May 2011) $
 * Tag : $Name$
 */

public class MakerPrepareCreateDirectorMasterCmd extends AbstractCommand implements ICommonEventConstant {
	
	
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
	public MakerPrepareCreateDirectorMasterCmd() {
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
				  {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
					 
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
				{"IDirectorMasterTrxValue", "com.integrosys.cms.app.directorMaster.trx.OBDirectorMasterTrxValue", SERVICE_SCOPE}
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
		OBDirectorMasterTrxValue directorMasterTrxValue = new OBDirectorMasterTrxValue();
		resultMap.put("IDirectorMasterTrxValue", directorMasterTrxValue);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, map);
		return returnMap;
	}

}
