package com.integrosys.cms.ui.relationshipmgr;

import java.util.HashMap;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.relationshipmgr.bus.RelationshipMgrException;
import com.integrosys.cms.app.relationshipmgr.proxy.IRelationshipMgrProxyManager;
import com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue;
import com.integrosys.cms.app.relationshipmgr.trx.OBRelationshipMgrTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.app.transaction.OBTrxContext;
/**
 
 * @author Dattatray Thorat: 
 * Command for maker to close the rejected Relationship Manager trx value
 */

public class MakerDraftCloseRelationShipManagerCmd extends AbstractCommand implements ICommonEventConstant {
    

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
    public MakerDraftCloseRelationShipManagerCmd() {
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
        		{"IRelationshipMgrTrxValue", "com.integrosys.cms.app.relationshipmgr.trx.IRelationshipMgrTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        try {
        	IRelationshipMgrTrxValue trxValueIn = (OBRelationshipMgrTrxValue) map.get("IRelationshipMgrTrxValue");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IRelationshipMgrTrxValue trxValueOut = getRelationshipMgrProxyManager().makerCloseDraftRelationshipMgr(ctx, trxValueIn);

            if(trxValueIn != null) {
            	if(trxValueIn.getStagingRelationshipMgr() != null) {
             ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
             collateralDAO.updateStagingLoccalCads(trxValueIn.getStagingRelationshipMgr().getRelationshipMgrCode());
            	}
            }
            
            resultMap.put("request.ITrxValue", trxValueOut);

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



