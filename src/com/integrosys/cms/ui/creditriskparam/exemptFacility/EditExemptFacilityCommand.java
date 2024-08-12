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
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacilityGroup;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.IExemptFacility;
import com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup;

import java.util.HashMap;


/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $
 * Tag: $Name:  $
 */
public class EditExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public EditExemptFacilityCommand() {
        DefaultLogger.debug(this,"Inside Edit Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {"InitialExemptFacilityGroup", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacilityGroup", SERVICE_SCOPE},
            {"exemptFacility", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility", FORM_SCOPE},
            {"index", "java.lang.String", REQUEST_SCOPE},
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
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
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
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
            OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");
            map.put("theOBTrxContext",trxContext);
            
            IExemptFacility  exemptFacility =(IExemptFacility)map.get("exemptFacility");
            DefaultLogger.debug(this, exemptFacility);
            IExemptFacilityGroup group = (IExemptFacilityGroup)map.get("InitialExemptFacilityGroup");

            if(group == null) {
                DefaultLogger.debug(this,"Group is null.. Creating....");
                group = new OBExemptFacilityGroup();
            }
            IExemptFacility[] currList = group.getExemptFacility();
            currList[index] = exemptFacility;

            group.setExemptFacility(currList);
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



