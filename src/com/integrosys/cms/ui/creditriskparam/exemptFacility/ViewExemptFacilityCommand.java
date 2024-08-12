/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

package com.integrosys.cms.ui.creditriskparam.exemptFacility;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.liquidation.bus.IRecoveryExpense;
import com.integrosys.cms.app.liquidation.bus.ILiquidation;
import com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense;
import com.integrosys.cms.app.liquidation.trx.ILiquidationTrxValue;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility;

import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.ArrayList;


/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $
 * Tag: $Name:  $
 */
public class ViewExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public ViewExemptFacilityCommand() {
        DefaultLogger.debug(this,"Inside View Exempt Facility Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {"ExemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.OBExemptFacilityGroupTrxValue", SERVICE_SCOPE},
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
            {"index", "java.lang.String", REQUEST_SCOPE},
        });
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
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
            {"exemptFacility", "com.integrosys.cms.app.liquidation.bus.OBRecoveryExpense", FORM_SCOPE},
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
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            String  strIndex = (String ) map.get("index");
            int index = 0;
            try{
                index = Integer.parseInt(strIndex);
            }catch (Exception e){
                DefaultLogger.debug(this, "index invalid");
            }
            OBExemptFacility exemptFacility = null;
            IExemptFacilityGroupTrxValue trxVal = (IExemptFacilityGroupTrxValue)map.get("ExemptFacilityTrxValue");
            IExemptFacilityGroup group = (IExemptFacilityGroup)map.get("InitialExemptFacilityGroup");

            DefaultLogger.debug(this,group);
            
            IExemptFacility[] exemptFacilities = null;

            if (group == null){
                if ( (  (trxVal.getStatus().equals(ICMSConstant.STATE_ND))
                        || (trxVal.getStatus().equals(ICMSConstant.STATE_ACTIVE))
                    )
                ) {
                    exemptFacilities = (IExemptFacility[])trxVal.getExemptFacilityGroup().getExemptFacility();
                }else{
                    exemptFacilities = (IExemptFacility[])trxVal.getStagingExemptFacilityGroup().getExemptFacility();
                }
            }
            else
                exemptFacilities = (IExemptFacility[])group.getExemptFacility();

            if (exemptFacilities != null)
                exemptFacility = (OBExemptFacility)exemptFacilities[index];

            resultMap.put("exemptFacility", exemptFacility);
            resultMap.put("InitialExemptFacilityGroup", group);
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



