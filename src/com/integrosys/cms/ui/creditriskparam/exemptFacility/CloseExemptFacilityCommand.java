/**
 * Copyright Integro Technologies Pte Ltd
*/

package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.creditriskparam.trx.policycap.IPolicyCapTrxValue;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.IPolicyCapProxyManager;
import com.integrosys.cms.app.creditriskparam.proxy.policycap.PolicyCapProxyManagerFactory;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.IExemptFacilityProxy;
import com.integrosys.cms.app.creditriskparam.proxy.exemptFacility.ExemptFacilityProxyFactory;

import java.util.HashMap;

/** Command class to Cancel Edited by maker
 * @author $Author: KienLeong $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2007/Mar/08 $
 * Tag: $Name:  $
 */
public class CloseExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public CloseExemptFacilityCommand() {
    }

    /**
     * Defines an two dimensional array with
     * the parameter list to be passed to the doExecute method by a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
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
            {"request.ITrxValue", "com.integrosys.cms.app.creditriskparam.trx.policycap.OBPolicyCapTrxValue", REQUEST_SCOPE}	        
        });
    }
    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here creation for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        OBTrxContext trxContext = (OBTrxContext)map.get("theOBTrxContext");
        IExemptFacilityGroupTrxValue exemptFacTrxVal = (IExemptFacilityGroupTrxValue)map.get("exemptFacilityTrxValue");
        DefaultLogger.debug(this, "Inside doExecute()  = "+trxContext);
        try {
				
            IExemptFacilityProxy proxy = ExemptFacilityProxyFactory.getProxy();
            exemptFacTrxVal = proxy.makerCloseExemptFacility(trxContext, exemptFacTrxVal);
            resultMap.put("request.ITrxValue", exemptFacTrxVal);

        }catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP,resultMap);
        return returnMap;
    }

}



