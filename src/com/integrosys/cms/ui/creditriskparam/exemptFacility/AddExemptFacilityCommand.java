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
import com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

import java.util.HashMap;

import org.apache.struts.action.ActionMessage;


/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/09/21 12:30:04 $
 * Tag: $Name:  $
 */
public class AddExemptFacilityCommand extends AbstractCommand implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public AddExemptFacilityCommand() {
        DefaultLogger.debug(this,"Inside Add Command >>>>>>>>>>>>>>>>>>>>>>>>>>>");
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
            {"exemptFacility", "com.integrosys.cms.app.creditriskparam.bus.exemptFacility.OBExemptFacility", FORM_SCOPE},
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
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
            {"exemptFacilityTrxValue", "com.integrosys.cms.app.creditriskparam.trx.exemptFacility.IExemptFacilityGroupTrxValue", SERVICE_SCOPE},
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
        HashMap excpMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            OBTrxContext  trxContext = (OBTrxContext )map.get("theOBTrxContext");
            map.put("theOBTrxContext",trxContext);
            
            IExemptFacility  exemptFacility =(IExemptFacility)map.get("exemptFacility");
            DefaultLogger.debug(this, exemptFacility);
            IExemptFacilityGroupTrxValue trxVal = (IExemptFacilityGroupTrxValue)map.get("exemptFacilityTrxValue");

//            System.out.println("Added this object exemptFacility = " + exemptFacility);

            // Validate for Duplicates
            
            boolean exists= false;
           
            
            if (trxVal.getStagingExemptFacilityGroup() != null &&
            		trxVal.getStagingExemptFacilityGroup().getExemptFacility() != null) {
            	IExemptFacility[] exemptFac = trxVal.getStagingExemptFacilityGroup().getExemptFacility();
                if (exemptFac != null){
                    for (int i=0 ; i < exemptFac.length; i++ ) {
                         if ( exemptFac[i].getFacilityCode().equals(exemptFacility.getFacilityCode())){
                            exists = true;
                            break;
                        }
                    }
                }
            }
            
            if (!exists) {
	            if (trxVal != null) {
	            	IExemptFacility[] stagingList = trxVal.getStagingExemptFacilityGroup().getExemptFacility();
	            	if (stagingList == null) {
	            		stagingList = new IExemptFacility[1];
	            		stagingList[0] = exemptFacility;
	            		trxVal.getStagingExemptFacilityGroup().setExemptFacility(stagingList);
	            	} else {
	            		IExemptFacility[] newList = new IExemptFacility[stagingList.length + 1];
	            		
	            		System.arraycopy(stagingList, 0, newList, 0, stagingList.length);
	            		
	            		newList[stagingList.length] = exemptFacility;
	            		trxVal.getStagingExemptFacilityGroup().setExemptFacility(newList);
	            	}            	
	            }
            } else
                excpMap.put(ICMSUIConstant.EXEMPT_FACILITY_DUPLICATE, new ActionMessage("error.duplicate.exempt.facility"));

            
            resultMap.put("exemptFacilityTrxValue", trxVal);
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, excpMap);
        return returnMap;
    }
}



