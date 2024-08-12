package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

/**
 * Purpose : This command delete the Relationship Manager selected for deletion 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * Tag : $Name$
 */
public class MakerSubmitDeleteRelationShipManagerCommand extends AbstractCommand implements ICommonEventConstant {

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

	public MakerSubmitDeleteRelationShipManagerCommand() {
	}
	
	public String[][] getParameterDescriptor() {
		
		return (new String[][]{
				{ "RelationshipMgrObj", "com.integrosys.cms.app.relationshipmgr.bus.OBRelationshipMgr", FORM_SCOPE },
				{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
	            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
	            {"event", "java.lang.String", REQUEST_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
		});
	}
	
	public String[][] getResultDescriptor() {
	
		return (new String[][]{
	            {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
	            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
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
	       
	        	OBRelationshipMgr relationshipMgr = (OBRelationshipMgr) map.get("RelationshipMgrObj");
				String event = (String) map.get("event");
				OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
				IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");

				IRelationshipMgrTrxValue trxValueOut = new OBRelationshipMgrTrxValue();

			
					if (event.equals("maker_submit_remove")) {
						trxValueOut = getRelationshipMgrProxyManager().makerDeleteRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					} else {
						// event is  maker_confirm_resubmit_edit
						String remarks = (String) map.get("remarks");
						ctx.setRemarks(remarks);
						trxValueOut = getRelationshipMgrProxyManager().makerEditRejectedRelationshipMgr(ctx, trxValueIn, relationshipMgr);
					} 

					resultMap.put("request.ITrxValue", trxValueOut);
				
	        	
	        } catch (RelationshipMgrException obe) {
		       	CommandProcessingException cpe = new CommandProcessingException(obe.getMessage());
				cpe.initCause(obe);
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



