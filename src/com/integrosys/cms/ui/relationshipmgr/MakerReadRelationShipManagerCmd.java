/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;

/**
 *@author dattatray.thorat $
 *Command for maker to read Relationship Manager Trx
 */
public class MakerReadRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {
	
	

	private IRelationshipMgrProxyManager relationshipMgrProxyManager;


		/**
		 * @return the relationshipMgrProxyManager
		 */
		public IRelationshipMgrProxyManager getRelationshipMgrProxyManager() {
			return relationshipMgrProxyManager;
		}

		/**
		 * @param relationshipMgrProxyManager the relationshipMgrProxyManager to set
		 */
		public void setRelationshipMgrProxyManager(
				IRelationshipMgrProxyManager relationshipMgrProxyManager) {
			this.relationshipMgrProxyManager = relationshipMgrProxyManager;
		}

	/**
	 * Default Constructor
	 */
	public MakerReadRelationShipManagerCmd() {
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "TrxId", "java.lang.String", REQUEST_SCOPE }, });
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
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.otherbank.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
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
		try {

			String bankCode=(String) (map.get("TrxId"));
			IRelationshipMgrTrxValue trxValue = (OBRelationshipMgrTrxValue) getRelationshipMgrProxyManager().getRelationshipMgrTrxValue(Long.parseLong(bankCode));
			IRelationshipMgr relationshipMgr = (OBRelationshipMgr) trxValue.getRelationshipMgr();
			resultMap.put("IRelationshipMgrTrxValue", trxValue);
			resultMap.put("RelationshipMgrObj", relationshipMgr);
		}catch (RelationshipMgrException ex) {
	       	 DefaultLogger.debug(this, "got exception in doExecute" + ex);
	         ex.printStackTrace();
	         throw (new CommandProcessingException(ex.getMessage()));
		}
		catch (TransactionException e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}

}
