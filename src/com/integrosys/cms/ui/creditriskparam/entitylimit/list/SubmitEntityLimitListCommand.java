/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.list;

import java.util.HashMap;

import com.integrosys.cms.ui.creditriskparam.entitylimit.EntityLimitCommand;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.entitylimit.IEntityLimit;
import com.integrosys.cms.app.creditriskparam.proxy.entitylimit.IEntityLimitProxy;
import com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * Maker Submit new or amended entity Limit
 *
 * @author  $Author: siewkheat $<br>
 * @version $Revision:  $
 * @since   $Date:  $
 * Tag:     $Name:  $
 */
//public class SubmitEntityLimitListCommand extends AbstractCommand {
public class SubmitEntityLimitListCommand extends EntityLimitCommand {

    public String[][] getParameterDescriptor() {
        return new String[][]{
            // Consume the input fields as a List of offset (String), length
            // (String) and feed group OB.
            {"EntityLimitMap", "java.util.HashMap", FORM_SCOPE}, // Consume the current entity Limit to be saved as a whole.
            {"EntityLimitTrxValue", 
            	"com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", SERVICE_SCOPE}, // Produce the offset.
            {"offset", "java.lang.Integer", SERVICE_SCOPE}, 
            {"theOBTrxContext","com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE}
        	};
    }


    /**
     * Defines a two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return new String[][]{
				{"request.ITrxValue",
		            "com.integrosys.cms.app.creditriskparam.trx.entitylimit.IEntityLimitTrxValue", REQUEST_SCOPE},
				{"duplicateEntry", "java.lang.String", REQUEST_SCOPE},
				{"noEntry", "java.lang.String", REQUEST_SCOPE},
				{"foundError", "java.lang.String", REQUEST_SCOPE},
			};
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.Here reading for Company Borrower is done.
     *
     * @param map is of type HashMap
     * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException on errors
     * @throws com.integrosys.base.uiinfra.exception.CommandValidationException on errors
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map)
            throws CommandProcessingException, CommandValidationException {

        DefaultLogger.debug(this, "Map is " + map);

        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {

            HashMap inputHash = (HashMap)map.get("entityLimitMap");

            ITrxContext trxContext = (ITrxContext)map.get("theOBTrxContext");

            // Added because when going from "view limits" to "manual feeds
            // update", some values are set in the trx context object which is
            // "global". Hence has to explicitly set the below to null.
            trxContext.setCustomer(null);
            trxContext.setLimitProfile(null);

            // Session-scoped trx value.
            IEntityLimitTrxValue value = (IEntityLimitTrxValue)map.get(
                    "EntityLimitTrxValue");

//            IEntityLimitProxy proxy = EntityLimitProxyFactory.getProxy();
            IEntityLimitProxy proxy = getEntityLimitProxy();

            IEntityLimitTrxValue resultValue = null;
            
			boolean foundError = false;
				
            DefaultLogger.debug(this, "Current State is " + value.getStatus());
            if ((value.getStatus().equals(ICMSConstant.STATE_ND) || value.getStatus().equals(ICMSConstant.STATE_NEW) || 
            		value.getStatus().equals(ICMSConstant.STATE_ACTIVE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_UPDATE) || 
            		value.getStatus().equals(ICMSConstant.STATE_REJECTED_CREATE)) &&
                	(value.getStagingEntityLimit() == null || value.getStagingEntityLimit().length == 0) &&
                	(value.getEntityLimit() == null || value.getEntityLimit().length == 0))
            {
				resultMap.put("noEntry","noEntry");
				resultMap.put("foundError","foundError");
				foundError = true;
            }
			else {
			
				IEntityLimit[] staging = value.getStagingEntityLimit();
				if (staging != null) {
					DefaultLogger.debug (this, " Staging length: " + staging.length);
					HashMap custMap = new HashMap();
					for (int i=0; i<staging.length; i++) {
						String key = String.valueOf( staging[i].getCustomerID() );
						String key2 = staging[i].getCustIDSource();
						
						if( custMap.get( key.concat( "|" ).concat( key2 )  ) == null ) {
							custMap.put( key.concat( "|" ).concat( key2 ), key.concat( "|" ).concat( key2 ) );
						}
						else {
							foundError = true;
							resultMap.put("duplicateEntry","duplicateEntry");
							resultMap.put("foundError","foundError");
	
							break;
						}
						
					} //end for
				} else {
					staging = new IEntityLimit[0];
					value.setStagingEntityLimit(staging);
				}
									
			}
							
			if ( !foundError ) {
	            resultValue = proxy.makerUpdateEntityLimit(trxContext, value,
	            				value.getStagingEntityLimit());
	            
	            resultMap.put("request.ITrxValue", resultValue);
			}

        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
